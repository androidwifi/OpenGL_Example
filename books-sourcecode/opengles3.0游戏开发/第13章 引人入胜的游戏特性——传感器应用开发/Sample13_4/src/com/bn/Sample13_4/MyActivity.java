package com.bn.Sample13_4;
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
	Sensor myLight; 	// Sensor��������
	TextView light;	//TextView��������	
	TextView info;	
    @Override
    public void onCreate(Bundle savedInstanceState) {//��д��onCreate����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); //�л���������
        light = (TextView)findViewById(R.id.light);	//������ʾ��ǿ�ȵ�TextView
        info= (TextView)findViewById(R.id.info);//������ʾ�⴫����������Ϣ��TextView
        //���SensorManager����
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	
        //������������Ϊ�⴫����
        myLight=mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        
        StringBuffer strb=new StringBuffer();//����һ��StringBuffer
        strb.append("\n����: ");
        strb.append(myLight.getName());//��ȡ����������
        strb.append("\n�ĵ���(mA) : ");
        strb.append(myLight.getPower());//��ȡ�˴������ĺĵ������Ժ���(mA)Ϊ��λ
        strb.append("\n���ͱ��  : ");
        strb.append(myLight.getType());//��ȡ���������ͱ��
        strb.append("\n������: ");
        strb.append(myLight.getVendor());//��ȡ��������������
        strb.append("\n�汾: ");
        strb.append(myLight.getVersion());//��ȡ�������汾
        strb.append("\n��������Χ: ");
        strb.append(myLight.getMaximumRange());//��ȡ����������������Χ(����)
        info.setText(strb.toString());	//����Ϣ�ŵ���ʾ�õ�TextView��
    }
    @Override
	protected void onResume(){ //��дonResume����
		super.onResume();
		mySensorManager.registerListener(//ע�������
				mySensorListener, 		//����������
				myLight, 	//�������Ĵ���������
				SensorManager.SENSOR_DELAY_NORMAL//������������Ƶ��
		);
	}	
	@Override
	protected void onPause(){//��дonPause����	
		super.onPause();
		mySensorManager.unregisterListener(mySensorListener);//ע��������
	}
	private SensorEventListener mySensorListener = 
		new SensorEventListener(){//ʵ����SensorEventListener�ӿڵĴ�����������
		@Override//��дonAccuracyChanged����
		public void onAccuracyChanged(Sensor sensor, int accuracy){}
		@Override	//��дonSensorChanged����
		public void onSensorChanged(SensorEvent event){
			float []values=event.values;//��ȡ����ǿ��ֵ������values����
			light.setText("���ǿ��Ϊ��"+values[0]);	//��ʾ����ǿ��		
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