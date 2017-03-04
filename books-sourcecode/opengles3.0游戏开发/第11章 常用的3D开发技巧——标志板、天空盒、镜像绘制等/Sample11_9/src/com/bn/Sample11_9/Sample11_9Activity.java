package com.bn.Sample11_9;

import android.app.Activity;
import android.os.Bundle;


public class Sample11_9Activity extends Activity
{
	MySurfaceView mview;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        mview = new MySurfaceView(this);
        mview.requestFocus();//��ȡ����
        mview.setFocusableInTouchMode(true);//����Ϊ�ɴ���  
        setContentView(mview);
        
    }
    @Override
    protected void onResume() {
        super.onResume();
        mview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mview.onPause();
    }    
}