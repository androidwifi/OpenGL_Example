package com.bn.Sample12_2;

import static com.bn.Sample12_2.Sample12_2Activity.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.view.MotionEvent;

public class MySurfaceView extends GLSurfaceView
{
	static final float UNIT_SIZE=1f;
	static float direction=0;//���߷���
    static float cx=0;//�����x����
    static float cz=15;//�����z����
    static final float DEGREE_SPAN=(float)(3.0/180.0f*Math.PI);//�����ÿ��ת���ĽǶ�
    //�߳�ѭ���ı�־λ
    boolean flag=true;
    float x;
    float y;
    float Offset=15;
	SceneRenderer mRender;
	float preX;
	float preY;
	float ratio;
	public MySurfaceView(Context context)
	{
		super(context);
		this.setEGLContextClientVersion(3); //����ʹ��OPENGL ES3.0
        mRender = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRender);				//������Ⱦ��		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ 
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
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
								Offset=Offset-0.5f;
							}
							else if(x>WIDTH/2&&x<WIDTH&&y>0&&y<HEIGHT/2)
							{//���
								Offset=Offset+0.5f;
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
		cx=(float)(Math.sin(direction)*Offset);//�۲�Ŀ���x���� 
        cz=(float)(Math.cos(direction)*Offset);//�۲�Ŀ���z����     
        
        //�����������ĳ���
        mRender.tg.calculateBillboardDirection();
        
        //�����������ӵ�ľ�������
        Collections.sort(mRender.tg.alist);
        //�����µ������λ��
        MatrixState.setCamera(cx,0,cz,0,0,0,0,1,0);
		return true;
	}
	
	private class SceneRenderer implements GLSurfaceView.Renderer 
    {
		TreeGroup tg;
		Desert desert;
		TextureRect tr;//alpha�������������
		int treeId;
		int desertId;
		int maskTextureId;///ϵͳ�����alpha��������id
		@Override
		public void onDrawFrame(GL10 gl)
		{
			//�����Ȼ�������ɫ����
            GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            //���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 1, 100);
            //���ô˷�������������۲����
            MatrixState.setCamera(cx,0,cz,0,0,0,0f,1.0f,0.0f);
            MatrixState.pushMatrix();
            MatrixState.translate(0, -2, 0);
            desert.drawSelf(desertId);
            MatrixState.popMatrix();
            
            //�������
            GLES30.glEnable(GLES30.GL_BLEND);
            //���û������
            GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);
            MatrixState.pushMatrix();
            MatrixState.translate(0, -2, 0);
            tg.drawSelf(treeId);
            MatrixState.popMatrix();
            //�رջ��
            GLES30.glDisable(GLES30.GL_BLEND);   
             
            //�����Ȼ���
            GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT);
			MatrixState.pushMatrix();
			//���ô˷����������ƽ��ͶӰ����
			MatrixState.setProjectOrtho(-1f, 1f, -1f, 1f, 1, 100);
			//���ô˷�������������۲����
			MatrixState.setCamera(0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);			
			tr.drawSelf(maskTextureId);//����alpha�����þ���
			MatrixState.popMatrix();
		}
		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height)
		{
			//�����Ӵ���С��λ�� 
        	GLES30.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            ratio = (float) width / height;
            //���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 1, 100);
            //���ô˷�������������۲����
            MatrixState.setCamera(cx,0,cz,0,0,0,0f,1.0f,0.0f);
		}
		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config)
		{
			//������Ļ����ɫRGBA
            GLES30.glClearColor(1.0f,1.0f,1.0f,1.0f);
            //����ȼ��
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            MatrixState.setInitStack();
            
            tg=new TreeGroup(MySurfaceView.this);
            tr = new TextureRect(MySurfaceView.this);
          	//��ʼ����˳��          
            Collections.sort(mRender.tg.alist);
            desert=new Desert
            (
            	MySurfaceView.this,
            	new float[]
	            {
	          		0,0, 0,6, 6,6,
	          		6,6, 6,0, 0,0
	            } ,
	            30,
	            20
            ); 
            //��ʼ������
            treeId=initTexture(R.drawable.tree);
            desertId=initTexture(R.drawable.desert); 
			maskTextureId = initTexture(R.drawable.mask);
		}
    }
	//���������id
	public int initTexture(int drawableId)
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