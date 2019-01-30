package com.opengl.study.example3;
import java.io.IOException;
import java.io.InputStream;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.view.MotionEvent;
import android.opengl.GLES30;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.opengl.study.R;

public class MySurfaceView extends GLSurfaceView
{
	private final float TOUCH_SCALE_FACTOR = 180.0f/320;//�Ƕ����ű���
    private SceneRenderer mRenderer;//������Ⱦ��
	
	private float mPreviousY;//�ϴεĴ���λ��Y����
    private float mPreviousX;//�ϴεĴ���λ��X����
    
    int textureId;//ϵͳ���������id
	
	public MySurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);	//����ʹ��OPENGL ES3.0
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ
    }
	
	//�����¼��ص�����
    @SuppressLint("ClickableViewAccessibility")
	@Override 
    public boolean onTouchEvent(MotionEvent e)	
    {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
            float dy = y - mPreviousY;//���㴥�ر�Yλ��
            float dx = x - mPreviousX;//���㴥�ر�Xλ��
            mRenderer.texRect.yAngle += dx * TOUCH_SCALE_FACTOR;//�������������y����ת�Ƕ�
            mRenderer.texRect.zAngle+= dy * TOUCH_SCALE_FACTOR;//���õ����������z����ת�Ƕ�
        }
        mPreviousY = y;//��¼���ر�λ��
        mPreviousX = x;//��¼���ر�λ��
        return true;
    }

	private class SceneRenderer implements Renderer
    {   
    	Triangle texRect;//���������ζ�������
    	
        public void onDrawFrame(GL10 gl)
        { 
        	//�����Ȼ�������ɫ����
            GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            //��������������
            texRect.drawSelf(textureId);
        }  

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����Ӵ���С��λ�� 
        	GLES30.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            float ratio = (float) width / height;
            //���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 1, 10);
            //���ô˷������������9����λ�þ���
            MatrixState.setCamera(0,0,3,0f,0f,0f,0f,1.0f,0.0f);
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //������Ļ����ɫRGBA
            GLES30.glClearColor(0.5f,0.5f,0.5f, 1.0f);              
            //���������ζԶ��� 
            texRect=new Triangle(MySurfaceView.this);        
            //����ȼ��
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            //��ʼ������
            initTexture();
            //�رձ������   
            GLES30.glDisable(GLES30.GL_CULL_FACE);
        }
    }
	
	public void initTexture()//textureId
	{
		//��������ID
		int[] textures = new int[1];
		GLES30.glGenTextures
		(
				1,          //����������id������
				textures,   //����id������
				0           //ƫ����
		);    
		textureId=textures[0];    
		GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId);
		GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER,GLES30.GL_NEAREST);
		GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_MAG_FILTER,GLES30.GL_LINEAR);
		GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S,GLES30.GL_MINOR_VERSION);
		GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T,GLES30.GL_MINOR_VERSION);
		
		GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_SWIZZLE_R, GLES30.GL_GREEN);
        //ͨ������������ͼƬ===============begin===================
        InputStream is = this.getResources().openRawResource(R.mipmap.wall);
        Bitmap bitmapTmp;
        try 
        {
        	bitmapTmp = BitmapFactory.decodeStream(is);
        } 
        finally 
        {
            try 
            {
                is.close();
            } 
            catch(IOException e) 
            {
                e.printStackTrace();
            }
        }
        //ͨ������������ͼƬ===============end=====================  
        
        //ʵ�ʼ�������
        GLUtils.texImage2D
        (
        		GLES30.GL_TEXTURE_2D,   //��������
        		0, 					  //����Ĳ�Σ�0��ʾ����ͼ��㣬�������Ϊֱ����ͼ
        		bitmapTmp, 			  //����ͼ��
        		0					  //����߿�ߴ�
        );
        bitmapTmp.recycle();//������سɹ����ͷ�����ͼ
	}
}
