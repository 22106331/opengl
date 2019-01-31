package com.opengl.catcher.object;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES30;

import com.opengl.catcher.MatrixState.MatrixState2D;
import com.opengl.catcher.constant.Constant;

import static com.opengl.catcher.constant.SourceConstant.*;

public class BN2DObject
{
	public FloatBuffer mVertexBuffer;//�����������ݻ���
	public FloatBuffer mTexCoorBuffer;//���������������ݻ���
    int muMVPMatrixHandle;//�ܱ任��������id
    int maPositionHandle;//����λ����������id  
    int maTexCoorHandle;//��������������������id
    int CLStepHandle;//�ı�͸������������id
    int xHandle;
    
    int programId;//�Զ�����Ⱦ���߳���id
	int texId;//����ͼƬ��
	int vCount;//�������
    boolean initFlag=false;//�ж��Ƿ��ʼ����ɫ��  
    float x;//��Ҫƽ�Ƶ�x����
	float y;//��Ҫƽ�Ƶ�y����
	boolean isLoad=false;
	
	int han=0;
	int lie=0;
	int HZ;
	int LZ;
	int muSjFactor;//˥����������id
	int count=0;
	int spng=0;
	public BN2DObject(float x,float y,float picWidth,float picHeight,int texId,int programId)
	{
		this.x=Constant.fromScreenXToNearX(x);//����Ļxת�����ӿ�x����
		this.y=Constant.fromScreenYToNearY(y);//����Ļyת�����ӿ�y����
		this.texId=texId;
		this.programId=programId;
		initVertexData(picWidth,picHeight);//��ʼ����������
	}
	public BN2DObject(float x,float y,float picWidth,float picHeight,int texId,int programId,int spng)
	{
		this.spng=spng;
		this.x=Constant.fromScreenXToNearX(x);//����Ļxת�����ӿ�x����
		this.y=Constant.fromScreenYToNearY(y);//����Ļyת�����ӿ�y����
		this.texId=texId;
		this.programId=programId;
		initVertexData(picWidth,picHeight);//��ʼ����������
	}
	public BN2DObject(float x,float y,float width,float height,int han,int lie,int HZ,int LZ,
			int texId,int programId)
	{//����һ��loadView�е�ͼƬ��new
		this.x=Constant.fromScreenXToNearX(x);//����Ļxת�����ӿ�x����
		this.y=Constant.fromScreenYToNearY(y);//����Ļyת�����ӿ�y����
		isLoad=true;
		this.HZ=HZ;
		this.LZ=LZ;
		this.han=han;
		this.lie=lie;
		this.texId=texId;
		this.programId=programId;
		initVertexData(width,height);//��ʼ����������
	}
	
