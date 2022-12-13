/**
 * 功能描述：按键设置对话框，可以修改功能对应的按键并保存 
 */
package cn.nandem.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.sun.awt.AWTUtilities;

import cn.nandem.controller.PlayerController;
import cn.nandem.utils.GameImages;
import cn.nandem.utils.KeyCalibration;
import cn.nandem.view.LeaderboardDialog.FrameMoveHandler;
import cn.nandem.view.LeaderboardDialog.LeaderBoardBackground;

/**
 * @author Nandem
 *
 */
public class KeyConfigDialog extends JDialog
{
	private JButton confirm;
	private JLabel rotateLabel;
	private JLabel leftLabel;
	private JLabel rightLabel;
	private JLabel downLabel;
	private JLabel landLabel;
	private JLabel pauseLabel;
	private JTextField rotateField;
	private JTextField leftField;
	private JTextField rightField;
	private JTextField downField;
	private JTextField landField;
	private JTextField pauseField;

	private FontMetrics metrics;
	private int labelHeight;
	private int labelWidht;
	private int fieldWidth;
	private int fieldHeight;
	private int buttonWidth;
	private int buttonHeight;

	private String keyValues[];
	private JTextField key[];

	protected static KeyConfigBackground backgroundPanel;

	public KeyConfigDialog()
	{
		keyValues = new String[6];
		key = new JTextField[6];
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
		LeaderboardDialog.backgroundPanel.backGroundIma = GameImages.getLeaderboardBackground();
	}

