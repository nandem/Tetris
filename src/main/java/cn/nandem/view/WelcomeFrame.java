/**
 * 功能描述：欢迎界面，程序启动、数据加载等
 */
package cn.nandem.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.border.LineBorder;

import cn.nandem.utils.Animation;
import cn.nandem.utils.GameImages;

/**
 * @author Nandem
 *
 */
public class WelcomeFrame extends JFrame
{
	private JProgressBar progressBar;
	private Thread load;
	
	private class LoadHandler implements Runnable
	{
		@Override
		public void run()
		{
			try
			{
				for(int i = 1; i <= 100; i++)
				{
					Thread.sleep(30);
					progressBar.setValue(i);
				}
			}
			catch(Exception e)
			{
				// TODO: handle exception
			}
		}
	}
	
	public WelcomeFrame(int t)
	{
		init();
		load = new Thread(new LoadHandler());
		load.setDaemon(true);
	}
	
	public void showAndLoad()
	{
		this.setVisible(true);
		load.start();
		try
		{
			load.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		this.dispose();
	}
	
	private void init()
	{
		ImageIcon icon = GameImages.getWelcomeBackground();
		JLabel lb = new JLabel(icon);
		progressBar = new JProgressBar();
		
		this.add(lb);
		this.setUndecorated(true);
		this.add(progressBar, BorderLayout.SOUTH);
		this.setSize(GameImages.getWelcomeBackground().getIconWidth(), GameImages.getWelcomeBackground().getIconHeight());
		this.setLocationRelativeTo(null);
	}

	public WelcomeFrame()
	{
		Animation a = new Animation(Toolkit.getDefaultToolkit().getImage("images/startAnimationLogo.png"), 
				50, 50, new Point(190, 70), new Point(190,170), 30, false, false);
		Animation b = new Animation(Toolkit.getDefaultToolkit().getImage("images/startAnimationCenterTop.png"), 
				125, 25, new Point(290, 200), new Point(290, 155), 30, false, false);
		Animation c = new Animation(Toolkit.getDefaultToolkit().getImage("images/startAnimationCenterBottom.png"), 
				125, 25, new Point(350, 180),new Point(290, 180), 30, false, false);
		Animation d = new Animation(Toolkit.getDefaultToolkit().getImage("images/startAnimationRight.png"), 280, 35, 
				new Point(180, 220), new Point(260, 220), 30, false, false);
		JFrame jf = new JFrame();
		jf.setUndecorated(true);
		jf.setSize(550, 400);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setTitle("test");
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);

		a.setBackground(Color.WHITE);
		a.setBorder(new LineBorder(Color.BLACK));
		b.setBackground(Color.WHITE);
		b.setBorder(new LineBorder(Color.BLACK));
		c.setBackground(Color.WHITE);
		c.setBorder(new LineBorder(Color.BLACK));
		d.setBackground(Color.WHITE);
		d.setBorder(new LineBorder(Color.BLACK));
		
		jf.add(a);
		jf.setVisible(true);
		a.start();
		jf.add(b);
		jf.setVisible(true);
		b.start();
		jf.add(c);
		jf.setVisible(true);
		c.start();
		jf.add(d);
		jf.setVisible(true);
		d.start();
		try
		{
			Thread.sleep(5000);
			jf.dispose();
			d.setBackground(Color.WHITE);
		}
		catch(Exception e)
		{
		}

	}
}
