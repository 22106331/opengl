package com.opengl.study.example4;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import android.opengl.GLES30;

import com.opengl.study.ShaderUtil;

import static com.opengl.study.ShaderUtil.createProgram;

//��ʾ������࣬Ϊ��ͨ������δ���ö�������
public class Moon  
{	
	int mProgram;//�Զ�����Ⱦ���߳���id
    int muMVPMatrixHandle;//�ܱ任��������
    int muMMatrixHandle;//λ�á���ת�任����
    int maCameraHandle; //�����λ���������� 
    int maPositionHandle; //����λ����������  
    int maNormalHandle; //���㷨������������  
    int maTexCoorHandle; //��������������������  
    int maSunLightLocationHandle;//��Դλ����������  
    
    String mVertexShader;//������ɫ��  ����ű�
    String mFragmentShader;//ƬԪ��ɫ������ű�
	
	FloatBuffer   mVertexBuffer;//�����������ݻ���
	FloatBuffer   mTexCoorBuffer;//���������������ݻ���
    int vCount=0; 
    
    public Moon(MySurfaceView mv,float r)
    {    	
    	//���ó�ʼ���������ݵķ���
    	initVertexData(r);
    	//���ó�ʼ����ɫ���ķ���        
    	initShader(mv);
    } 
    
    //��ʼ���������ݵķ���
    public void initVertexData(float r)
    {
    	//�����������ݵĳ�ʼ��================begin============================    	
    	final float UNIT_SIZE=0.5f;
    	ArrayList<Float> alVertix=new ArrayList<Float>();//��Ŷ��������ArrayList
    	final float angleSpan=10f;//������е�λ�зֵĽǶ�
    	for(float vAngle=90;vAngle>-90;vAngle=vAngle-angleSpan)//��ֱ����angleSpan��һ��
        {
        	for(float hAngle=360;hAngle>0;hAngle=hAngle-angleSpan)//ˮƽ����angleSpan��һ��
        	{//����������һ���ǶȺ�����Ӧ�Ĵ˵��������ϵ�����    	
        		double xozLength=r*UNIT_SIZE*Math.cos(Math.toRadians(vAngle));
        		float x1=(float)(xozLength*Math.cos(Math.toRadians(hAngle)));
        		float z1=(float)(xozLength*Math.sin(Math.toRadians(hAngle)));
        		float y1=(float)(r*UNIT_SIZE*Math.sin(Math.toRadians(vAngle)));
        		
        		xozLength=r*UNIT_SIZE*Math.cos(Math.toRadians(vAngle-angleSpan));
        		float x2=(float)(xozLength*Math.cos(Math.toRadians(hAngle)));
        		float z2=(float)(xozLength*Math.sin(Math.toRadians(hAngle)));
        		float y2=(float)(r*UNIT_SIZE*Math.sin(Math.toRadians(vAngle-angleSpan)));
        		
        		xozLength=r*UNIT_SIZE*Math.cos(Math.toRadians(vAngle-angleSpan));
        		float x3=(float)(xozLength*Math.cos(Math.toRadians(hAngle-angleSpan)));
        		float z3=(float)(xozLength*Math.sin(Math.toRadians(hAngle-angleSpan)));
        		float y3=(float)(r*UNIT_SIZE*Math.sin(Math.toRadians(vAngle-angleSpan)));
        		
        		xozLength=r*UNIT_SIZE*Math.cos(Math.toRadians(vAngle));
        		float x4=(float)(xozLength*Math.cos(Math.toRadians(hAngle-angleSpan)));
        		float z4=(float)(xozLength*Math.sin(Math.toRadians(hAngle-angleSpan)));
        		float y4=(float)(r*UNIT_SIZE*Math.sin(Math.toRadians(vAngle)));   
        		
        		//������һ������
        		alVertix.add(x1);alVertix.add(y1);alVertix.add(z1);
        		alVertix.add(x2);alVertix.add(y2);alVertix.add(z2);
        		alVertix.add(x4);alVertix.add(y4);alVertix.add(z4);        		
        		//�����ڶ�������
        		alVertix.add(x4);alVertix.add(y4);alVertix.add(z4);
        		alVertix.add(x2);alVertix.add(y2);alVertix.add(z2);
        		alVertix.add(x3);alVertix.add(y3);alVertix.add(z3); 
        	}
        } 	
        vCount=alVertix.size()/3;//���������Ϊ����ֵ������1/3����Ϊһ��������3������
    	
        //��alVertix�е�����ֵת�浽һ��float������
        float vertices[]=new float[vCount*3];
    	for(int i=0;i<alVertix.size();i++)
    	{
    		vertices[i]=alVertix.get(i);
    	}
		
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥������
        mVertexBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        
        //��alTexCoor�е���������ֵת�浽һ��float������
        float[] texCoor=generateTexCoor//��ȡ�з���ͼ����������                
        (
   			 (int)(360/angleSpan), //����ͼ�зֵ�����
   			 (int)(180/angleSpan)  //����ͼ�зֵ�����
        );
        ByteBuffer llbb = ByteBuffer.allocateDirect(texCoor.length*4);
        llbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTexCoorBuffer=llbb.asFloatBuffer();
        mTexCoorBuffer.put(texCoor);
        mTexCoorBuffer.position(0);    
        //�����������ݵĳ�ʼ��================end============================
    }
  
