package com.opengl.catcher.object;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.opengl.catcher.MatrixState.MatrixState3D;

import static com.opengl.catcher.constant.SourceConstant.ColorCS;

//���غ�����塪����Я��������Ϣ����ɫ���
public class LoadedObjectVertexNormalTexture
{	
	int mProgram;//�Զ�����Ⱦ������ɫ������id  
    int muMVPMatrixHandle;//�ܱ任��������
    int muMMatrixHandle;//λ�á���ת�任����
    int maPositionHandle; //����λ����������  
    int maTexCoorHandle; //��������������������  
    int vCount=0;  
	int mVertexBufferId;//�����������ݻ��� id
	int mTexCoorBufferId;//���������������ݻ���id
	int vaoId=0;
    int SwitchcolorHandle;//����holebox.obj�任��ɫ��ֵ�Ĳ�������
    public LoadedObjectVertexNormalTexture(GLSurfaceView mv,float[] vertices,float[] normals,float texCoors[],int programId)
    {    	
    	this.mProgram=programId;
    	//��ʼ��shader        
    	initShader();
    	//��ʼ��������������ɫ����
    	initVertexData(vertices,normals,texCoors);
    }
    
    //��ʼ��������������ɫ���ݵķ���
    public void initVertexData(float[] vertices,float[] normals,float texCoors[])
    {
    	//����id����
    	int[] buffIds=new int[2];
    	//����3������id
    	GLES30.glGenBuffers(2, buffIds, 0);
    	//�����������ݻ��� id
    	mVertexBufferId=buffIds[0];
    	//���������������ݻ���id
    	mTexCoorBufferId=buffIds[1];
    	
    	//�����������ݵĳ�ʼ��================begin============================
    	vCount=vertices.length/3;   
		
    	//���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        FloatBuffer mVertexBuffer = vbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //�󶨵������������ݻ��� 
    	GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,mVertexBufferId);
    	//�򶥵��������ݻ�����������
    	GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, vertices.length*4, mVertexBuffer, GLES30.GL_STATIC_DRAW);    	
        //�����������ݵĳ�ʼ��================end============================
        
        
        //���������������ݵĳ�ʼ��================begin============================  
        ByteBuffer tbb = ByteBuffer.allocateDirect(texCoors.length*4);
        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        FloatBuffer mTexCoorBuffer = tbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTexCoorBuffer.put(texCoors);//�򻺳����з��붥��������������
        mTexCoorBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //�󶨵����������������ݻ���
    	GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,mTexCoorBufferId);
    	//�򶥵������������ݻ�����������
    	GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, texCoors.length*4, mTexCoorBuffer, GLES30.GL_STATIC_DRAW);
    	//���������������ݵĳ�ʼ��================end============================
    	//�󶨵�ϵͳĬ�ϻ���
    	GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,0);    
    	initVAO();
    }
    public void initVAO()
    {
    	int[] vaoIds=new int[1];
      	//����VAO
      	GLES30.glGenVertexArrays(1, vaoIds, 0);
      	vaoId=vaoIds[0];
    	//��VAO
      	GLES30.glBindVertexArray(vaoId);
         
         //���ö���λ�á�������������
         GLES30.glEnableVertexAttribArray(maPositionHandle);  
         GLES30.glEnableVertexAttribArray(maTexCoorHandle); 
     	

         //�󶨵������������ݻ��� 
     	 GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,mVertexBufferId); 
         //ָ������λ������     	 
         GLES30.glVertexAttribPointer  
         (
         		maPositionHandle,   
         		3, 
         		GLES30.GL_FLOAT, 
         		false,
                3*4,   
                0
         );     
         //�󶨵����������������ݻ���
     	 GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,mTexCoorBufferId);
         //ָ������������������
         GLES30.glVertexAttribPointer  
         (
        		maTexCoorHandle, 
         		2, 
         		GLES30.GL_FLOAT, 
         		false,
                2*4,   
                0
         );
         // �󶨵�ϵͳĬ�ϻ���
     	 GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,0);
       	GLES30.glBindVertexArray(0);
    }
    //��ʼ��shader
    public void initShader()
    {
        //��ȡ�����ж���λ����������  
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж�������������������  
        maTexCoorHandle= GLES30.glGetAttribLocation(mProgram, "aTexCoor"); 
        //��ȡ�������ܱ任��������
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
        //��ȡλ�á���ת�任��������
        muMMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMMatrix"); 
        SwitchcolorHandle=GLES30.glGetUniformLocation(mProgram, "ColorCS");//�������ӵı任�Ĳ���ֵ
    
    }
    
    public void drawSelf(int texId)
    {   	
    	//�ƶ�ʹ��ĳ����ɫ������
    	GLES30.glUseProgram(mProgram);
    	//�����ձ任��������ɫ������
    	GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState3D.getFinalMatrix(), 0);
    	//��λ�á���ת�任��������ɫ������
    	GLES30.glUniformMatrix4fv(muMMatrixHandle, 1, false, MatrixState3D.getMMatrix(), 0);  
    	GLES30.glUniform1f(SwitchcolorHandle, ColorCS);
    	GLES30.glBindVertexArray(vaoId);
    	
    	
    	//������
    	GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
    	GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texId);
    	//���Ƽ��ص�����
    	GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount); 
    	GLES30.glBindVertexArray(0);
         
    }
}
