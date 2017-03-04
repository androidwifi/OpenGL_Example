function ObjectData//����������ObjectData
(
  vertexCountIn,
  verticesIn,
  texcoordsIn,
  normalsIn
)
{  	 
	this.vertexCount=vertexCountIn;//��ʼ������������Ա
	this.vertices=verticesIn;//��ʼ�������������ݳ�Ա
	this.texcoords=texcoordsIn;//��ʼ�����������������ݳ�Ա
	this.normals=normalsIn;  	//��ʼ�����㷨������Ա  
}	

function fromObjStrToObjectData(objStr)//����obj�ı����ݵĺ���
{
	//ԭʼ���������б�--ֱ�Ӵ�obj�ļ��м���
	var alv=new Array();
	//������������б�--������֯��
	var alvResult=[];  
	//ԭʼ�������������б�--ֱ�Ӵ�obj�ļ��м���
	var alt=new Array();
	//����������������б�--������֯��
	var altResult=[];  
	//ԭʼ�������б�--ֱ�Ӵ�obj�ļ��м���
	var aln=new Array();    
	//����������б�--������֯��
	var alnResult=[];     	
	
	var lines = objStr.split("\n");//��obj�е������Ի��з�Ϊ�ָ����ֿ�
	
	for (var lineIndex in lines) //����ÿһ���ı�
	{
		var line = lines[lineIndex].replace(/[ \t]+/g, " ").replace(/\s\s*$/, "");//ȥ���հ׷�
		if (line[0] == "#")//�������#��ͷ��
		{
			continue;//��������
		}

		var array = line.split(" ");//��һ�������Կո��Ϊ�ָ����ֿ�
		if (array[0] == "v") //�ж��Ƿ�Ϊ����������Ϣ��
		{
			alv.push(parseFloat(array[1]));//��ȡ����X���겢����ԭʼ���������б�
			alv.push(parseFloat(array[2]));//��ȡ����Y���겢����ԭʼ���������б�
			alv.push(parseFloat(array[3]));//��ȡ����Z���겢����ԭʼ���������б�
		}
		else if (array[0] == "vt") //�ж��Ƿ�Ϊ��������������Ϣ��
		{
			alt.push(parseFloat(array[1]));//��ȡ��������S���겢����ԭʼ�������������б�
			alt.push(1.0-parseFloat(array[2]));//��ȡ��������T���겢����ԭʼ�������������б�
		}
		else if (array[0] == "vn") //�ж��Ƿ�Ϊ��������Ϣ��
		{
			aln.push(parseFloat(array[1]));//��ȡ������X����������ԭʼ�������б�
			aln.push(parseFloat(array[2]));//��ȡ������y����������ԭʼ�������б�
			aln.push(parseFloat(array[3]));//��ȡ������z����������ԭʼ�������б�
		}
		else if (array[0] == "f") //�ж��Ƿ�Ϊ��װ�����������Ϣ��
		{
			if (array.length != 4)//��������������
			{
				alert("array.length != 4");//������ʾ��Ϣ
				continue;//��������
			}
			for (var i = 1; i < 4; ++i) //�������е���������Ԫ��
			{
				var tempArray=array[i].split("/");//��ÿ������Ԫ���á�/���з�
				var vIndex=tempArray[0]-1;//��ȡ��������ֵ
				var tIndex=tempArray[1]-1;//��ȡ������������ֵ
				var nIndex=tempArray[2]-1;//��ȡ����������ֵ
		  
				alvResult.push(alv[vIndex*3+0]);//����Ӧ���������X������������������б�
				alvResult.push(alv[vIndex*3+1]);//����Ӧ���������y������������������б�
				alvResult.push(alv[vIndex*3+2]);//����Ӧ���������z������������������б�
		  
				altResult.push(alt[tIndex*2+0]);//����Ӧ�������������s����������������������б�
				altResult.push(alt[tIndex*2+1]);//����Ӧ�������������T����������������������б�
	
				alnResult.push(aln[nIndex*3+0]);//����Ӧ������������X�����������������б�
				alnResult.push(aln[nIndex*3+1]);//����Ӧ������������y�����������������б�
				alnResult.push(aln[nIndex*3+2]);//����Ӧ������������z�����������������б�
			}
		}
	}
	return new ObjectData(alvResult.length/3,alvResult,altResult,alnResult);//����������ObjectData�Ķ���
}