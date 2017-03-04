package com.bn.Sample5_16;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

class MySurfaceView extends GLSurfaceView 
{
    private SceneRenderer mRenderer;//������Ⱦ��	  
	public MySurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3); //����ʹ��OPENGL ES3.0
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
    }
	
	boolean cullFaceFlag=false;//�Ƿ���������õı�־λ
	//�����Ƿ���������õı�־λ
	public void setCullFace(boolean flag)
	{
		cullFaceFlag=flag;
	}
	
	boolean cwCcwFlag=false;//�Ƿ���Զ�����Ƶı�־λ
	//�����Ƿ���Զ�����Ƶı�־λ
	public void setCwOrCcw(boolean flag)
	{
		cwCcwFlag=flag;
	}
	private class SceneRenderer implements GLSurfaceView.Renderer 
    {		 
		TrianglePair tp;//�����ζԶ�������
		public void onDrawFrame(GL10 gl) 
		{
			if(cullFaceFlag)
			{									//�ж��Ƿ�Ҫ�򿪱������
				GLES30.glEnable(GLES30.GL_CULL_FACE);			//�򿪱������
			}
			else
			{
				GLES30.glDisable(GLES30.GL_CULL_FACE);			//�رձ������
			}
			if(cwCcwFlag) 
			{									//�ж��Ƿ���Ҫ��˳ʱ�����
				GLES30.glFrontFace(GLES30.GL_CCW);			//ʹ����ʱ�����
			}
			else
			{
				GLES30.glFrontFace(GLES30.GL_CW);				//ʹ��˳ʱ�����
			}
			GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT |GLES30.GL_COLOR_BUFFER_BIT);//�����Ȼ�������ɫ����
			MatrixState.pushMatrix();            					//�����ֳ�
		 	MatrixState.translate(0, -1.4f, 0);						//��y�Ḻ����ƽ��
			tp.drawSelf();										//���������ζ�
			MatrixState.popMatrix();        						//�ָ��ֳ�
		}  

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����ӿڵĴ�С��λ�� 
        	GLES30.glViewport(0, 0, width, height); 
        	//�����ӿڵĿ�߱�
            Constant.ratio = (float) width / height;
			// ���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-Constant.ratio, Constant.ratio, -1, 1, 10, 100);
			// ���ô˷����������������
			MatrixState.setCamera(0, 0f, 20, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
            
            //��ʼ���任����
            MatrixState.setInitStack();
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //������Ļ����ɫRGBA
            GLES30.glClearColor(0.5f,0.5f,0.5f, 1.0f);  
            //���������ζԶ���
            tp=new TrianglePair(MySurfaceView.this);
            //����ȼ��
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        }
    }
}
