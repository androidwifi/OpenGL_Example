package com.bn.sample14_1;//��������

import android.content.Context;//����������
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

class GL2JNIView extends GLSurfaceView 
{
    Renderer renderer;//�Զ�����Ⱦ��������

    public GL2JNIView(Context context) //������
    {
        super(context);
		this.setEGLContextClientVersion(3);//ʹ��OpenGL ES 3.0�����øò���Ϊ3
		renderer=new Renderer();//����Renderer��Ķ���
		this.setRenderer(renderer);	//������Ⱦ��
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    private static class Renderer implements GLSurfaceView.Renderer 
    {
        public void onDrawFrame(GL10 gl) 
        {
            GL2JNILib.step();//���ñ��ط���ˢ�³���
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) 
        {
            GL2JNILib.init(width, height);//���ñ��ط�����ʼ��
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) 
        {
        	
        }
    }
}
