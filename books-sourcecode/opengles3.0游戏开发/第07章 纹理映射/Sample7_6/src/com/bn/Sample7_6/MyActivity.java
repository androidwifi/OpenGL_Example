package com.bn.Sample7_6;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MyActivity extends Activity {
	private MySurfaceView mGLSurfaceView;
	static boolean threadFlag;//���������X����ת������־λ
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);         
        //����Ϊȫ��
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//����Ϊ����ģʽ
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//�л���������
			
		//��ʼ��GLSurfaceView
        mGLSurfaceView = new MySurfaceView(this);
        setContentView(mGLSurfaceView);	
        mGLSurfaceView.requestFocus();//��ȡ����
        mGLSurfaceView.setFocusableInTouchMode(true);//����Ϊ�ɴ���  
    }

    @Override
    protected void onResume() {
        super.onResume();
        threadFlag=true;
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        threadFlag=false;
        mGLSurfaceView.onPause();
    }    
}



