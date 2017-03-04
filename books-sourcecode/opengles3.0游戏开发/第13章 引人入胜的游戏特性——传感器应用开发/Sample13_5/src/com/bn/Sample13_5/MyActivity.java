package com.bn.Sample13_5;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

public class MyActivity extends Activity {
	SensorManager mySensorManager; // SensorManager��������
	Sensor myTemperature; // ����������
	TextView temperature; // TextView��������
	TextView info;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);//�л���������
		temperature = (TextView) findViewById(R.id.temperature); //������ʾ�¶ȵ�TextView
		info = (TextView) findViewById(R.id.info);//������ʾ�¶ȴ�����������Ϣ��TextView
		// ���SensorManager����
		mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		//��ȡ������
		myTemperature = mySensorManager
				.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
		// ����һ��StringBuffer
		StringBuffer strb = new StringBuffer();
		strb.append("\n����: ");
		strb.append(myTemperature.getName());
		strb.append("\n�ĵ���(mA): ");
		strb.append(myTemperature.getPower());
		strb.append("\n���ͱ�� : ");
		strb.append(myTemperature.getType());
		strb.append("\n������: ");
		strb.append(myTemperature.getVendor());
		strb.append("\n�汾: ");
		strb.append(myTemperature.getVersion());
		strb.append("\n��������Χ: ");
		strb.append(myTemperature.getMaximumRange());
		info.setText(strb.toString()); // ����Ϣ�ַ���������Ϊinfo��TextView
	}

	@Override
	protected void onResume() { // ��дonResume����
		super.onResume();
		mySensorManager.registerListener(mySensorListener, // ��Ӽ���
				myTemperature, // ����������
				SensorManager.SENSOR_DELAY_NORMAL // �������¼����ݵ�Ƶ��
				);
	}

	@Override
	protected void onPause() {// ��дonPause����
		super.onPause();
		mySensorManager.unregisterListener(mySensorListener);// ȡ��ע�������
	}
	//ʵ����SensorEventListener�ӿڵĴ�����������
	private SensorEventListener mySensorListener = new SensorEventListener() {
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {//��дonAccuracyChanged����
		}

		@Override
		public void onSensorChanged(SensorEvent event) {//��дonSensorChanged����
			float[] values = event.values;//��ȡ�¶�ֵ������values����
			temperature.setText("�¶�Ϊ��" + values[0]);//��ʾ�¶�
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent e) {
		switch (keyCode) {
		case 4:
			System.exit(0);
			break;
		}
		return true;
	}

}