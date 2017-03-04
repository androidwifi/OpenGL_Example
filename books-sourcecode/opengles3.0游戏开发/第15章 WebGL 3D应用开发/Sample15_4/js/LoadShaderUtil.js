function shaderObject(typeIn,textIn)//����shaderObject��
{
   this.type=typeIn;//��ʼ��type��Ա����
   this.text=textIn;//��ʼ��text��Ա����
}

function processLoadShader(req,index)//������ɫ���ű����ݵĻص�����
{
	if (req.readyState == 4) //��״̬Ϊ4
	{
		var shaderStr = req.responseText;//��ȡ��Ӧ�ı�	
			var shaderStrA=shaderStr.split("<#BREAK_BN#>");	//�÷ָ���<#BREAK_BN#>�з�
			var vertexShader=new shaderObject("vertex",shaderStrA[0]);//������ɫ���ű�����
			var fragmentShader=new shaderObject("fragment",shaderStrA[1]);//ƬԪ��ɫ���ű�����					
			shaderProgArray[index]=loadShaderSerial(gl,vertexShader, fragmentShader);//������ɫ��
	}
}

//������ɫ���ķ���
//��һ������Ϊ��ɫ���ű�·�����ڶ�������Ϊ��ɫ�������ñ��
function loadShaderFile(url,index)//�ӷ�����������ɫ���ű��ĺ���
{
	var req = new XMLHttpRequest();//����XMLHttpRequest����
	req.onreadystatechange = function () //������Ӧ�ص�����
	{ processLoadShader(req,index) };//����processLoadShader������Ӧ
	req.open("GET", url, true);//��GET��ʽ��ָ��URL
	req.responseType = "text";//������Ӧ����
	req.send(null);//����HTTP����
}

