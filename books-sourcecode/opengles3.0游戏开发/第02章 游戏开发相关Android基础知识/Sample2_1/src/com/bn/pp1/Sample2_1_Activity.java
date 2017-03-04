package com.bn.pp1;

import java.util.HashMap;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Sample2_1_Activity extends Activity {
	SoundPool sp; // ����SoundPool������
	HashMap<Integer, Integer> hm; // ����HashMap����������ļ�
	int currStreamId;// ��ǰ�����ŵ�streamId

	@Override
	// ��дonCreate����
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main); //��ת��������
		initSoundPool(); // ��ʼ�������صķ���
		Button b1 = (Button) this.findViewById(R.id.Button01); // ��ȡ���Ű�ť
		b1.setOnClickListener // Ϊ���Ű�ť��Ӽ�����
		(new OnClickListener() {
			@Override
			public void onClick(View v) {
				playSound(1, 0); // ����1��������Դ���Ҳ���һ��
				// ��ʾ���ż�ʱ��Ч
				Toast.makeText(getBaseContext(), "���ż�ʱ��Ч", Toast.LENGTH_SHORT)
						.show();
			}
		});
		Button b2 = (Button) this.findViewById(R.id.Button02); // ��ȡֹͣ��ť
		b2.setOnClickListener // Ϊֹͣ��ť��Ӽ�����
		(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sp.stop(currStreamId); // ֹͣ���ڲ��ŵ�ĳ������
				// ��ʾֹͣ����
				Toast.makeText(getBaseContext(), "ֹͣ���ż�ʱ��Ч", Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	// ��ʼ�������صķ���
	public void initSoundPool() {
		sp = new SoundPool(4, AudioManager.STREAM_MUSIC, 0); // ����SoundPool����
		hm = new HashMap<Integer, Integer>(); // ����HashMap����
		hm.put(1, sp.load(this, R.raw.musictest, 1)); // ���������ļ�musictest��������Ϊ1����������hm��
	}

	// ���������ķ���
	public void playSound(int sound, int loop) { // ��ȡAudioManager����
		AudioManager am = (AudioManager) this
				.getSystemService(Context.AUDIO_SERVICE);
		// ��ȡ��ǰ����
		float streamVolumeCurrent = am
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		// ��ȡϵͳ�������
		float streamVolumeMax = am
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		// ����õ���������
		float volume = streamVolumeCurrent / streamVolumeMax;
		// ����SoundPool��play���������������ļ�
		currStreamId = sp.play(hm.get(sound), volume, volume, 1, loop, 1.0f);
	}
}