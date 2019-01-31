 package com.opengl.catcher.util.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLUtils;

import com.opengl.catcher.catcherFun.MySurfaceView;

 public class TextureManager
{
	static String[] texturesName={
		"claw.png","gan.png","hengtiao.png","floor.jpg","showscore.png",//5
		"jb.png","down.png","up.png","tod.png","tou.png",//10
		"tol.png","tor.png","doll1.png","doll2.png","hole.png",//15
		"doll0.png","dollbox.png","holebox.png","niu.png","dunId.png",//20
		"tv.png","catch.png","MainView_Background.png","Button_StartDown.png","Button_Start.png",//25
		
		"load.png","config_collectionsDown.png","config_collections.png","Button_Tutorail.png","Button_TutorailDown.png",//30
		"Box1.png","MainGame_start.png","MainGame_startDown.png","Tex_MoneyBox.png","Tex_Money.png"//35
		,"ganbox.png","HB.png","shuaxin.png","shuaxin_Down.png","parrot.png",//40
		"menu.png","set.png","off.png","0.png","1.png",//45
		"2.png","3.png","4.png","5.png","6.png",//50
		"7.png","8.png","9.png","x.png","background.png",//55
		"shutiao.png","back.png","salebackground.png","xing1.png","xing2.png",//60
		"sell.png","sell_down.png","car.png","camera.png","robot.png",//65
		"page0.png","page1.png","page2.png","page3.png","page4.png",//70
		"button_back.png","Game_AboutDown.png","aboutText.png","Game_About.png","Button_Config.png",//75
		"Button_ConfigDown.png","button_score.png","button_score_Down.png","score_background.png","%.png",//80
		"lock.png","message.png","Box2.png","catchbackground.png","stars.png",//85
		"stars2.png","fire.png","lu.png","button_quit_Down.png","button_quit.png",//90
		"lu1.png","backText.png",//93
		
		};//����ͼ������
	
	static HashMap<String,Integer> texList=new HashMap<String,Integer>();//������ͼ���б�
	public static int initTexture(MySurfaceView mv, String texName, boolean isRepeat)//��������id
	{
		int[] textures=new int[1];
		GLES30.glGenTextures
		(
				1,//����������id������
				textures,//����id������
				0//ƫ����
		);
		GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textures[0]);//������id
		//����MAGʱΪ���Բ���
		GLES30.glTexParameterf
		(
				GLES30.GL_TEXTURE_2D,
				GLES30.GL_TEXTURE_MAG_FILTER,
				GLES30.GL_LINEAR
		);
		//����MINʱΪ��������
		GLES30.glTexParameterf
		(
				GLES30.GL_TEXTURE_2D,
				GLES30.GL_TEXTURE_MIN_FILTER, 
				GLES30.GL_NEAREST
		);
		if(isRepeat)
		{
			//����S������췽ʽΪ�ظ�����
			GLES30.glTexParameterf
			(
					GLES30.GL_TEXTURE_2D,
					GLES30.GL_TEXTURE_WRAP_S, 
					GLES30.GL_REPEAT
			);
			//����T������췽ʽΪ�ظ�����
			GLES30.glTexParameterf
			(
					GLES30.GL_TEXTURE_2D,
					GLES30.GL_TEXTURE_WRAP_T, 
					GLES30.GL_REPEAT
			);
		}else
		{
			//����S������췽ʽΪ��ȡ
			GLES30.glTexParameterf
			(
					GLES30.GL_TEXTURE_2D,
					GLES30.GL_TEXTURE_WRAP_S, 
					GLES30.GL_CLAMP_TO_EDGE
			);
			//����T������췽ʽΪ��ȡ
			GLES30.glTexParameterf
			(
					GLES30.GL_TEXTURE_2D,
					GLES30.GL_TEXTURE_WRAP_T, 
					GLES30.GL_CLAMP_TO_EDGE
			);
		}
		String path="pic/"+texName;//����ͼƬ·��
		InputStream in = null;
		try {
			in = mv.getResources().getAssets().open(path);
		}catch (IOException e) {
			e.printStackTrace();
		}
		Bitmap bitmap=BitmapFactory.decodeStream(in);//�����м���ͼƬ����
		GLUtils.texImage2D
		(
				GLES30.GL_TEXTURE_2D,//�������ͣ���OpenGL ES�б���ΪGL10.GL_TEXTURE_2D
				0,//����Ĳ�Σ�0��ʾ����ͼ��㣬�������Ϊֱ����ͼ
				bitmap,//����ͼ��
				0//����߿�ߴ�
		);
		bitmap.recycle();//������سɹ����ͷ��ڴ��е�����ͼ
		return textures[0];
	}
	
	public static void loadingTexture(MySurfaceView mv,int start,int picNum)//������������ͼ
	{
		for(int i=start;i<start+picNum;i++)
		{
			int texture=0;
			if((texturesName[i].equals("claw.png"))||(texturesName[i].equals("gan.png"))
					||(texturesName[i].equals("f6.png"))||(texturesName[i].equals("floor.jpg"))
					||(texturesName[i].equals("doll1.png"))||(texturesName[i].equals("doll2.png"))
					||(texturesName[i].equals("hole.png"))||(texturesName[i].equals("doll0.png"))
					||(texturesName[i].equals("dollbox.png"))
					||(texturesName[i].equals("holebox.png"))||(texturesName[i].equals("ganbox.png"))
					||(texturesName[i].equals("car.png"))||(texturesName[i].equals("camera.png"))
					||(texturesName[i].equals("robot.png"))||(texturesName[i].equals("jb.png"))
					||(texturesName[i].equals("floor1.png"))
					)
			{
				texture=initTexture(mv,texturesName[i],true);
			}else
			{
				texture=initTexture(mv,texturesName[i],false);
			}
			texList.put(texturesName[i],texture);//�����ݼ��뵽�б���
		}
	}
	public static int getTextures(String texName)//�������ͼ
	{
		int result=0;
		if(texList.get(texName)!=null)//����б����д�����ͼ
		{
			result=texList.get(texName);//��ȡ����ͼ
		}else
		{
			result=-1;
		}
		return result;
	}
}
