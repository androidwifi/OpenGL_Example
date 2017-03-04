package com.bn.Sample5_4;
import java.nio.ByteBuffer;
import android.opengl.Matrix;

//�洢ϵͳ����״̬����
public class MatrixState 
{  
	private static float[] mProjMatrix = new float[16];//4x4���� ͶӰ��
    private static float[] mVMatrix = new float[16];//�����λ�ó���9��������   
    private static float[] currMatrix;//��ǰ�任����
      
    static float[][] mStack=new float[10][16];//���ڱ���任�����ջ
    static int stackTop=-1;//ջ������
    
  //�������κα任�ĳ�ʼ����
    public static void setInitStack()
    {
    	currMatrix=new float[16];
    	Matrix.setRotateM(currMatrix, 0, 0, 1, 0, 0);
    }
    
  //����ǰ�任�������ջ��
    public static void pushMatrix()
    {
    	stackTop++;//ջ��������1
    	for(int i=0;i<16;i++)
    	{
    		mStack[stackTop][i]=currMatrix[i];//��ǰ�任�����еĸ�Ԫ����ջ
    	}
    }
    //��ջ��ȡ���任����
    public static void popMatrix()
    {
    	for(int i=0;i<16;i++)
    	{
    		currMatrix[i]=mStack[stackTop][i];//ջ������Ԫ�ؽ���ǰ�任����
    	}
    	stackTop--;//ջ��������1
    }
    
  //��X��Y��Z�᷽�����ƽ�Ʊ任�ķ���
    public static void translate(float x,float y,float z)
    {
    	Matrix.translateM(currMatrix, 0, x, y, z);
    }
  //��X��Y��Z�᷽�������ת�任�ķ���
	public static void rotate(float angle, float x, float y, float z) {// ������xyz���ƶ�
		Matrix.rotateM(currMatrix, 0, angle, x, y, z);
	}
    
    //���������
    static ByteBuffer llbb= ByteBuffer.allocateDirect(3*4);
    static float[] cameraLocation=new float[3];//�����λ��   
	//����������ķ���
    public static void setCamera
    (
    		float cx,	
    		float cy,   
    		float cz,   
    		float tx,   
    		float ty,   
    		float tz,   
    		float upx,  
    		float upy, 
    		float upz   		
    )
    {
    	Matrix.setLookAtM
        (
        		mVMatrix, 	//�洢���ɾ���Ԫ�ص�float[]��������
        		0, 			//�����ʼƫ����
        		cx,cy,cz,	//�����λ�õ�X��Y��Z����
        		tx,ty,tz,	//�۲�Ŀ���X��Y��Z����
        		upx,upy,upz	//up������X��Y��Z���ϵķ���
        );
    }
    
    //����͸��ͶӰ����
    public static void setProjectFrustum
    ( 
    	float left,		//near���left
    	float right,    //near���right
    	float bottom,   //near���bottom
    	float top,      //near���top
    	float near,		//near�����ӵ�ľ���
    	float far       //far�����ӵ�ľ���
    )
    {
    	Matrix.frustumM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }
    
    //��������ͶӰ����
    public static void setProjectOrtho
    (
    	float left,		//near���left
    	float right,    //near���right
    	float bottom,   //near���bottom
    	float top,      //near���top
    	float near,		//near�����ӵ�ľ���
    	float far       //far�����ӵ�ľ���
    )
    {    	
    	Matrix.orthoM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }
    
    //��ȡ����������ܱ任����
    static float[] mMVPMatrix=new float[16];//�ܱ任����
    public static float[] getFinalMatrix()//��������ܱ任����ķ���
    {	
    	//�����������Ա任����
    	Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, currMatrix, 0);
    	//ͶӰ���������һ���Ľ������
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);        
        return mMVPMatrix;
    }
    
    //��ȡ��������ı任����
    public static float[] getMMatrix()
    {       
        return currMatrix;
    }
}
