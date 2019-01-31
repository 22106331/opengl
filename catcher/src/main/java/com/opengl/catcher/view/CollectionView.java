package com.opengl.catcher.view;

import java.util.ArrayList;
import java.util.List;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES30;
import android.view.MotionEvent;

import com.opengl.catcher.MainActivity;
import com.opengl.catcher.MatrixState.MatrixState2D;
import com.opengl.catcher.MatrixState.MatrixState3D;
import com.opengl.catcher.catcherFun.MySurfaceView;
import com.opengl.catcher.constant.Constant;
import com.opengl.catcher.object.BN2DObject;
import com.opengl.catcher.object.LoadedObjectVertexNormalTexture;
import com.opengl.catcher.util.DrawLine;
import com.opengl.catcher.util.DrawNumber;
import com.opengl.catcher.util.manager.ShaderManager;
import com.opengl.catcher.util.manager.TextureManager;

import static com.opengl.catcher.constant.SourceConstant.*;

public class CollectionView extends BNAbstractView{
	MySurfaceView mv;//��������������
	SellView saleview;//���۽�������
	DrawNumber score;//���Ʒ�����������
	DrawLine drawline;//���ƻ�ɫ��������
	float PreviousX;//��һ�δ���λ��X����
	float PreviousY;//��һ�δ���λ��Y����
	static float angle=-90;//������ʼ��ת�Ƕ�
	static int dt=1;//������ת�ٶ�
	public static float ObjX=-5.5f;//������ʼλ��X����
	public static float ObjY=11f;//������ʼλ��Y����
	
	public static List<BN2DObject> numberlist=new ArrayList<BN2DObject>();//������ֶ���
	
