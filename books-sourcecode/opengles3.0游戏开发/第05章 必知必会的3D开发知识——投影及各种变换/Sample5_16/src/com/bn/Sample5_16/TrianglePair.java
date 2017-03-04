package com.bn.Sample5_16;
import java.nio.ByteBuffer;

import static com.bn.Sample5_16.Constant.*;

import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES30;

//�����ζ�
public class TrianglePair {
	int mProgram;// �Զ�����Ⱦ������ɫ������id
	int muMVPMatrixHandle;// �ܱ任��������
	int maPositionHandle; // ����λ����������
	int maColorHandle; // ������ɫ��������
	String mVertexShader;// ������ɫ��
	String mFragmentShader;// ƬԪ��ɫ��

	FloatBuffer mVertexBuffer;// �����������ݻ���
	FloatBuffer mColorBuffer;// ������ɫ���ݻ���
	int vCount = 0;

	public TrianglePair(MySurfaceView mv) {
		// ��ʼ��������������ɫ����
		initVertexData();
		// ��ʼ��shader
		initShader(mv);
	}

	// ��ʼ��������������ɫ���ݵķ���
	public void initVertexData() {
		// �����������ݵĳ�ʼ��================begin============================
		vCount = 6;//��������
		float vertices[] = new float[] {
				-8 * UNIT_SIZE, 10 * UNIT_SIZE, 0,//���������εĵ�1������
				-2 * UNIT_SIZE, 2 * UNIT_SIZE, 0, //���������εĵ�2������
				-8 * UNIT_SIZE, 2 * UNIT_SIZE, 0,//���������εĵ�3������

				8 * UNIT_SIZE, 2 * UNIT_SIZE, 0, //���������εĵ�1������
				8 * UNIT_SIZE, 10 * UNIT_SIZE, 0, //���������εĵ�2������
				2 * UNIT_SIZE, 10 * UNIT_SIZE, 0 //���������εĵ�2������
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

		// ������ɫ���ݵĳ�ʼ��================begin============================
		float colors[] = new float[]// ������ɫֵ���飬ÿ������4��ɫ��ֵRGBA
		{ 
				1, 1, 1, 0, 
				0, 0, 1, 0, 
				0, 0, 1, 0, 
				1, 1, 1, 0, 
				0, 1, 0, 0, 
				0, 1, 0, 0 
		};
		// ����������ɫ���ݻ���
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());// �����ֽ�˳��
		mColorBuffer = cbb.asFloatBuffer();// ת��ΪFloat�ͻ���
		mColorBuffer.put(colors);// �򻺳����з��붥����ɫ����
		mColorBuffer.position(0);// ���û�������ʼλ��
		// �ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
		// ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
		// ������ɫ���ݵĳ�ʼ��================end============================

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
		// ��ȡ�����ж�����ɫ��������
		maColorHandle = GLES30.glGetAttribLocation(mProgram, "aColor");
		// ��ȡ�������ܱ任��������
		muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
	}

	public void drawSelf() {
		// ָ��ʹ��ĳ����ɫ������
		GLES30.glUseProgram(mProgram);
		// �����ձ任��������Ⱦ����
		GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false,
				MatrixState.getFinalMatrix(), 0);
		// ������λ������������Ⱦ����
		GLES30.glVertexAttribPointer(maPositionHandle, 3, GLES30.GL_FLOAT,
				false, 3 * 4, mVertexBuffer);
		// ��������ɫ����������Ⱦ����
		GLES30.glVertexAttribPointer(maColorHandle, 4, GLES30.GL_FLOAT, false,
				4 * 4, mColorBuffer);
		// ���ö���λ����������
		GLES30.glEnableVertexAttribArray(maPositionHandle);
		// ���ö�����ɫ��������
		GLES30.glEnableVertexAttribArray(maColorHandle);
		
		GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
	}
}
