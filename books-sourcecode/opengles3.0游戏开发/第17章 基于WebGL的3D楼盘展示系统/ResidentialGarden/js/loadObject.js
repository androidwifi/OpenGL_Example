function loadObjFile(url,object,id)
{//����ģ���ļ�����
	var req=new XMLHttpRequest();//����XMLHttpRequest����
	//��дonreadystatechange�¼�
	req.onreadystatechange=function (){processObjectLoadObj(req,object,id)};
	req.open("GET",url,true);//����open����
	req.responseType="text";//����responseType
	req.send(null);//����send����
}
function createObject(objDataIn,object,id)
{//�������巽��
	if(shaderProgArray[id])
	{//�õ�����ɫ���Ƿ�Ϊ��
		switch(object)
		{//������
			case 0://������ͼ����
				map=new ObjObject(gl,objDataIn.vertices,objDataIn.normals,objDataIn.texcoords,shaderProgArray[id]);
			break;
	  
			case 1://������պж���
				skybox=new ObjObject(gl,objDataIn.vertices,objDataIn.normals,objDataIn.texcoords,shaderProgArray[id]);
			break;
		  
			case 2://�������λ��ƶ���
				rectangle=new ObjObject(gl,objDataIn.vertices,objDataIn.normals,objDataIn.texcoords,shaderProgArray[id]); 
			break;
		  
			case 3://����ˮ�ض���
				pool=new ObjObject(gl,objDataIn.vertices,objDataIn.normals,objDataIn.texcoords,shaderProgArray[id]);
			break;
		  
			case 4://������ƺ����
				grand=new ObjObject(gl,objDataIn.vertices,objDataIn.normals,objDataIn.texcoords,shaderProgArray[id]);
			break;
			
			case 5://�����������2D����
				obj=new ObjObject(gl,objDataIn.vertices,objDataIn.normals,objDataIn.texcoords,shaderProgArray[id]);//
				//��������ͼ����
				mainView_rectangle=new BasicObject(obj,48,12,600,300,texMap["bg"]);
				
				//������ͨ״̬�İ�ť
				lable[0]=new BasicObject(obj,7,2,1050,290,texMap["label1_1"]);
				lable[1]=new BasicObject(obj,7,2,1050,440,texMap["label0_1"]);
				
				lable[2]=new BasicObject(obj,6.4,1.4,160,236,texMap["label2_1"]);
				lable[3]=new BasicObject(obj,6.4,1.4,160,316,texMap["label3_1"]);
				lable[4]=new BasicObject(obj,6.4,1.4,160,396,texMap["label4_1"]);
				lable[5]=new BasicObject(obj,6.4,1.4,160,476,texMap["label5_1"]);
				
				//�������µİ�ť
				lable_down[0]=new BasicObject(obj,7,2,1050,290,texMap["label1_2"]);
				lable_down[1]=new BasicObject(obj,7,2,1050,440,texMap["label0_2"]);
				lable_down[2]=new BasicObject(obj,6.4,1.4,160,236,texMap["label2_2"]);
				lable_down[3]=new BasicObject(obj,6.4,1.4,160,316,texMap["label3_2"]);
				lable_down[4]=new BasicObject(obj,6.4,1.4,160,396,texMap["label4_2"]);
				lable_down[5]=new BasicObject(obj,6.4,1.4,160,476,texMap["label5_2"]);
				//����������
				lable[6]=new BasicObject(obj,12.8,9,160,326,texMap["rightView"]);
				lable[7]=new BasicObject(obj,12.8,9,1040,326,texMap["leftView"]);
				
			break;
		}
	}
	else
	{
		setTimeout(function(){createObject(objDataIn,object,id);},10); //����10����
	}
}

function processObjectLoadObj(req,object,id)
{//����ģ���ļ��Ĺ��̷���
	if (req.readyState == 4) 
	{//���ݽ������
		var objStr = req.responseText;//��ȡ��ҳ��Ϣ			
		this.dataTemp=fromObjStrToObjectData(objStr);//��ȡģ���������
		createObject(dataTemp,object,id);//���ô������巽��	
	}
} 