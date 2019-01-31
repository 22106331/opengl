package com.opengl.catcher.addRigidBody;

import javax.vecmath.Vector3f;
import com.bulletphysics.collision.shapes.BoxShape;
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

public class Car extends BNAbstractDoll {
	int texId;
	DiscreteDynamicsWorld dynamicsWorld;
//	LoadedObjectVertexNormalTexture lovo;
	Vector3f position;
	CollisionShape[] Carshape=new CollisionShape[3];
	CollisionShape CarS;
	public Car(int texId, DiscreteDynamicsWorld dynamicsWorld,
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
		Carshape[0]=new BoxShape(new Vector3f(Carfootx,Carfooty,Carfootz));
		Carshape[1]=new BoxShape(new Vector3f(Carbuttomx,Carbuttomy,Carbuttomz));
		Carshape[2]=new SphereShape(CarR);
		
		CarS=addChild(Carshape);
		
		RigidBodydoll=RigidBodyHelper.addRigidBody(1,CarS,position.x,position.y,position.z,dynamicsWorld,false);
	}
	 //���ҵ���װ
  	public CompoundShape addChild(CollisionShape[] shape)//��װ������Ľ���
  	{
  		CompoundShape comShape=new CompoundShape(); //���������״
  		Transform localTransform = new Transform();//�����任����
  		//��ǰ��
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(Carbuttomx-Carfootx*2,Carfooty,Carbuttomz-Carfootz*3));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[0]);//�������״----����
  		//�����
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(Carbuttomx-Carfootx*2,Carfooty,-Carbuttomz+Carfootz*3));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[0]);//�������״----����
  		//��ǰ��
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(-Carbuttomx+Carfootx*2,Carfooty,Carbuttomz-Carfootz*3));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[0]);//�������״----����
  		//�Һ���
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(-Carbuttomx+Carfootx*2,Carfooty,-Carbuttomz+Carfootz*3));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[0]);//�������״----����
  		//������
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(0,Carfooty+Carbuttomy,0));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[1]);//�������״----����
  		//������
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(0,Carfooty+Carbuttomy*2,0));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[2]);//�������״----����
  		return comShape;
  	}
  	public void drawSelf()
	{

  	
	  		MatrixState3D.pushMatrix();
			Transform trans=RigidBodydoll.getMotionState().getWorldTransform(new Transform());//��ȡ�������ı任��Ϣ����
			MatrixState3D.translate(trans.origin.x,trans.origin.y-speed, trans.origin.z);//������λ�任
			trans.getOpenGLMatrix(MatrixState3D.getMMatrix());
			
			MatrixState3D.pushMatrix();
			MatrixState3D.scale(Carbl,Carbl,Carbl);
			lovo.drawSelf(texId);
			MatrixState3D.popMatrix();
			
			MatrixState3D.popMatrix();
  	
	}
	
}
