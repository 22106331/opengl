package com.opengl.study.example2;

import android.opengl.Matrix;

//�洢ϵͳ����״̬����
public class MatrixState 
{
	private static float[] mProjMatrix = new float[16];//4x4���� ͶӰ��
    private static float[] mVMatrix = new float[16];//�����λ�ó���9��������
    private static float[] mMVPMatrix;//���յ��ܱ任����
    
    //����������ķ���
    public static void setCamera
    (
    		float cx,	
    		float cy,   
    		float cz,  
    		float tx,   
    		float ty,   
    		float tz,  
    		float upx,  
    		float upy,  
    		float upz   	
    )
    {
    	Matrix.setLookAtM
        (
        		mVMatrix, 	//�洢���ɾ���Ԫ�ص�float[]��������
        		0, 			//�����ʼƫ����
        		cx,cy,cz,	//�����λ�õ�X��Y��Z����
        		tx,ty,tz,	//�۲�Ŀ���X��Y��Z����
        		upx,upy,upz	//up������X��Y��Z���ϵķ���
        );
    }

	//设置透视投影
	public static void setProjectFrustum(
			float left, // near面的left
			float right, // near面的right
			float bottom, // near面的bottom
			float top, // near面的top
			float near, // near面与视点的距离
			float far // far面与视点的距离
	) {
		Matrix.frustumM(mProjMatrix, 0, left, right, bottom, top, near, far);
	}

    //��������ͶӰ�ķ���
    public static void setProjectOrtho
    (
    	float left,		
    	float right,    
    	float bottom,  
    	float top,      
    	float near,		
    	float far       
    )
    {    	
    	Matrix.orthoM
    	(
    			mProjMatrix, 	//�洢���ɾ���Ԫ�ص�float[]��������
    			0, 				//�����ʼƫ����
    			left, right,	//near���left��right 
    			bottom, top, 	//near���bottom��top
    			near, far		//near�桢far�����ӵ�ľ���
    	);
    }   
   
    //��ȡ����������ܱ任����
    public static float[] getFinalMatrix(float[] spec)//���������ܱ任����ķ���  
    {
    	mMVPMatrix=new float[16];//��������������ձ任���������
    	Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, spec, 0); //�������������Ա任����
    	//��ͶӰ���������һ���Ľ������õ����ձ任����
    	Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);        
        return mMVPMatrix;
    }
}