	public void initVertexData(float width,float height)//��ʼ����������
	{
		vCount=4;//�������
		width=Constant.fromPixSizeToNearSize(width);//��Ļ���ת�����ӿڿ��
		height=Constant.fromPixSizeToNearSize(height);//��Ļ�߶�ת�����ӿڸ߶�
		//��ʼ��������������
		float vertices[]=new float[]
		{
				-width/2,height/2,0,
				-width/2,-height/2,0,
				width/2,height/2,0,
				width/2,-height/2,0
		};
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);//���������������ݻ���
		vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
		mVertexBuffer=vbb.asFloatBuffer();//ת��ΪFloat�ͻ���
		mVertexBuffer.put(vertices);//�򻺳����з��붥����������
		mVertexBuffer.position(0);//���û�������ʼλ��
		float[] texCoor=new float[12];//��ʼ��������������
		//����ͼ�ε���������
		if(!isLoad)
		{
			texCoor=new float[]{
					0,0,0,1,1,0,
					1,1,1,0,0,1};
		}else
		{
			//����һ������ͼ���и��У�������ڶ�Ӧ������ͼ�ĵط������������Ӧ����������
			float sstep=(float)1/LZ;
			float tstep=(float)1/HZ;
			texCoor=new float[]
					{
					   sstep*han-sstep,tstep*lie-tstep,
					   sstep*han-sstep,tstep*lie,
					   sstep*han,tstep*lie-tstep,
					   
					   sstep*han,tstep*lie,
					   sstep*han,tstep*lie-tstep,
					   sstep*han-sstep,tstep*lie
					};
		}
		ByteBuffer cbb=ByteBuffer.allocateDirect(texCoor.length*4);//�������������������ݻ���
		cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
		mTexCoorBuffer=cbb.asFloatBuffer();//ת��ΪFloat�ͻ���
		mTexCoorBuffer.put(texCoor);//�򻺳����з��붥����ɫ����
		mTexCoorBuffer.position(0);//���û�������ʼλ��
	}
	//��ʼ����ɫ��
	public void initShader()
	{
		//��ȡ�����ж���λ����������id  
		maPositionHandle = GLES30.glGetAttribLocation(programId, "aPosition");
		//��ȡ�����ж�������������������id  
		maTexCoorHandle= GLES30.glGetAttribLocation(programId, "aTexCoor");
		//��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES30.glGetUniformLocation(programId, "uMVPMatrix");  
        CLStepHandle=GLES30.glGetUniformLocation(programId, "CLStep");//�ı�͸���ȵĲ�������Id
        xHandle=GLES30.glGetUniformLocation(programId, "xPosition");//
	}
	public void setY(float y)
	{
		this.y=Constant.fromScreenYToNearY(y);//����Ļyת�����ӿ�y����
	}
	
	public void setX(float x)
	{
		this.x=Constant.fromScreenXToNearX(x);
	}
	//����ͼ��
	public void drawSelf()
	{        
		if(!initFlag)
		{
			//��ʼ����ɫ��        
    		initShader();
    		initFlag=true;
    	}
    	GLES30.glEnable(GLES30.GL_BLEND);//�򿪻��
//    	//���û������
		GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA,GLES30.GL_ONE_MINUS_SRC_ALPHA);
    	//�ƶ�ʹ��ĳ��shader����
    	GLES30.glUseProgram(programId);
    	GLES30.glUniform1f(CLStepHandle, step);
    	GLES30.glUniform1f(xHandle, loadPosition);
    	MatrixState2D.pushMatrix();//��������
		MatrixState2D.translate(x,y, 0);//ƽ��
		if(spng==1){
			MatrixState2D.scale(step/100,step/100,step/100);
		}
		if(spng==1){
			MatrixState2D.rotate(AngleSpng, 0, 0, 1);
		}
		if(spng==2){
			MatrixState2D.rotate(Angle2D, 0, 0, 1);
		}
		if(spng==3){
			MatrixState2D.rotate(AngleSpng, 0, 0, 1);
		}
        if(spng==4){
        	MatrixState2D.scale(step/100,step/100,step/100);
        }
    	//�����ձ任������shader����
    	GLES30.glUniformMatrix4fv
    	(
    			muMVPMatrixHandle, 
    			1, 
    			false, 
    			MatrixState2D.getFinalMatrix(), 
    			0
    	); 
    	
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
    	GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,texId);
    	
    	//�����������--������
    	GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, vCount); 
    	
    	//�رջ��

    	MatrixState2D.popMatrix();//�ָ�����
    	GLES30.glDisable(GLES30.GL_BLEND);
    
    	
	}
	
	public void drawSelf(float lx,float ly)
	{        
		if(!initFlag)
		{
			//��ʼ����ɫ��        
			initShader();
			initFlag=true;
		}
		GLES30.glEnable(GLES30.GL_BLEND);//�򿪻��
		//���û������
		GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA,GLES30.GL_ONE_MINUS_SRC_ALPHA);
		//�ƶ�ʹ��ĳ��shader����
		GLES30.glUseProgram(programId);
		
		MatrixState2D.pushMatrix();//��������
		lx=Constant.fromScreenXToNearX(lx);
		ly=Constant.fromScreenYToNearY(ly);
		MatrixState2D.translate(lx,ly, 0);//ƽ��
		//�����ձ任������shader����
		GLES30.glUniformMatrix4fv
		(
				muMVPMatrixHandle, 
				1, 
				false, 
				MatrixState2D.getFinalMatrix(), 
				0
				); 
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
		GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,texId);
		
		//�����������--������
		GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, vCount); 
		//�رջ��
		GLES30.glDisable(GLES30.GL_BLEND);
		MatrixState2D.popMatrix();//�ָ�����
	}
}
