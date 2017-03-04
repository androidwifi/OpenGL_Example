function DrawLandMaps(){//���Ƶ��湹�췽��
	this.mapsData0=mapsData0;//��ȡ��ͼ0���������λ������������
	this.mapsData1=mapsData1;
	this.mapsData2=mapsData2;
	this.mapsData3=mapsData3;
	this.mapsData4=mapsData4;
	this.mapsData5=mapsData5;
	this.mapsData6=mapsData6;
	
	this.treeSize=new Array(10);//��������ߴ�����
	for(var i=0;i<10;i++)
	{//ѭ����������
		var numF=Math.random()*2;//�����ȡ�ߴ�
		this.treeSize[i]=numF;//�������ĳߴ�
	}
	
	this.treeNum=new Array(6);//���ı���
	for(var i=0;i<this.treeNum.length;i++)
	{
		var numF=Math.round(Math.random()*5);//�����ȡ���ı��
		this.treeNum[i]=numF;//�������ı���
	}
	
	this.FlowerSize=new Array(360);//��������ߴ�
	for(var i=0;i<360;i++)
	{
		this.FlowerSize[i]=Math.random()*1.5;
	}
	this.R=6;//����ˮ�صĴ�С
	this.r=8;//�����ֻ��ķ�Χ
	
	this.Radin =0.017453;//�ֻ��ķ�Χ
}
DrawLandMaps.prototype.drawLandMaps=function(ms)
{//����������淽��
	//�������
    gl.enable(gl.BLEND);  
    //���û������
	gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA);
	
	ms.pushMatrix();//�����ֳ�
	this.drawLeftBelowLand(this.mapsData0,ms);//�������·�����
	ms.popMatrix();//�ָ��ֳ�
	
	ms.pushMatrix();
	this.drawRightBelowLand(this.mapsData1,ms);
	ms.popMatrix();
	
	ms.pushMatrix();
	this.drawMiddleLeftLand(this.mapsData2,ms);
	ms.popMatrix();
	
	ms.pushMatrix();
	this.drawMiddleRightLand(this.mapsData3,ms);
	ms.popMatrix();
	
	ms.pushMatrix();
	this.drawWestToNorthLand(this.mapsData4,ms);
	ms.popMatrix();
	
	ms.pushMatrix();
	this.drawEastToNorthLand(this.mapsData5,ms);
	ms.popMatrix();
	
	ms.pushMatrix();
	this.drawCenterLand(this.mapsData6,ms);
	ms.popMatrix();
	
	gl.disable(gl.BLEND);//�رջ��
	
};
DrawLandMaps.prototype.drawLeftBelowLand=function(plantData,ms)
{//�����·���·����
	ms.pushMatrix();//�����ֳ�
	for(var i=0;i<plantData.length/3;i++)
	{//ѭ��������ͼ�е�����
		ms.pushMatrix();//�����ֳ�
		//ƽ�Ƶ�ָ��λ��
		ms.translate(land[0]+plantData[i*3]*landSize,0,plantData[(i*3+1)]*landSize+land[1]);
		
		ms.pushMatrix();//�����ֳ�
		ms.scale(landSize,landSize,landSize);//���ű���
		grand.drawSelf(ms,texMap[plantData[(i*3+2)]]);//���Ƶ�������
		ms.popMatrix();//�ָ��ֳ�
		
		if(plantData[(i*3+2)]=="grass1_1")
		{//������Ϊ"grass1_1"
			ms.pushMatrix();//�����ֳ�
			this.num=this.treeNum[plantData[i*3]%6];//��ȡ���ı��
			switch(this.num)
			{//���ı��
				case 4://���������
					ms.pushMatrix();//�����ֳ�
					//���ű���
					ms.scale(this.treeSize[plantData[i*3]%10]+1,this.treeSize[plantData[i*3]%10]+2,this.treeSize[plantData[i*3]%10]+1);
					trees[4].drawSelf(ms,texMap["tree"]);//���������
					ms.popMatrix();//�ָ��ֳ�
					
					ms.pushMatrix();//�����ֳ�
					ms.translate(0,0,-0.2);//ƽ�Ƶ�ָ��λ��
					//���ű���
					ms.scale(this.treeSize[10-plantData[i*3]%10]+1.2,this.treeSize[10-plantData[i*3]%10]+1.8,this.treeSize[10-plantData[i*3]%10]+1);
					trees[4].drawSelf(ms,texMap["tree"]);//���������
					ms.popMatrix();//�ָ��ֳ�
				
				break;
				case 5://���ƴ���
					ms.pushMatrix();
					ms.scale(this.treeSize[plantData[i*3]%10]+1,this.treeSize[plantData[i*3]%10]+6,this.treeSize[plantData[i*3]%10]+0.8);
					trees[5].drawSelf(ms,texMap["chuiliu"]);//����
					ms.popMatrix();
					
					ms.pushMatrix();
					ms.translate(0,0,-0.2);
					ms.scale(this.treeSize[plantData[i*3]%10]+1,this.treeSize[plantData[i*3]%10]+4,this.treeSize[plantData[i*3]%10]+0.8);
					trees[5].drawSelf(ms,texMap["chuiliu"]);//���ƴ���
					ms.popMatrix();
				break;
				default :
					trees[this.num].drawSelf(ms,texMap["tree1"]);//���������
				break;
			}
			ms.popMatrix();//�ָ��ֳ�
		}
		ms.popMatrix();//�ָ��ֳ�
	}
	ms.popMatrix();//�ָ��ֳ�
};
DrawLandMaps.prototype.drawRightBelowLand=function(plantData,ms)
{//�����·���·
	for(var i=0;i<plantData.length/3;i++)
	{
		ms.pushMatrix();
		ms.translate(land[2]-plantData[i*3]*landSize,0,plantData[(i*3+1)]*landSize+land[3]);
		
		ms.pushMatrix();
		ms.scale(landSize,landSize,landSize);
		grand.drawSelf(ms,texMap[plantData[(i*3+2)]]);//��ƺ
		ms.popMatrix();
		
		if(plantData[(i*3+2)]=="grass1_1")
		{
			ms.pushMatrix();
			this.num=this.treeNum[plantData[i*3]%6];
			switch(this.num)
			{
				case 4:
				ms.pushMatrix();
				ms.scale(this.treeSize[plantData[i*3]%10]+0.8,this.treeSize[plantData[i*3]%10]+2,this.treeSize[plantData[i*3]%10]+0.8);
				trees[4].drawSelf(ms,texMap["tree"]);//�����
				ms.popMatrix();
				
				ms.pushMatrix();
				ms.translate(0,0,-0.2);
				ms.scale(this.treeSize[plantData[i*3]%10]+1,this.treeSize[plantData[i*3]%10]+1.5,this.treeSize[plantData[i*3]%10]+0.8);
				trees[4].drawSelf(ms,texMap["tree"]);//�����
				ms.popMatrix();
				break;
				case 5:
				ms.pushMatrix();
				ms.scale(this.treeSize[plantData[i*3]%10]+1,this.treeSize[plantData[i*3]%10]+4,this.treeSize[plantData[i*3]%10]+0.8);
				trees[5].drawSelf(ms,texMap["chuiliu"]);//����
				ms.popMatrix();
				
				ms.pushMatrix();
				ms.translate(0,0,-0.2);
				ms.scale(this.treeSize[10-plantData[i*3]%10]+1,this.treeSize[plantData[i*3]%10]+3,this.treeSize[10-plantData[i*3]%10]+0.8);
				trees[5].drawSelf(ms,texMap["chuiliu"]);//����
				ms.popMatrix();
				break;
				default :
				trees[3-this.num].drawSelf(ms,texMap["tree1"]);//�����
				break;
			}
			
			ms.popMatrix();
		}
		
		ms.popMatrix();
	}
};
DrawLandMaps.prototype.drawMiddleRightLand=function(plantData,ms)
{//�����ҷ���·
	ms.pushMatrix();
	for(var i=0;i<plantData.length/3;i++)
	{
		ms.pushMatrix();
		ms.translate(land[4]+plantData[i*3]*landSize,0,land[5]+plantData[(i*3+1)]*landSize);
		
		ms.pushMatrix();
		ms.scale(landSize,landSize,landSize);
		grand.drawSelf(ms,texMap[plantData[(i*3+2)]]);//��ƺ
		ms.popMatrix();
		
		if(plantData[(i*3+2)]=="grass4_1")
		{
			ms.pushMatrix();
			this.num=this.treeNum[plantData[i*3]%6];
			switch(this.num)
			{
				case 4:
				ms.pushMatrix();
				ms.scale(this.treeSize[plantData[i*3]%10]+1,this.treeSize[plantData[i*3]%10]+5,this.treeSize[plantData[i*3]%10]+0.8);
				trees[5].drawSelf(ms,texMap["chuiliu"]);//����
				ms.popMatrix();
				
				ms.pushMatrix();
				ms.translate(0,0,-0.2);
				ms.scale(this.treeSize[10-plantData[i*3]%10]+1,this.treeSize[plantData[i*3]%10]+4,this.treeSize[plantData[i*3]%10]+0.8);
				trees[5].drawSelf(ms,texMap["chuiliu"]);//����
				ms.popMatrix();
				break;
				
				case 5:
				ms.pushMatrix();
				ms.scale(this.treeSize[plantData[i*3]%10]+0.8,this.treeSize[plantData[i*3]%10]+2,this.treeSize[plantData[i*3]%10]+0.8);
				trees[4].drawSelf(ms,texMap["tree"]);//�����
				ms.popMatrix();
				
				ms.pushMatrix();
				ms.translate(0,0,-0.2);
				ms.scale(this.treeSize[plantData[i*3]%10]+1,this.treeSize[plantData[i*3]%10]+2.8,this.treeSize[10-plantData[i*3]%10]+0.8);
				trees[4].drawSelf(ms,texMap["tree"]);//�����
				ms.popMatrix();
				break;
				
				default :
				trees[this.num].drawSelf(ms,texMap["tree1"]);//�����
				break;
			}
			ms.popMatrix();
		}
		ms.popMatrix();
	}
	ms.popMatrix();
};
DrawLandMaps.prototype.drawMiddleLeftLand=function(plantData,ms)
{//��������·
	for(var i=0;i<plantData.length/3;i++)
	{
		ms.pushMatrix();
		ms.translate(land[6]-plantData[i*3]*landSize,0,land[7]+plantData[(i*3+1)]*landSize);
		
		ms.pushMatrix();
		ms.scale(landSize,landSize,landSize);
		grand.drawSelf(ms,texMap[plantData[(i*3+2)]]);//��ƺ
		ms.popMatrix();
		
		if(plantData[(i*3+2)]=="grass4_1")
		{
			ms.pushMatrix();
			this.num=this.treeNum[plantData[i*3]%6];
			switch(this.num)
			{
				case 4:
				ms.pushMatrix();
				ms.scale(this.treeSize[plantData[i*3]%10]+0.5,this.treeSize[plantData[i*3]%10]+2,this.treeSize[plantData[i*3]%10]+0.3);
				trees[4].drawSelf(ms,texMap["tree"]);//�����
				ms.popMatrix();
				
				ms.pushMatrix();
				ms.translate(0,0,0.3);
				ms.scale(this.treeSize[plantData[i*3]%10]+0.8,this.treeSize[plantData[i*3]%10]+2.4,this.treeSize[plantData[i*3]%10]+0.5);
				trees[4].drawSelf(ms,texMap["tree"]);//�����
				ms.popMatrix();
				break;
				case 5:
				ms.pushMatrix();
				ms.scale(this.treeSize[plantData[i*3]%10]+0.8,this.treeSize[plantData[i*3]%10]+4,this.treeSize[plantData[i*3]%10]+1);
				trees[5].drawSelf(ms,texMap["chuiliu"]);//����
				ms.popMatrix();
				
				ms.pushMatrix();
				ms.translate(0,0,-0.2);
				ms.scale(this.treeSize[plantData[i*3]%10]+1,this.treeSize[plantData[i*3]%10]+6,this.treeSize[plantData[i*3]%10]+0.8);
				trees[5].drawSelf(ms,texMap["chuiliu"]);//����
				ms.popMatrix();
				break;
				default :
				trees[3-this.num].drawSelf(ms,texMap["tree1"]);//�����
				break;
			}
			ms.popMatrix();
		}
		ms.popMatrix();
	}
};
DrawLandMaps.prototype.drawWestToNorthLand=function(plantData,ms)
{//����������·
	ms.pushMatrix();
	for(var i=0;i<plantData.length/3;i++)
	{
		ms.pushMatrix();
		ms.translate(land[8]+plantData[i*3]*landSize,0,land[9]+plantData[(i*3+1)]*landSize);
		
		ms.pushMatrix();
		ms.scale(landSize,landSize,landSize);
		grand.drawSelf(ms,texMap[plantData[(i*3+2)]]);//��ƺ
		ms.popMatrix();
		
		if(plantData[(i*3+2)]=="grass1_1")
		{
			ms.pushMatrix();
			ms.scale(this.treeSize[plantData[i*3]%10]+1.2,this.treeSize[plantData[i*3]%10]+7,this.treeSize[plantData[i*3]%10]+0.8);
			trees[5].drawSelf(ms,texMap["chuiliu"]);//����
			ms.popMatrix();
			
			ms.pushMatrix();
			ms.translate(0,0,-0.3);
			ms.scale(this.treeSize[plantData[i*3]%10]+1,this.treeSize[(plantData[i*3]%10)]+8,this.treeSize[plantData[i*3]%10]+0.8);
			trees[5].drawSelf(ms,texMap["chuiliu"]);//����
			ms.popMatrix();
		}
		ms.popMatrix();
	}
	ms.popMatrix();
};
DrawLandMaps.prototype.drawEastToNorthLand=function(plantData,ms)
{//����������·
	ms.pushMatrix();
	for(var i=0;i<plantData.length/3;i++)
	{
		ms.pushMatrix();
		ms.translate(land[10]-plantData[i*3]*landSize,0,land[11]+plantData[(i*3+1)]*landSize);
		
		ms.pushMatrix();
		ms.scale(landSize,landSize,landSize);
		grand.drawSelf(ms,texMap[plantData[(i*3+2)]]);//��ƺ
		ms.popMatrix();
		
		if(plantData[(i*3+2)]=="grass1_1")
		{
			ms.pushMatrix();
			ms.scale(this.treeSize[plantData[i*3]%10]+1,this.treeSize[plantData[i*3]%10]+7,this.treeSize[plantData[i*3]%10]+0.8);
			trees[5].drawSelf(ms,texMap["chuiliu"]);//����
			ms.popMatrix();
				
			ms.pushMatrix();
			ms.translate(0,0,-0.3);
			ms.scale(this.treeSize[plantData[i*3]%10]+0.8,this.treeSize[plantData[i*3]%10]+5,this.treeSize[plantData[i*3]%10]+0.8);
			trees[5].drawSelf(ms,texMap["chuiliu"]);//����
			ms.popMatrix();
		}
		ms.popMatrix();
	}
	ms.popMatrix();
};
DrawLandMaps.prototype.drawCenterLand=function(plantData,ms)
{//�����ķ���·
	ms.pushMatrix();
	for(var i=0;i<plantData.length/3;i++)
	{
		ms.pushMatrix();
		ms.translate(land[12]+plantData[i*3]*landSize,0,land[13]+plantData[(i*3+1)]*landSize);
		
		ms.pushMatrix();
		ms.scale(landSize,landSize,landSize);
		grand.drawSelf(ms,texMap[plantData[(i*3+2)]]);//��ƺ
		ms.popMatrix();
		
		if(plantData[(i*3+2)]=="grass1_1")
		{
			ms.pushMatrix();
			this.num=this.treeNum[plantData[i*3]%6];
			if(this.num<2)
			{
				this.num=5;
			}else
			{
				this.num=4;
			}
				
			switch(this.num)
			{
				case 4:
				ms.pushMatrix();
				ms.scale(this.treeSize[plantData[i*3]%10]+0.8,this.treeSize[plantData[i*3]%10]+2,this.treeSize[9-plantData[i*3]%10]+0.4);
				trees[4].drawSelf(ms,texMap["tree"]);//�����
				ms.popMatrix();
				
				ms.pushMatrix();
				ms.translate(0,0,-0.25);
				ms.scale(this.treeSize[plantData[i*3]%10]+0.8,this.treeSize[plantData[i*3]%10]+1.5,this.treeSize[9-plantData[i*3]%10]+0.4);
				trees[4].drawSelf(ms,texMap["tree"]);//�����
				ms.popMatrix();
				break;
				case 5:
				ms.pushMatrix();
				ms.scale(this.treeSize[plantData[i*3]%10]+1,this.treeSize[plantData[i*3]%10]+5,this.treeSize[plantData[i*3]%10]+0.8);
				trees[5].drawSelf(ms,texMap["chuiliu"]);//����
				ms.popMatrix();
				
				ms.pushMatrix();
				ms.translate(0,0,-0.25);
				ms.scale(this.treeSize[plantData[i*3]%10]+1,this.treeSize[plantData[i*3]%10]+6,this.treeSize[9-plantData[i*3]%10]+0.5);
				trees[5].drawSelf(ms,texMap["chuiliu"]);//����
				ms.popMatrix();
				break;
			}
			ms.popMatrix();
		}
		ms.popMatrix();
	}
	ms.popMatrix();
	//��������ˮ��
	ms.pushMatrix();
	ms.scale(6,1,6);
	pool.drawSelf(ms,texMap["shuichi"]);
	ms.popMatrix();
	
	ms.pushMatrix();
	//��������ˮ�رߵĻ�
	for(var angle=0;angle<360;angle=angle+5)
	{
		ms.pushMatrix();
		var angradElevation=this.Radin*angle;
		ms.translate(this.r*Math.sin(angradElevation),0,this.r*Math.cos(angradElevation));
		ms.scale(1+this.FlowerSize[angle],1.2+this.FlowerSize[angle],1+this.FlowerSize[angle]);
		flower.drawSelf(ms,texMap["suanFlower"]);//��
		ms.popMatrix();
	}
	ms.popMatrix();
};