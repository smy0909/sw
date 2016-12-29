package com.feicui.news.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.feicui.news.model.entity.BaseEntity;
import com.feicui.news.model.entity.Register;
import com.feicui.news.model.entity.User;

/**
 * SharedPreferences�洢������
 * @author qinqy
 *
 */
public class SharedPreferencesUtils {

	/**
	 * �˷�������ע����ߵ�¼�󣬱�������õ�������
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
	 * �����û�����
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
	 * ����û�����
	 * @param context
	 */
	public static void clearUser(Context context){
		SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.clear();
		editor.commit();
	}
	
	/**
	 * ��ȡtoken
	 * @param context
	 * @return
	 */
	public static String getToken(Context context){
		SharedPreferences sp = context.getSharedPreferences("register", Context.MODE_PRIVATE);
		return sp.getString("token", "");
	}
	
	/**
	 * ��ȡ�û������û�ͷ���ַ
	 * @param context
	 * @return  String����  0 --- username 1 --photo
	 */
	public static String[] getUserNameAndPhoto(Context context){
		SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
		return new String []{sp.getString("userName", ""),sp.getString("headImage", "")};
	}
	
	/**
	 * �����û�ͷ�񱾵�·��
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
	 * ��ȡ����ı���ͷ��·��
	 * @param context
	 * @return 
	 */
	public static String getUserLocalIcon(Context context){
		SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
		return sp.getString("imagePath", null);
	}
}
