package com.oz.zeus.permission;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.oz.zeus.permission.util.DeviceUtils;
import com.oz.zeus.permission.util.PermissionHuaWeiUtils;
import com.oz.zeus.permission.util.PermissionMIUIUtils;
import com.oz.zeus.permission.util.PermissionUtils;

public class PermissionOverlayActivity extends PermissionBaseActivity {
    private static final String TAG = "PermissionOverlayA";

    private static final int REQUEST_PERMISSIONS_OVERLAY = 201;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestFloatingWindowPermission(REQUEST_PERMISSIONS_OVERLAY);
    }

    private void requestFloatingWindowPermission(int requestCode) {
        Log.d(TAG, "requestFloatingWindowPermission");
        if (PermissionMIUIUtils.checkIsMiuiRom()) {
            PermissionMIUIUtils.requestMiUIPermission(this, requestCode);
        } else if (DeviceUtils.isHuaWei()) {
            PermissionHuaWeiUtils.requestHuaWeiPermission(this, requestCode);
        } else {
            PermissionUtils.requestFloatWindoWSDKV6(this, requestCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "result code: " + resultCode + " requstCode:" + requestCode + "  data:" + data);
        if (requestCode == REQUEST_PERMISSIONS_OVERLAY) {
            if (resultCode == RESULT_OK) {
                PermissionManager.getInstance().addPermission(PermissionManager.PERMISSION_OVERLAY);
                Log.d(TAG, "onActivityResult ---GET recorder permissions--Success");

            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "onActivityResult ---CANCEL recorder permissions--Start");
                if (PermissionUtils.isNeedDealyCheckOverlayPermission()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            doCancel();
                        }
                    }, 1000);
                } else {
                    doCancel();
                }
                return;
            }
        }
       finishActivity();
    }

    private void doCancel() {
        if(PermissionManager.getInstance().hasOverlayPermission(this)) {
            PermissionManager.getInstance().addPermission(PermissionManager.PERMISSION_OVERLAY);
        } else {
            PermissionManager.getInstance().cancelPermission(PermissionManager.PERMISSION_OVERLAY);
        }
        finishActivity();
    }
}
