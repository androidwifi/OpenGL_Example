package com.bn.sample14_2;

import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES30;
import android.opengl.GLUtils;

import static com.bn.sample14_2.Sample14_2Activity.*;

class GL2JNIView extends GLSurfaceView {
    Renderer renderer;
    
	static float direction=0;//���߷���
    static float cx=0;//�����x���� 
    static float cz=20;//�����z����
    
    static float tx=0;//�۲�Ŀ���x����
    static float tz=0;//�۲�Ŀ���z����
    static final float DEGREE_SPAN=(float)(3.0/180.0f*Math.PI);//�����ÿ��ת���ĽǶ�
    //�߳�ѭ���ı�־λ  
    boolean flag=true;
    float x;
    float y;
    float Offset=20;
    
    @SuppressLint("ClickableViewAccessibility") @Override
	public boolean onTouchEvent(MotionEvent event) {
    	x=event.getX();
		y=event.getY();
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				flag=true;
				new Thread()
				{
					@Override
					public void run()
					{
						while(flag)
						{
							if(x>0&&x<WIDTH/2&&y>0&&y<HEIGHT/2)
							{//��ǰ
								cx=cx-(float)Math.sin(direction)*1.0f;
								cz=cz-(float)Math.cos(direction)*1.0f;
							}
							else if(x>WIDTH/2&&x<WIDTH&&y>0&&y<HEIGHT/2)
							{//���
								cx=cx+(float)Math.sin(direction)*1.0f;
								cz=cz+(float)Math.cos(direction)*1.0f;
							}
							else if(x>0&&x<WIDTH/2&&y>HEIGHT/2&&y<HEIGHT)
							{
								direction=direction+DEGREE_SPAN;
							}
							else if(x>WIDTH/2&&x<WIDTH&&y>HEIGHT/2&&y<HEIGHT)
							{
								direction=direction-DEGREE_SPAN;
							}
							try
							{
								Thread.sleep(100);
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
						}
					}
				}.start();
			break;
			case MotionEvent.ACTION_UP:
				flag=false;
			break;
		}
			//�����µĹ۲�Ŀ���XZ����
			tx=(float)(cx-Math.sin(direction)*Offset);//�۲�Ŀ���x���� 
	        tz=(float)(cz-Math.cos(direction)*Offset);//�۲�Ŀ���z����
	        
	        //�����µ������λ��
	        GL2JNILib.setCamera(cx,5,cz,tx,1,tz,0,1,0);
			return true;
	}

	public GL2JNIView(Context context) {
        super(context);
		this.setEGLContextClientVersion(3);//ʹ��OpenGL ES 3.0�����øò���Ϊ3
		renderer=new Renderer();//����Renderer��Ķ���
		this.setRenderer(renderer);//������Ⱦ��
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    private class Renderer implements GLSurfaceView.Renderer {
        public void onDrawFrame(GL10 gl) {
            GL2JNILib.step();//���ñ��ط���ˢ�³���
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GL2JNILib.init(GL2JNIView.this,width, height);//���ñ��ط�����ʼ��
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) 
        {

        }
    }
    
	//��������ķ���
	@SuppressLint("NewApi")
	public static int initTextureRepeat(GLSurfaceView gsv,String pname)
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
		GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S,GLES30.GL_REPEAT);
		GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T,GLES30.GL_REPEAT);
        
        //ͨ������������ͼƬ===============begin===================
		InputStream is = null;
		try {
			is = gsv.getResources().getAssets().open(pname);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        Bitmap bitmapTmp;
        try {
        	bitmapTmp = BitmapFactory.decodeStream(is);
        } 
        finally {
            try {
                is.close();
            } 
            catch(IOException e) {
                e.printStackTrace();
            }
        }
        //ʵ�ʼ�������
        GLUtils.texImage2D
        (
        		GLES30.GL_TEXTURE_2D,   //��������
        		0, 					  	//����Ĳ�Σ�0��ʾ����ͼ��㣬�������Ϊֱ����ͼ
        		bitmapTmp, 			  	//����ͼ��
        		0					  	//����߿�ߴ�
        );
        bitmapTmp.recycle(); 		  	//������سɹ����ͷ�ͼƬ 
        return textureId;
	}
}
