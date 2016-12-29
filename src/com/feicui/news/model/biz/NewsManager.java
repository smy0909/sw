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
	 * news_sort?ver=版本号&imei=手机标识符
	 * 加载新闻分类
	 * @param context 上下文
	 * @param responseHandler 回调接口
	 */
	public static void loadNewsType(Context context, ResponseHandlerInterface responseHandler) {
		int ver = CommonUtil.VERSION_CODE;
		String imei = SystemUtils.getIMEI(context);
		AsyncHttpClient httpClient = new AsyncHttpClient();
		httpClient.get(CommonUtil.APPURL+"/news_sort?ver=" + ver + "&imei=" + imei, responseHandler);
	}
	
	/**
	 * news_list?ver=版本号&gid=分类名&dir=1&nid=新闻id&stamp=20140321&cnt=20
	 * 加载新闻数据
	 * @param mode 模式/方向
	 * @param gid 分类号
	 * @param nid 新闻id
	 * @param responseHandler 回调接口
	 */
	public static void loadNewsFromServer(int mode, int subId, int nid, ResponseHandlerInterface responseHandler){
		System.out.println("网络加载");
		//版本号
		int ver = CommonUtil.VERSION_CODE;
		String stamp = CommonUtil.getDate();
		AsyncHttpClient httpClient=new AsyncHttpClient();
		httpClient.get(CommonUtil.APPURL+"/news_list?ver="+ver+"dsf&subid="+subId+"&dir="+mode+"&nid="+nid+"&stamp="+stamp+"&cnt="+20, responseHandler);
	}

	public static void loadNewsFromsLocal(int mode, int curId,LocalResponseHandler handler) {
		System.out.println("数据库加载");
		if(mode==MODE_NEXT){
			
		}else if(mode==MODE_PREVIOUS){
			
		}
	}
	
	public interface LocalResponseHandler{
		public void update(ArrayList<News> data,boolean isCliearOld);
	}

}
