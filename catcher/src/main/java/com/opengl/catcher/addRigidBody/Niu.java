package com.opengl.catcher.addRigidBody;

import javax.vecmath.Vector3f;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.CompoundShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.linearmath.Transform;
import com.opengl.catcher.MatrixState.MatrixState3D;
import com.opengl.catcher.catcherFun.MySurfaceView;
import com.opengl.catcher.object.BNAbstractDoll;
import com.opengl.catcher.object.LoadedObjectVertexNormalTexture;
import com.opengl.catcher.util.RigidBodyHelper;

import static com.opengl.catcher.constant.SourceConstant.*;

public class Niu extends BNAbstractDoll {
	int texId;
	DiscreteDynamicsWorld dynamicsWorld;
	Vector3f position;
//	LoadedObjectVertexNormalTexture lovo;
	CollisionShape[] niushape=new CollisionShape[5];
	CollisionShape niujn;

	
	
	//���ǽ�����λ�û�������һ����������
	MySurfaceView mv;
	public Niu(int texId,DiscreteDynamicsWorld dynamicsWorld,LoadedObjectVertexNormalTexture lovo,
			Vector3f  position,int bianhao)
	{
		this.texId=texId;
		this.lovo=lovo;            
		this.position=position;
		this.dynamicsWorld=dynamicsWorld;   
		this.bianhao=bianhao;
		initRigidBodys();
	}
	public void initRigidBodys()
	{
		niushape[0]=new BoxShape(new Vector3f(niubodyx,niubodyy,niubodyz));//����ţ������
		niushape[1]=new BoxShape(new Vector3f(niufootx,niufooty,niufootz));
		niushape[2]=new BoxShape(new Vector3f(niuadd1x,niuadd1y,niuadd1z));
		niushape[3]=new BoxShape(new Vector3f(niuadd2x,niuadd2y,niuadd2z));
		
		niushape[4]=new BoxShape(new Vector3f(niuadd3x,niuadd3y,niuadd3z));
		niujn=addChild(niushape);
		RigidBodydoll=RigidBodyHelper.addRigidBody(1,niujn,position.x,position.y,position.z,dynamicsWorld,false);
	}
	//���ҵ���װ
  	public CompoundShape addChild(CollisionShape[] shape)//��װ������Ľ���
  	{
  		CompoundShape comShape=new CompoundShape(); //���������״
  		Transform localTransform = new Transform();//�����任����
  		
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(0,niubodyy+niufooty*2,0));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[0]);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(0,niubodyy*2+niufooty*2,0));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[2]);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(0,niubodyy+niufooty*2,niubodyz));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[3]);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(0,niubodyy+niufooty*2,-niubodyz));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[3]);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(niubodyx,niuadd3y+niufooty*2,0));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[4]);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(niubodyx-niufootx*2,niufooty,niubodyz-niufootz));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[1]);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(niubodyx-niufootx*2,niufooty,-niubodyz+niufootz));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[1]);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(-niubodyx+niufootx*2,niufooty,-niubodyz+niufootz));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[1]);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(-niubodyx+niufootx*2,niufooty,niubodyz-niufootz));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[1]);//�������״----����
  		return comShape;
  	}
  	public void drawSelf()
  	{
  		
	  		MatrixState3D.pushMatrix();
			Transform trans=RigidBodydoll.getMotionState().getWorldTransform(new Transform());//��ȡ�������ı任��Ϣ����
			MatrixState3D.translate(trans.origin.x,trans.origin.y-speed, trans.origin.z);//������λ�任
			trans.getOpenGLMatrix(MatrixState3D.getMMatrix());
			
			MatrixState3D.pushMatrix();
			MatrixState3D.scale(niubz,niubz,niubz);
			lovo.drawSelf(texId);
			MatrixState3D.popMatrix();
			MatrixState3D.popMatrix();
  	
  	}
}
