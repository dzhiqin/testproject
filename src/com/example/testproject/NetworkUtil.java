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
	 * �ж��Ƿ�����������
	 * @params context ������Context
	 * @return falseû������ true �Ѿ�����
	 */
	public static boolean isNetworkUseful(Context context){
		ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(manager==null){
			return false;
		}
		//��ȡ��ǰ�������״̬�����ͬʱ������2G/3G/4G��wifi����Ĭ�ϵ�ǰ���������wifi
		//���û���������罫�᷵��null
		NetworkInfo networkInfo=manager.getActiveNetworkInfo();
		if(networkInfo==null||!networkInfo.isAvailable()){
			return false;
		}
		return true;
	}
	/**
	 * ���û������������ʾ�����Ի����Ѿ�����������true
	 * 
	 * @params Context context
	 * @params handler ������������û�ȡ��������������������Message_Cancel
	 * @return falseû��������true�Ѿ�����
	 */
	public static boolean showCheckNetworkDialog(final Context context, final Handler handler){
		if(!isNetworkUseful(context)){
			new AlertDialog.Builder(context).setMessage("��ǰ���粻���ã���������������á�")
				.setPositiveButton("ȥ����", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// ��android3.0���ϵİ汾���֣�wifi���������ñ�������������ҳ����
						//��3.0֮ǰ�İ汾��wifi������wifi����ҳ���У�����Ҫ���ݰ汾���в�ͬ����ת
						if(android.os.Build.VERSION.SDK_INT>10){
							context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
						}else{
							context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
						}
					}
				}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO �Զ����ɵķ������
						handler.sendEmptyMessage(Message_Cancel);
					}
				}).show();
			return false;
		}
		return true;
	}
}
