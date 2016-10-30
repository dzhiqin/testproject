package com.example.testproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

public class NetworkUtil {
	public static final int Message_Cancel=0;
	public static final int Message_Succeed=1;
	/**
	 * 判断是否有网络连接
	 * @params context 上下文Context
	 * @return false没有联网 true 已经联网
	 */
	public static boolean isNetworkUseful(Context context){
		ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(manager==null){
			return false;
		}
		//获取当前活动的网络状态，如果同时开启了2G/3G/4G和wifi，则默认当前活动的网络是wifi
		//如果没有连接网络将会返回null
		NetworkInfo networkInfo=manager.getActiveNetworkInfo();
		if(networkInfo==null||!networkInfo.isAvailable()){
			return false;
		}
		return true;
	}
	/**
	 * 如果没有联网，则显示联网对话框；已经联网，返回true
	 * 
	 * @params Context context
	 * @params handler 处理器，如果用户取消联网，则向处理器发送Message_Cancel
	 * @return false没有联网，true已经联网
	 */
	public static boolean showCheckNetworkDialog(final Context context, final Handler handler){
		if(!isNetworkUseful(context)){
			new AlertDialog.Builder(context).setMessage("当前网络不可用，请检查你的网络设置。")
				.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 在android3.0以上的版本机种，wifi联网的设置被放置在主设置页面中
						//在3.0之前的版本，wifi联网在wifi设置页面中，所以要根据版本进行不同的跳转
						if(android.os.Build.VERSION.SDK_INT>10){
							context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
						}else{
							context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
						}
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO 自动生成的方法存根
						handler.sendEmptyMessage(Message_Cancel);
					}
				}).show();
			return false;
		}
		return true;
	}
}
