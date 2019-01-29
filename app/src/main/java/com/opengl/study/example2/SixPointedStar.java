package com.opengl.study.example2;
import static com.opengl.study.ShaderUtil.createProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import android.opengl.GLES30;
import android.opengl.Matrix;

import com.opengl.study.ShaderUtil;

//������
public class SixPointedStar 
{	
	int mProgram;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������
    int maPositionHandle; //����λ����������  
    int maColorHandle; //������ɫ��������  
    String mVertexShader;	//������ɫ������ű� 
    String mFragmentShader;	//ƬԪ��ɫ������ű�
    static float[] mMMatrix = new float[16];	//���������3D�任���󣬰�����ת��ƽ�ơ�����
	
	FloatBuffer   mVertexBuffer;//�����������ݻ���
	FloatBuffer   mColorBuffer;//������ɫ���ݻ���
    int vCount=0;    
    float yAngle=0;//��y����ת�ĽǶ�
    float xAngle=0;//��x����ת�ĽǶ�
    final float UNIT_SIZE=1;
    
    public SixPointedStar(MySurfaceView mv,float r,float R,float z)
    {    	
    	//���ó�ʼ���������ݵ�initVertexData����
    	initVertexData(R,r,z);
    	//���ó�ʼ����ɫ����intShader����     
    	initShader(mv);
    }
    
	//��ʼ���������ݵ�initVertexData����
    public void initVertexData(float R,float r,float z)
    {
		List<Float> flist=new ArrayList<Float>();
		float tempAngle=360/6;
		for(float angle=0;angle<360;angle+=tempAngle)//ѭ�����ɹ��������θ������εĶ�������
		{
			//��һ��������
			//��һ�����x��y��z����
			flist.add(0f);
			flist.add(0f);
			flist.add(z);
			//�ڶ������x��y��z����
			flist.add((float) (R*UNIT_SIZE*Math.cos(Math.toRadians(angle))));
			flist.add((float) (R*UNIT_SIZE*Math.sin(Math.toRadians(angle))));
			flist.add(z);
			//���������x��y��z����
			flist.add((float) (r*UNIT_SIZE*Math.cos(Math.toRadians(angle+tempAngle/2))));
			flist.add((float) (r*UNIT_SIZE*Math.sin(Math.toRadians(angle+tempAngle/2))));
			flist.add(z);
			
			//�ڶ���������
			//��һ�����ĵ��x��y��z����
			flist.add(0f);
			flist.add(0f);
			flist.add(z);
			//�ڶ������x��y��z����
			flist.add((float) (r*UNIT_SIZE*Math.cos(Math.toRadians(angle+tempAngle/2))));
			flist.add((float) (r*UNIT_SIZE*Math.sin(Math.toRadians(angle+tempAngle/2))));
			flist.add(z);
			//���������x��y��z����
			flist.add((float) (R*UNIT_SIZE*Math.cos(Math.toRadians(angle+tempAngle))));
			flist.add((float) (R*UNIT_SIZE*Math.sin(Math.toRadians(angle+tempAngle))));
			flist.add(z);
		}
		vCount=flist.size()/3;
		float[] vertexArray=new float[flist.size()];//������������
		for(int i=0;i<vCount;i++)
		{
			vertexArray[i*3]=flist.get(i*3);
			vertexArray[i*3+1]=flist.get(i*3+1);
			vertexArray[i*3+2]=flist.get(i*3+2);
		}
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertexArray.length*4);
		vbb.order(ByteOrder.nativeOrder());	//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
		mVertexBuffer=vbb.asFloatBuffer();
		mVertexBuffer.put(vertexArray);//�������������ݷŽ�����
		mVertexBuffer.position(0);
		
		
		
        
        //������ɫ���ݵĳ�ʼ��================begin============================
		float[] colorArray=new float[vCount*4];//������ɫ���ݵĳ�ʼ��
		for(int i=0;i<vCount;i++)
		{
			if(i%3==0){//���ĵ�Ϊ��ɫ��RGBA 4��ͨ��[1,1,1,0]
				colorArray[i*4]=1;
				colorArray[i*4+1]=1;
				colorArray[i*4+2]=1;
				colorArray[i*4+3]=0;
			}
			else{//���ϵĵ�Ϊ����ɫ��RGBA 4��ͨ��[0.45,0.75,0.75,0]
				colorArray[i*4]=0.45f;
				colorArray[i*4+1]=0.75f;
				colorArray[i*4+2]=0.75f;
				colorArray[i*4+3]=0;
			}
		}
		ByteBuffer cbb=ByteBuffer.allocateDirect(colorArray.length*4);
		cbb.order(ByteOrder.nativeOrder());	//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
		mColorBuffer=cbb.asFloatBuffer();
		mColorBuffer.put(colorArray);//��������ɫ���ݷŽ�����
		mColorBuffer.position(0);
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //������ɫ���ݵĳ�ʼ��================end============================

    }

  //��ʼ����ɫ����initShader����
    public void initShader(MySurfaceView mv)
    {
    	//���ض�����ɫ���Ľű�����
        mVertexShader=ShaderUtil.loadFromAssetsFile("example2/vertex.sh", mv.getResources());
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("example2/frag.sh", mv.getResources());
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        mProgram = createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ����������id  
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж�����ɫ��������id  
        maColorHandle= GLES30.glGetAttribLocation(mProgram, "aColor");
        //��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");  
    }
    
  //����ͼ�εķ���
    public void drawSelf()
    {        
    	//ָ��ʹ��ĳ����ɫ������
    	 GLES30.glUseProgram(mProgram);        
    	 //��ʼ���任����
         Matrix.setRotateM(mMMatrix,0,0,0,1,0);
         //������Z������λ��1
         Matrix.translateM(mMMatrix,0,0,0,1);
         //������y����תyAngle��
         Matrix.rotateM(mMMatrix,0,yAngle,0,1,0);
         //������x����תxAngle��
         Matrix.rotateM(mMMatrix,0,xAngle,1,0,0);  
         //�����ձ任��������Ⱦ����
         GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(mMMatrix), 0); 
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
         //��������ɫ����������Ⱦ����
         GLES30.glVertexAttribPointer  
         (
        		maColorHandle, 
         		4, 
         		GLES30.GL_FLOAT, 
         		false,
                4*4,   
                mColorBuffer
         );   
         //���ö���λ����������
         GLES30.glEnableVertexAttribArray(maPositionHandle); 
         //���ö�����ɫ��������
         GLES30.glEnableVertexAttribArray(maColorHandle);  
         //����������
         GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount); 
    }
}
