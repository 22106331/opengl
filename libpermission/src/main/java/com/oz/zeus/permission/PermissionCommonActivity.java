package com.oz.zeus.permission;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.Arrays;

public class PermissionCommonActivity extends PermissionBaseActivity {

    private static final String TAG = PermissionCommonActivity.class.getSimpleName();
    private static final int REQUEST_COMMON_PERMISSION = 202;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String permission = getIntent().getStringExtra("permission");
        Log.d(TAG, "onCreate: permission:" + permission);
        if (permission == null || permission.isEmpty()) {
            finishActivity();
            return;
        }
        ActivityCompat.requestPermissions(PermissionCommonActivity.this, new String[]{permission}, REQUEST_COMMON_PERMISSION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "result code: " + resultCode + " requestCode:" + requestCode + " data:" + data);
        if (requestCode == REQUEST_COMMON_PERMISSION) {
            PermissionManager.getInstance().commonPermissionGranted(resultCode == RESULT_OK ? true : false);
            finishActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "result requestCode:" + requestCode + "  permissions:" + Arrays.toString(permissions) + "  grantResults:" + Arrays.toString(grantResults));
        boolean isGranted = false;
        if (requestCode == REQUEST_COMMON_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isGranted = true;
            }
            PermissionManager.getInstance().commonPermissionGranted(isGranted);
        }
        finishActivity();
    }
}
