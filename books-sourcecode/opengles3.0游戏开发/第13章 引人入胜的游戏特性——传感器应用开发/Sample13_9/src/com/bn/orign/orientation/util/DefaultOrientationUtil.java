package com.bn.orign.orientation.util;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;

public class DefaultOrientationUtil 
{
	public static DefaultOrientation defaultOrientation;
	
	public static void calDefaultOrientation(Activity activity)
	{
		Display display;//����Display����
	    display = activity.getWindowManager().getDefaultDisplay();//��ȡDisplay����
	    int rotation = display.getRotation();//��ȡ��ǰ��̬�����ԭʼ��̬����ת�Ƕ�
	    int widthOrign=0;//��ʼ��̬��Ļ��ȴ洢����
	    int heightOrign=0;//��ʼ��̬��Ļ�߶ȴ洢����
	    DisplayMetrics dm = new DisplayMetrics();//����DisplayMetrics����
	    display.getMetrics(dm);//����ʾ�豸��Ϣ����DisplayMetrics����
	    switch (rotation) 
	    {
	    //��ǰ��̬�����ԭʼ��̬��ת��0�Ȼ�180��
		    case Surface.ROTATION_0:
		    case Surface.ROTATION_180:
		    	widthOrign=dm.widthPixels;
		    	heightOrign=dm.heightPixels;
		    break;
		    //��ǰ��̬�����ԭʼ��̬��ת��90�Ȼ�270��
		    case Surface.ROTATION_90:       
		    case Surface.ROTATION_270:
		    	widthOrign=dm.heightPixels;
		    	heightOrign=dm.widthPixels;
		    break;
	    }
	    
	    if(widthOrign>heightOrign)//����ʼ��̬����Ļ��ȴ��ڸ߶����ʼΪ����
	    {
	    	defaultOrientation=DefaultOrientation.LANDSCAPE;
	    }
	    else//����ʼ��̬����Ļ��Ȳ����ڸ߶����ʼΪ����
	    {
	    	defaultOrientation=DefaultOrientation.PORTRAIT;
	    }
	}
}
