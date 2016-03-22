package com.example.criminalintent;

import com.actionbarsherlock.view.Window;
import com.example.criminalintent.fragment.CrimeCameraFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.WindowManager;
/**
 * 
 * 版权：融贯资讯 <br/>
 * 作者：wei.miao@rogrand.com <br/>
 * 生成日期：2016-3-22 <br/>
 * 描述：创建相机的activity类
 * ，还需要在配置文件中增加uses-permission
元素节点以获得使用相机的权限。
 */
public class CrimeCameraActivity extends SingleFragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		//隐藏 window Title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//隐藏 状态栏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		super.onCreate(arg0);
	}
	
	/**
	 * 覆盖createFragment()方法返回一个CrimeCameraFragment
	 */
	@Override
	protected Fragment createFragment() {
		
		return new CrimeCameraFragment();
	}

}
