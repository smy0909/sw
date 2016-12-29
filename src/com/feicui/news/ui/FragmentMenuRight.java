package com.feicui.news.ui;

import org.apache.http.Header;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.feicui.news.R;
import com.feicui.news.common.CommonUtil;
import com.feicui.news.common.LoadImage;
import com.feicui.news.common.LoadImage.ImageLoadListener;
import com.feicui.news.common.LogUtil;
import com.feicui.news.common.SharedPreferencesUtils;
import com.feicui.news.common.SystemUtils;
import com.feicui.news.model.biz.UpdateManager;
import com.feicui.news.model.biz.parser.ParserVersion;
import com.feicui.news.model.entity.Version;
import com.feicui.news.model.httpclient.TextHttpResponseHandler;
import com.feicui.news.onkeyshare.OnekeyShare;
import com.feicui.news.receiver.DownloadCompleteReceiver;
/**�ұ߲�������**/
public class FragmentMenuRight extends Fragment implements ImageLoadListener{
	private View view;
	private RelativeLayout relativelayout_unlogin;
	private RelativeLayout relativeLayout_logined;
	private boolean islogin;
	private SharedPreferences sharedPreferences;
	private ImageView imageView1 ,iv_pic;
	private TextView textView1 , updateTv;
	private String [] str;
	DownloadCompleteReceiver receiver ;
	/**
	 * ����΢��
	 */
	private ImageView iv_friend;
	/**
	 * ����QQ
	 */
	private ImageView iv_qq;
	/**
	 * ��������Ȧ
	 */
	private ImageView iv_friends;
	/**
	 * ����΢��
	 */
	private ImageView iv_weibo;
	
