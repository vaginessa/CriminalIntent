package com.example.criminalintent;

import com.example.criminalintent.fragment.CrimeListFragment;

import android.support.v4.app.Fragment;
/**
 * 
 * 版权：融贯资讯 <br/>
 * 作者：wei.miao@rogrand.com <br/>
 * 生成日期：2016-3-14 <br/>
 * 描述：
 */
public class CrimeListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		
		return new CrimeListFragment();
	}

}
