package com.bn.Sample13_8;

public class Cube 
{
	MySurfaceView mv;
	TextureRect[] rect=new TextureRect[6];
	float xAngle=0;//��x����ת�ĽǶ�
    float yAngle=0;//��y����ת�ĽǶ�
    float zAngle=0;//��z����ת�ĽǶ�
    float a;	//������ĳ�
    float b;	//������ĸ�
    float c;	//������Ŀ���ȣ�
    float size;//�ߴ�
	public Cube(MySurfaceView mv,float scale,float[] abc)
	{
		a=abc[0];
		b=abc[1];
		c=abc[2];
		rect[0]=new TextureRect(mv,scale,a,b);
		rect[1]=new TextureRect(mv,scale,a,b);
		rect[2]=new TextureRect(mv,scale,c,b);
		rect[3]=new TextureRect(mv,scale,c,b);
		rect[4]=new TextureRect(mv,scale,a,c);
		rect[5]=new TextureRect(mv,scale,a,c);
		// ��ʼ����ɺ��ٸı������ֵ
		size=scale;
		a*=size;
		b*=size;
		c*=size;
	}
	public void drawSelf(int ballTexId)
	{
		MatrixState.rotate(xAngle, 1, 0, 0);
		MatrixState.rotate(yAngle, 0, 1, 0);
        MatrixState.rotate(zAngle, 0, 0, 1);
        //ǰ��
        MatrixState.pushMatrix();
        MatrixState.translate(0, 0, c/2);
		rect[0].drawSelf(ballTexId);
        MatrixState.popMatrix();
		//����
        MatrixState.pushMatrix();
        MatrixState.translate(0, 0, -c/2);
		MatrixState.rotate(180.0f, 0, 1, 0);
		rect[1].drawSelf(ballTexId);
        MatrixState.popMatrix();
		//����
        MatrixState.pushMatrix();
        MatrixState.translate(a/2, 0, 0);
		MatrixState.rotate(90.0f, 0, 1, 0);
		rect[2].drawSelf(ballTexId);
        MatrixState.popMatrix();
		//����
        MatrixState.pushMatrix();
        MatrixState.translate(-a/2, 0, 0);
		MatrixState.rotate(-90.0f, 0, 1, 0);
		rect[3].drawSelf(ballTexId);
        MatrixState.popMatrix();
		//����
        MatrixState.pushMatrix();
        MatrixState.translate(0, -b/2, 0);
		MatrixState.rotate(90.0f, 1, 0, 0);
		rect[4].drawSelf(ballTexId);
        MatrixState.popMatrix();
		//����
        MatrixState.pushMatrix();
        MatrixState.translate(0, b/2, 0);
		MatrixState.rotate(-90.0f, 1, 0, 0);
		rect[5].drawSelf(ballTexId);
        MatrixState.popMatrix();
	}
}
