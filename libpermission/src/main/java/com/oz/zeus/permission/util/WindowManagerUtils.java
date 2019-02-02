package com.oz.zeus.permission.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Surface;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import java.lang.reflect.Method;


/**
 * WindowManager工具类.
 */
public class WindowManagerUtils {
	
	private static final String TAG = WindowManagerUtils.class.getSimpleName();
	
	public static final boolean ATLEAST_LOLLIPOP = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
	public static final boolean ATLEAST_KITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
	public static final boolean ATLEAST_JELLY_BEAN_MR1 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
	
	public static final int DEFAULT_DEVICE_SCREEN_WIDTH = 1080;
	public static final int DEFAULT_DEVICE_SCREEN_HEIGHT = 1920;
	
	private static float sDensityRatio;
	
	public static boolean attachToWindowSafely(
			Context context, View view, WindowManager.LayoutParams lp) {
		try {
			WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			wm.addView(view, lp);
			return true;
		} catch (Exception e) {
			Log.e(TAG, "failed to attach to window, mostly because it is already attached", e);
			return false;
		}
	}
	
	public static boolean detachFromWindowSafely(Context context, View view) {
		try {
			if (view.isShown()) {
				WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
				wm.removeView(view);
			}
			return true;
		} catch (Exception e) {
			Log.e(TAG, "failed to detach from window, mostly because it is already detached", e);
			return false;
		}
	}
	
	public static boolean updateWindowSafely(
			Context context, View view, WindowManager.LayoutParams lp) {
		try {
			WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			if (view.isAttachedToWindow()) {
				wm.updateViewLayout(view, lp);
				return true;
			}
			return false;
		} catch (Exception e) {
			Log.e(TAG, "failed to update window, mostly because it is not attached", e);
			return false;
		}
	}
	
	//有些6.0的机器使用TYPE_TOAST不需要权限
	public static int getWindowType() {
		if (PermissionUtils.isNonFloatPermission()) {
			if (DeviceUtils.isOppoR9s()) {
				return getWindowTypeSomePhone();
			}else if ( !DeviceUtils.isVivo()){
				return getWindowTypeSomePhone();
			}
		}
		return getWindowType2003();
	}
	
	private static int getWindowTypeSomePhone() {
		return WindowManager.LayoutParams.TYPE_TOAST;
	}
	
	public static int getWindowType2003() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			return WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
		}
		return WindowManager.LayoutParams.TYPE_PHONE;
	}
	
	public static WindowManager.LayoutParams getLayoutParam(boolean needKeyEvent, int windowType) {
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.format = PixelFormat.RGBA_8888;
		lp.flags = WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
				           | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
		if (!needKeyEvent) {
			lp.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
			lp.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
		}
		//noinspection ResourceType
		lp.type = windowType;
		lp.gravity = Gravity.START | Gravity.TOP;
		return lp;
	}
	
	public static WindowManager.LayoutParams getWrapContentLayoutParam(Context context, int windowType) {
		WindowManager.LayoutParams lp = getLayoutParam(false, windowType);
		lp.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
		lp.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
		lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		Log.d(TAG, "getWrapContentLayoutParam: lp.width =" + lp.width + ",lp.height =" + lp.height);
		return lp;
	}
	
	public static String getScreenRotationStrOnPhone(Context context) {
		final Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		switch (display.getRotation()) {
			case Surface.ROTATION_0:
				return "portrait";
			case Surface.ROTATION_90:
				return "Landscape";
			case Surface.ROTATION_180:
				return "Reverse_Portrait";
			case Surface.ROTATION_270:
				return "Reverse_Landscape";
			default:
				return "Portrait";
		}
	}
	
	public static String translateScreenRotationStr(int rotation) {
		switch (rotation) {
			case Surface.ROTATION_0:
				return "Portrait";
			case Surface.ROTATION_90:
				return "Landscape";
			case Surface.ROTATION_180:
				return "Reverse_Portrait";
			case Surface.ROTATION_270:
				return "Reverse_Landscape";
			default:
				return "Portrait";
		}
	}
	
	public static int getScreenRotationOnPhone(Context context) {
		final Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		return display.getRotation();
	}
	
	/*public static int pxFromDp(float dp) {
		return Math.round(dp * getDensityRatio());
	}
	
	public static int dpFromPx(int px) {
		return Math.round(px / getDensityRatio());
	}
	
	public static float getDensityRatio() {
		if (sDensityRatio > 0f) {
			return sDensityRatio;
		}
		Resources resources = BaseApplication.getContext().getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		sDensityRatio = (float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT;
		return sDensityRatio;
	}*/
	
	/**
	 * @return Status bar (top bar) height. Note that this height remains fixed even when status bar is hidden.
	 */
	public static int getStatusBarHeight(Context context) {
		int height = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			height = context.getResources().getDimensionPixelSize(resourceId);
		}
		return height;
	}
	
	public static int getNavigationBarHeight(Context context) {
		if (null == context) {
			return 0;
		}
		if (context instanceof Activity && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			Activity activityContext = (Activity) context;
			DisplayMetrics metrics = new DisplayMetrics();
			activityContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);
			int usableHeight = metrics.heightPixels;
			activityContext.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
			int realHeight = metrics.heightPixels;
			if (realHeight > usableHeight) {
				return realHeight - usableHeight;
			} else {
				return 0;
			}
		}
		
		return getNavigationBarHeightUnconcerned(context);
	}
	
	public static int getNavigationBarHeightUnconcerned(Context context) {
		if (null == context) {
			return 0;
		}
		Resources localResources = context.getResources();
		if (!hasNavBar(context)) {
			return 0;
		}
		int i = localResources.getIdentifier("navigation_bar_height", "dimen", "android");
		if (i > 0) {
			return localResources.getDimensionPixelSize(i);
		}
		i = localResources.getIdentifier("navigation_bar_height_landscape", "dimen", "android");
		if (i > 0) {
			return localResources.getDimensionPixelSize(i);
		}
		return 0;
	}
	
	public static boolean hasNavBar(Context paramContext) {
		boolean bool = true;
		String sNavBarOverride;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			try {
				Object localObject = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", String.class);
				((Method) localObject).setAccessible(true);
				sNavBarOverride = (String) ((Method) localObject).invoke(null, "qemu.hw.mainkeys");
				localObject = paramContext.getResources();
				int i = ((Resources) localObject).getIdentifier("config_showNavigationBar", "bool", "android");
				if (i != 0) {
					bool = ((Resources) localObject).getBoolean(i);
					if ("1".equals(sNavBarOverride)) {
						return false;
					}
				}
			} catch (Throwable localThrowable) {
			}
		}
		
		if (!ViewConfiguration.get(paramContext).hasPermanentMenuKey()) {
			return bool;
		}
		
		return false;
	}
	
	public static void hideNavigationBar(Activity activity) {
		activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
	}
	
	public static void showNavigationBarAndStatusBar(Activity activity) {
		activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
	}
	
}