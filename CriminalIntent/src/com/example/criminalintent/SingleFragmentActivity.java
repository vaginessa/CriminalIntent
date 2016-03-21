package com.example.criminalintent;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
/**
 * 
 * 版权：融贯资讯 <br/>
 * 作者：wei.miao@rogrand.com <br/>
 * 生成日期：2016-3-14 <br/>
 * 描述：通用抽象类
 * activity都需要同样的代码。为避免不必要的重复性输入，我们将这
些重复代码置入一个抽象类
 */
public abstract class SingleFragmentActivity extends SherlockFragmentActivity {

	protected abstract Fragment createFragment();
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		setContentView(R.layout.activity_fragment);
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		if (fragment== null) {
			
			fragment = createFragment();
			FragmentTransaction beginTransaction = fm.beginTransaction();
			beginTransaction.add(R.id.fragmentContainer, fragment);
			beginTransaction.commit();
		}
	}
}
