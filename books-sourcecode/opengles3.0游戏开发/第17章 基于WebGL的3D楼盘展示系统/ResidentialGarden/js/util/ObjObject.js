//���ص����ڻ��Ƶ�3D����
function ObjObject
(
	gl,				//GL������
	vertexDataIn,   //������������
	vertexNormalIn, //���㷨��������
	vertexTexCoorIn,//����������������
	programIn		//��ɫ���������
)
{
	//���ն�������
	this.vertexData=vertexDataIn;   	  
	//�õ���������
	this.vcount=this.vertexData.length/3;
	//�����������ݻ���
	this.vertexBuffer=gl.createBuffer();
	//�������������뻺��
	gl.bindBuffer(gl.ARRAY_BUFFER,this.vertexBuffer);
	gl.bufferData(gl.ARRAY_BUFFER,new Float32Array(this.vertexData),gl.STATIC_DRAW); 

	//���ն��㷨��������
	this.vertexNormal=vertexNormalIn;  
	//�������㷨�������ݻ���
	this.vertexNormalBuffer=gl.createBuffer();
	//�����㷨�����������뻺��
	gl.bindBuffer(gl.ARRAY_BUFFER,this.vertexNormalBuffer);
	gl.bufferData(gl.ARRAY_BUFFER,new Float32Array(this.vertexNormal),gl.STATIC_DRAW); 

	//���ն���������������
	this.vertexTexCoor=vertexTexCoorIn;
	//���������������껺��
	this.vertexTexCoorBuffer=gl.createBuffer();
	//���������������������뻺��
	gl.bindBuffer(gl.ARRAY_BUFFER,this.vertexTexCoorBuffer);
	gl.bufferData(gl.ARRAY_BUFFER,new Float32Array(this.vertexTexCoor),gl.STATIC_DRAW);	    	  

	//������ɫ������
	this.program=programIn; 

	this.drawSelf=function(Oms,texture)
	{		
		Oms.pushMatrix(); 
		gl.useProgram(this.program);			
		//�����ܾ���
		var uMVPMatrixHandle=gl.getUniformLocation(this.program, "uMVPMatrix");   
		gl.uniformMatrix4fv(uMVPMatrixHandle,false,new Float32Array(Oms.getFinalMatrix())); 

		//����任����
		var uMMatrixHandle=gl.getUniformLocation(this.program, "uMMatrix");
		gl.uniformMatrix4fv(uMMatrixHandle,false,new Float32Array(Oms.currMatrix)); 
	  
		//�����Դλ��
		var uLightLocationHandle=gl.getUniformLocation(this.program, "uLightLocation");
		gl.uniform3fv(uLightLocationHandle,new Float32Array([lightManager.lx,lightManager.ly,lightManager.lz]));
		//[0,500000,0]));
	  
		//���������λ��
		var uCameraHandle=gl.getUniformLocation(this.program, "uCamera");
		gl.uniform3fv(uCameraHandle,new Float32Array([0,0,15]));
	  
		//���ö�������
		gl.enableVertexAttribArray(gl.getAttribLocation(this.program, "aPosition"));        
		//����������������Ⱦ����
		gl.bindBuffer(gl.ARRAY_BUFFER, this.vertexBuffer);
		gl.vertexAttribPointer(gl.getAttribLocation(this.program, "aPosition"), 3, gl.FLOAT, false, 0, 0);   

		//���÷���������
		gl.enableVertexAttribArray(gl.getAttribLocation(this.program, "aNormal")); 
		//�����㷨��������������Ⱦ����
		gl.bindBuffer(gl.ARRAY_BUFFER, this.vertexNormalBuffer);
		gl.vertexAttribPointer(gl.getAttribLocation(this.program, "aNormal"), 3, gl.FLOAT, false, 0, 0);    

		//����������������
		gl.enableVertexAttribArray(gl.getAttribLocation(this.program, "aTexCoor")); 
		//������������������������Ⱦ����
		gl.bindBuffer(gl.ARRAY_BUFFER, this.vertexTexCoorBuffer);
		gl.vertexAttribPointer(gl.getAttribLocation(this.program, "aTexCoor"), 2, gl.FLOAT, false, 0, 0);        
	  
		//��������
		gl.uniform1i(gl.getUniformLocation(this.program, "sTexture"), 0);

		//������
		gl.activeTexture(gl.TEXTURE0);
		gl.bindTexture(gl.TEXTURE_2D, texture);              

		//�ö��㷨��������
		gl.drawArrays(gl.TRIANGLES, 0, this.vcount); 
	  
		Oms.popMatrix();
	}      
}