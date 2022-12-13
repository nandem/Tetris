/**
 * 
 */
package cn.nandem.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import cn.nandem.model.GameLeaderboard;
import cn.nandem.utils.GameImages;

import com.sun.awt.AWTUtilities;

/**
 * @author Nandem
 *
 */
public class LeaderboardDialog extends JDialog
{
	protected static LeaderBoardBackground backgroundPanel;
	protected JButton confirm;
	private JLabel top1Name, top2Name, top3Name;
	private JLabel top1Score, top2Score, top3Score;
	
	public LeaderboardDialog()
	{
		initializeComponents();
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
		backgroundPanel.backGroundIma = GameImages.getLeaderboardBackground();
	}

	private void initializeComponents()
	{
		FrameMoveHandler handler = new FrameMoveHandler();
		backgroundPanel = new LeaderBoardBackground();
		int labelWidth = 200;
		int labelHeight = 50;
		GameLeaderboard leaderboard = GameLeaderboard.getInstance();
		Font nameFont = new Font("楷体", Font.BOLD, 20);
		Font scoreFont = new Font("楷体", Font.BOLD, 36);
		
		top1Name = new JLabel("*1*  " + leaderboard.getTopThree()[0].split(":")[0]);
		top1Name.setFont(nameFont);
		top1Name.setBounds((GameImages.getLeaderboardBackground().getWidth(null) - labelWidth) / 2, 90, labelWidth, labelHeight);
		top1Score = new JLabel(leaderboard.getTopThree()[0].split(":")[1]);
		top1Score.setFont(scoreFont);
		top1Score.setBounds(GameImages.getLeaderboardBackground().getWidth(null) - 130, 90, labelWidth, labelHeight);
		
		top2Name = new JLabel("*2*  " + leaderboard.getTopThree()[1].split(":")[0]);
		top2Name.setFont(nameFont);
		top2Name.setBounds((GameImages.getLeaderboardBackground().getWidth(null) - labelWidth) / 2, 140, labelWidth, labelHeight);
		top2Score = new JLabel(leaderboard.getTopThree()[1].split(":")[1]);
		top2Score.setFont(scoreFont);
		top2Score.setBounds(GameImages.getLeaderboardBackground().getWidth(null) - 130, 140, labelWidth, labelHeight);
		top3Name = new JLabel("*3*  " + leaderboard.getTopThree()[2].split(":")[0]);
		top3Name.setFont(nameFont);
		top3Name.setBounds((GameImages.getLeaderboardBackground().getWidth(null) - labelWidth) / 2, 190, labelWidth, labelHeight);
		top3Score = new JLabel(leaderboard.getTopThree()[2].split(":")[1]);
		top3Score.setFont(scoreFont);
		top3Score.setBounds(GameImages.getLeaderboardBackground().getWidth(null) - 130, 190, labelWidth, labelHeight);
		
		confirm = new JButton(GameFrame.bundle.getString("confirmButton"));
		confirm.setFont(new Font("华文新魏", Font.PLAIN, 18));
		confirm.setFocusable(false);
		confirm.setBackground(Color.WHITE);
		confirm.setBorder(new LineBorder(Color.BLACK, 2));
		int buttonWidth = 150;
		int buttonHeight = 30;
		confirm.setBounds((GameImages.getLeaderboardBackground().getWidth(null) - buttonWidth) / 2, GameImages.getLeaderboardBackground().getHeight(null) - buttonHeight * 3, buttonWidth, buttonHeight);
		confirm.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				LeaderboardDialog.this.dispose();
				//GameFrame.getCarrier().setPause(!GameFrame.getCarrier().isPause());
			}
		});
		backgroundPanel.setLayout(null);
		backgroundPanel.add(top1Name);
		backgroundPanel.add(top1Score);
		backgroundPanel.add(top2Name);
		backgroundPanel.add(top2Score);
		backgroundPanel.add(top3Name);
		backgroundPanel.add(top3Score);
		backgroundPanel.add(confirm);
		this.getContentPane().add(backgroundPanel);
		this.setUndecorated(true);
		AWTUtilities.setWindowOpaque(this, false);
		this.setSize(GameImages.getLeaderboardBackground().getWidth(null), GameImages.getLeaderboardBackground().getHeight(null));
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.addMouseListener(handler);
		this.addMouseMotionListener(handler);
	}
	
	public static void main(String[] args)
	{
		new LeaderboardDialog().setVisible(true);
	}
	
	class LeaderBoardBackground extends JPanel
	{
		public Image backGroundIma;

		public LeaderBoardBackground()
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
}



