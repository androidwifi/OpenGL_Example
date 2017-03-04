package com.bn.Sample5_14;
//������
public class Cube 
{
	//���ڻ��Ƹ��������ɫ����
	ColorRect cr;
	//��߳�
	float unitSize;
	
	public Cube(MySurfaceView mv,float unitSize,float[] color)
	{
		//�������ڻ��Ƹ��������ɫ����
		cr=new ColorRect(mv,unitSize,color);
		//��¼��߳�
		this.unitSize=unitSize;
	}
	
	public void drawSelf()
	{
		//�ܻ���˼�룺ͨ����һ����ɫ������ת��λ��������ÿ�����λ��
		//�����������ÿ����		
		//�����ֳ�
		MatrixState.pushMatrix();
		
		//����ǰС��
		MatrixState.pushMatrix();
		MatrixState.translate(0, 0, unitSize);
		cr.drawSelf();		
		MatrixState.popMatrix();
		
		//���ƺ�С��
		MatrixState.pushMatrix();		
		MatrixState.translate(0, 0, -unitSize);
		MatrixState.rotate(180, 0, 1, 0);
		cr.drawSelf();		
		MatrixState.popMatrix();
		
		//�����ϴ���
		MatrixState.pushMatrix();	
		MatrixState.translate(0,unitSize,0);
		MatrixState.rotate(-90, 1, 0, 0);
		cr.drawSelf();
		MatrixState.popMatrix();
		
		//�����´���
		MatrixState.pushMatrix();	
		MatrixState.translate(0,-unitSize,0);
		MatrixState.rotate(90, 1, 0, 0);
		cr.drawSelf();
		MatrixState.popMatrix();
		
		//���������
		MatrixState.pushMatrix();	
		MatrixState.translate(unitSize,0,0);
		MatrixState.rotate(-90, 1, 0, 0);
		MatrixState.rotate(90, 0, 1, 0);
		cr.drawSelf();
		MatrixState.popMatrix();
		
		//�����Ҵ���
		MatrixState.pushMatrix();				
		MatrixState.translate(-unitSize,0,0);
		MatrixState.rotate(90, 1, 0, 0);
		MatrixState.rotate(-90, 0, 1, 0);
		cr.drawSelf();
		MatrixState.popMatrix();
		
		//�ָ��ֳ�
		MatrixState.popMatrix();
	}
	

}
