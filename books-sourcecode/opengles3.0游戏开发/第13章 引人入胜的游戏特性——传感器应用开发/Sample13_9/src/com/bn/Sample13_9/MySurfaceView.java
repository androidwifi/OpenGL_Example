package com.bn.Sample13_9;
import java.io.IOException;
import java.io.InputStream;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.GLES30;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

@SuppressLint("ClickableViewAccessibility")
class MySurfaceView extends GLSurfaceView {
    
	private final float TOUCH_SCALE_FACTOR = 180.0f/320;//�Ƕ����ű���
	private float mPreviousY;//�ϴεĴ���λ��Y����
    private float mPreviousX;//�ϴεĴ���λ��X����
	
	private float cameraX=0;//�������λ��
	private float cameraY=30;
	private float cameraZ=0;
	
	private float targetX=0;//����
	private float targetY=0;
	private float targetZ=0;
	
	private float sightDis=26;//�������Ŀ��ľ���
	private float angdegElevation=90;//����
	private float angdegAzimuth=0;//��λ��
    
	private SceneRenderer mRenderer;//������Ⱦ��
    int texFloorId;		//�ذ������id
    int texWallId;		//ǽ�������

    BallGoThread ballGoThread;		//���˶����߳�
    
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
            
            angdegAzimuth += dx * TOUCH_SCALE_FACTOR;//������y����ת�Ƕ�
            angdegElevation+= dy * TOUCH_SCALE_FACTOR;//������x����ת�Ƕ�
            
            //����
            if(angdegElevation>=90){
            	angdegElevation=90;
            }else if(angdegElevation<=0){
            	angdegElevation=0;
            } 
        }
        mPreviousY = y;//��¼���ر�λ��
        mPreviousX = x;//��¼���ر�λ��
        return true;
    }
    
	private class SceneRenderer implements GLSurfaceView.Renderer 
    { 
		CubeGroup cubeGroup;//��������
		BallForControl ballForControl;	//��
		
        @SuppressLint("NewApi")
		public void onDrawFrame(GL10 gl) 
        { 
        	//�����Ȼ�������ɫ����
            GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            
            double angradElevation=Math.toRadians(angdegElevation);//���ǣ����ȣ�
        	double angradAzimuth=Math.toRadians(angdegAzimuth);//��λ��
            cameraX=(float) (targetX+sightDis*Math.cos(angradElevation)*Math.sin(angradAzimuth));
            cameraY=(float) (targetY+sightDis*Math.sin(angradElevation));
            cameraZ=(float) (targetZ+sightDis*Math.cos(angradElevation)*Math.cos(angradAzimuth));
            
            MatrixState.setCamera(//����cameraλ�� 
            		cameraX, //����λ�õ�X
            		cameraY, //����λ�õ�Y
            		cameraZ, //����λ�õ�Z
            		
            		targetX, //�����򿴵ĵ�X
            		targetY, //�����򿴵ĵ�Y
            		targetZ, //�����򿴵ĵ�Z
            		
            		0,  //ͷ�ĳ���
            		1, 
            		0
            );
            
            //����ǽ��
            MatrixState.pushMatrix();
            cubeGroup.drawSelf(texFloorId,texWallId);
            MatrixState.popMatrix();

            MatrixState.pushMatrix();
            //������
            ballForControl.drawSelf();
            MatrixState.popMatrix();
            
        }   

        @SuppressLint("NewApi")
		public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����Ӵ���С��λ�� 
        	GLES30.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            float ratio= (float) width / height;
            //���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 4f, 100);
	        //��ʼ����Դ
	        MatrixState.setLightLocation(0 , 12 , 0);
	        //�����̶߳���
	        ballGoThread=new BallGoThread(ballForControl);
	        //�̱߳�־λ��Ϊtrue
	        ballGoThread.setFlag(true);
	        //�����߳�
	        ballGoThread.start();
	        
        }

        @SuppressLint("NewApi")
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //������Ļ����ɫRGBA
            GLES30.glClearColor(0.0f,0.0f,0.0f, 1.0f);  
            //������Ȳ���
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
    		//����Ϊ�򿪱������
            GLES30.glEnable(GLES30.GL_CULL_FACE);
            //��ʼ���任����
            MatrixState.setInitStack();
            //�ذ������id
            texFloorId=initTexture(R.drawable.tex_floor);
            //ǽ�������id
            texWallId=initTexture(R.drawable.tex_wall);
            
            //��������������
            cubeGroup=new CubeGroup(MySurfaceView.this,Constant.SCALE, 
            		Constant.CUBE_LENGTH, Constant.CUBE_HEIGHT, Constant.CUBE_WIDTH ,Constant.WALL_WIDTH);//��������

            //������Ķ���
            ballForControl=new BallForControl(MySurfaceView.this,Constant.SCALE,Constant.AHALF,5);

            
            
        }
    }
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		//�ر��߳�
		ballGoThread.setFlag(false);
	}
	
	@SuppressLint("NewApi")
	public int initTexture(int drawableId)//textureId
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
		GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S,GLES30.GL_CLAMP_TO_EDGE);
		GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T,GLES30.GL_CLAMP_TO_EDGE);
        
        //ͨ������������ͼƬ===============begin===================
        InputStream is = this.getResources().openRawResource(drawableId);
        Bitmap bitmapTmp;
        try 
        {
        	bitmapTmp = BitmapFactory.decodeStream(is);
        } 
        finally 
        {
            try 
            {
                is.close();
            } 
            catch(IOException e) 
            {
                e.printStackTrace();
            }
        }
        //ͨ������������ͼƬ===============end=====================  
        
        //ʵ�ʼ�������
        GLUtils.texImage2D
        (
        		GLES30.GL_TEXTURE_2D,   //�������ͣ���OpenGL ES�б���ΪGL10.GL_TEXTURE_2D
        		0, 					  //����Ĳ�Σ�0��ʾ����ͼ��㣬�������Ϊֱ����ͼ
        		bitmapTmp, 			  //����ͼ��
        		0					  //����߿�ߴ�
        );
        bitmapTmp.recycle(); 		  //������سɹ����ͷ�ͼƬ
        
        return textureId;
	}
}
