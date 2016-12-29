package com.feicui.news.ui;

import org.apache.http.Header;

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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**��½����**/
public class FragmentLogin extends Fragment{
	private View view;
	private EditText editTextNickname,editTextPwd;
	private Button but_register,btn_login,btn_forgetPass;
	private UserManager userManager;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_login,container,false);
		editTextNickname=(EditText) view.findViewById(R.id.editText_nickname);
		editTextPwd=(EditText) view.findViewById(R.id.editText_pwd);
		but_register=(Button) view.findViewById(R.id.button_register);
		btn_forgetPass=(Button) view.findViewById(R.id.button_forgetPass);
		btn_login=(Button) view.findViewById(R.id.button_login);
		
		but_register.setOnClickListener(clickListener);
		btn_forgetPass.setOnClickListener(clickListener);
		btn_login.setOnClickListener(clickListener);
		return view;
	}
	
	private View.OnClickListener clickListener=new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.button_login:
				String name=editTextNickname.getText().toString().trim();
				String pwd=editTextPwd.getText().toString().trim();
				if(TextUtils.isEmpty(name)){
					Toast.makeText(getActivity(), "�������û���", 0).show();
					return;
				}
				if(TextUtils.isEmpty(pwd)){
					Toast.makeText(getActivity(), "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
					return ;
				}
				
				if(pwd.length() < 6 || pwd.length()  > 16 ){
					Toast.makeText(getActivity(), "���볤�ȴ���", Toast.LENGTH_SHORT).show();
					return ;
				}
				
				if(userManager==null)
					userManager=UserManager.getInstance(getActivity());
//				args �����������£� ver : �汾 uid : �û��ǳ�    pwd : ����    imei: �ֻ�IMEI��   device :��¼�豸 0Ϊ�ƶ���  ��1 Ϊpc�� 
				userManager.login(loginResponseHandler , CommonUtil.VERSION_CODE+"" ,name,pwd, "0");
				break;
			case R.id.button_register:
				((ActivityMain)getActivity()).showFragmentRegister();
				break;
			case R.id.button_forgetPass:
				((ActivityMain)getActivity()).showFragmentForgetPass();
				break;
			}
			
		}
	};
	
private ResponseHandlerInterface loginResponseHandler=new TextHttpResponseHandler() {
		
		@Override
		public void onSuccess(int statusCode, Header[] headers,String responseString) {
			
			if(statusCode == 200){
				LogUtil.d(LogUtil.TAG, "ִ�е�¼������������Ϣ��"+responseString);
				BaseEntity<Register> register = ParserUser.parserRegister(responseString);
				int status = Integer.parseInt(register.getStatus());
				String result = "";
				if(status == 0 ){
					result = "��¼�ɹ�";
					SharedPreferencesUtils.saveRegister(getActivity(), register);
					startActivity(new Intent(getActivity(),ActivityUser.class));
					//���Ӷ���=======
					getActivity().overridePendingTransition(R.anim.anim_activity_right_in, R.anim.anim_activity_bottom_out);
				}else if(status  == -3){
					result = "�û������������";
				}else{
					result = "��¼ʧ��";
				}
				Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
			}
			
			//TODO ��½����
//			if(statusCode==200){		
//				if("error".equals(responseString)){
//					Toast.makeText(getActivity(), "�û������������", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				User user=NewsParser.parserUser(getActivity(), responseString);
//				if(user!=null){
//					//2.�û���Ϣ���浽����
//					SharedPreferences sharedPreferences=getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
//					Editor editor=sharedPreferences.edit();
//					editor.putBoolean("islogin", true);
//					editor.putString("uname", user.uname);
//					editor.putString("upwd", user.upwd);
//					editor.putString("uemail", user.uemail);
//					editor.putInt("uid", user.uid);
//					editor.putString("utype", user.utype);
//					editor.putString("uip", user.uip);
//					editor.putString("uphoto", user.uphoto);
//					editor.commit();
//					Toast.makeText(getActivity(), "��¼�ɹ���", Toast.LENGTH_SHORT).show();
//					((ActivityMain)getActivity()).changeFragmentUser();
//					((ActivityMain)getActivity()).showFragmentMain();
//				}
//			}			
		}
		
		@Override
		public void onFailure(int statusCode, Header[] headers,
				String responseString, Throwable throwable) {
			Toast.makeText(getActivity(), "��¼�쳣��", Toast.LENGTH_SHORT).show();
		}
	};
}

