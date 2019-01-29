package com.opengl.study;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.opengl.study.example2.MySurfaceView;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private MySurfaceView mGLSurfaceView;
    @Override
    public void onCreate(Bundle savedInstanceState)//继承Activity后重写的方法
    {
        super.onCreate(savedInstanceState);//调用父类
        //设置为竖屏模式
        //设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置为横屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //初始化GLSurfaceView
        mGLSurfaceView = new MySurfaceView(this);

        //切换到主界面
        setContentView(mGLSurfaceView);

        mGLSurfaceView.requestFocus();//获取焦点
        mGLSurfaceView.setFocusableInTouchMode(true);//设置为可
    }
    @Override
    public void onResume()//继承Activity后重写的onResume方法
    {
        super.onResume();
        mGLSurfaceView.onResume();//通过MyTDView类的对象调用onResume方法
    }
    @Override
    public void onPause()//继承Activity后重写的onPause方法
    {
        super.onPause();
        mGLSurfaceView.onPause();//通过MyTDView类的对象调用onPause方法
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
