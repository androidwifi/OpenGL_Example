#version 300 es
precision mediump float;
in  vec4 vColor; //���մӶ�����ɫ�������Ĳ���
in vec3 vPosition;//���մӶ�����ɫ�������Ķ���λ��
layout (location = 0) out vec4 fragColor;    //������ɫ
void main() {  
   fragColor = vColor;//����ƬԪ��ɫֵ
}