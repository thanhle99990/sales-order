package com.mwg.scale.labelscale;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.cas.code.CommonDefines.CommState;
import com.cas.code.ResultReceive;
import com.mwg.scale.utils.WrappedObject;

public class CASResult implements ResultReceive {
  private static final long timeout = 1 * 60 * 1000L;
  private final WrappedObject<Boolean> result = new WrappedObject<>();
  private final WrappedObject<Exception> error = new WrappedObject<>();
  private final AtomicBoolean finished = new AtomicBoolean(false);

  @Override
  public void setResultData(Map<String, String> scaleEnv, int workResult, List<Map<String, String>> takeData, Object objSendList, Map<String, String> scaleInfo) {
    try {
      CommState emState = CommState.getEnumByCode(workResult);
      switch (emState) {
      case SUCCESS:
        result.setValue(true);
        break;
      case FAIL:
        throw new Exception("FAIL TO GET WEIGTH !");
      case CONNECTION_ERROR:
        throw new Exception("CONNECTION ERROR !");
      case PACKET_ERR_WRONG:
        throw new Exception("MAKE PACKET ERROR !");
      case SCALE_ERR_CHECKSUM:
        throw new Exception("CHECKSUM ERROR !");
      default:
        throw new Exception("UNKNOWN EXCEPTION !");
      }
    } catch (Exception e) {
      error.setValue(e);
    } finally {
      synchronized (finished) {
        finished.set(true);
        finished.notifyAll();
      }
    }
  }

  public void waitForResult() throws Exception {
    if (!finished.get()) {
      synchronized (finished) {
        finished.wait(timeout);
      }
    }
    if (error.getValue() != null) {
      throw error.getValue();
    }
    if (result.getValue() == null) {
      throw new Exception(String.format("Timed out in %d ms", timeout));
    }
  }
}
