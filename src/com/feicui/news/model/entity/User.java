package com.feicui.news.model.entity;

import java.util.List;

public class User {

	public User(String uid, String email, int integration, 
			int comnum, String portrait, List<LoginLog> loginlog) {
		this.uid = uid;
		this.integration = integration;

		this.comnum = comnum;
		this.portrait = portrait;
		this.loginlog = loginlog;
	}

	/** �û�id */
	private String uid;
	/** �û����� */
	private String email;
	/** �û����� */
	private int integration;
	/** �������� */
	private int comnum;
	/** ͷ�� */
	private String portrait;
	/** ��½��־ */
	private List<LoginLog> loginlog;

	public String getUid() {
		return uid;
	}

	public int getIntegration() {
		return integration;
	}



	public int getComnum() {
		return comnum;
	}

	public String getPortrait() {
		return portrait;
	}

	public List<LoginLog> getLoginlog() {
		return loginlog;
	}

}
