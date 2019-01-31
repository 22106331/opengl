package com.opengl.catcher.util;

import javax.vecmath.Vector3f;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
public class RigidBodyHelper {	
	public static RigidBody addRigidBody(float mass, CollisionShape shape,
			float cx,float cy,float cz,DiscreteDynamicsWorld dynamicsWorld,boolean noGravity) 
    {
		
		CollisionShape comShape=shape; //������ײ��״����
  		boolean isDynamic = (mass != 0f);//ȷ�������Ƿ���˶�
		Vector3f localInertia = new Vector3f(0f, 0f, 0f);//������Ź��Ե�����
		if (isDynamic) {
			comShape.calculateLocalInertia(mass, localInertia);//�������
		}
		
		Transform startTransform = new Transform();//��������ĳ�ʼ�任����
		startTransform.setIdentity();//��ʼ���任����
		startTransform.origin.set(new Vector3f(cx, cy, cz));//���ñ任�����
		DefaultMotionState myMotionState = new DefaultMotionState(startTransform);//����������˶�״̬����
		RigidBodyConstructionInfo rbInfo = new RigidBodyConstructionInfo(mass, myMotionState,
				comShape, localInertia);//��������������Ϣ����
		RigidBody body = new RigidBody(rbInfo);//�����������
		body.setRestitution(0.0f);//���÷���ϵ��
		body.setFriction(0.2f);//����Ħ��ϵ��
		body.forceActivationState(RigidBody.DISABLE_DEACTIVATION);//����һ��ʼ�ǷǼ���״̬
		dynamicsWorld.addRigidBody(body);//��������ӽ���������
		if(noGravity){
	 		body.setGravity(new Vector3f(0,0,0));//������Ҫ��������Ϊ0
	 	}
		return body;
    }

}
