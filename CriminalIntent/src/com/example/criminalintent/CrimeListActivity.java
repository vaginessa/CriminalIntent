package com.example.criminalintent;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.criminalintent.bean.Crime;
import com.example.criminalintent.fragment.CrimeFragment;
import com.example.criminalintent.fragment.CrimeListFragment;
/**
 * 
 * 版权：融贯资讯 <br/>
 * 作者：wei.miao@rogrand.com <br/>
 * 生成日期：2016-3-14 <br/>
 * 描述：
 */
public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callbacks,CrimeFragment.Callbacks {

	@Override
	protected Fragment createFragment() {
		
		return new CrimeListFragment();
	}
	
	/**
	 * 使用别名资源来解决这个问题。
	 * 用于手机指向activity_fragment.xml布局的别名资源，以及用于平板指向
activity_twopane.xml布局的别名资源。
	 */
	@Override
	protected int getLayoutResId() {
		
		//return R.layout.activity_twopane;
		return R.layout.activity_masterdetail;
	}

	/**
	 * onCrimeSelected(Crime)方法被调用时，CrimeListActivity需要完成以下二选一的任务：
	 * 如果使用手机用户界面布局，启动新的CrimePagerActivity；
	 * 如果使用平板设备用户界面布局，将CrimeFragment放入detailFragmentContainer中。
	 * 为确定需实例化手机还是平板界面布局，可以检查布局ID。但最好最准确的检查方式是检查
		布局是否包含detailFragmentContainer。因为，布局文件名随时可能更改，并且我们也不关
		心布局是从哪个文件实例化产生。我们只需知道，布局文件是否包含可以放入CrimeFragment
		的detailFragmentContainer。
	 */
	@Override
	public void onCrimeSelected(Crime crime) {
		if (findViewById(R.id.detailFragmentContainer) == null) {
			
			Intent intent = new Intent(this,CrimePagerActivity.class);
			intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getmId());
			startActivity(intent);
		}else {
			
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			
			Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
			Fragment newDeatil = CrimeFragment.newInstance(crime.getmId());
			
			if (oldDetail != null) {
				
				ft.remove(oldDetail);
			}
			ft.add(R.id.detailFragmentContainer, newDeatil);
			ft.commit();
		}
		
		
	}

	/**
	 * 刷新显示crime列
	 */
	@Override
	public void onCriemUpdate(Crime crime) {
		FragmentManager fm = getSupportFragmentManager();
		CrimeListFragment listFragment = (CrimeListFragment)fm.findFragmentById(R.id.fragmentContainer);
		listFragment.updateUI();
	}

}
