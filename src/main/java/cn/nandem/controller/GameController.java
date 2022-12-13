/**
 * 功能描述：游戏中控，包含界面、游戏业务、定时器，提供游戏所有系统功能 
 */
package cn.nandem.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;

import cn.nandem.config.CanvasConfig;
import cn.nandem.config.GameConfig;
import cn.nandem.model.DataCarrier;
import cn.nandem.model.OverListener;
import cn.nandem.model.Server;
import cn.nandem.utils.SoundPlayer;
import cn.nandem.view.GameFrame;

/**
 * @author Nandem
 *
 */
public class GameController
{
	private GameFrame gameFrame; //游戏视图
	private DataCarrier carrier; //数据载体
	private Timer timer; //游戏线程
	private Server server; //游戏业务

	public GameController()
	{
		carrier = new DataCarrier();
		carrier.addOverListener(new OverListener(){
			@Override
			public void performed()
			{
				gameFrame.resetMenu();
			}
		});
		server = new Server(carrier);
		PlayerController playerController = new PlayerController(this);
		gameFrame = new GameFrame(carrier);
		gameFrame.addStartListener(new StartHandler());
		gameFrame.addKeyListener(playerController);
		
		timer = new Timer(GameConfig.TIME_SLICE, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				gameFrame.repaint();
				//gameFrame.getGamePanel().repaint();
			}
		});
	}

	/**
	 * 显示窗口，并设置位置
	 */
	public void showWindow()
	{
		gameFrame.setVisible(true);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.repaint();
	}

	/**
	 * 右移
	 */
	public void moveToRight()
	{
		server.move(1);
	}

	/**
	 * 左移
	 */
	public void moveToLeft()
	{
		server.move(-1);
	}

	/**
	 * 下落
	 */
	public void moveToBottom()
	{
		server.drop();
	}

	/**
	 * 直落
	 */
	public void moveToBottomImmediately()
	{
		server.instant();
	}

	/**
	 * 旋转
	 */
	public void rotate()
	{
		server.rotate();
	}

	/**
	 * 暂停
	 */
	public void pause()
	{
		server.pause();
	}

	/**
	 * 开始游戏处理
	 * @author Nandem
	 *
	 */
	private class StartHandler implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			carrier.initialize();
			server.start();
			timer.start();
			gameFrame.resetMenu();
			SoundPlayer.playLoop();
		}
		
	}
}
