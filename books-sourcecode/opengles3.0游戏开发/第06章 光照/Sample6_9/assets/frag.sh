#version 300 es
precision mediump float;//����Ĭ�ϸ��㾫��
uniform float uR;//��İ뾶
uniform vec3 uLightLocation;//��Դλ��
uniform mat4 uMMatrix; //�任����
uniform vec3 uCamera;	//�����λ��
in vec3 vPosition;//���մӶ�����ɫ�������Ķ���λ��
in vec3 vNormal;//���մӶ�����ɫ�����ݹ����ķ�����
out vec4 fragColor;
void pointLight(					//��λ����ռ���ķ���
  in vec3 normal,				//������
  inout vec4 ambient,			//����������ǿ��
  inout vec4 diffuse,				//ɢ�������ǿ��
  inout vec4 specular,			//���������ǿ��
  in vec3 lightLocation,			//��Դλ��
  in vec4 lightAmbient,			//������ǿ��
  in vec4 lightDiffuse,			//ɢ���ǿ��
  in vec4 lightSpecular			//�����ǿ��
){
  ambient=lightAmbient;			//ֱ�ӵó������������ǿ��  
  vec3 normalTarget=vPosition+normal;	//����任��ķ�����
  vec3 newNormal=(uMMatrix*vec4(normalTarget,1)).xyz-(uMMatrix*vec4(vPosition,1)).xyz;
  newNormal=normalize(newNormal); 	//�Է��������
  //����ӱ���㵽�����������
  vec3 eye= normalize(uCamera-(uMMatrix*vec4(vPosition,1)).xyz);  
  //����ӱ���㵽��Դλ�õ�����vp
  vec3 vp= normalize(lightLocation-(uMMatrix*vec4(vPosition,1)).xyz);  
  vp=normalize(vp);//��ʽ��vp
  vec3 halfVector=normalize(vp+eye);	//����������ߵİ�����    
  float shininess=50.0;				//�ֲڶȣ�ԽСԽ�⻬
  float nDotViewPosition=max(0.0,dot(newNormal,vp)); 	//��������vp�ĵ����0�����ֵ
  diffuse=lightDiffuse*nDotViewPosition;				//����ɢ��������ǿ��
  float nDotViewHalfVector=dot(newNormal,halfVector);	//������������ĵ�� 
  float powerFactor=max(0.0,pow(nDotViewHalfVector,shininess)); 	//���淴���ǿ������
  specular=lightSpecular*powerFactor;    			//���㾵��������ǿ��
}
void main()                         
{
   vec3 color;
   float n = 8.0;//һ����������ֵ��ܷ���
   float span = 2.0*uR/n;//ÿһ�ݵĳ���
   //ÿһά���������ڵ�������
   int i = int((vPosition.x + uR)/span);
   int j = int((vPosition.y + uR)/span);
   int k = int((vPosition.z + uR)/span);
   //���㵱��Ӧλ�ڰ�ɫ�黹�Ǻ�ɫ����
   int whichColor = int(mod(float(i+j+k),2.0));
   if(whichColor == 1) {//����ʱΪ��ɫ
   		color = vec3(0.678,0.231,0.129);//��ɫ
   }
   else {//ż��ʱΪ��ɫ
   		color = vec3(1.0,1.0,1.0);//��ɫ
   }
   //������ɫ
   vec4 finalColor=vec4(color,0);
   
   
   vec4 ambient,diffuse,specular;    //������������ͨ������ǿ�ȵı��� 
   
   pointLight(normalize(vNormal),ambient,diffuse,specular,uLightLocation,//���㶨λ���ͨ��ǿ�� 
   vec4(0.15,0.15,0.15,1.0),vec4(0.8,0.8,0.8,1.0),vec4(0.7,0.7,0.7,1.0));  
   
   //�ۺ�����ͨ���������ǿ�ȼ�ƬԪ����ɫ���������ƬԪ����ɫ�����ݸ�����
   fragColor=finalColor*ambient + finalColor*diffuse + finalColor*specular;
}     