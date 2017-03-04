package com.bn.Sample11_4a;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES30;
import static com.bn.Sample11_4a.Constant.*;

public class Mountion
{
	//��λ����
	float UNIT_SIZE=3.0f;
	
	//�Զ�����Ⱦ���ߵ�id
	int mProgram;
	//�ܱ仯�������õ�id
	int muMVPMatrixHandle;
	//����λ����������id
	int maPositionHandle;
	//��������������������id
	int maTexCoorHandle;
	
	//�Ҷ�ͼ��������������������id
	int maTexCoorHandle1;
		
	//�ݵص�id
	int sTextureGrassHandle;
	//ʯͷ��id
	int sTextureRockHandle;
	
	//�Ҷ�ͼ��id
	int sTextureLandHandle;/////////////////////////////////////
		
	//��ʼxֵ
	int landStartYYHandle;
	//����
	int landYSpanHandle;
	//½�صĸ߶ȵ���ֵ
	int landHighAdjustHandle;
	//½�����߲�  
	int landHighestHandle;
		
	//�������ݻ���������������ݻ���
	FloatBuffer mVertexBuffer;
	FloatBuffer mTexCoorBuffer; 
	
	FloatBuffer mTexCoorBuffer1;//////////////////////////////////////
	//��������
	int vCount=0;
	
	public Mountion(MySurfaceView mv,int rows,int cols)
	{
		initVertexData(rows,cols);
		initShader(mv);
	}
	//��ʼ����������
    public void initVertexData(int rows,int cols)
    {
    	//�����������ݵĳ�ʼ��
    	vCount=cols*rows*2*3;//ÿ���������������Σ�ÿ��������3������   
        float vertices[]=new float[vCount*3];//ÿ������xyz��������
        int count=0;//���������
        for(int j=0;j<rows;j++)
        {
        	for(int i=0;i<cols;i++) 
        	{        		
        		//���㵱ǰ�������ϲ������ 
        		float zsx=-UNIT_SIZE*cols/2+i*UNIT_SIZE;
        		float zsz=-UNIT_SIZE*rows/2+j*UNIT_SIZE;
        		
        		vertices[count++]=zsx;
        		vertices[count++]=0;
        		vertices[count++]=zsz;
        		
        		vertices[count++]=zsx;
        		vertices[count++]=0;
        		vertices[count++]=zsz+UNIT_SIZE;
        		
        		vertices[count++]=zsx+UNIT_SIZE;
        		vertices[count++]=0;
        		vertices[count++]=zsz;
        		
        		vertices[count++]=zsx+UNIT_SIZE;
        		vertices[count++]=0;
        		vertices[count++]=zsz;
        		
        		vertices[count++]=zsx;
        		vertices[count++]=0;
        		vertices[count++]=zsz+UNIT_SIZE;
        		
        		vertices[count++]=zsx+UNIT_SIZE;
        		vertices[count++]=0;
        		vertices[count++]=zsz+UNIT_SIZE;
        	}
        }
		
        //���������������ݻ���
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��

        //���������������ݵĳ�ʼ��
        float[] texCoor=generateTexCoor(cols,rows,16.0f);
        //�������������������ݻ���
        ByteBuffer cbb = ByteBuffer.allocateDirect(texCoor.length*4);
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTexCoorBuffer = cbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTexCoorBuffer.put(texCoor);//�򻺳����з��붥����ɫ����
        mTexCoorBuffer.position(0);//���û�������ʼλ��
        /////////////////////////////////////////////////
        //�Ҷ�ͼ���������������ݵĳ�ʼ��
        float[] texCoor_land=generateTexCoor(cols,rows,1.0f);
        //�������������������ݻ���
        ByteBuffer cbb1 = ByteBuffer.allocateDirect(texCoor_land.length*4);
        cbb1.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTexCoorBuffer1 = cbb1.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTexCoorBuffer1.put(texCoor_land);//�򻺳����з��붥����ɫ����
        mTexCoorBuffer1.position(0);//���û�������ʼλ��
    }
	
