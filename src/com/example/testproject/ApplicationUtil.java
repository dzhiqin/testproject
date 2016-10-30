package com.example.testproject;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

public class ApplicationUtil {
	public static boolean isAvailable(Context context,String packageName){
		final PackageManager pgManager=context.getPackageManager();
		//获取所有已安装程序的包信息
		List<PackageInfo> pgInfo=pgManager.getInstalledPackages(0);
		for(int i=0;i<pgInfo.size();i++){
			
			if(pgInfo.get(i).packageName.equalsIgnoreCase(packageName))
				return true;
		}
	
		return false;
	}
	public static void install(Context context,String filePath){
		Intent i=new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://"+filePath), "application/vnd.android.package-archive");
		context.startActivity(i);
	}
}
