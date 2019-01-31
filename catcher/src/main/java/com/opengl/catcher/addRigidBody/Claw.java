package com.opengl.catcher.addRigidBody;

import javax.vecmath.Vector3f;

import com.bulletphysics.BulletGlobals;
import com.bulletphysics.collision.shapes.CapsuleShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.CompoundShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.Generic6DofConstraint;
import com.bulletphysics.dynamics.constraintsolver.HingeConstraint;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import com.opengl.catcher.MatrixState.MatrixState3D;
import com.opengl.catcher.catcherFun.MySurfaceView;
import com.opengl.catcher.object.LoadedObjectVertexNormalTexture;
import com.opengl.catcher.util.SliderHelper;

import static com.opengl.catcher.constant.SourceConstant.*;

public class Claw {
	MySurfaceView mv;
	DiscreteDynamicsWorld dynamicsWorld;
	CollisionShape handSZ;//һ����е�ֱ��ϵ�һ����е��ָͷ
	CollisionShape[] csa=new CollisionShape[3];//һ����е�ֱ��ϵĵ���������
	CollisionShape[] csc=new CollisionShape[3];//һ����е�ֱ��ϵĵ���������
	CollisionShape[] csag=new CollisionShape[2];//һ����е�ֱ��ϵĵ���������
	CollisionShape collisionShape1;
	public static HingeConstraint[] hingeConstraint = new HingeConstraint[4];
	LoadedObjectVertexNormalTexture lovo;
	LoadedObjectVertexNormalTexture[] bodyForDraws;
	public boolean motorFlag ;
	
	int mProgram;
	int clawId;
	int ganId;
	int dunId;
	int ganY=6;
	int ganZ=12; 
	public static RigidBody[] body=new RigidBody[4];
	public static RigidBody bodyg[]=new RigidBody[2];
	public static RigidBody bodyhuagan[]=new RigidBody[1];

