package com.bn.Sample11_2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class Constant
{
	public static float[][] yArray;//�洢���ζ���߶ȵ�����
	public static final float LAND_HIGH_ADJUST=-2f;//½�صĸ߶ȵ���ֵ
	public static final float LAND_HIGHEST=20f;//½�����߲�  
	//�ӻҶ�ͼƬ�м���½����ÿ������ĸ߶�
	public static float[][] loadLandforms(Resources resources,int index)
	{
		Bitmap bt=BitmapFactory.decodeResource(resources, index);//����Ҷ�ͼ
		int colsPlusOne=bt.getWidth(); //��ô洢���ζ���߶����������
		int rowsPlusOne=bt.getHeight(); //��ô洢���ζ���߶����������
		float[][] result=new float[rowsPlusOne][colsPlusOne];//�����洢���ζ���߶ȵ�����
		for(int i=0;i<rowsPlusOne;i++)//�ԻҶ�ͼ�����б���
		{
			for(int j=0;j<colsPlusOne;j++)//�ԻҶ�ͼ�����б���
			{
				int color=bt.getPixel(j,i);//���ָ�����д����ص���ɫ
				//��ø����غ졢�̡�������ɫ��ͨ����ֵ
				int r=Color.red(color);
				int g=Color.green(color); 
				int b=Color.blue(color);
				int h=(r+g+b)/3;//3��ɫ��ͨ����ƽ��
				result[i][j]=h*LAND_HIGHEST/255+LAND_HIGH_ADJUST;  //����ʽ���㶥�㺣��
			}
		}
		return result;//���ش洢���ζ���߶ȵ�����
	}
}