	public static List<LoadedObjectVertexNormalTexture> dollobj=new ArrayList<LoadedObjectVertexNormalTexture>();//�������obi����
	public static List<Integer> textureId=new ArrayList<Integer>();//��������ID����
	public static List<BN2DObject> backgroundlist=new ArrayList<BN2DObject>();//�ղؽ��汳��ͼ���
	public static List<Float> objscale=new ArrayList<Float>();//���޵�����С��������
	public static float[][] numberlocationdata=new float[9][2];//�����������ֻ���λ�ü���
	public static List<String> numberaward=new ArrayList<String>();//�����Ƿ������߱�־����
	int spanx=380;//��������ͼƬX���
	int spany=500;//��������ͼƬY���
	public  static boolean[] isOntouch=new boolean[9];//�Ƿ��������ޱ�־λ����
	public static boolean islock=true;//�м������Ƿ��ڱ���״̬��־λ
	public static boolean isSale=false;//�Ƿ���Ҫ���۱�־λ
	public CollectionView(MySurfaceView mv)
	{
		this.mv=mv;
		initView();//��ʼ������ķ���
		
	}
	public void initView() 
	{		
		initbackgroundlist();//���ؽ��汳��ͼ�ķ���
		initnumberlist();//��������ͼƬ�ķ���
		initnumberLocationData();//��ʼ������λ�õķ���
		initDoll();//��ʼ��������ز����ķ���
	
		score=new DrawNumber(mv);//�����������ֶ���
		drawline=new DrawLine(mv);//�������ƻ�ɫ���ߵĶ���
		saleview=new SellView(mv);	//�������۽������
		
	}
	public void initnumberLocationData()
	{
        for(int i=0;i<9;i++)//ѭ�����������������ֵ�λ��
        { 
        	numberlocationdata[i][0]=160+(i%3)*spanx;//X����
        	numberlocationdata[i][1]=600+(i/3)*spany;//Y����
        }
		
	}
	public void initbackgroundlist()//���ؽ��汳��ͼ�ķ���
	{
		backgroundlist.add(0,new BN2DObject(540, 960, 1080, 
				1920, TextureManager.getTextures("background.png"), 
				ShaderManager.getShader(2)));
		backgroundlist.add(1,new BN2DObject(390, 460, 420, 
				110, TextureManager.getTextures("hengtiao.png"),
				ShaderManager.getShader(2)));
		backgroundlist.add(2,new BN2DObject(775, 460, 420, 
				110, TextureManager.getTextures("hengtiao.png"), 
				ShaderManager.getShader(2)));
		backgroundlist.add(3,new BN2DObject(395, 970,420, 
				110, TextureManager.getTextures("hengtiao.png"), 
				ShaderManager.getShader(2)));
		backgroundlist.add(4,new BN2DObject(780, 970, 420, 
				110, TextureManager.getTextures("hengtiao.png"), 
				ShaderManager.getShader(2)));
		backgroundlist.add(5,new BN2DObject(390,1465, 420, 
				110, TextureManager.getTextures("hengtiao.png"), 
				ShaderManager.getShader(2)));
		backgroundlist.add(6,new BN2DObject(780,1465, 420, 
				110, TextureManager.getTextures("hengtiao.png"), 
				ShaderManager.getShader(2)));
		backgroundlist.add(7,new BN2DObject(195,700, 120, 
				500, TextureManager.getTextures("shutiao.png"), 
				ShaderManager.getShader(2)));
		backgroundlist.add(8,new BN2DObject(560,715, 120, 
				500, TextureManager.getTextures("shutiao.png"), 
				ShaderManager.getShader(2)));
		backgroundlist.add(9,new BN2DObject(935,705, 120, 
				500, TextureManager.getTextures("shutiao.png"), 
				ShaderManager.getShader(2)));
		backgroundlist.add(10,new BN2DObject(195,1195, 120, 
				500, TextureManager.getTextures("shutiao.png"), 
				ShaderManager.getShader(2)));
		backgroundlist.add(11,new BN2DObject(555,1195, 120, 
				500, TextureManager.getTextures("shutiao.png"), 
				ShaderManager.getShader(2)));
		backgroundlist.add(12,new BN2DObject(935,1195, 120, 
				500, TextureManager.getTextures("shutiao.png"), 
				ShaderManager.getShader(2)));
		
		backgroundlist.add(13,new BN2DObject(100,200, 150, 
				150, TextureManager.getTextures("Tex_Money.png"), 
				ShaderManager.getShader(2)));
		
		backgroundlist.add(14,new BN2DObject(132,1750,150, 
				150, TextureManager.getTextures("back.png"), 
				ShaderManager.getShader(2)));
		backgroundlist.add(15,new BN2DObject(560,900,180, 
				200, TextureManager.getTextures("lock.png"), 
				ShaderManager.getShader(2)));
	}
	public void initnumberlist()//��������ͼƬ�ķ���
	{
		numberlist.add(0,new BN2DObject(0, 0,80, 80, TextureManager.getTextures("0.png"), 
				ShaderManager.getShader(2)));
		numberlist.add(1,new BN2DObject(0, 0,80, 80, TextureManager.getTextures("1.png"), 
				ShaderManager.getShader(2)));
		numberlist.add(2,new BN2DObject(0, 0,80, 80, TextureManager.getTextures("2.png"), 
				ShaderManager.getShader(2)));
		numberlist.add(3,new BN2DObject(0, 0,80, 80, TextureManager.getTextures("3.png"), 
				ShaderManager.getShader(2)));
		numberlist.add(4,new BN2DObject(0, 0,80, 80, TextureManager.getTextures("4.png"), 
				ShaderManager.getShader(2)));
		numberlist.add(5,new BN2DObject(0, 0,80, 80, TextureManager.getTextures("5.png"), 
				ShaderManager.getShader(2)));
		numberlist.add(6,new BN2DObject(0, 0,80, 80, TextureManager.getTextures("6.png"), 
				ShaderManager.getShader(2)));
		numberlist.add(7,new BN2DObject(0, 0,80, 80, TextureManager.getTextures("7.png"), 
				ShaderManager.getShader(2)));
		numberlist.add(8,new BN2DObject(0,0,80, 80, TextureManager.getTextures("8.png"), 
				ShaderManager.getShader(2)));
		numberlist.add(9,new BN2DObject(0, 0,80, 80, TextureManager.getTextures("9.png"), 
				ShaderManager.getShader(2)));
		numberlist.add(10,new BN2DObject(0, 0,60, 60, TextureManager.getTextures("x.png"), 
				ShaderManager.getShader(2)));
		numberlist.add(11,new BN2DObject(0, 0,80, 80, TextureManager.getTextures("%.png"), 
				ShaderManager.getShader(2)));
		
	}
	public void drawcount()//������������λ�û�����������
	{
		for(int i=0;i<dollcount.length;i++)
		{
			initdatax=numberlocationdata[i][0];
			initdatay=numberlocationdata[i][1];
		    score.drawnumber(dollcount[i]);
		}
		initdatax=200;
		initdatay=200;
		score.drawnumber(moneycount);
	}
	public void initDoll()//��ʼ�����޵���ز���
	{
		dollobj.add(0,niu);textureId.add(niuId);objscale.add(2f);numberaward.add("012");
		dollobj.add(1,doll0);textureId.add(doll0Id);objscale.add(2f);numberaward.add("345");
		dollobj.add(2,doll2);textureId.add(doll2Id);objscale.add(2f);numberaward.add("678");
		dollobj.add(3,ParrotMd);textureId.add(parrotId);objscale.add(8f);numberaward.add("036");
		dollobj.add(4,RobotMD);textureId.add(robotId);objscale.add(9f);numberaward.add("147");
		dollobj.add(5,CarMD);textureId.add(CarId);objscale.add(8f);numberaward.add("258");
		dollobj.add(6,tvmodle);textureId.add(tvId);objscale.add(2f);
		dollobj.add(7,doll1);textureId.add(doll1Id);objscale.add(2f);
		dollobj.add(8,Camera);textureId.add(CameraId);objscale.add(7.5f);
		
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
	    	    if(x>back_left&&x<back_right&&y>back_top&&y<back_bottom&&!isSale)
		     	{//������ذ�ť
	    			 if(!effictOff){
	    				 MainActivity.sound.playMusic(SOUND_Back,0);//���ű�������
	    			 }
		    		  if(isCollection)
		    		  {
		     			isCollection=false;
		     			mv.currView=mv.gameView;//������Ϸ����
		     			mv.gameView.isMenu=false;
		     			mv.gameView.reData();
		    		  }else
		    		  {
		    			  mv.mainView.reSetData();
		    			  mv.currView=mv.mainView;//����������
		    		  }
		    		   
		    		  if(isSet)
		    		  {
		    			  isSet=false;
		    			  mv.currView=mv.mainView;
		    		  }
		     	}
	    	    for(int i=0;i<9;i++)
	    	    {
	    	    	if(x>numberlocationdata[i][0]-90&&x<numberlocationdata[i][0]+140
	    	    			&&y>numberlocationdata[i][1]-300&&y<numberlocationdata[i][1]-60&&!isSale)
	    	    	{//������޽�����۽���
	    	    		if(i==4&&islock)
	    	    		{
	    	    			break;
	    	    		}else
	    	    		{
		    	    		isOntouch[i]=true;
		    	    		isSale=true;
	    	    		}
	    	    		break;
	    	    	}
	    	    }
	    	   
	    		break;
    	}
		PreviousX=x;
		PreviousY=y;
		 
