package com.bn.pp7;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Sample2_7_Activity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {		//��дonCreate����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);						//��ת��������        
        Button ok=(Button)this.findViewById(R.id.Button01);	//��ȡ��Button
        ok.setOnClickListener								//Ϊ�򿪰�ť��Ӽ�����
        (   new OnClickListener()
        {	public void onClick(View v) 
			{
				EditText et1=(EditText)findViewById(R.id.EditText01);
				//����loadText������ȡ��Ӧ�ļ������ļ�
				String nr=loadText(et1.getText().toString().trim());
				EditText et2=(EditText)findViewById(R.id.EditText02);
				//������ʾ������
				et2.setText(nr);
			}});}    
    public String loadText(String name)						//����assets�ļ�����
    { 	String nr=null;    									//�����ַ���	
    	try 
    	{	//�򿪶�Ӧ�����ļ���������
    		InputStream is=this.getResources().getAssets().open(name);
    		int ch=0;										
    		//�����ֽ����������
    		ByteArrayOutputStream baos=new ByteArrayOutputStream();
    		while((ch=is.read())!=-1)
    		{	baos.write(ch);		}						//��ȡ�ļ�
    		byte[] buff=baos.toByteArray();					//ת��Ϊ�ֽ�����
    		baos.close();									//�ر����������
    		is.close();										//�ر����������
			nr=new String(buff,"utf-8");					//ת���������ַ���
			nr=nr.replaceAll("\\r\\n","\n");				//�滻���з��ȿհ��ַ�
		} catch (Exception e) 
    	{	//û���ҵ���Ӧ�ļ���������ʾ
			Toast.makeText(getBaseContext(), "�Բ���û���ҵ�ָ���ļ���", Toast.LENGTH_LONG).show();	}    	
		return nr;    										//���������ַ���	
}}