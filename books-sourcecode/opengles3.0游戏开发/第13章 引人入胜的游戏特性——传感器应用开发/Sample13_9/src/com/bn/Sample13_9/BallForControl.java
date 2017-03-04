package com.bn.Sample13_9;

import static com.bn.Sample13_9.Constant.*;
import android.opengl.Matrix;

//���ڿ��Ƶ��� 
public class BallForControl {
	MySurfaceView mv;
	Ball ball;//���ڻ��Ƶ�����
	
	float rotateX;		//�����ת��
	float rotateY;
	float rotateZ;
	
	float tempX;	//��λ�õ���ʱ����
	float tempZ;
	
	float tempSPANX;	//���ƶ��������ʱ����
	float tempSPANZ;
	
	float tempLength;	//��ǰ���ľ�����ʱ����
	
	float tempAngle;	//����ת�ĽǶ�

	float[] selfRotateMatrix;//�Դ���ת����
	
	public BallForControl(MySurfaceView mv,float scale,float aHalf,int n)
	{
		this.mv=mv;
		ball=new Ball(mv,scale,aHalf,n);
		//��ʼ���Դ���ת����
		selfRotateMatrix=new float[16];
		//��ʼʱ��תһ���Ķ���
		Matrix.setRotateM(selfRotateMatrix, 0, 10, 0, 1, 0);
	}
	
	public void drawSelf()
	{
		MatrixState.pushMatrix();
		//�ƶ���ָ��λ��
		MatrixState.translate(Constant.XOFFSET, 1.2f, Constant.ZOFFSET);		
		//�����Դ���ת����
		MatrixState.matrix(selfRotateMatrix);
		//������
		ball.drawSelf();		
		MatrixState.popMatrix();
	}
	
	//��ǰ���ķ���
	public void go(){
		tempSPANX=Constant.SPANX;	//���ƶ��������ʱ������ֵ
		tempSPANZ=Constant.SPANZ;
		
		tempX=Constant.XOFFSET+tempSPANX;	//���ݴ��������仯��ǰ���λ��	
		tempZ=Constant.ZOFFSET+tempSPANZ;
		
		
		//��������������߷�����ײ
		if( (tempZ<-ZBOUNDARY)||(tempZ>ZBOUNDARY))
		{	
			tempSPANZ=0;
		}
		//��������������߷�����ײ
		if((tempX<-XBOUNDARY)|| (tempX>XBOUNDARY))
		{
			tempSPANX=0;
		}
		

		//��ǰ��λ�÷����仯
		Constant.XOFFSET+=tempSPANX;
		Constant.ZOFFSET+=tempSPANZ;
		
		
		//*****************��ת begin************************

		//ǰ���ķ�������ΪConstant.SPANX Constant.SPANZ��
		//��ô��ת��Ϊ
		rotateX=tempSPANZ;
		rotateY=0;
		rotateZ=-tempSPANX;
		//ǰ���ľ���
		tempLength=(float) Math.sqrt(tempSPANX*tempSPANX+tempSPANZ*tempSPANZ);
		//����ǰ���ĽǶ�ֵ
		tempAngle=(float) Math.toDegrees(tempLength/Constant.BALLR);
		
		//�ı������ת����
		//��תʱҪ��ǶȲ�Ϊ0���᲻��ȫΪ0
		if(Math.abs(tempAngle)!=0&&(Math.abs(rotateZ)!=0||Math.abs(rotateX)!=0))
		{
			float[] newMatrix=new float[16];
			Matrix.setRotateM(newMatrix, 0, tempAngle, rotateX, rotateY, rotateZ);
			float[] resultMatrix=new float[16];
			Matrix.multiplyMM(resultMatrix, 0, newMatrix, 0, selfRotateMatrix,0);
			selfRotateMatrix=resultMatrix;
		}
		
		//************************��ת end************************
	}	
}
