package com.bn.pp2;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Sample2_2_Activity extends Activity {
	MediaPlayer mp; // ����MediaPlayer������
	AudioManager am; // ����AudioManager������
	private int maxVolume; // �������ֵ   
	private int currVolume; // ��ǰ����ֵ   
	private int stepVolume; // ÿ�ε�������������   

	@SuppressLint("SdCardPath")
	@Override
	public void onCreate(Bundle savedInstanceState) // ��дonCreate����
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main); //��ת��������
		mp = new MediaPlayer(); // ����MediaPlayerʵ������
		try {
			mp.setDataSource("/sdcard/gsls.mp3"); // ΪMediaPlayer����Ҫ�����ļ���Դ
			mp.prepare(); // MediaPlayer���л���׼��
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE); // ��ȡAudioManager��������
		// ��ȡ�����������   
		maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);  		
		// ÿ�ε������������Ϊ���������1/6   
		stepVolume = maxVolume / 6;  

		Button bstart = (Button) this.findViewById(R.id.Button01); // ��ȡ��ʼ��ť
		bstart.setOnClickListener // Ϊ��ʼ��ť��Ӽ�����
		(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mp.start(); // ����MediaPlayer��start��������������
				Toast.makeText(getBaseContext(), "��ʼ����'��ɽ��ˮ��'",
						Toast.LENGTH_LONG).show();
			}
		});
		Button bpause = (Button) this.findViewById(R.id.Button02); // ��ȡ��ͣ��ť
		bpause.setOnClickListener // Ϊ��ͣ��ť��Ӽ�����
		(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mp.pause(); // ����MediaPlayer��pause������ͣ��������
				Toast.makeText(getBaseContext(), "��ͣ����'��ɽ��ˮ��'",
						Toast.LENGTH_LONG).show();
			}
		});
		Button bstop = (Button) this.findViewById(R.id.Button03); // ��ȡֹͣ��ť
		bstop.setOnClickListener // Ϊֹͣ��ť��Ӽ�����
		(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mp.stop(); // ����MediaPlayer��stop����ֹͣ��������
				try {
					mp.prepare();//����׼��״̬
				} catch (IllegalStateException e) {//�����쳣
					e.printStackTrace();
				} catch (IOException e) {//�����쳣
					e.printStackTrace();
				}
				Toast.makeText(getBaseContext(), "ֹͣ����'��ɽ��ˮ��'",
						Toast.LENGTH_LONG).show();
			}
		});

		Button bUp = (Button) this.findViewById(R.id.Button04); // ��ȡ����������ť
		bUp.setOnClickListener // Ϊ����������ť��Ӽ�����
		(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ��ȡ��ǰ����  
				currVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);    
				//�������������������������ֵ
				int tmpVolume = currVolume + stepVolume; //��ʱ����
				currVolume = tmpVolume < maxVolume ? tmpVolume:maxVolume;
				am.setStreamVolume(AudioManager.STREAM_MUSIC, currVolume,
						AudioManager.FLAG_PLAY_SOUND);
				Toast.makeText(getBaseContext(), "��������",
						Toast.LENGTH_SHORT).show();
			}
		});
		Button bDown = (Button) this.findViewById(R.id.Button05); // ��ȡ��С������ť
		bDown.setOnClickListener // Ϊ��С������ť��Ӽ�����
		(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ��ȡ��ǰ����  
				currVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);    
				//��С����������С��0
				int tmpVolume = currVolume - stepVolume; //��ʱ����
				currVolume = tmpVolume > 0 ? tmpVolume:0;		
				am.setStreamVolume(AudioManager.STREAM_MUSIC, currVolume,
						AudioManager.FLAG_PLAY_SOUND);
				Toast.makeText(getBaseContext(), "��С����",
						Toast.LENGTH_SHORT).show();
			}
		});
	}
}