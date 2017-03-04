package com.bn.Sample5_15;

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
    float polygonOffsetFactor =-1.0f;
    float polygonOffsetUnits  =-2.0f;
    
    
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
		ColorRect color1;//ƽ�����1����
		ColorRect color2;//ƽ�����2����
    	
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
            MatrixState.translate(-250f, 0, -0.1f);
            color1.drawSelf();
            MatrixState.popMatrix();
            
            GLES30.glEnable (GLES30.GL_POLYGON_OFFSET_FILL );//���ö����ƫ��
            GLES30.glPolygonOffset ( polygonOffsetFactor, polygonOffsetUnits );
            
            //�����Ҳ�������
            MatrixState.pushMatrix();
            MatrixState.translate(250f,0, 0f);
            color2.drawSelf();
            MatrixState.popMatrix();
            
            GLES30.glDisable(GLES30.GL_POLYGON_OFFSET_FILL );//���ö����ƫ��
            
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
             
	        //���ô˷����������͸��ͶӰ����
	        MatrixState.setProjectFrustum(-ratio*75f, ratio*75f, -75f, 75f, 300f, 10000f);
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
            color1=new ColorRect(MySurfaceView.this,new float[]{0,1,1,0});//��ɫ
            color2=new ColorRect(MySurfaceView.this,new float[]{1,1,0,0});//��ɫ
            //����ȼ��
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            //�򿪱������   
            GLES30.glEnable(GLES30.GL_CULL_FACE);  
        }
    }
}
