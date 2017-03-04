package com.bn.lll;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.event.*;

//��ͼԪ��������

public class ItemDesignPanel extends JPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7443661768642367771L;
	
	MainFrame father;	
	ItemViewPanel ivp=new ItemViewPanel(this);
	JFileChooser jfc=new JFileChooser(".\\res");	
	Image tempImage;
	String imagePath;
	int pCol;//Ԫ�ص�ռλ��
	int pRow;//Ԫ�ص�ռλ��

	JButton jbIn=new JButton("����ͼƬ");
	JButton jbSave=new JButton("����Ԫ��");
	JButton jbSetZW=new JButton("����ռλ����");
	JButton jbSaveList=new JButton("����Ԫ���б�");
	JButton jbLoadList=new JButton("����Ԫ���б�");
	JButton jbDelete=new JButton("ɾ��Ԫ��");
	
	JTextField jlLeiMing = new JTextField("");
	
	JList jl=new JList();
	JScrollPane jspL=new JScrollPane(jl);
	
	int status=0;//����״̬ 0��ʲô������ 1������ͼƬ  2������Ԫ��  3������ռλ���� 4:����Ԫ���б�  5:����Ԫ���б� 6��ɾ��Ԫ�� 
	
	ArrayList<Item> alItem=new ArrayList<Item>();
	
	public ItemDesignPanel(MainFrame father)
	{
		this.father=father;
		
		this.setLayout(null);
		ivp.setBounds(10,10,512,384);
		this.add(ivp);
		
		jbIn.setBounds(540,10,140,30);
		this.add(jbIn);
		jbIn.addActionListener(this);
		
		jbSave.setBounds(540,60,140,30);
		this.add(jbSave);
		jbSave.addActionListener(this);
		
		jbSetZW.setBounds(540,110,140,30);
		this.add(jbSetZW);
		jbSetZW.addActionListener(this);
		
		jbSaveList.setBounds(540,160,140,30);
		this.add(jbSaveList);
		jbSaveList.addActionListener(this);
		
		jbLoadList.setBounds(540,210,140,30);
		this.add(jbLoadList);
		jbLoadList.addActionListener(this);
		
		jbDelete.setBounds(540,260,140,30);
		this.add(jbDelete);
		jbDelete.addActionListener(this);
		
		jlLeiMing.setBounds(10,420,120,30);
		this.add(jlLeiMing);
		
		jspL.setBounds(700,10,140,360);
		this.add(jspL);		
		jl.setCellRenderer(new MyCellRenderer());
		jl.addListSelectionListener(
		   new ListSelectionListener()
		   {
		   	  public void valueChanged(ListSelectionEvent e)
		   	  {
		   	  	  Item item=(Item)jl.getSelectedValue();
		   	  	  if(item==null){return;}
		   	  	  tempImage=item.img;
				  imagePath=item.imgPath;
				  pCol=item.pCol;//Ԫ�ص�ռλ��
				  pRow=item.pRow;//Ԫ�ص�ռλ��
				  jlLeiMing.setText(item.leiMing);
					
				  ivp.repaint();		   	  	  
		   	  }
		   }
		);
			
	}
	
	public void flush()
	{
		jl.setListData(alItem.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jbIn)
		{//"����ͼƬ"
		   status=1;
		   int jg=jfc.showOpenDialog(this);
		   if(jg==JFileChooser.APPROVE_OPTION)
		   {
		   	  tempImage=(new ImageIcon(jfc.getSelectedFile().getAbsolutePath())).getImage();
		   	  imagePath="res\\"+jfc.getSelectedFile().getName();
		   	  ivp.repaint();
		   }
		}
		else if(e.getSource()==jbSetZW)
		{//����ռλ����
			status=3;
		}
		else if(e.getSource()==jbSave)
		{//����Ԫ��
			status=2;
			if(tempImage!=null)
			{
				Item item=new Item
				(
					tempImage,imagePath,
					tempImage.getWidth(this),
					tempImage.getHeight(this),
					pCol,
					pRow,
					jlLeiMing.getText()
				);
				alItem.add(item);
				this.flush();	
			}
		}
		else if(e.getSource()==jbSaveList)
		{//����Ԫ���б�
			status=4;
			try
			{
				FileOutputStream fout=new FileOutputStream("data/ItemList.wyf");
				ObjectOutputStream oout=new ObjectOutputStream(fout);
				oout.writeObject(alItem);
				oout.close();
				fout.close();
				System.out.println("����Ԫ���б�ɹ�"+fout.getChannel());
			}
			catch(Exception ea)
			{
				ea.printStackTrace();
			}
		}
		else if(e.getSource()==jbLoadList)
		{//����Ԫ���б�
			status=5;
			try
			{
				FileInputStream fin=new FileInputStream("data/ItemList.wyf");
				ObjectInputStream oin=new ObjectInputStream(fin);
				alItem =(ArrayList<Item>)oin.readObject();
				oin.close();
				fin.close();
				this.flush();
			}
			catch(Exception ea)
			{
				ea.printStackTrace();
			}
		}
		else if(e.getSource()==jbDelete)
		{//ɾ��Ԫ��
			status=6;

			if(jl.getSelectedIndex() != -1){
				alItem.remove(jl.getSelectedIndex());
				tempImage = null;
				imagePath = null;
				this.flush();
				ivp.repaint();	
			}
		}
	}
	
	public static void main(String args[])
	{
		new MainFrame();
	}
}