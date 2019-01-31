package com.opengl.catcher.addRigidBody;

import javax.vecmath.Vector3f;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CapsuleShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.CompoundShape;
import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.linearmath.Transform;
import com.opengl.catcher.MatrixState.MatrixState3D;
import com.opengl.catcher.object.BNAbstractDoll;
import com.opengl.catcher.object.LoadedObjectVertexNormalTexture;
import com.opengl.catcher.util.RigidBodyHelper;

import static com.opengl.catcher.constant.SourceConstant.*;

public class Tv extends BNAbstractDoll {
	int texId;
	DiscreteDynamicsWorld dynamicsWorld;
	Vector3f position;
//	LoadedObjectVertexNormalTexture lovo;
	
	CollisionShape[] tvShape=new CollisionShape[5];
	CollisionShape tv;

	
	public Tv(int texId, DiscreteDynamicsWorld dynamicsWorld,
			  LoadedObjectVertexNormalTexture lovo, Vector3f position, int bianhao)
	{
		this.texId=texId;
		this.dynamicsWorld=dynamicsWorld;
		this.position=position;
		this.lovo=lovo;
		this.bianhao=bianhao;
		initRigidBodys();
	}
	public void initRigidBodys()
	{
		tvShape[0]=new BoxShape(new Vector3f(tvfootx,tvfooty,tvfootz));
		tvShape[1]=new BoxShape(new Vector3f(tvbodyx,tvbodyy,tvbodyz));
		tvShape[2]=new SphereShape(tvtopr);
		tvShape[3]=new CapsuleShape(tvyzr,tvyzh-tvyzr*2);
		tvShape[4]=new SphereShape(tvtopmr);
		tv=addChild(tvShape);
		RigidBodydoll=RigidBodyHelper.addRigidBody(1,tv,position.x,position.y,position.z,dynamicsWorld,false);
	}
	//���ҵ���װ
  	public CompoundShape addChild(CollisionShape[] shape)//��װ������Ľ���
  	{
  		CompoundShape comShape=new CompoundShape(); //���������״
  		Transform localTransform = new Transform();//�����任����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(tvbodyx-tvfootx*2,tvfooty,tvbodyz-2*tvfootz));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[0]);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(tvbodyx-tvfootx*2,tvfooty,-tvbodyz+2*tvfootz));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[0]);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(-tvbodyx+tvfootx*2,tvfooty,-tvbodyz+2*tvfootz));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[0]);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(-tvbodyx+tvfootx*2,tvfooty,tvbodyz-2*tvfootz));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[0]);//�������״----����
  		
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(0,tvfooty*2+tvbodyy,0));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[1]);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(0,tvfooty*2+tvbodyy*2,0));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[2]);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(-tvtopr-tvyzh*(float)Math.cos((float)Math.toRadians(tvangle)),
  				tvfooty*2+tvbodyy*2
  				+tvyzh*(float)Math.cos((float)Math.toRadians(tvangle)),0));//���ñ任�����
  		localTransform.basis.rotY((float)Math.toRadians(tvangle));
  		comShape.addChildShape(localTransform, shape[3]);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(tvtopr+tvyzh*(float)Math.cos((float)Math.toRadians(tvangle)),
  				tvfooty*2+tvbodyy*2+tvyzh*(float)Math.cos((float)Math.toRadians(tvangle)),0));//���ñ任�����
  		localTransform.basis.rotY((float)Math.toRadians(-tvangle));
  		comShape.addChildShape(localTransform, shape[3]);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(tvtopr+tvyzh*(float)Math.cos((float)Math.toRadians(tvangle))
  				+tvtopmr,
  				tvfooty*2+tvbodyy*2+tvyzh*(float)Math.cos((float)Math.toRadians(tvangle))+tvtopmr,0));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[4]);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(-tvtopr+tvyzh*(float)Math.cos((float)Math.toRadians(tvangle))-
  				tvtopmr,
  				tvfooty*2+tvbodyy*2+tvyzh*(float)Math.cos((float)Math.toRadians(tvangle))+tvtopmr,0));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[4]);//�������״----����
  		
  		return comShape;
  	}
  	public void drawSelf()
  	{
  		
	  		MatrixState3D.pushMatrix();
			Transform trans=RigidBodydoll.getMotionState().getWorldTransform(new Transform());//��ȡ�������ı任��Ϣ����
			MatrixState3D.translate(trans.origin.x,trans.origin.y-speed, trans.origin.z);//������λ�任
			trans.getOpenGLMatrix(MatrixState3D.getMMatrix());
			
			MatrixState3D.pushMatrix();
			MatrixState3D.scale(tvbz, tvbz, tvbz);
			lovo.drawSelf(texId);
			MatrixState3D.popMatrix();			
			MatrixState3D.popMatrix();
  	
  	}
}
