package com.oz.zeus.permission.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by j-songsaihua-ol on 2018/7/24.
 */

public class PermissionMIUIUtils {
    private static final String TAG = PermissionMIUIUtils.class.getSimpleName();

    //获取小米 rom miui 版本号，获取失败返回 -1
    public static int getMiuiVersion() {
        String version = getSystemProperty("ro.miui.ui.version.name");
        if (version != null) {
            try {
                return Integer.parseInt(version.substring(1));
            } catch (Exception e) {
                Log.e(TAG, "get miui version code error, version : " + version);
            }
        }
        return -1;
    }

    protected static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            Log.e(TAG, "Unable to read sysprop " + propName, ex);
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Log.e(TAG, "Exception while closing InputStream", e);
                }
            }
        }
        return line;
    }

    public static boolean requestMiUIPermission(Activity context,int requestCode) {
        int versionCode = getMiuiVersion();
        if (versionCode == -1) {//没获取到MIUI版本号失败
            Toast.makeText(context, "Request permission error!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (versionCode == 5) {
            return requestMiUIPermission_v5(context,requestCode);
        } else if (versionCode == 6) {
            return requestMiUIPermission_v6(context, requestCode);
        } else if (versionCode == 7) {
            return requestMiUIPermission_v7(context, requestCode);
        } else if (versionCode == 8) {
            return requestMiUIPermission_v8(context, requestCode);
        } else if (versionCode == 9){
            //MIUI 9.0系统和8.0系统权限时一样的
            return requestMiUIPermission_v8(context, requestCode);
        } else {
            return  PermissionUtils.requestFloatWindoWSDKV6(context,requestCode);
        }
    }

    private static boolean requestMiUIPermission_v5(Activity context,int requestCode) {
        try {
            String packageName = context.getPackageName();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package" , packageName, null);
            intent.setData(uri);
            context.startActivityForResult(intent,requestCode);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Request permission error!", Toast.LENGTH_SHORT).show();
            context.finish();
        }
        return false;
    }

    private static boolean requestMiUIPermission_v6(Activity context,int requestCode) {
        try {
            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra("extra_pkgname", context.getPackageName());
            context.startActivityForResult(intent,requestCode);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Request permission error!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private static boolean requestMiUIPermission_v7(Activity context,int requestCode) {
        try {
            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra("extra_pkgname", context.getPackageName());
            context.startActivityForResult(intent,requestCode);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Request permission error!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private static boolean requestMiUIPermission_v8(Activity context,int requestCode) {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                return PermissionUtils.requestFloatWindoWSDKV6(context,requestCode);
            } else {
                Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
                intent.putExtra("extra_pkgname", context.getPackageName());
                context.startActivityForResult(intent,requestCode);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Request permission error!", Toast.LENGTH_SHORT).show();
            context.finish();
        }
        return false;
    }

    public static boolean checkIsMiuiRom() {
        return !TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name"));
    }

}
