/**
 * 功能描述：声音播放类，播放游戏中用的背景音乐和各种音效 ，提供对外接口
 */
package cn.nandem.utils;

import java.io.FileInputStream;

import sun.audio.*;

/**
 * @author Nandem
 */
public class SoundPlayer
{
	private static boolean soundPlayFlag = true;
	private static boolean bgmPlayFlag = true;
	private static boolean bgmIsStop = false;
	private static ContinuousAudioDataStream continuousAudioDataStream;

	public static void playLoop()
	{
		if(bgmPlayFlag)
			playBGM();
		else
			stopBGM();
	}

	public static void play(String soundName)
	{
		if(!soundPlayFlag)
			return;
		try
		{
			String fileName = String.format("audio/default/%s.wav", soundName);
			FileInputStream file = new FileInputStream(fileName);
			AudioStream as = new AudioStream(file);
			AudioPlayer.player.start(as);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void playBGM()
	{
		if(continuousAudioDataStream == null)
		{
			try(AudioStream as = new AudioStream(new FileInputStream("audio/default/bg.wav")))
			{
				// String fileName = "audio/default/bg.wav";
				// FileInputStream file = new FileInputStream(fileName);
				// AudioStream as = new AudioStream(file);
				AudioData data = as.getData();
				continuousAudioDataStream = new ContinuousAudioDataStream(data);
			}catch(Exception e){e.printStackTrace();}
		}
		AudioPlayer.player.start(continuousAudioDataStream);

	}

	private static void stopBGM()
	{
		if(continuousAudioDataStream != null)
		{
			AudioPlayer.player.stop(continuousAudioDataStream);
		}
		if(bgmIsStop)
			continuousAudioDataStream = null;
			
	}

	public static boolean isSoundPlayFlag()
	{
		return soundPlayFlag;
	}

	public static void setSoundPlayFlag(boolean soundPlayFlag)
	{
		SoundPlayer.soundPlayFlag = soundPlayFlag;
	}

	public static boolean isBgmPlayFlag()
	{
		return bgmPlayFlag;
	}

	public static void setBgmPlayFlag(boolean bgmPlayFlag)
	{
		SoundPlayer.bgmPlayFlag = bgmPlayFlag;
	}

	public static void setBgmIsStop(boolean bgmIsStop)
	{
		SoundPlayer.bgmIsStop = bgmIsStop;
	}

	public static boolean isBgmIsStop()
	{
		return bgmIsStop;
	}
}
