#version 300 es
precision mediump float;
in  vec4 vaColor; //���մӶ�����ɫ�������Ĳ���
out vec4 fragColor;//�����ƬԪ��ɫ
void main()                         
{
	vec4 finalColor =vaColor;
	fragColor = finalColor;//����ƬԪ��ɫֵ
}              