package com.bn.thread;

import static com.bn.constant.SourceConstant.*;

public class Angle2DThread extends Thread{
	boolean isST=false;
	public Angle2DThread()
	{
	}
	public void run() 
	{
		while(true)
		{//ѭ����ʱ�ƶ��ڵ�
			try{
				if(!isST){
					Angle2D+=1;
					if(Angle2D>2){
						isST=true;
					}
				}
				if(isST){
					Angle2D-=1;
					if(Angle2D<-2){
						isST=false;
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				Thread.sleep(60);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}	
	}
}
