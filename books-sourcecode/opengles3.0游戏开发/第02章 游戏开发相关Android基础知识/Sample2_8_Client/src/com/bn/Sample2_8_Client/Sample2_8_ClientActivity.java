package com.bn.Sample2_8_Client;//��������

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Sample2_8_ClientActivity extends Activity//�����̳�Activity����������
{
    @Override
    public void onCreate(Bundle savedInstanceState)//��д��onCreate����
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//��ת��������
        Button button=(Button)findViewById(R.id.button);//���Button��ť������
        button.setOnClickListener//ΪButton��ť��Ӽ�����
        (
        	new OnClickListener()
        	{
				public void onClick(View v)	//��д��onClick����
				{
					new Thread()
					{
						public void run()
						{
							connectServer();//����connectServer���ӷ�����
						}
					}.start();
					
				}
        	}
        );
    }
    //���ӷ������˵ķ���
    public void connectServer()//�Զ�������ӷ������ķ���
    {
    	
    	String serverIp="10.16.189.20";//������������IP
    	try
    	{
    		Socket socket=new Socket(serverIp,8877); //����Socket�׽��֣�������������
    		DataInputStream din=new DataInputStream(socket.getInputStream());
			DataOutputStream dout=new DataOutputStream(socket.getOutputStream());
			EditText et=(EditText)this.findViewById(R.id.et);//���EditText����Ի������
			String tempStr=et.getText().toString();//��ȡ�öԻ����е���Ϣ
			dout.writeUTF(tempStr);//����Ϣд�뵽�������
			TextView tv=(TextView)this.findViewById(R.id.tv);//���TextView�Ķ���
			tv.setText(din.readUTF());//���������е�������TextView����ʾ
			din.close();//�ر�������
			dout.close();//�ر���chu��
			socket.close();//�ر�Socket�׽���
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();//���񲢴�ӡ�쳣��Ϣ
    	}
    }
}