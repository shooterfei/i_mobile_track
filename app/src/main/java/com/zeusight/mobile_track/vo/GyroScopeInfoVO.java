package com.zeusight.mobile_track.vo;

import com.zeusight.mobile_track.util.Describe;

import java.util.Arrays;

public class GyroScopeInfoVO {

    private Describe direction;   //方向信息
    private Describe gradient;    //倾斜信息
    private Describe rotation;    //旋转信息
    private long time;            //毫秒数
    private String userid;        //用户id
    private float[] angularVelocity;  //角速度数组
    private float[] acceleration; //加速度数组
    private float[] airHorn;       //航空角 yaw/azimuth pitch roll
    private float[] gravitys;       //重力加速度数组

    public GyroScopeInfoVO() {
        this.direction = new Describe();
        this.gradient = new Describe();
        this.rotation = new Describe();
    }

    public GyroScopeInfoVO(Describe direction, Describe gradient, Describe rotation) {
        this.direction = direction;
        this.gradient = gradient;
        this.rotation = rotation;
    }


    public float[] getGravitys() {
        return gravitys;
    }

    public void setGravitys(float[] gravitys) {
        this.gravitys = gravitys;
    }

    public float[] getAirHorn() {
        return airHorn;
    }

    public void setAirHorn(float[] airHorn) {
        this.airHorn = airHorn;
    }

    public float[] getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(float[] angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    public float[] getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float[] acceleration) {
        this.acceleration = acceleration;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Describe getDirection() {
        return direction;
    }

    public void setDirection(Describe direction) {
        this.direction = direction;
    }

    public Describe getGradient() {
        return gradient;
    }

    public void setGradient(Describe gradient) {
        this.gradient = gradient;
    }

    public Describe getRotation() {
        return rotation;
    }

    public void setRotation(Describe rotation) {
        this.rotation = rotation;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "GyroScopeInfoVO{" +
                "direction=" + direction +
                ", gradient=" + gradient +
                ", rotation=" + rotation +
                ", time=" + time +
                ", userid='" + userid + '\'' +
                ", angularVelocity=" + Arrays.toString(angularVelocity) +
                ", acceleration=" + Arrays.toString(acceleration) +
                ", airHorn=" + Arrays.toString(airHorn) +
                ", gravitys=" + Arrays.toString(gravitys) +
                '}';
    }
}