    //��ʼ����ɫ��
    public void initShader(MySurfaceView mv)
    {
    	//���ض�����ɫ���Ľű�����       
        mVertexShader=ShaderUtil.loadFromAssetsFile("example4/vertex_moon.sh", mv.getResources());
        ShaderUtil.checkGlError("==ss==");   
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("example4/frag_moon.sh", mv.getResources());
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        ShaderUtil.checkGlError("==ss==");      
        mProgram = createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ���������� 
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж��㾭γ����������   
        maTexCoorHandle=GLES30.glGetAttribLocation(mProgram, "aTexCoor");  
        //��ȡ�����ж��㷨������������  
        maNormalHandle= GLES30.glGetAttribLocation(mProgram, "aNormal");
        //��ȡ�������ܱ任��������
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");   
        //��ȡ�����������λ������
        maCameraHandle=GLES30.glGetUniformLocation(mProgram, "uCamera"); 
        //��ȡ�����й�Դλ������
        maSunLightLocationHandle=GLES30.glGetUniformLocation(mProgram, "uLightLocationSun"); 
        //��ȡλ�á���ת�任��������
        muMMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMMatrix");  
    }
    
    public void drawSelf(int texId) 
    {        
    	 //ָ��ʹ��ĳ��shader����
    	 GLES30.glUseProgram(mProgram);
         //�����ձ任��������Ⱦ����
         GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);  
         //��λ�á���ת�任��������Ⱦ����
         GLES30.glUniformMatrix4fv(muMMatrixHandle, 1, false, MatrixState.getMMatrix(), 0);    
         //�������λ�ô�����Ⱦ����  
         GLES30.glUniform3fv(maCameraHandle, 1, MatrixState.cameraFB);
         //����Դλ�ô�����Ⱦ����
         GLES30.glUniform3fv(maSunLightLocationHandle, 1, MatrixState.lightPositionFBSun);
         
         //������λ������������Ⱦ����
         GLES30.glVertexAttribPointer        
         (
         		maPositionHandle,   
         		3, 
         		GLES30.GL_FLOAT, 
         		false,
                3*4, 
                mVertexBuffer   
         );       
         //��������������������Ⱦ����
         GLES30.glVertexAttribPointer  
         (
        		maTexCoorHandle,  
         		2, 
         		GLES30.GL_FLOAT, 
         		false,
                2*4,   
                mTexCoorBuffer
         );   
         //�����㷨��������������Ⱦ����
         GLES30.glVertexAttribPointer  
         (
        		maNormalHandle, 
         		4, 
         		GLES30.GL_FLOAT, 
         		false,
                3*4,   
                mVertexBuffer
         );            
         //���ö���λ����������
         GLES30.glEnableVertexAttribArray(maPositionHandle);  
         //���ö���������������
         GLES30.glEnableVertexAttribArray(maTexCoorHandle); 
         //���ö��㷨������������
         GLES30.glEnableVertexAttribArray(maNormalHandle);           
         //������
         GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
         GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texId);         
         //�������η�ʽִ�л���
         GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount); 
    }
    
    //�Զ��з����������������ķ���
    public float[] generateTexCoor(int bw,int bh)
    {
    	float[] result=new float[bw*bh*6*2]; 
    	float sizew=1.0f/bw;//����
    	float sizeh=1.0f/bh;//����
    	int c=0;
    	for(int i=0;i<bh;i++)
    	{
    		for(int j=0;j<bw;j++)
    		{
    			//ÿ����һ�����Σ������������ι��ɣ��������㣬12����������
    			float s=j*sizew;
    			float t=i*sizeh;
    			
    			result[c++]=s;
    			result[c++]=t;
    			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t;    			
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t+sizeh;    			
    		}
    	}
    	return result;
    }
}
