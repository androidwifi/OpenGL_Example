//���ص����ڻ��Ƶ�3D����
	    function ObjObject
	    (
	       gl,						  //GL������
	       vertexDataIn,    //������������
	       vertexNormalIn,   //���㷨��������
		   programIn				//��ɫ���������
	    )
	    {
	    	//���ն�������
	    	this.vertexData=vertexDataIn;   	  
	    	//�õ���������
	    	this.vcount=this.vertexData.length/3;
	    	//�����������ݻ���
	    	this.vertexBuffer=gl.createBuffer();
	    	//�󶨶����������ݻ���
			gl.bindBuffer(gl.ARRAY_BUFFER,this.vertexBuffer);
			//�������������뻺��
			gl.bufferData(gl.ARRAY_BUFFER,new Float32Array(this.vertexData),gl.STATIC_DRAW); 
           
			//���ն��㷨��������
			this.vertexNormal=vertexNormalIn;  
			//�������㷨�������ݻ���
	    	this.vertexNormalBuffer=gl.createBuffer();
	    	//�󶨶��㷨�������ݻ���
			gl.bindBuffer(gl.ARRAY_BUFFER,this.vertexNormalBuffer);
			//�����㷨�����������뻺��
			gl.bufferData(gl.ARRAY_BUFFER,new Float32Array(this.vertexNormal),gl.STATIC_DRAW); 
          
			//��ʼ����ɫ������id
			this.program=programIn;
   
			this.drawSelf=function(ms)
			{		
				gl.useProgram(this.program);//ָ��ʹ��ĳ����ɫ������
				
				//��ȡ�ܱ任��������id
				var uMVPMatrixHandle=gl.getUniformLocation(this.program, "uMVPMatrix");   
				//���ܱ任����������Ⱦ����
				gl.uniformMatrix4fv(uMVPMatrixHandle,false,new Float32Array(ms.getFinalMatrix())); 

				//��ȡ�任��������id
				var uMMatrixHandle=gl.getUniformLocation(this.program, "uMMatrix");
				//���任����������Ⱦ����
				gl.uniformMatrix4fv(uMMatrixHandle,false,new Float32Array(ms.currMatrix)); 
				
				//����Դλ��������Ⱦ����
				var uLightLocationHandle=gl.getUniformLocation(this.program, "uLightLocation");
				gl.uniform3fv(uLightLocationHandle,new Float32Array([-20,25,15]));
              
				//��ȡ��Դλ������id
				var uCameraHandle=gl.getUniformLocation(this.program, "uCamera");
				//�������λ��������Ⱦ����
				gl.uniform3fv(uCameraHandle,new Float32Array([0,0,15]));
				
				//���ö���������������
				gl.enableVertexAttribArray(gl.getAttribLocation(this.program, "aPosition"));        
				//�󶨶����������ݻ���
				gl.bindBuffer(gl.ARRAY_BUFFER, this.vertexBuffer);
				//������ָ��������������
				gl.vertexAttribPointer(gl.getAttribLocation(this.program, "aPosition"), 3, gl.FLOAT, false, 0, 0);   
	        
				//���÷�������������
				gl.enableVertexAttribArray(gl.getAttribLocation(this.program, "aNormal")); 
				//�󶨶��㷨�������ݻ���
				gl.bindBuffer(gl.ARRAY_BUFFER, this.vertexNormalBuffer);
				//������ָ�����㷨��������
				gl.vertexAttribPointer(gl.getAttribLocation(this.program, "aNormal"), 3, gl.FLOAT, false, 0, 0);
              
				//�ö��㷨��������
				gl.drawArrays(gl.TRIANGLES, 0, this.vcount); 
	        }      
	    }