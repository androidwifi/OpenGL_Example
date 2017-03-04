package com.bn.Sample11_7;

import static com.bn.Sample11_7.Constant.*;
import android.opengl.GLES30;
//�洢���˶�������������Ϣ�Ķ���������
public class BallForControl   
{	
	public static final float TIME_SPAN=0.05f;//����ģ��ĵ�λʱ����
	public static final float G=0.8f;//�������ٶ�
	 
	BallTextureByVertex btv;//���ڻ��Ƶ������������
	float startY;//ÿ����ʼ��λ��
	float timeLive=0;//�����ڴ��ʱ��
	float currentY=0;//��ǰYλ��
	float vy=0;//ÿ�ֳ�ʼy�᷽���ٶ�
	
	public BallForControl(BallTextureByVertex btv,float startYIn)
	{
		//��ʼ���ٶ�����ʼλ��
		this.btv=btv;
		this.startY=startYIn;		
		currentY=startYIn;
		new Thread()
		{//����һ���߳��˶�����
			public void run()
			{
				while(true)
				{
					//�����˶�ʱ������
					timeLive+=TIME_SPAN;
					//���ݴ�����ʼY���ꡢ�����˶�ʱ�䡢������ʼ�ٶȼ��㵱ǰλ��
					float tempCurrY=startY-0.5f*G*timeLive*timeLive+vy*timeLive;
					
					
					if(tempCurrY<=FLOOR_Y)
					{//����ǰλ�õ��ڵ������������淴��
						//��������ʼ�߶�Ϊ0
						startY=FLOOR_Y;		
						//��������ʼ�ٶ�
						vy=-(vy-G*timeLive)*0.8f;
						//����������˶�ʱ����0
						timeLive=0;
						//���ٶ�С����ֵ��ֹͣ�˶�
						if(vy<0.35f)
						{
							currentY=FLOOR_Y;
							break;
						}
					}
					else
					{//��û�����������������˶�
						currentY=tempCurrY;
					}
					
					try
					{
						Thread.sleep(20);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	public void drawSelf(int texId)
	{//������������	
		MatrixState.pushMatrix();
		MatrixState.translate(0, UNIT_SIZE*BALL_SCALE+currentY, 0);
		btv.drawSelf(texId);		//��������			
		MatrixState.popMatrix();
	}
	
	public void drawSelfMirror(int texId)
	{//���� ������		
		GLES30.glFrontFace(GLES30.GL_CW);//˳ʱ�����
		MatrixState.pushMatrix();	
		MatrixState.scale(1, -1, 1);
		MatrixState.translate(0, UNIT_SIZE*BALL_SCALE+currentY-2*FLOOR_Y, 0);
		btv.drawSelf(texId);		//��������
		MatrixState.popMatrix();
		GLES30.glFrontFace(GLES30.GL_CCW);//��ʱ�����
	}
}
