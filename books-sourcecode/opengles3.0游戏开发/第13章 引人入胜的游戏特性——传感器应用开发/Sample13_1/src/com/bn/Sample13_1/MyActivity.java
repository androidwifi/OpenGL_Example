package com.bn.Sample13_1;
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
	Sensor myAccelerometer; 	//Sensor��������
	TextView tvX;	//TextView��������	
	TextView tvY;	//TextView��������	
	TextView tvZ;	//TextView��������
	TextView info;	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//�л���������
        
        tvX = (TextView)findViewById(R.id.tvX);	//������ʾx�᷽����ٶ�
        tvY = (TextView)findViewById(R.id.tvY);	//������ʾy�᷽����ٶ�	
        tvZ = (TextView)findViewById(R.id.tvZ); //������ʾz�᷽����ٶ�
        info= (TextView)findViewById(R.id.info);//������ʾ�ֻ��м��ٶȴ������������Ϣ
        //���SensorManager����
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	
        //������������
        myAccelerometer=mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
       
        StringBuffer strb=new StringBuffer(); //����һ��StringBuffer
        strb.append("\n����: ");
        strb.append(myAccelerometer.getName());//��ȡ����������
        strb.append("\n�ĵ���(mA): ");
        strb.append(myAccelerometer.getPower());//��ȡ�˴������ĺĵ������Ժ���(mA)Ϊ��λ
        strb.append("\n���ͱ�� : ");
        strb.append(myAccelerometer.getType());//��ȡ���������ͱ��
        strb.append("\n������: ");
        strb.append(myAccelerometer.getVendor());//��ȡ��������������
        strb.append("\n�汾: ");
        strb.append(myAccelerometer.getVersion());//��ȡ�������汾
        strb.append("\n��������Χ: ");
        strb.append(myAccelerometer.getMaximumRange());//��ȡ����������������Χ(����)
        
        info.setText(strb.toString());	//����Ϣ�ַ���������Ϊinfo��TextView
    }
    @Override
	protected void onResume(){ //��дonResume����
		super.onResume();
		mySensorManager.registerListener(//ע�������
				mySensorListener, 		//����������
				myAccelerometer, 		//�������Ĵ���������
				SensorManager.SENSOR_DELAY_NORMAL	//������������Ƶ��
		);
	}	
	@Override
	protected void onPause(){//��дonPause����	
		super.onPause();
		mySensorManager.unregisterListener(mySensorListener);//ע��������
	}
	private SensorEventListener mySensorListener = 
		new SensorEventListener(){//����ʵ����SensorEventListener�ӿڵĴ�����������
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy){}
		@Override
		public void onSensorChanged(SensorEvent event){
			float []values=event.values;//��ȡ�����᷽���ϵļ��ٶ�ֵ
			tvX.setText("x�᷽���ϵļ��ٶ�Ϊ��"+values[0]);		//��ʾx�᷽���ϵļ��ٶ�ֵ
			tvY.setText("y�᷽���ϵļ��ٶ�Ϊ��"+values[1]);		//��ʾy�᷽���ϵļ��ٶ�ֵ
			tvZ.setText("z�᷽���ϵļ��ٶ�Ϊ��"+values[2]);		//��ʾz�᷽���ϵļ��ٶ�ֵ
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