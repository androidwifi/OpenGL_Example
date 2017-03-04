package com.bn.Sample9_2;//������
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.res.Resources;
import android.util.Log;

//���ڴ�obj�ļ��м���3Dģ�͵Ĺ�����
public class LoadUtil 
{
	//��������������ķ���
	public static float[] getCrossProduct(float x1,float y1,float z1,float x2,float y2,float z2)
	{		
		//�������ʸ�����ʸ����XYZ��ķ���ABC
        float A=y1*z2-y2*z1;//������������Ĳ����������X��Y��Z���ϵķ���A��B��C
        float B=z1*x2-z2*x1;
        float C=x1*y2-x2*y1;
		
		return new float[]{A,B,C};//���ز���������
	}
	
	//������񻯵ķ���
	public static float[] vectorNormal(float[] vector)
	{
		//��������ģ
		float module=(float)Math.sqrt(vector[0]*vector[0]+vector[1]*vector[1]+vector[2]*vector[2]);
		//���ع�񻯵�����
		return new float[]{vector[0]/module,vector[1]/module,vector[2]/module};
	}
	
	//��obj�ļ��м��ؽ�Я��������Ϣ������
	//���ȼ��ض�����Ϣ���ٸ��ݶ�������������������Զ������ÿ����ķ�����
	//Ȼ�������ķ����������������ϵĶ���
    public static LoadedObjectVertexNormal loadFromFile(String fname, Resources r,MySurfaceView mv)
    {//��obj�ļ��м��ؽ�Я��������Ϣ�����壬�������淨����
    	
    	LoadedObjectVertexNormal lo=null; //���غ����������   	
    	//ԭʼ���������б�--��˳���obj�ļ��м��ص�
    	ArrayList<Float> alv=new ArrayList<Float>();
    	//������������б� --���������������֯�õ�
    	ArrayList<Float> alvResult=new ArrayList<Float>();	
    	//����������б�--���������������֯�õ�
    	ArrayList<Float> alnResult=new ArrayList<Float>();
    	
    	try
    	{
    		InputStream in=r.getAssets().open(fname);
    		InputStreamReader isr=new InputStreamReader(in);
    		BufferedReader br=new BufferedReader(isr);
    		String temps=null;
    		
    		//ѭ�����ϴ��ļ��ж�ȡ�У����������͵Ĳ�ִͬ�в�ͬ�Ĵ����߼�
		    while((temps=br.readLine())!=null) 
		    {//��ȡһ���ı�
		    	String[] tempsa=temps.split("[ ]+");//���ı����ÿո���з�
		      	if(tempsa[0].trim().equals("v"))
		      	{//����������
		      		//�������x��y��z�������ԭʼ���������б�
		      		alv.add(Float.parseFloat(tempsa[1]));
		      		alv.add(Float.parseFloat(tempsa[2]));
		      		alv.add(Float.parseFloat(tempsa[3]));
		      	}
		      	else if(tempsa[0].trim().equals("f"))
		      	{//��������
		      		/*
		      		 *��Ϊ��������������� �����Ķ����������ԭʼ���������б���
		      		 *��ȡ��Ӧ�Ķ�������ֵ��ӵ�������������б��У�ͬʱ��������
		      		 *�����������������������ӵ�����������б���
		      		*/
		      		
		      		//��ȡ�����ε�һ�����������
		      		int index=Integer.parseInt(tempsa[1].split("/")[0])-1;//�õ�������
		      		//�������ε�1�������x��y��z����ȡ��
		      		float x0=alv.get(3*index);
		      		float y0=alv.get(3*index+1);
		      		float z0=alv.get(3*index+2);
		      		alvResult.add(x0);
		      		alvResult.add(y0);
		      		alvResult.add(z0);  
		      		
		      	    //��ȡ�����εڶ������������
		      		index=Integer.parseInt(tempsa[2].split("/")[0])-1;
		      		float x1=alv.get(3*index);
		      		float y1=alv.get(3*index+1);
		      		float z1=alv.get(3*index+2);
		      		alvResult.add(x1);
		      		alvResult.add(y1);
		      		alvResult.add(z1);
		      		
		      		//��ȡ�����ε��������������
		      		index=Integer.parseInt(tempsa[3].split("/")[0])-1;
		      		float x2=alv.get(3*index);
		      		float y2=alv.get(3*index+1);
		      		float z2=alv.get(3*index+2);
		      		alvResult.add(x2);
		      		alvResult.add(y2);
		      		alvResult.add(z2);	 
		      		
		      		//ͨ��������������������0-1��0-2�����õ�����ķ�����
		      		
		      		//���������е�һ���㵽�ڶ����������
		      		float vxa=x1-x0;
		      		float vya=y1-y0;
		      		float vza=z1-z0;
		      		//���������е�һ���㵽�������������
		      		float vxb=x2-x0;
		      		float vyb=y2-y0;
		      		float vzb=z2-z0;
		      		
		      		//ͨ�������������Ĳ�����������������ķ�����
		      		float[] vNormal=vectorNormal
		      		                ( 
	                                    getCrossProduct
						      			(
						      					vxa,vya,vza,vxb,vyb,vzb
						      			)
		      		                );
		      		//��������ķ�������ӵ�����������б���
		      		for(int i=0;i<3;i++)
		      	    {
		      	    	alnResult.add(vNormal[0]);
		      	    	alnResult.add(vNormal[1]);
		      	    	alnResult.add(vNormal[2]);
		      	    }
		      	}		      		
		    } 
		    
		    //obj�ļ���ȡ���������ɶ������鼰���ɷ���������
		    //���ɶ�������
		    int size=alvResult.size();//��ȡ������������б�Ĵ�С
		    float[] vXYZ=new float[size];
		    for(int i=0;i<size;i++)
		    {
		    	vXYZ[i]=alvResult.get(i);
		    }
		    
		    //���ɷ���������
		    size=alnResult.size();//��ȡ����������б�Ĵ�С
		    float[] nXYZ=new float[size];
		    for(int i=0;i<size;i++)
		    {		    	
		    	nXYZ[i]=alnResult.get(i);
		    }
		    
		    lo=new LoadedObjectVertexNormal(mv,vXYZ,nXYZ);//���������������
    	}
    	catch(Exception e)
    	{
    		Log.d("load error", "load error");
    		e.printStackTrace();
    	}    	
    	return lo;//���ش�����������������
    }
}
