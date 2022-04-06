package com.zeusight.mobile_track.data.location;


import com.zeusight.mobile_track.vo.GyroScopeInfoVO;

import org.json.JSONObject;

import java.util.Queue;

/**
 * 公用的存储传感器数据的对象
 */
public class CommonLocationSensorsData {
  private CommonLocationSensorsData(){}

  public GyroScopeInfoVO gyroscopeInfo;
  public JSONObject GPSInfo;
  public Queue<GyroScopeInfoVO> gyroscopeInfoQueue;
  public Queue GPSInfoQueue;

  public static CommonLocationSensorsData getInstance() {
    return Singleton.INSTANCE.getInstance();
  }
  private enum Singleton{
    INSTANCE;
    private CommonLocationSensorsData singleton;
    Singleton() {
      singleton = new CommonLocationSensorsData();
    }
    public CommonLocationSensorsData getInstance() {
      return singleton;
    }
  }
}
