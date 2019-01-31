package com.opengl.catcher.view;

import java.util.ArrayList;
import java.util.List;
import android.view.MotionEvent;

import com.opengl.catcher.MatrixState.MatrixState2D;
import com.opengl.catcher.MatrixState.MatrixState3D;
import com.opengl.catcher.catcherFun.MySurfaceView;
import com.opengl.catcher.constant.Constant;
import com.opengl.catcher.object.BN2DObject;
import com.opengl.catcher.util.manager.ShaderManager;
import com.opengl.catcher.util.manager.TextureManager;

public class CatchSucceedView{
	MySurfaceView mv;
    public List<BN2DObject> background=new ArrayList<BN2DObject>();//���BNObject����
	float PreviousX;
	float PreviousY;
	float angle=0;
	public CatchSucceedView(MySurfaceView mv)
	{
		this.mv=mv;
		initView();
		
	}
	public void initView() 
	{	
		
		background.add(0,new BN2DObject(540, 960, 1080, 
				1920, TextureManager.getTextures("salebackground.png"),
				ShaderManager.getShader(5)));
		background.add(0,new BN2DObject(580, 660, 350, 
				200, TextureManager.getTextures("catchbackground.png"), 
				ShaderManager.getShader(2)));
		background.add(0,new BN2DObject(540, 1060, 1000, 
				1000, TextureManager.getTextures("showscore.png"), 
				ShaderManager.getShader(5),3));
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
	    		mv.gameView.isSuccess=false;
	    		angle=0;
	    		break;
    	}
		PreviousX=x;
		PreviousY=y;
		return true;
	}
	public void drawxing(int biaohao)
	{
		for(int i=0;i<4;i++)
		{
			SellView.xinglist.get(i).drawSelf(320+i*150,1500);
				
		}
		for(int j=0;j<SellView.jiazhilist.get(biaohao);j++)
		 {
			SellView.xinglist.get(j+4).drawSelf(320+j*150,1500);
		 }
	}
	public  void drawView(int biaohao) {
		//GLES20.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);   
		
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
		angle=angle+5;		
		MatrixState2D.pushMatrix();//�����ֳ�  
		for(int j=0;j<background.size();j++){
			background.get(j).drawSelf();
		}
	    drawxing( biaohao);
		
	    MatrixState2D.popMatrix();//�ָ��ֳ�
	
	    MatrixState3D.pushMatrix();
	   
	    MatrixState3D.translate(0,2.0f,5);
	    MatrixState3D.rotate(angle, 0, 1, 0);
	    //MatrixState3D.scale(2, 2, 2);
	    MatrixState3D.scale(CollectionView.objscale.get(biaohao),CollectionView.objscale.get(biaohao),CollectionView.objscale.get(biaohao));
	    CollectionView.dollobj.get(biaohao).drawSelf(CollectionView.textureId.get(biaohao));
	   // niu.drawSelf(niuId);
	    MatrixState3D.popMatrix();
	}


}
