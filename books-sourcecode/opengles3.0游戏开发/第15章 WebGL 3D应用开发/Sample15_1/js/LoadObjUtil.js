 	function ObjectData(verticesIn)//����������ObjectData
  	{  	 
  	  this.vertices=verticesIn;	//��ʼ�������������ݳ�Ա  	  
  	}	
  	
    function fromObjStrToObjectData(objStr)//����obj�ı����ݵĺ���
	{
		//ԭʼ���������б�--ֱ�Ӵ�obj�ļ��м���
    	var alv=new Array();
    	//������������б�--������֯��
    	var alvResult=[];       	
    	
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
              
              alvResult.push(alv[vIndex*3+0]);//����Ӧ���������X������������������б�
              alvResult.push(alv[vIndex*3+1]);//����Ӧ���������Y������������������б�
              alvResult.push(alv[vIndex*3+2]);//����Ӧ���������Z������������������б�
           }
        }
      }
      return new ObjectData(alvResult);//������ObjectData�Ķ���
	}