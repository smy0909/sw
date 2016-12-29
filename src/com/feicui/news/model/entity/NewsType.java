package com.feicui.news.model.entity;

import java.util.List;

public class NewsType {

	/**
	 * �����
	 */
	private int gid;
	/**
	 * ������
	 */
	private String group;
	/**
	 * �Ӷ���
	 */
	private List<SubType> subgrp;
	

	public NewsType(int gid, String group) {
		this.gid = gid;
		this.group = group;
	}
	
	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public List<SubType> getSubgrp() {
		return subgrp;
	}

	public void setSubgrp(List<SubType> subgrp) {
		this.subgrp = subgrp;
	}
	
}
