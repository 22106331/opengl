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

public class Doll extends BNAbstractDoll {
	
		DiscreteDynamicsWorld dynamicsWorld;
//		public LoadedObjectVertexNormalTexture lovo;
		int texId;
		
		CollisionShape body;
		BoxShape foot;
		BoxShape nose;
		CollisionShape[]  pigbody=new CollisionShape[5];
		CollisionShape  pigshpae;
		
		Vector3f position;
		
		
		//���ǽ�����λ�û�������һ����������
		MySurfaceView mv;
		public Doll(int texId, DiscreteDynamicsWorld dynamicsWorld,
					LoadedObjectVertexNormalTexture lovo, Vector3f position, int bianhao)
		{
			this.dynamicsWorld=dynamicsWorld;
			this.lovo=lovo;
			this.texId=texId;
			this.position=position;
			this.bianhao=bianhao;
			initRigidBodys();
		}
		public void initRigidBodys()
		{
			//����������ֵ�Ĵ���ӵĺ�����                            ��     ��      �� ��ߵĳ�
			body=new BoxShape(new Vector3f(bodyc,bodyg,bodyk)); 
//			body=new CapsuleShape(bodyr,bodyc*2-bodyr);
			foot=new BoxShape(new Vector3f(footc,footg,footk));
			nose=new BoxShape(new Vector3f(nosec,noseg,nosek));
			pigbody[0]=body;
			pigbody[1]=nose;
			pigbody[2]=foot;
			pigbody[3]=new BoxShape(new Vector3f(bodyadd1x,bodyadd1y,bodyadd1z));
			pigbody[4]=new BoxShape(new Vector3f(bodyadd2x,bodyadd2y,bodyadd2z));
			pigshpae=addChild(pigbody);
			
			RigidBodydoll=RigidBodyHelper.addRigidBody(1,pigshpae,position.x,position.y,position.z,dynamicsWorld,false);
		}
		//���ҵ���װ
	  	public CompoundShape addChild(CollisionShape[] shape)//��װ������Ľ���
	  	{
	  		CompoundShape comShape=new CompoundShape(); //���������״
	  		
	  		Transform localTransform = new Transform();//�����任����
	  		
	  		localTransform.setIdentity();//��ʼ���任
	  		localTransform.origin.set(new Vector3f(0,bodyg+footg*2,0));//���ñ任�����
//	  		localTransform.basis.rotZ((float)Math.toRadians(90));
	  		comShape.addChildShape(localTransform, shape[0]);//�������״----����
	  		
	  		localTransform.setIdentity();//��ʼ���任
	  		localTransform.origin.set(new Vector3f(0,bodyg+footg*2,bodyk));//���ñ任�����
	  		comShape.addChildShape(localTransform, shape[3]);//�������״----����
	  		
	  		localTransform.setIdentity();//��ʼ���任
	  		localTransform.origin.set(new Vector3f(0,bodyg+footg*2,-bodyk));//���ñ任�����
	  		comShape.addChildShape(localTransform, shape[3]);//�������״----����
	  		
	  		localTransform.setIdentity();//��ʼ���任
	  		localTransform.origin.set(new Vector3f(0,bodyg*2+footg*2,0));//���ñ任�����
	  		comShape.addChildShape(localTransform, shape[4]);//�������״----����
	  		
	  		localTransform.setIdentity();//��ʼ���任
	  		localTransform.origin.set(new Vector3f(bodyc+nosec,bodyg+footg*2,0));//���ñ任�����
	  		comShape.addChildShape(localTransform, shape[1]);//�������״----����
	  		
	  		//==============foot================================
	  		localTransform.setIdentity();//��ʼ���任1
	  		localTransform.origin.set(new Vector3f(bodyc-footc*2,footg,bodyk-footk*2));//���ñ任�����
	  		comShape.addChildShape(localTransform, shape[2]);//�������״----����
	  		
	  		
	  		
	  		localTransform.setIdentity();//��ʼ���任2
	  		localTransform.origin.set(new Vector3f(bodyc-footc*2,footg,-bodyk+footk*2));//���ñ任�����
	  		comShape.addChildShape(localTransform, shape[2]);//�������״----����
	  		
	  		
	  		
	  		localTransform.setIdentity();//��ʼ���任3
	  		localTransform.origin.set(new Vector3f(-bodyc+footc*2,footg,-bodyk+footk*2));//���ñ任�����
	  		comShape.addChildShape(localTransform, shape[2]);//�������״----����
	  		
	  		
	  		
	  		localTransform.setIdentity();//��ʼ���任4
	  		localTransform.origin.set(new Vector3f(-bodyc+footc*2,footg,bodyk-footk*2));//���ñ任�����
	  		comShape.addChildShape(localTransform, shape[2]);//�������״----����
//	  		
	  		
	  		
	  		return comShape;
	  	}
		public void drawSelf()
		{
			
		    	MatrixState3D.pushMatrix();
				Transform trans=RigidBodydoll.getMotionState().getWorldTransform(new Transform());//��ȡ�������ı任��Ϣ����
				trans.origin.y=trans.origin.y-speed;
				MatrixState3D.translate(trans.origin.x,trans.origin.y, trans.origin.z);//������λ�任
				trans.getOpenGLMatrix(MatrixState3D.getMMatrix());
				
				MatrixState3D.pushMatrix();
				MatrixState3D.translate(0.1f,0,0);
				MatrixState3D.scale(pigbl, pigbl, pigbl);
				lovo.drawSelf(texId);
				MatrixState3D.popMatrix();
				MatrixState3D.popMatrix();
			
		}
}
