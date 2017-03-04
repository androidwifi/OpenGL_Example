package com.bn.Sample11_7;

import java.io.IOException;
import java.io.InputStream;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import static com.bn.Sample11_7.Constant.*;

class MySurfaceView extends GLSurfaceView 
{
    private SceneRenderer mRenderer;//������Ⱦ��    
    int textureFloor;//ľ�ذ�����id
    int textureBallId;//��������id
	
	public MySurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3); //����ʹ��OPENGL ES3.0
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
    }

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
    	TextureRect texRect;//��ʾ�ذ���������
    	BallTextureByVertex btbv;//���ڻ��Ƶ�������
    	BallForControl bfd;//�����������������
    	
        public void onDrawFrame(GL10 gl) 
        { 
        	//�����Ȼ�������ɫ����
            GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
             
            MatrixState.pushMatrix();
            MatrixState.translate(0, -2, 0);
            
            MatrixState.pushMatrix();	
    		MatrixState.translate(0, FLOOR_Y, 0);
            //����ľ�ذ�
            texRect.drawSelf(textureFloor);
            MatrixState.popMatrix();
            
            bfd.drawSelfMirror(textureBallId);//���ƾ�����
            bfd.drawSelf(textureBallId);//����ʵ������ 
            MatrixState.popMatrix();
        }  

        public void onSurfaceChanged(GL10 gl, int width, int height) 
        {
            //�����Ӵ���С��λ�� 
        	GLES30.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            float ratio = (float) width / height;
            //���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 3, 100);
            //���ô˷������������9����λ�þ���
            MatrixState.setCamera(0.0f,7.0f,7.0f,0,0f,0,0,1,0);
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //������Ļ����ɫRGBA
            GLES30.glClearColor(0.0f,0.0f,0.0f,1.0f);  
            //����������ζԶ��� 
            texRect=new TextureRect(MySurfaceView.this,4,2.568f);  
            //�������ڻ�����������������
            btbv=new BallTextureByVertex(MySurfaceView.this,BALL_SCALE);
            //������������������������뷴����������BallForControl�����
            bfd=new BallForControl(btbv,3f);
            //�ر���ȼ��
            //������ȼ��֮��ֻ�о��������������Żᱻ����
            GLES30.glDisable(GLES30.GL_DEPTH_TEST);
            //��ʼ������
            textureFloor=initTexture(R.drawable.mdb);
            textureBallId=initTexture(R.drawable.basketball);
            //�򿪱������   
            GLES30.glEnable(GLES30.GL_CULL_FACE);
            //��ʼ���任����
            MatrixState.setInitStack();
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
        
        //ͨ������������ͼƬ
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
        
        //ʵ�ʼ�������
        GLUtils.texImage2D
        (
        		GLES30.GL_TEXTURE_2D,   //��������
        		0, 					  //����Ĳ�Σ�0��ʾ����ͼ��㣬�������Ϊֱ����ͼ
        		bitmapTmp, 			  //����ͼ��
        		0					  //����߿�ߴ�
        );
        bitmapTmp.recycle(); 		  //������سɹ����ͷ�ͼƬ
        return textureId;
	}
}
