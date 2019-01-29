package com.opengl.study.example1;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.annotation.SuppressLint;
import android.opengl.GLES30;
import android.opengl.Matrix;

import com.opengl.study.ShaderUtil;

public class Triangle
{
	public static float[] mProjMatrix = new float[16];//4x4 ͶӰ����
    public static float[] mVMatrix = new float[16];//�����λ�ó���Ĳ�������
    public static float[] mMVPMatrix;//��������õ��ܱ任����
	
	int mProgram;//�Զ�����Ⱦ���߳���id
    int muMVPMatrixHandle;//�ܱ任��������
    int maPositionHandle; //����λ����������
    int maColorHandle; //������ɫ��������
    String mVertexShader;//������ɫ������ű�
    String mFragmentShader;//ƬԪ��ɫ������ű�
    static float[] mMMatrix = new float[16];//����������ƶ���ת���󣬰�����ת��ƽ�ơ�����
	
	FloatBuffer   mVertexBuffer;//�����������ݻ���
	FloatBuffer   mColorBuffer;//������ɫ���ݻ���
    int vCount=0;	
    float xAngle=0;//��x����ת�ĽǶ�
    public Triangle(MyTDView mv)
    {    	
    	//���ó�ʼ���������ݵ�initVertexData����
    	initVertexData();
    	//���ó�ʼ����ɫ����intShader����
    	initShader(mv);
    }
   
    public void initVertexData()//��ʼ���������ݵķ���
    {
    	//�����������ݵĳ�ʼ��
        vCount=3;  
        final float UNIT_SIZE=0.2f;
        float vertices[]=new float[]//������������
        {
        	-4*UNIT_SIZE,0,0,
        	0,-4*UNIT_SIZE,0,
        	4*UNIT_SIZE,0,0,
        };
		
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊ����(Float)�ͻ���
        mVertexBuffer.put(vertices);//�ڻ�������д������
        mVertexBuffer.position(0);//���û�������ʼλ��
        
        float colors[]=new float[]//������ɫ����
        {
        		1,1,1,0,//��ɫ	
        		0,0,1,0,//��
        		0,1,0,0//��
        };
        
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
        mColorBuffer = cbb.asFloatBuffer();//ת��Ϊ����(Float)�ͻ���
        mColorBuffer.put(colors);//�ڻ�������д������
        mColorBuffer.position(0);//���û�������ʼλ��
    }

    //��ʼ����ɫ���ķ���
    @SuppressLint("NewApi")
	public void initShader(MyTDView mv)
    {
    	//���ض�����ɫ���Ľű�����
        mVertexShader=ShaderUtil.loadFromAssetsFile("example1/vertex.glsl", mv.getResources());
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("example1/frag.glsl", mv.getResources());
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ����������  
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж�����ɫ��������
        maColorHandle= GLES30.glGetAttribLocation(mProgram, "aColor");
        //��ȡ�������ܱ任��������
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");  
    }
    
    @SuppressLint("NewApi")
	public void drawSelf()
    {        
    	 //ָ��ʹ��ĳ��shader����
    	 GLES30.glUseProgram(mProgram);        
    	 //��ʼ���任����
         Matrix.setRotateM(mMMatrix,0,0,0,1,0);
         //������Z������λ��1
         Matrix.translateM(mMMatrix,0,0,0,1);
         //������x����ת
         Matrix.rotateM(mMMatrix,0,xAngle,1,0,0);
         //���任��������Ⱦ����
         GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, Triangle.getFianlMatrix(mMMatrix), 0); 
         //������λ�����ݴ��ͽ���Ⱦ����
         GLES30.glVertexAttribPointer(
         		maPositionHandle,   
         		3, 
         		GLES30.GL_FLOAT, 
         		false,
                3*4,   
                mVertexBuffer
         );
         //��������ɫ���ݴ��ͽ���Ⱦ����
         GLES30.glVertexAttribPointer  
         (
        		maColorHandle,
         		4,
         		GLES30.GL_FLOAT,
         		false,
                4*4,
                mColorBuffer
         );
         GLES30.glEnableVertexAttribArray(maPositionHandle);//���ö���λ������
         GLES30.glEnableVertexAttribArray(maColorHandle);//���ö�����ɫ����  
         //����������
         GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount); 
    }
    public static float[] getFianlMatrix(float[] spec)
    {
    	mMVPMatrix=new float[16];
    	Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, spec, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);        
        return mMVPMatrix;
    }
}