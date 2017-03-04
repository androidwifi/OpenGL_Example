package com.bn.sample14_1;

import android.app.Activity;
import android.os.Bundle;
//�����̳�Activity��������Sample14_1Activity
public class Sample14_1Activity extends Activity {

    GL2JNIView mView;

    @Override protected void onCreate(Bundle icicle) {//�̳�Activity����д��onCreate����
        super.onCreate(icicle);
        GL2JNILib.nativeSetAssetManager(this.getAssets()); //��AssetManager����C++
        mView = new GL2JNIView(getApplication());
		mView.requestFocus();					//��ȡ����
		mView.setFocusableInTouchMode(true); 	//����Ϊ�ɴ���
        setContentView(mView);//��ת����ؽ���
    }

    @Override protected void onPause() {//�̳�Activity����д��onPause����
        super.onPause();
        mView.onPause();//����GL2JNIView������onPause����
    }

    @Override protected void onResume() {//�̳�Activity����д��onResume����
        super.onResume();
        mView.onResume();//����GL2JNIView������onResume����
    }
}
