package com.feicui.news.model.biz;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.feicui.news.common.CommonUtil;
import com.feicui.news.model.httpclient.AsyncHttpClient;
import com.feicui.news.model.httpclient.ResponseHandlerInterface;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

public class UpdateManager {
	
	/**
	 * ���ذ汾
	 * @param context
	 * @param url ���ص�ַ
	 */
	public static void downLoad(Context context, String url) {
		DownloadManager manager = (DownloadManager) context
				.getSystemService(Context.DOWNLOAD_SERVICE); // ��ʼ�����ع�����
		DownloadManager.Request request = new DownloadManager.Request(
				Uri.parse(url));// ��������
		// ��������ʹ�õ��������ͣ�wifi
		request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
		// ��֪ͨ����ʾ��������  ��API 11�б�setNotificationVisibility()ȡ��
		request.setShowRunningNotification(true);
		// ��ʾ���ؽ��� 
		request.setVisibleInDownloadsUi(true);
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-ddhh-mm-ss");
		String date = dateformat.format(new Date());
		//�������غ��ļ���ŵ�λ��--���Ŀ��λ���Ѿ���������ļ�������ִ�����أ�������date�������ȡ����
		request.setDestinationInExternalFilesDir(context, null, date + ".apk");
		manager.enqueue(request);// ����������������
	}

	/**
	 * �ж��Ƿ����
	 * @param url ����·����ַ
	 * @param responseHandler �ص��ӿ�
	 * @param args ������� ��˳�����£�arg[0] : IMEI ,  arg[1] : pkg , arg[2] : ver 
	 */
	public static void judgeUpdate( ResponseHandlerInterface responseHandler  , String ...args){
		String url = CommonUtil.APPURL+"/update?imei="+args[0]+"&pkg="+args[1]+"&ver="+args[2];
		new AsyncHttpClient().get(url, responseHandler);
	}
}
