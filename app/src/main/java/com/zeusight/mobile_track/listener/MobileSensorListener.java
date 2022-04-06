package com.zeusight.mobile_track.listener;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.zeusight.mobile_track.util.LocationUtil;


public class MobileSensorListener implements SensorEventListener {
  @Override
  public void onSensorChanged(SensorEvent event) {
    if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
      LocationUtil.accValues = event.values.clone();
    }
    else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
      LocationUtil.magValues = event.values.clone();
    }
    else if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
      LocationUtil.gyroScopeValues = event.values.clone();
    }
    else if(event.sensor.getType() == Sensor.TYPE_GRAVITY) {
      LocationUtil.gravityValues = event.values.clone();
    }

  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {

  }
}
