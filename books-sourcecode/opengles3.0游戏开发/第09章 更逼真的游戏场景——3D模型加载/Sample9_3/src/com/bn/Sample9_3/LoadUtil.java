package com.bn.Sample9_3;//������
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import android.content.res.Resources;
import android.util.Log;

public class LoadUtil 
{
	//�����������Ĳ��
	public static float[] getCrossProduct(float x1,float y1,float z1,float x2,float y2,float z2)
	{		
		//�������ʸ�����ʸ����XYZ��ķ���ABC
        float A=y1*z2-y2*z1;
        float B=z1*x2-z2*x1;
        float C=x1*y2-x2*y1;
		
		return new float[]{A,B,C};
	}
	
	//�������
	public static float[] vectorNormal(float[] vector)
	{
		//��������ģ
		float module=(float)Math.sqrt(vector[0]*vector[0]+vector[1]*vector[1]+vector[2]*vector[2]);
		return new float[]{vector[0]/module,vector[1]/module,vector[2]/module};
	}
	
	//��obj�ļ��м���Я��������Ϣ�����壬���Զ�����ÿ�������ƽ��������
    public static LoadedObjectVertexNormal loadFromFile(String fname, Resources r,MySurfaceView mv)
    {
    	//���غ����������
    	LoadedObjectVertexNormal lo=null;
    	//ԭʼ���������б�--ֱ�Ӵ�obj�ļ��м���
    	ArrayList<Float> alv=new ArrayList<Float>();
    	//����������б�--���������Ϣ���ļ��м���
    	ArrayList<Integer> alFaceIndex=new ArrayList<Integer>();
    	//������������б�--������֯��
    	ArrayList<Float> alvResult=new ArrayList<Float>();    	
    	//ƽ��ǰ������ŵĵ�ķ��������ϵ�Map,��HashMap��keyΪ��ı�ţ�
    	//valueΪ�����ڵĸ�����ķ������ļ���
    	HashMap<Integer,HashSet<Normal>> hmn=new HashMap<Integer,HashSet<Normal>>();  
    	
    	try
    	{
    		InputStream in=r.getAssets().open(fname);
    		InputStreamReader isr=new InputStreamReader(in);
    		BufferedReader br=new BufferedReader(isr);
    		String temps=null;
    		
    		//ɨ���ļ������������͵Ĳ�ִͬ�в�ͬ�Ĵ����߼�
		    while((temps=br.readLine())!=null) 
		    {//��ȡһ���ı�
		    	
		    	String[] tempsa=temps.split("[ ]+");//���ı����ÿո���з�
		      	if(tempsa[0].trim().equals("v"))
		      	{//����������
		      	    //��Ϊ��������������ȡ���˶����XYZ������ӵ�ԭʼ���������б���
		      		alv.add(Float.parseFloat(tempsa[1]));
		      		alv.add(Float.parseFloat(tempsa[2]));
		      		alv.add(Float.parseFloat(tempsa[3]));
		      	}
		      	else if(tempsa[0].trim().equals("f"))
		      	{//��������
		      		/*
		      		 *��Ϊ��������������� �����Ķ����������ԭʼ���������б���
		      		 *��ȡ��Ӧ�Ķ�������ֵ��ӵ�������������б��У�ͬʱ��������
		      		 *�����������������ķ���������ӵ�ƽ��ǰ����������Ӧ�ĵ�
		      		 *�ķ�����������ɵ�Map��
		      		*/
		      		
		      		int[] index=new int[3];//������3��������ֵ������
		      		
		      		//�����0�����������������ȡ�˶����XYZ��������	      		
		      		index[0]=Integer.parseInt(tempsa[1].split("/")[0])-1;//�õ�������
		      		//�������ε�1�������x��y��z����ȡ��
		      		float x0=alv.get(3*index[0]);
		      		float y0=alv.get(3*index[0]+1);
		      		float z0=alv.get(3*index[0]+2);
		      		alvResult.add(x0);
		      		alvResult.add(y0);
		      		alvResult.add(z0);		
		      		
		      	    //�����1�����������������ȡ�˶����XYZ��������	  
		      		index[1]=Integer.parseInt(tempsa[2].split("/")[0])-1;
		      		float x1=alv.get(3*index[1]);
		      		float y1=alv.get(3*index[1]+1);
		      		float z1=alv.get(3*index[1]+2);
		      		alvResult.add(x1);
		      		alvResult.add(y1);
		      		alvResult.add(z1);
		      		
		      	    //�����2�����������������ȡ�˶����XYZ��������	
		      		index[2]=Integer.parseInt(tempsa[3].split("/")[0])-1;
		      		float x2=alv.get(3*index[2]);
		      		float y2=alv.get(3*index[2]+1);
		      		float z2=alv.get(3*index[2]+2);
		      		alvResult.add(x2);
		      		alvResult.add(y2); 
		      		alvResult.add(z2);	
		      		
		      		//��¼����������3�����������
		      		alFaceIndex.add(index[0]);
		      		alFaceIndex.add(index[1]);
		      		alFaceIndex.add(index[2]);
		      		
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
		      		float[] vNormal=vectorNormal(getCrossProduct
					      			(
					      					vxa,vya,vza,vxb,vyb,vzb
					      			));
		      		//������������ķ�������¼������3��������Եķ�����������
		      		for(int tempInxex:index)
		      		{
		      			//��ȡָ����Ŷ���ķ���������
		      			HashSet<Normal> hsn=hmn.get(tempInxex);
		      			if(hsn==null)
		      			{	//�����ϲ������򴴽�
		      				hsn=new HashSet<Normal>();
		      			}
		      			//���˵�ķ�������ӵ�������
		      			//����Normal����д��equals���������ͬ���ķ����������ظ������ڴ˵�
		      			//��Ӧ�ķ�����������
		      			hsn.add(new Normal(vNormal[0],vNormal[1],vNormal[2]));//���˵�ķ�������ӵ�������
		      			//�����������ϷŽ�HashMap��
		      			hmn.put(tempInxex, hsn);
		      		}
		      	}		      		
		    } 
		    
		    //���ɶ�������
		    int size=alvResult.size();
		    float[] vXYZ=new float[size];
		    for(int i=0;i<size;i++)
		    {
		    	vXYZ[i]=alvResult.get(i);
		    }
		    
		    //���ɷ���������
		    float[] nXYZ=new float[alFaceIndex.size()*3];//���ڴ�ŷ��������ݵ�����
		    int c=0;//��������������
		    for(Integer i:alFaceIndex)
		    {//�Խ��������ÿ������ı��ѭ��
		    	//���ݵ�ǰ�ı�Ŵ�Map��ȡ��һ������ķ������ļ���
		    	HashSet<Normal> hsn=hmn.get(i);
		    	//��������з�������ƽ��������
		    	float[] tn=Normal.getAverage(hsn);	
		    	//���������ƽ����������ŵ�������������
		    	nXYZ[c++]=tn[0];
		    	nXYZ[c++]=tn[1];
		    	nXYZ[c++]=tn[2];
		    }
		    //���������������
		    lo=new LoadedObjectVertexNormal(mv,vXYZ,nXYZ);
    	}
    	catch(Exception e)
    	{
    		Log.d("load error", "load error");
    		e.printStackTrace();
    	}    	
    	return lo;//���ش�����������������
    }
}
