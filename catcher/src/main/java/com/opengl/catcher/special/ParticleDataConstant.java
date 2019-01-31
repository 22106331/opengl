package com.opengl.catcher.special;

import android.opengl.GLES30;
     
public class ParticleDataConstant 
{
	//��ǰ����  
    public static int CURR_INDEX=5; 
    //��ʼ��ɫ
    public static final float[][] START_COLOR=
	{
    	{0.7569f,0.2471f,0.1176f,1.0f},	//0-��ͨ����
    	
    	{0.9882f,0.9765f,0.0118f,1.0f},	//��ɫ
    	{0.9882f,0.0196f,0.8863f,1.0f},	//�ۺ�ɫ
    	
    	
    	
    	{0.9804f,0.9804f,0.9804f,1.0f},//��ɫ
    	{0.9882f,0.9765f,0.0118f,1.0f},//��ɫ
    	{0.9882f,0.9765f,0.0118f,1.0f},//��ɫ
	};
    
    //��ֹ��ɫ
    public static final float[][] END_COLOR=
	{
    	{0.0f,0.0f,0.0f,0.0f},//0-��ͨ����
    	{0.0f,0.0f,0.0f,0.0f},//��ɫ
    	{1.0f,0.8431f,0.0f,0.0f},//��ɫ
    	
    	{0.9882f,0.0196f,0.8863f,0.0f},//�ۺ�ɫ
    	{0.1608f,0.9725f,0.2157f,0.0f},//��ɫ
    	{0.1608f,0.9725f,0.2157f,0.0f},//��ɫ
	};
    
    //Դ�������
    public static final int[] SRC_BLEND=
	{
    	GLES30.GL_SRC_ALPHA,
    	GLES30.GL_SRC_ALPHA,
    	GLES30.GL_SRC_ALPHA,
    	GLES30.GL_SRC_ALPHA,
    	GLES30.GL_SRC_ALPHA,
    	GLES30.GL_SRC_ALPHA,
	};
    
    //Ŀ��������
    public static final int[] DST_BLEND=
	{
    	GLES30.GL_ONE,
    	GLES30.GL_ONE,
    	GLES30.GL_ONE,
    	GLES30.GL_ONE,
    	GLES30.GL_ONE,
    	GLES30.GL_ONE,
	};
    
    //��Ϸ�ʽ
    public static final int[] BLEND_FUNC=
	{
    	GLES30.GL_FUNC_ADD,    				//0-��ͨ����
    	GLES30.GL_FUNC_ADD,
    	GLES30.GL_FUNC_ADD,
    	GLES30.GL_FUNC_ADD,
    	GLES30.GL_FUNC_ADD,
    	GLES30.GL_FUNC_ADD,
	};
    
    //�������Ӱ뾶
    public static final float[] RADIS=
    {
    	0.4f,		//0-��ͨ����
    	
    	0.3f,
    	0.2f,
    	
    	0.2f,
    	0.15f,
    	0.6f,
    };
    
    //�������������
    public static final float[] MAX_LIFE_SPAN=
    {
    	5.0f,		//0-��ͨ����
    	
    	2f,
    	1.2f,
    	
    	4f,
    	4f,
    	4f,
    };
    
    //�����������ڲ���
    public static final float[] LIFE_SPAN_STEP=
    {
    	0.2f,
    	
    	0.1f,
    	0.1f,
    	
    	0.03f,
    	0.03f,
    	0.01f,
    };
    
    //���ӷ����X���ҷ�Χ
    public static final float[] X_RANGE=
	{
	    0.05f,		//0-��ͨ����
	    
	    1f,		//0-��ͨ����
    	0.3f,
    	
    	0.8f,
    	0.8f,
    	2.0f,
	};
    
    //���ӷ����Y���·�Χ
    public static final float[] Y_RANGE=
	{
	    1f,		//0-��ͨ����
	    
	    0.8f,		//0-��ͨ����d
	    0.8f,
	    
	    1.8f,
	    1.8f,
	    1.0f,
	};
    
    //ÿ���緢���������
    public static final int[] GROUP_COUNT=
	{
    	10,
    	
    	1,
    	5,
    	
    	5,
    	4,
    	1,
	};
    
    //����Y�������ڵ��ٶ�
    public static final float[] VY=
	{
    	0.08f,
    	
    	0.02f,
    	0.02f,
    	
    	0.015f,
    	0.015f,
    	0.050f,
	};
    
    //���Ӹ��������߳���Ϣʱ��
    public static final int[] THREAD_SLEEP=
    {
    	15,
    	
    	16,
    	16,
    	
    	15,
    	15,
    	15,
    };
}
