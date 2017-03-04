 	  function ObjectData
  	(
  	  vertexCountIn,verticesIn,normalsIn
  	)
  	{  	 
  	  this.vertexCount=vertexCountIn;
  	  this.vertices=verticesIn;
  	  this.normals=normalsIn;  	  
  	}	
  	
    function fromObjStrToObjectData(objStr)
		{//����obj�ı����ݵĺ���
		  //ԭʼ���������б�--ֱ�Ӵ�obj�ļ��м���
    	var alv=new Array();
    	//������������б�--������֯��
    	var alvResult=[];
    	//ԭʼ�������б�
    	var aln=new Array();    
    	//���������б�
    	var alnResult=[];     	
    	
    	var lines = objStr.split("\n");//��obj�е������Ի��з�Ϊ�ָ����ֿ�
    	
    	for (var lineIndex in lines) 
    	{//����ÿһ���ı�
        var line = lines[lineIndex].replace(/[ \t]+/g, " ").replace(/\s\s*$/, "");//ȥ���հ׷�
        if (line[0] == "#")
        {//�������#��ͷ��
           continue;//��������
        }
        var array = line.split(" ");//��һ�������Կո��Ϊ�ָ����ֿ�
        if (array[0] == "v") //�ж��Ƿ�Ϊ����������Ϣ��
        {//��ȡ�������겢����ԭʼ���������б�
            alv.push(parseFloat(array[1]));
            alv.push(parseFloat(array[2]));
            alv.push(parseFloat(array[3]));
        }
        else if (array[0] == "vn") //�ж��Ƿ�Ϊ���㷨������Ϣ��
        {//��ȡ���㷨����������ԭʼ���㷨�����б�
            aln.push(parseFloat(array[1]));
            aln.push(parseFloat(array[2]));
            aln.push(parseFloat(array[3]));
        }
        else if (array[0] == "f") //�ж��Ƿ�Ϊ��װ�����������Ϣ��
        {
           if (array.length != 4)
           {//��������������
                alert("array.length != 4");//������ʾ��Ϣ
                continue;//��������
           }
           for (var i = 1; i < 4; ++i) //�������е���������Ԫ��
           {
              var tempArray=array[i].split("/");//��ÿ������Ԫ���á�/���з�
              var vIndex=tempArray[0]-1;//��ȡ������������ֵ
              var nIndex=tempArray[2]-1;//��ȡ���㷨��������ֵ
              
			  //����Ӧ���������������������������б�
              alvResult.push(alv[vIndex*3+0]);
              alvResult.push(alv[vIndex*3+1]);
              alvResult.push(alv[vIndex*3+2]);
        
			  //����Ӧ����������������������������б�
              alnResult.push(aln[nIndex*3+0]);
              alnResult.push(aln[nIndex*3+1]);
              alnResult.push(aln[nIndex*3+2]);
           }
        }
      }
      return new ObjectData(alvResult.length/3,alvResult,alnResult);	//������ObjectData�Ķ���
	}