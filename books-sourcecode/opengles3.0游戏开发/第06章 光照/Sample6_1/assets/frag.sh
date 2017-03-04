#version 300 es
precision mediump float;
uniform float uR;
in vec2 mcLongLat;//���մӶ�����ɫ�������Ĳ���
in vec3 vPosition;//���մӶ�����ɫ�������Ķ���λ��
out vec4 fragColor;//�����ƬԪ��ɫ
void main()                         
{
   vec3 color;
   float n = 8.0;//���������ÿ�������᷽���зֵķ���
   float span = 2.0*uR/n;//ÿһ�ݵĳߴ磨С����ı߳���
   int i = int((vPosition.x + uR)/span);//��ǰƬԪλ��С���������
   int j = int((vPosition.y + uR)/span);//��ǰƬԪλ��С����Ĳ���
   int k = int((vPosition.z + uR)/span);//��ǰƬԪλ��С���������
    //���㵱ǰƬԪ�����������������ĺͲ���2ȡģ
   int whichColor = int(mod(float(i+j+k),2.0));
   if(whichColor == 1) {//����ʱΪ��ɫ
   		color = vec3(0.678,0.231,0.129);//��ɫ
   }
   else {//ż��ʱΪ��ɫ
   		color = vec3(1.0,1.0,1.0);//��ɫ
   }
	//�����������ɫ���ݸ�����
   fragColor=vec4(color,0);
}     