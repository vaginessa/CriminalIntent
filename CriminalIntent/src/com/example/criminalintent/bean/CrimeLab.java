package com.example.criminalintent.bean;

import java.util.ArrayList;
import java.util.UUID;

import com.example.criminalintent.json.CriminalIntentJSONSerializer;

import android.content.Context;
import android.util.Log;

/** 
 * 
 * 版权：融贯资讯 <br/>
 * 作者：wei.miao@rogrand.com <br/>
 * 生成日期：2016-3-11 <br/>
 * 描述：模型层犯罪数组
 */
public class CrimeLab {
	
	//在CrimeLab类中进行数据持久保存
	private static final String TAG = "CrimeLab";
	private static final String FILENAME = "crimes.json";
	
	private CriminalIntentJSONSerializer mSerializer;
	
	
	/**创建可容纳Crime对象的ArrayList**/
	private ArrayList<Crime> mCrimes;
	
	private static CrimeLab sCrimeLab;
	private Context mAppContext;
	
	public CrimeLab(Context mContext) {
		
		mAppContext = mContext;
		//mCrimes = new ArrayList<Crime>();
		mSerializer = new CriminalIntentJSONSerializer(mAppContext, FILENAME);
		
		try {
			//首先尝试加载crime数据
			mCrimes = mSerializer.loadCrimes();
		} catch (Exception e) {
			
			mCrimes = new  ArrayList<Crime>();
		}
		//100条假数据
		/*for (int i = 0; i < 100; i++) {
			Crime c = new Crime();
			c.setmTitle("Crime #"+i);
			c.setmSolved(i%2 == 0);
			mCrimes.add(c);
		}*/
	}
	
	public static CrimeLab get(Context c){
		
		if (sCrimeLab == null) {
			//为保证单例总是有Context可以使用，可调用getApplicationContext()方法，将
			//不确定是否存在的Context替换成application context
			//ApplicationContext()针对应用全局性Context
			sCrimeLab = new  CrimeLab(c.getApplicationContext());
			
		}
		return sCrimeLab;
	}
	
	public ArrayList<Crime> getmCrimes() {
		return mCrimes;
	}
	
	public Crime getCrime(UUID id){
		for (Crime c : mCrimes) {
			
			if (c.getmId().equals(id)) {
				
				return c;
			}
		}
		return null;
	}
	/**
	 * 为响应用户点击New Crime菜单项，需实现新方法以添加新的Crime到crime数组列表中。
	 * @param crime
	 */
	public void addCrime(Crime crime){
		
		mCrimes.add(crime);
	}
	/**
	 * 进行数据持久保存
	 * @return
	 */
	public boolean saveCrimes(){
		
		try {
			mSerializer.saveCrimes(mCrimes);
			Log.d(TAG, "crimes saved to file");
			return true;
		} catch (Exception e) {
			Log.d(TAG, "Error saving crimes:",e);
			return false;
		}
	}
}

