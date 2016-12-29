package com.feicui.news.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.feicui.news.R;
import com.feicui.news.ui.adapter.LeadImgAdapter;
import com.feicui.news.ui.base.MyBaseActivity;
/**��������***/
public class ActivityLead extends MyBaseActivity {
	private ViewPager viewPager;
	private ImageView[] points=new ImageView[4];
	private LeadImgAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lead);
		//xml�ļ��洢  ƫ�ã������� ��Ч Ƥ��
		SharedPreferences preferences=getSharedPreferences("runconfig", MODE_PRIVATE);
		//���ļ��л�ȡ�洢�����ݣ�Ĭ��Ϊtrue
		boolean isFirst = preferences.getBoolean("isFirstRun", true);
		//������ǵ�һ�δ򿪣���ֱ����ת��Logo����
		if(!isFirst){
			openActivity(ActivityLogo.class);
			finish();
			return;
		}
		points[0]=(ImageView) findViewById(R.id.iv_p1);
		points[1]=(ImageView) findViewById(R.id.iv_p2);
		points[2]=(ImageView) findViewById(R.id.iv_p3);
		points[3]=(ImageView) findViewById(R.id.iv_p4);
		setPoint(0);
		//��ʼ���ؼ�
		viewPager=(ViewPager) findViewById(R.id.viewpager);
		//����ÿһ������������ʽ 
		List<View> viewList=new ArrayList<View>();
		viewList.add(getLayoutInflater().inflate(R.layout.lead_1, null));
		viewList.add(getLayoutInflater().inflate(R.layout.lead_2, null));
		viewList.add(getLayoutInflater().inflate(R.layout.lead_3, null));
		viewList.add(getLayoutInflater().inflate(R.layout.lead_4, null));
		//��ʼ��������
		adapter=new LeadImgAdapter(viewList);
		//����������
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(listener);
	}

	private void setPoint(int index) {
		for (int i = 0; i < points.length; i++) {
			if(i==index){
				points[i].setAlpha(255);
			}else{
				points[i].setAlpha(100);
			}
		}
	}
	
	private ViewPager.OnPageChangeListener listener=new ViewPager.OnPageChangeListener() {
		
		/**�������л������*/
		@Override
		public void onPageSelected(int arg0) {
			setPoint(arg0);
			if(arg0>=3){
				openActivity(ActivityLogo.class);
				finish();				
				SharedPreferences preferences=getSharedPreferences("runconfig", MODE_PRIVATE);
				Editor editor=preferences.edit();
				editor.putBoolean("isFirstRun", false);
				editor.commit();
			}
		}
		/**�����л�ʱ����*/
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {		}
		/**����״̬�仯ʱ����*/
		@Override
		public void onPageScrollStateChanged(int arg0) {  }
	};
}
