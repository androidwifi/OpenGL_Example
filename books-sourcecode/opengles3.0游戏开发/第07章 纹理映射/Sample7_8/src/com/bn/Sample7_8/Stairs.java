package com.bn.Sample7_8;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES30;

public class Stairs
{	
	int mProgram;//�Զ�����Ⱦ������ɫ������id  
    int muMVPMatrixHandle;//�ܱ任��������
    int muMMatrixHandle;//λ�á���ת�任����
    int maPositionHandle; //����λ����������  
    int maNormalHandle; //���㷨������������  
    int maLightLocationHandle;//��Դλ����������  
    int maCameraHandle; //�����λ���������� 
    String mVertexShader;//������ɫ������ű�    	 
    String mFragmentShader;//ƬԪ��ɫ������ű�    
	
	FloatBuffer   mVertexBuffer;//�����������ݻ���  
	FloatBuffer   mNormalBuffer;//���㷨�������ݻ���
    int vCount=0;  
    float xSize=0.2f;
	float y1Size=0.1f;
	float y2Size=0.2f;
	float y3Size=0.3f;
	float y4Size=0.4f;
	float z1Size=0.4f;
	float z2Size=0.3f;
	float z3Size=0.2f;
	float z4Size=0.1f;
    public Stairs(MySurfaceView mv)
    {    	
    	//��ʼ��������������ɫ����
    	initVertexData();
    	//��ʼ����ɫ��
    	initShader(mv);
    }
    
