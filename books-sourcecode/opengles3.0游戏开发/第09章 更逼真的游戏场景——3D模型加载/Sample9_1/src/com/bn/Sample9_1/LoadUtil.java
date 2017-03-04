package com.bn.Sample9_1;//������
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.res.Resources;
import android.util.Log;

public class LoadUtil 
{
	//��obj�ļ��м��ض������ݲ�����LoadedObjectVertexOnly�����
    public static LoadedObjectVertexOnly loadFromFile(String fname, Resources r,MySurfaceView mv)
    {
    	LoadedObjectVertexOnly lo=null;//���غ����������
    	
    	ArrayList<Float> alv=new ArrayList<Float>();//ԭʼ���������б�
    	ArrayList<Float> alvResult=new ArrayList<Float>();//������������б�
    	
    	try
    	{
    		InputStream in=r.getAssets().open(fname);//��ȡ�ֽ�������
    		InputStreamReader isr=new InputStreamReader(in);//���ֽ���ת�����ַ���
    		BufferedReader br=new BufferedReader(isr);//���ַ�����һ����װ
    		String temps=null;
    		
		    while((temps=br.readLine())!=null)//��ȡһ���ı�
		    {
		    	String[] tempsa=temps.split("[ ]+");//���ı����ÿո���з�
		      	if(tempsa[0].trim().equals("v"))//����������
		      	{
		      		alv.add(Float.parseFloat(tempsa[1]));//�������x��y��z�������
		      		alv.add(Float.parseFloat(tempsa[2]));//ԭʼ���������б�
		      		alv.add(Float.parseFloat(tempsa[3]));
		      	}
		      	else if(tempsa[0].trim().equals("f"))//��������
		      	{
		      		int index=Integer.parseInt(tempsa[1].split("/")[0])-1;//�õ�������
		      		//�������ε�1�������x��y��z������������������б�
		      		alvResult.add(alv.get(3*index));
		      		alvResult.add(alv.get(3*index+1));
		      		alvResult.add(alv.get(3*index+2));
		      		
		      		index=Integer.parseInt(tempsa[2].split("/")[0])-1; //�õ�������
		      		//�������ε�2�������x��y��z������������������б�
		      		alvResult.add(alv.get(3*index));
		      		alvResult.add(alv.get(3*index+1));
		      		alvResult.add(alv.get(3*index+2));
		      		
		      		index=Integer.parseInt(tempsa[3].split("/")[0])-1;//�õ�������
		      		//�������ε�3�������x��y��z������������������б�
		      		alvResult.add(alv.get(3*index));
		      		alvResult.add(alv.get(3*index+1));
		      		alvResult.add(alv.get(3*index+2));	
		      	}		      		
		    } 
		    
		    //���ɶ�������
		    int size=alvResult.size();//��ȡ������������
		    float[] vXYZ=new float[size];//�������ڴ洢�������������
		    for(int i=0;i<size;i++)
		    {
		    	vXYZ[i]=alvResult.get(i);//��������������ת�浽������
		    }
		    
		    lo=new LoadedObjectVertexOnly(mv,vXYZ);//�����������
    	}
    	catch(Exception e)
    	{
    		Log.d("load error", "load error");
    		e.printStackTrace();
    	}    	
    	return lo;//���ش���������������
    }
}
