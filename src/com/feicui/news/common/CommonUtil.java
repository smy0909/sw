package com.feicui.news.common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class CommonUtil {
//	public static final String APPURL="http://118.244.212.82:9094/newsClient"; 
	public static final String APPURL="http://20.14.3.61:8080/newsClient"; 
	public static final int VERSION_CODE = 1;
	public static String getSystime(){
		String systime;
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddhhmmss");
		systime=dateFormat.format(new Date(System.currentTimeMillis()));
		return systime;
	}
	
	public static String getFileSize(long fileSize) {
		DecimalFormat df = new DecimalFormat("#.00");
		StringBuffer sb = new StringBuffer();
		if (fileSize < 1024) {
			sb.append(fileSize);
			sb.append(" B");
		} else if (fileSize < 1048576) {
			sb.append(df.format((double) fileSize / 1024));
			sb.append(" K");
		} else if (fileSize < 1073741824) {
			sb.append(df.format((double) fileSize / 1048576));
			sb.append(" M");
		} else {
			sb.append(df.format((double) fileSize / 1073741824));
			sb.append(" G");
		}
		return sb.toString();
	}
	
	/**
	 * ��ȡ��ǰ����
	 * @return   20140716
	 */
	public static String getDate() {
		Date date = new Date(System.currentTimeMillis());
		String strs = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			strs = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strs;
	}
	
	/**
	 * ��֤�����ʽ
	 * @param email  email
	 * @return 
	 */
	public static boolean verifyEmail(String email){
		Pattern pattern = Pattern
				.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)" +
						"|(([a-zA-Z0-9\\-]+\\.)+))" +
						"([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	/***
	 * ��֤�����ʽ
	 * @param password
	 * @return
	 */
	public static boolean verifyPassword(String password){
		Pattern pattern = Pattern
				.compile("^[a-zA-Z0-9]{6,16}$");
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}
	
	/**
	 * ��ȡ��ǰ�İ汾��
	 * 
	 * @param context �����Ķ���
	 * @return ��ǰ�汾
	 */
	public static int getVersionCode(Context context)//��ȡ�汾��(�ڲ�ʶ���)
	{
		try {
			PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pi.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
}
