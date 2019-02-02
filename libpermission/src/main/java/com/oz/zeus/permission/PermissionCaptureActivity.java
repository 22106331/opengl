package com.oz.zeus.permission;

import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

public class PermissionCaptureActivity extends PermissionBaseActivity {
    private static final String TAG = "PermissionCapture";
    /**
     * 录屏权限
     */
    private static final int REQUEST_PERMISSIONS_MEDIA_PROJECTION = 200;
    private MediaProjectionManager mMediaProjectionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        Log.d(TAG, "onCreate: ");

        mMediaProjectionManager = (MediaProjectionManager) getApplicationContext().getSystemService(MEDIA_PROJECTION_SERVICE);
        PermissionManager.getInstance().setMediaProjectionManager(mMediaProjectionManager);
        startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(), REQUEST_PERMISSIONS_MEDIA_PROJECTION);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "result code: " + resultCode + " requestCode:" + requestCode);
        if (requestCode == REQUEST_PERMISSIONS_MEDIA_PROJECTION) {
            // NOTE: Should pass this result data into a Service to run ScreenRecorder.
            // The following codes are merely exemplary.
            PermissionManager.getInstance().setResult(resultCode);
            PermissionManager.getInstance().setIntent(data);
            if (resultCode == RESULT_OK) {
                PermissionManager.getInstance().addPermission(PermissionManager.PERMISSION_CAPTURE);

                Log.d(TAG, "onActivityResult ---GET recorder permissions--Success");

            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "onActivityResult ---CANCEL recorder permissions--Start");
                PermissionManager.getInstance().cancelPermission(PermissionManager.PERMISSION_CAPTURE);
            }

        }
        finishActivity();
    }
}
