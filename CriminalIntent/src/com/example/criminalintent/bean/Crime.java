package com.example.criminalintent.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import android.text.format.DateFormat;

/**
 * 
 * 版权：融贯资讯 <br/>
 * 作者：wei.miao@rogrand.com <br/>
 * 生成日期：2016-3-11 <br/>
 * 描述：模型层犯罪
 */
public class Crime {

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
	
	@Override
	public String toString() {
		
		return mTitle;
	}
	
}