    //��ʼ��������������ɫ���ݵķ���
    public void initVertexData()
    {
    	//�����������ݵĳ�ʼ��================begin============================
    	float[] vertices=new float[]{
    			//���ĸ��������棩�����������
    			xSize,0,0,
    			xSize,0,z1Size,
    			-xSize,0,z1Size,
    			-xSize,0,z1Size,
    			-xSize,0,0,
    			xSize,0,0,
    			//���ĸ��������棩�����������
    			xSize,y1Size,0,
    			-xSize,y1Size,0,
    			-xSize,y1Size,z1Size,
    			-xSize,y1Size,z1Size,
    			xSize,y1Size,z1Size,
    			xSize,y1Size,0,
    			//���ĸ��������棩�������ǰ��
    			xSize,y1Size,z1Size,
    			-xSize,y1Size,z1Size,
    			-xSize,0,z1Size,
    			-xSize,0,z1Size,
    			xSize,0,z1Size,
    			xSize,y1Size,z1Size,
    			//���ĸ��������棩������ĺ���
    			xSize,y1Size,0,
    			xSize,0,0,
    			-xSize,0,0,
    			-xSize,0,0,
    			-xSize,y1Size,0,
    			xSize,y1Size,0,
    			//���ĸ��������棩�����������
    			-xSize,y1Size,z1Size,
    			-xSize,y1Size,0,
    			-xSize,0,0,
    			-xSize,0,0,
    			-xSize,0,z1Size,
    			-xSize,y1Size,z1Size,
    			//���ĸ��������棩�����������
    			xSize,y1Size,z1Size,
    			xSize,0,z1Size,
    			xSize,0,0,
    			xSize,0,0,
    			xSize,y1Size,0,
    			xSize,y1Size,z1Size,
    			
    			//�����������������
    			xSize,y2Size,0,
    			-xSize,y2Size,0,
    			-xSize,y2Size,z2Size,
    			-xSize,y2Size,z2Size,
    			xSize,y2Size,z2Size,
    			xSize,y2Size,0,
    			//�������������ǰ��
    			xSize,y2Size,z2Size,
    			-xSize,y2Size,z2Size,
    			-xSize,y1Size,z2Size,
    			-xSize,y1Size,z2Size,
    			xSize,y1Size,z2Size,
    			xSize,y2Size,z2Size,
    			//������������ĺ���
    			xSize,y2Size,0,
    			xSize,y1Size,0,
    			-xSize,y1Size,0,
    			-xSize,y1Size,0,
    			-xSize,y2Size,0,
    			xSize,y2Size,0,
    			//�����������������
    			-xSize,y2Size,z2Size,
    			-xSize,y2Size,0,
    			-xSize,y1Size,0,
    			-xSize,y1Size,0,
    			-xSize,y1Size,z2Size,
    			-xSize,y2Size,z2Size,
    			//�����������������
    			xSize,y2Size,z2Size,
    			xSize,y1Size,z2Size,
    			xSize,y1Size,0,
    			xSize,y1Size,0,
    			xSize,y2Size,0,
    			xSize,y2Size,z2Size,
    			
    			//�ڶ��������������
    			xSize,y3Size,0,
    			-xSize,y3Size,0,
    			-xSize,y3Size,z3Size,
    			-xSize,y3Size,z3Size,
    			xSize,y3Size,z3Size,
    			xSize,y3Size,0,
    			//�ڶ����������ǰ��
    			xSize,y3Size,z3Size,
    			-xSize,y3Size,z3Size,
    			-xSize,y2Size,z3Size,
    			-xSize,y2Size,z3Size,
    			xSize,y2Size,z3Size,
    			xSize,y3Size,z3Size,
    			//�ڶ���������ĺ���
    			xSize,y3Size,0,
    			xSize,y2Size,0,
    			-xSize,y2Size,0,
    			-xSize,y2Size,0,
    			-xSize,y3Size,0,
    			xSize,y3Size,0,
    			//�ڶ��������������
    			-xSize,y3Size,z3Size,
    			-xSize,y3Size,0,
    			-xSize,y2Size,0,
    			-xSize,y2Size,0,
    			-xSize,y2Size,z3Size,
    			-xSize,y3Size,z3Size,
    			//�ڶ��������������
    			xSize,y3Size,z3Size,
    			xSize,y2Size,z3Size,
    			xSize,y2Size,0,
    			xSize,y2Size,0,
    			xSize,y3Size,0,
    			xSize,y3Size,z3Size,
    			
    			//��һ�������������
    			xSize,y4Size,0,
    			-xSize,y4Size,0,
    			-xSize,y4Size,z4Size,
    			-xSize,y4Size,z4Size,
    			xSize,y4Size,z4Size,
    			xSize,y4Size,0,
    			//��һ���������ǰ��
    			xSize,y4Size,z4Size,
    			-xSize,y4Size,z4Size,
    			-xSize,y3Size,z4Size,
    			-xSize,y3Size,z4Size,
    			xSize,y3Size,z4Size,
    			xSize,y4Size,z4Size,
    			//��һ��������ĺ���
    			xSize,y4Size,0,
    			xSize,y3Size,0,
    			-xSize,y3Size,0,
    			-xSize,y3Size,0,
    			-xSize,y4Size,0,
    			xSize,y4Size,0,
    			//��һ�������������
    			-xSize,y4Size,z4Size,
    			-xSize,y4Size,0,
    			-xSize,y3Size,0,
    			-xSize,y3Size,0,
    			-xSize,y3Size,z4Size,
    			-xSize,y4Size,z4Size,
    			//��һ�������������
    			xSize,y4Size,z4Size,
    			xSize,y3Size,z4Size,
    			xSize,y3Size,0,
    			xSize,y3Size,0,
    			xSize,y4Size,0,
    			xSize,y4Size,z4Size,
    	};
    	vCount=vertices.length/3;
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
        
        float[] normals=
    		{
    		//���ĸ��������棩�����������
    		0,-1,0, 0,-1,0, 0,-1,0,
    		0,-1,0, 0,-1,0, 0,-1,0,
    		//���ĸ��������棩�����������
    		0,1,0, 0,1,0, 0,1,0,
    		0,1,0, 0,1,0, 0,1,0,
    		//���ĸ��������棩�������ǰ��
    		0,0,1, 0,0,1, 0,0,1,
    		0,0,1, 0,0,1, 0,0,1,
    		//���ĸ��������棩������ĺ���
    		0,0,-1, 0,0,-1, 0,0,-1,
    		0,0,-1, 0,0,-1, 0,0,-1,
    		//���ĸ��������棩�����������
    		-1,0,0, -1,0,0, -1,0,0,
    		-1,0,0, -1,0,0, -1,0,0,
    		//���ĸ��������棩�����������
    		1,0,0, 1,0,0, 1,0,0,
    		1,0,0, 1,0,0, 1,0,0,

    		//�����������������
    		0,1,0, 0,1,0, 0,1,0,
    		0,1,0, 0,1,0, 0,1,0,
    		//�������������ǰ��
    		0,0,1, 0,0,1, 0,0,1,
    		0,0,1, 0,0,1, 0,0,1,
    		//������������ĺ���
    		0,0,-1, 0,0,-1, 0,0,-1,
    		0,0,-1, 0,0,-1, 0,0,-1,
    		//�����������������
    		-1,0,0, -1,0,0, -1,0,0,
    		-1,0,0, -1,0,0, -1,0,0,
    		//�����������������
    		1,0,0, 1,0,0, 1,0,0,
    		1,0,0, 1,0,0, 1,0,0,

    		//�ڶ��������������
    		0,1,0, 0,1,0, 0,1,0,
    		0,1,0, 0,1,0, 0,1,0,
    		//�ڶ����������ǰ��
    		0,0,1, 0,0,1, 0,0,1,
    		0,0,1, 0,0,1, 0,0,1,
    		//�ڶ���������ĺ���
    		0,0,-1, 0,0,-1, 0,0,-1,
    		0,0,-1, 0,0,-1, 0,0,-1,
    		//�ڶ��������������
    		-1,0,0, -1,0,0, -1,0,0,
    		-1,0,0, -1,0,0, -1,0,0,
    		//�ڶ��������������
    		1,0,0, 1,0,0, 1,0,0,
    		1,0,0, 1,0,0, 1,0,0,
    		
    		//��һ�������������
    		0,1,0, 0,1,0, 0,1,0,
    		0,1,0, 0,1,0, 0,1,0,
    		//��һ���������ǰ��
    		0,0,1, 0,0,1, 0,0,1,
    		0,0,1, 0,0,1, 0,0,1,
    		//��һ��������ĺ���
    		0,0,-1, 0,0,-1, 0,0,-1,
    		0,0,-1, 0,0,-1, 0,0,-1,
    		//��һ�������������
    		-1,0,0, -1,0,0, -1,0,0,
    		-1,0,0, -1,0,0, -1,0,0,
    		//��һ�������������
    		1,0,0, 1,0,0, 1,0,0,
    		1,0,0, 1,0,0, 1,0,0,
    		};
        //���㷨�������ݵĳ�ʼ��================begin============================  
        ByteBuffer cbb = ByteBuffer.allocateDirect(normals.length*4);
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mNormalBuffer = cbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mNormalBuffer.put(normals);//�򻺳����з��붥�㷨��������
        mNormalBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //���㷨�������ݵĳ�ʼ��================end============================
    }

