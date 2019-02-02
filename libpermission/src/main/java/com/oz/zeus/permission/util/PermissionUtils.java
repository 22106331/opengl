package com.oz.zeus.permission.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Process;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Log;


import com.oz.zeus.permission.BuildConfig;

import java.lang.reflect.Method;


import static android.os.Build.VERSION_CODES.M;

public class PermissionUtils {
	public static boolean requestFloatWindoWSDKV6(Activity context, int requestCode) {
		try {
			Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
			context.startActivityForResult(intent, requestCode);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 检查是否有悬浮窗权限：
	 *
	 * @param context
	 * @return 如果有，则返回true
	 */
	public static boolean checkFloatWindowPermission(Context context) {
		if (isNonFloatPermission()) {
			return true;
		}
		/**
		 * 华为手机悬浮窗检查
		 */
		if (PermissionHuaWeiUtils.checkIsHuaweiRom()) {
			return PermissionHuaWeiUtils.checkFloatWindowPermission(context);
		}
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			return Settings.canDrawOverlays(context);
		} else {
			/*24---OP_SYSTEM_ALERT_WINDOW*/
			return checkOp(context, 24);
		}
	}
	
	/**
	 * 适配不需要悬浮窗权限的手机
	 *
	 * @return 如果不需要权限，则返回true
	 */
	public static boolean isNonFloatPermission() {
		if (DeviceInfoUtils.isSDK_M()) {
			if (DeviceInfoUtils.isSamsung() || DeviceInfoUtils.isLGEVs987()) {//||DeviceInfoUtils.isOppoR9s()
				return true;
			}
		}
		return false;
	}
	
	@TargetApi(Build.VERSION_CODES.KITKAT)
	private static boolean checkOp(Context context, int op) {
		@SuppressWarnings("ResourceType")
		AppOpsManager manager = (AppOpsManager)
				                        context.getSystemService(Context.APP_OPS_SERVICE);
		Method checkOp = PermissionUtils.getDeclaredMethod(AppOpsManager.class, "checkOp"
				, new Class[]{int.class, int.class, String.class});
		try {
			int mode = (Integer) PermissionUtils.invokeMethod(manager
					, checkOp, op, Binder.getCallingUid(), BuildConfig.APPLICATION_ID);
			Log.d("hjy", "Check op result: " + mode
					                + "; 0--allowed, 1--ignored, 2--errored, 3--default");
			if (AppOpsManager.MODE_ALLOWED == mode) {
				return true;
			} else {
				return AppOpsManager.MODE_DEFAULT == mode
						       && Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1;
			}
		} catch (Exception e) {
			Log.w("hjy", "checkOp failed", e);
		}
		return false;
	}
	
	public static Method getDeclaredMethod(
			Class<?> cls, String methodName, Class<?> paramTypes[]) {
		Method m = null;
		if (methodName != null && methodName.length() > 0) {
			try {
				m = null != paramTypes
						    ? cls.getDeclaredMethod(methodName, paramTypes)
						    : cls.getDeclaredMethod(methodName);
			} catch (Throwable e) {
				Log.w("hjy", "exception when getting method "
						                + methodName + ": " + e.getMessage());
			}
		}
		return m;
	}
	
	public static Object invokeMethod(Object object, Method m, Object... args) {
		Object rc = null;
		try {
			boolean acc = m.isAccessible();
			if (!acc) {
				m.setAccessible(true);
			}
			rc = m.invoke(object, args);
			if (!acc) {
				m.setAccessible(false);
			}
		} catch (Throwable e) {
			Log.w("hjy", "exception", e);
		}
		return rc;
	}
	
	/**
	 * 是否通过手动添加悬浮窗来判断 overlay 权限
	 *
	 * @return
	 */
	public static boolean isNeedAddFloatingWindowViewChecker() {
		return !DeviceInfoUtils.isOppo() && !PermissionHuaWeiUtils.checkIsHuaweiRom()
				       && !DeviceUtils.isASUS() && !DeviceUtils.isMiuiRom() && !DeviceUtils.is360();
	}
	
	/**
	 * 是否需要自动检测逻辑: 悬浮窗 + add view to window
	 *
	 * @return
	 */
	public static boolean isNeedAutoCheckerLogic() {
		return !DeviceInfoUtils.isOppo() && !DeviceUtils.isHuaWei() && !DeviceUtils.isASUS();
	}
	
	/**
	 * 是否需要延迟检测悬浮窗权限
	 *
	 * @return
	 */
	public static boolean isNeedDealyCheckOverlayPermission() {
		return DeviceUtils.isHuaWei() || DeviceUtils.isMiuiRom() || DeviceUtils.isASUS();
	}
	
	/**
	 * 检查是否有“查看最近使用的应用”的权限
	 */
	@TargetApi(M)
	public static boolean isPermissionUsageStats(Context context) {
		AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
		if (appOps == null) {
			return false;
		}
		int granted = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), context.getPackageName());
		return granted == AppOpsManager.MODE_ALLOWED;
	}
	
	private static boolean isPermission(Context context, String permission) {
		int granted = ContextCompat.checkSelfPermission(context, permission);
		return granted == PackageManager.PERMISSION_GRANTED;
	}
	
	/**
	 * 检查录屏权限
	 */
	public static boolean hasPermissionRecordAudio(Context context) {
		return isPermission(context, Manifest.permission.RECORD_AUDIO);
	}
	
	/**
	 * 检查Usage acess权限
	 */
	public static boolean hasPermissionUsageAcess(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
			AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
			int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
					applicationInfo.uid, applicationInfo.packageName);
			return (mode == AppOpsManager.MODE_ALLOWED);
		} catch (Exception e) {
			return false;
		}
	}
	
}
