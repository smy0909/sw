package com.feicui.news.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.feicui.news.R;
import com.feicui.news.view.slidingmenu.SlidingMenu;

/**这个类没有关联上*/
public class ActivityMainBefor extends FragmentActivity {
	private SlidingMenu slidingMenu;//滑动菜单
	private ImageView iv_leftmenu,iv_rightmenu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_befor);
		//初始化滑动菜单
		initSlidingMenu();
		iv_leftmenu=(ImageView) findViewById(R.id.imageView_set);
		iv_rightmenu=(ImageView) findViewById(R.id.imageView_user);
		iv_leftmenu.setOnClickListener(onClickListener);
		iv_rightmenu.setOnClickListener(onClickListener);
	}
	private void initSlidingMenu() {
		slidingMenu=new SlidingMenu(this);
		slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingMenu.setMenu(R.layout.fragment_menu_left);
		slidingMenu.setSecondaryMenu(R.layout.fragment_menu_right);
	}
	
	private View.OnClickListener onClickListener=new View.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.imageView_set:
				if(slidingMenu.isMenuShowing()){
					slidingMenu.showContent();
				}else{
					slidingMenu.showMenu();
				}
				break;
			case R.id.imageView_user:
				if(slidingMenu.isMenuShowing()){
					slidingMenu.showContent();
				}else{
					slidingMenu.showSecondaryMenu();
				}
				break;
			}
		}
	};

}
