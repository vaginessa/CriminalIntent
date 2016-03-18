package com.example.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.example.criminalintent.bean.Crime;
import com.example.criminalintent.bean.CrimeLab;
import com.example.criminalintent.fragment.CrimeFragment;
/**
 * 
 * 版权：融贯资讯 <br/>
 * 作者：wei.miao@rogrand.com <br/>
 * 生成日期：2016-3-17 <br/>
 * 描述：将取代CrimeActivity。其布局将由一个ViewPager组成。
 *  其任务是创建并管理ViewPager
 */
public class CrimePagerActivity extends FragmentActivity {
	//可通过调用setOffscreenPageLimit(int)方法，定制预加载相邻页面的数目
	private ViewPager mViewPager;
	private ArrayList<Crime> mCrimes;
	//本章中的视图层级结构很简单，仅有一个视图。
	//因此，我们来学习以代码的方式定义视图层级结构。既然只有一个视图，此项任务处理起来并不复杂。
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		
		mCrimes = CrimeLab.get(this).getmCrimes();
		
		FragmentManager fm = getSupportFragmentManager();
		/**
		 * ViewPager在某种程度上有点类似于AdapterView（ListView的超类）。AdapterView需借
		  助于Adapter才能提供视图。同样地，ViewPager也需要PagerAdapter的支持。
		不过，相较于AdapterView与Adapter间的协同工作，ViewPager与PagerAdapte间的配合
		要复杂的多。幸运的是，可使用PagerAdapte的子类——FragmentStatePagerAdapter，来处
		理许多细节问题。
		FragmentStatePagerAdapter是我们的代理，负责管理与ViewPager的对话并协同工作
		 */
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			
			@Override
			public int getCount() {
				return mCrimes.size();
			}
			
			/**
			 * 是将返回的fragment添加给托管activity，并帮助Viewpager找到fragment的视图并一一对应。
			 */
			@Override
			public Fragment getItem(int arg0) {
				Crime crime = mCrimes.get(arg0);
				return CrimeFragment.newInstance(crime.getmId());
			}
		});
		//使用OnPageChangeListener监听ViewPager当前显示页面的状态变化
		//可将显示在操作栏（旧版本设备上叫标题栏）上的activity标题替换成当前Crime的标题
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				
				Crime crime = mCrimes.get(arg0);
				if (crime.getmTitle() != null) {
					
					setTitle(crime.getmTitle());
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
				
			}
			//方法可告知我们当前页面所处的行为状态，如正在被用户
			//滑动、页面滑动入位到完全静止以及页面切换完成后的闲置状态
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
		
		//循环检查crime的ID，找到所选crime在数组中的索引位置
		UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		for (int i = 0; i < mCrimes.size(); i++) {
			if (mCrimes.get(i).getmId().equals(crimeId)) {
				
				mViewPager.setCurrentItem(i);
				break;
			}
		}
	
	}
	
}