	public Claw(int clawId,int ganId,int dunId,MySurfaceView mv,DiscreteDynamicsWorld dynamicsWorld,
			LoadedObjectVertexNormalTexture[] bodyForDraws,int mProgram)
	{
		this.dunId=dunId;
		this.clawId=clawId;
		this.ganId=ganId;
		this.bodyForDraws=bodyForDraws;
		this.mv=mv;
		this.mProgram=mProgram;
		this.dynamicsWorld=dynamicsWorld;
		motorFlag=false;
		
		initRigidBodys();
		
	}
    private void initRigidBodys()
    {
    	csa[0]=new CapsuleShape(clawRadius,claw1th/2-2*clawRadius);
    	csa[1]=new CapsuleShape(clawRadius,claw2th/2-2*clawRadius);
    	csa[2]=new CapsuleShape(clawRadius,claw3th/2-2*clawRadius);
    	
        csc[0]=addChild(csa[0],claw1th);
        csc[1]=addChild(csa[1],claw2th);
        csc[2]=addChild(csa[2],claw3th);
        
    	body[0]=addRigidBody(1,csc,clawRadius,ganY,ganZ,false,0,0);
    	body[1]=addRigidBody(1,csc,0,ganY,ganZ-clawRadius,false,90,0);
    	body[2]=addRigidBody(1,csc,-clawRadius,ganY,ganZ,false,180,0);
    	body[3]=addRigidBody(1,csc,0,ganY,ganZ+clawRadius,false,270,0);
    	
		//��е�ֵ��ֱ۵Ľ��ҵĴ���
    	csag[0]=new CapsuleShape(ganURadius,ganULength-2*ganURadius);
    	csag[1]=new CapsuleShape(ganLRadius,ganLLength-2*ganLRadius);
    	bodyg[0]=addRigidBody(1,csag,0,ganY,ganZ,true,0,1);//��
    	bodyg[1]=addRigidBody(1,csag,0,ganY,ganZ,true,0,2);//��
    	addjoint6DOF(bodyg[0],bodyg[1]);
//    	 Transform transform = new Transform();//�����任����
//		 transform.setIdentity();//��ʼ���任
//		 transform.origin.set(new Vector3f(0,0,ganZ));//���ñ任�����
//		bodyg[0].setCenterOfMassTransform(transform);
    	addHingeConstraint(body[0],bodyg[1],-clawRadius,0,0,0);
    	addHingeConstraint(body[1],bodyg[1],-clawRadius,0,0,1);
    	addHingeConstraint(body[2],bodyg[1],-clawRadius,0,0,2);
    	addHingeConstraint(body[3],bodyg[1],-clawRadius,0,0,3);
    	
    }
    //���������ɶȹؽ�
    public void addjoint6DOF(RigidBody rbA,RigidBody rbB)
    {
    	Generic6DofConstraint joint6DOF;
		Transform localA = new Transform(); 
		Transform localB = new Transform();
		localA.setIdentity();
		localB.setIdentity();
		localA.origin.set(0,ganLLength,0);
		localB.origin.set(0,ganLLength,0);
		joint6DOF=new Generic6DofConstraint(rbA,rbB,localA,localB,true);
		Vector3f limitTrans=new Vector3f();
		limitTrans.set(-0.1f, -BulletGlobals.FLT_EPSILON, -0.1f);
		joint6DOF.setAngularLowerLimit(limitTrans);
		limitTrans.set(0.1f, BulletGlobals.FLT_EPSILON,0.1f);
		joint6DOF.setAngularUpperLimit(limitTrans);
		dynamicsWorld.addConstraint(joint6DOF, true);
    }
  //������װ��е��ץ����ָͷ�����ڹؽڵ�ÿ�ڹؽڵ��������ҵ���װ
  	public CompoundShape addChild(CollisionShape shape,float height)//��װ������Ľ���
  	{
  		CompoundShape comShape=new CompoundShape(); //���������״
  		
  		Transform localTransform = new Transform();//�����任����
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(0,-height/4,0));//���ñ任�����
  		comShape.addChildShape(localTransform, shape);//�������״----����
  		
