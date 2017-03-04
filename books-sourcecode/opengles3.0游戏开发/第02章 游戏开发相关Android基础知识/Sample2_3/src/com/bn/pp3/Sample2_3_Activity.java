package com.bn.pp3;

import java.util.Date;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class Sample2_3_Activity extends Activity {
   @SuppressWarnings("deprecation")
@Override
    public void onCreate(Bundle savedInstanceState) {	//��дonCreate����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);					//����layout
        //��ȡSharedPreferences���ã��洢����Ϊbn.xml����дģʽΪ˽��
        SharedPreferences sp=this.getSharedPreferences("bn", Context.MODE_PRIVATE);        
        String lastLoginTime=sp.getString("time", null);//��ȡ��Ϊ��time����ֵ       
        if(lastLoginTime==null)			//��ֵΪ�գ���Ϊ��һ�ε�¼������
        {  	lastLoginTime="�û����ã���ӭ����һ�ι��ٱ������";    }
        else							//��Ϊ�գ����޸��ַ���Ϊ�ϴε�¼ʱ��
        {  	lastLoginTime="�û����ã����ϴν���ʱ��Ϊ:"+lastLoginTime;    }        
        SharedPreferences.Editor editor=sp.edit();	//ȡ�ñ༭�������޸�Preferences�ļ�
        editor.putString("time", new Date().toLocaleString());	//�޸ļ�Ϊ��time����ֵΪ��ǰʱ��
        editor.commit();	//�ύ�޸�        
        TextView tv=(TextView)this.findViewById(R.id.TextView01);	//��ȡ������ʾ��TextView
        tv.setText(lastLoginTime);	//������ʾ���ַ���
}}