package com.bn.Sample3_1;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class Sample3_1Activity extends Activity//�����̳�Activity����������
{
	MyTDView mview;//����MyTDView�������
    @Override
    public void onCreate(Bundle savedInstanceState)//�̳�Activity����д�ķ���
    {
        super.onCreate(savedInstanceState);//���ø���
        //����Ϊ����ģʽ
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mview=new MyTDView(this);//����MyTDView��Ķ���
        mview.requestFocus();//��ȡ����
        mview.setFocusableInTouchMode(true);//����Ϊ�ɴ���
        setContentView(mview);
    }
    @Override
    public void onResume()//�̳�Activity����д��onResume����
    {
    	super.onResume();
    	mview.onResume();//ͨ��MyTDView��Ķ������onResume����
    }
    @Override
    public void onPause()//�̳�Activity����д��onPause����
    {
    	super.onPause();
    	mview.onPause();//ͨ��MyTDView��Ķ������onPause����
    }
}