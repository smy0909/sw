package com.feicui.news;

import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.content.res.Configuration;
import cn.jpush.android.api.JPushInterface;

import com.feicui.news.common.LogUtil;

public class MyApplication extends Application{
	private static  Map<String, Object> globalData=new HashMap<String, Object>();
	
	public static MyApplication application;

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		application=this;
		//初始化极光推送
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
		LogUtil.d(LogUtil.TAG, "MyApplication  onCreate() ");
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		LogUtil.d(LogUtil.TAG, "MyApplication  onLowMemory() ");
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		LogUtil.d(LogUtil.TAG, "MyApplication  onTerminate() ");
	}
	
	//增加数据
	public static void addCacheData(String key,Object value){
		globalData.put(key, value);
	}
	
	
	//删除数据
	public static void delCacheData(String key){
		if(globalData.containsKey(key))
			globalData.remove(key);
	}
	
	
	//清空数据
	public static void clearCacheData(){
		globalData.clear();
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public static Object getCacheData(String key){
		if(globalData.containsKey(key))
			return globalData.get(key);
		return null;
	}
	
	
	
}
