package com.ffmpeg.recorder;

import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener,View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Camera mCamera;
    private SurfaceTexture mSurfaceTexture;
    private Button startBtn;
    private int preview_w;
    private int preview_h;
    private LinkedBlockingQueue<byte[]> yuvQueue = new LinkedBlockingQueue<>(10);
    private AtomicBoolean isStop = new AtomicBoolean(false);
    private Thread encodeThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        TextureView textureView = findViewById(R.id.textureview);
        textureView.setSurfaceTextureListener(this);
        startBtn = findViewById(R.id.start);
        startBtn.setOnClickListener(this);
        findViewById(R.id.stop).setOnClickListener(this);

    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

        mSurfaceTexture = surface;
        startPreview();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        // Ignored, Camera does all the work for us
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // Invoked every time there's a new Camera preview frame
        //Log.d(TAG, "updated, ts=" + surface.getTimestamp());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        startPreview();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start){
            String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();
            String path = SDCARD_PATH + "/111/1.mp4";
            VideoRecorder.initMediaRecorder(path,preview_w,preview_h,preview_w,preview_h,
                    25,5760000, false, 40000, 44100);
            doEncode();
        }else if (v.getId() == R.id.stop){
            try {
                isStop.set(true);
                if (encodeThread != null) {
                    encodeThread.join(1000);
                    encodeThread.interrupt();
                }
                VideoRecorder.stopRecord();
                VideoRecorder.nativeRelease();
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }

    private void doEncode() {
        encodeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (!isStop.get()) {
                            byte[] buffer = yuvQueue.take();
                            if (buffer != null) {
                                VideoRecorder.encodeYUVFrame(buffer);
//                            isStop.set(true);
                            }
                        }else {
                            break;
                        }
                    }
                }catch (InterruptedException e){

                }


            }
        });
        encodeThread.start();
    }


    private void startPreview() {
        mCamera = Camera.open(1);
        if (mCamera == null) {
            // Seeing this on Nexus 7 2012 -- I guess it wants a rear-facing camera, but
            // there isn't one.  TODO: fix
            throw new RuntimeException("Default camera not available");
        }

        try {
            mCamera.setPreviewTexture(mSurfaceTexture);
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPreviewFormat(ImageFormat.NV21);
            parameters.setPreviewSize(1280,720);
            mCamera.setParameters(parameters);
            Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

            if(display.getRotation() == Surface.ROTATION_0) {
                mCamera.setDisplayOrientation(90);
            }
            if(display.getRotation() == Surface.ROTATION_270) {
                mCamera.setDisplayOrientation(180);
            }
            mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] data, Camera camera) {

                    preview_w =  camera.getParameters().getPreviewSize().width;
                    preview_h = camera.getParameters().getPreviewSize().height;
                    if (isStop.get()) {
                        return;
                    }
                    try {
                        if (yuvQueue.size() > 9) {
                            yuvQueue.take();
                        }
                        Log.e("ffrecorder","yuvQueue.put");
                        yuvQueue.put(data);
                    }catch (InterruptedException e){
                        Log.e("ffrecorder","e：" +e.toString());
                    }

                }
            });
            mCamera.startPreview();
        } catch (IOException ioe) {
            // Something bad happened
            Log.e(TAG,"Exception starting preview", ioe);
        }
    }


}
