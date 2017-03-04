package com.bn.lll;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.swing.ImageIcon;

public class Item implements Externalizable
{
	
	Image img;//Ԫ�ص�ͼƬ
	String imgPath;
	int w;//Ԫ�ص�ͼƬ���
	int h;//Ԫ�ص�ͼƬ�߶�
	
	int col;//Ԫ�صĵ�ͼ��
	int row;//Ԫ�صĵ�ͼ��
	
	int pCol;//Ԫ�ص�ռλ��
	int pRow;//Ԫ�ص�ռλ��
	
	String leiMing;//����
	
	public Item()
	{
	}
	
	public Item(Image img,String imgPath,int w,int h,int pCol,int pRow,String leiMing)
	{
		this.img=img;
		this.imgPath=imgPath;
		this.w=w;
		this.h=h;
		this.pCol=pCol;
		this.pRow=pRow;
		this.leiMing = leiMing;
		System.out.println("============="+leiMing+"==============");
	}
	
	public void setPosition(int col,int row)
	{
		this.col=col;
		this.row=row;
	}
	
	public void draw(Graphics g,ImageObserver io)
	{		
		int spanX=ConstantUtil.itemSpanX;
		int spanY=ConstantUtil.itemSpanY;
		
		int x=(col-pCol)*spanX;
		int y=row*spanY+(pRow+1)*spanY-h;
		
		g.drawImage(img,x,y,io);
	}
	
	public Item clone()
	{
		return new Item(img,imgPath,w,h,pCol,pRow,leiMing);
	}
	
	public void writeExternal(ObjectOutput out) throws IOException
	{
		out.writeObject(imgPath);
		out.writeInt(w);
		out.writeInt(h);
		out.writeInt(pCol);
		out.writeInt(pRow);
		out.writeInt(col);
		out.writeInt(row);
		out.writeUTF(leiMing);
	}
	
	public void readExternal(ObjectInput in)throws IOException,ClassNotFoundException
	{
		imgPath=(String)(in.readObject());
		img=new ImageIcon(imgPath).getImage();
		
		w=in.readInt();
		h=in.readInt();
		pCol=in.readInt();
		pRow=in.readInt();
		
		col=in.readInt();
		row=in.readInt();
		
		leiMing=in.readUTF();
	}

	public static void main(String args[])
	{
		new MainFrame();
	}
}
