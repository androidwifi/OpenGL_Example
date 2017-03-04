package com.bn.Sample5_3;
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

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
    	Cube cube;//�������������
    	
        public void onDrawFrame(GL10 gl) 
        { 
        	//�����Ȼ�������ɫ����
            GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);

            //����ԭ������
            MatrixState.pushMatrix();//�����ֳ�
            cube.drawSelf();//����������    
            MatrixState.popMatrix();//�ָ��ֳ�
            
            //���Ʊ任���������
            MatrixState.pushMatrix();//�����ֳ�
            MatrixState.translate(3.5f, 0, 0);//��x����ƽ��3.5
            cube.drawSelf();//����������   
            MatrixState.popMatrix();//�ָ��ֳ�
        }  

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����ӿڴ�С��λ�� 
        	GLES30.glViewport(0, 0, width, height); 
        	//�����ӿڵĿ�߱�
            Constant.ratio = (float) width / height;
			// ���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-Constant.ratio*0.8f, Constant.ratio*1.2f, -1, 1, 20, 100);
			// ���ô˷����������������
			MatrixState.setCamera(-16f, 8f, 45, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
            
            //��ʼ���任����
            MatrixState.setInitStack();
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //������Ļ����ɫRGBA
            GLES30.glClearColor(0.5f,0.5f,0.5f, 1.0f);  
            //�������������
            cube=new Cube(MySurfaceView.this);
            //����ȼ��
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            //�򿪱������   
            GLES30.glEnable(GLES30.GL_CULL_FACE);  
        }
    }
}
