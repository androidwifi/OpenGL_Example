#version 300 es
uniform mat4 uMVPMatrix; //�ܱ任����
uniform mat4 uMMatrix; //�任����
in vec3 aPosition;  //����λ��
in vec4 aColor;    //������ɫ
out vec4 vColor;  //���ڴ��ݸ�ƬԪ��ɫ������ɫ
out vec3 vPosition;//���ڴ��ݸ�ƬԪ��ɫ���Ķ���λ��
void main()  {                            		
   gl_Position = uMVPMatrix * vec4(aPosition,1); //�����ܱ任�������˴λ��ƴ˶���λ��
   vColor = aColor;//�����յ���ɫ���ݸ�ƬԪ��ɫ��
   vPosition=(uMMatrix * vec4(aPosition,1)).xyz;//������˶���任���λ�ô��ݸ�ƬԪ��ɫ��
}                      