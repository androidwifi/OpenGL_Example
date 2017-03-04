package com.bn.lll;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class MyCellRenderer extends JLabel implements ListCellRenderer {
     /**
	 * 
	 */
	private static final long serialVersionUID = -3186513249885242291L;

	public MyCellRenderer() 
     {
         setOpaque(true);
     }

     public Component getListCellRendererComponent(JList list,
                                                   Object value,
                                                   int index,
                                                   boolean isSelected,
                                                   boolean cellHasFocus) 
     {

         Item item=(Item)value;
         this.setIcon(new ImageIcon(item.img));
         this.setText("���: "+item.w+"�߶�: "+item.h);
         if(isSelected)
         {
         	setBackground(Color.red);//ѡ�е���ɫ
         }
         else
         {
         	setBackground(Color.yellow);//δѡ�е���ɫ
         }
         this.setPreferredSize(new Dimension(180,66));
         
         return this;
     }
}
