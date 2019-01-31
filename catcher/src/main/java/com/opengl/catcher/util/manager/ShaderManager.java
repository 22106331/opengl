package com.opengl.catcher.util.manager;


import android.content.res.Resources;

import com.opengl.catcher.util.ShaderUtil;

public class ShaderManager
{
	final static int shaderCount=6;
	final static String[][] shaderName=
	{
		{"vertex.sh","frag.sh"},//0
		{"vertex_load2d.sh","frag_load2d.sh"},//1
		{"vertex_2d.sh","frag_2d.sh"},//2
		{"holebox_vertex.sh","holebox_frag.sh"},//3
		{"vertex_lz.sh","frag_lz.sh"},//4
		{"vertex_spng.sh","frag_spng.sh"},//5
		
	};
	static String[]mVertexShader=new String[shaderCount];
	static String[]mFragmentShader=new String[shaderCount];
	static int[] program=new int[shaderCount];
	
	public static void loadCodeFromFile(Resources r)
	{
		for(int i=0;i<shaderCount;i++)
		{
			//���ض�����ɫ���Ľű�����       
	        mVertexShader[i]=ShaderUtil.loadFromAssetsFile(shaderName[i][0],r);
	        //����ƬԪ��ɫ���Ľű����� 
	        mFragmentShader[i]=ShaderUtil.loadFromAssetsFile(shaderName[i][1], r);
		}	
	}
	
	//����3D�����shader
	public static void compileShader()
	{
		for(int i=0;i<shaderCount;i++)
		{
			program[i]=ShaderUtil.createProgram(mVertexShader[i], mFragmentShader[i]);
		}
	}
	//���ﷵ�ص��������shader����
	public static int getShader(int index)
	{
		return program[index];
	}
		
}
