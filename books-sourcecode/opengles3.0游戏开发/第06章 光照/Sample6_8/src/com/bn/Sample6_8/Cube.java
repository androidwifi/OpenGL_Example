package com.bn.Sample6_8;

import java.nio.ByteBuffer;

import static com.bn.Sample6_8.Constant.*;

import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES30;

//������
public class Cube {
	int mProgram;// �Զ�����Ⱦ������ɫ������id
	int muMVPMatrixHandle;// �ܱ任��������
    int muMMatrixHandle;//λ�á���ת�任��������
	int muRHandle;// ������İ뾶��������   
	int maPositionHandle; // ����λ����������
    int maNormalHandle; //���㷨������������
    int maLightLocationHandle;//��Դλ����������
    int maCameraHandle; //�����λ���������� 
    
    
	String mVertexShader;// ������ɫ������ű�
	String mFragmentShader;// ƬԪ��ɫ������ű�

	FloatBuffer mVertexBuffer;// �����������ݻ���
	FloatBuffer mNormalBuffer;//���㷨�������ݻ���
	int vCount = 0;
	float yAngle = 0;// ��y����ת�ĽǶ�
	float xAngle = 0;// ��x����ת�ĽǶ�
	float zAngle = 0;// ��z����ת�ĽǶ�
	float r = 1.0f;
	public Cube(MySurfaceView mv) {
		//���ó�ʼ���������ݵķ���
		initVertexData();
		//���ó�ʼ����ɫ���ķ���
		initShader(mv);
	}

	// ��ʼ���������ݵķ���
	public void initVertexData() {

    	//�����������ݵĳ�ʼ��================begin============================
        vCount=6*6; //������
        
        float vertices[]=new float[]
        {
        	//ǰ��
        	Constant.UNIT_SIZE,Constant.UNIT_SIZE,Constant.UNIT_SIZE, //0       	
        	-Constant.UNIT_SIZE,Constant.UNIT_SIZE,Constant.UNIT_SIZE,//1 
        	-Constant.UNIT_SIZE,-Constant.UNIT_SIZE,Constant.UNIT_SIZE, //2 
        	
        	Constant.UNIT_SIZE,Constant.UNIT_SIZE,Constant.UNIT_SIZE, //0 
        	-Constant.UNIT_SIZE,-Constant.UNIT_SIZE,Constant.UNIT_SIZE, //2        	
        	Constant.UNIT_SIZE,-Constant.UNIT_SIZE,Constant.UNIT_SIZE,//3 
        	//����  	
        	Constant.UNIT_SIZE,Constant.UNIT_SIZE,-Constant.UNIT_SIZE,//0  
        	Constant.UNIT_SIZE,-Constant.UNIT_SIZE,-Constant.UNIT_SIZE,//1 
        	-Constant.UNIT_SIZE,-Constant.UNIT_SIZE,-Constant.UNIT_SIZE,//2
        	
        	Constant.UNIT_SIZE,Constant.UNIT_SIZE,-Constant.UNIT_SIZE,//0
        	-Constant.UNIT_SIZE,-Constant.UNIT_SIZE,-Constant.UNIT_SIZE,//2
        	-Constant.UNIT_SIZE,Constant.UNIT_SIZE,-Constant.UNIT_SIZE,//3
        	//����	
        	-Constant.UNIT_SIZE,Constant.UNIT_SIZE,Constant.UNIT_SIZE,//0 
        	-Constant.UNIT_SIZE,Constant.UNIT_SIZE,-Constant.UNIT_SIZE,//1
        	-Constant.UNIT_SIZE,-Constant.UNIT_SIZE,-Constant.UNIT_SIZE,//2
        	
        	-Constant.UNIT_SIZE,Constant.UNIT_SIZE,Constant.UNIT_SIZE,//0 
        	-Constant.UNIT_SIZE,-Constant.UNIT_SIZE,-Constant.UNIT_SIZE,//2
        	-Constant.UNIT_SIZE,-Constant.UNIT_SIZE,Constant.UNIT_SIZE,//3
        	//����
        	Constant.UNIT_SIZE,Constant.UNIT_SIZE,Constant.UNIT_SIZE,//0  
        	Constant.UNIT_SIZE,-Constant.UNIT_SIZE,Constant.UNIT_SIZE,//1
        	Constant.UNIT_SIZE,-Constant.UNIT_SIZE,-Constant.UNIT_SIZE,//2
        	
        	Constant.UNIT_SIZE,Constant.UNIT_SIZE,Constant.UNIT_SIZE,//0  
        	Constant.UNIT_SIZE,-Constant.UNIT_SIZE,-Constant.UNIT_SIZE,//2
        	Constant.UNIT_SIZE,Constant.UNIT_SIZE,-Constant.UNIT_SIZE,//3
        	//����     
        	Constant.UNIT_SIZE,Constant.UNIT_SIZE,Constant.UNIT_SIZE,//0  
        	Constant.UNIT_SIZE,Constant.UNIT_SIZE,-Constant.UNIT_SIZE,//1
        	-Constant.UNIT_SIZE,Constant.UNIT_SIZE,-Constant.UNIT_SIZE,//2
        	
        	Constant.UNIT_SIZE,Constant.UNIT_SIZE,Constant.UNIT_SIZE,//0 
        	-Constant.UNIT_SIZE,Constant.UNIT_SIZE,-Constant.UNIT_SIZE,//2
        	-Constant.UNIT_SIZE,Constant.UNIT_SIZE,Constant.UNIT_SIZE,//3
        	//����  	
        	Constant.UNIT_SIZE,-Constant.UNIT_SIZE,Constant.UNIT_SIZE,//0  
        	-Constant.UNIT_SIZE,-Constant.UNIT_SIZE,Constant.UNIT_SIZE,//1
        	-Constant.UNIT_SIZE,-Constant.UNIT_SIZE,-Constant.UNIT_SIZE,//2
        	
        	Constant.UNIT_SIZE,-Constant.UNIT_SIZE,Constant.UNIT_SIZE,//0 
        	-Constant.UNIT_SIZE,-Constant.UNIT_SIZE,-Constant.UNIT_SIZE,//2
        	Constant.UNIT_SIZE,-Constant.UNIT_SIZE,-Constant.UNIT_SIZE,//3
        };

		// ���������������ݻ���
		// vertices.length*4����Ϊһ�������ĸ��ֽ�
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());// �����ֽ�˳��
		mVertexBuffer = vbb.asFloatBuffer();// ת��Ϊfloat�ͻ���
		mVertexBuffer.put(vertices);// �򻺳����з��붥����������
		mVertexBuffer.position(0);// ���û�������ʼλ��
		// �ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
		// ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
		
