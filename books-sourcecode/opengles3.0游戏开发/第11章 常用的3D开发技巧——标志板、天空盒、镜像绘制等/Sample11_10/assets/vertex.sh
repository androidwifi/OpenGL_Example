#version 300 es
uniform mat4 uMVPMatrix; 		//�ܱ任����
uniform mat4 uMMatrix; 			//�任����
uniform vec3 uLightLocation;	//��Դλ��
uniform vec3 uCamera;			//�����λ��
in vec3 aPosition;  			//����λ��
in vec3 aNormal;    			//���㷨����
out float vEdge;				//���ϵ��
out vec2 vTextureCoord;			//���ݹ���ǿ���������������

void pointLight(				//��λ����ռ���ķ���
  in vec3 normal,				//������
  out float diffuse,			//ɢ�������ǿ��
  out float specular,			//���������ǿ��
  out float edge,				//���ϵ��
  in vec3 lightLocation,		//��Դλ��
  in float lightDiffuse,		//ɢ���ǿ��
  in float lightSpecular		//�����ǿ��
){
  vec3 normalTarget=aPosition+normal;	//����任��ķ�����
  vec3 newNormal=(uMMatrix*vec4(normalTarget,1)).xyz-(uMMatrix*vec4(aPosition,1)).xyz;
  newNormal=normalize(newNormal); 	//�Է��������
  //����ӱ���㵽�����������
  vec3 eye= normalize(uCamera-(uMMatrix*vec4(aPosition,1)).xyz);  
  //�������ϵ��
  edge = max(0.0,dot(newNormal,eye));
  //����ӱ���㵽��Դλ�õ�����vp
  vec3 vp= normalize(lightLocation-(uMMatrix*vec4(aPosition,1)).xyz);  
  vp=normalize(vp);//��ʽ��vp
  vec3 halfVector=normalize(vp+eye);	//����������ߵİ�����    
  float shininess=50.0;				//�ֲڶȣ�ԽСԽ�⻬
  float nDotViewPosition=max(0.0,dot(newNormal,vp)); 	//��������vp�ĵ����0�����ֵ  
  diffuse=lightDiffuse*nDotViewPosition;				//����ɢ��������ǿ��
  float nDotViewHalfVector=dot(newNormal,halfVector);	//������������ĵ�� 
  float powerFactor=max(0.0,pow(nDotViewHalfVector,shininess)); 	//���淴���ǿ������
  specular=lightSpecular*powerFactor;    			//���㾵��������ǿ��
}

void main(){ 
   gl_Position = uMVPMatrix * vec4(aPosition,1); //�����ܱ任�������˴λ��ƴ˶���λ��  
   float diffuse;//ɢ��������ǿ��
   float specular;//����������ǿ��
   //���й��ռ���
   pointLight(normalize(aNormal),diffuse,specular,vEdge,uLightLocation,0.8,0.9);
   //��ɢ��������ǿ���뾵��������ǿ�����--����ǿ��
   float s=diffuse+specular;
   //��Ӻ��ֵ��ΪS��������
   vTextureCoord=vec2(s,0.5);
}                      