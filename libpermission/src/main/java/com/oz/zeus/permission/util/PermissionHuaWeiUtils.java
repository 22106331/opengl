package com.oz.zeus.permission.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;


/**
 * Created by j-songsaihua-ol on 2018/7/24.
 */

public class PermissionHuaWeiUtils {
    static final String TAG  ="PermissionHuaWeiUtils";
    public static boolean checkIsHuaweiRom() {
        return Build.MANUFACTURER.toUpperCase().contains("HUAWEI");
    }

    //获取华为emui的版本
    private static double getEmuiVersion() {
        try {
            String emuiVersion = PermissionMIUIUtils.getSystemProperty("ro.build.version.emui");
            String version = emuiVersion.substring(emuiVersion.indexOf("_") + 1);
            return Double.parseDouble(version);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 4.0;
    }

    public static boolean requestHuaWeiPermission(Activity context,int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            return PermissionUtils.requestFloatWindoWSDKV6(context,requestCode);
        } else {
            try {
                Intent intent = new Intent();
                ////悬浮窗管理页面
                ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity");
                intent.setComponent(comp);
                if (getEmuiVersion() == 3.1) {
                    //emui 3.1 的适配
                    context.startActivityForResult(intent,requestCode);
                } else {
                    //emui 3.0 的适配
                    //悬浮窗管理页面
                    comp = new ComponentName("com.huawei.systemmanager", "com.huawei.notificationmanager.ui.NotificationManagmentActivity");
                    intent.setComponent(comp);
                    context.startActivityForResult(intent,requestCode);
                }
            } catch (SecurityException e) {
                e.printStackTrace();
                Intent intent = new Intent();
                ComponentName comp = new ComponentName("com.huawei.systemmanager",
                        "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理，跳转到本app的权限管理页面,这个需要华为接口权限，未解决
                intent.setComponent(comp);
                context.startActivityForResult(intent,requestCode);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                //手机管家版本较低 HUAWEI SC-UL10
                Intent intent = new Intent();
                //权限管理页面 android4.4
                ComponentName comp = new ComponentName("com.Android.settings", "com.android.settings.permission.TabItem");
                intent.setComponent(comp);
                context.startActivityForResult(intent,requestCode);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Request permission error!", Toast.LENGTH_SHORT).show();
                 return false;
            }
        }
        return true;

    }
    
    
    /**
     * 检测 Huawei 悬浮窗权限
     */
    public static boolean checkFloatWindowPermission(Context context) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            return checkOp(context, 24); //OP_SYSTEM_ALERT_WINDOW = 24;
        }
        return true;
    }
    
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static boolean checkOp(Context context, int op) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Class clazz = AppOpsManager.class;
                Method method = clazz.getDeclaredMethod("checkOp", int.class, int.class, String.class);
                return AppOpsManager.MODE_ALLOWED == (int) method.invoke(manager, op, Binder.getCallingUid(), context.getPackageName());
            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        } else {
            Log.e(TAG, "Below API 19 cannot invoke!");
        }
        return false;
    }
    
}
