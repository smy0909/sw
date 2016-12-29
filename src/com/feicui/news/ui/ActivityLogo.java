package com.feicui.news.ui;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.feicui.news.R;
import com.feicui.news.ui.base.MyBaseActivity;
/**Logo����***/
public class ActivityLogo extends MyBaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		ImageView logo = (ImageView) findViewById(R.id.iv_logo);
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.logo);
		animation.setFillAfter(true);
		animation.setAnimationListener(new Animation.AnimationListener() {
			//��������ʱ����
			@Override
			public void onAnimationStart(Animation animation) {
			}
			//�����ظ�ʱ����
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			//��������ʱ����
			@Override
			public void onAnimationEnd(Animation animation) {
				openActivity(ActivityMain.class);
				ActivityLogo.this.finish();
			}
		});
		logo.setAnimation(animation);
	}
}
