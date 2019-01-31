package com.opengl.catcher.addRigidBody;

import javax.vecmath.Vector3f;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.CompoundShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.linearmath.Transform;
import com.opengl.catcher.MatrixState.MatrixState3D;
import com.opengl.catcher.object.BNAbstractDoll;
import com.opengl.catcher.object.LoadedObjectVertexNormalTexture;
import com.opengl.catcher.util.RigidBodyHelper;

import static com.opengl.catcher.constant.SourceConstant.*;

public class Robot  extends BNAbstractDoll {
	int texId;
	DiscreteDynamicsWorld dynamicsWorld;
//	LoadedObjectVertexNormalTexture lovo;
	Vector3f position;
	
	CollisionShape[] robotshape=new CollisionShape[6];
	
	CollisionShape Robotz;
	public Robot(int texId, DiscreteDynamicsWorld dynamicsWorld,
				 LoadedObjectVertexNormalTexture lovo, Vector3f position, int bianhao){
		this.texId=texId;
		this.dynamicsWorld=dynamicsWorld;
		this.lovo=lovo;
		this.position=position;
		this.bianhao=bianhao;
		initRigidBodys();
	}
	public void initRigidBodys()
	{
		robotshape[0]=new BoxShape(new Vector3f(robotfootx,robotfooty,robotfootz));//���ǻ����˵ĽŵĽ���
		robotshape[1]=new BoxShape(new Vector3f(robottuix,robottuiy,robottuiz));//���ǻ����˵��ȵĽ���
		robotshape[2]=new BoxShape(new Vector3f(robotbodyx,robotbodyy,robotbodyz));//���ǻ����˵�����Ľ���
		robotshape[3]=new BoxShape(new Vector3f(robottopx,robottopy,robottopz));//���ǻ����˵��Դ��Ľ���
		robotshape[4]=new BoxShape(new Vector3f(robothand1x,robothand1y,robothand1z));//���ǻ����˵Ĵ��ֱ�1�Ľ���
		robotshape[5]=new BoxShape(new Vector3f(robothand2x,robothand2y,robothand2y));//���ǻ����˵�С�ֱ۵Ľ���
		Robotz=addChild(robotshape);//��װ���������˵���Ͻ���
		
		RigidBodydoll=RigidBodyHelper.addRigidBody(1,Robotz,position.x,position.y,position.z,dynamicsWorld,false);
	
	}
	 //���ҵ���װ
  	public CompoundShape addChild(CollisionShape[] shape)//��װ������Ľ���
  	{
  		CompoundShape comShape=new CompoundShape(); //���������״
  		Transform localTransform = new Transform();//�����任����
  		//���
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(robotfootx+robotfooty/4,-robotfooty-robottuiy*2-robotbodyy,0));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[0]);//�������״----����
  		//�ҽ�
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(-robotfootx-robotfooty/4,-robotfooty-robottuiy*2-robotbodyy,0));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[0]);//�������״----����
  		//����
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(robotfootx,-robottuiy-robotbodyy,0));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[1]);//�������״----����
  		//����
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(-robotfootx,-robottuiy-robotbodyy,0));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[1]);//�������״----����
  		//����
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(0,0,0));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[2]);//�������״----����
  		//�Դ�
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(0,robotbodyy+robottopy,0));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[3]);//�������״----����
  		//���ֱ�
  		//---���ֱ�
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(robotbodyx+robothand1x*2,0,0));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[4]);//�������״----����
  		//---С�ֱ�
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(robotbodyx+robothand1x*2,-robotbodyy+robothand2y,robothand1z+robothand2z));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[5]);//�������״----����
  		//���ֱ�
  		//---���ֱ�
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(-robotbodyx-robothand1x*2,0,0));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[4]);//�������״----����
  		//---С�ֱ�
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(-robotbodyx-robothand1x*2,-robotbodyy+robothand2y,robothand1z+robothand2z));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[5]);//�������״----����
  		
  		return comShape;
  	}
  	public void drawSelf()
	{
  		    MatrixState3D.pushMatrix();
			Transform trans=RigidBodydoll.getMotionState().getWorldTransform(new Transform());//��ȡ�������ı任��Ϣ����
			MatrixState3D.translate(trans.origin.x,trans.origin.y-speed, trans.origin.z);//������λ�任
			trans.getOpenGLMatrix(MatrixState3D.getMMatrix());
			
			MatrixState3D.pushMatrix();
			MatrixState3D.scale(robotbl,robotbl,robotbl);
			lovo.drawSelf(texId);
			MatrixState3D.popMatrix();

			MatrixState3D.popMatrix();
  		
	}
}
