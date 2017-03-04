package com.bn.lll;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame extends JFrame
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5427052287128029874L;
	
	JTabbedPane jtb=new JTabbedPane();
	ItemDesignPanel idp=new ItemDesignPanel(this);//Ԫ��������
	LayerDesignPanel ldp=new LayerDesignPanel(this);//��Ʋ�
	
	public MainFrame()
	{
		this.setTitle("��ͼ�����");
		
		this.add(jtb);
		jtb.add("Item���",idp);
		jtb.add("Layer���",ldp);
		jtb.addChangeListener(
		   new ChangeListener()
		   {
		   	  public void stateChanged(ChangeEvent e)
		   	  {
		   	  	ldp.flush();
		   	  }
		   }
		);
		
		this.setBounds(0,0,1360,720);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String args[])
	{
		new MainFrame();
	}
}