    //��ʼ����ɫ��
    public void initShader(MySurfaceView mv)
    {
    	//���ض�����ɫ���Ľű�����
        mVertexShader=ShaderUtil.loadFromAssetsFile("vertex.sh", mv.getResources());
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag.sh", mv.getResources());  
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ����������  
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж�����ɫ��������  
        maNormalHandle= GLES30.glGetAttribLocation(mProgram, "aNormal");
        //��ȡ�������ܱ任��������
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");  
        //��ȡλ�á���ת�任��������
        muMMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMMatrix"); 
        //��ȡ�����й�Դλ������
        maLightLocationHandle=GLES30.glGetUniformLocation(mProgram, "uLightLocation");
        //��ȡ�����������λ������
        maCameraHandle=GLES30.glGetUniformLocation(mProgram, "uCamera"); 
    }
    
    public void drawSelf(int texId)
    {        
    	 //ָ��ʹ��ĳ����ɫ������
    	 GLES30.glUseProgram(mProgram);
         //�����ձ任��������Ⱦ����
         GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
         //��λ�á���ת�任��������Ⱦ����
         GLES30.glUniformMatrix4fv(muMMatrixHandle, 1, false, MatrixState.getMMatrix(), 0);   
         //����Դλ�ô�����Ⱦ����
         GLES30.glUniform3fv(maLightLocationHandle, 1, MatrixState.lightPositionFB);
         //�������λ�ô�����Ⱦ����
         GLES30.glUniform3fv(maCameraHandle, 1, MatrixState.cameraFB);
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
         

         GLES30.glEnableVertexAttribArray(maPositionHandle);  //���ö���λ������
         GLES30.glEnableVertexAttribArray(maNormalHandle);  //���ö��㷨��������
         //������
         GLES30.glActiveTexture(GLES30.GL_TEXTURE0);//����ʹ�õ�������
         GLES30.glBindTexture(GLES30.GL_TEXTURE_3D, texId);//������
         //����¥��
         GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount); 
    }
}
