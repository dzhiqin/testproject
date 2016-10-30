package com.example.testproject;



import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends Activity {

	private Context context=this;
	private  Handler mHandler=new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case NetworkUtil.Message_Cancel:
				Toast.makeText(context, "取消设置网络", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LinearLayout mLayout=(LinearLayout)findViewById(R.id.my_layout);
		Log.v("DEBUG", "mainlayout parent="+mLayout.getParent());
		LayoutInflater inflater=LayoutInflater.from(this);
		View button=inflater.inflate(R.layout.my_button, null);
		mLayout.addView(button);
		NetworkUtil.showCheckNetworkDialog(context, mHandler);
		if(ApplicationUtil.isAvailable(getApplicationContext(), "com.example.customcitydatabase")){
			Intent intent=new Intent();
			ComponentName componentName=new ComponentName("com.example.customcitydatabase","com.example.customcitydatabase.MainActivity");
			intent.setComponent(componentName);
			startActivityForResult(intent,RESULT_OK);
		}else{
			//如果未安装指定app就从指定网址下载
			Uri uri=Uri.parse("market://details?id=com.tencent.mm");
			Intent i=new Intent(Intent.ACTION_VIEW,uri);
			startActivity(i);
		}
		importApk();
	}
	private void importApk(){
		Toast.makeText(MainActivity.this,"importApplication", Toast.LENGTH_SHORT).show();
		try{
			InputStream in=getResources().openRawResource(R.raw.customcitydatabase);
			FileOutputStream fos=this.openFileOutput("customcitydatabase.apk", Context.MODE_WORLD_READABLE);
			byte[] buffer=new byte[1024];
			int count=0;
			while((count=in.read(buffer))>0){
				fos.write(buffer,0,count);
			}
			fos.close();
			in.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
