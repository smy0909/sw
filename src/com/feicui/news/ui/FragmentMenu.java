package com.feicui.news.ui;

import com.feicui.news.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
/**×ó±ß²àÀ­½çÃæ**/
public class FragmentMenu extends Fragment{
	private View view;
	private RelativeLayout[] rls=new RelativeLayout[5];
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_menu_left, container, false);
		rls[0]=(RelativeLayout) view.findViewById(R.id.rl_news);
		rls[1]=(RelativeLayout) view.findViewById(R.id.rl_reading);
		rls[2]=(RelativeLayout) view.findViewById(R.id.rl_local);
		rls[3]=(RelativeLayout) view.findViewById(R.id.rl_commnet);
		rls[4]=(RelativeLayout) view.findViewById(R.id.rl_photo);
		for(int i=0;i<rls.length;i++){
			rls[i].setOnClickListener(onClickListener);
		}
		return view;
	}
	
	private OnClickListener onClickListener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			for (int i = 0; i < rls.length; i++) {
				rls[i].setBackgroundColor(0);
			}
			switch (v.getId()) {
			case R.id.rl_news:
				rls[0].setBackgroundColor(0x33c85555);
				((ActivityMain)getActivity()).showFragmentMain();
				break;
			case R.id.rl_reading:
				rls[1].setBackgroundColor(0x33c85555);
				((ActivityMain)getActivity()).showFragmentFavorite();
				break;
			case R.id.rl_local:
				rls[2].setBackgroundColor(0x33c85555);
				break;
			case R.id.rl_commnet:
				rls[3].setBackgroundColor(0x33c85555);
				break;
			case R.id.rl_photo:
				rls[4].setBackgroundColor(0x33c85555);
				break;
			}
		}
	};
}
