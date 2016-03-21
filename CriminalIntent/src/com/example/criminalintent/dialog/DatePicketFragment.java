package com.example.criminalintent.dialog;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.example.criminalintent.R;
import com.example.criminalintent.R.layout;
/**
 * 第12章 创建DialogFragment
 * 版权：融贯资讯 <br/>
 * 作者：wei.miao@rogrand.com <br/>
 * 生成日期：2016-3-17 <br/>
 * 描述：和其他fragment一样，DialogFragment实例也是由托管activity的FragmentManager管理着的。
 */
public class DatePicketFragment extends SherlockDialogFragment {

	
	public static final String EXTRA_DATE = "com.android.criminalintent.date";
	
	private Date mDate;
	/**
	 * CrimeFragment和DatePickerFragment间的数据传递
	 * 传递数据给DatePickerFragment
	 * @param date
	 * @return
	 */
	public static DatePicketFragment newInstance(Date date){
		
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_DATE,date);
		
		DatePicketFragment fragment = new DatePicketFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	
	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	
		//在onCreateDialog(...)方法内，从argument中获取Date对象，然后使用它和Calendar对
		//象完成DatePicker的初始化工作
		mDate = (Date)getArguments().getSerializable(EXTRA_DATE);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		
		//第一种方式(建议使用)
		//如想调整对话框的显示内容时，直接修改布局文件会更容易些。例如，如想在对话
		//框的DatePicker旁再添加一个TimePicker，这时，只需更新布局文件，即可完成新视图的显示。
		View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
		//第二种方式
		//DatePicker view = new DatePicker(getActivity());
		
		DatePicker datePicker = (DatePicker)view.findViewById(R.id.dialog_date_datePicker);
		datePicker.init(year, month, day, new OnDateChangedListener() {
			
			//用户改变DatePicker内的日期后，Date对象即可得到同步更新
			@Override
			public void onDateChanged(DatePicker view, int year, int month,int day) {
				
				mDate = new GregorianCalendar(year, month, day).getTime();
				//为防止设备旋转时发生Date数据的丢失，在onDateChanged(...)方法的尾部，我们将Date
				//对象回写保存到了fragment argument中
				getArguments().putSerializable(EXTRA_DATE, mDate);
			}
			
		});
		
		//Android有3种可用于对话框的按钮：positive按钮、negative按钮以及neutral按钮
		return new AlertDialog.Builder(getActivity())
				.setView(view)
				.setTitle(R.string.date_picker_title)
				//.setPositiveButton(android.R.string.ok, null)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						sendResult(Activity.RESULT_OK);
					}
				})
				.create();
	}
	
	
	
	private void sendResult(int resultCode){
		if (getTargetFragment() == null) {
			
			return ;
		}
		Intent i = new Intent();
		i.putExtra(EXTRA_DATE, mDate);
		//调用的是CrimeFragment.onActivityResult(...)方法，因为
		//目标fragment以及请求代码由FragmentManager负责跟踪记录，我们可调用fragment（设置
		//目标fragment的fragment）的getTargetFragment()和getTargetRequestCode()方法获取它们。
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
		
	}
}
