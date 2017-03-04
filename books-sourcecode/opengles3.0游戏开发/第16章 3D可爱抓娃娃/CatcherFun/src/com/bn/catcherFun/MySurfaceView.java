package com.bn.catcherFun;

import static com.bn.constant.SourceConstant.*;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import com.bn.MatrixState.MatrixState2D;
import com.bn.MatrixState.MatrixState3D;
import com.bn.hand.R;
import com.bn.util.manager.ShaderManager;
import com.bn.view.BNAbstractView;
import com.bn.view.GameView;
import com.bn.view.LoadView;
import com.bn.view.MainView;
import com.bn.view.MenuView;
import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;

public class MySurfaceView extends GLSurfaceView{
	public MainActivity  activity;
	private SceneRenderer mRenderer;//������Ⱦ��	
	public BNAbstractView currView;//��ǰ����
	public GameView gameView;//��ǰ����
	public boolean isInitOver = false;						//��Դ�Ƿ��ʼ�����
	public MainView mainView;
	public BNAbstractView collectionview;
	public MenuView  menuview;
	public BNAbstractView YXJXView;//��Ϸ��ѧ����
	public BNAbstractView GameAboutView;
	public BNAbstractView ScoreView;
	private static boolean isExit = false;
	public MySurfaceView(Context context) 
	{
		super(context);
		activity=(MainActivity) context;
        this.setEGLContextClientVersion(3);//����GLES�汾Ϊ3.0  
        
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
       
	}
	//�����¼��ص�����
    @Override
    public boolean onTouchEvent(MotionEvent e) 
    {
    	if(currView==null)
		{
			return false;
		}
		return currView.onTouchEvent(e);
    	
	
    }   
    public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if(currView==gameView)
			{
				if(gameView.isMenu)
				{
					currView=gameView;
					gameView.isMenu=false;
				}else
				{
					currView=mainView;
//					if(!isBGMusic){
//						//��������
//						if(!musicOff){
//							MainActivity.sound.playBackGroundMusic(activity, R.raw.nogame);
//						}
//					}
				}
					
			}else if(currView==ScoreView)
			{
				currView=mainView;
			}else if(currView==YXJXView)
			{
				currView=mainView;
			}else if(currView==GameAboutView)
			{
				currView=mainView;
			}else if(currView==collectionview)
			{
				if(isCollection)
				{
					currView=gameView;
					isCollection=false;
	     		     gameView.isMenu=false;
	     			 gameView.reData();
				}else 
				{
					currView=mainView;
//					if(!isBGMusic){
//						//��������
//						if(!musicOff){
//							MainActivity.sound.playBackGroundMusic(activity, R.raw.nogame);
//						}
//					}
				}
				 if(isSet)
	    		  {
	    			  isSet=false;
	    			  currView=mainView;
	    		  }
			
			}else if(currView==mainView)//ֻ�д���������ʱ�ſ��԰����ؼ���������
			{
				if(isSet)
				{
					isSet=false;
					currView=mainView;
				}else
				{
					exit();
				}
				
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void exit()
	{
		if (isExit == false) 
		{
			isExit = true; // ׼���˳�
			Toast.makeText(this.getContext(),"�ٰ�һ���˳���Ϸ", Toast.LENGTH_SHORT).show();
			new Handler().postDelayed(new Runnable()
			{
				public void run()
				{
					isExit = false;
					isBGMusic=true;
					effictOff=true;
				}
			}, 2500);
		}else
		{
			android.os.Process.killProcess(android.os.Process.myPid()); 
		}
	}
	
    private class SceneRenderer implements GLSurfaceView.Renderer 
    {
    	public void onDrawFrame(GL10 gl) 
		{
    		GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
    		if(currView != null)
			{
				currView.drawView(gl);//���ƽ�����Ϣ
			}
        
		}
		public void onSurfaceChanged(GL10 gl, int width, int height) 
		{
			 //�����ӿڴ�С��λ�� 
			GLES30.glViewport
			(
					0,//(int)Constant.ssr.lucX,//x
					0,//(int)Constant.ssr.lucY,//y
					width,
					height
			);
			
			 float ratio= (float) width/height;
            screenWidth = width;
            screenHeight = height;
            MatrixState3D.setInitStack();
           
            //���ô˷����������͸��ͶӰ����
           
            
                MatrixState3D.setProjectFrustum(-ratio, ratio, -1, 1, 1.5f, 100);  
        
            MatrixState3D.setCamera( 
            		EYE_X,   //����λ�õ�X
            		EYE_Y, 	//����λ�õ�Y
            		EYE_Z,   //����λ�õ�Z
            		TARGET_X, 	//�����򿴵ĵ�X
            		TARGET_Y,   //�����򿴵ĵ�Y
            		TARGET_Z,   //�����򿴵ĵ�Z
            		0, 
            		1, 
            		0);
            MatrixState2D.setInitStack();
        	MatrixState2D.setCamera(0,0,5,0f,0f,0f,0f,1f,0f);
        	MatrixState2D.setProjectOrtho(-ratio, ratio, -1, 1, 1, 100);
        	 //���ô˷������������9����λ�þ���
	        MatrixState2D.setCamera(0,0,5,0f,0f,0f,0f,1f,0f);
			MatrixState2D.setLightLocation(0,50,0);
			if(currView==null){
				LoadView lv=new LoadView(MySurfaceView.this);
				if(!isBGMusic){
				//��������
				if(!musicOff){
					MainActivity.sound.playBackGroundMusic(activity, R.raw.nogame);
				}
			}
				currView=lv;
				lv=null;
			}			
			
		}
		public void onSurfaceCreated(GL10 gl, EGLConfig config) 
		{
			//������Ļ����ɫRGBA
            GLES30.glClearColor(0.0f,0.0f,0.0f, 1.0f);
           
          //����Ϊ�򿪱������
            GLES30.glEnable(GL10.GL_CULL_FACE);
            ShaderManager.loadCodeFromFile(activity.getResources());
            ShaderManager.compileShader();
		}
    }
    
}
