/**
 * 功能描述：游戏主界面，包含菜单、游戏画布
 */
package cn.nandem.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import cn.nandem.config.CanvasConfig;
import cn.nandem.config.GameConfig;
import cn.nandem.model.DataCarrier;
import cn.nandem.model.GameRecord;
import cn.nandem.utils.GameImages;
import cn.nandem.utils.ShadowBorder;
import cn.nandem.utils.SoundPlayer;

/**
 * @author Nandem
 *
 */
public class GameFrame extends Irregular
{
	/* 国际化相关 */
	public static ResourceBundle bundle;
	/* 数据载体，游戏数据相关 */
	private static DataCarrier carrier;
	/* 菜单项，游戏菜单，包括新建、模式等 */
	private JMenuItem gameMenuNew;
	/* 游戏面板，容纳游戏层 */
	private GameCanvas gamePanel;
	private GameCanvas gameNextPanel;
	private GameCanvas gameInfoPanel;
	/* 游戏菜单栏，试图菜单 */
	private JMenu viewMenu;
	/* 游戏菜单栏字体 */
	private Font font;
	/* 游戏菜单栏 */
	private JMenuBar gameMenuBar;

	public GameFrame(DataCarrier carrier)
	{
		GameFrame.this.carrier = carrier;
		bundle = ResourceBundle.getBundle("dict", new Locale(GameConfig.getLanguage()));
		initializeComponents();
		font = new Font("华文新魏", Font.BOLD, 30);
	}

	public void resetMenu()
	{
		boolean s = gameMenuBar.getMenu(0).getItem(0).isEnabled();
		gameMenuBar.getMenu(0).getItem(0).setEnabled(!s);
		gameMenuBar.getMenu(0).getItem(2).setEnabled(!s);
	}

	private void localize()
	{
		JMenuBar menuBar = gameMenuBar;
		for(int i = 0; i < menuBar.getMenuCount(); i++)
		{
			JMenu menu = menuBar.getMenu(i);
			localizeMenu(menu);
		}
		this.setTitle(bundle.getString("Title"));
	}

	private void localizeMenu(JMenu menu)
	{
		menu.setText(bundle.getString(menu.getName()));
		for(int i = 0; i < menu.getItemCount(); i++)
		{
			JMenuItem menuItem = menu.getItem(i);
			if(menuItem == null)
				continue;
			menuItem.setText(bundle.getString(menuItem.getName()));
			if(menuItem instanceof JMenu)
				localizeMenu((JMenu) menuItem);
		}
	}

	private void initializeComponents()
	{
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		gamePanel = new GameCanvas(1, 1);
		gamePanel.setBounds(200, 242, 328, GameConfig.ROWS * GameConfig.getCellSize() + GameConfig.getUnit() * 2 + 20);
		gameNextPanel = new GameCanvas(1, 1);
		gameNextPanel.setBounds(550, 242, 148, 128);
		gameInfoPanel = new GameCanvas(1, 1);
		gameInfoPanel.setBounds(40, 242, 148, 308);
		gameMenuBar = createMenuBar();
		gameMenuBar.setBounds(GameImages.getGameFrameBackground().getWidth(null) - 280, 180, 150, icon_exitIcon.getIconWidth() + 10);
		//gameMenuBar.setBorder(new LineBorder(new Color(230, 0, 0), 2));
		gameMenuBar.setBorder(BorderFactory.createCompoundBorder(ShadowBorder.newInstance(), BorderFactory.createLineBorder(new Color(230, 0, 0), 2)));
		gameMenuBar.setBackground(Color.WHITE);

		backgroundPanel.add(gameMenuBar);
		backgroundPanel.add(gamePanel);
		backgroundPanel.add(gameNextPanel);
		backgroundPanel.add(gameInfoPanel);

		this.setIconImage(Toolkit.getDefaultToolkit().getImage("images/logo.png"));
		this.setTitle(bundle.getString("Title"));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.addWindowListener(new WindowHandler());
	}

