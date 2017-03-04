#version 300 es
precision mediump float;
//���մӶ�����ɫ�������Ĳ���
in vec4 ambient;
in vec4 diffuse;
in vec4 specular;
in float u_clipDist;

out vec4 fragColor;//�������ƬԪ��ɫ
void main()                         
{    
	 if(u_clipDist < 0.0) discard;

   //�����������ɫ����ƬԪ
   vec4 finalColor=vec4(0.95,0.95,0.95,1.0);   
   fragColor = finalColor*ambient+finalColor*specular+finalColor*diffuse;//����ƬԪ��ɫֵ
}   