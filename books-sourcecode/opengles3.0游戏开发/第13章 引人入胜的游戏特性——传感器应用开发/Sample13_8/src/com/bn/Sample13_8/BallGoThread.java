package com.bn.Sample13_8;
/*
 * �������˶����߳�
 */
public class BallGoThread extends Thread {
	BallForControl ballForControl;//����AllBalls������
	int timeSpan=12;
	private boolean flag=false;//ѭ����־λ
	
	public BallGoThread(BallForControl ballForControl)//������
	{
		this.ballForControl=ballForControl;//��Ա������ֵ
	}
	
	@Override
	public void run()//��дrun����
	{
		while(flag)//whileѭ��
		{	
			ballForControl.go();//����ʹ�������˶��ķ���
			
			try{
				Thread.sleep(timeSpan);//һ��ʱ������˶�
			}
			catch(Exception e){
				e.printStackTrace();//��ӡ�쳣
			}
		}		
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
