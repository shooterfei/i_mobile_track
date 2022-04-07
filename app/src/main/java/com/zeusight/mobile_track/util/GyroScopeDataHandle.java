package com.zeusight.mobile_track.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.ejml.simple.SimpleMatrix;

import java.util.Arrays;
import java.util.stream.Stream;

public class GyroScopeDataHandle {
 /*   let p0 = {x: 0, y: 0}



    //data 为要转化的值, pointCollection 为航向角集合 yaw pitch roll
    function transformX(data, pointCollection) {
        if (!pointCollection)
            pointCollection = airHorn
        // return data * Math.cos(pointCollection[0]) * Math.cos(pointCollection[2])
        return data
    }

    //data 为要转化的值, pointCollection 为航向角集合 yaw pitch roll
    function transformY(data, pointCollection) {
        if (!pointCollection)
            pointCollection = airHorn
        // return data * Math.cos(pointCollection[0]) * Math.cos(pointCollection[1])
        return data
    }

*//**
     *     求逆矩阵方法 原矩阵为   |a    b    c|
     *                          |d    e    f|
     *                          |g    h    i|
     *//*
    const matrix_inv = (m) => {
            let a = m[0][0]
            let b = m[0][1]
            let c = m[0][2]
            let d = m[1][0]
            let e = m[1][1]
            let f = m[1][2]
            let g = m[2][0]
            let h = m[2][1]
            let i = m[2][2]

            // 余子式矩阵行列式
            let rc = a * (e * i - h * f) - b * (d * i - g * f) + c * (d * h - g * e)

            let row_0 = [e * i - h * f, h * c - b * i, b * f - c * e]
            let row_1 = [f * g - i * d, i * a - c * g, c * d - a * f]
            let row_2 = [d * h - g * e, g * b - a * h, a * e - b * d]
            let res = [row_0, row_1, row_2]
            return res.map(item => {
            return item.map(i => {
            return i / rc
            })
            })
            }

            // 通过航空角计算旋转矩,为之后求逆矩阵所用, inverse 是否为逆时针旋转
            const matrix = (angArr, inverse) => {

            // y = yaw 绕z轴 | p = pitch 绕x轴｜ r = roll 绕y轴
            let y = angArr[0], p = angArr[1], r = angArr[2]
            if (inverse) {
            y = -y
            p = -p
            r = -r
            }
      *//*
              [                       cos(p)*cos(y),                       -cos(p)*sin(y),         sin(p)]
              [cos(r)*sin(y) + cos(y)*sin(p)*sin(r), cos(r)*cos(y) - sin(p)*sin(r)*sin(y), -cos(p)*sin(r)]
              [sin(r)*sin(y) - cos(r)*cos(y)*sin(p), cos(y)*sin(r) + cos(r)*sin(p)*sin(y),  cos(p)*cos(r)]
      *//*
     *//*
            [                       cos(r)*cos(y),                       -cos(r)*sin(y),         sin(r)]
      [cos(p)*sin(y) + cos(y)*sin(p)*sin(r), cos(p)*cos(y) - sin(p)*sin(r)*sin(y), -cos(r)*sin(p)]
      [sin(p)*sin(y) - cos(p)*cos(y)*sin(r), cos(y)*sin(p) + cos(p)*sin(r)*sin(y),  cos(p)*cos(r)]
      *//*

            let row_0 = [
            Math.cos(r) * Math.cos(y),
            Math.cos(r) * Math.sin(y),
            -Math.sin(r)
            ]
            let row_1 = [
            Math.cos(y) * Math.sin(p) * Math.sin(r) - Math.cos(p) * Math.sin(y),
            Math.cos(p) * Math.cos(y) + Math.sin(r) * Math.sin(p) * Math.sin(y),
            Math.cos(r) * Math.sin(p)
            ]
            let row_2 = [
            Math.sin(p) * Math.sin(y) + Math.cos(p) * Math.cos(y) * Math.sin(r),
            Math.cos(p) * Math.sin(r) * Math.sin(y) - Math.cos(y) * Math.sin(p),
            Math.cos(p) * Math.cos(r)
            ]
            return [row_0, row_1, row_2]
            }

            // 通过逆矩阵运算出加速度数组 仅考虑 3*3 矩阵 乘 1*3 矩阵，未做通用适配
            const matrix_mul = (m_inv, acc) => {
            let res = []
            return m_inv.map(item => {
            return item[0] * acc[0] + item[1] * acc[1] + item[2] * acc[2]
            })
            }

            //实时坐标,data 为加速度数据,t为时间，单位秒
            function realPoint(data, t) {
            let accData = data.acceleration
            airHorn = data.airHorn // 航空角 yaw/azimuth pitch roll

            // 三轴重力加速度
            let m_g = data.gravitys
            let acc = accData.map((item, index) => {
            return item - m_g[index]
            })
            console.log("accData====>", accData)
            console.log("acc====>", acc)
            console.log("gData====>", m_g)
            console.log("airHorn====>", airHorn)
            accData = matrix_mul(matrix(airHorn, true), acc)
            realAcc = [...accData]

            // 减少重力加速度的影响
      *//*      accData[0] -= (-g * Math.sin(airHorn[2]))
            accData[1] -= (-g * Math.cos(airHorn[2]) * Math.sin(airHorn[1]))
            accData[2] -= (g * Math.cos(airHorn[1]) * Math.cos(airHorn[2]))*//*

     *//*

            console.log('treated===> ', JSON.stringify(accData))*//*

            if (!t)
            t = 1

            // 此处进行坐标系转化 公式为 vx = (v0x + ax * t) * cos(yaw) * cos(roll)
            // vy = (v0y + ay * t) * cos(yaw) * cos(pitch)
            let v1 = {
            // x: v0.x + accData[0] * t,
            // y: v0.y + accData[1] * t
            x: Math.round((transformX(v0.x) + transformX(accData[0]) * t) * 100) / 100,
            y: Math.round((transformY(v0.y) + transformY(accData[1]) * t) * 100) / 100
            }

            let p1 = {
            x: p0.x + (v0.x + v1.x) / 2 * t,
            y: p0.y + (v0.y + v1.y) / 2 * t
            }
            v0 = v1
            p0 = p1
            }

*/

