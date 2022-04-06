package com.zeusight.mobile_track.task;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zeusight.mobile_track.MainActivity;
import com.zeusight.mobile_track.R;
import com.zeusight.mobile_track.data.location.CommonLocationSensorsData;
import com.zeusight.mobile_track.saveuser.User;
import com.zeusight.mobile_track.service.LocationService;
import com.zeusight.mobile_track.util.LOG;
import com.zeusight.mobile_track.util.LocationUtil;
import com.zeusight.mobile_track.util.SettingUtils;
import com.zeusight.mobile_track.vo.GyroScopeInfoVO;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

public class LocationTimerTask extends TimerTask {
    private Context context;
    private Handler mHandler;

    public LocationTimerTask(Context context) {
        this.context = context;
        mHandler = MainActivity.Companion.getHandler();
    }

    @Override
    public void run() {
        if (CommonLocationSensorsData.getInstance().gyroscopeInfoQueue == null)
            CommonLocationSensorsData.getInstance().gyroscopeInfoQueue = new LinkedBlockingQueue();
        if (CommonLocationSensorsData.getInstance().GPSInfoQueue == null)
            CommonLocationSensorsData.getInstance().GPSInfoQueue = new LinkedBlockingQueue();
        User userInfo = SettingUtils.getUserInfo(context);
        if (userInfo != null) {
            if (LocationService.isNetworkAvailable(context)) {
                String userid = userInfo.getUserid();
                //采集数据添加至队列中
                GyroScopeInfoVO gyroscopeInfo = LocationUtil.getGyroscopeInfo(context);
                JSONObject gpsLocationInfo = LocationUtil.getGPSLocationInfo(context);
                if (gpsLocationInfo != null) {
                    try {
                        gpsLocationInfo.put("userid", userid);
                        gyroscopeInfo.setUserid(userid);
                        long timeInMillis = Calendar.getInstance().getTimeInMillis();       //获取当前毫秒数
                        gyroscopeInfo.setTime(timeInMillis);
                        try {
                            gpsLocationInfo.put("time", timeInMillis);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        CommonLocationSensorsData.getInstance().gyroscopeInfoQueue.add(gyroscopeInfo);
                        CommonLocationSensorsData.getInstance().GPSInfoQueue.add(gpsLocationInfo);
                        CommonLocationSensorsData.getInstance().GPSInfo = gpsLocationInfo;
                        CommonLocationSensorsData.getInstance().gyroscopeInfo = gyroscopeInfo;

                       Message msg = Message.obtain();
                        msg.what = 1;
                        msg.obj = gyroscopeInfo.getAcceleration()[0];
                        mHandler.sendMessage(msg);
//                        LOG.d("TaskInfo:", "gyroscopeInfo==>%s", gyroscopeInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}
