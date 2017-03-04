package com.bn.Sample5_14;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.opengl.GLES30;
import android.os.Build;

//��ɫ����
@SuppressLint("NewApi")
public class ColorRect
{	
	int mProgram;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������
    int muMMatrixHandle;//λ�á���ת�任��������
    int maPositionHandle; //����λ���������� 
    int maColorHandle; //������ɫ�������� 
    String mVertexShader;//������ɫ������ű�  	 
    String mFragmentShader;//ƬԪ��ɫ������ű�
	
	FloatBuffer   mVertexBuffer;//�����������ݻ���
	FloatBuffer   mColorBuffer;//������ɫ���ݻ���
    int vCount=0;
    float colors[];
    float vertices[];
    
    public ColorRect(MySurfaceView mv,float unitSize,float[] color)
    {  
    	//��ʼ��������������ɫ����
    	initVertexData(unitSize,color);
    	//��ʼ��shader        
    	initShader(mv);
    	
    }
    
    //��ʼ��������������ɫ���ݵķ���
    public void initVertexData(float unitSize,float[] colorIn)
    {
    	//�����������ݵĳ�ʼ��================begin============================
        vCount=6; //�������       
        float vertices[]=new float[]//������������
        {
        	0,0,0,
        	unitSize,unitSize,0,
        	-unitSize,unitSize,0,
        	-unitSize,-unitSize,0,
        	unitSize,-unitSize,0,
        	unitSize,unitSize,0
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
        
        //������ɫ���ݵĳ�ʼ��================begin============================
        colors=new float[]//������ɫֵ���飬ÿ������4��ɫ��ֵRGBA
        {
    		colorIn[0],colorIn[1],colorIn[2],colorIn[3],
    		colorIn[0],colorIn[1],colorIn[2],colorIn[3],
    		colorIn[0],colorIn[1],colorIn[2],colorIn[3],
    		colorIn[0],colorIn[1],colorIn[2],colorIn[3],
    		colorIn[0],colorIn[1],colorIn[2],colorIn[3],
    		colorIn[0],colorIn[1],colorIn[2],colorIn[3]
        };         
        
        //����������ɫ���ݻ���
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mColorBuffer = cbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mColorBuffer.put(colors);//�򻺳����з��붥����ɫ����
        mColorBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //������ɫ���ݵĳ�ʼ��================end============================

    }

    //��ʼ����ɫ��
    @SuppressLint("NewApi")
	public void initShader(MySurfaceView mv)
    {
    	//���ض�����ɫ���Ľű�����
        mVertexShader=ShaderUtil.loadFromAssetsFile("vertex.sh", mv.getResources());
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag.sh", mv.getResources());  
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ����������id  
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж�����ɫ��������id  
        maColorHandle= GLES30.glGetAttribLocation(mProgram, "aColor");
        //��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");  
        //��ȡλ�á���ת�任��������id
        muMMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMMatrix");  
    }
    
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	@SuppressLint("NewApi")
	public void drawSelf()
    {        
    	 //ָ��ʹ��ĳ����ɫ������
    	GLES30.glUseProgram(mProgram);
         //�����ձ任��������Ⱦ����
    	GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
    	 //��ƽ�ơ���ת�任��������Ⱦ����
    	GLES30.glUniformMatrix4fv(muMMatrixHandle, 1, false, MatrixState.getMMatrix(), 0);   
    	//������λ�����ݴ�����Ⱦ����
    	GLES30.glVertexAttribPointer  
         (
         		maPositionHandle,   
         		3, 
         		GLES30.GL_FLOAT, 
         		false,
                3*4,   
                mVertexBuffer
         );       
    	 //��������ɫ���ݴ�����Ⱦ����
         GLES30.glVertexAttribPointer  
         (
        		maColorHandle, 
         		4, 
         		GLES30.GL_FLOAT, 
         		false,
                4*4,   
                mColorBuffer
         );   
       //���ö���λ����������
         GLES30.glEnableVertexAttribArray(maPositionHandle);
       //���ö�����ɫ��������
         GLES30.glEnableVertexAttribArray(maColorHandle);  
         //������ɫ����
         GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 0, vCount); 
    }
}
