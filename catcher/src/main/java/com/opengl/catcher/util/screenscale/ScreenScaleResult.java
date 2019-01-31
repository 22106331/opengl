package com.opengl.catcher.util.screenscale;

enum ScreenOrien
{
	HP,  //��ʾ������ö��ֵ
	SP   //��ʾ������ö��ֵ
}
//���ż���Ľ��
public class ScreenScaleResult
{
	public int lucX;//���Ͻ�X����
	public int lucY;//���Ͻ�y����
	public float ratio;//���ű���
	ScreenOrien so;//���������	
	
	public ScreenScaleResult(int lucX,int lucY,float ratio,ScreenOrien so)
	{
		this.lucX=lucX;
		this.lucY=lucY;
		this.ratio=ratio;
		this.so=so;
	}
	
	public String toString()
	{
		return "lucX="+lucX+", lucY="+lucY+", ratio="+ratio+", "+so;
	}
}