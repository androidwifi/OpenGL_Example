#version 300 es
uniform mat4 uMVPMatrix; 	//�ܱ任����
uniform mat4 uMMatrix; 		//�任����
uniform vec3 uLightLocation;	//��Դλ��
uniform vec3 uCamera;		//�����λ��
in vec3 aPosition;  	//����λ��
in vec3 aNormal;   	//������
out vec3 vPosition;		//���ڴ��ݸ�ƬԪ��ɫ���Ķ���λ��
out vec4 vSpecular;		//���ڴ��ݸ�ƬԪ��ɫ���ľ��������ǿ��
void pointLight(				//��λ����ռ���ķ���
  in vec3 normal,			//������
  inout vec4 specular,		//���������ǿ��
  in vec3 lightLocation,		//��Դλ��
  in vec4 lightSpecular		//�����ǿ��
){ 
  vec3 normalTarget=aPosition+normal; 	//����任��ķ�����
  vec3 newNormal=(uMMatrix*vec4(normalTarget,1)).xyz-(uMMatrix*vec4(aPosition,1)).xyz;
  newNormal=normalize(newNormal);  	//�Է��������
  //����ӱ���㵽�����������
  vec3 eye= normalize(uCamera-(uMMatrix*vec4(aPosition,1)).xyz);  
  //����ӱ���㵽��Դλ�õ�����vp
  vec3 vp= normalize(lightLocation-(uMMatrix*vec4(aPosition,1)).xyz);  
  vp=normalize(vp);//��ʽ��vp
  vec3 halfVector=normalize(vp+eye);	//����������ߵİ�����  
  float shininess=50.0;				//�ֲڶȣ�ԽСԽ�⻬    
  float nDotViewHalfVector=dot(newNormal,halfVector);			//������������ĵ�� 
  float powerFactor=max(0.0,pow(nDotViewHalfVector,shininess)); 	//���淴���ǿ������
  specular=lightSpecular*powerFactor;    //���յľ����ǿ��
}
void main()  {                        		
   gl_Position = uMVPMatrix * vec4(aPosition,1); //�����ܱ任�������˴λ��ƴ˶����λ��      
   vec4 specularTemp=vec4(0.0,0.0,0.0,0.0);   
   pointLight(normalize(aNormal), specularTemp, uLightLocation, vec4(0.7,0.7,0.7,1.0));//���㾵���  
   vSpecular=specularTemp;	//�����վ����ǿ�ȴ���ƬԪ��ɫ��   
   vPosition = aPosition; 		//�������λ�ô���ƬԪ��ɫ��
} 
