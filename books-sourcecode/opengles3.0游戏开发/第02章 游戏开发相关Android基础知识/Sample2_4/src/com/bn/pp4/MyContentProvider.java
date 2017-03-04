package com.bn.pp4;
import android.content.ContentProvider; //������ذ�
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
public class MyContentProvider extends ContentProvider { // �̳�ContentProvider
	private static final UriMatcher um; // ����Uriƥ������
	static {
		um = new UriMatcher(UriMatcher.NO_MATCH); // ����UriMatcher
		um.addURI("com.bn.pp4.provider.student", "stu", 1); // ����ƥ���ַ���
	}
	SQLiteDatabase sld; // ����SQLiteDatabase����
	@Override
	public String getType(Uri uri) {
		return null;
	}
	@Override	// �������ݿ��query����ʱ���Զ����ø÷���
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		switch (um.match(uri)) {// ��ƥ��ɹ�
		case 1: // ִ�в�������ȡCursor��������
			Cursor cur = sld.query("student", projection, selection,
					selectionArgs, null, null, sortOrder);
			return cur; // ����Cursor��������
		}
		return null;
	}
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {// ��ʵ��
		return 0;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {// ��ʵ��
		return null;
	}
	@Override
	public boolean onCreate() { // �������ݿ�ʱ�Զ����ø÷���
		sld = SQLiteDatabase.openDatabase(
				"/data/data/com.bn.pp4/mydb", // ���ݿ�����·��
				null, // �α깤����Ĭ��Ϊnull
				SQLiteDatabase.OPEN_READWRITE| 
				SQLiteDatabase.CREATE_IF_NECESSARY // ��д�����������򴴽�
		);
		return false;
	}
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {// ��ʵ��
		return 0;
	}
}
