package com.bn.pp4;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
public class Sample2_4_Activity extends Activity {
	SQLiteDatabase sld; // ����SQLiteDatabase����
	@Override
	public void onCreate(Bundle savedInstanceState) { // ����onCreate����
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main); // ����layout
		Button b = (Button) this.findViewById(R.id.Button01); // ��ȡ��/�������ݿⰴť������
		b.setOnClickListener( // Ϊ��/������ť��Ӽ�����
		new OnClickListener() {
			@Override
			public void onClick(View v) {
				createOrOpenDatabase(); // ���÷����򿪻򴴽����ݿ�
			}
		});
		b = (Button) this.findViewById(R.id.Button02);// ��ȡ�ر����ݿⰴť������
		b.setOnClickListener( // Ϊ�رհ�ť��Ӽ�����
		new OnClickListener() {
			@Override
			public void onClick(View v) {
				closeDatabase(); // ���÷����ر����ݿ�
			}
		});
		b = (Button) this.findViewById(R.id.Button03); // ��ȡ��Ӽ�¼��ť������
		b.setOnClickListener( // Ϊ��Ӱ�ť��Ӽ�����
		new OnClickListener() {
			@Override
			public void onClick(View v) {
				insert(); // ���÷��������¼
			}
		});
		b = (Button) this.findViewById(R.id.Button04);// ��ȡɾ����¼��ť������
		b.setOnClickListener( // Ϊɾ����ť��Ӽ�����
		new OnClickListener() {
			@Override
			public void onClick(View v) {
				delete(); // ���÷���ɾ����¼
			}
		});
		b = (Button) this.findViewById(R.id.Button05); // ��ȡ��ѯ��¼��ť������
		b.setOnClickListener( // Ϊ��ѯ��ť��Ӽ�����
		new OnClickListener() {
			@Override
			public void onClick(View v) {
				query(); // ���÷�����ѯ��¼
			}
		});
	}
	public void createOrOpenDatabase() {// ����������ݿ�ķ���
		try {
			sld = SQLiteDatabase.openDatabase(
					"/data/data/com.bn.pp4/mydb", // ���ݿ�����·��
					null, // �α깤����Ĭ��Ϊnull
					SQLiteDatabase.OPEN_READWRITE |
					SQLiteDatabase.CREATE_IF_NECESSARY // ģʽΪ��д�����������򴴽�
			);
			// ���ɴ������ݿ��sql���
			String sql = "create table if not exists student" +
					"(sno char(5),stuname varchar(20)," +
					"sage integer,sclass char(5))";
			sld.execSQL(sql); // ִ��sql���
			Toast.makeText(getBaseContext(), "�ɹ��������ݿ⡣", 
					Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void closeDatabase() {// �ر����ݿ�ķ���
		try {
			sld.close(); // �ر����ݿ�
			Toast.makeText(getBaseContext(), "�ɹ��ر����ݿ⡣", 
					Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void insert() {// �����¼�ķ���
		try  {// ���ɲ����¼��sql���
			String sql = "insert into student values" +
					"('001','Android',22,'283')";
			sld.execSQL(sql); // ִ��sql���
			Toast.makeText(getBaseContext(), "�ɹ�����һ����¼��",
					 Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void delete() {// ɾ����¼�ķ���
		try  {// ����ɾ�����м�¼��sql���
			String sql = "delete from student;";
			sld.execSQL(sql); // ִ��sql���
			Toast.makeText(getBaseContext(), "�ɹ�ɾ�����м�¼��", 
					Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void query(){// ��ѯ�ķ���
		try {// ���ɲ�ѯ��¼��sql���
			String sql = "select * from student where sage>?";
			Cursor cur = sld.rawQuery(sql, new String[] { "20" }); // ��ȡCursor��������
			while (cur.moveToNext()) {// �����ڼ�¼
				String sno = cur.getString(0); // ��ȡ��һ����Ϣ
				String sname = cur.getString(1); // ��ȡ�ڶ�����Ϣ
				int sage = cur.getInt(2); // ��ȡ��������Ϣ
				String sclass = cur.getString(3); // ��ȡ��������Ϣ
				Toast.makeText(
						getBaseContext(),
						"��ѯ���ļ�¼Ϊ��'" + sno + "'\t'" + sname 
						+ "'\t\t'" + sage+ "'\t'" + sclass + "'", 
						Toast.LENGTH_LONG).show();
			}
			cur.close(); // �ر�Cursor
		} catch (Exception e) {
			e.printStackTrace();
}}}