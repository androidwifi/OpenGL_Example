#version 300 es
uniform mat4 uMVPMatrix; //�ܱ任����
in vec3 aPosition;  //����λ��
out vec3 vPosition;//���ڴ��ݸ�ƬԪ��ɫ���Ķ���λ��
void main()     
{                   
   //�����ܱ任�������˴λ��ƴ˶���λ��         		
   gl_Position = uMVPMatrix * vec4(aPosition,1); 
   //�������λ�ô���ƬԪ��ɫ��
   vPosition = aPosition;//��ԭʼ����λ�ô��ݸ�ƬԪ��ɫ��
}