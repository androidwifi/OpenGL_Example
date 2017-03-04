package com.bn.pp6;

import java.io.File; //������ذ�
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Sample2_6_Activity extends Activity {// ����Activity
	String currPath; // ��ǰ·���ַ���
	String rootPath = "/"; // ��Ŀ¼·��
	TextView currDirTV;//��ʾ��ǰ·����TextView
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main); // ����layout
		final ListView lv = (ListView) this.findViewById(R.id.lv); // ��ȡListView
		Button back = (Button) this.findViewById(R.id.back); // ��ȡ���ذ�ť
		final File[] files = getFiles(rootPath); // ����getFiles������ȡ��Ŀ¼���ļ��б�
		currDirTV = (TextView) this.findViewById(R.id.currDirTV); // ��ȡListView
		currPath = rootPath;
		currDirTV.setText("��ǰ·����" + currPath);//���õ�ǰ·��
		initListView(files, lv); // ��ʼ����ʾ�б�
		back.setOnClickListener // ���ذ�ť������
		(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!currPath.equals(rootPath)) {// ����ǰ·�����Ǹ�Ŀ¼�����ص���һ��Ŀ¼
					File f = new File(currPath); // ��ȡ��ǰ·���µ��ļ��б�
					f = f.getParentFile(); // ��ȡ��ǰ·�����ϲ�·��
					currPath = f.getPath(); // ���ĵ�ǰ·��
					currDirTV.setText("��ǰ·����" + currPath);//���õ�ǰ·��
					initListView(getFiles(currPath), lv); // ��ʼ����ʾ�б�
				}
			}
		});
	}
	// ��ȡ��ǰĿ¼�µ��ļ��б�ķ���
	public File[] getFiles(String filePath) {
		File[] files = new File(filePath).listFiles();// ��ȡ��ǰĿ¼�µ��ļ��б�
		return files; // �����ļ��б�
	}
	// ��ʼ��ListView������ʾ	
	public void initListView(final File[] files, final ListView lv) {
		// ���ļ��б�Ϊ��ʱ
		if (files != null) {
			if (files.length == 0) {// ��ǰĿ¼Ϊ��
				File f = new File(currPath); // ��ȡ��ǰ·����Ӧ�ļ��б�
				f = f.getParentFile(); // ��ȡ�ϲ�·��
				currPath = f.getPath(); // ��¼��ǰ·��
				currDirTV.setText("��ǰ·����" + currPath);//���õ�ǰ·��
				Toast.makeText(this, "���ļ���Ϊ�գ���", Toast.LENGTH_SHORT).show();
			} else {
				BaseAdapter ba = new BaseAdapter()// ����������
				{
					@Override
					public int getCount() {
						return files.length;
					}

					@Override
					public Object getItem(int position) {
						return null;
					}

					@Override
					public long getItemId(int position) {
						return 0;
					}

					@Override
					public View getView(int arg0, View arg1, ViewGroup arg2) {
						LinearLayout ll = new LinearLayout(
								Sample2_6_Activity.this); // ����LinearLayout
						ll.setOrientation(LinearLayout.VERTICAL); // ��ֱ����
						ll.setPadding(5, 5, 5, 5); // ��������
						TextView tv = new TextView(Sample2_6_Activity.this); // ����TextView
						tv.setTextColor(Color.BLACK); // ����������ɫ
						tv.setText(files[arg0].getName()); // �������Ϊ�ļ�����
						tv.setGravity(Gravity.LEFT); // �����
						tv.setTextSize(16); // �����С
						ll.addView(tv); // ���TextView
						return ll; // ����LinearLayout
					}
				};
				lv.setAdapter(ba); // ΪListView����������
				lv.setOnItemClickListener // ΪListView��Ӽ�����
				(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						currPath = files[arg2].getPath(); // ��ȡ������ļ����Ƶĵ�ǰ·��
						currDirTV.setText("��ǰ·����" + currPath);//���õ�ǰ·��
						File[] fs = getFiles(currPath); // ��ȡ��ǰ·�����ļ��б�
						initListView(fs, lv); // ��ʼ��ListView
					}
				});
			}
		} else {
			File f = new File(currPath); // ��ȡ��ǰ�ļ��б��·����Ӧ���ļ�
			f = f.getParentFile(); // ��ȡ��Ŀ¼�ļ�
			currPath = f.getPath(); // ��¼��ǰ�ļ��б�·��
			currDirTV.setText("��ǰ·����" + currPath);//���õ�ǰ·��
			Toast.makeText(this, "��Ŀ¼�����ļ��л���Ȩ�޷��ʣ�", Toast.LENGTH_SHORT).show();
		}
	}
}