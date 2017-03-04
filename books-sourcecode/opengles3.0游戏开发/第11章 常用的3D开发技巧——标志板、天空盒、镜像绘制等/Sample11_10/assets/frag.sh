#version 300 es   
precision mediump float;//����Ĭ�ϵĸ��㾫��
uniform sampler2D sTexture;//������������
in float vEdge;//���ϵ��
in vec2 vTextureCoord;//��������
out vec4 fragColor;//�������ƬԪ��ɫ

void main()                         
{
   //�������в�������ɫֵ            
   vec4 finalColor=texture(sTexture, vTextureCoord);
   //��ߵ���ɫ
   const vec4 edgeColor=vec4(0.0);
   //�����ƬԪ�Ƿ������ߵ�����
   float mbFactor=step(0.2,vEdge);//vEdge>0.2--return0
   //�����Ϊ��Ե���������������ɫ�����Ϊ��Ե�����������ɫ
   fragColor=(1.0-mbFactor)*edgeColor+mbFactor*finalColor;
}