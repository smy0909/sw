package com.feicui.news.ui;

import java.util.List;

import org.apache.http.Header;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.feicui.news.R;
import com.feicui.news.common.CommonUtil;
import com.feicui.news.common.LogUtil;
import com.feicui.news.common.SharedPreferencesUtils;
import com.feicui.news.common.SystemUtils;
import com.feicui.news.model.biz.CommentsManager;
import com.feicui.news.model.biz.NewsManager;
import com.feicui.news.model.biz.parser.ParserComments;
import com.feicui.news.model.entity.Comment;
import com.feicui.news.model.httpclient.JsonHttpResponseHandler;
import com.feicui.news.model.httpclient.TextHttpResponseHandler;
import com.feicui.news.ui.adapter.CommentsAdapter;
import com.feicui.news.ui.base.MyBaseActivity;
import com.feicui.news.view.xlistview.XListView;
import com.feicui.news.view.xlistview.XListView.IXListViewListener;
import com.google.gson.JsonObject;
/**评论界面***/
public class ActivityComment extends MyBaseActivity {
	/**新闻id*/
	private int nid;
	/**评论列表*/
	private XListView listView;
	/**评论列表适配器*/
	private CommentsAdapter adapter;
	/***/
    private  int mode;	
    /**发送评论按钮*/
    private ImageView imageView_send;
    /**返回按钮*/
    private ImageView imageView_back;
    /**评论编辑框*/
    private EditText editText_content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		nid=getIntent().getIntExtra("nid", -1);
		Log.d(LogUtil.TAG, "nid------------->"+nid);
		listView= (XListView) findViewById(R.id.listview);
		imageView_send=(ImageView) findViewById(R.id.imageview);
		imageView_back=(ImageView) findViewById(R.id.imageView_back);
		editText_content=(EditText) findViewById(R.id.edittext_comment);
		adapter=new CommentsAdapter(this, listView);
		listView.setAdapter(adapter);
		listView.setPullRefreshEnable(true);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(listViewListener);	
		loadNextComment();
		
		imageView_back.setOnClickListener(clickListener);
		imageView_send.setOnClickListener(clickListener);
	}
	
	private View.OnClickListener clickListener=new View.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.imageView_back://返回按钮
				finish();
				break;
			case R.id.imageview://发送评论按钮
				String ccontent=editText_content.getText().toString();
				if(ccontent==null || ccontent.equals("")){
					Toast.makeText(ActivityComment.this, "要先写评论内容哦，亲！", Toast.LENGTH_SHORT).show();
					return;
				}
				imageView_send.setEnabled(false);
				String imei = SystemUtils.getInstance(ActivityComment.this).getIMEI();
				String token = SharedPreferencesUtils.getToken(ActivityComment.this);
				if(TextUtils.isEmpty(token)){
					Toast.makeText(ActivityComment.this, "对不起，您还没有登录.", 0).show();
					return;
				}
				showLoadingDialog(ActivityComment.this, "", true);
				
				CommentsManager.sendCommnet(ActivityComment.this, nid, new TextHttpResponseHandler() {
					
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String responseString) {
						// TODO Auto-generated method stub
						LogUtil.d(LogUtil.TAG, "发表评论返回信息-----》"+responseString);
								int status = ParserComments
										.parserSendComment(responseString);
								if (status == 0) {
									showToast("评论成功！");
									editText_content.setText(null);
									editText_content.clearFocus();
									loadNextComment();
								} else {
									showToast("评论失败！");
								}
								imageView_send.setEnabled(true);
								dialog.cancel();
					}
					
					@Override
					public void onFailure(int statusCode, Header[] headers,
							String responseString, Throwable throwable) {
						showToast("服务器连接异常！");
						imageView_send.setEnabled(true);
						dialog.cancel();
					}
				},CommonUtil.VERSION_CODE+"",token,imei,ccontent );
				//加载最新评论放在此处，有些不妥，因为同为子线程操作，有可能操纵发表评论的的线程还没结束，获取最新评论的子线程已经就结束了。所以
				//无法获取到最新评论，这应该考虑同步问题。
//				loadNextComment();				
				break;
			}
		}
	};
	
	private IXListViewListener listViewListener=new IXListViewListener() {
		@Override
		public void onRefresh() {			
			//加载最新数据。。。。。。。。。。。。。。。。。。。
			loadNextComment();
			// 加载完毕
			listView.stopLoadMore();
			listView.stopRefresh();
			listView.setRefreshTime(CommonUtil.getSystime());
		}
		@Override
		public void onLoadMore() {
			//加载下面更多的数据。。。。。。。。。。。。。。。。。。。
			int count = adapter.getCount();
			if(count > 1  ){ // 如果当前的ListView不存在一条item是不允许用户加载更多
				loadPreComment();
			}
			listView.stopLoadMore();
			listView.stopRefresh();
		}
	};
	/**
	 * 加载下面的XX条数据
	 */
	protected void loadPreComment() {
		Comment comment=adapter.getItem(listView.getLastVisiblePosition()-2);
		mode=NewsManager.MODE_PREVIOUS;
		if(SystemUtils.getInstance(this).isNetConn()){
			CommentsManager.loadComments(CommonUtil.VERSION_CODE+"", new MyJsonHttpResponseHandler(),nid , 1 ,comment.getCid() );
//		entity修改，先注释	CommentsManager.loadComments(mode,comment.cid,comment.cnid,new MyJsonHttpResponseHandler());
		}
	}
	/**
	 * 请求最新的评论
	 */
	protected void loadNextComment() {
		int curId = adapter.getAdapterData().size()<=0 ? 0 :adapter.getItem(0).getCid() ;
		LogUtil.d(LogUtil.TAG, "loadnextcomment--->currentId="+curId);
		mode=NewsManager.MODE_NEXT;
		if(SystemUtils.getInstance(this).isNetConn()){
			CommentsManager.loadComments(CommonUtil.VERSION_CODE+"", new MyJsonHttpResponseHandler(),nid , 2 ,curId );
//			CommentsManager.loadComments(mode, nid, curId,new MyJsonHttpResponseHandler());
		}
	}
	
	
	
	private class MyJsonHttpResponseHandler extends TextHttpResponseHandler{		
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				String responseString) {
			LogUtil.d(LogUtil.TAG, "请求评论信息返回结果为---->"+responseString);
			if(statusCode == 200){
				List<Comment> comments = ParserComments.parserComment(responseString);
				if(comments == null  || comments.size() < 1){
					return;
				}
				boolean flag = mode == NewsManager.MODE_NEXT ? true :false;
				adapter.appendData(comments, flag);
				/*if(mode==NewsManager.MODE_NEXT){
					boolean isClear=false;
					if(comments.size()>=12)
						isClear=true;
					adapter.appendDataTop(comments, true);
				}else if(mode==NewsManager.MODE_PREVIOUS){
					adapter.appendData(comments, false);
				}*/
				adapter.update();
			}
			
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				String responseString, Throwable throwable) {
			LogUtil.e(LogUtil.TAG, throwable.getMessage());
			Toast.makeText(ActivityComment.this, "服务器连接错误！", Toast.LENGTH_SHORT).show();			
		}	
	}	
	
	
}
