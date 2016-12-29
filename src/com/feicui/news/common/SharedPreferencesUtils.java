package com.feicui.news.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.feicui.news.model.entity.BaseEntity;
import com.feicui.news.model.entity.Register;
import com.feicui.news.model.entity.User;

/**
 * SharedPreferences存储工具类
 * @author qinqy
 *
 */
public class SharedPreferencesUtils {

	/**
	 * 此方法用于注册或者登录后，保存解析得到的内容
	 * @param context
	 * @param register
	 */
	public static void saveRegister(Context context, BaseEntity<Register> register){
		SharedPreferences sp = context.getSharedPreferences("register", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("message", register.getMessage());
		editor.putInt("status", Integer.parseInt(register.getStatus()));
		Register data = register.getData();
		editor.putString("result", data.getResult());
		editor.putString("token", data.getToken());
		editor.putString("explain", data.getExplain());
		editor.commit();
	}
	
	/**
	 * 保存用户数据
	 * @param context
	 * @param user
	 */
	public static void saveUser(Context context ,BaseEntity<User> user){
		SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		User core = user.getData();
		editor.putString("userName", core.getUid());
		editor.putString("headImage", core.getPortrait());
		editor.commit();
	}
	
	/**
	 * 清除用户数据
	 * @param context
	 */
	public static void clearUser(Context context){
		SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.clear();
		editor.commit();
	}
	
	/**
	 * 获取token
	 * @param context
	 * @return
	 */
	public static String getToken(Context context){
		SharedPreferences sp = context.getSharedPreferences("register", Context.MODE_PRIVATE);
		return sp.getString("token", "");
	}
	
	/**
	 * 获取用户名和用户头像地址
	 * @param context
	 * @return  String【】  0 --- username 1 --photo
	 */
	public static String[] getUserNameAndPhoto(Context context){
		SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
		return new String []{sp.getString("userName", ""),sp.getString("headImage", "")};
	}
	
	/**
	 * 保存用户头像本地路径
	 * @param context
	 * @param path
	 */
	public static void saveUserLocalIcon(Context context ,String path){
		SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("imagePath", path);
		editor.commit();
	}
	
	/**
	 * 获取保存的本地头像路劲
	 * @param context
	 * @return 
	 */
	public static String getUserLocalIcon(Context context){
		SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
		return sp.getString("imagePath", null);
	}
}
