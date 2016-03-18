package com.example.criminalintent.adapter;

import java.util.ArrayList;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.criminalintent.R;
import com.example.criminalintent.bean.Crime;
/**
 * 
 * 版权：融贯资讯 <br/>
 * 作者：wei.miao@rogrand.com <br/>
 * 生成日期：2016-3-14 <br/>
 * 描述：定制布局用来显示特定Crime对象信息的列表项
 */
public class CrimeAdapter extends ArrayAdapter<Crime> {
	
	private FragmentActivity mActivity;
	/**
	 * adapter是一个控制器对象，从模型层获取数据，并将之提供给ListView显示，起到了沟通桥梁的作用
	 * @param activity
	 * @param crimes
	 */
	public CrimeAdapter(FragmentActivity activity,ArrayList<Crime> crimes) {
		
		super(activity, 0, crimes);
		mActivity = activity;
		
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		if (convertView == null) {
			
			convertView = mActivity.getLayoutInflater().inflate(R.layout.list_item_crime,null);
		}
		
		Crime crime = getItem(position);
		TextView titleTextView = (TextView)convertView.findViewById(R.id.crime_list_item_titleTextView);
		TextView dateTextView = (TextView)convertView.findViewById(R.id.crime_list_item_dateTextView);
		CheckBox solvedCheckBox = (CheckBox)convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
		
		titleTextView.setText(crime.getmTitle());
		dateTextView.setText(crime.getmDate().toString());
		solvedCheckBox.setChecked(crime.ismSolved());

		
		
		return convertView;
	}

}
