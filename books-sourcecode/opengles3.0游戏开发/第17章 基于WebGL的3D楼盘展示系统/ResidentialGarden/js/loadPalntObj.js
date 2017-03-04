function loadPalntObjFile(url,object,id)
{
	var req = new XMLHttpRequest();
	req.onreadystatechange = function () { processTreeLoadObj(req,object,id) };
	req.open("GET", url, true);
	req.responseType = "text";
	req.send(null);
}
function createTree(objDataIn,object,id)
{
	if(shaderProgArray[id])
	{
		//���������õ�����
		switch(object)
		{
			case 0://��1====tree1
				trees[0]=new ObjObject(gl,objDataIn.vertices,objDataIn.normals,objDataIn.texcoords,shaderProgArray[id]); 
			break;
		  
			case 1://��2====tree3
				trees[1]=new ObjObject(gl,objDataIn.vertices,objDataIn.normals,objDataIn.texcoords,shaderProgArray[id]);
			break;
		  
			case 2://��3====tree4
				trees[2]=new ObjObject(gl,objDataIn.vertices,objDataIn.normals,objDataIn.texcoords,shaderProgArray[id]); 
			break;
		  
			case 3://��4====tree5
				trees[3]=new ObjObject(gl,objDataIn.vertices,objDataIn.normals,objDataIn.texcoords,shaderProgArray[id]); 
			break;
			
			case 4://��5====zonglvTree
				trees[4]=new ObjObject(gl,objDataIn.vertices,objDataIn.normals,objDataIn.texcoords,shaderProgArray[id]); 
			break;
			
			case 5://��6====chuiliuTree
				trees[5]=new ObjObject(gl,objDataIn.vertices,objDataIn.normals,objDataIn.texcoords,shaderProgArray[id]); 
			break;
			
			case 6://��1====suanFlower
				flower=new ObjObject(gl,objDataIn.vertices,objDataIn.normals,objDataIn.texcoords,shaderProgArray[id]); 
			break;
		}
	}
	else
	{
		setTimeout(function(){createTree(objDataIn,object,id);},10); //����10����
	}
}

function processTreeLoadObj(req,object,id)
{
	if (req.readyState == 4) 
	{
		var objStr = req.responseText;			
		this.dataTemp=fromObjStrToObjectData(objStr);
		createTree(dataTemp,object,id); 	
	}
} 