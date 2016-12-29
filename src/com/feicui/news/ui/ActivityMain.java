package com.feicui.news.ui;

import com.feicui.news.R;
import com.feicui.news.ui.base.MyBaseActivity;
import com.feicui.news.view.slidingmenu.SlidingMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**������**/
public class ActivityMain extends MyBaseActivity {
	private Fragment fragmentMenu, fragmentMenuRight;
	private Fragment fragmentType, fragmentMain, fragmentLogin,fragmentRegister,fragmentFavorite,fragmentForgetPass;
	public static SlidingMenu slidingMenu;
	private ImageView iv_set, iv_user;
	private TextView textView_title;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
		textView_title=(TextView) findViewById(R.id.textView1);
		iv_set = (ImageView) findViewById(R.id.imageView_set);
		iv_user = (ImageView) findViewById(R.id.imageView_user);
		iv_set.setOnClickListener(onClickListener);
		iv_user.setOnClickListener(onClickListener);
		initSlidingMenu();
		showFragmentMain();
	}

	private View.OnClickListener onClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.imageView_set:
				if (slidingMenu != null && slidingMenu.isMenuShowing()) {
					slidingMenu.showContent();
				} else if (slidingMenu != null) {
					slidingMenu.showMenu();
				}
				break;
			case R.id.imageView_user:
				if (slidingMenu != null && slidingMenu.isMenuShowing()) {
					slidingMenu.showContent();
				} else if (slidingMenu != null) {
					slidingMenu.showSecondaryMenu();
				}
				break;
			}
		}
	};
	/**��ʼ���໬�˵�**/
	public void initSlidingMenu() {
		fragmentMenu = new FragmentMenu();
		fragmentMenuRight = new FragmentMenuRight();
		
		slidingMenu = new SlidingMenu(this);
		slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		
		slidingMenu.setMenu(R.layout.layout_menu);
		slidingMenu.setSecondaryMenu(R.layout.layout_menu_right);
		
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.layout_menu, fragmentMenu).commit();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.layout_menu_right, fragmentMenuRight).commit();
	}

	
	@Override
	public void onBackPressed() {
		if (slidingMenu.isMenuShowing()) {
			slidingMenu.showContent();
		} else {
			exitTwice();
		}
	}
	
	//�����˳�
	private boolean isFirstExit=true;
	private void exitTwice(){
		if(isFirstExit){
			Toast.makeText(this, "�ٰ�һ���˳���", Toast.LENGTH_SHORT).show();
			isFirstExit=false;
			new Thread(){
				public void run() {
					try {
						Thread.sleep(3000);
						isFirstExit=true;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				};
			}.start(); 
		}else{
			finish();
		}
	}

	/**
	 * ��ʾ������ʾ���Ÿ������Fragment��
	 */
	public void showFragmentType() {
		setTitle("����");
		slidingMenu.showContent();
		if (fragmentType == null)
			fragmentType = new FragmentType();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.layout_content, fragmentType).commit();
	}
	/**
	 * ��ʾ:����ʾ�����б��Fragment��
	 */
	public void showFragmentMain() {
		setTitle("�YӍ");
		slidingMenu.showContent();
		if (fragmentMain == null)
			fragmentMain = new FragmentMain();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.layout_content, fragmentMain).commit();
	}
	/**
	 * ��ʾ:����¼��Fragment��
	 */
	public void showFragmentLogin() {
		setTitle("�û���¼");
		slidingMenu.showContent();
		if (fragmentLogin == null)
			fragmentLogin = new FragmentLogin();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.layout_content, fragmentLogin).commit();
	}

	/**
	 * ��ʾ:��ע���Fragment��
	 */
	public void showFragmentRegister() {
		setTitle("�û�ע��");
		if (fragmentRegister == null)
			fragmentRegister = new FragmentRegister();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.layout_content, fragmentRegister).commit();
	}
	/**
	 * ��ʾ:�����������Fragment��
	 */
	public void showFragmentForgetPass() {
		setTitle("��������");
		if (fragmentForgetPass == null)
			fragmentForgetPass = new FragmentForgetPass();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.layout_content, fragmentForgetPass).commit();
	}
	/**
	 * ��ʾ:����ʾ�ղ������б��Fragment��
	 */
	public void showFragmentFavorite() {
		setTitle("�ղ�����");
		slidingMenu.showContent();
		if(fragmentFavorite==null)
			fragmentFavorite=new FragmentFavorite();
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.layout_content, fragmentFavorite).commit();
	}

	/**
	 * �Ҳ��Ƿ��¼���л�
	 */
	public void changeFragmentUser() {
			((FragmentMenuRight) fragmentMenuRight).changeView();
	}
	
	/**
	 * ������ǰ�����Title
	 */
	private void setTitle(String title){
		textView_title.setText(title);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		System.out.println("ActivityMain");
		return false;
	}
	
}
