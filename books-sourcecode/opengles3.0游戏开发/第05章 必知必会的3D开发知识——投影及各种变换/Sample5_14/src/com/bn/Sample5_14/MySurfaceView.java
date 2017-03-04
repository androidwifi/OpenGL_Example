package com.bn.Sample5_14;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.annotation.SuppressLint;
import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

class MySurfaceView extends GLSurfaceView 
{
	private final float TOUCH_SCALE_FACTOR = 180.0f/320;//�Ƕ����ű���
    private SceneRenderer mRenderer;//������Ⱦ��
    private float mPreviousX;//�ϴεĴ���λ��X����
    float yAngle=90;//�ܳ�����y����ת�ĽǶ�
    private float mPreviousY;//�ϴεĴ���λ��X����
    float xAngle=20;//�ܳ�����y����ת�ĽǶ�
    
	public MySurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3); //����ʹ��OPENGL ES3.0
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
    }
	//�����¼��ص�����
    @SuppressLint("ClickableViewAccessibility")
	@Override 
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
            float dx = x - mPreviousX;//���㴥�ر�Xλ��
            yAngle += dx * TOUCH_SCALE_FACTOR;//���������ζ���y����ת�Ƕ�
            float dy = y - mPreviousY;//���㴥�ر�Xλ��
            xAngle += dy * TOUCH_SCALE_FACTOR;//���������ζ���y����ת�Ƕ�
        }
        mPreviousX=x;
        mPreviousY=y;
        return true;
    }

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
    	Cube cube1;//���������1����
    	Cube cube2;//���������2����
    	
        @SuppressLint({ "InlinedApi", "NewApi" })
		public void onDrawFrame(GL10 gl) 
        { 
        	//�����Ȼ�������ɫ����
            GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            //�����ֳ�
            MatrixState.pushMatrix();
            //��Y����ת
            MatrixState.rotate(yAngle, 0, 1, 0);//��y����תyAngle��
            MatrixState.rotate(xAngle, 1, 0, 0);//��X����תxAngle��
            //�������������
            MatrixState.pushMatrix();
            MatrixState.translate(-250f, 0, 0);
            cube1.drawSelf();
            MatrixState.popMatrix();
            
            //�����Ҳ�������
            MatrixState.pushMatrix();
            MatrixState.translate(250f,0, 0);
            cube2.drawSelf();
            MatrixState.popMatrix();
            
            //�ָ��ֳ�
            MatrixState.popMatrix();
        }  

        @SuppressLint("NewApi")
		public void onSurfaceChanged(GL10 gl, int width, int height) 
        {
            //�����ӿڵĴ�С��λ�� 
        	GLES30.glViewport(0, 0, width, height); 
        	//�����ӿڵĿ�߱�
            float ratio = (float) width / height;
            
            final float NEAR=1.0f;//͸�Ӳ���near
            final float FAR=10000.0f;//͸�Ӳ���far
            final float LEFT=-NEAR*ratio*0.25f;//͸�Ӳ���left
            final float RIGHT=NEAR*ratio*0.25f;//͸�Ӳ���right
            final float BOTTOM=-NEAR*0.25f;//͸�Ӳ���bottom
            final float TOP=NEAR*0.25f;//͸�Ӳ���top
            
            
	        //���ô˷����������͸��ͶӰ����
	        MatrixState.setProjectFrustum(LEFT, RIGHT, BOTTOM, TOP, NEAR, FAR);
	        //���ô˷����������������
	        MatrixState.setCamera(5000f,0.5f,0f,0f,0f,0f,0f,1.0f,0.0f);
	        
            //��ʼ���任����
            MatrixState.setInitStack();
        }

        @SuppressLint("NewApi")
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //������Ļ����ɫRGBA
            GLES30.glClearColor(0f,0f,0f, 1.0f);  
            //�������������
            cube1=new Cube(MySurfaceView.this,500,new float[]{0,1,1,0});
            cube2=new Cube(MySurfaceView.this,499.5f,new float[]{1,1,0,0});
            //����ȼ��
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            //�򿪱������   
            GLES30.glEnable(GLES30.GL_CULL_FACE);  
        }
    }
}
