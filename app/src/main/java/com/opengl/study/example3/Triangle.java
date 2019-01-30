package com.opengl.study.example3;
import static com.opengl.study.ShaderUtil.createProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES30;

import com.opengl.study.ShaderUtil;

//����������
public class Triangle 
{	
	int mProgram;//�Զ�����Ⱦ���߳���id
    int muMVPMatrixHandle;//�ܱ任��������
    int maPositionHandle; //����λ����������
    int maTexCoorHandle; //��������������������
    String mVertexShader;//������ɫ������ű�
    String mFragmentShader;//ƬԪ��ɫ������ű�
	
	FloatBuffer   mVertexBuffer;//�����������ݻ���
	FloatBuffer   mTexCoorBuffer;//���������������ݻ���
    int vCount=0;   
    float xAngle=0;//��x����ת�ĽǶ�
    float yAngle=0;//��y����ת�ĽǶ�
    float zAngle=0;//��z����ת�ĽǶ�
    
    public Triangle(MySurfaceView mv)
    {    	
    	//���ó�ʼ���������ݵķ���
    	initVertexData();
    	//���ó�ʼ����ɫ���ķ���
    	initShader(mv);
    }
    
    //��ʼ���������ݵķ���
    public void initVertexData()
    {
    	//�����������ݵĳ�ʼ��================begin============================
        vCount=3;
        final float UNIT_SIZE=0.15f;
        float vertices[]=new float[]
        {
        	0*UNIT_SIZE,11*UNIT_SIZE,0,
        	-11*UNIT_SIZE,-11*UNIT_SIZE,0,
        	11*UNIT_SIZE,-11*UNIT_SIZE,0,
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
        		0.5f,0, 
        		0,1, 
        		1,1        		
        };        
        //�������������������ݻ���
        ByteBuffer cbb = ByteBuffer.allocateDirect(texCoor.length*4);
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTexCoorBuffer = cbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTexCoorBuffer.put(texCoor);//�򻺳����з��붥����������
        mTexCoorBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //���������������ݵĳ�ʼ��================end============================

    }

    //�Զ���ĳ�ʼ����ɫ������
    public void initShader(MySurfaceView mv)
    {
    	//���ض�����ɫ���Ľű�����
        mVertexShader=ShaderUtil.loadFromAssetsFile("example3/vertex.sh", mv.getResources());
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("example3/frag.sh", mv.getResources());
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        mProgram = createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ����������
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж�������������������
        maTexCoorHandle= GLES30.glGetAttribLocation(mProgram, "aTexCoor");
        //��ȡ�������ܱ任��������
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");  
    }
    
    public void drawSelf(int texId)
    {        
    	 //ָ��ʹ��ĳ��shader����
    	 GLES30.glUseProgram(mProgram);        
    	 
    	 MatrixState.setInitStack();
    	 
         //������Z������λ��1
         MatrixState.translate(0, 0, 1);
         
         //������y����ת
         MatrixState.rotate(yAngle, 0, 1, 0);
         //������z����ת
         MatrixState.rotate(zAngle, 0, 0, 1);  
         //������x����ת
         MatrixState.rotate(xAngle, 1, 0, 0);
         //�����ձ任��������Ⱦ����
         GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
         //������λ�����ݴ��ͽ���Ⱦ����
         GLES30.glVertexAttribPointer  
         (
         		maPositionHandle,   
         		3, 
         		GLES30.GL_FLOAT, 
         		false,
                3*4,   
                mVertexBuffer
         );
         //�������������ݴ��ͽ���Ⱦ����
         GLES30.glVertexAttribPointer  
         (
        		maTexCoorHandle, 
         		2, 
         		GLES30.GL_FLOAT, 
         		false,
                2*4,   
                mTexCoorBuffer
         );

         GLES30.glEnableVertexAttribArray(maPositionHandle);  //���ö���λ������
         GLES30.glEnableVertexAttribArray(maTexCoorHandle);//���ö���������������
         
         //������
         GLES30.glActiveTexture(GLES30.GL_TEXTURE0);//����ʹ�õ�������
         GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texId);//��ָ��������id
         
         //�����������
         GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
    }
}