	//��ʼ����ɫ���ķ���
	public void initShader(MySurfaceView mv) 
	{
		String mVertexShader=ShaderUtil.loadFromAssetsFile("vertex.sh", mv.getResources());
		String mFragmentShader=ShaderUtil.loadFromAssetsFile("frag.sh", mv.getResources());
		//���ڶ�����ɫ����ƬԪ��ɫ����������
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ����������id  
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж�������������������id  
        maTexCoorHandle= GLES30.glGetAttribLocation(mProgram, "aTexCoor");
        
        //��ȡ�����лҶ�ͼ��������������������id  
        maTexCoorHandle1= GLES30.glGetAttribLocation(mProgram, "aTexLandCoor");////////////////////////
        
        //��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");  
        
        //����
		//�ݵ�
		sTextureGrassHandle=GLES30.glGetUniformLocation(mProgram, "sTextureGrass");
		//ʯͷ
		sTextureRockHandle=GLES30.glGetUniformLocation(mProgram, "sTextureRock");		
		//�Ҷ�ͼ
		sTextureLandHandle=GLES30.glGetUniformLocation(mProgram, "sTextureLand");/////////////////////////////
		
		//xλ��
		landStartYYHandle=GLES30.glGetUniformLocation(mProgram, "landStartY");
		//x���
		landYSpanHandle=GLES30.glGetUniformLocation(mProgram, "landYSpan");
		//½�صĸ߶ȵ���ֵ
		landHighAdjustHandle=GLES30.glGetUniformLocation(mProgram, "landHighAdjust");
		//½�����߲�  
		landHighestHandle=GLES30.glGetUniformLocation(mProgram, "landHighest");
	}
	
	public void drawSelf(int texId,int rock_textId,int land_textId)
	{
		//ָ��ʹ��ĳ����ɫ������
   	 	GLES30.glUseProgram(mProgram); 
   	 	//�����ձ任����������Ⱦ����
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
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
		//��������������������Ⱦ����
		GLES30.glVertexAttribPointer
		(
			maTexCoorHandle, 
			2, 
			GLES30.GL_FLOAT, 
			false, 
			2*4, 
			mTexCoorBuffer
		);
		////////////////////////////////////////////////////////
		//���Ҷ�ͼ����������������������Ⱦ����
		GLES30.glVertexAttribPointer
		(
			maTexCoorHandle1, 
			2, 
			GLES30.GL_FLOAT, 
			false, 
			2*4, 
			mTexCoorBuffer1
		);
		/////////////////////////////////////////////////////////////
		//���ö���λ����������
        GLES30.glEnableVertexAttribArray(maPositionHandle);  
        //��������������������
        GLES30.glEnableVertexAttribArray(maTexCoorHandle);  
        
        GLES30.glEnableVertexAttribArray(maTexCoorHandle1);////////////////////////
        
        //������
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texId);
        GLES30.glActiveTexture(GLES30.GL_TEXTURE1);
		GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, rock_textId);
		
		GLES30.glActiveTexture(GLES30.GL_TEXTURE2);///////////////
		GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, land_textId);////////////
        
        GLES30.glUniform1i(sTextureGrassHandle, 0);//ʹ��0������
        GLES30.glUniform1i(sTextureRockHandle, 1); //ʹ��1������
        
        GLES30.glUniform1i(sTextureLandHandle, 2); //ʹ��2������///////////////////
        
        //������Ӧ��x����
        GLES30.glUniform1f(landStartYYHandle, 0);
        GLES30.glUniform1f(landYSpanHandle, 50);
        GLES30.glUniform1f(landHighAdjustHandle, LAND_HIGH_ADJUST);
        GLES30.glUniform1f(landHighestHandle, LAND_HIGHEST);
        
        //���Ƶ���
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount); 
	}
	//�Զ��з����������������ķ���
    public float[] generateTexCoor(int bw,int bh,float size)
    {
    	float[] result=new float[bw*bh*6*2]; 
    	float sizew=size/bw;//����
    	float sizeh=size/bh;//����
    	int c=0;
    	for(int i=0;i<bh;i++)
    	{
    		for(int j=0;j<bw;j++)
    		{
    			//ÿ����һ�����Σ������������ι��ɣ��������㣬12����������
    			float s=j*sizew;
    			float t=i*sizeh;
    			
    			result[c++]=s;
    			result[c++]=t;
    			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t+sizeh;    			
    		}
    	}
    	return result;
    }

}