package com.bn.lll;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//��ͼ��������
public class LayerViewPanel extends JPanel implements MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5919783310156740753L;
	
	LayerDesignPanel father;
	public boolean flag=true;
	
	public LayerViewPanel(LayerDesignPanel father)//������
	{
		this.father=father;
		this.setPreferredSize(new Dimension(ConstantUtil.Rows+10,ConstantUtil.Cols+10));//1930,1450
		this.addMouseListener(this);
	}
	
	public void paint(Graphics g)
	{
		//������
		g.setColor(Color.black);
		g.fillRect(0,0,ConstantUtil.Rows+10,ConstantUtil.Cols+10);
		int spanX=ConstantUtil.itemSpanX;
		int spanY=ConstantUtil.itemSpanY;
		
		if(flag)//���Ƶ�ͼԪ��
		{
			for(int i=0;i<ConstantUtil.Row;i++)
			{
				for(int j=0;j<ConstantUtil.Col;j++)
				{
					Item item=father.itemArray[i][j];//��Ԫ��
					if(item!=null)
					{
						item.draw(g,this);
					}
				}
			}
		}
		
		//����λ��-����
		for(int i=0;i<ConstantUtil.Row;i++)
		{
			g.setColor(Color.green);
			g.drawLine(0,i*spanY,ConstantUtil.Rows,i*spanY);
		}
		
	    //����λ��-������
		for(int i=0;i<ConstantUtil.Col;i++)
		{
			g.setColor(Color.green);
			g.drawLine(i*spanX,0,i*spanX,ConstantUtil.Cols);
		}	
	}
	
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	
	public void mouseClicked(MouseEvent e)
	{
		int mx=e.getX();
		int my=e.getY();
		int spanX=ConstantUtil.itemSpanX;
		int spanY=ConstantUtil.itemSpanY;
		
		int col=(mx/spanX-1)+((mx%spanX==0)?0:1);
		int row=(my/spanY-1)+((my%spanY==0)?0:1);
		
		Item item=((Item)(father.jl.getSelectedValue())).clone();
		
		father.itemArray[row][col]=item;//��Ʋ��ͼ
		if(item!=null)
		{
			item.setPosition(col,row);
		}	
		this.repaint();
	}	
	
	public static void main(String args[])
	{
		new MainFrame();
	}
}