  		localTransform.setIdentity();//��ʼ���任
  		localTransform.origin.set(new Vector3f(0,-3*height/4,0));//���ñ任�����
  		comShape.addChildShape(localTransform, shape);//�������״----����
  		return comShape;
  	}
    private RigidBody addRigidBody(float mass, CollisionShape[] shape,
    		float cx,float cy,float cz,boolean isgan,float angle,int gbz) 
    {
		boolean isDynamic = (mass != 0f);
		CompoundShape comShape=new CompoundShape(); //���������״
		if(isgan){
			//�˵����������ڸ˵��е�������1��λ�á�
			Transform localTransform = new Transform();//�����任����
			if(gbz==1){
				localTransform.setIdentity();//��ʼ���任
				localTransform.origin.set(new Vector3f(0,ganTLength/2+ganLLength/2,0));//���ñ任�����
				comShape.addChildShape(localTransform, shape[0]);//�������״----����
			}
			if(gbz==2){
				localTransform.setIdentity();//��ʼ���任-ganULength/2-ganLLength/2+1
				localTransform.origin.set(new Vector3f(0,ganLLength/2,0));//���ñ任�����1.403
				comShape.addChildShape(localTransform, shape[1]);//�������״---����
			}
		}else{
			//����һ����е��ץ����ָͷ
			Transform localTransform = new Transform();//�����任����
			localTransform.setIdentity();//��ʼ���任
			localTransform.origin.set(new Vector3f(0,0,0));//���ñ任�����
			localTransform.basis.rotZ((float)Math.toRadians(90+clawAngle1));//90+
			comShape.addChildShape(localTransform, shape[0]);//�������״----����
			
			localTransform.setIdentity();//��ʼ���任
			localTransform.origin.set(new Vector3f(claw1th*(float)Math.cos(Math.toRadians(clawAngle1)),
					claw1th*(float)Math.sin(Math.toRadians(clawAngle1)),0));//���ñ任�����
			localTransform.basis.rotZ((float)Math.toRadians(90-clawAngle2));//
			comShape.addChildShape(localTransform, shape[1]);//�������״---����
		
			localTransform.setIdentity();//��ʼ���任
			localTransform.origin.set(new Vector3f(clawtzx,
					claw1th*(float)Math.sin(Math.toRadians(clawAngle1))-
					claw2th*(float)Math.cos(Math.toRadians(90-clawAngle2)),0));//���ñ任�����1.403
			localTransform.basis.rotZ((float)Math.toRadians(360-clawAngle3));//
			comShape.addChildShape(localTransform, shape[2]);//�������״---����1
		}
		Vector3f localInertia = new Vector3f();
		localInertia.set(0f, 0f, 0f);
		if (isDynamic) {
			comShape.calculateLocalInertia(mass, localInertia);
		}
		
		Transform startTransform = new Transform();//��������ĳ�ʼ�任����
		startTransform.setIdentity();//��ʼ���任����
		startTransform.basis.rotY((float)Math.toRadians(angle));
		startTransform.origin.set(new Vector3f(cx, cy, cz));//���ñ任�����
		DefaultMotionState myMotionState = new DefaultMotionState(startTransform);
		RigidBodyConstructionInfo rbInfo = new RigidBodyConstructionInfo(mass, myMotionState, comShape, localInertia);
		RigidBody body = new RigidBody(rbInfo);
		body.setRestitution(0.8f);
		body.setFriction(0.0f);
		body.setGravity(new Vector3f(0,0,0));
		body.setActivationState(RigidBody.DISABLE_DEACTIVATION);
		
		dynamicsWorld.addRigidBody(body);
		return body;
  }
    
    public void addHingeConstraint(RigidBody rbA,RigidBody rbB,float cx,float cy,float cz,int index){//��ӽ���Լ��
    	Transform transA = new Transform();//�����任
    	Transform transB = new Transform();//�����任	
    	if(index==0)
    	{
    		transA.setIdentity();//��ʼ���任     getCenterOfMassPosition(out);
    		transA.origin.set(cx,cy,cz);//Լ����λ��
    			
    		transB.setIdentity();//��ʼ���任	
    		transB.origin.set(new Vector3f(0,ganLLength/2,0));
    		
			hingeConstraint[index] = new HingeConstraint(rbA,rbB,transA,transB);//����Լ��
			hingeConstraint[0].setLimit(-0.7f, 0);
			dynamicsWorld.addConstraint(hingeConstraint[index], true);//��Լ����ӵ���������
			hingeConstraint[0].enableAngularMotor(true, motorFlag?-1.4f:1.4f, 500f);
    	}
    	if(index==1)
    	{
    		transA.setIdentity();//��ʼ���任
    		transA.origin.set(cx,cy,cz);//Լ����λ��
    		transB.setIdentity();//��ʼ���任	
    		transB.basis.rotY((float)Math.toRadians(90));
    		transB.origin.set(new Vector3f(0,ganLLength/2,0));
			hingeConstraint[1] = new HingeConstraint(rbA,rbB,transA,transB);//����Լ��
			hingeConstraint[1].setLimit(-0.7f, 0, 0.9f, 0.3f,1.0f);
			dynamicsWorld.addConstraint(hingeConstraint[1], true);//��Լ����ӵ���������
			hingeConstraint[1].enableAngularMotor(true, motorFlag?-1.4f:1.4f, 500f);
    	}
    	if(index==2)
    	{
    		transA.setIdentity();//��ʼ���任
    		transA.basis.rotY((float)Math.toRadians(180));
    		transA.origin.set(cx,cy,cz);//Լ����λ��	
    		transB.setIdentity();//��ʼ���任	
    		transB.origin.set(new Vector3f(0,ganLLength/2,0));
			hingeConstraint[index] = new HingeConstraint(rbA,rbB,transA,transB);//����Լ��
			hingeConstraint[index].setLimit(0.0f, 0.7f, 0.9f, 0.3f,1.0f);
			dynamicsWorld.addConstraint(hingeConstraint[index], true);//��Լ����ӵ���������
			hingeConstraint[index].enableAngularMotor(true, motorFlag?1.4f:-1.4f, 500f);
    	}
    	if(index==3)
    	{
    		transA.setIdentity();//��ʼ���任 		
    		transB.setIdentity();//��ʼ���任	
    		transB.basis.rotY((float)Math.toRadians(-90));
    		transB.origin.set(new Vector3f(0,ganLLength/2,0));		
    		hingeConstraint[index] = new HingeConstraint(rbA,rbB,transA,transB);//����Լ��
    		hingeConstraint[index].setLimit(-0.7f, 0, 0.9f, 0.3f,1.0f);
    		dynamicsWorld.addConstraint(hingeConstraint[index], true);//��Լ����ӵ���������
    		hingeConstraint[index].enableAngularMotor(true, motorFlag?-1.4f:1.4f, 500f);
    	}
	}
    public void drawSelf()
    {
    	drawclaw();
		drawgan();
	}
    public void drawclaw()
    {
    	for(int i=0;i<body.length;i++){
	    	MatrixState3D.pushMatrix();
			Transform trans=body[i].getMotionState().getWorldTransform(new Transform());//��ȡ�������ı任��Ϣ����
			MatrixState3D.translate(trans.origin.x,trans.origin.y, trans.origin.z);//������λ�任
			trans.getOpenGLMatrix(MatrixState3D.getMMatrix());
			MatrixState3D.pushMatrix();
			MatrixState3D.scale(scaleblclaw*1, scaleblclaw*1, scaleblclaw*1);
			bodyForDraws[i].drawSelf(clawId);
			MatrixState3D.popMatrix();
		    MatrixState3D.popMatrix();
    	}
	}
    public void drawgan()
    {
    	for(int i=0;i<bodyg.length;i++){
			MatrixState3D.pushMatrix();
			Transform trans=bodyg[i].getMotionState().getWorldTransform(new Transform());//��ȡ�������ı任��Ϣ����
			MatrixState3D.translate(trans.origin.x,trans.origin.y, trans.origin.z);//������λ�任
			trans.getOpenGLMatrix(MatrixState3D.getMMatrix());
			MatrixState3D.pushMatrix();
			MatrixState3D.scale(scalebl*1, scalebl*1, scalebl*1);
			if(i==0){
				bodyForDraws[4].drawSelf(ganId);
			}
			if(i==1){
				bodyForDraws[5].drawSelf(dunId);
			}
			MatrixState3D.popMatrix();
			
			MatrixState3D.popMatrix();
		}
	}
    
    public void changeMotor() {
			for(int i=0;i<hingeConstraint.length;i++){
			 
				hingeConstraint[0].enableAngularMotor(true, motorFlag?1.4f:-1.4f, 500f);
				hingeConstraint[3].enableAngularMotor(true, motorFlag?1.4f:-1.4f, 500f);
				hingeConstraint[1].enableAngularMotor(true, motorFlag?1.4f:-1.4f, 500f);
				hingeConstraint[2].enableAngularMotor(true, motorFlag?-1.4f:1.4f, 500f);
			}

	}
    
    public void moveBy(Vector3f vec3){		
		MotionState ms1 = SliderHelper.cubeBody.getMotionState();
		Transform trans1 = ms1.getWorldTransform(new Transform());
		trans1.origin.add(vec3);
		ms1.setWorldTransform(trans1);
		SliderHelper.cubeBody.setMotionState(ms1);
	}  
}
