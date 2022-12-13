package cn.nandem.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.nandem.config.GameConfig;
import cn.nandem.utils.GameImages;
import cn.nandem.utils.ShadowBorder;

import com.sun.awt.AWTUtilities;

public class Irregular extends JFrame
{
	protected static BackgroundPanel backgroundPanel;
	protected JButton button_exit;
	protected JButton button_mini;
	protected ImageIcon icon_exitIcon;
	protected ImageIcon icon_miniIcon;
	
	public Irregular()
	{
		initializeComponents();
	}

	// 退出处理
	public class ExitHandler implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			// TODO Auto-generated method stub
			// 1907533503
			System.exit(0);
		}

	}

	// 最小化处理
	public class MiniHandler implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			setExtendedState(JFrame.ICONIFIED);
		}

	}

	// 移动处理
	public class FrameMoveHandler extends MouseAdapter
	{
		private Point origin;
		@Override
		public void mousePressed(MouseEvent arg0)
		{
			origin = arg0.getPoint();
		}

		@Override
		public void mouseDragged(MouseEvent arg0)
		{
			Point pt = arg0.getPoint();
			int x = getLocation().x + pt.x - origin.x;
			int y = getLocation().y + pt.y - origin.y;
			setLocation(x, y);
		}

	}
	
	protected void resetImages()
	{
		this.backgroundPanel.backGroundIma = GameImages.getGameFrameBackground();
		button_exit.setIcon(GameImages.getGameFrameCloseButtonUnselected());
		button_exit.setRolloverIcon(GameImages.getGameFrameCloseButtonSelected());
		button_mini.setIcon(GameImages.getGameFrameMiniButtonUnselected());
		button_mini.setRolloverIcon(GameImages.getGameFrameMiniButtonSelected());
	}

	private void initializeComponents()
	{
		FrameMoveHandler handler = new FrameMoveHandler();
		int windowWidth = GameImages.getGameFrameBackground().getWidth(null);
		int windowHeight = GameImages.getGameFrameBackground().getHeight(null);
		backgroundPanel = new BackgroundPanel();
		
		icon_exitIcon = GameImages.getGameFrameCloseButtonUnselected();
		icon_miniIcon = GameImages.getGameFrameMiniButtonUnselected();

		button_exit = new JButton(icon_exitIcon);
		button_exit.setRolloverIcon(GameImages.getGameFrameCloseButtonSelected());
		button_exit.setFocusable(false);
		button_exit.setBorder(null);
		button_exit.setContentAreaFilled(false);
		button_exit.addActionListener(new ExitHandler());

		button_mini = new JButton(icon_miniIcon);
		button_mini.setRolloverIcon(GameImages.getGameFrameMiniButtonSelected());
		button_mini.setFocusable(false);
		button_mini.setBorder(null);
		button_mini.setContentAreaFilled(false);
		button_mini.addActionListener(new MiniHandler());

		button_mini.setBounds(windowWidth - 100, 180,icon_exitIcon.getIconWidth(), icon_exitIcon.getIconHeight());
		button_exit.setBounds(windowWidth - 65, 180,icon_miniIcon.getIconWidth(), icon_miniIcon.getIconHeight());
		backgroundPanel.setLayout(null);
		backgroundPanel.add(button_exit);
		backgroundPanel.add(button_mini);
		this.getContentPane().add(backgroundPanel);
		this.setUndecorated(true);
		AWTUtilities.setWindowOpaque(this, false);
		this.setSize(GameImages.getGameFrameBackground().getWidth(null), GameImages.getGameFrameBackground().getHeight(null));
		//this.setLocationRelativeTo(null);
		this.addMouseListener(handler);
		this.addMouseMotionListener(handler);
	}
//	
//	public static void main(String[] args)
//	{
//		Irregular tg = new Irregular();
//		JPanel test = new JPanel();
//		test.setBackground(Color.BLACK);
//		test.setBounds(10, 10, 100, 100);
//		//tg.setLayout(null);
//		tg.backgroundPanel.add(test);
//		System.out.println(tg.backgroundPanel);
//		//tg.setSize(1000, 700);
//		tg.setVisible(true);
//	}
}

class BackgroundPanel extends JPanel
{
	public Image backGroundIma;

	public BackgroundPanel()
	{
		backGroundIma = GameImages.getGameFrameBackground();
		this.setOpaque(false);//设置JPanel透明
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		int bkImgWidth = backGroundIma.getWidth(null);
		int bkImgHeight = backGroundIma.getHeight(null); // 定义图片的宽度、高度
		int FWidth = getWidth();
		int FHeight = getHeight();// 定义窗口的宽度、高度
		int x = (FWidth - bkImgWidth) / 2;
		int y = (FHeight - bkImgHeight) / 2;// 计算图片的坐标,使图片显示在窗口正中间
		g.drawImage(backGroundIma, x, y, null);// 绘制图片
	}
}
