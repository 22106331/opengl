package com.opengl.catcher.util.manager;

import java.util.HashMap;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.opengl.catcher.MainActivity;
import com.opengl.catcher.R;

import static com.opengl.catcher.constant.SourceConstant.*;

@SuppressLint("UseSparseArrays")
public class SoundManager
{
	SoundPool sp ;
	HashMap<Integer	,Integer> hm ;
	MainActivity activity ;

	public MediaPlayer mp  ;
	public SoundManager(MainActivity activity)
	{
		this.activity = activity  ;
		initSound();
	}
	
	//���� ��ʼ��
	
	public void initSound()
	{
		sp = new SoundPool
		(4, 
		AudioManager.STREAM_MUSIC, 
		100
		);
		hm = new HashMap<Integer, Integer>();  
		hm.put(SOUND_Click, sp.load(activity,R.raw.click, 1));//�����ť
		hm.put(SOUND_Back, sp.load(activity, R.raw.back, 1));//������ذ�ť
		hm.put(SOUND_DropMoney, sp.load(activity, R.raw.dropmoney, 1));//��ҵ�����Ч
	}
	public void playBackGroundMusic(Activity ac,int Id)
	{
		if(MainActivity.sound.mp!=null){
			MainActivity.sound.mp.pause();
		    MainActivity.sound.mp=null;
		}
	 	 if(MainActivity.sound.mp==null)
	 	 {
 			 MainActivity.sound.mp =  MediaPlayer.create(ac, Id);
 			 MainActivity.sound.mp.setVolume(0.2f, 0.2f);//����������������
 			 MainActivity.sound.mp.setLooping(true);//ѭ������
 			 MainActivity.sound.mp.start();
	 	 }
	}
	public void playMusic(int sound,int loop)
	{
		@SuppressWarnings("static-access")
		AudioManager am = (AudioManager)activity.getSystemService(activity.AUDIO_SERVICE);
		float steamVolumCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC)  ;
		float steamVolumMax = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)  ;
		float volum = steamVolumCurrent/steamVolumMax  ;
		sp.play(hm.get(sound), volum, volum, 1	, loop, 1)  ;//����
	}
	

	long preTimeStamp=0;
	public void playGameMusic(int sound,int loop)
	{
		long currTimeStamp=System.nanoTime();
		if(currTimeStamp-preTimeStamp<500000000L)
		{
			return;
		}
		preTimeStamp=currTimeStamp;
		@SuppressWarnings("static-access")
		AudioManager am = (AudioManager)activity.getSystemService(activity.AUDIO_SERVICE);
		float steamVolumCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC)  ;//��õ�ǰ����
		float steamVolumMax = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)  ;//����������
		float volum = steamVolumCurrent/steamVolumMax  ;//�����������ŵ�����
		sp.play(
				hm.get(sound), //������Դid
				volum, //����������
				volum, //����������
				1	, //���ȼ�
				loop, //ѭ������ -1������Զѭ��
				1//�ط��ٶ�0.5f��2.0f֮��
				);//����
	}	
	
	public void stopGameMusic(int sound)
	{
		sp.pause(sound);
		sp.stop(sound);
		sp.setVolume(sound, 0, 0);
	}
}

