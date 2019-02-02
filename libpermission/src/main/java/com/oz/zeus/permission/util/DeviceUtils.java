package com.oz.zeus.permission.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;


public class DeviceUtils {
    private static final String TAG = "DeviceUtils";

    private static final String PRODUCT = Build.PRODUCT.toLowerCase();

    private static final String MODEL = Build.MODEL.toLowerCase();

    private static final String MANUFACTURER = Build.MANUFACTURER.toLowerCase();

    private static final String DISPLAY = Build.DISPLAY.toLowerCase();

    private DeviceUtils() {
    }

    public static String getSystemProperty(String name) {
        try {
            Method m = Class.forName(
                    "android.os.SystemProperties").getMethod("get", String.class);
            return (String) m.invoke(null, name);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * 获取手机型号
     * @return
     */
    public static String getModel(){
        return MODEL;
    }

    public static boolean isW2013() {
        return "samsung".equals(MANUFACTURER) && "sch-w2013".equals(MODEL);
    }

    public static boolean isMeizu() {
        return "meizu".equals(MANUFACTURER);
    }

    public static boolean isMeizuM9() {
        return PRODUCT.contains("meizu_m9") && MODEL.contains("m9");
    }

    public static boolean isMeizuMX() {
        return PRODUCT.contains("meizu_mx");
    }

    public static boolean isMeizuMX2() {
        return PRODUCT.contains("meizu_mx2");
    }

    public static boolean isMeizuMX3() {
        return PRODUCT.contains("meizu_mx3");
    }

    public static boolean isMeizuMX4() {
        return PRODUCT.contains("meizu_mx4");
    }

    public static boolean isMeizuMXs() {
        return PRODUCT.startsWith("meizu_mx");
    }

    public static boolean isMeizuNote2() {
        return MANUFACTURER.startsWith("meizu") && PRODUCT.contains("m2 note");
    }

    public static boolean isTcl() {
        return MANUFACTURER.equalsIgnoreCase("tcl");
    }

    public static boolean isHtcDevice() {
        return MODEL.contains("htc") || MODEL.contains("desire");
    }

    public static boolean isLephoneDevice() {
        return PRODUCT.contains("lephone");
    }

    public static boolean isZTEU880() {
        return "zte".equals(MANUFACTURER) && MODEL.contains("blade");
    }

    /**
     * 制造商：ZTE
     * 型号：ZTE-U V880
     *
     * @return
     */
    public static boolean isZTEUV880() {
        return "zte".equals(MANUFACTURER) && MODEL.contains("zte-u v880");
    }

    public static boolean isZTEU985() {
        return "zte".equals(MANUFACTURER) && MODEL.contains("zte u985");
    }

    public static boolean isHTCHD2() {
        return "htc".equals(MANUFACTURER) && MODEL.contains("hd2");
    }

    public static boolean isHTCOneX() {
        return "htc".equals(MANUFACTURER) && MODEL.contains("htc one x");
    }

    public static boolean isHTCOne() {
        return "htc".equals(MANUFACTURER) && MODEL.contains("htc 802w");
    }

    public static boolean isHTCX710S() {
        return "htc".equals(MANUFACTURER) && MODEL.contains("velocity 4g x710s");
    }

    public static boolean isHTC609D() {
        return "htc".equals(MANUFACTURER) && MODEL.contains("htc 609d");
    }

    public static boolean isHtcM7() {
        return MODEL.contains("htc 801e");
    }

    public static boolean isI9100() {
        return "samsung".equals(MANUFACTURER) && "gt-i9100".equals(MODEL);
    }

    public static boolean isMiOne() {
        return MODEL.startsWith("mi-one");
    }

    public static boolean isMiTwo() {
        return MODEL.startsWith("mi 2");
    }

    public static boolean isMiThree() {
        return MODEL.startsWith("mi 3");
    }

    public static boolean isHongMi() {
        return "xiaomi".equals(MANUFACTURER) && Build.DEVICE.startsWith("HM");
    }

    public static boolean isXiaomi() {
        return "xiaomi".equals(MANUFACTURER);
    }

    public static boolean isGtS5830() {
        return MODEL.equalsIgnoreCase("gt-s5830");
    }

    public static boolean isGtS5830i() {
        return MODEL.equalsIgnoreCase("gt-s5830i");
    }

    public static boolean isGTP1000() {
        return MODEL.equalsIgnoreCase("gt-p1000");
    }

    public static boolean isMb525() {
        return MODEL.startsWith("mb525");
    }

    public static boolean isMe525() {
        return MODEL.startsWith("me525");
    }

    public static boolean isMb526() {
        return MODEL.startsWith("mb526");
    }

    public static boolean isMe526() {
        return MODEL.startsWith("me526");
    }

    public static boolean isMe860() {
        return MODEL.startsWith("me860");
    }

    public static boolean isMe865() {
        return MODEL.startsWith("me865");
    }

    public static boolean isXT882() {
        return MODEL.startsWith("xt882");
    }

    public static boolean isYulong() {
        return MANUFACTURER.equalsIgnoreCase("yulong");
    }

    public static boolean isKindleFire() {
        return MODEL.contains("kindle fire");
    }

    public static boolean isLGP970() {
        return MODEL.startsWith("lg-p970");
    }

    public static boolean isU8800() {
        return MODEL.startsWith("u8800");
    }

    public static boolean isU9200() {
        return MODEL.startsWith("u9200");
    }

    public static boolean isC8650E() {
        return PRODUCT.equalsIgnoreCase("c8650e") && MANUFACTURER.equalsIgnoreCase("huawei");
    }

    public static boolean isMt15i() {
        return MODEL.startsWith("mt15i");
    }

    public static boolean isSonyE15i() {
        return MODEL.startsWith("e15i");
    }

    public static boolean isSonyLT29i() {
        return MODEL.startsWith("lt29i");
    }

    public static boolean isSonyST18i() {
        return MODEL.startsWith("st18i");
    }

    public static boolean isSonyC2105() {
        return "sony".equals(MANUFACTURER) && MODEL.startsWith("c2105");
    }

    public static boolean isDEOVOV5() {
        return MODEL.equalsIgnoreCase("deovo v5");
    }

    public static boolean isXT702() {
        return MODEL.startsWith("xt702");
    }

    public static boolean isLenovoA390t() {
        return MODEL.equalsIgnoreCase("Lenovo A390t");
    }

    public static boolean isLenovoK860i() {
        return MODEL.equalsIgnoreCase("lenovo k860i");
    }

    public static boolean isLenovoA850() {
        return MODEL.contains("lenovo a850");
    }

    public static boolean isLenovoP770() {
        return MODEL.equalsIgnoreCase("lenovo p770");
    }

    public static boolean isLenovoA789() {
        return MODEL.equalsIgnoreCase("lenovo a789");
    }

    public static boolean isHongZ() {
        return MODEL.equalsIgnoreCase("z8050") && MANUFACTURER.equalsIgnoreCase("cellon");
    }

    public static boolean isVivoX3t() {
        return MODEL.equalsIgnoreCase("vivo x3t");
    }

    private static String sName;
    private static String sVersion;

//    public static String getName() {
//        if (sName == null) {
//            check("");
//        }
//        return sName;
//    }
//
//    public static String getVersion() {
//        if (sVersion == null) {
//            check("");
//        }
//        return sVersion;
//    }


    public static boolean isVivo() {
        Log.d(TAG, "isVivo: ?" + check("VIVO"));
        return check("VIVO");
    }

    public static boolean check(String rom) {
        if (sName != null) {
            return sName.equals(rom);
        }

        if (!TextUtils.isEmpty(sVersion = getProp("ro.vivo.os.version"))) {
            sName = "VIVO";
        } else {
            sVersion = Build.UNKNOWN;
            sName = Build.MANUFACTURER.toUpperCase();
        }
        return sName.equals(rom);
    }


    public static String getProp(String name) {
        String line = null;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + name);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            Log.e(TAG, "Unable to read prop " + name, ex);
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }


    public static boolean isOnePlus() {
        return "oneplus".equals(PRODUCT);
//        return MODEL.equalsIgnoreCase("a0001") && MANUFACTURER.equalsIgnoreCase("oneplus");
    }

    /**
     * 判断是否 Android 4.1
     *
     * @return
     */
    public static boolean isApiLevel16() {
        return Build.VERSION.SDK_INT >= 16;
    }
    
    /**
     * 判断是否 Android 8.0
     *
     * @return
     */
    public static boolean isApiLevel26() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    public static boolean isIceCreamSandwich() {
        return Build.VERSION.SDK_INT >= 14;
    }

    public static boolean isHoneycomb() {
        return Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT < 14;
    }

    /**
     * if android sdk is lower than 3.0
     *
     * @return
     */
    public static boolean isBeforeHoneycomb() {
        return Build.VERSION.SDK_INT < 11;
    }

    public static boolean isHtcG7() {
        return "htc".equals(MANUFACTURER) && "htc desire".equals(MODEL);
    }

    public static boolean isJellyBean() {
        return Build.VERSION.SDK_INT >= 16;
    }

    /**
     * whether the folder 'res/[drawable|values|layout|...]-sw&lt;N&gt;dp' works
     */
    public static boolean isSWNDPValuesSupported() {
        return Build.VERSION.SDK_INT >= 13;
    }

    public static boolean isSendBroadcastDirectlySupported() {
        return Build.VERSION.SDK_INT < 12;
    }

    public static boolean isLockScreenSupported() {
        return !isKindleFire();
    }


    /**
     * 三星I909
     *
     * @return
     */
    public static boolean isI909() {
        return "samsung".equals(MANUFACTURER) && "sch-i909".equals(PRODUCT);
    }

    /**
     * 三星S3(I9300)
     * MANUFACTURER: samsung, PRODUCT: m0xx ID:IMM76D, brand:samsung, display:IMM76D.I9300XXBLG1, MODEL: GT-I9300, screen: 720*1280 DPI:320
     *
     * @return
     */
    public static boolean isS3() {
        return "samsung".equals(MANUFACTURER) && "gt-i9300".equals(MODEL);
    }

    /**
     * 三星S4(I9500)
     * MANUFACTURER: samsung, PRODUCT: ja3gxx ID:IMM76D, display:jdq39.i9500xxuamdk, MODEL: gt-i9500
     *
     * @return
     */
    public static boolean isS4() {
        return "samsung".equals(MANUFACTURER) && (MODEL.contains("sm-g950"));
    }

    /**
     * 三星S5(SM-G9006V)
     * MANUFACTURER: samsung, PRODUCT: kltezn ID:KOT49H, display:KOT49H.G9006VZNU1ANF1, MODEL: SM-G9006V
     *
     * @return
     */
    public static boolean isS5() {
        return "samsung".equals(MANUFACTURER) && (MODEL.contains("sm-g900"));
    }
    /**
     * 三星S8
     *
     * @return
     */
    public static boolean isS8() {
        return "samsung".equals(MANUFACTURER) && (MODEL.contains("sm-g9500"));
    }
    /**
     * 三星note港货N7000
     * MANUFACTURER: samsung, PRODUCT: GT-N7000 ID:JZO54K, brand:samsung, display:JZO54K.N7000ZSLO2, MODEL: GT-N7000, screen: 800*1280 DPI:320
     */
    public static boolean isNote() {
        return "samsung".equals(MANUFACTURER) && (MODEL.contains("gt-n7000"));
    }

    /**
     * 三星note2 MANUFACTURER: samsung, PRODUCT: t03gzm ID:JZO54K, brand:samsung, display:JZO54K.N7108ZMDMF1, MODEL: GT-N7108, screen: 720*1280 DPI:320
     *
     * @return
     */
    public static boolean isNote2() {
        return "samsung".equals(MANUFACTURER) && "gt-n7108".equals(MODEL);
    }

    /**
     * 三星note3 12-12 21:28:31.082: MANUFACTURER: samsung, PRODUCT: h3gzc ID:JSS15J, brand:samsung, display:JSS15J.N9006ZCUBMJ3, MODEL: SM-N9006, screen: 1080*1920 DPI:480
     *
     * @return
     */
    public static boolean isNote3() {
        return "samsung".equals(MANUFACTURER) && (MODEL.contains("sm-n900"));
    }

    public static boolean isNote3_9006() {
        return "samsung".equals(MANUFACTURER) && ("sm-n9006".equals(MODEL));
    }

    public static boolean is360() {
        return "360".equals(MANUFACTURER);
    }

    public static boolean isMiuiRom() {
        return DISPLAY.contains("miui")
                || DISPLAY.contains("mione")
                || !TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name"));
    }

    public static boolean isAliYunOs() {
        return !TextUtils.isEmpty(getSystemProperty("ro.yunos.version"));
    }

    public static boolean isOppoColorOs() {
        return !TextUtils.isEmpty(getSystemProperty("ro.build.version.opporom"));
    }

    /**
     * 小米V5.V6
     * <p>
     * MANUFACTURER: Xiaomi, PRODUCT: aries ID:JRO03L, brand:Xiaomi, display:JRO03L, MODEL: MI 2, screen: 720*1280 DPI:320
     * CODENAME = REL,  INCREMENTAL = 3.4.5, RELEASE =4.1.1, SDK = 16
     * <strong>不是很确定此函数的判断依据是否准确，未来如果有更靠谱的判断标准则再修正</strong>
     * <p/>
     * 已改正，方法来源：MIUI论坛，<MIUI论坛-MIUI专区-应用开发者-小米应用开发者文档>
     * 如有错误，可从论坛参考
     * </p>
     *
     * @return
     */
    public static boolean isMiuiV5OrV6() {
        String miuiVersionName = getSystemProperty("ro.miui.ui.version.name");
        return null != miuiVersionName
                && (miuiVersionName.equalsIgnoreCase("V5")
                || miuiVersionName.equalsIgnoreCase("V6"));
    }

    public static boolean isMiuiV5() {
        String miuiVersionName = getSystemProperty("ro.miui.ui.version.name");
        return null != miuiVersionName && miuiVersionName.equalsIgnoreCase("V5");
    }

    public static boolean isMiuiV6() {
        String miuiVersionName = getSystemProperty("ro.miui.ui.version.name");
        return null != miuiVersionName && miuiVersionName.equalsIgnoreCase("V6");
    }

   /* public static boolean isMiuiV6OrLater() {
        String miuiVersionName = getSystemProperty("ro.miui.ui.version.name");
        if (TextUtils.isEmpty(miuiVersionName)){
            return false;
        }
        miuiVersionName = miuiVersionName.substring(1);
        return StringUtils.parseInt(miuiVersionName) >= 6;
    }*/


   /* public static String getDeviceInfoString() {
        try {
            Context context = BaseApplication.getApp();
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            return "MANUFACTURER:" + Build.MANUFACTURER + ", PRODUCT:" + Build.PRODUCT + "， ID:"
                    + Build.ID + ", brand:" + Build.BRAND + ", display:" + Build.DISPLAY + ", MODEL:" + Build.MODEL
                    + ", DPI:" + DeviceInfoUtils.getDpi(context)
                    + ", SDK:" + Build.VERSION.SDK_INT
                    + ", screenWidth:" + displayMetrics.widthPixels + ", screenHeight:" + displayMetrics.heightPixels;
        } catch (Exception ignored) {
            return "UNKNOWN";
        }
    }*/

    /**
     * 酷派手机
     */
//    public static boolean isCoolpad5Series() {
//        return getDeviceInfoString().contains("coolpad")
//                || getDeviceInfoString().contains("yulong")
//                && PRODUCT.matches("(?:coolpad|yulong)?5[0-9]+");
//    }

    /**
     * 特工机 deovo V5
     * MANUFACTURER: NVIDIA, PRODUCT: kai ID:IML74K, brand:generic, display:IML74K.V03.023.1992-user, MODEL: deovo V5, screen: 720*1184 DPI:320
     *
     * @return
     */
    public static boolean isDeovoV5() {
        return MODEL.contains("deovo v5");
    }

    /**
     * 特工机 BOVO
     * MANUFACTURER: BOVO, PRODUCT: full_blaze ID:IMM76D, brand:BOVO, display:IMM76D, MODEL: S-F16, screen: 720*1184 DPI:320
     *
     * @return
     */
    public static boolean isBOVO() {
        return MANUFACTURER.equals("bovo") && MODEL.equals("s-f16");
    }

    /**
     * oppo find 5(also named X909)
     *
     * @return
     */
    public static boolean isOppoFind5() {
        return MANUFACTURER.equals("oppo") && MODEL.contains("x909");
    }

    /**
     * oppo find 7(also named X9007)
     *
     * @return
     */
    public static boolean isOppoFind7() {
        return MANUFACTURER.equals("oppo") && MODEL.contains("x9007");
    }

    /**
     * oppo R8007
     *
     * @return
     */
    public static boolean isOppoR8007() {
        return MANUFACTURER.equals("oppo") && MODEL.contains("r8007");
    }

    public static boolean isOppo() {
        return MANUFACTURER.equals("oppo");
    }
    
    /**
     * oppo R8007
     *
     * @return
     */
    public static boolean isOppoR9s() {
        return Build.MANUFACTURER.toLowerCase().equals("oppo") && Build.MODEL.toLowerCase().contains("r9s");
    }

    //LGE VS987
    public static boolean isLGEVs987() {
        return MANUFACTURER.equals("lge") && MODEL.contains("vs987");
    }

    public static boolean isLG() {
        return MANUFACTURER.equals("lge");
    }

    /**
     * 某些机型的 PackageManager.resolveActivity 以及 PackageManager.queryIntentActivities 返回为空
     * 此处对这些机型做特殊处理
     *
     * @param action
     * @param uri
     * @return
     */
    public static ComponentName resolveActivity(String action, String uri) {
        if (isZTEUV880()) {
            if (Intent.ACTION_VIEW.equals(action)
                    && "content://com.android.contacts/contacts".equals(uri)) {
                return new ComponentName(
                        "com.android.contacts"
                        , "com.android.contacts.DialtactsContactsEntryActivity");
            }
        }
        return null;
    }

    public static boolean isGalaxyNexusAndApiLevel16() {
        return MODEL.equalsIgnoreCase("Galaxy Nexus") && Build.VERSION.SDK_INT == 16;
    }

    public static boolean isHuaWei() {
        return MANUFACTURER.equalsIgnoreCase("huawei");
    }

    /*public static boolean huaWeiSystemManagerExists(Context context) {
        return null != AppUtils.getPackageInfo(context, "com.huawei.systemmanager");
    }

    public static boolean isEmui30() {
        String version = getSystemProperty("persist.sys.launcher.emuiver");
        return Utils.equals(version, "EmotionUI_3.0");
    }

    public static boolean isEmui23() {
        return Utils.equals(getSystemProperty("ro.build.version.emui"), "EmotionUI_2.3")
                || Build.DISPLAY.startsWith("EMUI2.3");
    }

    public static boolean isEmui31() {
        return Utils.equals(getSystemProperty("ro.build.version.emui"), "EmotionUI_3.1")
                || Build.DISPLAY.startsWith("EMUI3.1");
    }*/

    public static boolean isHuaWeiP6() {
        return MANUFACTURER.equalsIgnoreCase("huawei") && MODEL.contains("p6-u06");
    }

    public static boolean isHuaWeiG700() {
        return MANUFACTURER.equalsIgnoreCase("huawei") && MODEL.contains("g700-t00");
    }

    public static boolean isHuaWei8500() {
        return MANUFACTURER.equalsIgnoreCase("huawei") && MODEL.contains("c8500");
    }

    public static boolean isHuaWeiRongYao() {
        return MANUFACTURER.equalsIgnoreCase("huawei") && MODEL.contains("u9508");
    }

    public static boolean isHuaWeiRongYao3C() {
        return MANUFACTURER.equalsIgnoreCase("huawei") && (MODEL.contains("h30-t10")
                || MODEL.contains("h30-t00"));
    }

    public static boolean isHuaWeiC8813Q() {
        return MANUFACTURER.equalsIgnoreCase("huawei") && MODEL.contains("c8813q");
    }

    public static boolean isHuaWeiMT7() {
        return MANUFACTURER.equalsIgnoreCase("huawei") && MODEL.contains("mt7-tl00");
    }

    public static boolean isNexus5() {
        return MODEL.contains("nexus 5");
    }

    /**
     * 4.2
     *
     * @return
     */
    public static boolean isAfterApiLevel17() {
        return Build.VERSION.SDK_INT >= 17;
    }

    /**
     * 4.3
     *
     * @return
     */
    public static boolean isAfterApiLevel18() {
        return Build.VERSION.SDK_INT >= 18;
    }

    /**
     * 4.4
     *
     * @return
     */
    public static boolean isAfterApiLevel19() {
        return Build.VERSION.SDK_INT >= 19;
    }

    /**
     * 5.0
     *
     * @return
     */
    public static boolean isAfterApiLevel21() {
        return Build.VERSION.SDK_INT >= 21;
    }

    /**
     * 夏新大V
     *
     * @return
     */
    public static boolean isBigV() {
        return ("amoi n826".equalsIgnoreCase(MODEL)
                || "amoi n821".equalsIgnoreCase(MODEL)
                || "amoi n820".equalsIgnoreCase(MODEL));
    }

    public static boolean isCoolpad5950() {
        return MANUFACTURER.equals("yulong") && PRODUCT.contains("5950");
    }

    public static boolean isCoolpad7298A() {
        return MANUFACTURER.equals("coolpad") && PRODUCT.contains("7298a");
    }

    public static boolean isCoolpad8297() {
        return MANUFACTURER.equals("coolpad") && PRODUCT.contains("8297");
    }

    public static boolean isCoolpad8675() {
        return MANUFACTURER.equals("yulong") && PRODUCT.contains("8675");
    }

    public static boolean isCoolpad7295() {
        return MANUFACTURER.equals("coolpad") && MODEL.contains("7295");
    }

    public static boolean isSamsungButNotGalaxy() {
        return MANUFACTURER.contains("samsung") && !MODEL.equalsIgnoreCase("Galaxy Nexus");
    }

    public static boolean isHuaWeiRio() {
        return MANUFACTURER.equals("huawei") && PRODUCT.equals("rio-al00");
    }

    // 华为荣耀7
    public static boolean isHuaWeiRongYao7() {
        return MANUFACTURER.equals("huawei") && PRODUCT.equals("plk-al10");
    }

    public static boolean isSamsung() {
        return MANUFACTURER.contains("samsung");
    }
    public static boolean isASUS() {
        return MANUFACTURER.contains("asus");
    }

    public static String getAndroidId(Context context) {
        try {
            String id = Settings.Secure.getString(
                    context.getContentResolver(), Settings.Secure.ANDROID_ID);
            return id != null ? id : "";
        } catch (Exception ignored) {
        }
        return "";
    }

    /**
     * 判断是否是平板
     *
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static boolean hasLightSensor(Context context) {
        if (isLGP970()) {
            return true;
        }

        SensorManager sensorService = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);

        if (sensorService != null) {
            List<Sensor> lightSensors = sensorService.getSensorList(Sensor.TYPE_LIGHT);
            return lightSensors != null && !lightSensors.isEmpty();
        }
        return false;
    }

    public static boolean isMotoG() {
        return Build.MODEL.startsWith("XT103") && Build.MANUFACTURER.equals("motorola");
    }

    public static boolean isMotoQuard() {
        return Build.MODEL.equals("XT1254") && Build.MANUFACTURER.equals("motorola");
    }

    public static String getCountryCode(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        return locale.getCountry();
    }

}
