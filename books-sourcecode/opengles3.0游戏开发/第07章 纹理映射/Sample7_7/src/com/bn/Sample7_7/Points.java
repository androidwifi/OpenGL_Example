package com.bn.Sample7_7;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES30;

public class Points {
	int mProgram;// �Զ�����Ⱦ������ɫ������id
	int muMVPMatrixHandle;// �ܱ任��������
	int maPositionHandle; // ����λ����������
	String mVertexShader;// ������ɫ������ű�
	String mFragmentShader;// ƬԪ��ɫ������ű�

	FloatBuffer mVertexBuffer;// �����������ݻ���
	int vCount = 0;

	public Points(MySurfaceView mv) 
	{
		//���ó�ʼ���������ݵķ���
		initVertexData();
		//���ó�ʼ����ɫ���ķ���
		initShader(mv);
	}

	// ��ʼ���������ݵķ���
	public void initVertexData() 
	{
		// �����������ݵĳ�ʼ��================begin============================
		vCount = 9;

		float vertices[] = new float[] {
				0, 0, 0, 
				0, Constant.UNIT_SIZE*2, 0, 
				Constant.UNIT_SIZE, Constant.UNIT_SIZE/2, 0,
				-Constant.UNIT_SIZE/3, Constant.UNIT_SIZE, 0,
				-Constant.UNIT_SIZE*0.4f, -Constant.UNIT_SIZE*0.4f, 0,
				-Constant.UNIT_SIZE, -Constant.UNIT_SIZE, 0,
				Constant.UNIT_SIZE*0.2f, -Constant.UNIT_SIZE*0.7f, 0, 
				Constant.UNIT_SIZE/2, -Constant.UNIT_SIZE*3/2, 0, 
				-Constant.UNIT_SIZE*4/5, -Constant.UNIT_SIZE*3/2, 0, 
		};

		// ���������������ݻ���
		// vertices.length*4����Ϊһ�������ĸ��ֽ�
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());// �����ֽ�˳��
		mVertexBuffer = vbb.asFloatBuffer();// ת��ΪFloat�ͻ���
		mVertexBuffer.put(vertices);// �򻺳����з��붥����������
		mVertexBuffer.position(0);// ���û�������ʼλ��
		// �ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
		// ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
		// �����������ݵĳ�ʼ��================end============================
	}

	// ��ʼ����ɫ��
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
	}
	public void drawSelf(int texId) 
	{
		//ָ��ʹ��ĳ����ɫ������
		GLES30.glUseProgram(mProgram);
		// �����ձ任��������Ⱦ����
		GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false,
				MatrixState.getFinalMatrix(), 0);
		// ������λ�����ݴ�����Ⱦ����
		GLES30.glVertexAttribPointer(maPositionHandle, 3, GLES30.GL_FLOAT,
				false, 3 * 4, mVertexBuffer);
		// ���ö���λ����������
		GLES30.glEnableVertexAttribArray(maPositionHandle);
		
		//�������� 
		GLES30.glEnable(GLES30.GL_TEXTURE_2D);  
		
		
		GLES30.glActiveTexture(GLES30.GL_TEXTURE0);	//��������
		GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texId);//������
		//ִ�л���
		GLES30.glDrawArrays(GLES30.GL_POINTS, 0, vCount);
	}
}
