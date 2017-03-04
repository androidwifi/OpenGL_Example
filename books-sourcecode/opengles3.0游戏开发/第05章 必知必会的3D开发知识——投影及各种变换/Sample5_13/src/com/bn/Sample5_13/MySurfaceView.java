package com.bn.Sample5_13;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import static com.bn.Sample5_13.Constant.*;

class MySurfaceView extends GLSurfaceView 
{
	private final float TOUCH_SCALE_FACTOR = 180.0f/320;//�Ƕ����ű���
    private SceneRenderer mRenderer;//������Ⱦ��
	
    private float mPreviousX;//�ϴεĴ���λ��X����
    
    float yAngle=0;//�ܳ�����y����ת�ĽǶ�
	
	public MySurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3); //����ʹ��OPENGL ES3.0
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
    }   
	
	//�����¼��ص�����
    @Override 
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
            float dx = x - mPreviousX;//���㴥��λ��Xλ��
            yAngle += dx * TOUCH_SCALE_FACTOR;//���������ζ���y����ת�Ƕ�
        }
        mPreviousX=x;
        return true;
    }

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
    	Cube cube;//�������������
    	
        public void onDrawFrame(GL10 gl) 
        { 
        	//�����Ȼ�������ɫ����
            GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            //�����ֳ�
            MatrixState.pushMatrix();
          //��Y����ת��ʵ���ܳ�����ת��
            MatrixState.rotate(yAngle, 0, 1, 0);
            
            //�������������
            MatrixState.pushMatrix();
            MatrixState.translate(-3, 0, 0);
            MatrixState.rotate(60, 0, 1, 0);
            cube.drawSelf();
            MatrixState.popMatrix();
            
            //�����Ҳ�������
            MatrixState.pushMatrix();
            MatrixState.translate(3, 0, 0);
            MatrixState.rotate(-60, 0, 1, 0);
            cube.drawSelf();
            MatrixState.popMatrix();
            
            //�ָ��ֳ�
            MatrixState.popMatrix();
        }  

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����ӿڵĴ�С��λ�� 
        	GLES30.glViewport(0, 0, width, height); 
        	//�����ӿڵĿ�߱�
            ratio = (float) width / height;
            
	        //���ô˷����������͸��ͶӰ����
	        MatrixState.setProjectFrustum(-ratio*0.7f, ratio*0.7f, -0.7f, 0.7f, 1, 10);
	        //���ô˷����������������
	        MatrixState.setCamera(0,0.5f,4,0f,0f,0f,0f,1.0f,0.0f);
            
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
