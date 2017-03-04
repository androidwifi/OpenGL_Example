package com.bn.Sample5_12;// ������
import android.opengl.Matrix; //���������
public class MatrixState {//�洢ϵͳ����״̬����
	private static float[] mProjMatrix = new float[16];//4x4���� ͶӰ��
    private static float[] mVMatrix = new float[16];//�����λ�ó���9��������
    private static float[] mMVPMatrix;//���յ��ܱ任����
	
	public static void setCamera(// ���������
			float cx, // �����λ��x
			float cy, // �����λ��y
			float cz, // �����λ��z
			float tx, // �����Ŀ���x
			float ty, // �����Ŀ���y
			float tz, // �����Ŀ���z
			float upx, // �����UP����X����
			float upy, // �����UP����Y����
			float upz // �����UP����Z����
	) {
		Matrix.setLookAtM(mVMatrix, 0, cx, cy, cz, tx, ty, tz, upx, upy, upz);
	}	
	public static void setProjectFrustum(// ����͸��ͶӰ����
			float left, // near���left
			float right, // near���right
			float bottom, // near���bottom
			float top, // near���top
			float near, // near�����ӵ�ľ���
			float far // far�����ӵ�ľ���
	) {
		Matrix.frustumM(mProjMatrix, 0, left, right, bottom, top, near, far);
	}	
	public static void setProjectOrtho(// ��������ͶӰ����
			float left, // near���left
			float right, // near���right
			float bottom, // near���bottom
			float top, // near���top
			float near, // near�����ӵ�ľ���
			float far // far�����ӵ�ľ���
	) {
		Matrix.orthoM(mProjMatrix, 0, left, right, bottom, top, near, far);
	}	
	public static float[] getFinalMatrix(float[] spec) {// ��ȡ����������ܱ任����
		mMVPMatrix = new float[16];
		Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, spec, 0);
		Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
		return mMVPMatrix;
	}
}
