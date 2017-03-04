package com.bn.Sample11_9;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class FontUtil 
{
	static int cIndex=0;//�ı���������
	static final float textSize=40;//����Ĵ�С
	static int R=255;//���ʺ�ɫͨ����ֵ
	static int G=255;//������ɫͨ����ֵ
	static int B=255;//������ɫͨ����ֵ
	public static Bitmap generateWLT(String[] str,int width,int height)
	{//�����ı�����ͼ�ķ���
		Paint paint=new Paint();//�������ʶ���
		paint.setARGB(255, R, G, B);//���û�����ɫ
		paint.setTextSize(textSize);//���������С
		paint.setTypeface(null);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);//�򿪿���ݣ�ʹ�����Ե�⻬
		Bitmap bmTemp=Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvasTemp = new Canvas(bmTemp);//����ָ����λͼ��������
		for(int i=0;i<str.length;i++)//���Ƶ�ǰ����ͼ��Ӧ��ÿ������
		{
			canvasTemp.drawText(str[i], 0, textSize*i+(i-1)*5, paint);
		}
		return bmTemp;//���ػ��Ƶ���Ϊ����ͼ��λͼ
	}
	static String[] content=
	{
		"�Կ��Ϻ�ӧ���⹳˪ѩ����",
		"�����հ����������ǡ�",
		"ʮ��ɱһ�ˣ�ǧ�ﲻ���С�",
		"���˷���ȥ�������������",
		"�й����������ѽ�ϥǰ�ᡣ",
		"������캥������Ȱ������",
		"������Ȼŵ��������Ϊ�ᡣ",
		"�ۻ����Ⱥ�������������",
		"���Իӽ�鳣��������𾪡�",
		"ǧ���׳ʿ���Ӻմ����ǡ�",
		"���������㣬��������Ӣ��",
		"˭�����x�£�����̫������",
	};
	
	public static String[] getContent(int length,String[] content)
	{//��ȡָ�������ַ�������ķ���
		String[] result=new String[length+1];//�����ַ�������
		for(int i=0;i<=length;i++)
		{
			result[i]=content[i];//����ǰ��Ҫ��������������
		}
		return result;
	}
	
	public static void updateRGB()//�������������ɫֵ�ķ���
	{
		R=(int)(255*Math.random());//����������ʺ�ɫͨ��ֵ
		G=(int)(255*Math.random());//�������������ɫͨ��ֵ
		B=(int)(255*Math.random());//�������������ɫͨ��ֵ
	}
}