package com.opengl.catcher.addRigidBody;

import javax.vecmath.Vector3f;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CapsuleShape;
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

public class Phone extends BNAbstractDoll {
	int texId;
	DiscreteDynamicsWorld dynamicsWorld;
//	LoadedObjectVertexNormalTexture lovo;
	Vector3f position;
	CollisionShape[]  phoneshape=new CollisionShape[4];
	CollisionShape phone;

	
	//���ǽ�����λ�û�������һ����������
	MySurfaceView mv;
	public Phone(int texId, DiscreteDynamicsWorld dynamicsWorld,
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
		phoneshape[0]=new BoxShape(new Vector3f(phone3c,phone3g,phone3k));
		phoneshape[1]=new BoxShape(new Vector3f(phone2c,phone2g,phone2k));
		phoneshape[2]=new BoxShape(new Vector3f(phone1c,phone1g,phone1k));
		phoneshape[3]=new CapsuleShape(phoneyr,phoneyh-phoneyr*2);
		phone=addChild(phoneshape);
		RigidBodydoll=RigidBodyHelper.addRigidBody(1,phone,position.x,position.y,position.z,dynamicsWorld,false);
	
	}
	//���ҵ���װ
  	public CompoundShape addChild(CollisionShape[] shape)//��װ������Ľ���
  	{
  		CompoundShape comShape=new CompoundShape(); //���������״
  		Transform localTransform = new Transform();//�����任����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(0,-phone3g-phone2g*2,phone3g/2));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[0]);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(0,-phone2g,phone3g));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[1]);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(0,phone1g,0));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[2]);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(0,phone1g*2+phoneyh,
  				phone1k-phoneyr*2));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[3]);//�������״----����
  		
  		return comShape;
  	}
  	public void drawSelf()
  	{
  		
	  		MatrixState3D.pushMatrix();
			Transform trans=RigidBodydoll.getMotionState().getWorldTransform(new Transform());//��ȡ�������ı任��Ϣ����
			MatrixState3D.translate(trans.origin.x,trans.origin.y-speed, trans.origin.z);//������λ�任
			trans.getOpenGLMatrix(MatrixState3D.getMMatrix());
			
			MatrixState3D.pushMatrix();
			MatrixState3D.scale(phonebz, phonebz, phonebz);
			lovo.drawSelf(texId);
			MatrixState3D.popMatrix();
			MatrixState3D.popMatrix();
  	
  	}
}
