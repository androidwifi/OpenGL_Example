package com.bn.Sample7_9;
import java.nio.ByteBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.bn.Sample7_9.R;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

class MySurfaceView extends GLSurfaceView 
{
    private SceneRenderer mRenderer;//������Ⱦ��
    
    //����ͼ��Դid����
    int[] texResIdArray=
    {
    	R.drawable.tex1,R.drawable.tex2,R.drawable.tex3,R.drawable.tex4,
    	R.drawable.tex5,R.drawable.tex6,R.drawable.tex7,R.drawable.tex8,
        R.drawable.tex9,R.drawable.tex10,R.drawable.tex11,R.drawable.tex12,
    	R.drawable.tex13,R.drawable.tex14,R.drawable.tex15,R.drawable.tex16
    };
    
	public MySurfaceView(Context context) 
	{
        super(context);
        this.setEGLContextClientVersion(3); //����ʹ��OPENGL ES3.0
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ 
    }

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
    	Points point;//�����
    	int texId;//����id
    	
        public void onDrawFrame(GL10 gl) 
        { 
        	//�����Ȼ�������ɫ����
            GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            //�����ֳ�
            MatrixState.pushMatrix(); 
            //���Ƶ�
            MatrixState.pushMatrix();
            point.drawSelf(texId);    
            MatrixState.popMatrix();            
            //�ָ��ֳ�
            MatrixState.popMatrix();
        }  

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����Ӵ���С��λ�� 
        	GLES30.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            Constant.ratio = (float) width / height;
			// ���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-Constant.ratio, Constant.ratio, -1, 1, 20, 100);
			// ���ô˷������������9����λ�þ���
			MatrixState.setCamera(0, 8f, 30, 0f, 0f, 0f, 0f, 1.0f, 0.0f);            
            //��ʼ���任����
            MatrixState.setInitStack(); 
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) 
        {
            //������Ļ����ɫRGBA
            GLES30.glClearColor(0,0,0, 1.0f);  
            //���������
            point=new Points(MySurfaceView.this);
            //����ȼ��
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            //�򿪱������   
            GLES30.glEnable(GLES30.GL_CULL_FACE);  
            //��������
            texId=initTextureArray(texResIdArray,128,128);
        }
    }
	
	//��������ķ���
	public int initTextureArray(int[] picId,int width,int height)
	{
		//��������ID
		int[] textures = new int[1];
		GLES30.glGenTextures
		(
				1,          //����������id������
				textures,   //����id������
				0           //ƫ����
		);    
		int textureId=textures[0];    
		//��2D��������
		GLES30.glBindTexture(GLES30.GL_TEXTURE_2D_ARRAY, textureId);
		//���ò�������		
		GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D_ARRAY, GLES30.GL_TEXTURE_WRAP_S,GLES30.GL_CLAMP_TO_EDGE);//����S�����췽ʽ
		GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D_ARRAY, GLES30.GL_TEXTURE_WRAP_T,GLES30.GL_CLAMP_TO_EDGE);//����T�����췽ʽ
		GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D_ARRAY, GLES30.GL_TEXTURE_WRAP_R,GLES30.GL_CLAMP_TO_EDGE);//����R�����췽ʽ
		GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D_ARRAY, GLES30.GL_TEXTURE_MIN_FILTER,GLES30.GL_NEAREST);//����MIN��������
		GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D_ARRAY,GLES30.GL_TEXTURE_MAG_FILTER,GLES30.GL_LINEAR);	//����MAG��������
		//��һ��ͼƬ���ݼ��ص��ڴ滺��
		ByteBuffer texels=ConvertUtil.convertPicsToBuffer
		(
			this.getResources(),
			picId,
			width,
			height
		);		
		//���û�������ʼλ��
		texels.position(0);
		//����2D��������
		GLES30.glTexImage3D
		(
				GLES30.GL_TEXTURE_2D_ARRAY,  //��������
				0, //����Ĳ��
				GLES30.GL_RGBA8, //������ɫ����
				width, //���
				height, //�߶�
				picId.length,//���鳤�� 
				0, //����߿�ߴ�
				GLES30.GL_RGBA, //�������ݵĸ�ʽ
				GLES30.GL_UNSIGNED_BYTE, //�������ݵ���������
	            texels//������
	    );
        
		
        return textureId;//��������id
	}
}