			 if(isSale)
			 {
				 return saleview.onTouchEvent(e);
			 }
		
		return true;
	}
	public static void CalculateAward()//���㽱��
	{
		
		
		List<String> removeAward=new ArrayList<String>();
		for(String a:numberaward)
		{
		  if(dollcount[a.charAt(0)-'0']!=0&&dollcount[a.charAt(1)-'0']!=0&&dollcount[a.charAt(2)-'0']!=0)
		  {
			moneycount=moneycount+3;
			removeAward.add(a);
		  }
		}
		for(int i=0;i<removeAward.size();i++)
		{
			
			numberaward.remove(removeAward.get(i));
		}
		if(numberaward.size()==2)
		{
		  
			  islock=false;
		}
		
	}
	public void drawdollObjAndCount()//�������޼��������ķ���
	{
		angle=angle+dt;
		if(angle>0)
		{
			dt=-1;
		}else if(angle<-180)
		{
			dt=1;
		}
       
		for(int i=0;i<dollcount.length;i++)
		{		    
	          if(dollcount[i]!=0&&!isOntouch[i])
	          { 
	        	    MatrixState3D.pushMatrix();
	        	    if(i==4)
	  	  			{
	        	    	MatrixState3D.translate(ObjX+(i%3)*5.8f, ObjY-(i/3)*6.5f, 0);
	  	  			}else if(i==2)
	  	  			{
	  	  			 MatrixState3D.translate(ObjX+(i%3)*5.8f, 12, 0);
	  	  			}else
	  	  			{
	  	  			   MatrixState3D.translate(ObjX+(i%3)*5.8f, ObjY-(i/3)*7.8f, 0);
	  	  			}
	  	  			MatrixState3D.rotate(angle, 0, 1, 0);
	  	  			MatrixState3D.scale(objscale.get(i),objscale.get(i),objscale.get(i));
	  	  			dollobj.get(i).drawSelf(textureId.get(i));
	  	  			MatrixState3D.popMatrix();
	          }else if(!isOntouch[i])
	          {
	        	   MatrixState3D.pushMatrix();
	        	   if(i==4)
	  	  			{
	        	    	MatrixState3D.translate(ObjX+(i%3)*5.8f, ObjY-(i/3)*6.5f, 0);
	  	  			}else if(i==2)
	  	  			{
	  	  			 MatrixState3D.translate(ObjX+(i%3)*5.8f, 12, 0);
	  	  			}else
	  	  			{
	  	  			   MatrixState3D.translate(ObjX+(i%3)*5.8f, ObjY-(i/3)*7.8f, 0);
	  	  			}
	  	  			MatrixState3D.rotate(angle, 0, 1, 0);
	  	  		    MatrixState3D.scale(objscale.get(i),objscale.get(i),objscale.get(i));
	  	  			dollobj.get(i).drawSelf(0);
	  	  			MatrixState3D.popMatrix();
	          }
	     }
		
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
		backgroundlist.get(0).drawSelf();	
		backgroundlist.get(13).drawSelf();	
		backgroundlist.get(14).drawSelf();
		drawline.drawSelf();
		drawcount();
		
		GLES30.glEnable(GLES30.GL_DEPTH_TEST); 
		drawdollObjAndCount();
		GLES30.glDisable(GLES30.GL_DEPTH_TEST); 	
		if(islock)
		{
		 MatrixState2D.pushMatrix();
		 MatrixState2D.rotate(-20, 0,0, 1);
		 backgroundlist.get(15).drawSelf();
		 MatrixState2D.popMatrix();
		}
		GLES30.glEnable(GLES30.GL_DEPTH_TEST); 
		 if(isSale)
	     {
	    	 saleview.drawView(); 
	     }
	}


}
