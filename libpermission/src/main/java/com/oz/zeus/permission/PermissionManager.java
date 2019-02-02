package com.oz.zeus.permission;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import com.oz.zeus.permission.util.PermissionUtils;

import java.util.HashSet;
import java.util.Set;

public class PermissionManager {

    public static PermissionManager permissionManager;

    public static final int NO_PERMISSION = 0x0;//0000
    public static final int PERMISSION_CAPTURE = 0x1;//0001
    public static final int PERMISSION_OVERLAY =0x2;//0010

    public static int mCurrentPermisson;

    private int mResultCode;
    private Intent mIntent;

    private MediaProjectionManager mMediaProjectionManager;

    private Set<PermissionCallbacks> captureCallbacksSet = new HashSet<>();
    private Set<PermissionCallbacks> overlayCallbacksSet = new HashSet<>();
    private Set<PermissionCallbacks> commonCallbacksSet = new HashSet<>();
    public static PermissionManager getInstance() {
        if (permissionManager == null) {
            synchronized (PermissionManager.class) {
                if( null == permissionManager ) {
                    permissionManager = new PermissionManager();
                }
            }
        }
        return permissionManager;
    }

    public boolean hasPermission(Context context, String mainfestPermission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return  true;
        }
        return ActivityCompat.checkSelfPermission(context, mainfestPermission) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission(Context context, String mainfestPermission, PermissionCallbacks permissionCallbacks) {
        commonCallbacksSet.add(permissionCallbacks);
        Intent intent = PermissionBaseActivity.newIntent(context,PermissionCommonActivity.class);
        intent.putExtra("permission", mainfestPermission);
        context.startActivity(intent);
    }

    public void commonPermissionGranted(boolean isGranted) {
        loopPermissionCallbacksSet(commonCallbacksSet, isGranted);
    }

    public boolean hasCaptruePerssion() {
        return (mCurrentPermisson & PERMISSION_CAPTURE) != NO_PERMISSION;
    }

    public boolean hasOverlayPermission(Context context) {
        try {
            boolean hasCheckedFloatPermission = ((mCurrentPermisson & PERMISSION_OVERLAY) != NO_PERMISSION);
            return hasCheckedFloatPermission || PermissionUtils.checkFloatWindowPermission(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void requestCapturePermissions(Context context, PermissionCallbacks permissionCallbacks) {
        captureCallbacksSet.add(permissionCallbacks);
        context.startActivity(PermissionBaseActivity.newIntent(context,PermissionCaptureActivity.class));
    }

    public void requestOverlayPermissions(Context context, PermissionCallbacks permissionCallbacks) {
        overlayCallbacksSet.add(permissionCallbacks);
        context.startActivity(PermissionBaseActivity.newIntent(context, PermissionOverlayActivity.class));
    }

    public void addPermission(int permission) {
        if(permission == PERMISSION_CAPTURE) {
            loopPermissionCallbacksSet(captureCallbacksSet, true);
        } else if (permission == PERMISSION_OVERLAY) {
            loopPermissionCallbacksSet(overlayCallbacksSet, true);
        }
        mCurrentPermisson |= permission;
    }

    public void loopPermissionCallbacksSet(Set<PermissionCallbacks> permissionCallbacksSet, boolean isGranted) {
        if(permissionCallbacksSet.size() > 0) {
            for (PermissionCallbacks permissionCallbacks:
                    permissionCallbacksSet) {
                if(isGranted) {
                    permissionCallbacks.onPermissionsGranted();
                } else {
                    permissionCallbacks.onPermissionsDenied();
                }
            }
            permissionCallbacksSet.clear();
        }
    }

    public void cancelPermission(int permission) {
        if(permission == PERMISSION_CAPTURE) {
            loopPermissionCallbacksSet(captureCallbacksSet, false);
        } else if (permission == PERMISSION_OVERLAY) {
            loopPermissionCallbacksSet(overlayCallbacksSet, false);
        }
        mCurrentPermisson &= (~permission);
    }

    public interface PermissionCallbacks {

        void onPermissionsGranted();

        void onPermissionsDenied();
    }


    public void setResult(int resultCode) {
        this.mResultCode = resultCode;
    }

    public int getResult() {
        return mResultCode;
    }

    public void setIntent(Intent intent) {
        this.mIntent = intent;
    }

    public Intent getIntent() {
        return mIntent;
    }

    public MediaProjectionManager getMediaProjectionManager() {
        return mMediaProjectionManager;
    }

    public void setMediaProjectionManager (MediaProjectionManager mediaProjectionManager) {
        this.mMediaProjectionManager = mediaProjectionManager;
    }
}
