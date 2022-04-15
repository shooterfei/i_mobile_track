package com.zeusight.mobile_track

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.permissionx.guolindev.PermissionX
import com.zeusight.mobile_track.service.LocationService
import com.zeusight.mobile_track.util.GyroScopeDataHandle
import com.zeusight.mobile_track.vo.GyroScopeInfoVO
import org.ejml.simple.SimpleMatrix

class MainActivity : AppCompatActivity() {
    companion object {
        val filename = "userinfo" //用户文件名
        var filePath = "/data/data/com.semdo.app.ajb/files/userinfo" //用户文件地址
        val serverFilename = "serverinfo" //服务文件名
        val serverFilePath = "/data/data/com.semdo.app.ajb/files/serverinfo" //服务文件地址
        val locationfile = "locations" //离线定位文件名称
        var textView: TextView? = null
        var handler: Handler? = null
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById<TextView>(R.id.test)
        val accMatrix = SimpleMatrix(3,1)

        handler = object : Handler(Looper.getMainLooper()) {
            @SuppressLint("SetTextI18n")
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
//                LOG.d("TaskInfo:", "gyroscopeInfo==>%s", msg)
                when (msg.what) {
                    1 -> {
                        val gyroScopeInfoVO = msg.obj as GyroScopeInfoVO
//                        accData = matrix_mul(matrix(airHorn, true), acc)
//                        SimpleMatrix(gyroScopeInfoVO.acceleration)
                        /*val mult = SimpleMatrix(GyroScopeDataHandle.matrix(gyroScopeInfoVO.airHorn,
                            true)).mult(
                            SimpleMatrix{ gyroScopeInfoVO.acceleration })*/
                        val simpleMatrix =
                            SimpleMatrix(GyroScopeDataHandle.matrix(gyroScopeInfoVO.airHorn, true))
                        accMatrix.set(0,0, gyroScopeInfoVO.acceleration[0].toDouble())
                        accMatrix.set(1,0, gyroScopeInfoVO.acceleration[1].toDouble())
                        accMatrix.set(2,0, gyroScopeInfoVO.acceleration[2].toDouble())
                        val mult = simpleMatrix.mult(accMatrix)
//                        LOG.d("mult:", mult.toString())

                        textView!!.text = """
                            |原始数据:
                            |
                            |方向:    ${gyroScopeInfoVO.direction}
                            |倾斜信息:  ${gyroScopeInfoVO.gradient}
                            |旋转信息:  ${gyroScopeInfoVO.rotation}
                            |航向角:   ${gyroScopeInfoVO.airHorn.contentToString()}
                            |重力加速度:  ${gyroScopeInfoVO.gravitys.contentToString()}
                            |加速度:   ${gyroScopeInfoVO.acceleration.contentToString()}
                            |角速度:   ${gyroScopeInfoVO.angularVelocity.contentToString()}
                            |
                            |处理后数据:
                            |加速度:   x: ${mult.get(0)}, y: ${mult.get(1)}
                        """.trimMargin()
                    }
                }
            }
        }


        //判断当前服务是否存在
        /*if(!isServiceExisted(getApplicationContext(),"com.semdo.task.CoreService")){//服务关闭就启动起来
        	Intent newIntent = new Intent(getApplicationContext(),CoreService.class);
        	getApplicationContext().startService(newIntent);
        }*/
        PermissionX.init(this)
            .permissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .onExplainRequestReason { scope, deniedList ->
                val message = "请同意定位权限"
                val ok = "确定"
                scope.showRequestReasonDialog(deniedList, message, ok)
            }.onForwardToSettings { scope, deniedListg ->
                val message = "请在设置当中同意定位权限"
                val ok = "确定"
            }
            .request { _, _, _ -> startLocationService() }
        //判断gps服务以及陀螺仪是否存在并使其启动
    }

    private fun startLocationService() {
        println("start locationService")
        val context = textView!!.context
        if (!isServiceExisted(
                context,
                "com.zeusight.mobile_track.LocationService"
            )
        ) { //服务关闭就启动起来
            val newIntent = Intent(context, LocationService::class.java)
            context.startService(newIntent)
/*
            context.bindService(newIntent, object : ServiceConnection {
                override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
//                    TODO("Not yet implemented")
                    LOG.i("bindService", "locationService绑定成功")
                }

                override fun onServiceDisconnected(p0: ComponentName?) {
                    LOG.i("bindService", "locationService解绑定成功")
//                    TODO("Not yet implemented")
                }
            }, BIND_AUTO_CREATE)
*/
        }
    }

    /**
     * 判断service是否运行
     * @return
     */
    fun isServiceExisted(context: Context, className: String): Boolean {
        val activityManager = context
            .getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val serviceList = activityManager
            .getRunningServices(Int.MAX_VALUE)
        if (serviceList.size <= 0) {
            return false
        }
        for (i in serviceList.indices) {
            val serviceInfo = serviceList[i]
            val serviceName = serviceInfo.service
            if (serviceName.className == className) {
                return true
            }
        }
        return false
    }

}