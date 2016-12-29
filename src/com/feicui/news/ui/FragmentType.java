package com.feicui.news.ui;

import com.feicui.news.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 更多分类页面
 * @author hj
 *
 */
public class FragmentType extends Fragment{
	
	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_type, container, false);
		return view;
	}
}
