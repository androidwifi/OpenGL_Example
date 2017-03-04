package com.bn.Sample11_6;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import android.opengl.GLES30;

public class Sky
{
	final float UNIT_SIZE=100.0f;
	//�Զ�����Ⱦ���������
	int mProgram;
	//�ܱ任���������
	int muMVPMatrixHandle;
	//�������Ե�����
	int maPositionHandle;
	//���������������Ե�����
	int maTexCoorHandle;
	//�������ݻ����Լ����������������ݻ���
	FloatBuffer mVertexBuffer;
	FloatBuffer mTexCoorBuffer;
	//��������
	int vCount=0;  
	
	public Sky(MySurfaceView mv)
	{
		initVertexData(UNIT_SIZE);
		initShader(mv);
	}
	//��ʼ���������ݵķ���
	public void initVertexData(float radius)
	{
		float ANGLE_SPAN=18f;
    	float angleV=90;
    	ArrayList<Float> alVertix=new ArrayList<Float>();//��Ŷ��������ArrayList
    	 
        for(float vAngle=angleV;vAngle>0;vAngle=vAngle-ANGLE_SPAN)//��ֱ����angleSpan��һ��
        {
        	for(float hAngle=360;hAngle>0;hAngle=hAngle-ANGLE_SPAN)//ˮƽ����angleSpan��һ��
        	{
        		//����������һ���ǶȺ�����Ӧ�Ĵ˵��������ϵ��ı��ζ�������
        		//��������������ı��ε�������
        		double xozLength=radius*Math.cos(Math.toRadians(vAngle));
        		float x1=(float)(xozLength*Math.cos(Math.toRadians(hAngle)));
        		float z1=(float)(xozLength*Math.sin(Math.toRadians(hAngle)));
        		float y1=(float)(radius*Math.sin(Math.toRadians(vAngle)));
        		
        		xozLength=radius*Math.cos(Math.toRadians(vAngle-ANGLE_SPAN));
        		float x2=(float)(xozLength*Math.cos(Math.toRadians(hAngle)));
        		float z2=(float)(xozLength*Math.sin(Math.toRadians(hAngle)));
        		float y2=(float)(radius*Math.sin(Math.toRadians(vAngle-ANGLE_SPAN)));
        		
        		xozLength=radius*Math.cos(Math.toRadians(vAngle-ANGLE_SPAN));
        		float x3=(float)(xozLength*Math.cos(Math.toRadians(hAngle-ANGLE_SPAN)));
        		float z3=(float)(xozLength*Math.sin(Math.toRadians(hAngle-ANGLE_SPAN)));
        		float y3=(float)(radius*Math.sin(Math.toRadians(vAngle-ANGLE_SPAN)));
        		
        		xozLength=radius*Math.cos(Math.toRadians(vAngle));
        		float x4=(float)(xozLength*Math.cos(Math.toRadians(hAngle-ANGLE_SPAN)));
        		float z4=(float)(xozLength*Math.sin(Math.toRadians(hAngle-ANGLE_SPAN)));
        		float y4=(float)(radius*Math.sin(Math.toRadians(vAngle)));
        		
        		//������һ������
        		alVertix.add(x1);alVertix.add(y1);alVertix.add(z1);
        		alVertix.add(x4);alVertix.add(y4);alVertix.add(z4);
        		alVertix.add(x2);alVertix.add(y2);alVertix.add(z2);
        		
        		//�����ڶ�������
        		alVertix.add(x2);alVertix.add(y2);alVertix.add(z2);
        		alVertix.add(x4);alVertix.add(y4);alVertix.add(z4);
        		alVertix.add(x3);alVertix.add(y3);alVertix.add(z3); 
        	}
        }
        vCount=alVertix.size()/3;//���������Ϊ����ֵ������1/3����Ϊһ��������3������
        //��alVertix�е�����ֵת�浽һ��float������
        float vertices[]=new float[vCount*3];
    	for(int i=0;i<alVertix.size();i++)
    	{
    		vertices[i]=alVertix.get(i);
    	}
        //�������ƶ������ݻ���
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        
        //�����������껺��
        float[] textureCoors=generateTexCoor
        (
       		(int)(360/ANGLE_SPAN), //����ͼ�зֵ�����
       		(int)(angleV/ANGLE_SPAN)  //����ͼ�зֵ�����
       	);
        ByteBuffer tbb = ByteBuffer.allocateDirect(textureCoors.length*4);
        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTexCoorBuffer = tbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        mTexCoorBuffer.put(textureCoors);//�򻺳����з��붥����ɫ����
        mTexCoorBuffer.position(0);//���û�������ʼλ��
	}
	//��ʼ��Shader����ķ���
	public void initShader(MySurfaceView mv)
	{
		String mVertexHandle=ShaderUtil.loadFromAssetsFile("vertex.sh", mv.getResources());
		String mTexCoorHandle=ShaderUtil.loadFromAssetsFile("frag.sh", mv.getResources());
		mProgram=ShaderUtil.createProgram(mVertexHandle, mTexCoorHandle);
		//��ö����������ݵ�����
		maPositionHandle=GLES30.glGetAttribLocation(mProgram, "aPosition");
		//�����������������id
		maTexCoorHandle=GLES30.glGetAttribLocation(mProgram, "aTexCoor");
		muMVPMatrixHandle=GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
	}
	
	public void drawSelf(int texId)
	{
		//ָ��ʹ��ĳ����ɫ������
		GLES30.glUseProgram(mProgram);
		//�����ձ任����������Ⱦ����
		GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
		//������λ������������Ⱦ����
		GLES30.glVertexAttribPointer
		(
			maPositionHandle, 
			3, 
			GLES30.GL_FLOAT, 
			false, 
			3*4, 
			mVertexBuffer
		);
		//������������������������Ⱦ����
		GLES30.glVertexAttribPointer
		(
			maTexCoorHandle, 
			2, 
			GLES30.GL_FLOAT,
			false, 
			2*4, 
			mTexCoorBuffer
		);
		//���ö���λ�á�����������������
        GLES30.glEnableVertexAttribArray(maPositionHandle);  
        GLES30.glEnableVertexAttribArray(maTexCoorHandle);  
        
        //������
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texId);
        
        //�����������
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);   
	}
	//�Զ��з����������������ķ���
    public float[] generateTexCoor(int bw,int bh)
    {
    	float[] result=new float[bw*bh*6*2]; 
    	float sizew=1.0f/bw;//����
    	float sizeh=1.0f/bh;//����
    	int c=0;
    	for(int i=0;i<bh;i++)
    	{
    		for(int j=0;j<bw;j++)
    		{
    			//ÿ����һ�����Σ������������ι��ɣ��������㣬12����������
    			float s=j*sizew;
    			float t=i*sizeh;
    			
    			result[c++]=s;
    			result[c++]=t;
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    			
    			result[c++]=s+sizew;
    			result[c++]=t+sizeh;    			
    		}
    	}
    	return result;
    }
}