package com.bn.sample14_2;

import android.content.res.AssetManager;
import android.opengl.GLSurfaceView;

public class GL2JNILib {
     static {
         System.loadLibrary("gl2jni");//����so��̬��
     }
     public static native void init(GLSurfaceView gsv,int width, int height);//���س�ʼ������
     public static native void step();//����ˢ�³�������
     public static native void setCamera(    		
    		float cx,	//�����λ��x
     		float cy,   //�����λ��y
     		float cz,   //�����λ��z
     		float tx,   //�����Ŀ���x
     		float ty,   //�����Ŀ���y
     		float tz,   //�����Ŀ���z
     		float upx,  //�����UP����X����
     		float upy,  //�����UP����Y����
     		float upz   //�����UP����Z����		
     		);
     //��AssetManager����C++�ķ���
     public static native void nativeSetAssetManager(AssetManager am); 
}
