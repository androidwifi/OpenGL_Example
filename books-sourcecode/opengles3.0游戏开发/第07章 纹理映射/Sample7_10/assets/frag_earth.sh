#version 300 es      
precision mediump float;
in vec2 vTextureCoord;//���մӶ�����ɫ�������Ĳ���
in vec4 vAmbient;			//���մӶ�����ɫ����������������ǿ��
in vec4 vDiffuse;			//���մӶ�����ɫ������ɢ�������ǿ��
in vec4 vSpecular;			//���մӶ�����ɫ���������淴�������ǿ��
out vec4 fragColor;			//���ݵ���Ⱦ���ߵ�ƬԪ��ɫ
uniform sampler2D sTextureDay;	//�����������������
uniform sampler2D sTextureNight;//��ҹ�������������
void main()                         
{  //������ɫ����main����
	vec4 finalColorDay;  		//�Ӱ��������в�������ɫֵ
	vec4 finalColorNight;   	//��ҹ�������в�������ɫֵ
  
  finalColorDay= texture(sTextureDay, vTextureCoord);//�����������������ɫֵ
  finalColorDay = finalColorDay*vAmbient+finalColorDay*vSpecular+finalColorDay*vDiffuse;
  finalColorNight = texture(sTextureNight, vTextureCoord);  //������ҹ���������ɫֵ
  finalColorNight = finalColorNight*vec4(0.5,0.5,0.5,1.0);//������ĸ�ƬԪҹ����ɫֵ
  
  if(vDiffuse.x>0.21)
  {//��ɢ����������0.21ʱ
    fragColor=finalColorDay;     //���ð������� 
  } 
  else if(vDiffuse.x<0.05)
  {     //��ɢ������С��0.05ʱ
     fragColor=finalColorNight;//����ҹ������
  }
  else
  {//���������������0.05С��0.21ʱ��Ϊ����ҹ������Ĺ��ɽ׶�
     float t=(vDiffuse.x-0.05)/0.16;//�����������Ӧռ������ɽ׶εİٷֱ�
     fragColor=t*finalColorDay+(1.0-t)*finalColorNight;//��������ҹ���ɽ׶ε���ɫֵ
  }  
}              