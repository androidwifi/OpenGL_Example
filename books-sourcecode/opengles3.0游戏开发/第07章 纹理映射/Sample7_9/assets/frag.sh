#version 300 es
precision mediump float;//��������Ĭ�Ͼ���
uniform mediump sampler2DArray sTexture;//������������
in float vid;//������
out vec4 fragColor; //���ݵ���Ⱦ���ߵ�ƬԪ��ɫ

void main() 
{  
   //��װ��������
   vec3 texCoor=vec3(gl_PointCoord.st,vid);
   //�����������
   fragColor = texture(sTexture,texCoor);
}