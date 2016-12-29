package com.feicui.news.common;

import org.apache.http.Header;
import org.apache.http.client.ResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import com.feicui.news.model.httpclient.AsyncHttpClient;
import com.feicui.news.model.httpclient.JsonHttpResponseHandler;
import com.feicui.news.model.httpclient.ResponseHandlerInterface;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;

public class SystemUtils {
	private static SystemUtils systemUtils;
	private Context context;
	private TelephonyManager telManager;
	private ConnectivityManager connManager;
	private LocationManager locationManager;
	private String position;

	private SystemUtils(Context context) {
		this.context = context;
		telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		locationManager=(LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	}
	
	public static SystemUtils getInstance(Context context) {
		if (systemUtils == null) {
			systemUtils = new SystemUtils(context);
		}
		return systemUtils;
	}
	/**�ж������Ƿ�����*/
	public boolean isNetConn() {
		NetworkInfo info = connManager.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			return true;
		}
		return false;
	}
	/**��ȡsim��������*/
	public String simType(){
		String simOperator = telManager.getSimOperator();
		String type = "";		
		if ("46000".equals(simOperator)) {
			type = "�ƶ�";
		} else if ("46002".equals(simOperator)) {
			type = "�ƶ�";
		} else if ("46001".equals(simOperator)) {
			type = "��ͨ";
		} else if ("46003".equals(simOperator)) {
			type = "����";
		}
		return type;
	}
	
	/**
	 * ��ȡ�ֻ�IMEI ����
	 * @return IMEI
	 */
	public String getIMEI(){
		return telManager.getDeviceId();
	}
	
	public String getPosition(){
		return this.position;
	}
	
	public void locatPosition(){
		 locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,0,new LocationListener(){
			public void onLocationChanged(Location location) {
				double longitude=location.getLongitude();
				double latitude=location.getLatitude();
				AsyncHttpClient client=new AsyncHttpClient();
				client.get("http://maps.googleapis.com/maps/api/geocode/json?latlng="+longitude+","+latitude+"&sensor=false", handler);
			}
			public void onStatusChanged(String provider, int status,Bundle extras) {}
			public void onProviderEnabled(String provider) {}
			public void onProviderDisabled(String provider) {}
		 });
	}
	
	private JsonHttpResponseHandler handler=new JsonHttpResponseHandler(){

		@Override
		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
			if(statusCode==200){
				System.out.println(response.toString());
			}
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
			if(statusCode==200){
				System.out.println(response.toString());
			}
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
			super.onFailure(statusCode, headers, responseString, throwable);
			System.out.println("onFailure");
		}
		
	};
	
	/**
	 * ��ȡ�ֻ���IMEIֵ
	 */
	
	public static String getIMEI(Context context){
		TelephonyManager telephonyManager= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}
}
