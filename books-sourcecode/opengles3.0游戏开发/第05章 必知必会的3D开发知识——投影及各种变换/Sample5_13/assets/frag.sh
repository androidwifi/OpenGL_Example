#version 300 es
precision mediump float;
in  vec4 vColor; //���մӶ�����ɫ�������Ĳ���
in vec3 vPosition;//���մӶ�����ɫ�������Ķ���λ��
out vec4 fragColor;//�������ƬԪ��ɫ
void main() {                       
   vec4 finalColor=vColor;
   //��z��ת20�ȵ���ת�任����
   mat4 mm=mat4(0.9396926,-0.34202012,0.0,0.0,  0.34202012,0.9396926,0.0,0.0,  
   			0.0,0.0,1.0,0.0,  0.0,0.0,0.0,1.0);   
   vec4 tPosition=mm*vec4(vPosition,1);//������������z��ת20��   
   if(mod(tPosition.x+100.0,0.4)>0.3) {//����X�����ڲ��ں��ɫ����Χ��     
     finalColor=vec4(0.4,0.0,0.0,1.0)+finalColor;//���ڸ�������ɫ���ϵ���ɫ
   }
   fragColor = finalColor;//����ƬԪ��ɫֵ
}