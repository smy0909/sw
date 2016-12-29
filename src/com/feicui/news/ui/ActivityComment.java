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
/**���۽���***/
public class ActivityComment extends MyBaseActivity {
	/**����id*/
	private int nid;
	/**�����б�*/
	private XListView listView;
	/**�����б�������*/
	private CommentsAdapter adapter;
	/***/
    private  int mode;	
    /**�������۰�ť*/
    private ImageView imageView_send;
    /**���ذ�ť*/
    private ImageView imageView_back;
    /**���۱༭��*/
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
			case R.id.imageView_back://���ذ�ť
				finish();
				break;
			case R.id.imageview://�������۰�ť
				String ccontent=editText_content.getText().toString();
				if(ccontent==null || ccontent.equals("")){
					Toast.makeText(ActivityComment.this, "Ҫ��д��������Ŷ���ף�", Toast.LENGTH_SHORT).show();
					return;
				}
				imageView_send.setEnabled(false);
				String imei = SystemUtils.getInstance(ActivityComment.this).getIMEI();
				String token = SharedPreferencesUtils.getToken(ActivityComment.this);
				if(TextUtils.isEmpty(token)){
					Toast.makeText(ActivityComment.this, "�Բ�������û�е�¼.", 0).show();
					return;
				}
				showLoadingDialog(ActivityComment.this, "", true);
				
				CommentsManager.sendCommnet(ActivityComment.this, nid, new TextHttpResponseHandler() {
					
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String responseString) {
						// TODO Auto-generated method stub
						LogUtil.d(LogUtil.TAG, "�������۷�����Ϣ-----��"+responseString);
								int status = ParserComments
										.parserSendComment(responseString);
								if (status == 0) {
									showToast("���۳ɹ���");
									editText_content.setText(null);
									editText_content.clearFocus();
									loadNextComment();
								} else {
									showToast("����ʧ�ܣ�");
								}
								imageView_send.setEnabled(true);
								dialog.cancel();
					}
					
					@Override
					public void onFailure(int statusCode, Header[] headers,
							String responseString, Throwable throwable) {
						showToast("�����������쳣��");
						imageView_send.setEnabled(true);
						dialog.cancel();
					}
				},CommonUtil.VERSION_CODE+"",token,imei,ccontent );
				//�����������۷��ڴ˴�����Щ���ף���ΪͬΪ���̲߳������п��ܲ��ݷ������۵ĵ��̻߳�û��������ȡ�������۵����߳��Ѿ��ͽ����ˡ�����
				//�޷���ȡ���������ۣ���Ӧ�ÿ���ͬ�����⡣
//				loadNextComment();				
				break;
			}
		}
	};
	
	private IXListViewListener listViewListener=new IXListViewListener() {
		@Override
		public void onRefresh() {			
			//�����������ݡ�������������������������������������
			loadNextComment();
			// �������
			listView.stopLoadMore();
			listView.stopRefresh();
			listView.setRefreshTime(CommonUtil.getSystime());
		}
		@Override
		public void onLoadMore() {
			//���������������ݡ�������������������������������������
			int count = adapter.getCount();
			if(count > 1  ){ // �����ǰ��ListView������һ��item�ǲ������û����ظ���
				loadPreComment();
			}
			listView.stopLoadMore();
			listView.stopRefresh();
		}
	};
	/**
	 * ���������XX������
	 */
	protected void loadPreComment() {
		Comment comment=adapter.getItem(listView.getLastVisiblePosition()-2);
		mode=NewsManager.MODE_PREVIOUS;
		if(SystemUtils.getInstance(this).isNetConn()){
			CommentsManager.loadComments(CommonUtil.VERSION_CODE+"", new MyJsonHttpResponseHandler(),nid , 1 ,comment.getCid() );
//		entity�޸ģ���ע��	CommentsManager.loadComments(mode,comment.cid,comment.cnid,new MyJsonHttpResponseHandler());
		}
	}
	/**
	 * �������µ�����
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
			LogUtil.d(LogUtil.TAG, "����������Ϣ���ؽ��Ϊ---->"+responseString);
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
			Toast.makeText(ActivityComment.this, "���������Ӵ���", Toast.LENGTH_SHORT).show();			
		}	
	}	
	
	
}
