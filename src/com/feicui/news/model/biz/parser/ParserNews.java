package com.feicui.news.model.biz.parser;

import java.util.List;

import com.feicui.news.model.entity.BaseEntity;
import com.feicui.news.model.entity.News;
import com.feicui.news.model.entity.NewsType;
import com.feicui.news.model.entity.SubType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ParserNews {

	/**
	 * gson�������ŷ���
	 * @param json
	 * @return list
	 */
	public static List<SubType> parserTypeList(String json){
		Gson gson = new Gson();
		BaseEntity<List<NewsType>> typeEntity = gson.fromJson(json, new TypeToken<BaseEntity<List<NewsType>>>() {}.getType());
		for(NewsType type: typeEntity.getData()) {
			System.out.println(type.getSubgrp());
		}
		//��һ��û�з����б�,���ҷ���Ĭ����������ӷ���
 		return typeEntity.getData().get(0).getSubgrp();
	}
	
	/**
	 * gson���������б�
	 * @param json
	 * @return list
	 */
	public static List<News> parserNewsList(String json){
		Gson gson = new Gson();
		BaseEntity<List<News>> newsEntity = gson.fromJson(json, new TypeToken<BaseEntity<List<News>>>() {}.getType());
 		return newsEntity.getData();
	}
}
