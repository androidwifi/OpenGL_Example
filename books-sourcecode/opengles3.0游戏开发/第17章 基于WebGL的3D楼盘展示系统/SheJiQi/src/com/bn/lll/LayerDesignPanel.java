package com.bn.lll;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

//��ͼ��������
public class LayerDesignPanel extends JPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -250254695630556258L;
	
	MainFrame father;	
	Item[][] itemArray=new Item[ConstantUtil.Row][ConstantUtil.Col];
	
	LayerViewPanel lvp=new LayerViewPanel(this);
	JScrollPane jsp=new JScrollPane(lvp);

	JList jl=new JList();
	JScrollPane jspL=new JScrollPane(jl);
	
	JButton jcb=new JButton("���ز�");
	
	JButton jbSaveLayer=new JButton("�����");
	JButton jbclear=new JButton("���");
	
	JButton jbLoadLayer1=new JButton("��Ʋ�");
	
	JButton jbCreate=new JButton("���ɵ�ͼ�ļ�");
	JButton jbLoadAll=new JButton("ȫ�����ϵ�ǰѡ��");
	
	JTextField jtfCengMing=new JTextField("Layer");
	
	final int FileNum=10;//�ļ����������
	
	public LayerDesignPanel(MainFrame father)
	{
		this.father=father;
		this.setLayout(null);
		
		jsp.setBounds(8,8,1200,620);
		this.add(jsp);
		
		jspL.setBounds(1220,10,120,200);
		this.add(jspL);		
		jl.setCellRenderer(new MyCellRenderer());
		
		jcb.setBounds(1220,240,120,20);//���ز�
		this.add(jcb);
		jcb.addActionListener(this);
		
		jbLoadLayer1.setBounds(1220,280,120,20);//��Ʋ�
		this.add(jbLoadLayer1);
		jbLoadLayer1.addActionListener(this);
		
		jbSaveLayer.setBounds(1220,340,120,20);//�����
		this.add(jbSaveLayer);
		jbSaveLayer.addActionListener(this);
		
		jbLoadAll.setBounds(1220,370,120,20);//ƽ��
		this.add(jbLoadAll);
		jbLoadAll.addActionListener(this);
		
		jbCreate.setBounds(1220,400,120,20);//������ͼ
		this.add(jbCreate);
		jbCreate.addActionListener(this);		
		
		jtfCengMing.setBounds(1220,430,120,20);//�ļ���
		this.add(jtfCengMing);
		
		jbclear.setBounds(1220,460,120,20);//���
		this.add(jbclear);
		jbclear.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jbclear)//���
		{
			lvp.flag=false;//������ͼƬ
			lvp.repaint();
		}
		else if(e.getSource()==jbSaveLayer)//����
		{
			try
			{
				//�����ͼ
				FileOutputStream fout=new FileOutputStream("data/"+jtfCengMing.getText()+".wyf");
				ObjectOutputStream oout=new ObjectOutputStream(fout);
				oout.writeObject(itemArray);
				oout.close();
				fout.close();
				System.out.println("�����ͼ�ɹ�");
			}
			catch(Exception ea)
			{
				ea.printStackTrace();
			}			
		}else if(e.getSource()==jbLoadLayer1)//��Ʋ�
		{
			try
			{
				FileInputStream fin=new FileInputStream("data/"+jtfCengMing.getText()+".wyf");
				ObjectInputStream oin=new ObjectInputStream(fin);
				itemArray=(Item[][])oin.readObject();		
				oin.close();
				fin.close();
				this.flush();
				System.out.println("��Ʋ�");
			}
			catch(Exception ea)
			{
				ea.printStackTrace();
			}
			lvp.flag=true;	
			lvp.repaint();
		}else if(e.getSource()==jcb)//���ز�ͼ
		{
			try
			{
				FileInputStream fin=new FileInputStream("data/"+jtfCengMing.getText()+".wyf");
				ObjectInputStream oin=new ObjectInputStream(fin);
				itemArray =(Item[][])oin.readObject();		
				oin.close();
				fin.close();
				this.flush();
				System.out.println("���ز�ͼ�ɹ�");
			}
			catch(Exception ea)
			{
				ea.printStackTrace();
			}
			lvp.flag=true;//����ͼƬ
			lvp.repaint();		
		}else if(e.getSource()==jbLoadAll)//ȫ��ƽ��
		{//ȫ�����ϵ�ǰѡ��
			for(int row=0; row<ConstantUtil.Row; row++)
			{
				for(int col=0; col<ConstantUtil.Col; col++)
				{
					Item item=((Item)(jl.getSelectedValue())).clone();
					itemArray[row][col]=item;
					if(item!=null)
					{
						item.setPosition(col,row);
					}	
					
				}
			}
			lvp.flag=true;
			lvp.repaint();
		}else if(e.getSource()==jbCreate)
		{//�������ɵ�ͼ�ļ���ť
			int index=0;//ȷ���ļ�����
			try
			{	
				for(;index<FileNum;index++)
				{
					File f=new File("data/maps"+index+".js");
					if(!f.exists())
					{
						f.createNewFile();//�����ļ���
						break;
					}
				}
					
				
				//�����JS�ļ�============================================
			    StringBuilder sb=new StringBuilder();
			    //�������������Ϣ
			    sb.append("var mapsData"+index+"=new Array(\n");
			    
				int totalBlocks=0;//�������
				for(int i=0; i<ConstantUtil.Row; i++)
				{
					for(int j=0; j<ConstantUtil.Col; j++)
					{
						Item item=itemArray[i][j];
						if(item != null)
						{
							totalBlocks++;
						}
					}
				}
				System.out.println("�������totalBlocks=="+totalBlocks);
				for(int i=0; i<ConstantUtil.Row; i++)
				{
					for(int j=0; j<ConstantUtil.Col; j++)
					{
						Item item=itemArray[i][j];
						if(item != null)
						{
							int col = item.col;//Ԫ�صĵ�ͼ��
							int row = item.row;//Ԫ�صĵ�ͼ��
							String leiMing = "\""+item.leiMing+"\"";//����
					
							sb.append(col);	//��Ԫ�ص���
							sb.append(",");//�ָ��
		
							sb.append(row);	//��Ԫ�ص���
							sb.append(",");//�ָ��
							
							sb.append(leiMing.trim());//���ͼƬ��
							if(totalBlocks>1)//�����������1
					    	{
					    		sb.append(",");//�ָ��
					    	}
					    	totalBlocks--;//��������һ
						}							
				    }
				}
				sb.append(");\n");
				
				FileWriter fw=new FileWriter("data/maps"+index+".js");	
			    fw.write(sb.toString());
			    fw.close();
			    System.out.println("OK.........");
			}
			catch(Exception ea)
			{
				ea.printStackTrace();
			}
		}
	}
	
	public void flush()
	{
		ArrayList<Item> alItem=father.idp.alItem;
		jl.setListData(alItem.toArray());
	}

	public static void main(String args[])
	{
		new MainFrame();
	}
}