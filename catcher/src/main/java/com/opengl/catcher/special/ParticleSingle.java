package com.opengl.catcher.special;

import com.opengl.catcher.MatrixState.MatrixState3D;
import com.opengl.catcher.constant.SourceConstant;

public class ParticleSingle
{    
    public float x;
    public float y;
    public float vx;
    public float vy;
    public float lifeSpan;
    
    ParticleForDraw fpfd;
    
    public float vx1;
    public float vy1;
    public float vz1;
    public float x1;
    public float y1;
    public float z1;
    
    public ParticleSingle(float x,float y,float vx,float vy,ParticleForDraw fpfd)
    {
    	this.x=x;
    	this.y=y;
    	this.vx=vx;
    	this.vy=vy;
    	this.fpfd=fpfd;
    }
    public ParticleSingle(float x,float y,float vx,float vy,float vz,ParticleForDraw fpfd)
    {
    	this.vx1=vx;
    	this.vy1=vy;
    	this.vz1=vz;
    	this.x1=x;
    	this.y1=y;
    	this.z1=0;
    	this.fpfd=fpfd;
    }
    public void go(float lifeSpanStep)
    {
    	//���ӽ����ƶ��ķ�����ͬʱ��������ķ���
    	if(SourceConstant.SpecialBZ==5)
    	{//ˢ�°�ť���µ�����ϵͳ
    		x1=x1+vx1*lifeSpan/6.0f;
    		y1=y1+0.5f*1*lifeSpan*lifeSpan*2.0f;
    		z1=z1+vz1*lifeSpan/6.0f;
    	}
    	if(SourceConstant.SpecialBZ==2)
    	{
    		x1=x1+vx1*lifeSpan*0.02f;
    		y1=y1-0.5f*1*lifeSpan*lifeSpan;
    		z1=z1+vz1*lifeSpan*0.2f;
    	}
//    	else{
//        	x=x+vx;
//        	y=y+vy;
//    	}
    	lifeSpan+=lifeSpanStep;
    }
    
    public void drawSelf(float[] startColor,float[] endColor,float maxLifeSpan){
    	
    	MatrixState3D.pushMatrix();//�����ֳ�
    	if(SourceConstant.SpecialBZ==5){
    		MatrixState3D.translate(x1, y1, z1);
    	}
    	if(SourceConstant.SpecialBZ==2)
    	{
    		MatrixState3D.translate(x1, y1, z1);
    	}
//    	else{
//    		MatrixState3D.translate(x, y, 0);
//    	}
    	float sj=(maxLifeSpan-lifeSpan)/maxLifeSpan;//˥���������𽥵ı�С������Ϊ0
    	fpfd.drawSelf(sj,startColor,endColor);//���Ƶ�������   	
    	MatrixState3D.popMatrix();//�ָ��ֳ�
    }
}
