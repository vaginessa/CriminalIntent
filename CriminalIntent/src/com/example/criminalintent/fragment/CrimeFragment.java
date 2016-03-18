package com.example.criminalintent.fragment;

import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.example.criminalintent.R;
import com.example.criminalintent.bean.Crime;
import com.example.criminalintent.bean.CrimeLab;
import com.example.criminalintent.dialog.DatePicketFragment;
/**
 * 
 * 版权：融贯资讯 <br/>
 * 作者：wei.miao@rogrand.com <br/>
 * 生成日期：2016-3-11 <br/>
 * 描述：本类是与模型及视图对象交互的控制器，用于显示特定crime的明细信息，并在
用户修改这些信息后立即进行内容更新
 */

/** 向处于运行状态的activity中添加fragment时，以下fragment生命周期方法会被依次调用：
onAttach(Activity)、onCreate(Bundle)、 onCreateView(...)、onActivityCreated(Bundle)、
onStart()，以及onResume()方法 **/
public class CrimeFragment extends Fragment {

	public static final String EXTRA_CRIME_ID = "com.criminalintent.crime_id";
	
	private static final String DIALOG_DATE = "date";
	/**
	 * 请求代码
	 */
	private static final int REQUEST_DATE = 0;
	
	private Crime mCrime;
	
	private EditText mTitleFil;
	
	private Button mDateButton;
	
	private CheckBox mSolvedCheckBox;
	
	/** 首先，Fragment.onCreate(Bundle)是公共方法，而Activity.onCreate(Bundle)是保护
方法。因为需要被托管fragment的任何activity调用 **/
	//配置fragment实例
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//首先应通知FragmentManager：CrimeFragment将代表其托管
		//activity 实现选项菜单相关的回调方法
		setHasOptionsMenu(true);
		
		//mCrime = new Crime();
		//简单的获取方式
		//UUID crimeId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_CRIME_ID);
		
		//获取argument
		UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
		mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
	
	
	}
	
	/** 创建和配置fragment视图 ,将生成的View 返回给托管activity**/
	//Bundle包含了供该方法在保存状态下重建视图所使用的数据。
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_crime, container,false);
		
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				
				//启用应用图标向上导航按钮的功能，并在fragment视图上显示向左的图标
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
		
		mTitleFil = (EditText) view.findViewById(R.id.crime_title);
		mTitleFil.setText(mCrime.getmTitle());
		mTitleFil.addTextChangedListener(textWatcher);
		
		mDateButton = (Button) view.findViewById(R.id.crime_date);
		//mDateButton.setText(mCrime.getmDate().toString());
		//mDateButton.setEnabled(false);
		updateDate();
		mDateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				FragmentManager fm = getActivity().getSupportFragmentManager();
				
				//DatePicketFragment dialog = new DatePicketFragment();
				DatePicketFragment dialog = DatePicketFragment.newInstance(mCrime.getmDate());
				//设置目标fragment,可将CrimeFragment设置成DatePickerFragment的目标fragment，建立关联，类似于传入startActivityForResult(...)
				//该方法接受目标fragment以及一个类似于传入startActivityForResult(...)方法的请求
				//代码作为参数
				dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
				//要将DialogFragment添加给FragmentManager管理并放置到屏幕上，可调用fragment实例show方法
				dialog.show(fm, DIALOG_DATE);
				
			}
		});
		
		mSolvedCheckBox = (CheckBox) view.findViewById(R.id.crime_solved);
		mSolvedCheckBox.setChecked(mCrime.ismSolved());
		mSolvedCheckBox.setOnCheckedChangeListener(onChecked);
		
		return view;
	}
	
	
	
	private TextWatcher textWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			
			mCrime.setmTitle(s.toString());
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			
		}
	};
	
	private OnCheckedChangeListener onChecked = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			
			mCrime.setmSolved(isChecked);
			
		}
	};
	/** 保存及获取状态的bundle **/
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	/**
	 * 附加argument给fragment
	 * 附加argument bundle给fragment，需调用Fragment.setArguments(Bundle)方法。注意，该
		任务必须在fragment创建后、添加给activity前完成。
	 * @param crimeId
	 * @return
	 */
	public static CrimeFragment newInstance(UUID crimeId){

		Bundle args = new Bundle();
		args.putSerializable(EXTRA_CRIME_ID, crimeId);
		
		CrimeFragment fragment = new CrimeFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	//覆盖onActivityResult(...)方法，从extra中获取日期数据，设置
	//对应Crime的记录日期，然后刷新日期按钮的显示
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (resultCode != Activity.RESULT_OK) {
			
			return;
		}
		if (requestCode == REQUEST_DATE) {
			
			Date date = (Date)data.getSerializableExtra(DatePicketFragment.EXTRA_DATE);
			mCrime.setmDate(date);
			//mDateButton.setText(mCrime.getmDate().toString());
			updateDate();
			
		}
	}
	
	private void updateDate() {
		
		mDateButton.setText(mCrime.getmDate().toString());
	}
	
	/**
	 * 覆盖onOptionsItemSelected(MenuItem)方法，响应用户对该菜单项
		的点击事件
		无需在XML文件中定义或生成应用图标菜单项。它已具有现成的资源ID：android.R.id.home。
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			//调用NavUtils.get-ParentActivityName(Activity)方法，检查元数据中是否指定了父activity。如指定有父activity，
			//则调用navigateUpFromSameTask(Activity)方法，导航至父activity界面。
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				
				NavUtils.navigateUpFromSameTask(getActivity());
			}
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
}
