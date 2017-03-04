package com.bn.Sample7_9;

import java.io.IOException;
import java.io.InputStream;
import java.nio.*;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ConvertUtil 
{
	public static ByteBuffer convertPicsToBuffer(Resources res,int[] resIds,int width,int height)
	{
		//ÿ��ͼ�����ֽ���
		int perPicByteCount=width*height*4;
		//����һ��ͼƬ��Ӧ����Ĵ�С�����ܻ���
		ByteBuffer buf=ByteBuffer.allocateDirect(perPicByteCount*resIds.length);
		//����ÿһ��ͼ
		for(int i=0;i<resIds.length;i++)
		{
			int id=resIds[i];//��ȡÿһ��ͼ��id
			//ͨ����������ͼƬ���ص��ڴ�===============begin===================
			InputStream is = res.openRawResource(id);//����ָ������ͼ����
			Bitmap bitmapTmp;//���غ��ͼƬ��������
	        try 
	        {
	        	bitmapTmp = BitmapFactory.decodeStream(is);	//�����м���ͼƬ����        	
	        } 
	        finally 
	        {
	            try {is.close();}catch(IOException e){e.printStackTrace();}
	        }
	        //ͨ������������ͼƬ===============end=====================  
	        buf.position(i*perPicByteCount);//���û�������ʼλ��
	        bitmapTmp.copyPixelsToBuffer(buf);//��ͼ������ؿ������ܻ���	        
	        bitmapTmp.recycle();//���سɹ����ͷ�ͼƬ
		}

		return buf;		//���ؼ��������ݺ�Ļ���
	}
}
