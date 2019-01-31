package com.opengl.catcher.special;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


import android.opengl.GLES30;

import com.opengl.catcher.MatrixState.MatrixState3D;

//����������
public class ParticleForDraw 
{	
	int mProgram;//�Զ�����Ⱦ���߳���id
    int muMVPMatrixHandle;//�ܱ任��������id
    int muSjFactor;//˥����������id
    int muBj;//�뾶����id  
    int muStartColor;//��ʼ��ɫ����id
    int muEndColor;//��ֹ��ɫ����id
    int maPositionHandle; //����λ����������id  
    int maTexCoorHandle; //��������������������id  
    String mVertexShader;//������ɫ��    	 
    String mFragmentShader;//ƬԪ��ɫ��
	
	FloatBuffer   mVertexBuffer;//�����������ݻ���
	FloatBuffer   mTexCoorBuffer;//���������������ݻ���
    int vCount=0;   
    float halfSize;
    int texId;
    public ParticleForDraw(float halfSize,int mProgram,int texId)
    {    	
    	this.halfSize=halfSize;
    	this.mProgram=mProgram;
    	this.texId=texId;
    	//��ʼ��������������ɫ����
    	initVertexData(halfSize);
    	//��ʼ����ɫ��        
    	initShader();
    }
    
    //��ʼ��������������ɫ���ݵķ���
    public void initVertexData(float halfSize)
    {
    	//�����������ݵĳ�ʼ��================begin============================
        vCount=6;
        float vertices[]=new float[]
        {
        	-halfSize,halfSize,0,
        	-halfSize,-halfSize,0,
        	 halfSize,halfSize,0,
        	
        	-halfSize,-halfSize,0,
        	 halfSize,-halfSize,0,
        	 halfSize,halfSize,0
        };
		
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //�����������ݵĳ�ʼ��================end============================
        //���������������ݵĳ�ʼ��================begin============================
        float texCoor[]=new float[]//������ɫֵ���飬ÿ������4��ɫ��ֵRGBA
        {
        		0,0, 0,1, 1,0,
        		0,1, 1,1, 1,0
        };        
        //�������������������ݻ���
        ByteBuffer cbb = ByteBuffer.allocateDirect(texCoor.length*4);
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTexCoorBuffer = cbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTexCoorBuffer.put(texCoor);//�򻺳����з��붥����ɫ����
        mTexCoorBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //���������������ݵĳ�ʼ��================end============================

    }

    //��ʼ����ɫ��
    public void initShader()
    {
        //��ȡ�����ж���λ����������id  
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж�������������������id  
        maTexCoorHandle= GLES30.glGetAttribLocation(mProgram, "aTexCoor");
        //��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");  
        //��ȡ������˥����������id
        muSjFactor=GLES30.glGetUniformLocation(mProgram, "sjFactor");
        //��ȡ�����а뾶����id
        muBj=GLES30.glGetUniformLocation(mProgram, "bj");
        //��ȡ��ʼ��ɫ����id
        muStartColor=GLES30.glGetUniformLocation(mProgram, "startColor");
        //��ȡ��ֹ��ɫ����id
        muEndColor=GLES30.glGetUniformLocation(mProgram, "endColor");
    }
    
    public void drawSelf(float sj,float[] startColor,float[] endColor)
    {        
    	 //�ƶ�ʹ��ĳ��shader����
    	 GLES30.glUseProgram(mProgram);  
         //�����ձ任������shader����
         GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState3D.getFinalMatrix(), 0);
         //��˥�����Ӵ���shader����
         GLES30.glUniform1f(muSjFactor, sj);
         //���뾶����shader����
         GLES30.glUniform1f(muBj, halfSize);
         //����ʼ��ɫ������Ⱦ����
         GLES30.glUniform4fv(muStartColor, 1, startColor, 0);
         //����ֹ��ɫ������Ⱦ����
         GLES30.glUniform4fv(muEndColor, 1, endColor, 0);
         //Ϊ����ָ������λ������
         GLES30.glVertexAttribPointer  
         (
         		maPositionHandle,   
         		3, 
         		GLES30.GL_FLOAT, 
         		false,
                3*4,   
                mVertexBuffer
         );       
         //Ϊ����ָ������������������
         GLES30.glVertexAttribPointer  
         (
        		maTexCoorHandle, 
         		2, 
         		GLES30.GL_FLOAT, 
         		false,
                2*4,   
                mTexCoorBuffer
         );   
         //������λ����������
         GLES30.glEnableVertexAttribArray(maPositionHandle);  
         GLES30.glEnableVertexAttribArray(maTexCoorHandle);  
         
         //������
         GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
         GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texId);
         
         //�����������
         GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount); 
    }
}
