#version 300 es
precision mediump float;
in vec4 ambient;							//�Ӷ�����ɫ�����ݹ����Ļ���������ǿ��
in vec4 diffuse;								//�Ӷ�����ɫ�����ݹ�����ɢ�������ǿ��
in vec4 specular;							//�Ӷ�����ɫ�����ݹ����ľ��������ǿ��
in float vFogFactor;							//�Ӷ�����ɫ�����ݹ�����������
out vec4 fragColor;//�������ƬԪ��ɫ
void main()                         
{
	vec4 objectColor=vec4(0.95,0.95,0.95,1.0);//������ɫ	
	vec4 fogColor = vec4(0.97,0.76,0.03,1.0);//�����ɫ	
 	if(vFogFactor != 0.0){//���������Ϊ0�����ؼ������
		objectColor = objectColor*ambient+objectColor*specular+objectColor*diffuse;//�������֮��������ɫ
		fragColor = objectColor*vFogFactor + fogColor*(1.0-vFogFactor);//������ɫ������ɫ��ֵ����������ɫ
	}else{
 	    fragColor=fogColor;
 	}
}