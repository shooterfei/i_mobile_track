package com.zeusight.mobile_track.service;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.zeusight.mobile_track.R;
import com.zeusight.mobile_track.listener.MobileSensorListener;
import com.zeusight.mobile_track.task.LocationTimerTask;
import com.zeusight.mobile_track.task.UploadTimerTask;
import com.zeusight.mobile_track.util.LOG;
import com.zeusight.mobile_track.util.LocationUtil;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Timer;

public class LocationService extends Service {
    private Context context;
    private static String userid = "";
    private static String groupid = "";
    private static String serverUrl = "";
    private static String serverName = "";
    private SensorManager sensorManager = null;
    private MobileSensorListener mylistener = new MobileSensorListener();

    /**
     * GPS服务
     */
    public LocationManager locationManager = null;

    @Override
    public IBinder onBind(Intent intent) {
        /*@SuppressLint("ResourceType") TextView textView = (TextView) LayoutInflater.from(context).inflate(R.id.test, null);
        textView.setText("123333");*/
        timerTaskLocation(context);
        return null;
    }

    @Override
    public void onCreate() {
        context = getApplicationContext();

        super.onCreate();
    }

    @Override
    public void onDestroy() {
        sensorManager.unregisterListener(mylistener);
        context = null;
        super.onDestroy();
    }

    public static void startService(Context context, Class c) {
        Intent newIntent = new Intent(context, c);
        context.startService(newIntent);
//        context.bindService(newIntent);
    }

    public static boolean isServiceRuning(Context context, String action) {
        boolean isServiceRunning = false;
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (action.equals(service.service.getClassName())) {
                isServiceRunning = true;
            }
        }
        return isServiceRunning;
    }


    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        flags = START_NOT_STICKY;
        timerTaskLocation(context);
        return super.onStartCommand(intent, flags, startId);
    }

    private void timerTaskLocation(final Context context) {
/*    if (!isGpsEnable(context)) {//GPS不可用 提示
      showTips(context, Settings.ACTION_LOCATION_SOURCE_SETTINGS, "请打开GPS,已保证定位的准确性!");
    } else {*/
        //先GPS定位
//      final JSONObject obj = getGpsLocation(context);
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor acc_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor mag_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor gyroscope_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Sensor gravity_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener(mylistener, acc_sensor, Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(mylistener, mag_sensor, Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(mylistener, gravity_sensor, Sensor.TYPE_GRAVITY);
        sensorManager.registerListener(mylistener, gyroscope_sensor, SensorManager.SENSOR_DELAY_NORMAL);

        Timer timer = new Timer();
        LOG.i("userid===>", userid);
        LOG.i("groupid===>", groupid);
        LOG.i("serverUrl===>", serverUrl);
        LocationTimerTask locationTimerTask = new LocationTimerTask(context);
        UploadTimerTask uploadTimerTask = new UploadTimerTask(context);
        LocationUtil.context = context;
        timer.schedule(locationTimerTask, 0, 200);
        timer.schedule(uploadTimerTask, 60 * 1000, 60 * 1000);
//    }
    }


    /**
     * Gps是否可用
     */
    private boolean isGpsEnable(Context context) {
        initGpsLocation(context);//初始化系统GPS定位
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (gps) {
            return true;
        }
        return false;
    }

    /**
     * 检测当的网络（WLAN、3G/2G）状态
     *
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }


    //初始化GPS定位
    public void initGpsLocation(Context context) {
        if (locationManager == null) {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }
    }


    /**
     * 根据输入流返回一个字符串
     *
     * @param is
     * @return
     * @throws Exception
     */
    private static String getStringFromInputStream(InputStream is) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len = -1;
        while ((len = is.read(buff)) != -1) {
            baos.write(buff, 0, len);
        }
        is.close();
        String html = baos.toString();
        baos.close();
        return html;
    }


}
