package com.example.criminalintent.fragment;

import java.util.List;

import com.example.criminalintent.R;

import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
/**
 * 
 * 版权：融贯资讯 <br/>
 * 作者：wei.miao@rogrand.com <br/>
 * 生成日期：2016-3-22 <br/>
 * 描述：创建并管理一个用来拍照的取景器
 */
public class CrimeCameraFragment extends Fragment {

	private static final String TAG = "CrimeCameraFragment";
	
	private Camera mCamera;
	private SurfaceView mSurfaceView;
	private Button takePictureButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_crime_camera,container, false);
			
		takePictureButton = (Button)view.findViewById(R.id.crime_camera_takePictureButton);
		takePictureButton.setOnClickListener(onClickListener);
		
		mSurfaceView = (SurfaceView)view.findViewById(R.id.crime_camera_sufaceView);
		SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
		//setType(...)方法和SURFACE_TYPE_PUSH_BUFFERS常量都已被弃用
		//既然已经是弃用的代码，为什么还要使用它们呢？在运行Honeycomb之前版本的设备上，相
		//机预览能够工作离不开setType(...)方法以及SURFACE_TYPE_PUSH_BUFFERS常量的支持。在代
		//码中，我们使用@SuppressWarnings注解来消除弃用代码相关的警告信息
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		surfaceHolder.addCallback(surfaceCallback);
		return view;
	}
	
	
	private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			switch (v.getId()) {
			case R.id.crime_camera_takePictureButton:
				
				getActivity().finish();
				
				break;

			default:
				break;
			}
			
		}
	};
	/**
	 * 该接口监听Surface生命周期中的事件，这样就可以控制Surface与其客户端协同工作。
	 */
	private SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
		
		//SurfaceView从屏幕上移除时，Surface也随即被销毁。通过该方法，可以通知Surface
		//的客户端停止使用Surface。
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			
			if (mCamera != null) {
				//该方法用来停止在Surface上绘制帧
				mCamera.stopPreview();
			}
		}
		//包含SurfaceView的视图层级结构被放到屏幕上时调用该方法。这里也是Surface与其客
		//户端进行关联的地方
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			
			try {
				if (mCamera != null) {
					//该方法用来连接Camera与Surface
					mCamera.setPreviewDisplay(holder);
				}
			} catch (Exception e) {
				
				Log.e(TAG, "Error setting up preview display",e);
			}
		}
		//Surface首次显示在屏幕上时调用该方法。通过传入的参数，可以知道Surface的像素格
		//式以及它的宽度和高度。该方法内可以通知Surface的客户端，有多大的绘制区域可以
		//使用。
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			
			if (mCamera == null) {
				
				return; 
			}
			Camera.Parameters parameters = mCamera.getParameters();
			//getSupportedPreviewSizes()该方法返回android.hardware.Camera.Size类实例的一个列表，每个实例封装了一个具体的图片宽高尺寸。
			Size size = getBestSupportedSize(parameters.getSupportedPreviewSizes(), width, height);
			parameters.setPreviewSize(size.width, size.height);
			mCamera.setParameters(parameters);
			try {
				//该方法用来在Surface上绘制帧
				mCamera.startPreview();
			} catch (Exception e) {
				Log.e(TAG, "Could not start preview",e);
				mCamera.release();
				mCamera = null;
			}
		}
	};
	
	/**
	 * 在onResume()方法中打开相机
	 */
	@Override
	public void onResume() {
		super.onResume();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			
			mCamera = Camera.open(0);
		}else {
			
			mCamera = Camera.open();
		}
	}
	/**
	 * onPause()方法释放相机资源
	 */
	@Override
	public void onPause() {
		super.onPause();
		if (mCamera != null) {
			
			mCamera.release();
			mCamera = null;
		}
	}
	/**
	 * 找出设备支持的最佳尺寸
	 * 该方法接受一组预览尺寸，然后找出具有最大数目像素的尺寸
	 * @param sizes
	 * @param width
	 * @param height
	 * @return
	 */
	private Size getBestSupportedSize(List<Size> sizes,int width,int height) {
		
		Size bestSize = sizes.get(0);
		int largestArea = bestSize.width * bestSize.height;
		for (Size size : sizes) {
			
			int area = size.width * size.height;
			if (area > largestArea) {
				
				bestSize = size;
				largestArea = area;
			}
		}
		
		return bestSize;
	}
}
