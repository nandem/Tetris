/**
 * ������������Ϸ�пأ��������桢��Ϸҵ�񡢶�ʱ�����ṩ��Ϸ����ϵͳ���� 
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
	private GameFrame gameFrame; //��Ϸ��ͼ
	private DataCarrier carrier; //��������
	private Timer timer; //��Ϸ�߳�
	private Server server; //��Ϸҵ��

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
	 * ��ʾ���ڣ�������λ��
	 */
	public void showWindow()
	{
		gameFrame.setVisible(true);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.repaint();
	}

	/**
	 * ����
	 */
	public void moveToRight()
	{
		server.move(1);
	}

	/**
	 * ����
	 */
	public void moveToLeft()
	{
		server.move(-1);
	}

	/**
	 * ����
	 */
	public void moveToBottom()
	{
		server.drop();
	}

	/**
	 * ֱ��
	 */
	public void moveToBottomImmediately()
	{
		server.instant();
	}

	/**
	 * ��ת
	 */
	public void rotate()
	{
		server.rotate();
	}

	/**
	 * ��ͣ
	 */
	public void pause()
	{
		server.pause();
	}

	/**
	 * ��ʼ��Ϸ����
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
