package com.example.criminalintent.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.format.DateFormat;

/**
 * 
 * 版权：融贯资讯 <br/>
 * 作者：wei.miao@rogrand.com <br/>
 * 生成日期：2016-3-11 <br/>
 * 描述：模型层犯罪
 */
public class Crime {

	//添加下列常量，然后实现toJSON()方法，以JSON格式保存Crime对象，并
	//返回可放入JSONArray的JSONObject类实例
	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	private static final String JSON_SOLVED = "solved";
	private static final String JSON_DATE = "date";
	
	/** 生成唯一标识符 **/
	private UUID mId;
	/** 犯罪标题 **/
	private String mTitle;
	
	private Date mDate;
	/** 是否已经处理 **/
	private boolean mSolved;
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
	public Crime() {
		
		mId  = UUID.randomUUID();
		mDate = new Date();
		format.format(mDate);
	}

	/**
	 * 接受JSONObject对象的构造方法
	 * @param json
	 * @throws JSONException
	 */
	public Crime(JSONObject json)throws JSONException{
		
		mId = UUID.fromString(json.getString(JSON_ID));
		if (json.has(JSON_TITLE)) {
			
		 mTitle = json.getString(JSON_TITLE);
		}
		
		mSolved = json.getBoolean(JSON_SOLVED);
		mDate = new Date(json.getLong(JSON_DATE));
	}
	public String getmTitle() {
		return mTitle;
	}

	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public UUID getmId() {
		return mId;
	}

	public Date getmDate() {
		return mDate;
	}

	public void setmDate(Date mDate) {
		this.mDate = mDate;
	}

	public boolean ismSolved() {
		return mSolved;
	}

	public void setmSolved(boolean mSolved) {
		this.mSolved = mSolved;
	}
	
	
	
	/**
	 * 将Crime对象数据转换为可写入JSON文件JSONObject对象数据
	 * @return
	 * @throws JSONException
	 */
	public JSONObject toJSON() throws JSONException{
		
		JSONObject json = new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_TITLE, mTitle);
		json.put(JSON_SOLVED, mSolved);
		json.put(JSON_DATE, mDate.getTime());
		
		return json;
	}
	
	@Override
	public String toString() {
		
		return mTitle;
	}
	
}
