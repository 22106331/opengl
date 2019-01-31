package com.opengl.catcher.view;

import java.util.ArrayList;
import java.util.List;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES30;
import android.view.MotionEvent;

import com.opengl.catcher.MainActivity;
import com.opengl.catcher.MatrixState.MatrixState3D;
import com.opengl.catcher.catcherFun.MySurfaceView;
import com.opengl.catcher.constant.Constant;
import com.opengl.catcher.object.BN2DObject;
import com.opengl.catcher.util.DrawNumber;
import com.opengl.catcher.util.manager.ShaderManager;
import com.opengl.catcher.util.manager.TextureManager;

import static com.opengl.catcher.constant.SourceConstant.*;

public class ScoreView extends BNAbstractView{
	MySurfaceView mv;
	DrawNumber score;
	float PreviousX;
	float PreviousY;
	public static  boolean isPrecent=false;
	public static List<BN2DObject> backgroundlist=new ArrayList<BN2DObject>();//���BNObject����
	public static List<Integer> Scorelist=new ArrayList<Integer>();//���BNObject����
	public static float[][] scorelocationdata=new float[5][2];
	int spany=200;
	public ScoreView(MySurfaceView mv)
	{
		this.mv=mv;
		
		initView();
		
	}
	public void initView() 
	{		
		initbackgroundlist();
		calculateScore();
		initnumberLocationData();
		score=new DrawNumber(mv);
	}
	public void initbackgroundlist()
	{
		backgroundlist.add(0,new BN2DObject(540, 960, 1080, 
				1920, TextureManager.getTextures("score_background.png"),
				ShaderManager.getShader(2)));
		backgroundlist.add(1,new BN2DObject(132,1650,150, 
				150, TextureManager.getTextures("back.png"), 
				ShaderManager.getShader(2)));

	}
	public void calculateScore()
	{
		Scorelist.add(allcount);
		Scorelist.add(getcount);
		failcount=allcount-getcount;
		Scorelist.add(failcount);
		for(int i=0;i<dollcount.length;i++)
		{
			if(dollcount[i]!=0)
			{
				getdolltypecount++;
			}
		}
		getcollectionpercent=(int) (getdolltypecount/9.0*100);
		System.out.println("getcollectionpercent:   "+getcollectionpercent);
	}
	public void initnumberLocationData()
	{
        for(int i=0;i<3;i++)
        { 

        	scorelocationdata[i][0]=900;
        	scorelocationdata[i][1]=550+i*spany;
        }

        
	}
	public void drawcount()
	{
		for(int i=0;i<3;i++)
		{
			initdatax=scorelocationdata[i][0];
			initdatay=scorelocationdata[i][1];
		    score.drawScore(Scorelist.get(i));
		}
		
		
		isPrecent=true;
		initdatax=900;
		initdatay=1120;
		score.drawScore(getcollectionpercent);
		isPrecent=false;
		initdatax=900;
		initdatay=1320;
		score.drawScore(getdolltypecount);
	
	}
	
	public boolean onTouchEvent(MotionEvent e) 
	{
		float x=Constant.fromRealScreenXToStandardScreenX(e.getX());//��ȡ���ص������
		float y=Constant.fromRealScreenYToStandardScreenY(e.getY());
		switch(e.getAction())
    	{
		
	    	case  MotionEvent.ACTION_MOVE:
				break;
	    	case MotionEvent.ACTION_UP:
	    		
	    		break;
	    	case MotionEvent.ACTION_DOWN:
	    		
	    	    if(x>scoreback_left&&x<scoreback_right&&y>scoreback_top&&y<scoreback_bottom)
		     	{
	    	    	 mv.mainView.reSetData();
    			  	 mv.currView=mv.mainView;
					 if(!effictOff){
				    		MainActivity.sound.playMusic(SOUND_Back,0);
				     }
		     	}
	    	   
	    		break;
    	}
		PreviousX=x;
		PreviousY=y;
		
		return true;
	}
	@Override
	public void drawView(GL10 gl) {
		GLES30.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);   
				  MatrixState3D.setCamera(
	         		0,   //����λ�õ�X
	         		4, 	//����λ�õ�Y
	         		22,   //����λ�õ�Z
	         		0, 	//�����򿴵ĵ�X
	         		4,   //�����򿴵ĵ�Y
	         		14,   //�����򿴵ĵ�Z
	         		0, 
	         		1, 
	         		0);
		GLES30.glDisable(GLES30.GL_DEPTH_TEST); 	
		for(int i=0;i<backgroundlist.size();i++)
		{
			backgroundlist.get(i).drawSelf();	
		}
		drawcount();
		GLES30.glEnable(GLES30.GL_DEPTH_TEST); 
		
	}


}
