package com.opengl.catcher.addRigidBody;

import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.CompoundShape;
import com.bulletphysics.collision.shapes.CylinderShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.linearmath.Transform;
import com.opengl.catcher.MatrixState.MatrixState3D;
import com.opengl.catcher.object.BNAbstractDoll;
import com.opengl.catcher.object.LoadedObjectVertexNormalTexture;
import com.opengl.catcher.util.RigidBodyHelper;

import static com.opengl.catcher.constant.SourceConstant.*;
public class Parrot extends BNAbstractDoll {
	CollisionShape[] ParrotShape=new CollisionShape[3];
	CollisionShape ParrotFH;
	int texId;
	DiscreteDynamicsWorld dynamicsWorld;
//	LoadedObjectVertexNormalTexture lovo;
	Vector3f position;

	public Parrot(int texId, DiscreteDynamicsWorld dynamicsWorld,
				  LoadedObjectVertexNormalTexture lovo, Vector3f position, int bianhao)
	{
		this.texId=texId;
		this.dynamicsWorld=dynamicsWorld;
		this.lovo=lovo;
		this.position=position;
		this.bianhao=bianhao;
		initRigidBodys();
	}
    public void initRigidBodys()
    {
    	//��һ����������������Բ������ĳ��̰뾶���ڶ���������Բ���߶�
    	ParrotShape[0]=new CylinderShape(new Vector3f(Parrotx,Parroty,Parrotz));
    	ParrotShape[1]=new BoxShape(new Vector3f(ParrotFootx,ParrotFooty,ParrotFootz));
    	ParrotFH=addChild(ParrotShape);//������ϳ���������
    	RigidBodydoll=RigidBodyHelper.addRigidBody(1,ParrotFH,position.x,position.y,position.z,dynamicsWorld,false);
    	
    }
  //���ҵ���װ
  	public CompoundShape addChild(CollisionShape[] shape)//��װ������Ľ���
  	{
  		CompoundShape comShape=new CompoundShape(); //���������״
  		Transform localTransform = new Transform();//�����任����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(0,Parroty,0));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[0]);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(Parrotx-ParrotFooty*2,ParrotFooty,Parrotz));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[1]);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(-Parrotx+ParrotFooty*2,ParrotFooty,Parrotz));//���ñ任�����
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
			MatrixState3D.scale(ParrotBL, ParrotBL, ParrotBL);
			lovo.drawSelf(texId);
			MatrixState3D.popMatrix();
			
			MatrixState3D.popMatrix();
  		
		
	}
}
