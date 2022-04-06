package com.zeusight.mobile_track.task;


import android.content.Context;

import java.util.TimerTask;

public class UploadTimerTask extends TimerTask {
  private Context context;

  public UploadTimerTask(Context context) {
    this.context = context;
  }

  @Override
  public void run() {

   /* User userInfo = SettingUtils.getUserInfo(context);
    if (userInfo != null) {
      if (LocationService.isNetworkAvailable(context)) {
        List gyroscopeInfoList = new ArrayList<GyroScopeInfoVO>();
        List gpsInfoList = new ArrayList<JSONObject>();
        for (int i = 0; i < CommonLocationSensorsData.getInstance().gyroscopeInfoQueue.size(); i++) {
          gyroscopeInfoList.add(CommonLocationSensorsData.getInstance().gyroscopeInfoQueue.poll());
        }
        for (int i = 0; i < CommonLocationSensorsData.getInstance().GPSInfoQueue.size(); i++) {
          gpsInfoList.add(CommonLocationSensorsData.getInstance().GPSInfoQueue.poll());
        }
        JSONObject jo = new JSONObject();
        try {
          JSONArray gyroscopeArray = new JSONArray(com.alibaba.fastjson.JSONObject.toJSONString(gyroscopeInfoList));
          JSONArray gpsArray = new JSONArray(gpsInfoList);
          jo.put("gyroscopeInfoList", gyroscopeArray);
          jo.put("gpsInfoList", gpsArray);
        } catch (JSONException e) {
          e.printStackTrace();
        }
        System.out.println("result====>" + HttpUtil.postJSON("http://212.129.137.160:9081/SDT_AJB/App/sensorsCollect/gyroscopeCollect", jo));
//        System.out.println("result====>" + HttpUtil.postJSON("http://192.168.2.91:8080/SDT_AJB/App/sensorsCollect/gyroscopeCollect", jo));
      }
    }*/
  }
}
