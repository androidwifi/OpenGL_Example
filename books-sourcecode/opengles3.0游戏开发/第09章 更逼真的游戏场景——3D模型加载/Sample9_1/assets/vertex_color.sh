#version 300 es
uniform mat4 uMVPMatrix; //�ܱ任����
in vec3 aPosition;  //����Ⱦ���߽��յĶ���λ��
out vec3 vPosition;  //���ڴ��ݸ�ƬԪ��ɫ���Ķ���λ��
void main() {                            		
   gl_Position = uMVPMatrix * vec4(aPosition,1); //�����ܱ任�������˴λ��ƴ˶����λ��
   vPosition=aPosition;//������λ�ô��ݸ�ƬԪ��ɫ��
}