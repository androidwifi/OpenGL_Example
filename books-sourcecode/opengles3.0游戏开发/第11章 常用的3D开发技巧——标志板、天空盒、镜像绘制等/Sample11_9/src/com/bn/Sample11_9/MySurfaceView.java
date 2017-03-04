package com.bn.Sample11_9;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

public class MySurfaceView extends GLSurfaceView
{
	SceneRenderer mRender;//��Ⱦ������
	public MySurfaceView(Context context)
	{
		super(context);
		this.setEGLContextClientVersion(3); //����ʹ��OPENGL ES3.0
        mRender = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRender);				//������Ⱦ��		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ 
	}
	
	private class SceneRenderer implements GLSurfaceView.Renderer 
    {
		TextRect tRect;
		int wlWidth=512;//��������ͼ�Ŀ��
		int wlHeight=512;//��������ͼ�ĸ߶�
		long timeStamp=System.currentTimeMillis();//��ȡ��ǰϵͳ��ʱ��
		int texId=-1;//��ǰ������id
		@Override
		public void onDrawFrame(GL10 gl)
		{
			long tts=System.currentTimeMillis();//��ȡ���ƴ�֡ʱ��ϵͳʱ��
        	if(tts-timeStamp>500)
        	{//�ж�ʱ����Ƿ����500ms���������500ms������ı�
        		timeStamp=tts;//��¼���µ�ʱ��
        		FontUtil.cIndex=(FontUtil.cIndex+1)%FontUtil.content.length;
            	FontUtil.updateRGB();//�������ɻ�����ɫ
        	}
        	
        	if(texId!=-1)
        	{//����ǰ������ڣ���ɾ��
        		GLES30.glDeleteTextures(1, new int[]{texId}, 0);
        	}
        	//���������ı�����ͼ
        	Bitmap bm=FontUtil.generateWLT(FontUtil.getContent(FontUtil.cIndex, FontUtil.content), wlWidth, wlHeight);
        	texId=initTexture(bm);//�����ɵ�����ͼ����
        	
			//�����Ȼ�������ɫ����
            GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            
            MatrixState.pushMatrix();
            MatrixState.translate(0, 0, -2);
            tRect.drawSelf(texId);
            MatrixState.popMatrix();
		}
		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height)
		{
			//�����Ӵ���С��λ�� 
        	GLES30.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            float ratio = (float) width / height;
            //���ô˷����������͸��ͶӰ����
            MatrixState.setProjectOrtho(-ratio, ratio, -1, 1, 1, 100);
            //���ô˷������������9����λ�þ���
            MatrixState.setCamera(0,0,1,0,0,0,0f,1.0f,0.0f);
		}
		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config)
		{
			//������Ļ����ɫRGBA
            GLES30.glClearColor(0,0,0,1.0f);
            //����ȼ��
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            //�򿪱������
            GLES30.glEnable(GLES30.GL_CULL_FACE);
            
            MatrixState.setInitStack();
            tRect=new TextRect(MySurfaceView.this);
		}
    }
	//���������id
	public int initTexture(Bitmap bitmap)
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
		GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId);
		GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER,GLES30.GL_NEAREST);
		GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_MAG_FILTER,GLES30.GL_LINEAR);
		GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S,GLES30.GL_REPEAT);
		GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T,GLES30.GL_REPEAT);
        
        //ʵ�ʼ�������
        GLUtils.texImage2D
        (
        		GLES30.GL_TEXTURE_2D,   //��������
        		0, 					  //����Ĳ�Σ�0��ʾ����ͼ��㣬�������Ϊֱ����ͼ
        		bitmap, 			  //����ͼ��
        		0					  //����߿�ߴ�
        );
        bitmap.recycle(); 		  //������سɹ����ͷ�ͼƬ
        return textureId;
	}
}