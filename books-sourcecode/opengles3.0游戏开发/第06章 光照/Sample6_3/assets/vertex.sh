#version 300 es
uniform mat4 uMVPMatrix; 						//�ܱ任����
uniform mat4 uMMatrix; 							//�任����(����ƽ�ơ���ת������)
uniform vec3 uLightLocation;						//��Դλ��
in vec3 aPosition;  						//����λ��
in vec3 aNormal;    						//���㷨����
out vec3 vPosition;							//���ڴ��ݸ�ƬԪ��ɫ���Ķ���λ��
out vec4 vDiffuse;							//���ڴ��ݸ�ƬԪ��ɫ����ɢ������
void pointLight (								//ɢ�����ռ���ķ���
  in vec3 normal,								//������
  inout vec4 diffuse,								//ɢ��������
  in vec3 lightLocation,							//��Դλ��
  in vec4 lightDiffuse							//ɢ���ǿ��
){  
  vec3 normalTarget=aPosition+normal;					//����任��ķ�����
  vec3 newNormal=(uMMatrix*vec4(normalTarget,1)).xyz-(uMMatrix*vec4(aPosition,1)).xyz;
  newNormal=normalize(newNormal);					//�Է��������
//����ӱ���㵽��Դλ�õ�����vp
  vec3 vp= normalize(lightLocation-(uMMatrix*vec4(aPosition,1)).xyz);
  vp=normalize(vp);									//���vp
  float nDotViewPosition=max(0.0,dot(newNormal,vp)); 	//��������vp�����ĵ����0�����ֵ
  diffuse=lightDiffuse*nDotViewPosition;			//����ɢ��������ǿ��
}
void main(){
   gl_Position = uMVPMatrix * vec4(aPosition,1); 	//�����ܱ任�������˴λ��ƴ˶����λ�� 
   vec4 diffuseTemp=vec4(0.0,0.0,0.0,0.0);   
   pointLight(normalize(aNormal), diffuseTemp, uLightLocation, vec4(0.8,0.8,0.8,1.0));  
   vDiffuse=diffuseTemp;					//��ɢ�������ǿ�ȴ���ƬԪ��ɫ��
   vPosition = aPosition; 					//�������λ�ô���ƬԪ��ɫ��
}

