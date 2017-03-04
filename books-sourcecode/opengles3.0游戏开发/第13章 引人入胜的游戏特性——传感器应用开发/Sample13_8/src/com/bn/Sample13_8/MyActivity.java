package com.bn.Sample13_8;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class MyActivity extends Activity {
	//SensorManager��������
	SensorManager mySensorManager;		
	Sensor sensorAccelerometer;//���ٶȴ�����������
	MySurfaceView mySurfaceView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);		
		//����Ϊ��ģʽ
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		//���SensorManager����
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensorAccelerometer=mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);       
        
        mySurfaceView = new MySurfaceView(this);
        this.setContentView(mySurfaceView);       
        //��ȡ����
        mySurfaceView.requestFocus();
        //����Ϊ�ɴ���
        mySurfaceView.setFocusableInTouchMode(true);
    }
    

  //�����������ļ�����
  	private SensorEventListener mek=new SensorEventListener()
  	{
  		@Override
  		public void onAccuracyChanged(Sensor sensor, int accuracy) {}

  		@Override
  		public void onSensorChanged(SensorEvent event) 
  		{			
  			//��ȡ�������ٶ�����Ļ�ϵ�XY����
  			float gx=event.values[0];
  			float gy=event.values[1];

  			//�����Ļ���������ٶ������ķ�������
  			double mLength=gx*gx+gy*gy;
  			mLength=Math.sqrt(mLength);
  			//������Ϊ0�򷵻�
  			if(mLength==0)
  			{
  				return;
  			}
  			//��������Ϊ0������������Ĳ���
  			Constant.SPANX=(float)((gy/mLength)*0.08);
  			Constant.SPANZ=(float)((gx/mLength)*0.08);				
  		}		
  	};	

	@Override
	protected void onResume() {						//��дonResume����
		mySensorManager.registerListener
		(mek, sensorAccelerometer, SensorManager.SENSOR_DELAY_UI);
		super.onResume();
	}
	@Override
	protected void onPause() {									//��дonPause����
		mySensorManager.unregisterListener(mek);
		super.onPause();
	}
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent e)
	{
		switch(keyCode)
	    	{
		case 4:
			System.exit(0);
			break;
	    	}
		return true;
	}
}