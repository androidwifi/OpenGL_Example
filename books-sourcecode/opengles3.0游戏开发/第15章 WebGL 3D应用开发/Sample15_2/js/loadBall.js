  	function loadBallObjFile(url)//�ӷ�������ȡObj�ı����ݵĺ���
		{
		    var req = new XMLHttpRequest();//�첽�������
			//������Ӧ�ص�����������processObjectLoadObj������Ӧ
		    req.onreadystatechange = function () { processBallLoadObj(req) };
		    req.open("GET", url, true);//��GET��ʽ��ָ��URL
		    req.responseType = "text";//������Ӧ����
		    req.send(null);//����HTTP����
		}
		
		function createBall(objDataIn)
		{//����������ƶ���ķ���
		   if(shaderProgArray[0])//�����ɫ���Ѽ������
		   {
				//���������õ�����
				ooTri=new ObjObject(gl,objDataIn.vertices,objDataIn.normals,shaderProgArray[0]); 
		   }
		   else
		   {
		      setTimeout(function(){createBall(objDataIn);},10); //��Ϣ10ms����ִ��
		   }
		}
		
		function processBallLoadObj(req)
		{//����obj�ı����ݵĻص�����
		    if (req.readyState == 4) 
		    {//��״̬Ϊ4
		        var objStr = req.responseText;	//��ȡ��Ӧ�ı�       
		        var dataTemp=fromObjStrToObjectData(objStr);//��obj�ı�����Ϊ���ݶ���	
				
		        createBall(dataTemp);        //�����������������             
		    }
		} 