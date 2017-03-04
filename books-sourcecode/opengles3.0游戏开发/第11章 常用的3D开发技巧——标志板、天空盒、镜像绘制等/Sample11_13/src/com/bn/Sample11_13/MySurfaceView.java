package com.bn.Sample11_13;
import android.opengl.GLSurfaceView;
import android.opengl.GLES30;
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
    //����������ı���
  	float cx=0;//�����xλ��
  	float cy=0;//�����yλ��
  	float cz=60;//�����zλ��
  	
  	float tx=0;//Ŀ���xλ��
  	float ty=12;//Ŀ���yλ��
  	float tz=0;//Ŀ���zλ��
  	public float currSightDis=50;//�������Ŀ��ľ���
	public float angdegAzimuth=180;//��λ��	
	
	public MySurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3); //����ʹ��OPENGL ES3.0
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
    }
	
	//�����¼��ص�����
    @Override 
    public boolean onTouchEvent(MotionEvent e) 
    {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
        	 float dy = y - mPreviousY;//���㴥�ر�Yλ��
             float dx = x - mPreviousX;//���㴥�ر�Xλ��
             //��������ֵ���ƶ������
             if(Math.abs(dx)<7f && Math.abs(dy)<7f){
             	break;
             }            
             angdegAzimuth += dx * TOUCH_SCALE_FACTOR;//������x����ת�Ƕ�
             //�����������λ��
             setCameraPostion();
        }
        mPreviousY = y;//��¼���ر�λ��
        mPreviousX = x;//��¼���ر�λ��
        return true;
    }

    //���������λ�õķ���
  	public void setCameraPostion() {
  		//�����������λ��
  		double angradAzimuth = Math.toRadians(angdegAzimuth);//��λ��
  		cx = (float) (tx - currSightDis * Math.sin(angradAzimuth));
  		cy = (float) (ty + currSightDis);
  		cz = (float) (tz - currSightDis * Math.cos(angradAzimuth));
  	}
	private class SceneRenderer implements GLSurfaceView.Renderer 
    {
    	//��ָ����obj�ļ��м��ض���
		LoadedObjectVertexNormal lovo[]=new LoadedObjectVertexNormal[2];//0---ԭ������   1---���                ���
		LoadedObjectVertexNormal lovo0[]=new LoadedObjectVertexNormal[2];//0---ԭ������   1---���                ������
    	
        public void onDrawFrame(GL10 gl) 
        { 
        	 //����cameraλ��
			MatrixState.setCamera(cx, ty, cz, tx, ty, tz, 0, 1, 0);
			MatrixState.setLightLocation(cx,cy,cz);
			
        	//�����Ȼ�������ɫ����
            GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);

            MatrixState.pushMatrix();
            
            //���1
            MatrixState.pushMatrix();
            MatrixState.translate(15f, 0f, -25f);   //ch.obj
            //�����Ʊ���
            GLES30.glFrontFace(GLES30.GL_CW);
            lovo[1].drawSelfEdge();//�������
            //��������
            GLES30.glFrontFace(GLES30.GL_CCW);
            lovo[0].drawSelf();//����ԭ������
            MatrixState.popMatrix();
            
            //���2
            MatrixState.pushMatrix();
            MatrixState.translate(15f,0f, 5f);   //ch.obj
            //�����Ʊ���
            GLES30.glFrontFace(GLES30.GL_CW);
            lovo[1].drawSelfEdge();//�������
            //��������
            GLES30.glFrontFace(GLES30.GL_CCW);
            lovo[0].drawSelf();//����ԭ������
            MatrixState.popMatrix();
            
            
            //Բ1
            MatrixState.pushMatrix();
            MatrixState.translate(-15f, 0f, 8f);
            //�����Ʊ���
            GLES30.glFrontFace(GLES30.GL_CW);
            lovo0[1].drawSelfEdge();//�������
            //��������
            GLES30.glFrontFace(GLES30.GL_CCW);
            lovo0[0].drawSelf();//����ԭ������
            MatrixState.popMatrix();
            
            //Բ2
            MatrixState.pushMatrix();
            MatrixState.translate(-15f,3f, -2f);
            //�����Ʊ���
            GLES30.glFrontFace(GLES30.GL_CW);
            lovo0[1].drawSelfEdge();//�������
            //��������
            GLES30.glFrontFace(GLES30.GL_CCW);
            lovo0[0].drawSelf();//����ԭ������
            MatrixState.popMatrix();
            
            MatrixState.popMatrix();  
        }  

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����Ӵ���С��λ�� 
        	GLES30.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            float ratio = (float) width / height;
            //���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 2, 500);
            //�����������λ��
            setCameraPostion();	
           
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //������Ļ����ɫRGBA
        	GLES30.glClearColor(0f,0f,0f,1.0f);        
            //����ȼ��
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            //�򿪱������   
            GLES30.glEnable(GLES30.GL_CULL_FACE);
            //��ʼ���任����
            MatrixState.setInitStack();
            //��ʼ����Դλ��
            MatrixState.setLightLocation(400, 100, 200);
            //����Ҫ���Ƶ�����
            lovo=LoadUtil.loadFromFile("ch.obj", MySurfaceView.this.getResources(),MySurfaceView.this);
            lovo0=LoadUtil.loadFromFile("qt.obj", MySurfaceView.this.getResources(),MySurfaceView.this);
        }
    }
}
