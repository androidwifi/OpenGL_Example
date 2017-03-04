//���ص����ڻ��Ƶ�3D����
function ObjObject
(
 	gl,						  //GL������
	vertexDataIn,    //������������
	vertexNormalIn,   //���㷨��������
	vertexTexCoorIn,	//����������������
	programIn		//��ɫ������id
)
{
	//��ʼ��������������
	this.vertexData=vertexDataIn;   	  
	//�õ���������
	this.vcount=this.vertexData.length/3;
	//���������������ݻ���
	this.vertexBuffer=gl.createBuffer();
	//�󶨶����������ݻ���
	gl.bindBuffer(gl.ARRAY_BUFFER,this.vertexBuffer);
	//�����������������뻺��
	gl.bufferData(gl.ARRAY_BUFFER,new Float32Array(this.vertexData),gl.STATIC_DRAW);  

	//��ʼ�����㷨��������
	this.vertexNormal=vertexNormalIn;  
	//�������㷨�������ݻ���
	this.vertexNormalBuffer=gl.createBuffer();
	//�󶨶��㷨�������ݻ���
	gl.bindBuffer(gl.ARRAY_BUFFER,this.vertexNormalBuffer);
	//�����㷨�����������뻺��
	gl.bufferData(gl.ARRAY_BUFFER,new Float32Array(this.vertexNormal),gl.STATIC_DRAW); 

  
	//��ʼ������������������
	this.vertexTexCoor=vertexTexCoorIn;
	//�������������������ݻ���
	this.vertexTexCoorBuffer=gl.createBuffer();
	//�󶨶��������������ݻ���
	gl.bindBuffer(gl.ARRAY_BUFFER,this.vertexTexCoorBuffer);
	//���������������������뻺��
	gl.bufferData(gl.ARRAY_BUFFER,new Float32Array(this.vertexTexCoor),gl.STATIC_DRAW);  
	  
	//��ʼ����ɫ������id
	this.program=programIn;          
	
	this.drawSelfEarth=function(ms,texture0,texture1)
	{//���Ƶ�������ķ���			
		gl.useProgram(this.program);	//ָ��ʹ��ĳ����ɫ������	
		
		//��ȡ�ܱ任��������id
		var uMVPMatrixHandle=gl.getUniformLocation(this.program, "uMVPMatrix");
		//���ܱ任����������Ⱦ����		
		gl.uniformMatrix4fv(uMVPMatrixHandle,false,new Float32Array(ms.getFinalMatrix())); 

		//��ȡ�任��������id
		var uMMatrixHandle=gl.getUniformLocation(this.program, "uMMatrix");
		//���任����������Ⱦ����	
		gl.uniformMatrix4fv(uMMatrixHandle,false,new Float32Array(ms.currMatrix)); 

		//��ȡ��Դλ������id
		var uLightLocationHandle=gl.getUniformLocation(this.program, "uLightLocation");
		//����Դλ��������Ⱦ����
		gl.uniform3fv(uLightLocationHandle,new Float32Array([sunx,5,sunz]));
		
		//��ȡ�����λ������id
		var uCameraHandle=gl.getUniformLocation(this.program, "uCamera");
		//�������λ��������Ⱦ����
		gl.uniform3fv(uCameraHandle,new Float32Array([ms.cx,ms.cy,ms.cz]));

		//���ö���������������
		gl.enableVertexAttribArray(gl.getAttribLocation(this.program, "aPosition"));        
		//�󶨶����������ݻ���
		gl.bindBuffer(gl.ARRAY_BUFFER, this.vertexBuffer);
		//������ָ��������������
		gl.vertexAttribPointer(gl.getAttribLocation(this.program, "aPosition"), 3, gl.FLOAT, false, 0, 0);   

		//���ö��㷨������������
		gl.enableVertexAttribArray(gl.getAttribLocation(this.program, "aNormal")); 
		//�󶨶��㷨�������ݻ���
		gl.bindBuffer(gl.ARRAY_BUFFER, this.vertexNormalBuffer);
		//������ָ�����㷨��������
		gl.vertexAttribPointer(gl.getAttribLocation(this.program, "aNormal"), 3, gl.FLOAT, false, 0, 0);    

		//���ö�������������������
		gl.enableVertexAttribArray(gl.getAttribLocation(this.program, "aTexCoor")); 
		//�󶨶��������������ݻ���
		gl.bindBuffer(gl.ARRAY_BUFFER, this.vertexTexCoorBuffer);
		//������ָ������������������
		gl.vertexAttribPointer(gl.getAttribLocation(this.program, "aTexCoor"), 2, gl.FLOAT, false, 0, 0); 
		
		gl.activeTexture(gl.TEXTURE0);//����ʹ�õ�������-0
		gl.bindTexture(gl.TEXTURE_2D, texture0);//�󶨰�������
		gl.activeTexture(gl.TEXTURE1);//����ʹ�õ�������-1
		gl.bindTexture(gl.TEXTURE_2D, texture1);//�󶨺�ҹ����
		//��������
		gl.uniform1i(gl.getUniformLocation(this.program, "sTexture"), 0);//����������������Ⱦ���� 
		gl.uniform1i(gl.getUniformLocation(this.program, "sTextureNight"), 1);	 //����ҹ����������Ⱦ����		  

		//�ö��㷨��������
		gl.drawArrays(gl.TRIANGLES, 0, this.vcount); 
	}      

	this.drawSelfMoon=function(ms,texture)
	{//������������ķ���	
	
		gl.useProgram(this.program);//ָ��ʹ��ĳ����ɫ������			
		
		//��ȡ�ܱ任��������id
		var uMVPMatrixHandle=gl.getUniformLocation(this.program, "uMVPMatrix");
		//���ܱ任����������Ⱦ����		
		gl.uniformMatrix4fv(uMVPMatrixHandle,false,new Float32Array(ms.getFinalMatrix())); 

		//��ȡ�任��������id
		var uMMatrixHandle=gl.getUniformLocation(this.program, "uMMatrix");
		//���任����������Ⱦ����	
		gl.uniformMatrix4fv(uMMatrixHandle,false,new Float32Array(ms.currMatrix)); 

		//��ȡ��Դλ������id
		var uLightLocationHandle=gl.getUniformLocation(this.program, "uLightLocation");
		//����Դλ��������Ⱦ����
		gl.uniform3fv(uLightLocationHandle,new Float32Array([sunx,5,sunz]));
		
		//��ȡ�����λ������id
		var uCameraHandle=gl.getUniformLocation(this.program, "uCamera");
		//�������λ��������Ⱦ����
		gl.uniform3fv(uCameraHandle,new Float32Array([ms.cx,ms.cy,ms.cz]));

		//���ö���������������
		gl.enableVertexAttribArray(gl.getAttribLocation(this.program, "aPosition"));        
		//�󶨶����������ݻ���
		gl.bindBuffer(gl.ARRAY_BUFFER, this.vertexBuffer);
		//������ָ��������������
		gl.vertexAttribPointer(gl.getAttribLocation(this.program, "aPosition"), 3, gl.FLOAT, false, 0, 0);   

		//���ö��㷨������������
		gl.enableVertexAttribArray(gl.getAttribLocation(this.program, "aNormal")); 
		//�󶨶��㷨�������ݻ���
		gl.bindBuffer(gl.ARRAY_BUFFER, this.vertexNormalBuffer);
		//������ָ�����㷨��������
		gl.vertexAttribPointer(gl.getAttribLocation(this.program, "aNormal"), 3, gl.FLOAT, false, 0, 0);    

		//���ö�������������������
		gl.enableVertexAttribArray(gl.getAttribLocation(this.program, "aTexCoor")); 
		//�󶨶��������������ݻ���
		gl.bindBuffer(gl.ARRAY_BUFFER, this.vertexTexCoorBuffer);
		//������ָ������������������
		gl.vertexAttribPointer(gl.getAttribLocation(this.program, "aTexCoor"), 2, gl.FLOAT, false, 0, 0);

		
		gl.activeTexture(gl.TEXTURE0);//����ʹ�õ�������-0
		gl.bindTexture(gl.TEXTURE_2D, texture);//������
		//��������
		gl.uniform1i(gl.getUniformLocation(this.program, "sTexture"), 0);//������������Ⱦ����  			  

		//�ö��㷨��������
		gl.drawArrays(gl.TRIANGLES, 0, this.vcount); 
	}      
}