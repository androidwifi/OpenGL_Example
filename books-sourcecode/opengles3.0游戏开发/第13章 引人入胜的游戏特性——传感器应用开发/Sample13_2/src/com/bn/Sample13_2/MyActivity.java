package com.bn.Sample13_2;

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
	Sensor myMagnetic; //Sensor��������
	TextView tvX;	//��ʾ�ų�ǿ����x���Ϸ�����TextView	
	TextView tvY;	//��ʾ�ų�ǿ����y���Ϸ�����TextView
	TextView tvZ;	//��ʾ�ų�ǿ����z���Ϸ�����TextView
	TextView info;	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//�л���������
        
        tvX = (TextView)findViewById(R.id.tvX);	//������ʾx�᷽��ų�ǿ��
        tvY = (TextView)findViewById(R.id.tvY);	//������ʾy�᷽��ų�ǿ��
        tvZ = (TextView)findViewById(R.id.tvZ); //������ʾz�᷽��ų�ǿ��
        info= (TextView)findViewById(R.id.info);//������ʾ�ֻ��дų��������������Ϣ
        //���SensorManager����
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	
        //������������Ϊ�ų�������
        myMagnetic=mySensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        
        StringBuffer strb=new StringBuffer();//����һ��StringBuffer 
        strb.append("\n����: ");
        strb.append(myMagnetic.getName());//��ȡ����������
        strb.append("\n�ĵ���(mA): ");
        strb.append(myMagnetic.getPower());//��ȡ�˴������ĺĵ������Ժ���(mA)Ϊ��λ
        strb.append("\n���ͱ�� : ");
        strb.append(myMagnetic.getType());//��ȡ���������ͱ��
        strb.append("\n������: ");
        strb.append(myMagnetic.getVendor());//��ȡ��������������
        strb.append("\n�汾: ");
        strb.append(myMagnetic.getVersion());//��ȡ�������汾
        strb.append("\n��������Χ: ");
        strb.append(myMagnetic.getMaximumRange());//��ȡ����������������Χ(����)
        
        info.setText(strb.toString());	//����Ϣ�ŵ���ʾ�õ�TextView��
    }
	private SensorEventListener mySensorListener = 
		new SensorEventListener(){//����ʵ����SensorEventListener�ӿڵĴ�����������
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy){}
		@Override
		public void onSensorChanged(SensorEvent event){
			float []values=event.values;//��ȡx��y��z�����᷽���ϵĴų�ǿ�ȷ���ֵ
			tvX.setText("x�᷽���ϵĴų�ǿ��Ϊ�� "+values[0]);		
			tvY.setText("y�᷽���ϵĴų�ǿ��Ϊ�� "+values[1]);		
			tvZ.setText("z�᷽���ϵĴų�ǿ��Ϊ�� "+values[2]);		
		}
	};
    @Override
	protected void onResume(){ //��дonResume����
		super.onResume();
		mySensorManager.registerListener(//ע�������
				mySensorListener, 		//����������
				myMagnetic, 			//�������Ĵ���������
				SensorManager.SENSOR_DELAY_NORMAL	//������������Ƶ��
		);
	}	
	@Override
	protected void onPause(){//��дonPause����	
		super.onPause();
		mySensorManager.unregisterListener(mySensorListener);//ע��������
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