package com.bn.Sample11_1;

import static com.bn.Sample11_1.MySurfaceView.*;

//����������
public class SingleTree implements Comparable<SingleTree>
{
	float x;//ֲ���x����
	float z;//ֲ���z����
	float yAngle;//ֲ�����������y����ת�ĽǶ�
	TreeGroup tg;//����TreeGroup������
	public SingleTree(float x,float z,float yAngle,TreeGroup tg)
	{
		//��ʼ��ֲ���x��z����
		this.x=x;
		this.z=z;
		this.yAngle=yAngle;//��ʼ��ֲ�����������y����ת�ĽǶ�
		this.tg=tg;
	}
	public void drawSelf(int texId)
	{
		MatrixState.pushMatrix();		
		MatrixState.translate(x, 0, z);
		MatrixState.rotate(yAngle, 0, 1, 0);
		tg.tfd.drawSelf(texId);//����TreeForDraw�е�drawSelf����������ľ
		MatrixState.popMatrix();
	}
	public void calculateBillboardDirection()
	{//���������λ�ü�����ľ�泯��
		float xspan=x-cx;//�����ֲ��λ�õ������λ��������x����
		float zspan=z-cz;//�����ֲ��λ�õ������λ��������z����
		
		//��������������������������������y����ת�ĽǶ�
		if(zspan<=0)
		{
			yAngle=(float)Math.toDegrees(Math.atan(xspan/zspan));	
		}
		else
		{
			yAngle=180+(float)Math.toDegrees(Math.atan(xspan/zspan));
		}
	}
	@Override
	public int compareTo(SingleTree another)
	{
		//����ֲ�ﵽ������ľ���Ƚ�����ֲ���С���ķ���
		float xs=x-cx;//����ӱ�ֲ��λ�õ������λ��������x����
		float zs=z-cz;//����ӱ�ֲ��λ�õ������λ��������z����
		
		float xo=another.x-cx;//�������һֲ��λ�õ������λ��������x����
		float zo=another.z-cz;//�������һֲ��λ�õ������λ��������z����
		
		float disA=(float)Math.sqrt(xs*xs+zs*zs);//���㵱ǰֲ�ﵽ������ľ���
		float disB=(float)Math.sqrt(xo*xo+zo*zo);//������һֲ�ﵽ������ľ���
		
		return ((disA-disB)==0)?0:((disA-disB)>0)?-1:1;  //���ݾ����С������������ֵ
	}
}