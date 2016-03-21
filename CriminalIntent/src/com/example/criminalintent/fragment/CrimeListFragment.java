package com.example.criminalintent.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.actionbarsherlock.app.SherlockListFragment;
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
public class CrimeListFragment extends SherlockListFragment {

	private static final String TAG = "CrimeListFragment";
	private ArrayList<Crime> mCrimes;
	
	private CrimeAdapter adapter;
	/**
	 * 
	 * 解决子标题显示后，旋转设备，这时因为用户界面的重新生
成，显示的子标题会消失。问题
	 * 
	 * 添加一个布尔类型的成员变量，在onCreate(...)方法中保留
CrimeListFragment并对变量进行初始化
	 */
	private boolean mSubtitleVisible;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//通知FragmentManager：CrimeListFragment需接收选项菜单方法回调
		setHasOptionsMenu(true);
		
		//方法可保留fragment。已保留的fragment不会随activity一起被销毁
		//使得mSubtitleVisible变量在设备旋转后依然可用
		setRetainInstance(true);
		mSubtitleVisible = false;
		
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
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = super.onCreateView(inflater, container, savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			//根据变量mSubtitleVisible的值确定是否要设置子标题
			if (mSubtitleVisible) {
				
				//getActivity().getActionBar().setSubtitle(R.string.subtitle);
				getSherlockActivity().getSupportActionBar().setSubtitle(R.string.subtitle);
			}
		}
		
		ListView listView = (ListView)view.findViewById(android.R.id.list);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			
			registerForContextMenu(listView);
		}else {
			
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
			
			listView.setMultiChoiceModeListener(multiChoice);
		}
		return view;
		
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
		
		((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
	}
	/**
	 * 实例化生成选项菜单
	 */
	@Override
	public void onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu, com.actionbarsherlock.view.MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		
		inflater.inflate(R.menu.fragment_crime_list, menu);
		//查看子标题的状态，以保证菜单项标题与之匹配显示
		com.actionbarsherlock.view.MenuItem showSubtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
		
		if (mSubtitleVisible && showSubtitleItem != null) {
			
			showSubtitleItem.setTitle(R.string.hide_subtitle);
		}
	}
	
	/**
	 * 实现onOptionsItemSelected(MenuItem)方法响应菜单项的选择事件
	 */
	@Override
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_new_crime:
			Crime crime = new Crime();
			CrimeLab.get(getActivity()).addCrime(crime);
			Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
			intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getmId());
			startActivityForResult(intent, 0);
			return true;
		case R.id.menu_item_show_subtitle:
			//切换菜单项标题,实现显示或隐藏CrimeListActivity操作栏的子标题
			/*if (getActivity().getActionBar().getSubtitle() == null) {
				
				getActivity().getActionBar().setSubtitle(R.string.subtitle);
				item.setTitle(R.string.hide_subtitle);
				mSubtitleVisible = true;
			} else {
				
				getActivity().getActionBar().setSubtitle(null);
				item.setTitle(R.string.show_subtitle);
				mSubtitleVisible = false;
			}*/
			if(getSherlockActivity().getSupportActionBar().getSubtitle() == null) {
				
				getSherlockActivity().getSupportActionBar().setSubtitle(R.string.subtitle);
				item.setTitle(R.string.hide_subtitle);
				mSubtitleVisible = true;
			} else {
				
				getSherlockActivity().getSupportActionBar().setSubtitle(null);
				item.setTitle(R.string.show_subtitle);
				mSubtitleVisible = false;
			}
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	/**
	 * 当前我们只定义了一个上下文菜单资源，因此，无论用户长按的是哪个视图，菜单都是以该
资源实例化生成的。假如定义了多个上下文菜单资源，通过检查传入onCreateContextMenu(...)
方法的View视图ID，我们可以自由决定使用哪个资源来生成上下文菜单。
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		
		getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
	}
	
	/**
	 * 监听上下文菜单项选择事件
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		/**
		 * 以下代码中，因为ListView是AdapterView的子类，所以getMenuInfo()方法返回了一个
AdapterView.AdapterContextMenuInfo实例。然后，将getMenuInfo()方法的返回结果进行
类型转换，获取选中列表项在数据集中的位置信息。最后，使用列表项的位置，获取要删除的
Crime对象。
		 */
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		int position = info.position;
		CrimeAdapter adapter = (CrimeAdapter)getListAdapter();
		Crime crime = adapter.getItem(position);
		
		switch (item.getItemId()) {
		case R.id.menu_item_delete_crime:
			
			CrimeLab.get(getActivity()).delete(crime);
			adapter.notifyDataSetChanged();
			return true;
		}
		return super.onContextItemSelected(item);
	}
	
	/**
	 * 视图在选中或撤销选中时会触发它
	 */
	private MultiChoiceModeListener multiChoice = new MultiChoiceModeListener() {
		
		//2在onCreateActionMode(...)方法之后，以及当前上下文操作栏需要刷新显示新数据时调用。
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}
		//4在用户退出上下文操作模式或所选菜单项操作已被响应，从而导致ActionMode对象将要销毁时调用
		//这里，也可完成在上下文操作模式下，响应菜单项操作而引发的相应fragment更新。
		@Override
		public void onDestroyActionMode(ActionMode mode) {
			
		}
		//1在ActionMode对象创建后调用。也是实例化上下文菜单资源，并显示在上下文操作栏上的任务完成的地方
		//系统自动产生的onCreateActionMode(...)存根方法会返回false值。记得将其改为返回true值，因为返回false值会导致操作模式创建失败。
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			//是从操作模式，而非activity中获取MenuInflater的
			//操作模式负责对上下文操作栏进行配置.。例如，可调用ActionMode.setTitle(...),方法为上下文操作栏设置标题，而activity的MenuInflater则做不到这一点。
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.crime_list_item_context, menu);
			return true;
		}
		//3在用户选中某个菜单项操作时调用。是响应上下文菜单项操作的地方。
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
			case R.id.menu_item_delete_crime:
				CrimeAdapter adapter = (CrimeAdapter)getListAdapter();
				CrimeLab crimeLab = CrimeLab.get(getActivity());
				for (int i = adapter.getCount() - 1; i >= 0; i--) {
					if (getListView().isItemChecked(i)) {
						
						crimeLab.delete(adapter.getItem(i));
					}
				}
				mode.finish();
				adapter.notifyDataSetChanged();
				return true;

			default:
				return false;
			}
			
		}
		
		@Override
		public void onItemCheckedStateChanged(ActionMode mode, int position,
				long id, boolean checked) {
			
		}
	};
	
	
	private OnItemLongClickListener onItemLongClickListener = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			
			
			return false;
		}
	
	
	};
}
