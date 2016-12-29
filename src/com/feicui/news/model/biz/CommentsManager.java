package com.feicui.news.model.biz;

import android.content.Context;
import android.content.SharedPreferences;

import com.feicui.news.common.CommonUtil;
import com.feicui.news.common.SystemUtils;
import com.feicui.news.model.httpclient.AsyncHttpClient;
import com.feicui.news.model.httpclient.RequestParams;
import com.feicui.news.model.httpclient.ResponseHandlerInterface;

/**
 * 评论管理
 * 
 * @author qinyq
 * 
 */
public class CommentsManager {

	// cmt_list ?ver=版本号&nid=新闻id&type=1&stamp=yyyyMMdd&cid=评论id&dir=0&cnt=20
	/**
	 * 请求评论
	 * 
	 * @param ver
	 *            版本
	 * @param responseHandler
	 *            回调接口
	 * @param args
	 *            顺序要求如下 。包含 nid (新闻id) dir ( 刷新方向 ，1 表示：上拉刷新，即加载更多的xx条 ， 2
	 *            表示下拉刷新，请求最新的数据) cid 评论id
	 */
	public static void loadComments(String ver,
			ResponseHandlerInterface responseHandler, int... args) {
		String url = CommonUtil.APPURL + "/cmt_list?ver=" + ver + "&nid="
				+ args[0] + "&dir=" + args[1] + "&cid=" + args[2] + "&type="
				+ 1 + "&stamp=" + "20140707";
		new AsyncHttpClient().get(url, responseHandler);
	}

	/**
	 * cmt?ver=版本号&nid=新闻编号&token=用户令牌&imei=手机标识符&ctx=评论内容 发送评论
	 * 
	 * @param context
	 *            上下文
	 * @param nid
	 *            新闻id
	 * @param responseHandler
	 *            回调接口
	 * @param args
	 *            顺序如下：包含 ver:版本 ， token :用户令牌 imei:手机IMEI号 　 ctx : 评论内容
	 */
	public static void sendCommnet(Context context, int nid,
			ResponseHandlerInterface responseHandler, String... args) {
		String url = CommonUtil.APPURL + "/cmt_commit?nid=" + nid + "&ver="
				+ args[0] + "&token=" + args[1] + "&imei=" + args[2] + "&ctx="
				+ args[3];
		new AsyncHttpClient().get(url, responseHandler);
	}

	/*
	 * public static void sendComment(Context context,int nid, String
	 * ccontent,ResponseHandlerInterface responseHandler) { AsyncHttpClient
	 * httpClient=new AsyncHttpClient(); RequestParams params=new
	 * RequestParams(); params.put("cnid", nid); params.put("ccontent",
	 * ccontent); params.put("cip",
	 * SystemUtils.getInstance(context).getPosition()); //--- 后期完成定位功能+
	 * //params.put("cauthor", SystemUtils.getInstance(context).simType());
	 * SharedPreferences preferences=context.getSharedPreferences("userinfo",
	 * Context.MODE_PRIVATE); params.put("cuserid", preferences.getInt("uid",
	 * 0)); httpClient.post(CommonUtil.APPURL+"/DoCommentAdd", params,
	 * responseHandler); }
	 */
	/**
	 * cmt_num?ver=版本号& nid=新闻编号
	 * @param ver
	 *            版本号
	 * @param nid
	 *            新闻id
	 * @param responseHandlerInterface
	 *            回调接口
	 */
	public static void commentNum(int ver, int nid,
			ResponseHandlerInterface responseHandlerInterface) {
		String url = CommonUtil.APPURL + "/cmt_num?nid=" + nid + "&ver="
				+ ver;
		new AsyncHttpClient().get(url, responseHandlerInterface);
	}
}
