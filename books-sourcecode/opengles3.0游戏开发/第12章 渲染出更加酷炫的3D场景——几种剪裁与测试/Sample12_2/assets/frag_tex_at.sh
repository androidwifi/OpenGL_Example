#version 300 es
precision mediump float;
in vec2 vTextureCoord; //���մӶ�����ɫ�������Ĳ���
uniform sampler2D sTexture;//������������

out vec4 fragColor;//�������ƬԪ��ɫ
void main() { 
   vec4 bcolor = texture(sTexture, vTextureCoord);//����ƬԪ�������в�������ɫֵ 
   if(bcolor.a<0.6) {
   		discard;
   } else {
      fragColor=bcolor;
}}