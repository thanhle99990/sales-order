package com.bss.commons.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ShellCmd {

  private final List<String> cmds = new ArrayList<>();
  private Consumer<String> outputConsumer = null;
  private File workingFolder = null;
  private boolean bash = false;
  private File cdFolder = null;

  private ShellCmd() {
  }

  public static ShellCmd create() {
    return new ShellCmd();
  }

  public ShellCmd addCmd(String cmd) {
    this.cmds.add(cmd);
    return this;
  }

  public ShellCmd workingFolder(File workingFolder) {
    this.workingFolder = workingFolder;
    return this;
  }

  public ShellCmd bash(boolean bash) {
    this.bash = bash;
    return this;
  }

  public ShellCmd cdFolder(File cdFolder) {
    this.cdFolder = cdFolder;
    return this;
  }

  public ShellCmd outputConsumer(Consumer<String> outputConsumer) {
    this.outputConsumer = outputConsumer;
    return this;
  }

  public int exe(long timeoutInMills) throws Exception {
    ProcessBuilder builder = new ProcessBuilder();
    if (cdFolder != null) {
      cmds.add(0, getCDCommand(cdFolder));
    }
    if (OsCheck.getOSType() == OsCheck.OSType.Windows) {
      builder.command("cmd.exe", "/c", getCommandLine(cmds));
    } else {
      builder.command(bash ? "/bin/bash" : "/bin/sh", "-c", getCommandLine(cmds));
    }
    if (workingFolder != null) {
      builder.directory(workingFolder);
    }
    Process process = builder.start();
    long startTime = System.currentTimeMillis();
    StreamGobbler processInputStream = StreamGobbler.create(process.getInputStream(), outputConsumer).start();
    while (!processInputStream.isEOF() && System.currentTimeMillis() - startTime < timeoutInMills) {
      Thread.sleep(100);
    }
    long timeOut = timeoutInMills - (System.currentTimeMillis() - startTime);
    if (timeOut > 0 && process.waitFor(timeOut, TimeUnit.MILLISECONDS)) {
      return process.exitValue();
    }
    process.destroyForcibly();
    throw new Exception(String.format("Timed out in %d ms", timeoutInMills));
  }

  private static String getCDCommand(File path) {
    return OsCheck.getOSType() == OsCheck.OSType.Windows ? "cd /d " + path : "cd " + path;
  }

  private static String getCommandLine(List<String> cmds) {
    String cmdSep = OsCheck.getOSType() == OsCheck.OSType.Windows ? " && " : " ; ";
    return String.join(cmdSep, cmds);
  }
}
