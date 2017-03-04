package com.bn.Sample10_4;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES30;
public class TextureRect 
{
	int mProgram;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������  
    int muMMatrixHandle;//λ�á���ת�任����
    int maCameraHandle; //�����λ���������� 
    int maPositionHandle; //����λ���������� 
    int maNormalHandle; //���㷨������������  
    int maLightLocationHandle;//��Դλ����������  
    
    String mVertexShader;//������ɫ��    	 
    String mFragmentShader;//ƬԪ��ɫ��
	
    private FloatBuffer   mVertexBuffer;//�����������ݻ���
	FloatBuffer   mNormalBuffer;//���㷨�������ݻ���
    int vCount;//��������
    
    float width;
    float height;
    
	public TextureRect(MySurfaceView mv, 
			float width,float height	//���εĿ��
			)
	{

		this.width=width;
    	this.height=height;
    	
		initVertexData();
        initShader(mv);
        
	}
    //��ʼ���������ݵķ���
    public void initVertexData()
    {
    	//�����������ݵĳ�ʼ��================begin============================
        vCount=6;//ÿ���������������Σ�ÿ��������3������        
        float vertices[]=
        {
        		-width/2, 0,-height/2,
        		-width/2, 0,height/2,
        		width/2, 0,height/2,
        		
        		-width/2, 0,-height/2,
        		width/2, 0,height/2,
        		width/2, 0, -height/2,
        };
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        
        float[] normals = {
        		0,1,0,
        		0,1,0,
        		0,1,0,
        		0,1,0,
        		0,1,0,
        		0,1,0,
        };
        //���㷨�������ݵĳ�ʼ��================begin============================  
        ByteBuffer cbb = ByteBuffer.allocateDirect(normals.length*4);
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mNormalBuffer = cbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mNormalBuffer.put(normals);//�򻺳����з��붥�㷨��������
        mNormalBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //������ɫ���ݵĳ�ʼ��================end============================
    }
    public void initShader(MySurfaceView mv)
    {
    	//���ض�����ɫ���Ľű�����
        mVertexShader=ShaderUtil.loadFromAssetsFile("vertex_light.sh", mv.getResources());
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag_light.sh", mv.getResources());  
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ���������� 
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition"); 
        //��ȡ�����ж��㷨������������  
        maNormalHandle= GLES30.glGetAttribLocation(mProgram, "aNormal");
        //��ȡ�������ܱ任����id
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");  
        //��ȡ�����������λ������
        maCameraHandle=GLES30.glGetUniformLocation(mProgram, "uCamera"); 
        //��ȡ�����й�Դλ������
        maLightLocationHandle=GLES30.glGetUniformLocation(mProgram, "uLightLocation"); 
        //��ȡλ�á���ת�任��������
        muMMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMMatrix"); 
    }
	public void drawSelf()
	{
		 //ָ��ʹ��ĳ����ɫ������
   	 	GLES30.glUseProgram(mProgram);
        //�����ձ任��������Ⱦ����
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
        //��λ�á���ת�任��������Ⱦ����
        GLES30.glUniformMatrix4fv(muMMatrixHandle, 1, false, MatrixState.getMMatrix(), 0);   
        //�������λ�ô�����Ⱦ����  
        GLES30.glUniform3fv(maCameraHandle, 1, MatrixState.cameraFB);
        //����Դλ�ô�����Ⱦ���� 
        GLES30.glUniform3fv(maLightLocationHandle, 1, MatrixState.lightPositionFB);
        //�����㷨�������ݴ�����Ⱦ����
        GLES30.glVertexAttribPointer  
        (
        		maPositionHandle,   
        		3, 
        		GLES30.GL_FLOAT, 
        		false,
               3*4,   
               mVertexBuffer
        );       
        //�����㷨�������ݴ�����Ⱦ����
        GLES30.glVertexAttribPointer  
        (
       		maNormalHandle, 
        		3,   
        		GLES30.GL_FLOAT, 
        		false,
               3*4,   
               mNormalBuffer
        );   
        //���ö���λ�á���������������
        GLES30.glEnableVertexAttribArray(maPositionHandle);  
        GLES30.glEnableVertexAttribArray(maNormalHandle);  
        
        //���ƾ���
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount); 
	}
	
}
