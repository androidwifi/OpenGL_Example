package com.bn.Sample5_10;
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
    	Circle circle;//Բ��������
    	
    	public void onDrawFrame(GL10 gl) 
    	{
    		GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT	//�����Ȼ�������ɫ����
    				| GLES30.GL_COLOR_BUFFER_BIT);
    		MatrixState.pushMatrix();							//�����ֳ�
    		//����Բ
    		MatrixState.pushMatrix();							//�����ֳ�
    		MatrixState.translate(-1.2f, 0, 0);					//��x������ƽ��
    		circle.drawSelf(0,24);							//����Բ
    		MatrixState.popMatrix();							//�ָ��ֳ�
    		//���ư��Բ
    		MatrixState.pushMatrix();							//�����ֳ�
    		MatrixState.translate(1.2f, 0, 0);					//��x������ƽ��
    		circle.drawSelf(6,12);							//���ư��Բ
    		MatrixState.popMatrix();							//�ָ��ֳ�
    		MatrixState.popMatrix();							//�ָ��ֳ�
    	}  

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����ӿڵĴ�С��λ�� 
        	GLES30.glViewport(0, 0, width, height); 
        	//�����ӿڵĿ�߱�
            Constant.ratio = (float) width / height;
			// ���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-Constant.ratio, Constant.ratio, -1, 1, 20, 100);
			// ���ô˷����������������
			MatrixState.setCamera(0, 8f, 30, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
            
            //��ʼ���任����
            MatrixState.setInitStack();
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //������Ļ����ɫRGBA
            GLES30.glClearColor(0.5f,0.5f,0.5f, 1.0f);  
            //����Բ����
            circle=new Circle(MySurfaceView.this);
            //����ȼ��
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            //�򿪱������   
            GLES30.glEnable(GLES30.GL_CULL_FACE);
        }
    }
}
