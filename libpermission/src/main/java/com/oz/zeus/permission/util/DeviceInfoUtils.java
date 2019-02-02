package com.oz.zeus.permission.util;

import android.os.Build;

public class DeviceInfoUtils {

    public static boolean isSDK_M() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.M;
    }

    public static String getManufacturer() {
        try {
            return Build.MANUFACTURER;
        } catch (Throwable e) {
            return "";
        }
    }

    public static boolean isSamsung() {
        return getManufacturer().toLowerCase().contains("samsung");
    }
    
    public static boolean isOppo() {
        return getManufacturer().toLowerCase().equals("oppo");
    }
    
    /**
     * oppo R8007
     *
     * @return
     */
    public static boolean isOppoR9s() {
        return Build.MANUFACTURER.toLowerCase().equals("oppo") && Build.MODEL.toLowerCase().contains("r9s");
    }
    
    
    public static boolean isLGEVs987() {
        return Build.MANUFACTURER.toLowerCase().equals("lge") && Build.MODEL.toLowerCase().contains("vs987");
    }

    public static boolean isAfterAndroid_8_0() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

}
