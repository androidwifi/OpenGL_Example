				//��ʼ��WebGL Canvas�ķ���
				function initWebGLCanvas(canvasName)
				{
				    var canvas = document.getElementById(canvasName);//��ȡCanvas����
				    var names = ["webgl", "experimental-webgl", "webkit-3d", "moz-webgl"];
					var context = null;//���������ı���
				    for (var ii = 0; ii < names.length; ++ii) 
				    {//�������ܵ�GL����������
					    try 
					    {
					      context = canvas.getContext(names[ii], null);			//��ȡGL������		      
					    } 
					    catch(e) {}
					    if (context) 
					    {//���ɹ���ȡGL����������ֹѭ��
					      break;
					    }
				    }			    
				    return context;
				}
	
				//���ص�����ɫ���ķ���			
				function loadSingleShader(ctx, shaderScript)
				{
				    if (shaderScript.type == "vertex")//��Ϊ������ɫ��
				        var shaderType = ctx.VERTEX_SHADER;//������ɫ������
				    else if (shaderScript.type == "fragment")//��ΪƬԪ��ɫ��
				        var shaderType = ctx.FRAGMENT_SHADER;//ƬԪ��ɫ������
				    else {//�����ӡ������Ϣ
				        log("*** Error: shader script of undefined type '"+shaderScript.type+"'");
				        return null;
				    }
				
				    // Create the shader object
				    var shader = ctx.createShader(shaderType);//�������ʹ�����ɫ������
				
				    // Load the shader source
				    ctx.shaderSource(shader, shaderScript.text);//������ɫ���ű�
				
				    // Compile the shader
				    ctx.compileShader(shader);//������ɫ��
				
				    // Check the compile status
				    var compiled = ctx.getShaderParameter(shader, ctx.COMPILE_STATUS);//������״̬
				    if (!compiled && !ctx.isContextLost()) {//���������
				        // Something went wrong during compilation; get the error
				        var error = ctx.getShaderInfoLog(shader);//��ȡ������Ϣ
				        log("*** Error compiling shader '"+shaderId+"':"+error);//��ӡ������Ϣ
				        ctx.deleteShader(shader);//ɾ����ɫ������
				        return null;	//���ؿ�
				    }			
				    return shader;
				}	
				
				//���ض�����ɫ����ƬԪ��ɫ�����׼�
				function loadShaderSerial(gl, vshader, fshader)
				{
						//���ض�����ɫ��
				    var vertexShader = loadSingleShader(gl, vshader);
				    //����ƬԪ��ɫ��
				    var fragmentShader = loadSingleShader(gl, fshader);
				
				    //������ɫ������
				    var program = gl.createProgram();
				
				    //��������ɫ����ƬԪ��ɫ���ҽӵ���ɫ������
				    gl.attachShader (program, vertexShader);
				    gl.attachShader (program, fragmentShader);
				
				
				    //������ɫ������
				    gl.linkProgram(program);
				
				    //��������Ƿ�ɹ�
				    var linked = gl.getProgramParameter(program, gl.LINK_STATUS);
				    if (!linked && !gl.isContextLost()) 
				    {//�����Ӳ��ɹ�
				        //��ȡ���ڿ���̨��ӡ������Ϣ
				        var error = gl.getProgramInfoLog (program);
				        log("Error in program linking:"+error);//��ӡ������Ϣ
				
				        gl.deleteProgram(program);//ɾ����ɫ������
				        gl.deleteProgram(fragmentShader);//ɾ��ƬԪ��ɫ��
				        gl.deleteProgram(vertexShader);//ɾ��������ɫ��
				
				        return null;
				    }
				    return program;//������ɫ������
				}