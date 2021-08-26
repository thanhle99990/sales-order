package com.mwg.scale;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.cas.code.CommonDefines.CommState;
import com.cas.code.CommonDefines.EmActionType;
import com.cas.code.CommonDefines.EmDataType;
import com.cas.code.CommonDefines.EmScaleModel;
import com.cas.code.CommonDefines.MapKeyWeightInfo;
import com.cas.code.ResultReceive;
import com.cas.commlib.CasCommLib;
import com.cas.trans.vo.WeightVo;
import com.mwg.scale.exeptions.OverflowException;
import com.mwg.scale.utils.WrappedObject;

public class CASLabelScale implements IScale {
  private String ip = "127.0.0.1";
  private static int port = 20304;
  private static final Map<String, String> scaleEnv = CASHelper.makeScaleEnv(EmDataType.Weight, EmActionType.Take);

  @Override
  public long getValueInGam() throws Exception {
    final CasCommLib cas = new CasCommLib();
    final WrappedObject<Long> result = new WrappedObject<>();
    final WrappedObject<Exception> error = new WrappedObject<>();
    final AtomicBoolean finished = new AtomicBoolean(false);
    final Map<String, String> scaleInfo = CASHelper.makeScaleInfo(this.ip, CASLabelScale.port, EmScaleModel.CL5200);
    cas.sendToScale(scaleEnv, null, scaleInfo, new ResultReceive() {
      @Override
      public void setResultData(Map<String, String> scaleEnv, int workResult, List<Map<String, String>> takeData, Object objSendList, Map<String, String> scaleInfo) {
        try {
          CommState emState = CommState.getEnumByCode(workResult);
          switch (emState) {
          case SUCCESS:
            result.setValue(CASLabelScale.getWeightFromData(takeData));
            break;
          case FAIL:
            result.setValue(CASLabelScale.getWeightFromData(takeData));
            break;
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
    });
    if (!finished.get()) {
      synchronized (finished) {
        finished.wait(scaleTimeoutMs);
      }
    }
    try {
      if (error.getValue() != null) {
        throw error.getValue();
      }
      if (result.getValue() == null) {
        throw new Exception(String.format("Timed out in %d ms", scaleTimeoutMs));
      }
    } finally {
      CASHelper.destroyCasIns(cas);
    }
    return result.getValue();
  }

  private static long getWeightFromData(List<Map<String, String>> takeData) throws Exception {
    Iterator<Map<String, String>> resultIter = takeData.iterator();
    if (resultIter.hasNext()) {
      Map<String, String> resultMap = resultIter.next();
      String errCode = resultMap.get(MapKeyWeightInfo.Error);
      if (errCode != null) {
        switch (Integer.parseInt(errCode)) {
        case WeightVo.STATE_OK:
        case WeightVo.STATE_ERR_STABLE:
          Integer weightDecimalPoint = Integer.parseInt(resultMap.get(MapKeyWeightInfo.WeightDecimalPoint));
          Double weight = Double.parseDouble(resultMap.get(MapKeyWeightInfo.Weight));
          return (long) (weight * Math.pow(10, weightDecimalPoint));
        case WeightVo.STATE_ERR_WEIGHT:
          throw new OverflowException();
        default:
          throw new Exception("UNKNOWN EXCEPTION !");
        }
      } else {
        throw new Exception("CONNECTION FAIL !");
      }
    } else {
      throw new Exception("NO RESPONSE EXCEPTION !");
    }
  }

  @Override
  public String getConf() {
    return this.ip;
  }

  @Override
  public void setConf(String conf) {
    this.ip = conf;
  }

}
