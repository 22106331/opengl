package com.opengl.catcher.constant;

import com.opengl.catcher.MainActivity;
import com.opengl.catcher.util.screenscale.ScreenScaleResult;

public class Constant
{
	static MainActivity mainActivity;
    //=======��Ļ����Ӧ����=======start=======================================================//
	public static float SCREEN_WIDTH_STANDARD = 1080;//720;//��Ļ��׼���	
	public static float SCREEN_HEIGHT_STANDARD = 1920;//1280;//��Ļ��׼�߶�
	public static float RATIO = SCREEN_WIDTH_STANDARD/SCREEN_HEIGHT_STANDARD;//��Ļ��׼����--��͸��ͶӰ�ı���
	public static ScreenScaleResult ssr;//��Ļ����Ӧ����
	//=======��Ļ����Ӧ����=======end=========================================================//
	
	
	public static float fromPixSizeToNearSize(float size)
	{
		return size*2/SCREEN_HEIGHT_STANDARD;
	}
	//��Ļx���굽�ӿ�x����
	public static float fromScreenXToNearX(float x)
	{
		return (x-SCREEN_WIDTH_STANDARD/2)/(SCREEN_HEIGHT_STANDARD/2);
	}
	//��Ļy���굽�ӿ�y����
	public static float fromScreenYToNearY(float y)
	{
		return -(y-SCREEN_HEIGHT_STANDARD/2)/(SCREEN_HEIGHT_STANDARD/2);
	}
	//ʵ����Ļx���굽��׼��Ļx����
	public static float fromRealScreenXToStandardScreenX(float rx)
	{
		return (rx-ssr.lucX)/ssr.ratio;
	}
	//ʵ����Ļy���굽��׼��Ļy����
	public static float fromRealScreenYToStandardScreenY(float ry)
	{
		return (ry-ssr.lucY)/ssr.ratio;
	}
	//�ӱ�׼��Ļ��ʵ����Ļx����
	public static float fromStandardScreenXToRealScreenX(float tx)
	{
		return tx*ssr.ratio+ssr.lucX;
	}
	//�ӱ�׼��Ļ��ʵ����Ļy����
	public static float fromStandardScreenYToRealScreenY(float ty)
	{
		return ty*ssr.ratio+ssr.lucY;
	}
	public static float fromStandardScreenSizeToRealScreenSize(float size)
	{
		return size*ssr.ratio;
	}
}
