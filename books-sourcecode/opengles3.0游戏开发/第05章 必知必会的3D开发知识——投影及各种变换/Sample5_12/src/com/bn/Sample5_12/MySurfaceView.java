package com.bn.Sample5_12;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;

class MySurfaceView extends GLSurfaceView 
{
	private final float TOUCH_SCALE_FACTOR = 180.0f/320;//�Ƕ����ű���
    private SceneRenderer mRenderer;//������Ⱦ��
	 
	private float mPreviousY;//�ϴεĴ���λ��Y����
    private float mPreviousX;//�ϴεĴ���λ��X����
	
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
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
            float dy = y - mPreviousY;//���㴥�ر�Yλ��
            float dx = x - mPreviousX;//���㴥�ر�Xλ��            
            for(SixPointedStar h:mRenderer.ha)
            {
            	h.yAngle += dx * TOUCH_SCALE_FACTOR;//���������������еĸ�����������y����ת�Ƕ�
                h.xAngle+= dy * TOUCH_SCALE_FACTOR;//���������������еĸ�����������x����ת�Ƕ�
            }
        }
        mPreviousY = y;//��¼���ر�λ��
        mPreviousX = x;//��¼���ر�λ��
        return true;
    }

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
    	SixPointedStar[] ha=new SixPointedStar[6];//����������
    	float[][] color=new float[][]{
				{1, 0, 0.1f},//��
				{0.98f, 0.49f, 0.04f},//��
				{1f, 1f, 0.04f},//��
				{0.67f, 1, 0},//��
				{0.27f, 0.41f, 1f},//��
				{0.88f,0.43f,0.92f}};//��
        public void onDrawFrame(GL10 gl) 
        { 
        	//�����Ȼ�������ɫ����
            GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            
            //���������������еĸ���������
            for(int i=0;i<ha.length;i++)
            {
            	SixPointedStar h=ha[i];
            	h.drawSelf();
            }
        }  

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����ӿڵĴ�С��λ�� 
        	GLES30.glViewport(0, 0, width, height); 
        	//�����ӿڵĿ�߱�
        	float ratio= (float) width / height;
            //����͸��ͶӰ
        	MatrixState.setProjectFrustum(-ratio*0.4f, ratio*0.4f, -1*0.4f, 1*0.4f, 1, 50); 
        	
            //���ô˷����������������
            MatrixState.setCamera(0,0,6,0f,0f,0f,0f,1.0f,0.0f);
        }

		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			GLES30.glClearColor(0.5f, 0.5f, 0.5f, 1.0f); // ������Ļ����ɫRGBA
			// ���������������еĸ�������
			for (int i = 0; i < ha.length; i++) {
				ha[i] = new SixPointedStar(MySurfaceView.this, 0.4f, 1.0f,-1.0f * i,color[i]);						
			}			
			GLES30.glEnable(GLES30.GL_DEPTH_TEST);// ����ȼ��
		}
    }
}
