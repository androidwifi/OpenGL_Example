function BasicObject
(
	object,//obj����
	width,//2D����Ŀ��
	height,//2D����ĸ߶�
	x,//���Ƶ�λ��
	y,
	texId//����ID
)
{
	this.texId=texId;//�����������Id
	this.width=fromPixWIDTHToNearWIDTH(width);
	this.height=fromPixHEIGHTToNearHEIGHT(height);
	this.x=fromScreenTo2DWordX(x);
	this.y=fromScreenTo2DWordY(y);
	
	this.drawSelf=function(lms)
	{
		lms.pushMatrix();
		lms.translate(this.x,this.y, 0);//ƽ��
		lms.scale(this.width,this.height,0);
		
		object.drawSelf(lms,this.texId);//����3D����
		
		lms.popMatrix();
	}      
}