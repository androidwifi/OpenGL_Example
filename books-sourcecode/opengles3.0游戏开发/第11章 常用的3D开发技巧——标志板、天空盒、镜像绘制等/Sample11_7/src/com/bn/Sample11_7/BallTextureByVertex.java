package com.bn.Sample11_7;
import static com.bn.Sample11_7.Constant.*;
import static com.bn.Sample11_7.ShaderUtil.createProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import android.opengl.GLES30;

//���ڻ��Ƶ�����
public class BallTextureByVertex 
{	
	int mProgram;//�Զ�����Ⱦ���߳���id
    int muMVPMatrixHandle;//�ܱ任��������id
    int maPositionHandle; //����λ����������id  
    int maTexCoorHandle; //��������������������id  
    String mVertexShader;//������ɫ��    	 
    String mFragmentShader;//ƬԪ��ɫ��
    static float[] mMMatrix = new float[16];//����������ƶ���ת����
	
	FloatBuffer   mVertexBuffer;//�����������ݻ���
	FloatBuffer   mTexCoorBuffer;//���������������ݻ���
    int vCount=0;   //��������
    
    public BallTextureByVertex(MySurfaceView mv,float scale)
    {    	
    	//��ʼ����������
    	initVertexData(scale);
    	//��ʼ����ɫ��      
    	initShader(mv);
    }
    
    //��ʼ����������
    public void initVertexData(float scale)
    {
    	//��ȡ�з���ͼ����������
    	float[] texCoorArray= 
         generateTexCoor
    	 (
    			 (int)(360/ANGLE_SPAN), //����ͼ�зֵ�����
    			 (int)(180/ANGLE_SPAN)  //����ͼ�зֵ�����
    	);
        int tc=0;//�������������
        int ts=texCoorArray.length;//�������鳤��
    	
    	ArrayList<Float> alVertix=new ArrayList<Float>();//��Ŷ��������ArrayList
    	ArrayList<Float> alTexture=new ArrayList<Float>();//������������ArrayList
    	
        for(float vAngle=90;vAngle>-90;vAngle=vAngle-ANGLE_SPAN)//��ֱ����angleSpan��һ��
        {
        	for(float hAngle=360;hAngle>0;hAngle=hAngle-ANGLE_SPAN)//ˮƽ����angleSpan��һ��
        	{
        		//����������һ���ǶȺ�����Ӧ�Ĵ˵��������ϵ��ı��ζ�������
        		//��������������ı��ε�������
        		
        		double xozLength=scale*UNIT_SIZE*Math.cos(Math.toRadians(vAngle));
        		float x1=(float)(xozLength*Math.cos(Math.toRadians(hAngle)));
        		float z1=(float)(xozLength*Math.sin(Math.toRadians(hAngle)));
        		float y1=(float)(scale*UNIT_SIZE*Math.sin(Math.toRadians(vAngle)));
        		
        		xozLength=scale*UNIT_SIZE*Math.cos(Math.toRadians(vAngle-ANGLE_SPAN));
        		float x2=(float)(xozLength*Math.cos(Math.toRadians(hAngle)));
        		float z2=(float)(xozLength*Math.sin(Math.toRadians(hAngle)));
        		float y2=(float)(scale*UNIT_SIZE*Math.sin(Math.toRadians(vAngle-ANGLE_SPAN)));
        		
        		xozLength=scale*UNIT_SIZE*Math.cos(Math.toRadians(vAngle-ANGLE_SPAN));
        		float x3=(float)(xozLength*Math.cos(Math.toRadians(hAngle-ANGLE_SPAN)));
        		float z3=(float)(xozLength*Math.sin(Math.toRadians(hAngle-ANGLE_SPAN)));
        		float y3=(float)(scale*UNIT_SIZE*Math.sin(Math.toRadians(vAngle-ANGLE_SPAN)));
        		
        		xozLength=scale*UNIT_SIZE*Math.cos(Math.toRadians(vAngle));
        		float x4=(float)(xozLength*Math.cos(Math.toRadians(hAngle-ANGLE_SPAN)));
        		float z4=(float)(xozLength*Math.sin(Math.toRadians(hAngle-ANGLE_SPAN)));
        		float y4=(float)(scale*UNIT_SIZE*Math.sin(Math.toRadians(vAngle)));   
        		
        		//������һ������
        		alVertix.add(x1);alVertix.add(y1);alVertix.add(z1);
        		alVertix.add(x2);alVertix.add(y2);alVertix.add(z2);
        		alVertix.add(x4);alVertix.add(y4);alVertix.add(z4);        		
        		//�����ڶ�������
        		alVertix.add(x4);alVertix.add(y4);alVertix.add(z4);
        		alVertix.add(x2);alVertix.add(y2);alVertix.add(z2);
        		alVertix.add(x3);alVertix.add(y3);alVertix.add(z3); 
        		
        		//��һ������3�������6����������
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);        		
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);
        		//�ڶ�������3�������6����������
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);        		
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);       		
        	}
        } 	
        
        vCount=alVertix.size()/3;//���������Ϊ����ֵ������1/3����Ϊһ��������3������
    	
        //��alVertix�е�����ֵת�浽һ��int������
        float vertices[]=new float[vCount*3];
    	for(int i=0;i<alVertix.size();i++)
    	{
    		vertices[i]=alVertix.get(i);
    	}
		
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        
        //���������������ݵĳ�ʼ��
        float texCoor[]=new float[alTexture.size()];//��������ֵ����
        for(int i=0;i<alTexture.size();i++) 
        {
        	texCoor[i]=alTexture.get(i);
        }
        //�������������������ݻ���
        ByteBuffer cbb = ByteBuffer.allocateDirect(texCoor.length*4);
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTexCoorBuffer = cbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTexCoorBuffer.put(texCoor);//�򻺳����з��붥����ɫ����
        mTexCoorBuffer.position(0);//���û�������ʼλ��
    }

    //��ʼ����ɫ��
    public void initShader(MySurfaceView mv)
    {
    	//���ض�����ɫ���Ľű�����
        mVertexShader=ShaderUtil.loadFromAssetsFile("vertex_tex.sh", mv.getResources());
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag_tex.sh", mv.getResources());  
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        mProgram = createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ����������id  
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж�������������������id  
        maTexCoorHandle= GLES30.glGetAttribLocation(mProgram, "aTexCoor");
        //��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");  
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
    			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    			
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t+sizeh;    			
    		}
    	}
    	return result;
    }
}