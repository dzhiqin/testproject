package com.example.testproject;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
				Toast.makeText(context, "ȡ����������", Toast.LENGTH_SHORT).show();
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
		importApk();
		if(ApplicationUtil.isAvailable(getApplicationContext(), "com.example.customcitydatabase")){
			Intent intent=new Intent();
			ComponentName componentName=new ComponentName("com.example.customcitydatabase","com.example.customcitydatabase.MainActivity");
			intent.setComponent(componentName);
			startActivityForResult(intent,RESULT_OK);
			
		}else{
			//���δ��װָ��app�ʹ�ָ����ַ����,���ߴ��ֻ��ڰ�װ
			/*Uri uri=Uri.parse("market://details?id=com.tencent.mm");
			Intent i=new Intent(Intent.ACTION_VIEW,uri);
			startActivity(i);*/
			
			new AlertDialog.Builder(context).setMessage("δ��װָ��app")
				.setPositiveButton("ȥ��װ", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String filePath="/data"+Environment.getDataDirectory().getAbsolutePath()
								+"/com.example.testproject/files/customcitydatabase.apk";
						Intent i=new Intent(Intent.ACTION_VIEW);
						i.setDataAndType(Uri.parse("file://"+filePath), "application/vnd.android.package-archive");
						startActivity(i);
						
					}
				}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO �Զ����ɵķ������
						
					}
				}).show();
		}
		
	}
	private void importApk(){
		String filePath="/data"+Environment.getDataDirectory().getAbsolutePath()
				+"/com.example.testproject/files/customcitydatabase.apk";
		if(!new File(filePath).exists())
		{
			//���apk�ļ������ڣ��ʹ�raw�ļ����е���
			try{
				InputStream in=getResources().openRawResource(R.raw.customcitydatabase);
				//FileOutputStream fos=this.openFileOutput("customcitydatabase.apk", Context.MODE_WORLD_READABLE);
				//FileOutputStream fos=new FileOutputStream(filePath);//Ҫ�ȶ����file�ļ��������ܸ���mode
				File file =new File(filePath);				
				FileOutputStream fos=new FileOutputStream(file);
				byte[] buffer=new byte[1024];
				int count=0;
				while((count=in.read(buffer))>0){
					fos.write(buffer,0,count);
				}
				Runtime.getRuntime().exec("chmod 777 "+file);
				fos.close();
				in.close();
			}catch(FileNotFoundException e){
				e.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}
		}else{
			Log.v("testproject", "file already exist");
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
