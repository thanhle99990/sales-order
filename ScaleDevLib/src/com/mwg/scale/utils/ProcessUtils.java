package com.mwg.scale.utils;

import java.io.File;
import java.util.function.Consumer;

public class ProcessUtils {
  public static int runCommand(String[] cmds, File workingFolder, Consumer<String> outputConsumer) throws Exception {
    ProcessBuilder builder = new ProcessBuilder();
    if (OsCheck.getOSType() == OsCheck.OSType.Windows) {
      builder.command("cmd.exe", "/c", getCommandLine(cmds));
    } else {
      builder.command("/bin/sh", "-c", getCommandLine(cmds));
    }
    builder.directory(workingFolder);
    Process process = builder.start();
    StreamGobbler processInputStream = StreamGobbler.create(process.getInputStream(), outputConsumer).start();
    while (!processInputStream.isEOF()) {
      Thread.sleep(100);
    }
    return process.waitFor();
  }

  public static String getCDCommand(File path) {
    return OsCheck.getOSType() == OsCheck.OSType.Windows ? "cd /d " + path : "cd " + path;
  }

  private static String getCommandLine(String[] cmds) {
    String cmdSep = OsCheck.getOSType() == OsCheck.OSType.Windows ? " && " : " ; ";
    String cmdString = "";
    for (String cmd : cmds) {
      if (cmdString.isEmpty()) {
        cmdString = cmd;
      } else {
        cmdString = cmdString + cmdSep + cmd;
      }
    }
    return cmdString;
  }
}
