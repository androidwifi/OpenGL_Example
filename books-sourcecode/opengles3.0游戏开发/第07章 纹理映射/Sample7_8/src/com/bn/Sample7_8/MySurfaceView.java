package com.bn.Sample7_8;
import java.nio.ByteBuffer;
import android.opengl.GLSurfaceView;
import android.opengl.GLES30;
import android.view.MotionEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.annotation.SuppressLint;
import android.content.Context;

class MySurfaceView extends GLSurfaceView
{
	private final float TOUCH_SCALE_FACTOR = 180.0f/320;//�Ƕ����ű���
    private SceneRenderer mRenderer;//������Ⱦ��    
    
    private float mPreviousY;//�ϴεĴ���λ��Y���� 
    private float mPreviousX;//�ϴεĴ���λ��X����
    
    int textureId;//ϵͳ���������id
	
	public MySurfaceView(Context context) { 
        super(context);
        this.setEGLContextClientVersion(3); //����ʹ��OPENGL ES 3.0
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
    } 
	
	//�����¼��ص�����
	@SuppressLint("ClickableViewAccessibility")
	@Override 
    public boolean onTouchEvent(MotionEvent e) 
    {
        float y = e.getY(); 
        float x = e.getX();
        switch (e.getAction())
        {
          case MotionEvent.ACTION_MOVE:
            float dy = y - mPreviousY;//���㴥�ر�Yλ��
            float dx = x - mPreviousX;//���㴥�ر�Xλ��
            mRenderer.yAngle += dx * TOUCH_SCALE_FACTOR;//������x����ת�Ƕ�
            mRenderer.xAngle+= dy * TOUCH_SCALE_FACTOR;//������z����ת�Ƕ�
        }
        mPreviousY = y;//��¼���ر�λ��
        mPreviousX = x;//��¼���ر�λ��
        return true;
    }

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {
		float yAngle;//��Y����ת�ĽǶ�
    	float xAngle; //��X����ת�ĽǶ�
    	//¥�ݻ��ƶ�������
		Stairs lovo;
        public void onDrawFrame(GL10 gl)
        {
        	//�����Ȼ�������ɫ����
            GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            //����ϵ��Զ
            MatrixState.pushMatrix();
            MatrixState.translate(0, -0.2f, -3f);   
            //��Y�ᡢX����ת
            MatrixState.rotate(yAngle, 0, 1, 0);
            MatrixState.rotate(xAngle, 1, 0, 0);
            lovo.drawSelf(textureId);
            MatrixState.popMatrix();                  
        }  

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����Ӵ���С��λ�� 
        	GLES30.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            float ratio = (float) width / height;
            //���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-ratio*0.3f, ratio*0.3f, -1*0.3f, 1*0.3f, 2, 100);
            //���ô˷������������9����λ�þ���
            MatrixState.setCamera(0,0,0,0f,0f,-1f,0f,1.0f,0.0f);
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) 
        {
            //������Ļ����ɫRGBA
            GLES30.glClearColor(0.0f,0.0f,0.0f,1.0f);    
            //����ȼ��
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            //�򿪱������   
            GLES30.glEnable(GLES30.GL_CULL_FACE);
            
            //��ʼ���任����
            MatrixState.setInitStack();
            MatrixState.setLightLocation(0, 100, 200);
            //����¥�ݻ��ƶ���
            lovo=new Stairs(MySurfaceView.this);
            //��������
            //��������
    		byte[] texData=
    		{
    			//3d 1
    			80,80,80,(byte)255, (byte)255,(byte)255,(byte)255,(byte)255,
    			(byte)255,(byte)255,(byte)255,(byte)255, 80,80,80,(byte)255,
    			//3d 2
    			(byte)255,(byte)255,(byte)255,(byte)255, 80,80,80,(byte)255,
    			80,80,80,(byte)255, (byte)255,(byte) 255,(byte)255,(byte)255,
    		};
    		textureId=init3DTexture(texData,2,2,2);
        }
    }
  	public int init3DTexture(byte[] texData,int width,int height,int depth)//����3D����ķ���
	{
		//��������ID
		int[] textures = new int[1];
		GLES30.glGenTextures
		(
				1,          //����������id������
				textures,   //����id������
				0           //ƫ����
		);    
		int textureId=textures[0];//�������id
		GLES30.glBindTexture(GLES30.GL_TEXTURE_3D, textureId);//������
		//����MIN������ʽ
		GLES30.glTexParameterf(GLES30.GL_TEXTURE_3D, GLES30.GL_TEXTURE_MIN_FILTER,GLES30.GL_NEAREST);
		//����MAG������ʽ
		GLES30.glTexParameterf(GLES30.GL_TEXTURE_3D,GLES30.GL_TEXTURE_MAG_FILTER,GLES30.GL_NEAREST);
		//S��Ϊ�ظ����췽ʽ
		GLES30.glTexParameterf(GLES30.GL_TEXTURE_3D, GLES30.GL_TEXTURE_WRAP_S,GLES30.GL_REPEAT);
		//T��Ϊ�ظ����췽ʽ
		GLES30.glTexParameterf(GLES30.GL_TEXTURE_3D, GLES30.GL_TEXTURE_WRAP_T,GLES30.GL_REPEAT);
		//R��Ϊ�ظ����췽ʽ
		GLES30.glTexParameterf(GLES30.GL_TEXTURE_3D, GLES30.GL_TEXTURE_WRAP_R,GLES30.GL_REPEAT);
		//�����������ݻ���
		ByteBuffer texels = ByteBuffer.allocateDirect(texData.length);
		texels.put(texData);//�򻺳���������������
		texels.position(0);//���û�������ʼλ��
		
		GLES30.glTexImage3D//ʵ�ʼ���3D����ķ���
		(
				GLES30.GL_TEXTURE_3D,  //��������
				0, //������
				GLES30.GL_RGBA8, //�����ڲ���ʽ
				width, //����Ŀ��
				height, //����ĸ߶�
				depth, //��������
				0, //����߿�ߴ�
				GLES30.GL_RGBA, //����ĸ�ʽ
				GLES30.GL_UNSIGNED_BYTE, //�������ݵ�����
	            texels//�������ݵĻ���
	    );
	    
        return textureId;//����3D����id
	}
}
