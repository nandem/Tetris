/**
 * 功能描述：关于对话框，一般包括游戏的一些基本信息、开发者信息等
 */
package cn.nandem.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.awt.AWTUtilities;

import cn.nandem.utils.GameImages;
import cn.nandem.utils.SoundPlayer;

/**
 * @author Nandem
 *
 */
public class AboutDialog extends JDialog
{

	private JButton confirm;

	private FontMetrics metrics;
	private JLabel messageLabel;
	
	protected static AboutBackground backgroundPanel;
	
	public AboutDialog(String title, String message)
	{
		initializeComponents(title, message);
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
	
	public static void resetImages()
	{
		LeaderboardDialog.backgroundPanel.backGroundIma = GameImages.getLeaderboardBackground();
	}

	private void initializeComponents(String title, String message)
	{
		FrameMoveHandler handler = new FrameMoveHandler();
		backgroundPanel = new AboutBackground();
		Font font = new Font("华文新魏", Font.BOLD, 20);
		messageLabel = new JLabel(message);
		messageLabel.setFont(font);
		metrics = messageLabel.getFontMetrics(messageLabel.getFont());
		int messageHeight = metrics.getHeight();
		int messageWidht = metrics.stringWidth(message);
		int buttonWidth = 100;
		int buttonHeight = 35;

		messageLabel.setBounds((GameImages.getGameFrameBackground().getWidth(null) / 2 - messageWidht) / 2, (GameImages.getGameFrameBackground().getHeight(null) / 2 - messageHeight - 40) / 2, messageWidht, messageHeight);

		confirm = new JButton(GameFrame.bundle.getString("confirmButton"));
		confirm.setFont(font);
		confirm.setFocusable(false);
		confirm.setBackground(Color.WHITE);
		confirm.setBounds((GameImages.getGameFrameBackground().getWidth(null) / 2 - buttonWidth) / 2, GameImages.getGameFrameBackground().getHeight(null) / 3 - 100, buttonWidth, buttonHeight);

		int marginV = 50;
		int marginH = 15;

		confirm = new JButton(GameFrame.bundle.getString("confirmButton"));
		confirm.setFont(font);
		confirm.setFocusable(false);
		confirm.setBackground(Color.WHITE);
		confirm.setBounds((GameImages.getGameFrameBackground().getWidth(null) / 2 - buttonWidth) / 2,
				GameImages.getGameFrameBackground().getHeight(null) / 2 - 105, buttonWidth, buttonHeight);
		confirm.addActionListener(new ConfirmHandler());

		backgroundPanel.setLayout(null);
		backgroundPanel.add(messageLabel);
		backgroundPanel.add(confirm);
		this.add(backgroundPanel);
		this.setUndecorated(true);
		AWTUtilities.setWindowOpaque(this, false);
		this.setSize(GameImages.getGameFrameBackground().getWidth(null) / 2, GameImages.getGameFrameBackground().getHeight(null) / 2);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.addMouseListener(handler);
		this.addMouseMotionListener(handler);
	}
	
	class AboutBackground extends JPanel
	{
		public Image backGroundIma;

		public AboutBackground()
		{
			backGroundIma = GameImages.getLeaderboardBackground();
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

	public class ConfirmHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			AboutDialog.this.dispose();
			//GameFrame.getCarrier().setPause(!GameFrame.getCarrier().isPause());
		}
	}
}
