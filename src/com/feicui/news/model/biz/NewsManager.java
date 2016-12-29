package com.feicui.news.model.biz;

import java.util.ArrayList;

import android.content.Context;

import com.feicui.news.MyApplication;
import com.feicui.news.common.CommonUtil;
import com.feicui.news.common.SystemUtils;
import com.feicui.news.model.entity.News;
import com.feicui.news.model.httpclient.AsyncHttpClient;
import com.feicui.news.model.httpclient.ResponseHandlerInterface;

public class NewsManager {
	public static final int MODE_NEXT=1;
	public static final int MODE_PREVIOUS=2;
	
	/**
	 * news_sort?ver=�汾��&imei=�ֻ���ʶ��
	 * �������ŷ���
	 * @param context ������
	 * @param responseHandler �ص��ӿ�
	 */
	public static void loadNewsType(Context context, ResponseHandlerInterface responseHandler) {
		int ver = CommonUtil.VERSION_CODE;
		String imei = SystemUtils.getIMEI(context);
		AsyncHttpClient httpClient = new AsyncHttpClient();
		httpClient.get(CommonUtil.APPURL+"/news_sort?ver=" + ver + "&imei=" + imei, responseHandler);
	}
	
	/**
	 * news_list?ver=�汾��&gid=������&dir=1&nid=����id&stamp=20140321&cnt=20
	 * ������������
	 * @param mode ģʽ/����
	 * @param gid �����
	 * @param nid ����id
	 * @param responseHandler �ص��ӿ�
	 */
	public static void loadNewsFromServer(int mode, int subId, int nid, ResponseHandlerInterface responseHandler){
		System.out.println("�������");
		//�汾��
		int ver = CommonUtil.VERSION_CODE;
		String stamp = CommonUtil.getDate();
		AsyncHttpClient httpClient=new AsyncHttpClient();
		httpClient.get(CommonUtil.APPURL+"/news_list?ver="+ver+"dsf&subid="+subId+"&dir="+mode+"&nid="+nid+"&stamp="+stamp+"&cnt="+20, responseHandler);
	}

	public static void loadNewsFromsLocal(int mode, int curId,LocalResponseHandler handler) {
		System.out.println("���ݿ����");
		if(mode==MODE_NEXT){
			
		}else if(mode==MODE_PREVIOUS){
			
		}
	}
	
	public interface LocalResponseHandler{
		public void update(ArrayList<News> data,boolean isCliearOld);
	}

}
