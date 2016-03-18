package com.example.criminalintent.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.example.criminalintent.CrimeActivity;
import com.example.criminalintent.CrimePagerActivity;
import com.example.criminalintent.R;
import com.example.criminalintent.adapter.CrimeAdapter;
import com.example.criminalintent.bean.Crime;
import com.example.criminalintent.bean.CrimeLab;
/**
 * 
 * 版权：融贯资讯 <br/>
 * 作者：wei.miao@rogrand.com <br/>
 * 生成日期：2016-3-14 <br/>
 * 描述：
 * ListFragment类默认实现方法已生成了一个全屏ListView布局
 */
public class CrimeListFragment extends ListFragment {

	private static final String TAG = "CrimeListFragment";
	private ArrayList<Crime> mCrimes;
	
	private CrimeAdapter adapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		//设置托管CrimeListFragment的activity标题
		getActivity().setTitle(R.string.crime_title);
		mCrimes = CrimeLab.get(getActivity()).getmCrimes();
	
		///ListView会提前准备好所有要显示的View对象吗？倘若这样，效
		//率可就太低了。其实View对象只有在屏幕上显示时才有必要存在。列表的数据量非常大，为整个
		//列表创建并储存所有视图对象不仅没有必要，而且会导致严重的系统性能及内存占用问题。
		//ListView找谁去申请视图对象呢？ 答案是adapter。
		//adapter是一个控制器对象，从模型层获取数据，并将之提供给ListView显示，起到了沟通桥梁的作用
		/*ArrayAdapter<Crime> adapter = new ArrayAdapter<Crime>(getActivity(), android.R.layout.simple_list_item_1, mCrimes);
		setListAdapter(adapter);*/
		//使用自定义adapter
		adapter = new CrimeAdapter(getActivity(), mCrimes);
		setListAdapter(adapter);
	}
	//CheckBox默认是可聚焦的。这意味着，点击列表项会被解读为切换CheckBox
	//的状态，自然也就无法触发onListItemClick(...)方法了
	//将CheckBox定义为非聚焦状态组件即可
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		//Crime crime = (Crime)getListAdapter().getItem(position);
		Crime crime = ((CrimeAdapter)getListAdapter()).getItem(position);
		//Log.d(TAG, crime.getmTitle()+"was clicked");
		
		//只需几行简单的代码，就可实现让fragment直接获取托管activity的intent。然而，这种方式是
		//以牺牲fragment的封装性为代价的。CrimeFragment不再是可复用的构建单元， 因为它总是需要
		//由某个具体activity托管着，该activity的Intent又定义了名为EXTRA_CRIME_ID的extra。
		//按照当前的编码实现，CrimeFragment便再也无法用于任何其他的activity了。
		/*Intent intent = new Intent(getActivity(),CrimeActivity.class);
		intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getmId());
		startActivity(intent);*/
	
		//废弃使用CrimeActivity，我们来配置使用CrimePagerActivity
		
		Intent intent = new Intent(getActivity(),CrimePagerActivity.class);
		intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getmId());
		startActivity(intent);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
	}
	
	//通过fragment 获取返回结果
	//如需从被启动的activity获取返回结果，
	//本章我们将选择调用Fragment.startActivityForResult(...)方法，而非Activity的startActivityForResult(...)方法
	//从fragment中返回结果的处理稍有不同。fragment能够从activity中接收返回结果，但其自身无法产
	//生返回结果(不具有任何setResult(...)方法)。只有activity拥有返回结果
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
