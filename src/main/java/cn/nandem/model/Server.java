/**
 * ������������Ϸ�����࣬�����ṩ��Ϸҵ��������н飬������Ϸ�������壬�ṩ�ƶ�����ת���ȹ��� 
 */
package cn.nandem.model;

import cn.nandem.config.GameConfig;
import cn.nandem.utils.SoundPlayer;

/**
 * @author Nandem
 *
 */
public class Server
{
	private DataCarrier carrier;
	private PlayerRunnaer runner;
	private Thread playerThread;

	private class PlayerRunnaer implements Runnable
	{
		@Override
		public void run()
		{
			try
			{
				while(true)
				{
					Thread.sleep(GameConfig.TIME_SLICE * GameConfig.getSpeed());
					drop();
				}
			}
			catch(Exception e)
			{
				// TODO: handle exception
			}
		}
	}

	public Server(DataCarrier carrier)
	{
		this.carrier = carrier;
		runner = new PlayerRunnaer();
		playerThread = new Thread(runner);
		playerThread.setDaemon(true);
	}

	/**
	 * ��ʼ������������Ϸ���߳�
	 */
	public void start()
	{
		if(playerThread.isAlive())
			return;
		playerThread.start();
	}

	/**
	 * �ƶ�
	 * @param xOffset ˮƽƫ����
	 */
	public void move(int xOffset)
	{
		if(carrier.isPause() || carrier.isOver())
			return;
		carrier.moveMino(xOffset, 0);
		SoundPlayer.play("move");
	}

	/**
	 * ����
	 */
	public void drop()
	{
		if(carrier.isPause() || carrier.isOver())
			return;
		if(!carrier.moveMino(0, 1))
			carrier.landed();
	}

	/**
	 * ֱ��
	 */
	public void instant()
	{
		if(carrier.isPause() || carrier.isOver())
			return;
		while(carrier.moveMino(0, 1));
		carrier.landed();
		SoundPlayer.play("instant");
	}

	/**
	 * ��ת
	 */
	public void rotate()
	{
		if(carrier.isPause() || carrier.isOver())
			return;
		carrier.rotateMino();
		SoundPlayer.play("rotate");
	}
	
	/**
	 * ��ͣ
	 */
	public void pause()
	{
		carrier.setPause(!carrier.isPause());

//		SoundPlayer.setBgmPlayFlag(!SoundPlayer.isBgmPlayFlag());
//		SoundPlayer.playLoop();
	}

}
