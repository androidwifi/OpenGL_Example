function loadObjFile(url,id)//�ӷ�������ȡObj�ı����ݵĺ���
{
	var req = new XMLHttpRequest();//�첽�������
	//������Ӧ�ص�����������processObjectLoadObj������Ӧ
	req.onreadystatechange = function () { processObjectLoadObj(req,id) };
	req.open("GET", url, true);//��GET��ʽ��ָ��URL
	req.responseType = "text";//������Ӧ����
	req.send(null);//����HTTP����
}
function createObject(objDataIn,id)//����������ƶ���ķ���
{
	if(shaderProgArray[id])//���ָ����ɫ���Ѽ������
	{
		switch(id)
		{
			case 0:
			//���������õ�����
			ooTri=new ObjObject(gl,objDataIn.vertices,objDataIn.normals,objDataIn.texcoords,shaderProgArray[id]);
			break;
			
			case 1:
			//���������õ�����
			ooTri_Moon=new ObjObject(gl,objDataIn.vertices,objDataIn.normals,objDataIn.texcoords,shaderProgArray[id]);
			break;
		}
		
	}
	else
	{
		setTimeout(function(){createObject(objDataIn,id);},10); //��Ϣ10ms����ִ��
	}
}

function processObjectLoadObj(req,id)//����obj�ı����ݵĻص�����
{
	if (req.readyState == 4) //��״̬Ϊ4
	{
		var objStr = req.responseText;		//��ȡ��Ӧ�ı�	
		this.dataTemp=fromObjStrToObjectData(objStr);//��obj�ı�����Ϊ���ݶ���
		createObject(dataTemp,id); 	//�����������������
	}
} 