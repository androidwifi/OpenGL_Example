package com.bn.Sample7_4;

import com.bn.Sample7_4.R;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MyActivity extends Activity {
	private MySurfaceView mGLSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);         
        //����Ϊȫ��
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//����Ϊ����ģʽ
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		//�л���������
		setContentView(R.layout.main);	
			
		//��ʼ��GLSurfaceView
        mGLSurfaceView = new MySurfaceView(this);
        mGLSurfaceView.requestFocus();//��ȡ����
        mGLSurfaceView.setFocusableInTouchMode(true);//����Ϊ�ɴ���  
        //���Զ����GLSurfaceView��ӵ����LinearLayout��
        LinearLayout ll=(LinearLayout)findViewById(R.id.main_liner); 
        ll.addView(mGLSurfaceView);        
        
        //ΪRadioButton��Ӽ�����
        RadioButton rb=(RadioButton)findViewById(R.id.Radi01);
        rb.setOnCheckedChangeListener(
            new OnCheckedChangeListener()
            {
     			@Override
     			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) 
     			{
     				if(isChecked)
     				{
     					mGLSurfaceView.currenttexId32 = mGLSurfaceView.texId[0];
     					mGLSurfaceView.currenttexId256 = mGLSurfaceView.texId[4];
     				}
     			}        	   
            }         		
        );
        rb=(RadioButton)findViewById(R.id.Radi02);
        rb.setOnCheckedChangeListener(
            new OnCheckedChangeListener()
            {
     			@Override
     			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) 
     			{
     				if(isChecked)
     				{
     					mGLSurfaceView.currenttexId32 = mGLSurfaceView.texId[1];
     					mGLSurfaceView.currenttexId256 = mGLSurfaceView.texId[5];
     				}
     			}        	   
            }         		
        ); 
        rb=(RadioButton)findViewById(R.id.Radi03);
        rb.setOnCheckedChangeListener(
            new OnCheckedChangeListener()
            {
     			@Override
     			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) 
     			{
     				if(isChecked)
     				{
     					mGLSurfaceView.currenttexId32 = mGLSurfaceView.texId[2];
     					mGLSurfaceView.currenttexId256 = mGLSurfaceView.texId[6];
     				}
     			}        	   
            }         		
        );
        rb=(RadioButton)findViewById(R.id.Radi04);
        rb.setOnCheckedChangeListener(
            new OnCheckedChangeListener()
            {
     			@Override
     			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) 
     			{
     				if(isChecked)
     				{
     					mGLSurfaceView.currenttexId32 = mGLSurfaceView.texId[3];
     					mGLSurfaceView.currenttexId256 = mGLSurfaceView.texId[7];
     				}
     			}        	   
            }         		
        );
        
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
    }    
}



