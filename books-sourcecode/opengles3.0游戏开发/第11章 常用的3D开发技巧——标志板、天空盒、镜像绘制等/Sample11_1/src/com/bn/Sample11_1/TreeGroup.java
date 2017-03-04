package com.bn.Sample11_1;

import static com.bn.Sample11_1.MySurfaceView.*;
import java.util.ArrayList;
import java.util.List;

public class TreeGroup
{
	TreeForDraw tfd;
	List<SingleTree> alist=new ArrayList<SingleTree>();//����SingleTree�����б�
	
	public TreeGroup(MySurfaceView mv)
	{
		tfd=new TreeForDraw(mv);//����TreeForDraw����
		alist.add(new SingleTree(0,0,0,this));//����һ��SingleTree���󲢼����б�
		alist.add(new SingleTree(8*UNIT_SIZE,0,0,this));
		alist.add(new SingleTree(5.7f*UNIT_SIZE,5.7f*UNIT_SIZE,0,this));
		alist.add(new SingleTree(0,-8*UNIT_SIZE,0,this));
		alist.add(new SingleTree(-5.7f*UNIT_SIZE,5.7f*UNIT_SIZE,0,this));
		alist.add(new SingleTree(-8*UNIT_SIZE,0,0,this));
		alist.add(new SingleTree(-5.7f*UNIT_SIZE,-5.7f*UNIT_SIZE,0,this));
		alist.add(new SingleTree(0,8*UNIT_SIZE,0,this));
		alist.add(new SingleTree(5.7f*UNIT_SIZE,-5.7f*UNIT_SIZE,0,this));
	}
	public void calculateBillboardDirection()
    {//�����б���ÿ����ľ�ĳ���
    	for(int i=0;i<alist.size();i++)
    	{
    		alist.get(i).calculateBillboardDirection();//����ÿ��ֲ��������εĳ���
    	}
    }
    
    public void drawSelf(int texId)
    {//�����б��е�ֲ��
    	for(int i=0;i<alist.size();i++)
    	{
    		alist.get(i).drawSelf(texId);//����SingleTree�����drawSelf��������ֲ��
    	}
    }
}