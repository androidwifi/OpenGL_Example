#version 300 es
precision mediump float;//���ø��㾫��
//���մӶ�����ɫ�������Ĳ���
in vec4 ambientZM;					//���մӶ�����ɫ�����������滷��������ǿ��
in vec4 diffuseZM;					//���մӶ�����ɫ������������ɢ�������ǿ��
in vec4 specularZM;					//���մӶ�����ɫ�����������澵�淴�������ǿ��
in vec4 ambientFM;					//���մӶ�����ɫ�������ķ��滷��������ǿ��
in vec4 diffuseFM;					//���մӶ�����ɫ�������ķ���ɢ�������ǿ��
in vec4 specularFM;					//���մӶ�����ɫ�������ķ��澵�淴�������ǿ��
out vec4 fragColor;								//�����ƬԪ����ɫ

void main()                         
{//������    
   //�����������ɫ����ƬԪ
   vec4 finalColor=vec4(0.9,0.9,0.9,1.0);   //ƬԪ��ԭʼ��ɫ
   if(gl_FrontFacing)
   {//�ж�����������滹�Ƿ���
   	 fragColor = finalColor*ambientZM+finalColor*specularZM+finalColor*diffuseZM;//����ƬԪ������ɫֵ
   }
   else
   {//��������ķ���
     fragColor = finalColor*ambientFM+finalColor*specularFM+finalColor*diffuseFM;//����ƬԪ������ɫֵ
   }
}   