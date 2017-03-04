function DrawHouseMap(){}
DrawHouseMap.prototype.drawSelf=function(ms)
{
	ms.pushMatrix();
	this.drawBuilding2(ms);
	ms.popMatrix();
	
	ms.pushMatrix();
	this.drawBuilding8(ms);
	ms.popMatrix();
	
	ms.pushMatrix();
	this.drawBuilding5(ms);
	ms.popMatrix();
	
	ms.pushMatrix();
	this.drawBuilding4(ms);
	ms.popMatrix();
	
	ms.pushMatrix();
	this.drawBuilding01(ms);
	ms.popMatrix();
	
	ms.pushMatrix();
	this.drawBuilding02(ms);
	ms.popMatrix();
};
DrawHouseMap.prototype.drawBuilding2=function(ms)
{
	//====================����¥��1start=============����===builsing2
	ms.pushMatrix();
	for(var i=0;i<building2.length/2;i++)
	{
		ms.pushMatrix();
		ms.translate(building2[i*2]-MapHalfSize,0,building2[i*2+1]+MapHalfSize);
		buildings[0].drawSelf(ms,texMap["building2Tex"]);//����¥��1
		ms.popMatrix();
	}
	ms.popMatrix();
};
DrawHouseMap.prototype.drawBuilding8=function(ms)
{
	//====================����¥��8start=============����===builsing8
	ms.pushMatrix();
	for(var i=0;i<building8.length/2;i++)
	{
		ms.pushMatrix();
		ms.translate(building8[i*2],0,building8[i*2+1]+MapHalfSize);
		buildings[1].drawSelf(ms,texMap["building8Tex"]);//����¥��8
		ms.popMatrix();
	}
	ms.popMatrix();
};
DrawHouseMap.prototype.drawBuilding4=function(ms)
{
	//====================����¥��4start=============����===builsing4
	//ms.pushMatrix();
	for(var i=0;i<building4.length/2;i++)
	{
		ms.pushMatrix();
		ms.translate(building4[i*2]+40,0,building4[i*2+1]+40);
		buildings[2].drawSelf(ms,texMap["building4Tex"]);//����¥��4
		ms.popMatrix();
	}
	//ms.popMatrix();
};
DrawHouseMap.prototype.drawBuilding5=function(ms)
{
	//====================����¥��5start=============����===builsing5
	//ms.pushMatrix();
	for(var i=0;i<building5.length/2;i++)
	{
		ms.pushMatrix();
		ms.translate(building5[i*2]-100,0,building5[i*2+1]+40);
		buildings[3].drawSelf(ms,texMap["building5Tex"]);//����¥��5
		ms.popMatrix();
	}
	//ms.popMatrix();
};
DrawHouseMap.prototype.drawBuilding01=function(ms)
{
	//====================����¥��01start=============����===builsing01
	ms.pushMatrix();
	for(var i=0;i<building01.length/2;i++)
	{
		ms.pushMatrix();
		ms.translate(building01[i*2]-100,0,building01[i*2+1]-40);
		buildings[4].drawSelf(ms,texMap["building01Tex"]);//����¥��5
		ms.popMatrix();
	}
	ms.popMatrix();
};
DrawHouseMap.prototype.drawBuilding02=function(ms)
{
	//====================����¥��02start=============����===builsing02
	//ms.pushMatrix();
	for(var i=0;i<building02.length/2;i++)
	{
		ms.pushMatrix();
		ms.translate(building02[i*2],0,building02[i*2+1]-40);
		buildings[5].drawSelf(ms,texMap["building02Tex"]);//����¥��5
		ms.popMatrix();
	}
	//ms.popMatrix();
};


