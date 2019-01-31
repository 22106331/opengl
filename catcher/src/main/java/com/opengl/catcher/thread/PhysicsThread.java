package com.opengl.catcher.thread;

import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Vector3f;
import com.bulletphysics.linearmath.Transform;
import com.opengl.catcher.object.BNAbstractDoll;
import com.opengl.catcher.util.SliderHelper;
import com.opengl.catcher.view.CollectionView;
import com.opengl.catcher.view.GameView;

import static com.opengl.catcher.constant.SourceConstant.*;

public class PhysicsThread extends Thread
{
	GameView gv; //��Ϸ��������
	Vector3f origin;//�������滬���ؽ������ӵ�λ��
	public PhysicsThread(GameView gv)
	{
		this.gv=gv;
		
	}
	public void run() 
	{
		while(true)
		{            			
			try 
			{		
				origin=SliderHelper.cubeBody.getMotionState().getWorldTransform(new Transform()).origin;//��ȡ����λ��
				gv.dynamicsWorld.stepSimulation(TIME_STEP, MAX_SUB_STEPS);//��ʼģ��
    			delDoll();//����ɾ�����޸���ķ���
    			if((gv.keyState & 0x01) != 0){//�����ǰ��ť
    				if(origin.z<=14.7f)
					{
    					istop=false;
    					gv.claw.moveBy(new Vector3f(0,0,0.05f));//��Z���������ƶ�
    					if(origin.z<14.65f&&origin.z>14.6f)
    					{
    					 isbottom=true;//��ǰ��ť��ʧ��־Ϊtrue
    					}
					}	
    				
    			}else if((gv.keyState & 0x02) != 0){//������ť
    				
    				isbottom=false;
    				if(origin.z>=11.4f)
					{
    					gv.claw.moveBy(new Vector3f(0,0,-0.05f));//��Z�Ḻ�����ƶ�
    					if(origin.z>11.4&&origin.z<11.49)
    					{
    						istop=true;//���ť��ʧ��־Ϊtrue
    					}
					}			
    			}else if((gv.keyState & 0x04) != 0){//�������ť
    				isright=false;
    				if(origin.x>=-0.85f)
					{
    					gv.claw.moveBy(new Vector3f(-0.05f,0,0));//��X�Ḻ�����ƶ�
    					if(origin.x>-0.8f&&origin.x<-0.75f)
    					{
    						isleft=true;//����ť��ʧ��־Ϊtrue
    					}
					}
    			}else if((gv.keyState & 0x08) != 0){//������Ұ�ť
    				isleft=false;
    				if(origin.x<=1.2f)
					{
    					gv.claw.moveBy(new Vector3f(0.05f,0,0));//��X���������ƶ�
    					if(origin.x==1.15f)
    					{
    					 isright=true;//���Ұ�ť��ʧ��־Ϊtrue
    					}
					}
    			}
				Thread.sleep(20);	//��ǰ�߳�˯��20����
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			
		}
	}
	
	public void delDoll()
	{
		if(isupdate)//������ˢ�°�ť
		{
			for(int i=0;i<updatedoll.size();i++)
			{
				
				gv.dynamicsWorld.removeRigidBody(updatedoll.get(i).RigidBodydoll);//������������ɾ������
				
			}
			updatedoll.clear();	//������޸����б�
			isupdate=false;//ˢ�±�־λ��Ϊfalse
			gv.update();//����ˢ�����޷���
		}
		 List<BNAbstractDoll> removedoll=new ArrayList<BNAbstractDoll>();//���Ҫɾ�������޶���
		for(int i=0;i<updatedoll.size();i++)
		{
			Transform posi2=updatedoll.get(i).RigidBodydoll.getMotionState().getWorldTransform(new Transform());//��ȡ���޸���λ��
					
			if(posi2.origin.z>14.4f&&posi2.origin.x>0.5f&&posi2.origin.y<1.8f)
			{//�ж������Ƿ������ɺ���
				updatedoll.get(i).isInBox=true;
			
				int count=dollcount[updatedoll.get(i).bianhao]+1;
				dollcount[updatedoll.get(i).bianhao]=count;//��Ӧ����������1
				gv.dynamicsWorld.removeRigidBody(updatedoll.get(i).RigidBodydoll);//������������ɾ������
				removedoll.add(updatedoll.get(i));	
				getcount++;//��ȡ������������1
				CollectionView.CalculateAward();//���ü���ץ�����޽�������
				gv.isSuccess=true;//�Ƿ�ץȡ�ɹ���־λ��Ϊtrue
				gv.successId=updatedoll.get(i).bianhao;//��¼��ǰ���ޱ��
				break;
			}
			
		}
		for(int i=0;i<removedoll.size();i++)//ѭ��ɾ��ָ�����޶���
		{
			updatedoll.remove(removedoll.get(i));
		}
	}
	
	
		
}
