package com.example.criminalintent.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;

import com.example.criminalintent.bean.Crime;

/**
 * 版权：融贯资讯 <br/>
 * 作者：wei.miao@rogrand.com <br/>
 * 生成日期：2016-3-18 <br/>
 * 描述：创建和解析JSON数据
 * 封装到独立的单元会有诸多优点
 * 对应用中其他代码部分的依赖度较低，独立封装类更容易进行单元测试
 */
public class CriminalIntentJSONSerializer {

	private Context mContext;
	private String mFilename;
	
	public CriminalIntentJSONSerializer(Context c,String f) {
		
		mContext = c;
		mFilename = f;
	}
	
	/**
	 * 从文件中加载crime记录的loadCrimes()
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public ArrayList<Crime> loadCrimes()throws IOException,JSONException {
		ArrayList<Crime> crimes = new ArrayList<Crime>();
		BufferedReader reader = null;
		
		try {
			
			InputStream in = mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			//一次读取一行
			while ((line = reader.readLine()) != null) {
				
				jsonString.append(line);
				
			}
			JSONArray array = (JSONArray)new JSONTokener(jsonString.toString()).nextValue();
			for (int i = 0; i < array.length(); i++) {
				
				crimes.add(new Crime(array.getJSONObject(i)));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			//即使发生错误，也可以保证完成底层文件句柄的释放
			if (reader != null) {
				
				reader.close();
			}
		}
		
		return crimes;
	}
	
	/**
	 * 创建json对象
	 * @param crimes
	 */
	public void saveCrimes(ArrayList<Crime> crimes) throws JSONException,IOException {
		
		JSONArray array = new JSONArray();
		for (Crime crime : crimes) {
			
			array.put(crime.toJSON());
			
			Writer writer = null;
			
			try {
				//openFileOutput该方法接受文件名
				//以及文件操作模式参数，会自动将传入的文件名附加到应用沙盒文件目录路径之后，形成一个新
				//路径，然后在新路径下创建并打开文件，等待数据写入
				
				//如选择手动获取私有文件目录并在其下
				//创建和打开文件，记得总是使用Context.getFilesDir()替代方法
				OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
				
				writer = new OutputStreamWriter(out);
				writer.write(array.toString());
			} catch (Exception e) {
				
				e.printStackTrace();
			}finally{
				
				if (writer != null) {
					
					writer.close();
				}
			}
		}

	}
}
