package com.bn.Sample13_9;

public class CubeGroup {
	
	MySurfaceView mv;
	
	TextureRect textureRect;//�ײ�������
	Cube sideCube1;//���Ҳ�������
	Cube sideCube2;//ǰ���������
	float size;//�ߴ�
	float a;
	float b;
	float c;
	float width;
	public CubeGroup(MySurfaceView mv,
			float scale, 		//����
			float a, 			//����ƽ�泤��
			float b, 			//ǽ�ĸ߶�
			float c ,			//����ƽ����
			float width			//ǽ�ĺ��
		){
		//����������ɲ��ֵĶ���
		textureRect = new TextureRect(mv,scale,a,c);//������
		sideCube1 = new Cube(mv, scale, new float[]{c, b, width});//������
		sideCube2 = new Cube(mv, scale, new float[]{a-2*width, b, width});//������
		// ��ʼ����ɺ��ٸı������ֵ
		size = scale;
		a *= size; 
		b *= size;
		c *= size;
		width *= size;
		//��ʼ����Ա������ֵ
		this.a = a;
		this.b = b;
		this.c = c;
		this.width = width;
	}
	public void drawSelf(int floorTexId,int wallTexId){
		//�ײ�
        MatrixState.pushMatrix();
        MatrixState.rotate(-90, 1, 0, 0);
        textureRect.drawSelf(floorTexId);
        MatrixState.popMatrix();
		//���Ҳ�
        MatrixState.pushMatrix();
        MatrixState.translate(-(a - width)/2,b/2, 0);
        MatrixState.rotate(90, 0, 1, 0);
        sideCube1.drawSelf(wallTexId);
        MatrixState.popMatrix();
        
        MatrixState.pushMatrix();
        MatrixState.translate((a - width)/2,b/2, 0);
        MatrixState.rotate(90, 0, 1, 0);
        sideCube1.drawSelf(wallTexId);
        MatrixState.popMatrix();
		//ǰ���
        MatrixState.pushMatrix();
        MatrixState.translate(0,b/2, (c - width)/2);
        sideCube2.drawSelf(wallTexId);
        MatrixState.popMatrix();
        
        MatrixState.pushMatrix();
        MatrixState.translate(0,b/2, -(c - width)/2);
        sideCube2.drawSelf(wallTexId);
        MatrixState.popMatrix();
	}
}
