package com.feicui.news.model.entity;

public class LoginLog {

	/** ��½��ַ */
	private String address;
	/**
	 * ��½ʱ���豸 true:�ֻ���½ false:��ҳ��½
	 */
	private int device;
	/** ��½ʱ�� */
	private String time;

	public LoginLog(String address, int device, String time) {
		this.address = address;
		this.device = device;
		this.time = time;
	}

	public String getAddress() {
		return address;
	}

	public int getDevice() {
		return device;
	}

	public String getTime() {
		return time;
	}

}
