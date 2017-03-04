package com.bn.Sample13_6;

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
	Sensor myIsNear; 	//����������
	TextView distance;	//TextView��������	
	TextView info;	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        distance = (TextView)findViewById(R.id.distance);	//������ʾ�����TextView
        info= (TextView)findViewById(R.id.info);//������ʾ�ֻ��о��봫�����������Ϣ
        
        //���SensorManager����
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	
        //������������Ϊ���봫����
        myIsNear=mySensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        
        //����һ��StringBuffer
        StringBuffer strb=new StringBuffer();
        strb.append("\n����: ");
        strb.append(myIsNear.getName());
        strb.append("\n�ĵ���(mA): ");
        strb.append(myIsNear.getPower());
        strb.append("\n���ͱ��  : ");
        strb.append(myIsNear.getType());
        strb.append("\n������: ");  
        strb.append(myIsNear.getVendor());
        strb.append("\n�汾: ");
        strb.append(myIsNear.getVersion());
        strb.append("\n��������Χ: ");
        strb.append(myIsNear.getMaximumRange());
        
        info.setText(strb.toString());	//����Ϣ�ַ���������Ϊinfo��TextView
    }
    @Override
	protected void onResume(){ //��дonResume����
		super.onResume();
		mySensorManager.registerListener(
				mySensorListener, 		//��Ӽ���
				myIsNear, 		//����������
				SensorManager.SENSOR_DELAY_NORMAL	//�������¼����ݵ�Ƶ��
		);
	}	
	@Override
	protected void onPause(){//��дonPause����	
		super.onPause();
		mySensorManager.unregisterListener(mySensorListener);//ȡ��ע�������
	}
	private SensorEventListener mySensorListener = 
		new SensorEventListener(){//����ʵ����SensorEventListener�ӿڵĴ�����������
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy){}//��дonSensorChanged����
		@Override
		public void onSensorChanged(SensorEvent event){
			float []values=event.values;//��ȡ����ֵ������values����
			distance.setText("����Ϊ��"+values[0]);//��ʾ����			
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