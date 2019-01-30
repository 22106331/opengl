package com.opengl.study.example4;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES30;

import com.opengl.study.ShaderUtil;

import static com.opengl.study.ShaderUtil.createProgram;

public class Celestial {	//��ʾ�ǿ��������
	final float UNIT_SIZE=10.0f;//����뾶
	private FloatBuffer   mVertexBuffer;//�����������ݻ���
    int vCount=0;//��������
    float yAngle;//������Y����ת�ĽǶ�
    float scale;//���ǳߴ�  
    String mVertexShader;//������ɫ�� ����ű�
    String mFragmentShader;//ƬԪ��ɫ������ű�
    int mProgram;//�Զ�����Ⱦ���߳���id 
    int muMVPMatrixHandle;//�ܱ任��������   
    int maPositionHandle; //����λ����������  
    int uPointSizeHandle;//����ߴ��������
    public Celestial(float scale,float yAngle,int vCount,MySurfaceView mv){
    	this.yAngle=yAngle;//��Y����ת�ĽǶ�
    	this.scale=scale;
    	this.vCount=vCount;  	//���������
    	initVertexData();//���ó�ʼ���������ݵ�initVertexData����
    	intShader(mv);//���ó�ʼ����ɫ����initShader����
    }
    public void initVertexData(){//�Զ����ʼ���������ݵ�initVertexData����  	  	
    	//�����������ݵĳ�ʼ��       
        float vertices[]=new float[vCount*3];
        for(int i=0;i<vCount;i++){
        	//�������ÿ�����ǵ�xyz����
        	double angleTempJD=Math.PI*2*Math.random();
        	double angleTempWD=Math.PI*(Math.random()-0.5f);
        	vertices[i*3]=(float)(UNIT_SIZE*Math.cos(angleTempWD)*Math.sin(angleTempJD));
        	vertices[i*3+1]=(float)(UNIT_SIZE*Math.sin(angleTempWD));
        	vertices[i*3+2]=(float)(UNIT_SIZE*Math.cos(angleTempWD)*Math.cos(angleTempJD));
        }
        //���������������ݻ���
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥������
        mVertexBuffer.position(0);//���û�������ʼλ��
    }
    public void intShader(MySurfaceView mv){    //��ʼ����ɫ��
    	//���ض�����ɫ���Ľű�����       
        mVertexShader=ShaderUtil.loadFromAssetsFile("example4/vertex_xk.sh", mv.getResources());
        ShaderUtil.checkGlError("==ss==");
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("example4/frag_xk.sh", mv.getResources());
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        ShaderUtil.checkGlError("==ss==");      
        mProgram = createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ����������
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");        
        //��ȡ�������ܱ任��������
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix"); 
        //��ȡ����ߴ��������
        uPointSizeHandle = GLES30.glGetUniformLocation(mProgram, "uPointSize"); 
    }
    public void drawSelf(){  
   	    GLES30.glUseProgram(mProgram); //ָ��ʹ��ĳ����ɫ������
        //�����ձ任��������Ⱦ����
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);  
        GLES30.glUniform1f(uPointSizeHandle, scale);  //������ߴ紫����Ⱦ����
        GLES30.glVertexAttribPointer( //������λ������������Ⱦ����    
        		maPositionHandle,   
        		3, 
        		GLES30.GL_FLOAT, 
        		false,
                3*4, 
                mVertexBuffer   
        );   
        //���ö���λ����������
        GLES30.glEnableVertexAttribArray(maPositionHandle);         
        GLES30.glDrawArrays(GLES30.GL_POINTS, 0, vCount); //�������ǵ�    
}}
