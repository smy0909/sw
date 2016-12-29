package com.feicui.news.model.entity;

import java.io.Serializable;

public class News implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ���ͱ�ʶ  1:�б����� 2����ͼ����
	 */
	private int type;
	/**
	 * ����id
	 */
	private int nid;
	/**
	 * ʱ���
	 */
	private String stamp;
	/**
	 * ͼ��
	 */
	private String icon;
	/**
	 * ���ű���
	 */
	private String title;
	/**
	 * ����ժҪ
	 */
	private String summary;
	/**
	 * ��������
	 */
	private String link;
	
	/**
	 * 
	 * @param type
	 * @param nid
	 * @param stamp
	 * @param icon
	 * @param title
	 * @param summary
	 * @param link
	 */
	public News(int type, int nid, String stamp, String icon, String title,
			String summary, String link) {
		super();
		this.type = type;
		this.nid = nid;
		this.stamp = stamp;
		this.icon = icon;
		this.title = title;
		this.summary = summary;
		this.link = link;
	}

	public int getType() {
		return type;
	}

	public int getNid() {
		return nid;
	}

	public String getStamp() {
		return stamp;
	}

	public String getIcon() {
		return icon;
	}

	public String getTitle() {
		return title;
	}

	public String getSummary() {
		return summary;
	}

	public String getLink() {
		return link;
	}
	
	
}
