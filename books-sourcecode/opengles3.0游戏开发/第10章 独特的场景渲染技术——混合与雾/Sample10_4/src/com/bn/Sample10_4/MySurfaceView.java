package com.bn.Sample10_4;
import java.io.IOException;
import java.io.InputStream;
import android.opengl.GLSurfaceView;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.view.MotionEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

class MySurfaceView extends GLSurfaceView 
{
	private final float TOUCH_SCALE_FACTOR = 180.0f/200;//�Ƕ����ű���
    private SceneRenderer mRenderer;//������Ⱦ��    
    private float mPreviousX;//�ϴεĴ���λ��X����
	//����������ı���
	float cx=0;//�����xλ��
	float cy=150;//�����yλ��
	float cz=400;//�����zλ��
	
	float pmScale = 200f;//ƽ����εı߳�
	
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
        float x = e.getX();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
            float dx = x - mPreviousX;//���㴥�ر�Xλ��
            cx += dx * TOUCH_SCALE_FACTOR;//������x����ת�Ƕ�
            //��cx������һ����Χ��
            cx = Math.max(cx, -200);
            cx = Math.min(cx, 200);
            break;
        }
        mPreviousX = x;//��¼���ر�λ��
        return true;
    }
	private class SceneRenderer implements GLSurfaceView.Renderer 
    {
    	//��ָ����obj�ļ��м��ض���
		LoadedObjectVertexNormalFace cft;
		LoadedObjectVertexNormalAverage qt;
		LoadedObjectVertexNormalAverage yh;
		LoadedObjectVertexNormalAverage ch;
		TextureRect pm;
		final float disWithCenter = 12.0f;//���������ĵ�ľ���
        public void onDrawFrame(GL10 gl) 
        { 
        	//�����Ȼ�������ɫ����
            GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            //����cameraλ��
            MatrixState.setCamera
            (
            		cx,	//����λ�õ�X
            		cy, //����λ�õ�Y
            		cz, //����λ�õ�Z
            		0, 	//�����򿴵ĵ�X
            		0,  //�����򿴵ĵ�Y
            		0,  //�����򿴵ĵ�Z
            		0, 	//up����
            		1, 
            		0
            );
            MatrixState.pushMatrix();      
            //�����ص����岿λ�����������   
            MatrixState.pushMatrix();
            pm.drawSelf();//ƽ��
            MatrixState.popMatrix();   
            //��������
            MatrixState.pushMatrix();
            MatrixState.scale(5.0f, 5.0f, 5.0f);          
            //�������� 
            //���Ƴ�����
            MatrixState.pushMatrix();
            MatrixState.translate(-disWithCenter, 0f, 0);
            cft.drawSelf();
            MatrixState.popMatrix();   
            //��������
            MatrixState.pushMatrix();
            MatrixState.translate(disWithCenter, 0f, 0);
            qt.drawSelf();
            MatrixState.popMatrix();  
            //����Բ��
            MatrixState.pushMatrix();
            MatrixState.translate(0, 0, -disWithCenter);
            yh.drawSelf();
            MatrixState.popMatrix();  
            //���Ʋ��
            MatrixState.pushMatrix();
            MatrixState.translate(0, 0, disWithCenter);
            ch.drawSelf();
            MatrixState.popMatrix();
            MatrixState.popMatrix();             
          
            MatrixState.popMatrix();                  
        }  

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����Ӵ���С��λ�� 
        	GLES30.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            float ratio = (float) width / height;
            //���ô˷����������͸��ͶӰ����
            float a = 0.5f;
            MatrixState.setProjectFrustum(-ratio*a, ratio*a, -1*a, 1*a, 2, 1000);
            //��ʼ����Դλ��
            MatrixState.setLightLocation(100, 100, 100);
        }
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //������Ļ����ɫRGBA
            GLES30.glClearColor(0.0f,0.0f,0.0f,1.0f);    
            //����ȼ��
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            //�򿪱������   
            GLES30.glEnable(GLES30.GL_CULL_FACE);
            //��ʼ���任����
            MatrixState.setInitStack();  
            //����Ҫ���Ƶ�����
            ch=LoadUtil.loadFromFileVertexOnlyAverage("ch.obj", MySurfaceView.this.getResources(),MySurfaceView.this);
    		cft=LoadUtil.loadFromFileVertexOnlyFace("cft.obj", MySurfaceView.this.getResources(),MySurfaceView.this);
    		qt=LoadUtil.loadFromFileVertexOnlyAverage("qt.obj", MySurfaceView.this.getResources(),MySurfaceView.this);
    		yh=LoadUtil.loadFromFileVertexOnlyAverage("yh.obj", MySurfaceView.this.getResources(),MySurfaceView.this);
    		pm = new TextureRect(MySurfaceView.this,pmScale, pmScale);
        }
    }

	
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
        		GLES30.GL_TEXTURE_2D, //��������
        		0, 					  //����Ĳ�Σ�0��ʾ����ͼ��㣬�������Ϊֱ����ͼ
        		bitmapTmp, 			  //����ͼ��
        		0					  //����߿�ߴ�
        );
        bitmapTmp.recycle(); 		  //������سɹ����ͷ�ͼƬ
        
        return textureId;
	}
}
