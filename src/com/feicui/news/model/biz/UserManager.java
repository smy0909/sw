package com.feicui.news.model.biz;

import java.io.File;
import java.io.FileNotFoundException;
import com.feicui.news.common.CommonUtil;
import com.feicui.news.common.LogUtil;
import com.feicui.news.common.SharedPreferencesUtils;
import com.feicui.news.common.SystemUtils;
import com.feicui.news.model.httpclient.AsyncHttpClient;
import com.feicui.news.model.httpclient.RequestParams;
import com.feicui.news.model.httpclient.ResponseHandlerInterface;

import android.content.Context;
import android.os.AsyncTask;

public class UserManager {
	private static UserManager userManager;
	private Context context;
	private String imei;

	private UserManager(Context context) {
		this.context = context;
		imei = SystemUtils.getIMEI(context);
	}

	public static UserManager getInstance(Context context) {
		if (userManager == null)
			userManager = new UserManager(context);
		return userManager;
	}

	public void user(String token, ResponseHandlerInterface handler) {
		LogUtil.i("�û����Ľ���", "ִ��");
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("token", token);
		params.put("imei", imei);
		params.put("ver", 1);
		httpClient.post(CommonUtil.APPURL + "/user_home", params, handler);
	}

	/**
	 * user_register?ver=�汾��&uid=�û���&email=����&pwd=��½����
	 * 
	 * @param handler
	 *            �ص��ӿ�
	 * @param args
	 *            �����������£� ver : �汾 uid : �û��ǳ� pwd : ���� email : ����
	 */
	public void register(ResponseHandlerInterface handler, String... args) {
		LogUtil.d(LogUtil.TAG, "ִ��ע��...");
		// 1.�ύ���������� �����û�
		AsyncHttpClient httpClient = new AsyncHttpClient();
		httpClient.get(CommonUtil.APPURL + "/user_register?ver=" + args[0]
				+ "&uid=" + args[1] + "&pwd=" + args[2] + "&email=" + args[3],
				handler);
	}

	/**
	 * ��http://118.244.212.82:9094//newsClient/login?uid=admin&pwd=admin&imei=
	 * abc&ver=1&device=1
	 * http://118.244.212.82:9094//newsClient/login?ver=1&uid=
	 * admin&pwd=admin&device=000000000000000 �û���¼������
	 * 
	 * @param handler
	 *            �ص��ӿ�
	 * @param args
	 *            �����������£� ver : �汾 uid : �û��ǳ� pwd : ���� imei : �ֻ�IMEI�� device :
	 *            ��¼�豸 �� 0 Ϊ�ƶ��� ��1ΪPC��
	 */
	public void login(ResponseHandlerInterface handler, String... args) {
		LogUtil.d(LogUtil.TAG, "ִ�е�¼...");
		// 1.�ύ���������� �����û�
		// login?uid=admin&pwd=admin&imei=abc&ver=1&device=1
		AsyncHttpClient httpClient = new AsyncHttpClient();
		httpClient.get(CommonUtil.APPURL + "/user_login?ver=" + args[0]
				+ "&uid=" + args[1] + "&pwd=" + args[2] + "&device=" + args[3],
				handler);
	}

	/***
	 * user_forgetpass?ver=�汾��&email=����
	 * 
	 * @param handler
	 * @param args
	 *            �����Ĳ������£�
	 *             ver���汾�� 
	 *             email������
	 */
	public void forgetPass(ResponseHandlerInterface handler, String... args) {
		LogUtil.d(LogUtil.TAG, "ִ����������...");
		AsyncHttpClient httpClient = new AsyncHttpClient();
		httpClient.get(CommonUtil.APPURL + "/user_forgetpass?ver=" + args[0]
				+ "&email=" + args[1] ,
				handler);
	}

	/**
	 * 
	 * http://118.244.212.82:9094//newsClient/home?ver=sfkl&token=admin1abc&imei
	 * =sdf ��ȡ�û���������
	 * 
	 * @param handler
	 *            �ص��ӿ�
	 * @param args
	 *            �������� ˳������ ver : �汾 token : ���� imei :�ֻ�IMEI
	 */
	public void getUserInfo(ResponseHandlerInterface handler, String... args) {
		LogUtil.d(LogUtil.TAG, "ִ���û�����...");
		new AsyncHttpClient().get(CommonUtil.APPURL + "/user_home?ver="
				+ args[0] + "&token=" + args[1] + "&imei=" + args[2], handler);
	}
	
	/**
	 * �����û�ͷ��
	 * @param email
	 * @param file
	 * @param handler
	 */

	public void changePhoto(String token ,File file,
			ResponseHandlerInterface handler) {
		System.out.println("�޸�ͼ��");
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		try {
//			params.put("token",token);
			params.put("portrait", file);
			System.out.println("===========================");
			httpClient.post(CommonUtil.APPURL+"/user_image?token="+token, params, handler);		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
