package com.feicui.news.ui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import com.feicui.news.R;
import com.feicui.news.common.CommonUtil;
import com.feicui.news.common.LogUtil;
import com.feicui.news.common.SharedPreferencesUtils;
import com.feicui.news.common.SystemUtils;
import com.feicui.news.model.biz.UserManager;
import com.feicui.news.model.biz.parser.ParserUser;
import com.feicui.news.model.entity.BaseEntity;
import com.feicui.news.model.entity.Register;
import com.feicui.news.model.httpclient.ResponseHandlerInterface;
import com.feicui.news.model.httpclient.TextHttpResponseHandler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
/**ע�����**/
public class FragmentRegister extends Fragment{
	private View view;
	private EditText editTextEmail,editTextName,editTextPwd;
	private Button but_register;
	private CheckBox checkBox;
	private UserManager userManager;
	private String email,name,pwd;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_register,container,false);
		editTextEmail=(EditText) view.findViewById(R.id.editText_email);
		editTextName=(EditText) view.findViewById(R.id.editText_name);
		editTextPwd=(EditText) view.findViewById(R.id.editText_pwd);
		but_register=(Button) view.findViewById(R.id.button_register);
		checkBox=(CheckBox) view.findViewById(R.id.checkBox1);
		
		but_register.setOnClickListener(clickListener);
		
		return view;
	}
	
	private View.OnClickListener clickListener=new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(!checkBox.isChecked()){
				Toast.makeText(getActivity(), "û��ͬ��Э�����", Toast.LENGTH_SHORT).show();
				return;
			}
			String email=editTextEmail.getText().toString();
			String name=editTextName.getText().toString().trim();
			String pwd=editTextPwd.getText().toString().trim();
			if(!CommonUtil.verifyEmail(email)){
				Toast.makeText(getActivity(), "����������ȷ�������ʽ", Toast.LENGTH_SHORT).show();
				return;
			}
			if(TextUtils.isEmpty(name) ){
				Toast.makeText(getActivity(), "�������û��ǳ�", Toast.LENGTH_SHORT).show();
				return ;
			}
			if(pwd.length() < 6 || pwd.length()  > 16 ){
				Toast.makeText(getActivity(), "���볤�ȴ���", Toast.LENGTH_SHORT).show();
				return ;
			}
			if(!CommonUtil.verifyPassword(pwd)){
				Toast.makeText(getActivity(), "������6-22λ���ֺ���ĸ��ϵ�����", Toast.LENGTH_SHORT).show();
				return;
			}
			if(userManager==null)
				userManager=UserManager.getInstance(getActivity());
//			ver : �汾 uid : �û��ǳ� pwd : ���� email : ���� device : �ֻ�IMEI��
			userManager.register(new MyResponseHandlerInterface(),
						CommonUtil.VERSION_CODE+"",name,pwd ,email);
		}
	};
	
	private class MyResponseHandlerInterface extends TextHttpResponseHandler{
		@Override
		public void onSuccess(int statusCode, Header[] headers,String responseString) {
			LogUtil.d(LogUtil.TAG, "onSuccess--ִ��ע�᷵����Ϣ��"+responseString);
			if(statusCode==200){	
				
				BaseEntity<Register> register = ParserUser.parserRegister(responseString);
				String result = null;
				String explain =null;
				Register data = register.getData();
				int status = Integer.parseInt(register.getStatus());
				if(status == 0 ){

					result=data.getResult().trim();
					explain =data.getExplain();
//					result ="ע��ɹ�";
					if(result.equals("0")){
						//�����û���Ϣ
						SharedPreferencesUtils.saveRegister(getActivity(), register);
						startActivity(new Intent(getActivity(),ActivityUser.class));
						//���Ӷ���=======
						getActivity().overridePendingTransition(R.anim.anim_activity_right_in, R.anim.anim_activity_bottom_out);
						//���½���
					((ActivityMain)getActivity()).changeFragmentUser();
					}
					/*	((ActivityMain)getActivity()).showFragmentMain();*/
				Toast.makeText(getActivity(), explain, Toast.LENGTH_SHORT).show();
			}
			}
		}
		
		@Override
		public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
			LogUtil.d(LogUtil.TAG, "onFailure --ִ��ע�᷵����Ϣ��"+responseString);
			Toast.makeText(getActivity(), "ע��ʧ�ܣ�", Toast.LENGTH_SHORT).show();
		}
	};
}
