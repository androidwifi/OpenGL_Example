#version 300 es
precision mediump float;
in vec2 vTextureCoord; //���մӶ�����ɫ�������Ĳ���
out vec4 fragColor;
uniform sampler2D sTexture;//������������
void main()                         
{           
   //����ƬԪ�������в�������ɫֵ            
   fragColor = texture(sTexture, vTextureCoord); 
}              