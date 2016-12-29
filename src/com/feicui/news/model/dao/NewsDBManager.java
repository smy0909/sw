package com.feicui.news.model.dao;

import java.util.ArrayList;
import java.util.List;

import com.feicui.news.model.entity.News;
import com.feicui.news.model.entity.NewsType;
import com.feicui.news.model.entity.SubType;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NewsDBManager {
	private DBOpenHelper dbHelper;
	private Context context;
	
	public  NewsDBManager(Context context){
		this.context=context;
		dbHelper=new DBOpenHelper(context);
	}
	
	/** 数据数量 */
	public long getCount() {
		SQLiteDatabase db=dbHelper.getReadableDatabase();
		Cursor cursor=db.rawQuery("select count(*) from news",null);
		long len = 0;
		if (cursor.moveToFirst()) {
			len = cursor.getLong(0);
		}
		cursor.close();
		db.close();
		return len;
	}
	/** 添加数据 */
	public void insertNews(News news) {
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("type", news.getType());
		values.put("nid", news.getNid());
		values.put("stamp", news.getStamp());
		values.put("icon", news.getIcon());
		values.put("title", news.getTitle());
		values.put("summary", news.getSummary());
		values.put("link", news.getLink());
		db.insert("news", null, values);
		db.close();
	}
	/** 查询数据 */
	public ArrayList<News> queryNews(int count, int offset) {
		ArrayList<News> newsList=new ArrayList<News>();
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		String sql="select * from news order by _id desc limit "+count+" offset "+offset;
		Cursor cursor=db.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				int type = cursor.getInt(cursor.getColumnIndex("type"));
				int nid = cursor.getInt(cursor.getColumnIndex("nid"));
				String stamp = cursor.getString(cursor.getColumnIndex("stamp"));
				String icon = cursor.getString(cursor.getColumnIndex("icon"));
				String title = cursor.getString(cursor.getColumnIndex("title"));
				String summary = cursor.getString(cursor.getColumnIndex("summary"));
				String link = cursor.getString(cursor.getColumnIndex("link"));
				News news = new News(type, nid, stamp, icon, title, summary, link);
				newsList.add(news);
			} while (cursor.moveToNext());
			cursor.close();
			db.close();
		}
		return newsList;
	}

	public List<Integer> queryNewsIds() {
		List<Integer> ids=null;
		try {
			ids=new ArrayList<Integer>();
			dbHelper=new DBOpenHelper(context);
			SQLiteDatabase db=dbHelper.getReadableDatabase();
			String sql="select nid from news";
			Cursor cursor=db.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				do {
					int nid = cursor.getInt(cursor.getColumnIndex("nid"));
					ids.add(nid);
				} while (cursor.moveToNext());
				cursor.close();
				db.close();
			}
			return ids;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 收藏新闻
	 * @param news
	 */
	public boolean saveLoveNews(News news){
		try {
			SQLiteDatabase db=dbHelper.getWritableDatabase();
			Cursor cursor=db.rawQuery("select * from lovenews where nid="+news.getNid(),null);
			if(cursor.moveToFirst()){
				cursor.close();
				return false;
			}
			cursor.close();
			ContentValues values=new ContentValues();
			values.put("type", news.getType());
			values.put("nid", news.getNid());
			values.put("stamp", news.getStamp());
			values.put("icon", news.getIcon());
			values.put("title", news.getTitle());
			values.put("summary", news.getSummary());
			values.put("link", news.getLink());
			db.insert("lovenews", null, values);
			db.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取收藏新闻的列表
	 * @return 新闻的列表
	 */
	public ArrayList<News> queryLoveNews(){
		ArrayList<News> newsList=new ArrayList<News>();
		SQLiteDatabase db=dbHelper.getReadableDatabase();
		String sql="select * from lovenews order by _id desc";
		Cursor cursor=db.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				int type = cursor.getInt(cursor.getColumnIndex("type"));
				int nid = cursor.getInt(cursor.getColumnIndex("nid"));
				String stamp = cursor.getString(cursor.getColumnIndex("stamp"));
				String icon = cursor.getString(cursor.getColumnIndex("icon"));
				String title = cursor.getString(cursor.getColumnIndex("title"));
				String summary = cursor.getString(cursor.getColumnIndex("summary"));
				String link = cursor.getString(cursor.getColumnIndex("link"));
				News news = new News(type, nid, stamp, icon, title, summary, link);
				newsList.add(news);
			} while (cursor.moveToNext());
			cursor.close();
			db.close();
		}
		return newsList;
	}
	
	/**
	 * 保存新闻分类
	 * @param news
	 */
	public boolean saveNewsType(List<SubType> types){
		for(SubType type:types) {
			try {
				SQLiteDatabase db=dbHelper.getWritableDatabase();
				Cursor cursor=db.rawQuery("select * from type where subid="+type.getSubid(),null);
				if(cursor.moveToFirst()){
					cursor.close();
					return false;
				}
				cursor.close();
				ContentValues values=new ContentValues();
				values.put("subid", type.getSubid());
				values.put("subgroup", type.getSubgroup());
				db.insert("type", null, values);
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 获取新闻分类
	 * @return 新闻的列表
	 */
	public ArrayList<SubType> queryNewsType(){
		ArrayList<SubType> newsList=new ArrayList<SubType>();
		SQLiteDatabase db=dbHelper.getReadableDatabase();
		String sql="select * from type order by _id desc";
		Cursor cursor=db.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				int subId = cursor.getInt(cursor.getColumnIndex("subid"));
				String subGroup = cursor.getString(cursor.getColumnIndex("subgroup"));
				SubType subType = new SubType(subId, subGroup);
				newsList.add(subType);
			} while (cursor.moveToNext());
			cursor.close();
			db.close();
		}
		return newsList;
	}
}
	
