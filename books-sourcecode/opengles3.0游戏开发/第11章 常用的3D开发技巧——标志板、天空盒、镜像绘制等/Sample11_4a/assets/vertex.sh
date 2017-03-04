#version 300 es
uniform mat4 uMVPMatrix; 		//�ܱ任����

uniform sampler2D sTextureLand;//�����������ݣ��Ҷ�ͼ��
uniform float landHighAdjust;//½�صĸ߶ȵ���ֵ
uniform float landHighest;//½�����߲� 
in vec2 aTexLandCoor;		//�Ҷ�ͼ������������
in vec3 aPosition;  		//����λ��
in vec2 aTexCoor;    		//������������
out vec2 vTextureCoord;  		//���ڴ��ݸ�ƬԪ��ɫ������������
out float currY;				//���ڴ��ݸ�ƬԪ��ɫ����Y����

void main(){  
   vTextureCoord = aTexCoor;						//�����յ��������괫�ݸ�ƬԪ��ɫ��
   vec4 gColor=texture(sTextureLand, aTexLandCoor);	//�ӻҶ�ͼ�����в�������ɫ
   float tempy=(((gColor.r+gColor.g+gColor.b)/3.0)*landHighest)+landHighAdjust;//���㶥���yֵ
   currY=tempy;		//�������Y���괫�ݸ�ƬԪ��ɫ��
   gl_Position = uMVPMatrix * vec4(aPosition.x,tempy,aPosition.z,1); 	//�����ܱ任�������˴λ��ƴ˶����λ��			
}    
                