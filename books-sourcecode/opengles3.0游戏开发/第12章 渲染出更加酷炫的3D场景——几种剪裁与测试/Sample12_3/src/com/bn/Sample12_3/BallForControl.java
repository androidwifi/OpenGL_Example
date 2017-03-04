package com.bn.Sample12_3;
import static com.bn.Sample12_3.Constant.*;
//�洢���˶�������������Ϣ�Ķ���������
public class BallForControl   
{	
	public static final float TIME_SPAN=0.05f;//��λʱ����
	public static final float G=0.8f;//�������ٶ�
	
	BallTextureByVertex btv;//���ڻ��Ƶ�����
	float startY;//ÿ����ʼ��λ��
	float timeLive=0;//�����ڴ��ʱ��
	float currentY=0;//��ǰYλ��
	float vy=0;//ÿ�ֳ�ʼ�ٶ�
	
	public BallForControl(BallTextureByVertex btv,float startYIn)
	{
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
					
					
					if(tempCurrY<=0)
					{//����ǰλ�õ��ڵ������������淴��
						//��������ʼλ��Ϊ0
						startY=0;		
						//��������ʼ�ٶ�
						vy=-(vy-G*timeLive)*0.995f;
						//����������˶�ʱ����0
						timeLive=0;
						//���ٶ�С����ֵ��ֹͣ�˶�
						if(vy<0.35f)
						{
							currentY=0;
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
	{//���������Լ�		
		MatrixState.pushMatrix();
		MatrixState.translate(0, UNIT_SIZE*BALL_SCALE+currentY, 0);
		btv.drawSelf(texId);		
		MatrixState.popMatrix();
	}
	
	public void drawSelfMirror(int texId)
	{//���� ������		
		MatrixState.pushMatrix();
		MatrixState.translate(0, -UNIT_SIZE*BALL_SCALE-currentY, 0);
		btv.drawSelf(texId);		
		MatrixState.popMatrix();
	}
}