    /**
     * 求逆矩阵方法 原矩阵为   |a    b    c|
     * |d    e    f|
     * |g    h    i|
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static SimpleMatrix matrix_inv(float[][] m) {

       /* float a = m[0][0];
        float b = m[0][1];
        float c = m[0][2];
        float d = m[1][0];
        float e = m[1][1];
        float f = m[1][2];
        float g = m[2][0];
        float h = m[2][1];
        float i = m[2][2];


        // 余子式矩阵行列式
        float rc = a * (e * i - h * f) - b * (d * i - g * f) + c * (d * h - g * e);

        float[] row_0 = {e * i - h * f, h * c - b * i, b * f - c * e};
        float[] row_1 = {f * g - i * d, i * a - c * g, c * d - a * f};
        float[] row_2 = {d * h - g * e, g * b - a * h, a * e - b * d};
        float[][] res = {row_0, row_1, row_2};*/
        /*        return Arrays.stream(res)
                .map(item -> Arrays.stream(item).map(ite -> ite/rc))
                .toArray(float[][]::new);*/
//        Arrays.stream(res).flatMap(rows -> Arrays.stream(rows))
//        int[][] ints = Arrays.stream(data).map(integers -> Arrays.stream(integers).mapToInt(value -> value/2).toArray()).toArray(int[][]::new);

//        return Arrays.stream(res).map(row -> Arrays.stream(row).map(row).map());

        return new SimpleMatrix(m).invert();
        /*return res.map(item = > {
        return item.map(i = > {
        return i / rc
        })
      })*/
    }

    /**
     * 欧拉旋转计算
     * @param angArr 三轴角度
     * @return
     */
    public static SimpleMatrix eularRotation(float[] angArr) {
        return  new SimpleMatrix(3,3);
    }

    public static double[][] matrix(float[] angArr, boolean inverse) {

        // y = yaw 绕z轴 | p = pitch 绕x轴｜ r = roll 绕y轴
        float y = angArr[0], p = angArr[1], r = angArr[2];
        if (inverse) {
            y = -y;
            p = -p;
            r = -r;
        }
      /*
              [                       cos(p)*cos(y),                       -cos(p)*sin(y),         sin(p)]
              [cos(r)*sin(y) + cos(y)*sin(p)*sin(r), cos(r)*cos(y) - sin(p)*sin(r)*sin(y), -cos(p)*sin(r)]
              [sin(r)*sin(y) - cos(r)*cos(y)*sin(p), cos(y)*sin(r) + cos(r)*sin(p)*sin(y),  cos(p)*cos(r)]
      */
      /*
            [                       cos(r)*cos(y),                       -cos(r)*sin(y),         sin(r)]
      [cos(p)*sin(y) + cos(y)*sin(p)*sin(r), cos(p)*cos(y) - sin(p)*sin(r)*sin(y), -cos(r)*sin(p)]
      [sin(p)*sin(y) - cos(p)*cos(y)*sin(r), cos(y)*sin(p) + cos(p)*sin(r)*sin(y),  cos(p)*cos(r)]
      */

        double[] row_0 = {
                Math.cos(r) * Math.cos(y),
                Math.cos(r) * Math.sin(y),
                -Math.sin(r)
        };
        double[] row_1 = {
                Math.cos(y) * Math.sin(p) * Math.sin(r) - Math.cos(p) * Math.sin(y),
                Math.cos(p) * Math.cos(y) + Math.sin(r) * Math.sin(p) * Math.sin(y),
                Math.cos(r) * Math.sin(p)
        };
        double[] row_2 = {
                Math.sin(p) * Math.sin(y) + Math.cos(p) * Math.cos(y) * Math.sin(r),
                Math.cos(p) * Math.sin(r) * Math.sin(y) - Math.cos(y) * Math.sin(p),
                Math.cos(p) * Math.cos(r)
        };
        return new double[][]{row_0, row_1, row_2};
    }

}
