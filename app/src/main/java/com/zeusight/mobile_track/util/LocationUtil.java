package com.zeusight.mobile_track.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.zeusight.mobile_track.vo.GyroScopeInfoVO;

import org.json.JSONObject;

import java.text.DecimalFormat;

public class LocationUtil {

    //加速度传感器数据
    public static float accValues[] = new float[3];
    //地磁传感器数据
    public static float magValues[] = new float[3];
    //陀螺仪传感器数据
    public static float gyroScopeValues[] = new float[3];

    public static float gravityValues[] = new float[3];
    public static Context context;

    @SuppressLint("MissingPermission")
    public static JSONObject getGPSLocationInfo(Context context) {
        DecimalFormat df = new DecimalFormat("0.000000");
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (loc == null) {
            loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (loc == null) {
            //showMessage(getApplicationContext(), "loc==null");
            return null;
        }
        if ("0.000000".equals(df.format(loc.getLongitude()))) {
            //showMessage(getApplicationContext(), "0.000000");
            return null;
        }
        //long elapsedTime = Math.abs(DateT.getCurrentTimestamp().getTime() - loc.getTime());
        //if(elapsedTime >= 5 * 60 * 1000){//GPS坐标5分钟未更新 才用百度定位插件
        //	return null;
        //}
        try {
/*      JSONObject obj = LocationService.getLocObjByXY(loc.getLongitude() + "", loc.getLatitude() + "", context, loc.getTime());
      if (obj == null) {
        //showMessage(getApplicationContext(), "obj == null");
        return null;
      }
      obj.put("dwlx", "1");
      obj.put("dwjk", "1");
      obj.put("dwfs", "1");
      obj.put("dwtype", "GPS");
      obj.put("bj", "");*/
            JSONObject obj = new JSONObject();
            obj.put("longitude",loc.getLongitude());
            obj.put("latitude",loc.getLatitude());
            return obj;
        } catch (Exception e) {
            String errMsg = e.getMessage();
            LOG.e("获取GPS位置", errMsg, e);
        }
        return null;
    }


    public static GyroScopeInfoVO getGyroscopeInfo(Context context) {
        float[] r = new float[9];
        final float[] values = new float[3];
        SensorManager.getRotationMatrix(r, null, accValues, magValues);
        SensorManager.getOrientation(r, values);
        int directionValue = (int) Math.round(Math.toDegrees(values[0]));        //方向角
        if (directionValue<0)
            directionValue += 360;
        int pitchValue = (int) Math.round(Math.toDegrees(values[1]));             //倾斜角
        int rollValue = (int) Math.round(Math.toDegrees(values[2]));              //旋转角
        GyroScopeInfoVO gyroScopeInfoVO = new GyroScopeInfoVO();
        gyroScopeInfoVO.getDirection().setLabel(getDirections(directionValue));
        gyroScopeInfoVO.getDirection().setAngle(directionValue);
        gyroScopeInfoVO.getGradient().setLabel(pitchValue > 0 ? "反倾:" : "正倾:");
        gyroScopeInfoVO.setAirHorn(values);
        gyroScopeInfoVO.setGravitys(gravityValues);
        gyroScopeInfoVO.getGradient().setAngle(Math.abs(pitchValue));
        gyroScopeInfoVO.getRotation().setLabel(rollValue > 0 ? "左旋:" : "右旋:");
        gyroScopeInfoVO.getRotation().setAngle(Math.abs(rollValue));
        gyroScopeInfoVO.setAngularVelocity(gyroScopeValues);
        gyroScopeInfoVO.setAcceleration(accValues);
        return gyroScopeInfoVO;
    }

    public static String getDirections(int v) {
        int deviation = 15;     //正向偏差角,一般最好不要超过30度

        if (v > 360 - deviation || v <= deviation)
            return "北";
        if (v > deviation && v <= 90 - deviation)
            return "东北";
        if (v > 90 - deviation && v <= 90 + deviation)
            return "东";
        if (v > 90 + deviation && v <= 180 - deviation)
            return "东南";
        if (v > 180 - deviation && v <= 180 + deviation)
            return "南";
        if (v > 180 + deviation && v <= 270 - deviation)
            return "西南";
        if (v > 270 - deviation && v <= 270 + deviation)
            return "西";
        if (v > 270 + deviation && v <= 360 - deviation)
            return "西北";
        else
            return "错误";
    }
}
