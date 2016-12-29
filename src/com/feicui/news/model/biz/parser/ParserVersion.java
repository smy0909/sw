package com.feicui.news.model.biz.parser;

import java.lang.reflect.Type;

import com.feicui.news.model.entity.Version;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ParserVersion {

	/**
	 * 解析版本更新
	 * @param json
	 * @return
	 */
	public static Version parserJson(String json){
		Gson gson = new Gson();
		Type type =new TypeToken<Version>(){}.getType();
		return gson.fromJson(json, type);
	}
}
