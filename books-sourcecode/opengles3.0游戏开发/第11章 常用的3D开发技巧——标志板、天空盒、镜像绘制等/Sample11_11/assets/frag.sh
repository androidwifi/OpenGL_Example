#version 300 es
precision mediump float;
//���մӶ�����ɫ�������Ĳ���
in vec4 ambient;
in vec4 diffuse;
in vec4 specular;
out vec4 fragColor;//�����ƬԪ��ɫ

void main()                         
{//�������屾��
	   	//�����������ɫ����ƬԪ
   		vec4 finalColor=vec4(0.93,0.51,0.79,0);   
   		fragColor = finalColor*ambient+finalColor*specular+finalColor*diffuse;//����ƬԪ��ɫֵ
}   