	private JMenuBar createMenuBar()
	{
		JMenuBar menuBar = new JMenuBar();

		/* ^_^*------------------游戏菜单设置--------------------*^_^ */
		JMenu gameMenu = new JMenu();
		gameMenu.setFont(font);
		gameMenu.setMnemonic('G');
		gameMenu.setName("GameMenu");
		gameMenu.setText(bundle.getString("GameMenu"));

		gameMenuNew = new JMenuItem(bundle.getString("GameMenuNew"));
		gameMenuNew.setMnemonic('N');
		gameMenuNew.setAccelerator(KeyStroke.getKeyStroke("F2"));
		gameMenuNew.setName("GameMenuNew");

		JMenu gameModel = new JMenu(bundle.getString("GameModel"));
		gameModel.setMnemonic('M');
		gameModel.setName("GameModel");

		JRadioButtonMenuItem gameMenuSingle = new JRadioButtonMenuItem(bundle.getString("GameMenuSingle"));
		gameMenuSingle.setMnemonic('S');
		gameMenuSingle.setName("GameMenuSingle");

		JRadioButtonMenuItem gameMenuDouble = new JRadioButtonMenuItem(bundle.getString("GameMenuDouble"));
		gameMenuDouble.setMnemonic('D');
		gameMenuDouble.setName("GameMenuDouble");

		ButtonGroup modelGroup = new ButtonGroup();
		modelGroup.add(gameMenuSingle);
		modelGroup.add(gameMenuDouble);
		gameModel.add(gameMenuSingle);
		gameModel.add(gameMenuDouble);

		JMenuItem gameMenuNetwork = new JMenuItem(bundle.getString("GameMenuNetwork"));
		gameMenuNetwork.setMnemonic('W');
		gameMenuNetwork.setName("GameMenuNetwork");

		JMenuItem gameMenuLeaderborder = new JMenuItem(bundle.getString("GameMenuLeaderborder"));
		gameMenuLeaderborder.setMnemonic('L');
		gameMenuLeaderborder.setName("GameMenuLeaderborder");
		gameMenuLeaderborder.addActionListener(new GameConfigHandler());

		JMenuItem gameMenuKeyConfig = new JMenuItem(bundle.getString("GameMenuKeyConfig"));
		gameMenuKeyConfig.setMnemonic('K');
		gameMenuKeyConfig.setName("GameMenuKeyConfig");
		gameMenuKeyConfig.addActionListener(new GameConfigHandler());

		JMenuItem gameMenuExit = new JMenuItem(bundle.getString("GameMenuExit"));
		gameMenuExit.setMnemonic('E');
		gameMenuExit.setName("GameMenuExit");
		gameMenuExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		gameMenuExit.addActionListener(new ExitHandler());
		viewMenu = new JMenu(bundle.getString("ViewMenu"));
		viewMenu.setMnemonic('V');
		viewMenu.setName("ViewMenu");

		JMenu viewMenuSkin = new JMenu(bundle.getString("ViewMenuSkin"));
		viewMenuSkin.setMnemonic('S');
		viewMenuSkin.setName("ViewMenuSkin");

		JMenuItem viewMenuSkinClassic = new JRadioButtonMenuItem(bundle.getString("ViewMenuSkinClassic"));
		viewMenuSkinClassic.setMnemonic('C');
		viewMenuSkinClassic.setName("ViewMenuSkinClassic");
		viewMenuSkinClassic.setSelected(true);
		viewMenuSkinClassic.addActionListener(new SkinHandler());

		JMenuItem viewMenuSkinCartoon = new JRadioButtonMenuItem(bundle.getString("ViewMenuSkinCartoon"));
		viewMenuSkinCartoon.setMnemonic('A');
		viewMenuSkinCartoon.setName("ViewMenuSkinCartoon");
		viewMenuSkinCartoon.addActionListener(new SkinHandler());

		JMenuItem viewMenuSkinChinaStyle = new JRadioButtonMenuItem(bundle.getString("ViewMenuSkinChinaStyle"));
		viewMenuSkinChinaStyle.setMnemonic('H');
		viewMenuSkinChinaStyle.setName("ViewMenuSkinChinaStyle");
		viewMenuSkinChinaStyle.addActionListener(new SkinHandler());

		ButtonGroup skinGroup = new ButtonGroup();
		skinGroup.add(viewMenuSkinClassic);
		skinGroup.add(viewMenuSkinChinaStyle);
		skinGroup.add(viewMenuSkinCartoon);
		viewMenuSkin.add(viewMenuSkinClassic);
		viewMenuSkin.add(viewMenuSkinChinaStyle);
		viewMenuSkin.add(viewMenuSkinCartoon);

		JMenu viewMenuLanguage = new JMenu(bundle.getString("ViewMenuLanguage"));
		viewMenuLanguage.setMnemonic('L');
		viewMenuLanguage.setName("ViewMenuLanguage");

		JMenuItem viewMenuLanguageEnglish = new JRadioButtonMenuItem(bundle.getString("en"));
		viewMenuLanguageEnglish.setMnemonic('E');
		viewMenuLanguageEnglish.setName("en");
		viewMenuLanguageEnglish.setSelected(GameConfig.getLanguage().equals("en"));
		viewMenuLanguageEnglish.addActionListener(new LocalizeHandler());

		JMenuItem viewMenuLanguageChinese = new JRadioButtonMenuItem(bundle.getString("zh"));
		viewMenuLanguageChinese.setMnemonic('C');
		viewMenuLanguageChinese.setName("zh");
		viewMenuLanguageChinese.setSelected(GameConfig.getLanguage().equals("zh"));
		viewMenuLanguageChinese.addActionListener(new LocalizeHandler());

		ButtonGroup languageGroup = new ButtonGroup();
		languageGroup.add(viewMenuLanguageChinese);
		languageGroup.add(viewMenuLanguageEnglish);
		viewMenuLanguage.add(viewMenuLanguageChinese);
		viewMenuLanguage.add(viewMenuLanguageEnglish);

		JCheckBoxMenuItem viewMenuBackgroundMusic = new JCheckBoxMenuItem(bundle.getString("ViewMenuBackgroundMusic"));
		viewMenuBackgroundMusic.setMnemonic('B');
		viewMenuBackgroundMusic.setAccelerator(KeyStroke.getKeyStroke("F3"));
		viewMenuBackgroundMusic.setName("ViewMenuBackgroundMusic");
		viewMenuBackgroundMusic.setSelected(true);
		viewMenuBackgroundMusic.addActionListener(new BGMHandler());

		JCheckBoxMenuItem viewMenuKeySound = new JCheckBoxMenuItem(bundle.getString("ViewMenuKeySound"));
		viewMenuKeySound.setMnemonic('K');
		viewMenuKeySound.setAccelerator(KeyStroke.getKeyStroke("F4"));
		viewMenuKeySound.setName("ViewMenuKeySound");
		viewMenuKeySound.setSelected(true);
		viewMenuKeySound.addActionListener(new KeySoundHandler());
		/* ^_^*------------------游戏帮助设置--------------------*^_^ */
		JMenu helpMenu = new JMenu(bundle.getString("HelpMenu"));
		helpMenu.setMnemonic('H');
		helpMenu.setName("HelpMenu");

		JMenuItem helpMenuManual = new JMenuItem(bundle.getString("HelpMenuManual"));
		helpMenuManual.setMnemonic('M');
		helpMenuManual.setName("HelpMenuManual");
		helpMenuManual.addActionListener(new HelpHandler());

		JMenuItem helpMenuAbout = new JMenuItem(bundle.getString("HelpMenuAbout"));
		helpMenuAbout.setMnemonic('A');
		helpMenuAbout.setName("HelpMenuAbout");
		helpMenuAbout.addActionListener(new HelpHandler());

		gameMenu.add(gameMenuNew);
		gameMenu.addSeparator();
		gameMenu.add(gameModel);
		gameMenu.addSeparator();
		gameMenu.add(gameMenuNetwork);
		gameMenu.addSeparator();
		gameMenu.add(gameMenuLeaderborder);
		gameMenu.addSeparator();
		gameMenu.add(gameMenuKeyConfig);
		gameMenu.addSeparator();
		gameMenu.add(gameMenuExit);
		viewMenu.add(viewMenuSkin);
		viewMenu.addSeparator();
		viewMenu.add(viewMenuLanguage);
		viewMenu.addSeparator();
		viewMenu.add(viewMenuBackgroundMusic);
		viewMenu.addSeparator();
		viewMenu.add(viewMenuKeySound);
		helpMenu.add(helpMenuManual);
		helpMenu.addSeparator();
		helpMenu.add(helpMenuAbout);

		menuBar.add(gameMenu);
		menuBar.add(viewMenu);
		menuBar.add(helpMenu);
		return menuBar;
	}

