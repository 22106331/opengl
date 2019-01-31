package com.opengl.catcher.addRigidBody;

import javax.vecmath.Vector3f;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.CompoundShape;
import com.bulletphysics.collision.shapes.CylinderShapeZ;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.linearmath.Transform;
import com.opengl.catcher.MatrixState.MatrixState3D;
import com.opengl.catcher.catcherFun.MySurfaceView;
import com.opengl.catcher.object.BNAbstractDoll;
import com.opengl.catcher.object.LoadedObjectVertexNormalTexture;
import com.opengl.catcher.util.RigidBodyHelper;

import static com.opengl.catcher.constant.SourceConstant.*;

public class Camera extends BNAbstractDoll {
	int texId;
	DiscreteDynamicsWorld dynamicsWorld;
	//LoadedObjectVertexNormalTexture lovo;
	Vector3f position;
	CollisionShape[] camerashape=new CollisionShape[5];
	//���Ǹ�����һ������λ������
	MySurfaceView mv;
	public Camera(int texId, DiscreteDynamicsWorld dynamicsWorld,
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
		//���������������Body��һ�������彺��
		camerashape[0]=new BoxShape(new Vector3f(Camerabodyx,Camerabodyy,Camerabodyz));
		//��������������Ͳ��һ��Բ������
		camerashape[1]=new CylinderShapeZ(new Vector3f(CameraR,CameraH,CameraR));
		//����������������һ�������彺��
		camerashape[2]=new BoxShape(new Vector3f(CameraTopx,CameraTopy,CameraTopz));
		
		camerashape[3]=addChild(camerashape);
		RigidBodydoll=RigidBodyHelper.addRigidBody(1,camerashape[3],position.x,position.y,position.z,dynamicsWorld,false);
		
		
	} 
	 //���ҵ���װ
  	public CompoundShape addChild(CollisionShape[] shape)//��װ������Ľ���
  	{
  		CompoundShape comShape=new CompoundShape(); //���������״
  		Transform localTransform = new Transform();//�����任����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(0,Camerabodyy,-Camerabodyz-0.08f));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[0]);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(-0.06f,CameraR,CameraH/2-0.03f));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[1]);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(-0.022f,Camerabodyy*2+CameraTopy,-Camerabodyz-0.08f));//���ñ任�����
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
			MatrixState3D.scale(Camerabl,Camerabl,Camerabl);
			lovo.drawSelf(texId);
			MatrixState3D.popMatrix();
			MatrixState3D.popMatrix();
	}

}