	private void initializeComponents()
	{
		FrameMoveHandler handler = new FrameMoveHandler();
		backgroundPanel = new KeyConfigBackground();
		Font font = new Font("华文新魏", Font.PLAIN, 16);
		rotateLabel = new JLabel(GameFrame.bundle.getString("Rotate") + " : ");
		rotateLabel.setFont(font);
		metrics = rotateLabel.getFontMetrics(rotateLabel.getFont());
		labelHeight = metrics.getHeight();
		labelWidht = metrics.stringWidth(rotateLabel.getText());
		fieldWidth = 50;
		fieldHeight = labelHeight;
		buttonWidth = 100;
		buttonHeight = 35;
		int windowWidth = GameImages.getGameFrameBackground().getWidth(null) / 2;

		int marginV = 50;
		int marginH = 15;
		int x = (windowWidth - labelWidht * 2 - fieldWidth * 2 - marginH) / 2;
		int y = 85;
		rotateField = new JTextField(1);
		key[0] = rotateField;
		rotateField.setText(KeyCalibration.getKeyValue("rotateText"));
		rotateField.addKeyListener(new ChangeKeyHandler());
		rotateField.setEditable(false);
		rotateLabel.setBounds(x, y, labelWidht, labelHeight);
		rotateField.setBounds(x + labelWidht, y, fieldWidth, fieldHeight);
		downLabel = new JLabel(GameFrame.bundle.getString("Down") + " : ");
		downField = new JTextField(1);
		key[1] = downField;
		downField.setText(KeyCalibration.getKeyValue("downText"));
		downField.addKeyListener(new ChangeKeyHandler());
		downField.setEditable(false);
		downLabel.setBounds(x + labelWidht + fieldWidth + marginH, y, labelWidht, labelHeight);
		downField.setBounds(x + labelWidht * 2 + fieldWidth + marginH, y, fieldWidth, fieldHeight);

		leftLabel = new JLabel(GameFrame.bundle.getString("Left") + " : ");
		leftField = new JTextField(1);
		key[2] = leftField;
		leftField.setText(KeyCalibration.getKeyValue("leftText"));
		leftField.addKeyListener(new ChangeKeyHandler());
		leftField.setEditable(false);
		leftLabel.setBounds(x, y + marginV, labelWidht, labelHeight);
		leftField.setBounds(x + labelWidht, y + marginV, fieldWidth, fieldHeight);
		rightLabel = new JLabel(GameFrame.bundle.getString("Right") + " : ");
		rightField = new JTextField(1);
		key[3] = rightField;
		rightField.setText(KeyCalibration.getKeyValue("rightText"));
		rightField.addKeyListener(new ChangeKeyHandler());
		rightField.setEditable(false);
		rightLabel.setBounds(x + labelWidht + fieldWidth + marginH, y + marginV, labelWidht, labelHeight);
		rightField.setBounds(x + labelWidht * 2 + fieldWidth + marginH, y + marginV, fieldWidth, fieldHeight);

		landLabel = new JLabel(GameFrame.bundle.getString("Landed") + " : ");
		landField = new JTextField(1);
		key[4] = landField;
		landField.setText(KeyCalibration.getKeyValue("landText"));
		landField.addKeyListener(new ChangeKeyHandler());
		landField.setEditable(false);
		landLabel.setBounds(x, y + marginV * 2, labelWidht, labelHeight);
		landField.setBounds(x + labelWidht, y + marginV * 2, fieldWidth, fieldHeight);

		pauseLabel = new JLabel(GameFrame.bundle.getString("Pause") + " : ");
		pauseField = new JTextField(1);
		key[5] = pauseField;
		pauseField.setText(KeyCalibration.getKeyValue("pauseText"));
		pauseField.addKeyListener(new ChangeKeyHandler());
		pauseField.setEditable(false);
		pauseLabel.setBounds(x + labelWidht + fieldWidth + marginH, y + marginV * 2, labelWidht, labelHeight);
		pauseField.setBounds(x + labelWidht * 2 + fieldWidth + marginH, y + marginV * 2, fieldWidth, fieldHeight);

		rotateLabel.setFont(font);
		leftLabel.setFont(font);
		rightLabel.setFont(font);
		downLabel.setFont(font);
		landLabel.setFont(font);
		pauseLabel.setFont(font);
		
		
		recoverTextFieldLineColor();//加在这里纯粹是为了偷懒！！！

		confirm = new JButton(GameFrame.bundle.getString("confirmButton"));
		confirm.setFont(font);
		confirm.setFocusable(false);
		confirm.setBackground(Color.WHITE);
		confirm.setBounds((GameImages.getGameFrameBackground().getWidth(null) / 2 - buttonWidth) / 2,
				GameImages.getGameFrameBackground().getHeight(null) / 2 - 105, buttonWidth, buttonHeight);
		confirm.addActionListener(new ConfirmHandler());

		backgroundPanel.setLayout(null);
		backgroundPanel.add(rotateLabel);
		backgroundPanel.add(rotateField);
		backgroundPanel.add(landLabel);
		backgroundPanel.add(landField);
		backgroundPanel.add(leftLabel);
		backgroundPanel.add(leftField);
		backgroundPanel.add(rightLabel);
		backgroundPanel.add(rightField);
		backgroundPanel.add(downLabel);
		backgroundPanel.add(downField);
		backgroundPanel.add(confirm);
		backgroundPanel.add(pauseLabel);
		backgroundPanel.add(pauseField);
		this.add(backgroundPanel);
		this.setUndecorated(true);
		AWTUtilities.setWindowOpaque(this, false);
		this.setSize(GameImages.getGameFrameBackground().getWidth(null) / 2, GameImages.getGameFrameBackground().getHeight(null) / 2);
		this.setResizable(false);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.addMouseListener(handler);
		this.addMouseMotionListener(handler);
	}

	private void prepareJudgeKeyConflict()
	{
		keyValues[0] = KeyConfigDialog.this.rotateField.getText();
		keyValues[1] = KeyConfigDialog.this.downField.getText();
		keyValues[2] = KeyConfigDialog.this.leftField.getText();
		keyValues[3] = KeyConfigDialog.this.rightField.getText();
		keyValues[4] = KeyConfigDialog.this.landField.getText();
		keyValues[5] = KeyConfigDialog.this.pauseField.getText();
	}
	

	private void recoverTextFieldLineColor()
	{
		for(int i = 0; i < key.length; i++)
		{
			key[i].setBorder(new LineBorder(Color.BLACK));
		}
	}

	private boolean judgeKeyConflict()
	{
		boolean flag = true;
		this.prepareJudgeKeyConflict();
		for(int i = 0; i < keyValues.length; i++)
		{
			for(int j = 0; j < keyValues.length; j++)
			{
				if(i != j)
					if(keyValues[i].equals(keyValues[j]))
					{
						key[i].setBorder(new LineBorder(Color.RED));
						key[j].setBorder(new LineBorder(Color.RED));
						flag = false;
					}
			}
		}
		return flag;
	}

