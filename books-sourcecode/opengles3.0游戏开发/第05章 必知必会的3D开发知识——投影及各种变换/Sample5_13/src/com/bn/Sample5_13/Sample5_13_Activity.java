package com.bn.Sample5_13;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import static com.bn.Sample5_13.Constant.*;

public class Sample5_13_Activity extends Activity {
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
 		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
 		//�л���������
 		setContentView(R.layout.main);		
 		//��ʼ��GLSurfaceView
         mGLSurfaceView = new MySurfaceView(this);
         mGLSurfaceView.requestFocus();//��ȡ����
         mGLSurfaceView.setFocusableInTouchMode(true);//����Ϊ�ɴ���  
         //���Զ����GLSurfaceView��ӵ����LinearLayout��
         LinearLayout ll=(LinearLayout)findViewById(R.id.main_liner); 
         ll.addView(mGLSurfaceView);        
         //�����Ƿ�򿪱�����õ�ToggleButton
         ToggleButton tb=(ToggleButton)this.findViewById(R.id.ToggleButton01);
         tb.setOnCheckedChangeListener(
             new OnCheckedChangeListener()
             {
 	 			@Override
 	 			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) 
 	 			{
 	 				if(isChecked)
 	 				{
 	 				    //�ӽǲ����ʵ��±��ε����
 	 		            //���ô˷����������͸��ͶӰ����
 	 		            MatrixState.setProjectFrustum(-ratio*0.7f, ratio*0.7f, -0.7f, 0.7f, 1, 10);
 	 		            //���ô˷�������������۲����
 	 		            MatrixState.setCamera(0,0.5f,4,0f,0f,0f,0f,1.0f,0.0f);
 	 				}
 	 				else
 	 				{
	 	 	             //�ӽǺ��ʲ����ε����
	 	 	             //���ô˷����������͸��ͶӰ����
	 	 	             MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 20, 100);
	 	 	             //���ô˷�������������۲����
	 	 	             MatrixState.setCamera(0,8f,45,0f,0f,0f,0f,1.0f,0.0f);
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