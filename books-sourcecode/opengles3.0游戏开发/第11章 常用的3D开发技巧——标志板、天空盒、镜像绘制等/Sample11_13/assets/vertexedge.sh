#version 300 es
uniform mat4 uMVPMatrix; //�ܱ任����
in vec3 aPosition;  //����λ��
in vec3 aNormal;    //���㷨����

void main()     
{ 
	vec3 position=aPosition;//��ȡ�˶���λ��
	//��������ת�����ӿռ�
	vec4 ydskj=uMVPMatrix*vec4(0,0,0,1);
	vec4 fxldskj=uMVPMatrix*vec4(aNormal.xyz,1.0);
	vec2 skjNormal=fxldskj.xy-ydskj.xy;
	skjNormal=normalize(skjNormal); 
	vec4 finalPosition=uMVPMatrix * vec4(position.xyz,1);
	finalPosition=finalPosition/finalPosition.w;
   	gl_Position =finalPosition+vec4(skjNormal.xy,1.0,1.0)*0.01;//�����ܱ任�������˴λ��ƴ˶���λ��  
}                    