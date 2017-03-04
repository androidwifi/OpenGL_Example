package com.bn.pp5;

import java.io.File;										//������ذ�
import java.io.FileInputStream;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Sample2_5_Activity extends Activity {			//����Activity   
    @Override
    public void onCreate(Bundle savedInstanceState) {		//��дonCreate����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);						//����layout        
        Button ok=(Button)this.findViewById(R.id.Button01);	//��ȡ�򿪰�ť����
        ok.setOnClickListener								//Ϊ�򿪰�ť��Ӽ�����
        (	new OnClickListener()
        	{	public void onClick(View v) 
				{	EditText et1=(EditText)findViewById(R.id.EditText01);
					//����loadText������ȡ��Ӧ�ļ������ļ�
					String nr=loadText(et1.getText().toString().trim());
					EditText et2=(EditText)findViewById(R.id.EditText02);
					//������ʾ������
					et2.setText(nr);
		}});}    
    public String loadText(String name)						//����SD���ļ�����
    {  	String nr=null;										//�����ַ���
    	try 
    	{	File f=new File("/sdcard/"+name);				//������Ӧ�ļ�
        	byte[] buff=new byte[(int) f.length()];			//������Ӧ��С��byte����
			FileInputStream fis=new FileInputStream(f);
			fis.read(buff);									//�����ļ�
			fis.close();									//�ر�������
			nr=new String(buff,"utf-8");					//ת�������ַ���
			nr=nr.replaceAll("\\r\\n","\n");} 				//�滻���з�
    	catch (Exception e) 
    	{	//û���ҵ��ļ���ʾ
    		Toast.makeText(getBaseContext(), "�Բ���û���ҵ�ָ���ļ���", Toast.LENGTH_LONG).show();	}
		return nr; 											//���������ַ���   	
    }}