	private class KeyConfigBackground extends JPanel
	{
		public Image backGroundIma;

		public KeyConfigBackground()
		{
			backGroundIma = GameImages.getLeaderboardBackground();
			this.setOpaque(false);// 设置JPanel透明
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

	private class JTextFieldLimit extends PlainDocument
	{
		private int limit;
		// optional uppercase conversion
		private boolean toUppercase = true;

		JTextFieldLimit(int limit)
		{
			super();
			this.limit = limit;
		}

		JTextFieldLimit(int limit, boolean upper)
		{
			super();
			this.limit = limit;
			toUppercase = upper;
		}

		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException
		{
			if(str == null)
				return;

			if((getLength() + str.length()) <= limit)
			{
				if(toUppercase)
					str = str.toUpperCase();
				super.insertString(offset, str, attr);
			}
		}
	}

	private class ChangeKeyHandler implements KeyListener
	{

		@Override
		public void keyTyped(KeyEvent e)
		{
			// TODO Auto-generated method stub

		}

		int t = 1;
		@Override
		public void keyPressed(KeyEvent e)
		{
			JTextField jt = (JTextField) e.getSource();
			jt.setText(e.getKeyText(e.getKeyCode()));
			recoverTextFieldLineColor();
			judgeKeyConflict();
		}

		@Override
		public void keyReleased(KeyEvent e)
		{
			// TODO Auto-generated method stub

		}

	}

	private class ConfirmHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(judgeKeyConflict())
			{
				PlayerController.setKeyDown(Integer.valueOf(KeyCalibration.getKeyValue(KeyConfigDialog.this.downField.getText())));
				PlayerController.setKeyUp(Integer.valueOf(KeyCalibration.getKeyValue(KeyConfigDialog.this.rotateField.getText())));
				PlayerController.setKeyLeft(Integer.valueOf(KeyCalibration.getKeyValue(KeyConfigDialog.this.leftField.getText())));
				PlayerController.setKeyRight(Integer.valueOf(KeyCalibration.getKeyValue(KeyConfigDialog.this.rightField.getText())));
				PlayerController.setKeyLanded(Integer.valueOf(KeyCalibration.getKeyValue(KeyConfigDialog.this.landField.getText())));
				PlayerController.setKeyPause(Integer.valueOf(KeyCalibration.getKeyValue(KeyConfigDialog.this.pauseField.getText())));

				KeyCalibration.setKeyAndValue("rotate", KeyCalibration.getKeyValue(KeyConfigDialog.this.rotateField.getText()));
				KeyCalibration.setKeyAndValue("down", KeyCalibration.getKeyValue(KeyConfigDialog.this.downField.getText()));
				KeyCalibration.setKeyAndValue("left", KeyCalibration.getKeyValue(KeyConfigDialog.this.leftField.getText()));
				KeyCalibration.setKeyAndValue("right", KeyCalibration.getKeyValue(KeyConfigDialog.this.rightField.getText()));
				KeyCalibration.setKeyAndValue("pause", KeyCalibration.getKeyValue(KeyConfigDialog.this.pauseField.getText()));
				KeyCalibration.setKeyAndValue("land", KeyCalibration.getKeyValue(KeyConfigDialog.this.landField.getText()));

				KeyCalibration.setKeyAndValue("rotateText", KeyConfigDialog.this.rotateField.getText());
				KeyCalibration.setKeyAndValue("downText", KeyConfigDialog.this.downField.getText());
				KeyCalibration.setKeyAndValue("leftText", KeyConfigDialog.this.leftField.getText());
				KeyCalibration.setKeyAndValue("rightText", KeyConfigDialog.this.rightField.getText());
				KeyCalibration.setKeyAndValue("pauseText", KeyConfigDialog.this.pauseField.getText());
				KeyCalibration.setKeyAndValue("landText", KeyConfigDialog.this.landField.getText());

				KeyCalibration.saveConfig();
				KeyConfigDialog.this.dispose();
			}
		}
	}

}
