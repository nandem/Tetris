/**
 * 功能描述：游戏服务类，对外提供游戏业务操作的中介，包含游戏数据载体，提供移动、旋转、等功能 
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
	 * 开始函数，启动游戏主线程
	 */
	public void start()
	{
		if(playerThread.isAlive())
			return;
		playerThread.start();
	}

	/**
	 * 移动
	 * @param xOffset 水平偏移量
	 */
	public void move(int xOffset)
	{
		if(carrier.isPause() || carrier.isOver())
			return;
		carrier.moveMino(xOffset, 0);
		SoundPlayer.play("move");
	}

	/**
	 * 下落
	 */
	public void drop()
	{
		if(carrier.isPause() || carrier.isOver())
			return;
		if(!carrier.moveMino(0, 1))
			carrier.landed();
	}

	/**
	 * 直落
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
	 * 旋转
	 */
	public void rotate()
	{
		if(carrier.isPause() || carrier.isOver())
			return;
		carrier.rotateMino();
		SoundPlayer.play("rotate");
	}
	
	/**
	 * 暂停
	 */
	public void pause()
	{
		carrier.setPause(!carrier.isPause());

//		SoundPlayer.setBgmPlayFlag(!SoundPlayer.isBgmPlayFlag());
//		SoundPlayer.playLoop();
	}

}
