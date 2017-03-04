function ObjObject(								//�����������������������
	gl,						 					//GL������
	vertexDataIn,    							//������������
	programIn									//��ɫ������id
){
	this.vertexData=vertexDataIn;						//��ʼ��������������
	this.vcount=this.vertexData.length/3;					//�õ���������
	this.vertexBuffer=gl.createBuffer();				//���������������ݻ���
	gl.bindBuffer(gl.ARRAY_BUFFER,this.vertexBuffer); 	//�󶨶����������ݻ���
	//�����������������뻺��
	gl.bufferData(gl.ARRAY_BUFFER,new Float32Array(this.vertexData),gl.STATIC_DRAW);
	this.program=programIn;		//��ʼ����ɫ������id
	
	this.drawSelf=function(ms)//��������ķ���
	{	
		gl.useProgram(this.program);//ָ��ʹ��ĳ����ɫ������
		//��ȡ�ܱ任��������id
		var uMVPMatrixHandle=gl.getUniformLocation(this.program, "uMVPMatrix");
		//���ܱ任����������Ⱦ����
		gl.uniformMatrix4fv(uMVPMatrixHandle,false,new Float32Array(ms.getFinalMatrix()));
		
		gl.enableVertexAttribArray(gl.getAttribLocation(this.program, "aPosition"));//���ö���������������
		gl.bindBuffer(gl.ARRAY_BUFFER, this.vertexBuffer);	//�󶨶����������ݻ���
		//������ָ��������������
		gl.vertexAttribPointer(gl.getAttribLocation(this.program,"aPosition"),3,gl.FLOAT,false,0, 0);
		
		gl.drawArrays(gl.TRIANGLES, 0, this.vcount);		//�ö��㷨��������
	}
}
