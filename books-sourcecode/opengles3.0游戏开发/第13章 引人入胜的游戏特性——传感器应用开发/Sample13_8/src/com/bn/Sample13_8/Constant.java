package com.bn.Sample13_8;
//������
public class Constant 
{
	public static final float  SCALE=1;	
	
	//�ƽ𳤷��γ��ߵ�һ��
	public static final float AHALF=1;
	
	//����ľ�鰼�۵�����
	public static final float CUBE_LENGTH=18;	//����ƽ��ĳ���
	public static final float CUBE_HEIGHT=1;	//ǽ�ĸ߶�
	public static final float CUBE_WIDTH=12;	//����ƽ��Ŀ��
	public static final float WALL_WIDTH=1f;	//ǽ�ĺ��
	//----------------------------���ݸ�������-----------------------------------------
	
	public static float D3_CUBE_LENGTH=CUBE_LENGTH*SCALE;	//��ά�ռ��е�����ε���������
	public static float D3_CUBE_WIDTH=CUBE_WIDTH*SCALE;	//��ά�ռ��е�����ε��������
	public static float D3_WALL_WIDTH=WALL_WIDTH*SCALE;	//��ά�ռ���ǽ���������
	public static float BALLR=(float) Math.sqrt(SCALE*AHALF*SCALE*AHALF+SCALE*AHALF*0.618034f*SCALE*AHALF*0.618034f);		//��İ뾶
	
	//���氼�۵ı߽�,��Ϊ��ֵ
	public static float XBOUNDARY=D3_CUBE_LENGTH/2-D3_WALL_WIDTH-BALLR;	//x�����ϵı߽�
	public static float ZBOUNDARY=D3_CUBE_WIDTH/2-D3_WALL_WIDTH-BALLR;	//z�����ϵı߽�
	
	
	public static float XOFFSET=0;	//��λ������
	public static float ZOFFSET=0;
	
	public static float SPANX=0;	//��Ĳ���
	public static float SPANZ=0;
}
