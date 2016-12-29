package com.feicui.news.ui;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.feicui.news.R;
import com.feicui.news.common.CommonUtil;
import com.feicui.news.common.SystemUtils;
import com.feicui.news.model.biz.NewsManager;
import com.feicui.news.model.biz.parser.ParserNews;
import com.feicui.news.model.dao.NewsDBManager;
import com.feicui.news.model.entity.News;
import com.feicui.news.model.entity.SubType;
import com.feicui.news.model.httpclient.TextHttpResponseHandler;
import com.feicui.news.ui.adapter.NewsAdapter;
import com.feicui.news.ui.adapter.NewsTypeAdapter;
import com.feicui.news.view.HorizontalListView;
import com.feicui.news.view.xlistview.XListView;
import com.feicui.news.view.xlistview.XListView.IXListViewListener;
/**�����б����**/
public class FragmentMain extends Fragment{
	//���view
	private View view;
	//�����б�
	private XListView listView;
	//����������
	private NewsAdapter newsAdapter;
	//�����б�
	private HorizontalListView hl_type;
	//����������
	private NewsTypeAdapter typeAdapter;
	//���ŷ����� Ĭ��Ϊ1
	private int subId = 1;
	//ģʽ  1���� 2����
    private int mode;
    //���ݿ�
    private NewsDBManager dbManager;
    //��ǰActivity
    private ActivityMain mainActivity;
    //�������
    private ImageView btn_moretype;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_newslist,container,false);
		dbManager = new NewsDBManager(getActivity());
		mainActivity = (ActivityMain)getActivity();
		hl_type = (HorizontalListView) view.findViewById(R.id.hl_type);
		listView= (XListView) view.findViewById(R.id.listview);
		btn_moretype = (ImageView) view.findViewById(R.id.iv_moretype);
		btn_moretype.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mainActivity.showFragmentType();
			}
		});
		if(hl_type != null) {
			typeAdapter = new NewsTypeAdapter(getActivity());
			hl_type.setAdapter(typeAdapter);
			hl_type.setOnItemClickListener(typeItemListener);
		}
		//�������ŷ���
		loadNewsType();
		if(listView != null){
			newsAdapter=new NewsAdapter(getActivity(), listView);
			listView.setAdapter(newsAdapter);
			listView.setPullRefreshEnable(true);
			listView.setPullLoadEnable(true);
			listView.setXListViewListener(listViewListener);			
			listView.setOnItemClickListener(newsItemListener);
		}
		//���������б�
		loadNextNews(true);
		mainActivity.showLoadingDialog(mainActivity, "������", false);
		return view;
	}
	
	private IXListViewListener listViewListener=new IXListViewListener() {
		@Override
		public void onRefresh() {			
			//�������ݡ�������������������������������������
			loadNextNews(false);
			// �������
			listView.stopLoadMore();
			listView.stopRefresh();
			listView.setRefreshTime(CommonUtil.getSystime());
		}
		@Override
		public void onLoadMore() {
			//�������ݡ�������������������������������������
			loadPreNews();
			listView.stopLoadMore();
			listView.stopRefresh();
		}
	};
	
	/**
	 * ���൥�����¼�
	 */
	private OnItemClickListener typeItemListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			SubType subType = (SubType) parent.getItemAtPosition(position);
			subId = subType.getSubid();
			typeAdapter.setSelectedPosition(position);
			typeAdapter.update();
			loadNextNews(true);
			mainActivity.showLoadingDialog(mainActivity, "������", false);
		}
	};
	
	/**
	 * ���ŵ������¼�
	 */
	private OnItemClickListener newsItemListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			// ����ʾ��ǰѡ�е�����
			News news = (News) parent.getItemAtPosition(position);
			Intent intent=new Intent(getActivity(), ActivityShow.class);
			intent.putExtra("newsitem", news);
		    getActivity().startActivity(intent);
		}
	};
	
	/**
	 * ������������
	 */
	protected void loadNewsType() {
		if(dbManager.queryNewsType().size() == 0) {
			if(SystemUtils.getInstance(getActivity()).isNetConn()) {
				NewsManager.loadNewsType(getActivity(), new NewsTypeResponseHandler());
			}
		} else {
			List<SubType> types = dbManager.queryNewsType();
			typeAdapter.appendData(types, true);
			typeAdapter.update();
		}
	}
	
	/**
	 * ������ǰ����������
	 */
	protected void loadPreNews() {
		if(listView.getCount()-2<=0) return;
		int nId =newsAdapter.getItem(listView.getLastVisiblePosition()-2).getNid();
		mode=NewsManager.MODE_PREVIOUS;
		if(SystemUtils.getInstance(getActivity()).isNetConn()){
			NewsManager.loadNewsFromServer(mode, subId, nId, new NewsResponseHandler());
		}else{
			NewsManager.loadNewsFromsLocal(mode, nId ,new MyLocalResponseHandler());
		}
	}
	
	/**
	 * �����µ�����
	 */
	protected void loadNextNews(boolean isNewType) {
		int nId = 0;
		if(!isNewType) {
			if(newsAdapter.getAdapterData().size() > 0){
				nId=newsAdapter.getItem(0).getNid();
			}
		}
		mode=NewsManager.MODE_NEXT;
		if(SystemUtils.getInstance(getActivity()).isNetConn()){
			NewsManager.loadNewsFromServer(mode, subId, nId, new NewsResponseHandler());
		}else{
			NewsManager.loadNewsFromsLocal(mode,nId,new MyLocalResponseHandler());
		}
	}
	
	/**
	 * 
	 * @author hj
	 * �������ͻص��ӿ�ʵ����
	 *
	 */
	private class NewsTypeResponseHandler extends TextHttpResponseHandler{
		@Override
		public void onSuccess(int statusCode, Header[] headers, String responseString) {
			if (statusCode == 200) {
				List<SubType> types = ParserNews.parserTypeList(responseString);
				dbManager.saveNewsType(types);
				typeAdapter.appendData(types, true);
				typeAdapter.update();
			}
		}
		@Override
		public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
			mainActivity.showToast("�����������쳣");
		}	
	}
	
	/**
	 * 
	 * @author hj
	 * �����б�ص��ӿ�ʵ����
	 *
	 */
	private class NewsResponseHandler extends TextHttpResponseHandler{
		@Override
		public void onSuccess(int statusCode, Header[] headers, String responseString) {
			if (statusCode == 200) {
				List<News> data = ParserNews.parserNewsList(responseString);
				boolean isClear = mode == NewsManager.MODE_NEXT ? true:false;
				newsAdapter.appendData((ArrayList<News>)data, isClear);
				mainActivity.cancelDialog();
				newsAdapter.update();
			}
		}
		@Override
		public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
			mainActivity.cancelDialog();
			mainActivity.showToast("�����������쳣");
		}	
	}
	
	public class MyLocalResponseHandler implements NewsManager.LocalResponseHandler{
		public void update(ArrayList<News> data,boolean isClearOld) {
			newsAdapter.appendData(data, isClearOld);
			newsAdapter.update();
			if(data.size()<=0){
				Toast.makeText(getActivity(), "���������������ӣ�", Toast.LENGTH_SHORT).show();
			}
		}		
	}

}
	
	
