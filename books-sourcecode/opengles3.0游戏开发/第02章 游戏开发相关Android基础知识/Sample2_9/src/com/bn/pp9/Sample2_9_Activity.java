package com.bn.pp9;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * ������ʾ�Ի�����Activity
 */
public class Sample2_9_Activity extends Activity {
	private EditText outEt;// �����еĿؼ�����
	private Button sendBtn;
	private String connectedNameStr = null;// �����ӵ��豸����
	private StringBuffer outSb;// ���͵��ַ���Ϣ
	private BluetoothAdapter btAdapter = null;// ��������������
	private MyService myService = null;// Service����
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// ��ȡ��������������
		btAdapter = BluetoothAdapter.getDefaultAdapter();
	}
	@Override
	public void onStart() {
		super.onStart();
		// �������û�п�������ʾ�������������˳�Activity
		if (!btAdapter.isEnabled()) {
			Toast.makeText(this, "���ȿ���������", Toast.LENGTH_LONG).show();
			finish();
		} else {// �����ʼ������Ŀؼ�
			if (myService == null)
				initChat();
		}
	}
	@Override
	public synchronized void onResume() {
		super.onResume();		
		if (myService != null) {// ����������Service
			// ���ServiceΪ��״̬
			if (myService.getState() == MyService.STATE_NONE) {
				myService.start();// ����Service
			}
		}
	}
	private void initChat() {
		outEt = (EditText) findViewById(R.id.edit_text_out);// ��ȡ�༭�ı��������
		// ��ȡ���Ͱ�ť���ã���Ϊ����Ӽ���
		sendBtn = (Button) findViewById(R.id.button_send);
		sendBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// ��ȡ�༭�ı����е��ı����ݣ���������Ϣ
				TextView view = (TextView) findViewById(R.id.edit_text_out);
				String message = view.getText().toString();
				sendMessage(message);
			}
		});
		myService = new MyService(this, mHandler);// ����Service����
		// ��ʼ���洢������Ϣ��StringBuffer
		outSb = new StringBuffer("");
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (myService != null) {// ֹͣService
			myService.stop();
		}
	}
	// ������Ϣ�ķ���
	private void sendMessage(String message) {
		// �ȼ���Ƿ��Ѿ����ӵ��豸
		if (myService.getState() != MyService.STATE_CONNECTED) {
			Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT)
					.show();
			return;
		}
		if (message.length() > 0) {// �����Ϣ��Ϊ���ٷ�����Ϣ
			byte[] send = message.getBytes();// ��ȡ������Ϣ���ֽ����飬������
			myService.write(send);
			// ����StringBuffer�ͱ༭�ı��������
			outSb.setLength(0);
			outEt.setText(outSb);
		}
	}
	// �����Service��������Ϣ��Handler
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constant.MSG_READ:
				byte[] readBuf = (byte[]) msg.obj;
				// ����Ҫ���͵���Ϣ���ַ���
				String readMessage = new String(readBuf, 0, msg.arg1);
				Toast.makeText(Sample2_9_Activity.this,
						connectedNameStr + ":  " + readMessage,
						Toast.LENGTH_LONG).show();
				break;
			case Constant.MSG_DEVICE_NAME:
				// ��ȡ�����ӵ��豸���ƣ���������ʾ��Ϣ
				connectedNameStr = msg.getData().getString(
						Constant.DEVICE_NAME);
				Toast.makeText(getApplicationContext(),
						"�����ӵ� " + connectedNameStr, Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}
	};
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			// ����豸�б�Activity����һ�����ӵ��豸
			if (resultCode == Activity.RESULT_OK) {
				// ��ȡ�豸��MAC��ַ
				String address = data.getExtras().getString(
						MyDeviceListActivity.EXTRA_DEVICE_ADDR);
				// ��ȡBLuetoothDevice����
				BluetoothDevice device = btAdapter
						.getRemoteDevice(address);
				myService.connect(device);// ���Ӹ��豸
			}
			break;
		}
	}
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// �����豸�б�Activity�����豸
		Intent serverIntent = new Intent(this, MyDeviceListActivity.class);
		startActivityForResult(serverIntent, 1);
		return true;
	}
}