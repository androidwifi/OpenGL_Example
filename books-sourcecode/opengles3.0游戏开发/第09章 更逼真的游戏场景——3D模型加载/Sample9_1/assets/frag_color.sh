#version 300 es
precision mediump float;//����Ĭ�ϸ��㾫��
in  vec3 vPosition;  //�Ӷ�����ɫ�����յĶ���λ��
out vec4 fragColor;//����ƬԪ��ɫ
void main() {
   vec4 bColor=vec4(0.678,0.231,0.129,0);//���Ƶ���ɫ(���ɫ)
   vec4 mColor=vec4(0.763,0.657,0.614,0);//����������ɫ(����ɫ)
   float y=vPosition.y;//��ȡ�����y����ֵ
   y=mod((y+100.0)*4.0,4.0);//���������ֵ
   if(y>1.8) {//������ֵ����ָ��ֵʱ
     fragColor = bColor;//����ƬԪ��ɫΪ���Ƶ���ɫ
   } else {//������ֵ������ָ��ֵʱ
     fragColor = mColor;//����ƬԪ��ɫΪ����������ɫ
}} 