		float normals[]=vertices;
		//�������ƶ��㷨��������
        ByteBuffer nbb = ByteBuffer.allocateDirect(normals.length*4);
        nbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mNormalBuffer = nbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        mNormalBuffer.put(normals);//�򻺳����з��붥�㷨��������
        mNormalBuffer.position(0);//���û�������ʼλ��     
	}

	//��ʼ����ɫ��
	public void initShader(MySurfaceView mv) {
		// ���ض�����ɫ���Ľű�����
		mVertexShader = ShaderUtil.loadFromAssetsFile("vertex.sh",
				mv.getResources());
		// ����ƬԪ��ɫ���Ľű�����
		mFragmentShader = ShaderUtil.loadFromAssetsFile("frag.sh",
				mv.getResources());
		// ���ڶ�����ɫ����ƬԪ��ɫ����������
		mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
		// ��ȡ�����ж���λ����������
		maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
		// ��ȡ�������ܱ任��������
		muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
        //��ȡλ�á���ת�任��������
        muMMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMMatrix");  
		// ��ȡ������������뾶����
		muRHandle = GLES30.glGetUniformLocation(mProgram, "uR");
        //��ȡ�����ж��㷨������������  
        maNormalHandle= GLES30.glGetAttribLocation(mProgram, "aNormal");
        //��ȡ�����й�Դλ������
        maLightLocationHandle=GLES30.glGetUniformLocation(mProgram, "uLightLocation");
        //��ȡ�����������λ������
        maCameraHandle=GLES30.glGetUniformLocation(mProgram, "uCamera"); 
	}

	public void drawSelf() {		
    	MatrixState.rotate(xAngle, 1, 0, 0);//��X��ת��
    	MatrixState.rotate(yAngle, 0, 1, 0);//��Y��ת��
    	MatrixState.rotate(zAngle, 0, 0, 1);//��Z��ת��
		//ָ��ʹ��ĳ����ɫ������
		GLES30.glUseProgram(mProgram);
		// �����ձ任��������Ⱦ����
		GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false,
				MatrixState.getFinalMatrix(), 0); 
        //��λ�á���ת�任��������Ⱦ����
        GLES30.glUniformMatrix4fv(muMMatrixHandle, 1, false, MatrixState.getMMatrix(), 0);
		// ���뾶�ߴ紫����Ⱦ����
		GLES30.glUniform1f(muRHandle, r * UNIT_SIZE);  
        //����Դλ�ô�����Ⱦ����
        GLES30.glUniform3fv(maLightLocationHandle, 1, MatrixState.lightPositionFB);
        //�������λ�ô�����Ⱦ����
        GLES30.glUniform3fv(maCameraHandle, 1, MatrixState.cameraFB);
        
		// ������λ�����ݴ�����Ⱦ����
		GLES30.glVertexAttribPointer(maPositionHandle, 3, GLES30.GL_FLOAT,
				false, 3 * 4, mVertexBuffer);
        //�����㷨�������ݴ�����Ⱦ����
		GLES30.glVertexAttribPointer(maNormalHandle, 3, GLES30.GL_FLOAT, false,
				3 * 4, mNormalBuffer);
		// ���ö���λ����������
		GLES30.glEnableVertexAttribArray(maPositionHandle); 
        GLES30.glEnableVertexAttribArray(maNormalHandle);// ���ö��㷨������������
		// ����������		
		GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
	}
}
