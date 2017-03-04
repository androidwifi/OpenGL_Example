package com.bn.Sample10_1b;
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
    int maTexCoorHandle; //�������������������� 
    int maSunLightLocationHandle;//��Դλ���������� 
    
    String mVertexShader;//������ɫ��    	 
    String mFragmentShader;//ƬԪ��ɫ��
	
    private FloatBuffer   mVertexBuffer;//�����������ݻ���
    private FloatBuffer   mTextureBuffer;//������ɫ���ݻ���
    int vCount;//��������
    int texId;//����Id
    
    float width;
    float height;
    
	public TextureRect(MySurfaceView mv, 
			float width,float height	//������εĿ��
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
        		-width/2, height/2,0,
        		-width/2, -height/2,0,
        		width/2, height/2,0,
        		
        		-width/2, -height/2,0,
        		width/2, -height/2,0,
        		width/2, height/2,0
        };
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        float textures[]=
        {
        		0f,0f, 0f,1, 1,0f,
        		0f,1,  1,1,  1,0f
        };
        //���������������ݻ���
        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);
        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTextureBuffer= tbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTextureBuffer.put(textures);//�򻺳����з��붥����ɫ����
        mTextureBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //�����������ݵĳ�ʼ��================end============================  
    }
    public void initShader(MySurfaceView mv)
    {
    	//���ض�����ɫ���Ľű�����
        mVertexShader=ShaderUtil.loadFromAssetsFile("shader/vertex_tex.sh", mv.getResources());
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("shader/frag_tex.sh", mv.getResources());  
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ���������� 
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж������������������� 
        maTexCoorHandle= GLES30.glGetAttribLocation(mProgram, "aTexCoor");
        //��ȡ�������ܱ任����id
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");  
    }
	public void drawSelf(int texId)
	{
		 //ָ��ʹ��ĳ����ɫ������
   	 	GLES30.glUseProgram(mProgram);
        //�����ձ任��������Ⱦ����
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
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
        //���������ݴ�����Ⱦ����
        GLES30.glVertexAttribPointer  
        (
       		maTexCoorHandle, 
        		2, 
        		GLES30.GL_FLOAT, 
        		false,
               2*4,   
               mTextureBuffer
        );   
        //���ö���λ����������
        GLES30.glEnableVertexAttribArray(maPositionHandle);  
        //���ö�������������������
        GLES30.glEnableVertexAttribArray(maTexCoorHandle);  
        
        //������
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texId);
        
        //�����������
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount); 
	}
}