	/**
	 * ����λ�ù涨
	 */
	public static final int WEBCHAT = 1,QQ = 2,WEBCHATMOMENTS = 3,SINA = 4;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_menu_right, container, false);
		sharedPreferences=getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		islogin=sharedPreferences.getBoolean("islogin", false);
		relativelayout_unlogin=(RelativeLayout) view.findViewById(R.id.relativelayout_unlogin);
		relativeLayout_logined=(RelativeLayout) view.findViewById(R.id.relativelayout_logined);
		imageView1=(ImageView) view.findViewById(R.id.imageView1);
		textView1=(TextView) view.findViewById(R.id.textView1);
		updateTv = (TextView) view.findViewById(R.id.update_version);
		//��ʼ�������ܿؼ�
		iv_friend = (ImageView) view.findViewById(R.id.fun_friend);
		iv_qq = (ImageView) view.findViewById(R.id.fun_qq);
		iv_friends = (ImageView) view.findViewById(R.id.fun_friends);
		iv_weibo = (ImageView) view.findViewById(R.id.fun_weibo);
		
		iv_friend.setOnClickListener(l);
		iv_qq.setOnClickListener(l);
		iv_friends.setOnClickListener(l);
		iv_weibo.setOnClickListener(l);
		
		imageView1.setOnClickListener(l);
		textView1.setOnClickListener(l);		
		
		relativeLayout_logined.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent  intent=new Intent(getActivity(),ActivityUser.class);
				startActivity(intent);
			}
		});
		receiver = new DownloadCompleteReceiver();//����������Ͻ�����
		
		//�汾geng
		updateTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				UpdateManager.judgeUpdate(new TextHttpResponseHandler() {
					
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String responseString) {
						//��������json����
						Version version = ParserVersion.parserJson(responseString);
						//�жϱ��ذ汾��������汾
						if(CommonUtil.getVersionCode(FragmentMenuRight.this.getActivity()) < Integer.parseInt(version.getVersion())){
							//ִ����������
							Toast.makeText(getActivity(), "�����������°汾.", 0).show();
							UpdateManager.downLoad(getActivity(), version.getLink());
						}else{
							Toast.makeText(getActivity(), "��ǰ�������°汾", 0).show();
						}
						
						LogUtil.d(LogUtil.TAG, "ִ�а汾���·������ݣ�"+responseString);
					}
					
					@Override
					public void onFailure(int statusCode, Header[] headers,
							String responseString, Throwable throwable) {
						Toast.makeText(getActivity(), "����ʧ��", 0).show();
					}
				}, SystemUtils.getIMEI(getActivity()),"package-name",CommonUtil.VERSION_CODE+"");
				
				/*
				 * 
				 * UpdateManager manager = new UpdateManager();
				String url = "http://20.14.3.61:8080/Images/sogou_pinyin_68e.3984320491.exe";
				manager.downLoad(getActivity(), url);*/
			}
		});
		
		return view;
	}
	
	private View.OnClickListener l=new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			//�жϵ�¼
			if(v.getId()==R.id.imageView1 || v.getId()==R.id.textView1){
				((ActivityMain)getActivity()).showFragmentLogin();
			}
			
			//�жϷ���
			switch (v.getId()) {
			case R.id.fun_friend://����΢��
				showShare(WEBCHAT);
				break;
			case R.id.fun_qq:
				showShare(QQ);
				break;
			case R.id.fun_friends:
				showShare(WEBCHATMOMENTS);
				break;
			case R.id.fun_weibo:
				showShare(SINA);
				break;
			}
		}
	};
	/**��ʼ���û���Ϣ**/
	private void initUserInfo() {
		TextView tv_name=(TextView) view.findViewById(R.id.textView_name);
		iv_pic=(ImageView) view.findViewById(R.id.imageView_photo);
		tv_name.setText(str[0]);
		String iconPath = SharedPreferencesUtils.getUserLocalIcon(getActivity());
		if(!TextUtils.isEmpty(iconPath)){
			LogUtil.d(LogUtil.TAG, "menu right ���ش����û������ϴ���ͷ��");
			Bitmap bitmap = BitmapFactory.decodeFile(iconPath);
			iv_pic.setImageBitmap(bitmap);
			return ;
		}
		if(!TextUtils.isEmpty(str[1])){
			LogUtil.d(LogUtil.TAG, "menu right ���ش����û������������ͷ��");
			LoadImage loadImage = new LoadImage(getActivity(), this);
			iv_pic.setImageBitmap(loadImage.geBitmap(str[1]));
		}
	}
	/**�����û���Ϣ�Ƿ���ڱ��������õ�ǰ��ͼ**/
	public void changeView(){
		islogin=sharedPreferences.getBoolean("islogin", false);
		if(islogin){
			relativeLayout_logined.setVisibility(View.VISIBLE);
			relativelayout_unlogin.setVisibility(View.GONE);
			initUserInfo();
		}else{
			relativelayout_unlogin.setVisibility(View.VISIBLE);
			relativeLayout_logined.setVisibility(View.GONE);
		}
	}
	
	
	
	@Override
	public void onResume() {
		super.onResume();
		LogUtil.d(LogUtil.TAG, "menu right onResume...");
		str = SharedPreferencesUtils.getUserNameAndPhoto(getActivity());
		if(!TextUtils.isEmpty(str[0])){
			relativeLayout_logined.setVisibility(View.VISIBLE);
			relativelayout_unlogin.setVisibility(View.GONE);
			initUserInfo();
		}else{
			relativelayout_unlogin.setVisibility(View.VISIBLE);
			relativeLayout_logined.setVisibility(View.GONE);
		}
		getActivity().registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));  
	}
	@Override
	public void onStop() {
		super.onStop();
		getActivity().unregisterReceiver(receiver);
		
	}
	@Override
	public void imageLoadOk(Bitmap bitmap, String url) {
		if(bitmap != null){ //��������ͼƬ�ص�
			iv_pic.setImageBitmap(bitmap);
		}
	}
	
	/**
	 * ȫ�����������ʾ
	 * 
	 * @param �����λ��
	 */
	private void showShare(int platforms) {
        ShareSDK.initSDK(getActivity());
        OnekeyShare oks = new OnekeyShare();
        //�ر�sso��Ȩ
        oks.disableSSOWhenAuthorize();
        
        // title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
        oks.setTitle(getString(R.string.share));
        // titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
        oks.setTitleUrl("http://sharesdk.cn");
        // text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
        oks.setText("Tower���ſͻ���");
        // imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
        //oks.setImagePath("/sdcard/test.jpg");
        // url����΢�ţ��������Ѻ�����Ȧ����ʹ��
        oks.setUrl("http://sharesdk.cn");
        // comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
        oks.setComment("Tower���ſͻ�����һ��õ��������");
        
        switch (platforms) {
		case WEBCHAT:
			oks.setPlatform(Wechat.NAME);
			break;
		case WEBCHATMOMENTS:
			oks.setPlatform(WechatMoments.NAME);
			break;
		case QQ:
			oks.setPlatform(cn.sharesdk.tencent.qq.QQ.NAME);
			break;
		case SINA:
			oks.setPlatform(SinaWeibo.NAME);
			break;
		}
        // ��������GUI
        oks.show(getActivity());
   }
	
}
