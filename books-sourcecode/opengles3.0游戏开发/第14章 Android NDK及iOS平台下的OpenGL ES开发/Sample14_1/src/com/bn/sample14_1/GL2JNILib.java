package com.bn.sample14_1;//��������

import android.content.res.AssetManager;

public class GL2JNILib 
{
     static 
     {
         System.loadLibrary("gl2jni");//����so��̬��
     }
     public static native void init(int width, int height);//���س�ʼ������
     public static native void step();//����ˢ�³�������
     public static native void nativeSetAssetManager(AssetManager am); 	//��AssetManager����C++�ķ���
}
