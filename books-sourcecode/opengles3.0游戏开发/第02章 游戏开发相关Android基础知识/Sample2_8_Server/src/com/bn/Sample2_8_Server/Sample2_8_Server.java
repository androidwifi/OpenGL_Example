package com.bn.Sample2_8_Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//��������
public class Sample2_8_Server
{
	static ServerSocket sSocket;// ServerSocket������
	public static void main(String[] args)
	{
		try
		{
			sSocket=new ServerSocket(8877);//����ServerSocket����
			System.out.println("����8877�ӿ�......");//��ӡ��Ϣ
			while(true)//������������һֱѭ�������ڲ�ͬ�Ŀͻ���
			{
				Socket socket=sSocket.accept();//����Socket����
				DataInputStream diStream=new DataInputStream(socket.getInputStream());
				DataOutputStream dotStream=new DataOutputStream(socket.getOutputStream());
				System.out.println("�ͻ�����Ϣ��"+diStream.readUTF());
				dotStream.writeUTF("�ɹ����ӷ�������");//д�뵽�������
				diStream.close();//�ر�������
				dotStream.close();//�ر������
				socket.close();//�ر�Socket�׽���
			}
		} 
		catch(Exception e)
		{
			e.printStackTrace();//���񲢴�ӡ�쳣��Ϣ
		}
	}
}
