package com.zeusight.mobile_track.util;

import android.content.Context;
import android.util.Log;


import com.zeusight.mobile_track.MainActivity;
import com.zeusight.mobile_track.saveuser.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * APP系统配置文件工具类
 * @author cyhuang
 *
 */
public class SettingUtils {
	
	/**
	 * 根据编码获取配置值
	 * @param code
	 * @return
	 */
	public static String getSettingByCode(String code,String serverUrl){
		/**
		 * {"status":"1","t":{"id":"4cf0f6b9331c453e931369a9fcfc5e8c","isNewRecord":false,"remarks":"1.蹲守模块上线;\r\n2.隐患缺陷流程优化;\r\n3.系统bug修改与完善;","createDate":"2018-05-03 15:37:04","updateDate":"2018-05-03 15:37:04","moduleId":"260ad31f7d1e4e8c92d2ed33e63bb992","code":"APP.VERSION","name":"APP版本更新","showType":"1","value":"1.6","sort":0,"moduleName":"系统模块(APP)","moduleIsLock":"0","delete":true,"moduleType":"2"}}
		 */
		String res = null;

		return res;
	}
	

	/**
	 * 获取用户信息
	 * @return
	 */
	public static User getUserInfo(Context context){
    	/*FileInputStream fis = null;
    	ObjectInputStream ois = null;
    	User user = null;
    	try{
    		if(fileIsExists(MainActivity.Companion.getFilePath())){
    			fis = context.openFileInput(MainActivity.Companion.getFilename());
        		ois = new ObjectInputStream(fis);
        		user = (User)ois.readObject();
        		Log.i("system", "user : "+user.getUserid()+" , groupid ; "+user.getGroupid());
    		}else{
    			Log.i("system", "文件不存在");
    		}
    	}catch(Exception e){
    		Log.i("system", "获取用户信息失败");
       	 	Log.i("system", e.getMessage());
    		e.printStackTrace();
    	}finally{
    		try {
    			if(ois!=null){
    				ois.close();
    			}
				if(fis!=null){
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}*/
		User user1 = new User();
    	user1.setUserid("1");
    	user1.setGroupid("1");
		return user1;
    }
	
	/**
	 * 文件是否存在
	 * @return
	 */
	public static boolean fileIsExists(String filePath){
		try{
			File f=new File(filePath);
			if(f.exists()){
				return true;
			}
		}catch(Exception e){
			return false;
		}
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println(SettingUtils.getSettingByCode("system_app_location_open","http://192.168.10.136:8080/xjb_boot/app"));
	}

}
