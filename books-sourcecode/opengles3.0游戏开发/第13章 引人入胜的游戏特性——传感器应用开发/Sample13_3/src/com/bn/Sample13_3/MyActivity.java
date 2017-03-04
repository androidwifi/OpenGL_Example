package com.bn.Sample13_3;
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
	Sensor myGyroscope; 	// Sensor��������
	TextView tvX;	//������ʾx����ٶȵ�TextView
	TextView tvY;	//������ʾy����ٶȵ�TextView
	TextView tvZ;	//������ʾz����ٶȵ�TextView
	TextView info;	//��ʾ������������Ϣ��TextView
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tvX = (TextView)findViewById(R.id.tvX);	//������ʾx����ٶ�
        tvY = (TextView)findViewById(R.id.tvY);	//������ʾy����ٶ�	
        tvZ = (TextView)findViewById(R.id.tvZ); //������ʾz����ٶ�
        info= (TextView)findViewById(R.id.info);//������ʾ�����Ǵ�������������Ϣ
        //���SensorManager����
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	
        //������������
        myGyroscope=mySensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        

        StringBuffer strb=new StringBuffer();//����һ��StringBuffer
        strb.append("\n����: ");
        strb.append(myGyroscope.getName());	//��ȡ����������
        strb.append("\n�ĵ���(mA): ");
        strb.append(myGyroscope.getPower());//��ȡ�˴������ĺĵ������Ժ���(mA)Ϊ��λ
        strb.append("\n���ͱ�� : ");
        strb.append(myGyroscope.getType());//��ȡ���������ͱ��
        strb.append("\n������: ");
        strb.append(myGyroscope.getVendor());//��ȡ��������������
        strb.append("\n�汾: ");
        strb.append(myGyroscope.getVersion());//��ȡ�������汾
        strb.append("\n��������Χ: ");
        strb.append(myGyroscope.getMaximumRange());	//��ȡ����������������Χ(����)
        
        info.setText(strb.toString());	//����Ϣ�ŵ���ʾ�õ�TextView��
    }
    @Override
	protected void onResume(){ //��дonResume����
		super.onResume();
		mySensorManager.registerListener(//ע�������
				mySensorListener, 		//����������
				myGyroscope, 			//�������Ĵ���������
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
			float []values=event.values;//��ȡ�����᷽���ϵĽ��ٶ�ֵ
			tvX.setText("x��Ľ��ٶ�Ϊ��"+values[0]);		
			tvY.setText("y��Ľ��ٶ�Ϊ��"+values[1]);		
			tvZ.setText("z��Ľ��ٶ�Ϊ��"+values[2]);		
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