package com.bn.Sample13_8;

import static com.bn.Sample13_8.ShaderUtil.createProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.annotation.SuppressLint;
import android.opengl.GLES30;

//����ƽ��
public class TextureRect 
{	
	int mProgram;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������
    int maPositionHandle; //����λ���������� 
    int maTexCoorHandle; //��������������������
    
    int muMMatrixHandle;//λ�á���ת�����ű任����
    int maCameraHandle; //�����λ����������
    int maNormalHandle; //���㷨������������
    int maLightLocationHandle;//��Դλ���������� 
    
    
    String mVertexShader;//������ɫ������ű�	 
    String mFragmentShader;//ƬԪ��ɫ������ű�
	
	FloatBuffer   mVertexBuffer;//�����������ݻ���
	FloatBuffer   mTexCoorBuffer;//���������������ݻ���
	FloatBuffer   mNormalBuffer;//���㷨�������ݻ���
    int vCount=0;   
    float xAngle=0;//��x����ת�ĽǶ�
    float yAngle=0;//��y����ת�ĽǶ�
    float zAngle=0;//��z����ת�ĽǶ�
    
    public TextureRect(MySurfaceView mv,float scale,float a,float b)
    {    	
    	//���ó�ʼ���������ݵ�initVertexData����
    	initVertexData(scale,a,b);
    	//���ó�ʼ����ɫ����intShader����   
    	initShader(mv);
    }
    
    //�Զ����ʼ�������������ݵķ���
    public void initVertexData(float scale,float a, float b) {

		a*=scale;
		b*=scale;
		float xOffset=a/2;
		float yOffset=b/2;
		vCount=6;
		//�������ݳ�ʼ��
		float[] vertices=new float[]{
			-xOffset,-yOffset,0,
			xOffset,yOffset,0,
			-xOffset,yOffset,0,
        	
        	-xOffset,-yOffset,0,
        	xOffset,-yOffset,0,
        	xOffset,yOffset,0
		};
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);//���������������ݻ���
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        //���������ݳ�ʼ�� 
		float[] normals=new float[]{
				0,0,1,
				0,0,1,
				0,0,1,
				
				0,0,1,
				0,0,1,
				0,0,1,
		};//����������
        ByteBuffer nbb = ByteBuffer.allocateDirect(normals.length*4);//�������㷨�������ݻ���
        nbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mNormalBuffer = nbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        mNormalBuffer.put(normals);//�򻺳����з��붥�㷨��������
        mNormalBuffer.position(0);//���û�������ʼλ��
        //�������ݵĳ�ʼ��
        float[] textures=new float[]{//��������S��T����ֵ����
        		0,1,
        		1,0,
        		0,0,
        		
        		0,1,
        		1,1,
        		1,0,
        };
        ByteBuffer cbb = ByteBuffer.allocateDirect(textures.length*4);//���������������ݻ���
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTexCoorBuffer = cbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        mTexCoorBuffer.put(textures);//�򻺳����з��붥����������
        mTexCoorBuffer.position(0);//���û�������ʼλ��
	}

    //�Զ����ʼ����ɫ����initShader����
    @SuppressLint("NewApi")
	public void initShader(MySurfaceView mv)
    {
    	//���ض�����ɫ���Ľű�����
        mVertexShader=ShaderUtil.loadFromAssetsFile("vertex_tex_light.sh", mv.getResources());
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag_tex_light.sh", mv.getResources());  
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        mProgram = createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ����������
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж�������������������
        maTexCoorHandle= GLES30.glGetAttribLocation(mProgram, "aTexCoor");
        //��ȡ�������ܱ任��������
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
        
        //��ȡ�����ж��㷨������������
        maNormalHandle= GLES30.glGetAttribLocation(mProgram, "aNormal"); 
        //��ȡ�����������λ������
        maCameraHandle=GLES30.glGetUniformLocation(mProgram, "uCamera"); 
        //��ȡ�����й�Դλ������
        maLightLocationHandle=GLES30.glGetUniformLocation(mProgram, "uLightLocation"); 
        //��ȡλ�á���ת�任��������
        muMMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMMatrix");         
        
    }
    
    @SuppressLint("NewApi")
	public void drawSelf(int texId)
    {        
    	 //ָ��ʹ��ĳ��shader����
    	 GLES30.glUseProgram(mProgram);        
         //�����ձ任��������Ⱦ����
         GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
         //��λ�á���ת�任��������Ⱦ����
         GLES30.glUniformMatrix4fv(muMMatrixHandle, 1, false, MatrixState.getMMatrix(), 0); 
         //�������λ�ô�����Ⱦ����
         GLES30.glUniform3fv(maCameraHandle, 1, MatrixState.cameraFB);
         //����Դλ�ô�����Ⱦ����
         GLES30.glUniform3fv(maLightLocationHandle, 1, MatrixState.lightPositionFB);
         
         //���Ͷ���λ�����ݽ���Ⱦ����
         GLES30.glVertexAttribPointer  
         (
         		maPositionHandle,   
         		3, 
         		GLES30.GL_FLOAT, 
         		false,
                3*4,   
                mVertexBuffer
         );       
         //���Ͷ��������������ݽ���Ⱦ����
         GLES30.glVertexAttribPointer  
         (
        		maTexCoorHandle, 
         		2, 
         		GLES30.GL_FLOAT, 
         		false,
                2*4,   
                mTexCoorBuffer
         ); 
         //���Ͷ��㷨�������ݽ���Ⱦ����
         GLES30.glVertexAttribPointer  
         (
        		maNormalHandle, 
         		4, 
         		GLES30.GL_FLOAT, 
         		false,
                3*4,   
                mNormalBuffer
         ); 
         
         //���ö���λ������
         GLES30.glEnableVertexAttribArray(maPositionHandle);
         //���ö�����������
         GLES30.glEnableVertexAttribArray(maTexCoorHandle);  
         //���ö��㷨��������
         GLES30.glEnableVertexAttribArray(maNormalHandle);
         //������
         GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
         GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texId);
         
         //�����������
         GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount); 
    }
}
