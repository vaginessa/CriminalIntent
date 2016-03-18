package com.example.criminalintent;

import java.util.UUID;

import android.support.v4.app.Fragment;

import com.example.criminalintent.fragment.CrimeFragment;
/**
 * 
 * 版权：融贯资讯 <br/>
 * 作者：wei.miao@rogrand.com <br/>
 * 生成日期：2016-3-11 <br/>
 * 描述：Android编程权威指南第7章内容
 * 
 * FragmentActivity
 * 1、在布局中为fragment的视图安排位置
 *  1.1 添加fragment到activity布局中(简单但不灵活)
 *  1.2 在activity代码中添加fragment(复杂但灵活，可以在运行时控制fragment)
 * 2、管理fragment实例的生命周期
 */
public class CrimeActivity extends SingleFragmentActivity {

	/*@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
		//因为使用了支持库及FragmentActivity类，
		//因此这里调用的方法是getSupportFragmentManager()
		//FragmentManager类具体管理的是：
		// fragment队列；
		// fragment事务的回退栈
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		
		if (fragment == null) {
			fragment = new CrimeFragment();
			//创建并提交一个fragment事务
			//fragment事务被用来添加、移除、附加、分离或替换fragment队列中的fragment
			FragmentTransaction transaction = fm.beginTransaction();
			transaction.add(R.id.fragmentContainer, fragment);
			//将新建的fragment添到队列中
			transaction.commit();
		}
	}*/

	@Override
	protected Fragment createFragment() {
		
		//return new CrimeFragment();
		
		//当CrimeActivity创建CrimeFragment时，应调用CrimeFragment.newInstance(UUID)
		//方法，并传入从它的extra中获取的UUID参数
		UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		
		return CrimeFragment.newInstance(crimeId);
	}

}
