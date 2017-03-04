package com.bn.pp4f;//������
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
public class ContentConsumerActivity extends Activity {
    ContentResolver cr;// ContentResolver������
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//�̳и����onCreate����
        setContentView(R.layout.main);//��ת��������
        cr=this.getContentResolver();//��ȡContentResolver�Ķ���
        //��ʼ����ѯ��ť
        Button b=(Button)this.findViewById(R.id.Button01);// Button�������
        b.setOnClickListener(//���ð�ť����
          new OnClickListener(){
			@Override
			public void onClick(View v) {//��дonClick����
				String stuname="Android";				
				Cursor cur=cr.query(//���ò�ѯ���ַ���
				   Uri.parse("content://com.bn.pp4.provider.student/stu"), 
				   new String[]{"sno","stuname","sage","sclass"}, 
				   "stuname=?", //��ѯ����
				   new String[]{stuname}, 
				   "sage ASC"
				);	
	        	while(cur.moveToNext()){
	        		String sno=cur.getString(0);//��ȡѧ��
	        		String sname=cur.getString(1);//��ȡ����
	        		int sage=cur.getInt(2);//��ȡ����
	        		String sclass=cur.getString(3);//��ȡ�༶
	        		appendMessage(sno+"\t"+sname+"\t\t"+sage+"\t"+sclass);
	        	}
	        	cur.close();//�ر�ContentResolver
	}});}
    public void appendMessage(String msg){    //���ı���������ı�
    	EditText et=(EditText)this.findViewById(R.id.EditText02);//��ȡEditText�Ķ���
    	et.append(msg+"\n");//�����ʾ���ַ���
}}