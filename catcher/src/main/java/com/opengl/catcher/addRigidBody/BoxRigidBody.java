package com.opengl.catcher.addRigidBody;

import javax.vecmath.Vector3f;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.CompoundShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.Transform;
import com.opengl.catcher.MatrixState.MatrixState3D;
import com.opengl.catcher.object.LoadedObjectVertexNormalTexture;
import com.opengl.catcher.util.RigidBodyHelper;

import static com.opengl.catcher.constant.SourceConstant.*;
public class BoxRigidBody {
	
	int texId;
	DiscreteDynamicsWorld dynamicsWorld;
	LoadedObjectVertexNormalTexture lovo;
	Vector3f position;
	CollisionShape[] Boxbian=new CollisionShape[2];
	CollisionShape Box;
	public RigidBody Boxrg;
	public BoxRigidBody(int texId, DiscreteDynamicsWorld dynamicsWorld,
						LoadedObjectVertexNormalTexture lovo, Vector3f position)
	{
		this.texId=texId;
		this.dynamicsWorld=dynamicsWorld;
		this.lovo=lovo;
		this.position=position;
		initRigidBody();
	}
	public void initRigidBody()
	{
		Boxbian[0]=new BoxShape(new Vector3f(boxbian1x,boxbian1y,boxbian1z));//����һ��x����Ľ���
		Boxbian[1]=new BoxShape(new Vector3f(boxbian2x,boxbian2y,boxbian2z));//����һ��y����Ľ���
		Box=addChild(Boxbian);
		
		Boxrg=RigidBodyHelper.addRigidBody(0,Box,position.x,position.y,position.z,dynamicsWorld,false);
	}
	//���ҵ���װ
  	public CompoundShape addChild(CollisionShape[] shape)//��װ������Ľ���
  	{
  		CompoundShape comShape=new CompoundShape(); //���������״
  		Transform localTransform = new Transform();//�����任����
  		
  		//��ǰ��ı�  
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(0,boxbian1y,boxbian1x));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[0]);//�������״----����
  		
  		//�����ı�  
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(0,boxbian1y,-boxbian1x));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[0]);//�������״----����
  		
  		
  		//������ı�  
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(-boxbian1x,boxbian2y,0));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[0]);//�������״----����
  		
  		//������ı�  
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(boxbian1x,boxbian2y,0));//���ñ任�����
  		comShape.addChildShape(localTransform, shape[0]);//�������״----����
  		
  		return comShape;
  	}
  	public void drawSelf()
  	{
  		MatrixState3D.pushMatrix();
		Transform trans=Boxrg.getMotionState().getWorldTransform(new Transform());//��ȡ�������ı任��Ϣ����
		MatrixState3D.translate(trans.origin.x,trans.origin.y, trans.origin.z);//������λ�任
		trans.getOpenGLMatrix(MatrixState3D.getMMatrix());
		
		MatrixState3D.pushMatrix();
		lovo.drawSelf(texId);
		MatrixState3D.popMatrix();
		MatrixState3D.popMatrix();
  	}
}