	public void addStartListener(ActionListener listener)
	{
		gameMenuNew.addActionListener(listener);
	}

	public void resetSkin()
	{
		GameImages.resetSkinImages();
		// LeaderboardDialog.resetImages();
		this.resetImages();
	}

	private class WindowHandler implements WindowListener
	{

		@Override
		public void windowOpened(WindowEvent e)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosing(WindowEvent e)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosed(WindowEvent e)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeiconified(WindowEvent e)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void windowActivated(WindowEvent e)
		{
			// GameFrame.this.carrier.setPause(false);
		}

		@Override
		public void windowDeactivated(WindowEvent e)
		{
			if(!SoundPlayer.isBgmPlayFlag())
				SoundPlayer.setBgmPlayFlag(!SoundPlayer.isBgmPlayFlag());
			GameFrame.this.carrier.setPause(true);
		}

		@Override
		public void windowIconified(WindowEvent e)
		{
			// TODO Auto-generated method stub

		}
	}

	/**
	 * 按键设置和排行榜处理
	 * 
	 * @author Nandem
	 *
	 */
	private class GameConfigHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			carrier.setPause(true);
			JMenuItem menuItem = (JMenuItem) e.getSource();
			if(menuItem.getName().equals("GameMenuLeaderborder"))
			{
				if(!SoundPlayer.isBgmPlayFlag())
					SoundPlayer.setBgmPlayFlag(!SoundPlayer.isBgmPlayFlag());
				new LeaderboardDialog().setVisible(true);;
			}
			else if(menuItem.getName().equals("GameMenuKeyConfig"))
			{
				if(!SoundPlayer.isBgmPlayFlag())
					SoundPlayer.setBgmPlayFlag(!SoundPlayer.isBgmPlayFlag());
				new KeyConfigDialog().setVisible(true);
			}
		}
	}

	/**
	 * 帮助处理
	 * 
	 * @author Nandem
	 *
	 */
	private class HelpHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			carrier.setPause(true);
			JMenuItem menuItem = (JMenuItem) e.getSource();
			if(menuItem.getName().equals("HelpMenuManual"))
			{
				if(!SoundPlayer.isBgmPlayFlag())
					SoundPlayer.setBgmPlayFlag(!SoundPlayer.isBgmPlayFlag());
				new AboutDialog("帮助", bundle.getString("Manual")).setVisible(true);
			}
			else if(menuItem.getName().equals("HelpMenuAbout"))
			{
				if(!SoundPlayer.isBgmPlayFlag())
					SoundPlayer.setBgmPlayFlag(!SoundPlayer.isBgmPlayFlag());
				new AboutDialog("关于我们", bundle.getString("About")).setVisible(true);
			}
		}
	}

	/**
	 * 皮肤处理
	 * 
	 * @author Nandem
	 *
	 */
	private class SkinHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(!SoundPlayer.isBgmPlayFlag())
				SoundPlayer.setBgmPlayFlag(!SoundPlayer.isBgmPlayFlag());
			carrier.setPause(true);
			JMenuItem menuItem = (JMenuItem) e.getSource();
			if(menuItem.getName().equals("ViewMenuSkinClassic"))
			{
				GameConfig.setSkinPath("images/default");
				gamePanel.setBounds(200, 242, 328, CanvasConfig.getHeight());
				gameNextPanel.setBounds(550, 242, 148, 128);
				gameInfoPanel.setBounds(40, 242, 148, 308);
				gameMenuBar.setBounds(833 - 280, 180, 150, icon_exitIcon.getIconWidth() + 10);
				button_mini.setBounds(833 - 100, 180, icon_exitIcon.getIconWidth(), icon_exitIcon.getIconHeight());
				button_exit.setBounds(833 - 65, 180, icon_miniIcon.getIconWidth(), icon_miniIcon.getIconHeight());
			}
			else if(menuItem.getName().equals("ViewMenuSkinChinaStyle"))
			{
				GameConfig.setSkinPath("images/chinastyle");
				gamePanel.setBounds(300, 242, 328, CanvasConfig.getHeight());
				gameNextPanel.setBounds(695, 266, 148, 128);
				gameInfoPanel.setBounds(135, 292, 148, 308);
				gameMenuBar.setBounds(1017 - 325, 223, 150, icon_exitIcon.getIconHeight() + 10);
				button_mini.setBounds(1017 - 276, 180, icon_exitIcon.getIconWidth(), icon_exitIcon.getIconHeight());
				button_exit.setBounds(1017 - 240, 180, icon_miniIcon.getIconWidth(), icon_miniIcon.getIconHeight());
			}
			else if(menuItem.getName().equals("ViewMenuSkinCartoon"))
			{
				GameConfig.setSkinPath("images/cartoon");
				gamePanel.setBounds(300, 272, 328, CanvasConfig.getHeight());
				gameNextPanel.setBounds(650, 272, 148, 128);
				gameInfoPanel.setBounds(140, 272, 148, 308);
				gameMenuBar.setBounds(900 - 329, 220, 150, icon_exitIcon.getIconWidth() + 10);
				button_mini.setBounds(900 - 150, 220, icon_exitIcon.getIconWidth(), icon_exitIcon.getIconHeight());
				button_exit.setBounds(900 - 115, 220, icon_miniIcon.getIconWidth(), icon_miniIcon.getIconHeight());

			}
			resetSkin();
			GameFrame.this.setSize(GameImages.getGameFrameBackground().getWidth(null), GameImages.getGameFrameBackground().getHeight(null));
			GameFrame.this.setLocationRelativeTo(null);
			GameFrame.this.repaint();
		}
	}

	/**
	 * 按键音处理
	 * 
	 * @author Nandem
	 *
	 */
	private class KeySoundHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			SoundPlayer.setSoundPlayFlag(!SoundPlayer.isSoundPlayFlag());
		}
	}

	/**
	 * 背景音乐处理
	 * 
	 * @author Nandem
	 *
	 */
	private class BGMHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			SoundPlayer.setBgmPlayFlag(!SoundPlayer.isBgmPlayFlag());
			if(GameFrame.carrier.getCurrent() != null)
			{
				SoundPlayer.playLoop();
			}
		}
	}

	/**
	 * 本地化事件处理
	 * 
	 * @author Nandem
	 *
	 */
	private class LocalizeHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			carrier.setPause(true);
			JMenuItem menuItem = (JMenuItem) e.getSource();
			GameConfig.setLanguage(menuItem.getName());
			bundle = ResourceBundle.getBundle("cn.nandem.resource.dict", new Locale(menuItem.getName()));
			localize();
			if(GameConfig.getLanguage().equals("en"))
			{
				GameImages.resetStringImages(true);
			}
			else if(GameConfig.getLanguage().equals("zh"))
			{
				GameImages.resetStringImages(false);
			}
			GameFrame.this.gameInfoPanel.repaint();
		}
	}

	/**
	 * 退出事件处理
	 * 
	 * @author Nandem
	 *
	 */
	private class ExitHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			GameConfig.saveConfig();
			Toolkit.getDefaultToolkit().beep();
			System.exit(0);
		}
	}

	/**
	 * 游戏面板，用来容纳游戏图层
	 * 
	 * @author Nandem
	 *
	 */
	private class GamePanel extends Panel
	{
		private Image bufferImage;
		@Override
		public void paint(Graphics g)
		{
			super.paint(g);
		}

		@Override
		public void update(Graphics g)
		{
			if(bufferImage == null)
			{
				bufferImage = this.createImage(this.getWidth(), this.getHeight());
			}
			Graphics gBuffer = bufferImage.getGraphics();// 获取内存画板的画笔
			if(gBuffer != null)
			{
				paint(gBuffer);
			}
			else
			{
				paint(g);
			}
			gBuffer.dispose();
			g.drawImage(bufferImage, 0, 0, null);// 将内容 一次性写到屏幕上
		}
	}

	public static DataCarrier getCarrier()
	{
		return carrier;
	}
}
