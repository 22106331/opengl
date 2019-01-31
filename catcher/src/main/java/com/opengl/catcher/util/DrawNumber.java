package com.opengl.catcher.util;


import com.opengl.catcher.MatrixState.MatrixState2D;
import com.opengl.catcher.catcherFun.MySurfaceView;
import com.opengl.catcher.object.BN2DObject;
import com.opengl.catcher.view.CollectionView;
import com.opengl.catcher.view.MainView;
import com.opengl.catcher.view.ScoreView;

import static com.opengl.catcher.constant.SourceConstant.initdatax;
import static com.opengl.catcher.constant.SourceConstant.initdatay;

public class DrawNumber
{
	MySurfaceView mv;
	BN2DObject[] numberReds=new BN2DObject[12];//������������
	public DrawNumber(MySurfaceView mv)
	{
		this.mv=mv;		
		//����0-9ʮ�����ֵ��������
		for(int i=0;i<12;i++)
		{
			numberReds[i]=CollectionView.numberlist.get(i);
		}
	}
	
	public void drawScore(int count)//���Ʒ����ķ���
	{
		String str=count+"";//��int��ֵת��Ϊ�ַ���
		for(int i=0;i<str.length();i++)
		{
			char c=str.charAt(i);//��ȡ�ַ���ָ���ַ�
			  MatrixState2D.pushMatrix();//�����ֳ�
			  numberReds[c-'0'].drawSelf(initdatax+i*50-str.length()*20,initdatay);	//����ָ������
			  if(ScoreView.isPrecent)//����ɶȱ�־λΪtrue
			  {
				  numberReds[11].drawSelf(initdatax+str.length()*50-str.length()*20,initdatay);	//���ưٷֺ�
				  ScoreView.isPrecent=false;//��־�÷�
			  }
			  MatrixState2D.popMatrix();//�ָ��ֳ�
		}
	}
	
	public void drawnumber(int count)
	{					
			
		String str=count+"";
		for(int i=0;i<str.length();i++)
		{
			char c=str.charAt(i);
			  MatrixState2D.pushMatrix();
			 
			  if(MainView.isMainView)
			  {
				  MatrixState2D.scale(0.6f, 0.6f, 0.6f);
			  }
			  numberReds[10].drawSelf(initdatax,initdatay);	
			  numberReds[c-'0'].drawSelf(initdatax+(i+1)*50,initdatay);	
			  MatrixState2D.popMatrix();
		}
		
	}
}
