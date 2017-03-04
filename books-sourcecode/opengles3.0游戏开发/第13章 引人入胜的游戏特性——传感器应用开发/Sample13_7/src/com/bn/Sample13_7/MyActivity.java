package com.bn.Sample13_7;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
public class MyActivity extends Activity {
	SensorManager mySensorManager;	//SensorManager��������	
	Sensor myAccelerometer; 	//����������
	Sensor myMagnetic; 	//����������
	TextView tYaw;	 //TextView��������	
	TextView tPitch; //TextView��������	
	TextView tRoll;	 //TextView��������
	float []vlAccelerometer=new float[3];
	float []vlManager=new float[3];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tYaw = (TextView)findViewById(R.id.tYaw);	//������ʾYaw��ת�Ƕ�
        tPitch = (TextView)findViewById(R.id.tPicth);	//������ʾPitch��ת�Ƕ�
        tRoll = (TextView)findViewById(R.id.tRoll); //������ʾRoll��ת�Ƕ�
        //���SensorManager����
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	
        //������������
        myAccelerometer=mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        myMagnetic=mySensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        }
    @Override
	protected void onResume(){ //��дonResume����
		super.onResume();
		mySensorManager.registerListener(
				myAccelerometerListener, 		//Ϊ������������Ӽ���
				myAccelerometer, 		//����������
				SensorManager.SENSOR_DELAY_NORMAL	//�������¼����ݵ�Ƶ��
		);
		mySensorManager.registerListener(
				myMagneticListener, 		//Ϊ�ų���������Ӽ���
				myMagnetic, 		//����������
				SensorManager.SENSOR_DELAY_NORMAL	//�������¼����ݵ�Ƶ��
		);
	}	
	@Override
	protected void onPause(){//��дonPause����	
		super.onPause();
		mySensorManager.unregisterListener(myAccelerometerListener);//ȡ��ע�������
		mySensorManager.unregisterListener(myMagneticListener);//ȡ��ע�������
		
	}
	private SensorEventListener myAccelerometerListener = 
		new SensorEventListener(){//����ʵ����SensorEventListener�ӿڵĴ�����������
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy){}
		@Override
		public void onSensorChanged(SensorEvent event){
			vlAccelerometer=event.values;//��ȡ�����᷽���ϵļ��ٶ�ֵ
            //������ת����
		  float[] R=new float[9];
		  //��ȡ��ת����ĸ���ֵ
		  SensorManager.getRotationMatrix
		  (
			R, 
			null, 
			vlAccelerometer, 
			vlManager
	      );
		//��ֵ̬����
		float[] Values=new float[3];
		//��ȡ��ֵ̬
		SensorManager.getOrientation(R, Values);
		  tYaw.setText(  "Yaw�����ת�Ƕȣ�"+Values[0]);		
		  tPitch.setText("Picth�����ת�Ƕȣ�"+Values[1]);		
		  tRoll.setText( "Roll�����ת�Ƕȣ�"+Values[2]);		
		}
	};
	private SensorEventListener myMagneticListener=new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			vlManager=event.values;//��ȡ�����᷽���ϵĴų�ֵ
            //������ת����
		  float[] R=new float[9];
		//��ȡ��ת����ĸ���ֵ
		  SensorManager.getRotationMatrix
		  (
			R, 
			null, 
			vlAccelerometer, 
			vlManager
	      );
		//��ֵ̬����
		float[] Values=new float[3];
		//��ȡ��ֵ̬
		SensorManager.getOrientation(R, Values);
		  tYaw.setText(  "Yaw�����ת�Ƕȣ�"+Values[0]);		
		  tPitch.setText("Pitch�����ת�Ƕȣ�"+Values[1]);		
		  tRoll.setText( "Roll�����ת�Ƕȣ�"+Values[2]);	
		}
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
		}
	};
	
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