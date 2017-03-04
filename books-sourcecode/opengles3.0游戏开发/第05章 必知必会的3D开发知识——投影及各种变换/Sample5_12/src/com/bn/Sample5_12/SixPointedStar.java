package com.bn.Sample5_12;
import static com.bn.Sample5_12.ShaderUtil.createProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import android.opengl.GLES30;
import android.opengl.Matrix;

//������
public class SixPointedStar 
{	
	int mProgram;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������
    int maPositionHandle; //����λ����������  
    int maColorHandle; //������ɫ��������  
    String mVertexShader;	//������ɫ������ű� 
    String mFragmentShader;	//ƬԪ��ɫ������ű�
    static float[] mMMatrix = new float[16];	//���������3D�任���󣬰�����ת��ƽ�ơ�����
	
	FloatBuffer   mVertexBuffer;//�����������ݻ���
    int vCount=0;    
    float yAngle=0;//��y����ת�ĽǶ�
    float xAngle=0;//��z����ת�ĽǶ�
    final float UNIT_SIZE=1;
    final float UNIT_COLOR=1;
    float color[]=new float[3];//����ǵ���ɫ
    
    public SixPointedStar(MySurfaceView mv,float r,float R,float z,float[] color)
    {    	
    	this.color=color;//����ǵ���ɫ
    	//���ó�ʼ���������ݵ�initVertexData����
    	initVertexData(R,r,z);
    	//���ó�ʼ����ɫ����intShader����     
    	initShader(mv);
    }
    
 // ��ʼ�������������ݵķ���
    public void initVertexData(float R,float r,float z)
    {
		List<Float> flist=new ArrayList<Float>();
		float tempAngle=360/6;//ƽ���Ƕ�ֵ
		for(float angle=0;angle<360;angle+=tempAngle)
		{
			//��һ��������
			//��һ�����ĵ�
			flist.add(0f);
			flist.add(0f);
			flist.add(z);
			//�ڶ�����
			flist.add((float) (R*UNIT_SIZE*Math.cos(Math.toRadians(angle))));
			flist.add((float) (R*UNIT_SIZE*Math.sin(Math.toRadians(angle))));
			flist.add(z);
			//��������
			flist.add((float) (r*UNIT_SIZE*Math.cos(Math.toRadians(angle+tempAngle/2))));
			flist.add((float) (r*UNIT_SIZE*Math.sin(Math.toRadians(angle+tempAngle/2))));
			flist.add(z);
			
			//�ڶ���������
			//��һ�����ĵ�
			flist.add(0f);
			flist.add(0f);
			flist.add(z);
			//�ڶ�����
			flist.add((float) (r*UNIT_SIZE*Math.cos(Math.toRadians(angle+tempAngle/2))));
			flist.add((float) (r*UNIT_SIZE*Math.sin(Math.toRadians(angle+tempAngle/2))));
			flist.add(z);
			//��������
			flist.add((float) (R*UNIT_SIZE*Math.cos(Math.toRadians(angle+tempAngle))));
			flist.add((float) (R*UNIT_SIZE*Math.sin(Math.toRadians(angle+tempAngle))));
			flist.add(z);
		}
		vCount=flist.size()/3;//�������
		float[] vertexArray=new float[flist.size()];//������������
		for(int i=0;i<vCount;i++)//ѭ������������������
		{
			vertexArray[i*3]=flist.get(i*3);//Ϊ�����������鸳ֵ-x
			vertexArray[i*3+1]=flist.get(i*3+1);//Ϊ�����������鸳ֵ-y
			vertexArray[i*3+2]=flist.get(i*3+2);//Ϊ�����������鸳ֵ-z
		}
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertexArray.length*4);
		vbb.order(ByteOrder.nativeOrder());	//�����ֽ�˳��Ϊ���ز���ϵͳ˳��
		mVertexBuffer=vbb.asFloatBuffer();
		mVertexBuffer.put(vertexArray);//�������������ݷŽ�����
		mVertexBuffer.position(0);//���û�����ʼλ��
    }

   // ��ʼ����ɫ��
    public void initShader(MySurfaceView mv)
    {
    	//���ض�����ɫ���Ľű�����
        mVertexShader=ShaderUtil.loadFromAssetsFile("vertex.sh", mv.getResources());
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag.sh", mv.getResources());  
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        mProgram = createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ����������id  
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж�����ɫ��������id  
        maColorHandle= GLES30.glGetAttribLocation(mProgram, "aColor");
        //��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");  
    }
    //���Ʒ���
    public void drawSelf()
    {        
    	// ָ��ʹ��ĳ����ɫ������
    	 GLES30.glUseProgram(mProgram);  
    	 //��ʼ���任����
         Matrix.setRotateM(mMMatrix,0,0,0,1,0);
         //������Z������λ��1
         Matrix.translateM(mMMatrix,0,0,0,1);
         //������y����ת
         Matrix.rotateM(mMMatrix,0,yAngle,0,1,0);
         //������z����ת
         Matrix.rotateM(mMMatrix,0,xAngle,1,0,0);  
         //�����ձ任��������Ⱦ����
         GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(mMMatrix), 0); 
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
       //��������ɫ����������Ⱦ����
         GLES30.glVertexAttrib4f(maColorHandle, color[0],color[1],color[2], 1.0f);
       //���ö���λ����������
         GLES30.glEnableVertexAttribArray(maPositionHandle);  
         //����������
         GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount); 
    }
}
