package com.opengl.catcher.util;

import javax.vecmath.Vector3f;
import com.bulletphysics.BulletGlobals;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.constraintsolver.SliderConstraint;
import com.bulletphysics.linearmath.MatrixUtil;
import com.bulletphysics.linearmath.Transform;
import com.opengl.catcher.MatrixState.MatrixState3D;
import com.opengl.catcher.addRigidBody.Claw;
import com.opengl.catcher.catcherFun.MySurfaceView;

import android.annotation.SuppressLint;

import static com.opengl.catcher.constant.SourceConstant.*;

@SuppressLint("UseSparseArrays")
public class SliderHelper 
{	
	CollisionShape boxShape;//��ײ��״����
	public static RigidBody cubeBody;//��������
	SliderConstraint sliderUD;//�����ؽ�����
	int TextureId;//��������
    MySurfaceView viewManager;//��������������
    DiscreteDynamicsWorld dynamicsWorld;//�������
	public SliderHelper(int TextureId, MySurfaceView viewManager, DiscreteDynamicsWorld dynamicsWorld)
	{
		this.TextureId=TextureId;
		this.viewManager=viewManager;
		this.dynamicsWorld=dynamicsWorld;
		initWorld();
	}
	public void initWorld()
	{
		boxShape=new BoxShape(new Vector3f(cubeSize,cubeSize/2,cubeSize));//�������������ײ��״
		cubeBody = RigidBodyHelper.addRigidBody(0f,boxShape,0,8,12,dynamicsWorld,true);	//��Ӹ���
		//��ӳ�������צ��֮���Լ��
        Vector3f originA = new Vector3f(0, 0, 0);
 		Vector3f originB = new Vector3f(0, 0, 0);
		
		originA.set(0, 0, 0);//���ô�Լ�������������ĵ�ƽ�Ʊ任��Ϣ
	    originB.set(0,ganULength/2, 0);//���ô�Լ����צ�Ӹ����ĵ�ƽ�Ʊ任��Ϣ
	 	addSliderConstraint(0,cubeBody,Claw.bodyg[0],BulletGlobals.SIMD_PI/2,originA,originB,true);//��ӳ�������צ��֮���Լ��
	}
	public void addSliderConstraint(int index,RigidBody ra,RigidBody rb,float angle,Vector3f originA,Vector3f originB,boolean force){
		Transform localA = new Transform();//�����任����A
		Transform localB = new Transform();//�����任����B
		localA.setIdentity();//��ʼ���任����A
		localB.setIdentity();//��ʼ���任����B
		MatrixUtil.setEulerZYX(localA.basis, 0, 0, angle);//���ñ任����A����ת����
		MatrixUtil.setEulerZYX(localB.basis, 0, 0, angle);//���ñ任�������ת����
		localA.origin.set(originA);//���ñ任����A��ƽ�Ʋ���
		localB.origin.set(originB);	//���ñ任����B��ƽ�Ʋ���
		sliderUD = new SliderConstraint(ra, rb, localA, localB, force);//���������ؽ�Լ������
		sliderUD.setLowerLinLimit(-2.8f);//���ƻ�������С����
		sliderUD.setUpperLinLimit(0f);//���ƻ�����������
		sliderUD.setLowerAngLimit(0);//����ת��������
		sliderUD.setUpperAngLimit(0);//����ת��������
		sliderUD.setDampingDirAng(1.0f);//����ת������
		sliderUD.setDampingDirLin(1f); //������������
		dynamicsWorld.addConstraint(sliderUD,true);//��Լ����ӽ���������
		
	}
	public void slideUD(float mulFactor){
		sliderUD.getRigidBodyB().activate();//�������Ӹ���
		sliderUD.setPoweredLinMotor(true);//�����ؽڶ�Ӧ�����
		sliderUD.setMaxLinMotorForce(200.0f);//�������Ļ���������
		sliderUD.setTargetLinMotorVelocity(20.0f*mulFactor);//�������Ļ��������ٶ�
	}
	public void drawSelf() {
		
         	MatrixState3D.pushMatrix();
         	Transform trans = cubeBody.getMotionState().getWorldTransform(new Transform());
			MatrixState3D.translate(trans.origin.x,trans.origin.y, trans.origin.z);
			trans.getOpenGLMatrix(MatrixState3D.getMMatrix());
			MatrixState3D.translate(0, -0.6f, 0);
         	ganbox.drawSelf(TextureId);
			MatrixState3D.popMatrix();
         
	}
}
