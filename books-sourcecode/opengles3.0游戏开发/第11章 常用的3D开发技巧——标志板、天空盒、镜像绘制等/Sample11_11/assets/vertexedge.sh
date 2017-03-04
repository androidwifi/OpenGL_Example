#version 300 es
uniform mat4 uMVPMatrix; //�ܱ任����
in vec3 aPosition;  //����λ��
in vec3 aNormal;    //���㷨����

void main()     
{ 
	vec3 position=aPosition;//��ȡ�˶���λ��
	position.xyz+=aNormal*0.4;//���ж��㼷��
	
   	gl_Position = uMVPMatrix * vec4(position.xyz,1);//�����ܱ任�������˴λ��ƴ˶���λ��  
}                    