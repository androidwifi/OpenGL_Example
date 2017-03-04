package com.bn.Sample7_3;
import static com.bn.Sample7_3.ShaderUtil.createProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES30;
import android.opengl.Matrix;

//�������
public class TextureRect 
{	
	int mProgram;//�Զ�����Ⱦ���߳���id
    int muMVPMatrixHandle;//�ܱ任��������
    int maPositionHandle; //����λ����������
    int maTexCoorHandle; //��������������������
    String mVertexShader;//������ɫ������ű�
    String mFragmentShader;//ƬԪ��ɫ������ű�
    static float[] mMMatrix = new float[16];//���������3D�任���󣬰�����ת��ƽ�ơ�����
	
	FloatBuffer   mVertexBuffer;//�����������ݻ���
	FloatBuffer   mTexCoorBuffer;//���������������ݻ���
    int vCount=0;   
    float xAngle=0;//��x����ת�ĽǶ�
    float yAngle=0;//��y����ת�ĽǶ�
    float zAngle=0;//��z����ת�ĽǶ�
    
    float sRange;//s�������귶Χ
    float tRange;//t�������귶Χ
    
    public TextureRect(MySurfaceView mv,float sRange,float tRange)
    {    	
    	//����s�������귶Χ
    	this.sRange=sRange;
    	//����t�������귶Χ
    	this.tRange=tRange;
    	
    	//���ó�ʼ���������ݵ�initVertexData����
    	initVertexData();
    	//���ó�ʼ����ɫ����initShader����     
    	initShader(mv);
    }
    
    //��ʼ���������ݵ�initVertexData����
    public void initVertexData()
    {
    	//�����������ݵĳ�ʼ��================begin============================
        vCount=6;
        final float UNIT_SIZE=0.3f;
        float vertices[]=new float[]
        {
        	-4*UNIT_SIZE,4*UNIT_SIZE,0,
        	-4*UNIT_SIZE,-4*UNIT_SIZE,0,
        	4*UNIT_SIZE,-4*UNIT_SIZE,0,
        	
        	4*UNIT_SIZE,-4*UNIT_SIZE,0,
        	4*UNIT_SIZE,4*UNIT_SIZE,0,
        	-4*UNIT_SIZE,4*UNIT_SIZE,0
        };
		
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //�����������ݵĳ�ʼ��================end============================
        
        //���������������ݵĳ�ʼ��================begin============================
        float texCoor[]=new float[]//������ɫֵ���飬ÿ������4��ɫ��ֵRGBA
  	    {
  	      		0,0, 0,tRange, sRange,tRange,
  	      		sRange,tRange, sRange,0, 0,0        		
  	    };  
        //�������������������ݻ���
        ByteBuffer cbb = ByteBuffer.allocateDirect(texCoor.length*4);
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTexCoorBuffer = cbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTexCoorBuffer.put(texCoor);//�򻺳����з��붥����������
        mTexCoorBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //���������������ݵĳ�ʼ��================end============================

    }

    //�Զ���ĳ�ʼ����ɫ���ķ���
    public void initShader(MySurfaceView mv)
    {
    	//���ض�����ɫ���Ľű�����
        mVertexShader=ShaderUtil.loadFromAssetsFile("vertex.sh", mv.getResources());
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag.sh", mv.getResources());  
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        mProgram = createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ����������
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж�������������������
        maTexCoorHandle= GLES30.glGetAttribLocation(mProgram, "aTexCoor");
        //��ȡ�������ܱ任��������
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");  
    }
    
    public void drawSelf(int texId)
    {        
    	 //ָ��ʹ��ĳ��shader����
    	 GLES30.glUseProgram(mProgram);        
    	 //��ʼ���任����
         Matrix.setRotateM(mMMatrix,0,0,0,1,0);
         //������Z������λ��1
         Matrix.translateM(mMMatrix,0,0,0,1);
         //������y����ת
         Matrix.rotateM(mMMatrix,0,yAngle,0,1,0);
         //������z����ת
         Matrix.rotateM(mMMatrix,0,zAngle,0,0,1);  
         //������x����ת
         Matrix.rotateM(mMMatrix,0,xAngle,1,0,0);
         //�����ձ任��������Ⱦ����
         GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(mMMatrix), 0); 
         //������λ�����ݴ��ͽ���Ⱦ����
         GLES30.glVertexAttribPointer  
         (
         		maPositionHandle,   
         		3, 
         		GLES30.GL_FLOAT, 
         		false,
                3*4,   
                mVertexBuffer
         );       
         //�������������ݴ��ͽ���Ⱦ����
         GLES30.glVertexAttribPointer  
         (
        		maTexCoorHandle, 
         		2, 
         		GLES30.GL_FLOAT, 
         		false,
                2*4,   
                mTexCoorBuffer
         );   

         GLES30.glEnableVertexAttribArray(maPositionHandle);  //���ö���λ����������
         GLES30.glEnableVertexAttribArray(maTexCoorHandle);  //���ö�������������������
         
         //������
         GLES30.glActiveTexture(GLES30.GL_TEXTURE0);//����ʹ�õ�������
         GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texId);//��ָ��������id
         
         //����
         GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount); 
    }
}
