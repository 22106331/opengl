package com.opengl.catcher.special;

import java.util.ArrayList;
import java.util.List;
import com.opengl.catcher.MatrixState.MatrixState3D;
import com.opengl.catcher.util.manager.ShaderManager;
import com.opengl.catcher.util.manager.TextureManager;
import static com.opengl.catcher.constant.SourceConstant.*;
public class SpecialUtil
{
	List<ParticleSystem> fps=new ArrayList<ParticleSystem>();//������ϵͳ���б�
	ParticleForDraw[] fpfd;//ѩ�����ƵĶ���
	int count=0;
	int index=0;
	int index2=0;
	public SpecialUtil()
	{
		initSpecial();
	}
	public void initSpecial()
	{
		//�ܹ�����������ϵͳ
		count=ParticleDataConstant.START_COLOR.length;
		fpfd=new ParticleForDraw[count];//6������ţ�6����ɫ
		for(int i=0;i<count;i++)
		{
			ParticleDataConstant.CURR_INDEX=i;
			if(i==0)//����0
			{
				fpfd[i]=new ParticleForDraw(ParticleDataConstant.RADIS[ParticleDataConstant.CURR_INDEX],
						ShaderManager.getShader(4),TextureManager.getTextures("fire.png"));
				//��������,��ѩ���ĳ�ʼλ�ô���������
				fps.add(new ParticleSystem(0,0,0,fpfd[i]));
			}else if(i>=1&&i<=3)//��ײ1
			{
				fpfd[i]=new ParticleForDraw(ParticleDataConstant.RADIS[ParticleDataConstant.CURR_INDEX],
						ShaderManager.getShader(4),TextureManager.getTextures("stars.png"));
				//��������,��ѩ���ĳ�ʼλ�ô���������
				fps.add(new ParticleSystem(0,0,0,fpfd[i]));
			}else if(i>3)//�̻�2
			{
				fpfd[i]=new ParticleForDraw(ParticleDataConstant.RADIS[ParticleDataConstant.CURR_INDEX],
						ShaderManager.getShader(4),TextureManager.getTextures("stars2.png"));
				//��������,��ѩ���ĳ�ʼλ�ô���������
				fps.add(new ParticleSystem(0,0,0,fpfd[i]));
			}
		}
	}
	
	public void drawSpecial(int i)
	{ 
		if(i==5){
			//ˢ�¹��ܵ�����ϵͳ
			MatrixState3D.pushMatrix();
			fps.get(5).positionX=0.0f;
			fps.get(5).positionY=0.0f;
			fps.get(5).positionZ=10.5f;
			SpecialBZ=5;
			fps.get(5).drawSelf();
			MatrixState3D.popMatrix();
		}else if(i==2){
			MatrixState3D.pushMatrix();
			fps.get(2).positionX=0.0f;
			fps.get(2).positionY=1.5f;
			fps.get(2).positionZ=10.5f;
			SpecialBZ=2;
			fps.get(2).drawSelf();
			MatrixState3D.popMatrix();
		}
	}
}
