#version 300 es
precision mediump float;//��������Ĭ�Ͼ���
uniform sampler2D sTexture;//������������
out vec4 fragColor; 	//���ƬԪ����ɫ
void main() 
{  
   	//���ڽ�������ȡ��������
   vec2 texCoor=gl_PointCoord;
   //�����������
   fragColor = texture(sTexture,texCoor);
}