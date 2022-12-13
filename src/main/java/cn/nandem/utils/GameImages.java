/**
 * 功能描述：游戏图片类，几种加载游戏中用到的各种图片 
 */
package cn.nandem.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import cn.nandem.config.GameConfig;

/**
 * @author Nandem
 *
 */
public class GameImages
{
	private static Image[] gamePanelBackgrounds;
	private static Image gamePanelBorder;
	private static Image gameFrameBackground;
	private static Image leaderboardBackground;
	private static ImageIcon gameFrameMiniButtonSelected;
	private static ImageIcon gameFrameMiniButtonUnselected;
	private static ImageIcon gameFrameCloseButtonSelected;
	private static ImageIcon gameFrameCloseButtonUnselected;
	private static ImageIcon welcomeBackground;

	private static Image gamePlayer;
	private static Image gameLevel;
	private static Image gamePoint;
	private static Image gameRow;
	private static Image gameNext;
	private static Image gameNumbers;
	private static Image gameProcessBar;
	private static Image flashes;
	private static Image gameMinos;
	private static Image[] gameMinoImages;
	private static Image[] gameNumberImages;
	private static Image[] flashImages;

	static
	{
		welcomeBackground = new ImageIcon("images/welcome.png");
		gamePanelBackgrounds = new Image[10];
		gamePanelBorder = new ImageIcon(GameConfig.getSkinPath() + "/game/window.png").getImage();
		gameFrameBackground = new ImageIcon(GameConfig.getSkinPath() + "/interface.png").getImage();
		leaderboardBackground = new ImageIcon(GameConfig.getSkinPath() + "/leaderboard.png").getImage();
		gameFrameMiniButtonSelected = new ImageIcon(GameConfig.getSkinPath() + "/strings/miniButtonSelected.png");
		gameFrameMiniButtonUnselected = new ImageIcon(GameConfig.getSkinPath() + "/strings/miniButtonUnselected.png");
		gameFrameCloseButtonSelected = new ImageIcon(GameConfig.getSkinPath() + "/strings/closeButtonSelected.png");
		gameFrameCloseButtonUnselected = new ImageIcon(GameConfig.getSkinPath() + "/strings/closeButtonUnselected.png");
		if(GameConfig.getLanguage().equals("en"))
		{
			gamePlayer = new ImageIcon(GameConfig.getSkinPath() + "/strings/english/player.png").getImage();
			gameLevel = new ImageIcon(GameConfig.getSkinPath() + "/strings/english/level.png").getImage();
			gamePoint = new ImageIcon(GameConfig.getSkinPath() + "/strings/english/points.png").getImage();
			gameRow = new ImageIcon(GameConfig.getSkinPath() + "/strings/english/lines.png").getImage();
		}
		else
		{
			gamePlayer = new ImageIcon(GameConfig.getSkinPath() + "/strings/player.png").getImage();
			gameLevel = new ImageIcon(GameConfig.getSkinPath() + "/strings/level.png").getImage();
			gamePoint = new ImageIcon(GameConfig.getSkinPath() + "/strings/points.png").getImage();
			gameRow = new ImageIcon(GameConfig.getSkinPath() + "/strings/lines.png").getImage();
		}
		gameNext = new ImageIcon(GameConfig.getSkinPath() + "/strings/nextlevel.png").getImage();
		gameNumbers = new ImageIcon(GameConfig.getSkinPath() + "/strings/numbers.png").getImage();
		gameProcessBar = new ImageIcon(GameConfig.getSkinPath() + "/game/process.png").getImage();
		gameMinos = new ImageIcon(GameConfig.getSkinPath() + "/game/square.png").getImage();
		flashes = new ImageIcon(GameConfig.getSkinPath() + "/game/flash.png").getImage();

		for(int i = 0; i < gamePanelBackgrounds.length; i++)
		{
			// String imgName = String.format("%s/bgimg/bg_%02d.png",
			// GameConfig.getSkinPath(), i + 1);
			gamePanelBackgrounds[i] = new ImageIcon(GameConfig.getSkinPath() + "/bgimg/bg_0" + (i + 1) + ".png").getImage();
		}

		gameNumberImages = new Image[10];
		split(gameNumberImages, gameNumbers, 2, 4);
		gameMinoImages = new Image[8];
		split(gameMinoImages, gameMinos, 4, 4);
		flashImages = new Image[8];
		split(flashImages, flashes, 4, 4);
	}

	public static void resetStringImages(boolean isEnglish)
	{
		if(isEnglish)
		{
			gamePlayer = new ImageIcon(GameConfig.getSkinPath() + "/strings/english/player.png").getImage();
			gameLevel = new ImageIcon(GameConfig.getSkinPath() + "/strings/english/level.png").getImage();
			gamePoint = new ImageIcon(GameConfig.getSkinPath() + "/strings/english/points.png").getImage();
			gameRow = new ImageIcon(GameConfig.getSkinPath() + "/strings/english/lines.png").getImage();
		}
		else
		{
			gamePlayer = new ImageIcon(GameConfig.getSkinPath() + "/strings/player.png").getImage();
			gameLevel = new ImageIcon(GameConfig.getSkinPath() + "/strings/level.png").getImage();
			gamePoint = new ImageIcon(GameConfig.getSkinPath() + "/strings/points.png").getImage();
			gameRow = new ImageIcon(GameConfig.getSkinPath() + "/strings/lines.png").getImage();
		}
	}

	public static void resetSkinImages()
	{
		gamePanelBackgrounds = new Image[10];
		gamePanelBorder = new ImageIcon(GameConfig.getSkinPath() + "/game/window.png").getImage();
		gameFrameBackground = new ImageIcon(GameConfig.getSkinPath() + "/interface.png").getImage();
		leaderboardBackground = new ImageIcon(GameConfig.getSkinPath() + "/leaderboard.png").getImage();
		gameFrameMiniButtonSelected = new ImageIcon(GameConfig.getSkinPath() + "/strings/miniButtonSelected.png");
		gameFrameMiniButtonUnselected = new ImageIcon(GameConfig.getSkinPath() + "/strings/miniButtonUnselected.png");
		gameFrameCloseButtonSelected = new ImageIcon(GameConfig.getSkinPath() + "/strings/closeButtonSelected.png");
		gameFrameCloseButtonUnselected = new ImageIcon(GameConfig.getSkinPath() + "/strings/closeButtonUnselected.png");
		if(GameConfig.getLanguage().equals("en"))
		{
			gamePlayer = new ImageIcon(GameConfig.getSkinPath() + "/strings/english/player.png").getImage();
			gameLevel = new ImageIcon(GameConfig.getSkinPath() + "/strings/english/level.png").getImage();
			gamePoint = new ImageIcon(GameConfig.getSkinPath() + "/strings/english/points.png").getImage();
			gameRow = new ImageIcon(GameConfig.getSkinPath() + "/strings/english/lines.png").getImage();
		}
		else
		{
			gamePlayer = new ImageIcon(GameConfig.getSkinPath() + "/strings/player.png").getImage();
			gameLevel = new ImageIcon(GameConfig.getSkinPath() + "/strings/level.png").getImage();
			gamePoint = new ImageIcon(GameConfig.getSkinPath() + "/strings/points.png").getImage();
			gameRow = new ImageIcon(GameConfig.getSkinPath() + "/strings/lines.png").getImage();
		}
		gameNext = new ImageIcon(GameConfig.getSkinPath() + "/strings/nextlevel.png").getImage();
		gameNumbers = new ImageIcon(GameConfig.getSkinPath() + "/strings/numbers.png").getImage();
		gameProcessBar = new ImageIcon(GameConfig.getSkinPath() + "/game/process.png").getImage();
		gameMinos = new ImageIcon(GameConfig.getSkinPath() + "/game/square.png").getImage();
		flashes = new ImageIcon(GameConfig.getSkinPath() + "/game/flash.png").getImage();

		for(int i = 0; i < gamePanelBackgrounds.length; i++)
		{
			// String imgName = String.format("%s/bgimg/bg_%02d.png",
			// GameConfig.getSkinPath(), i + 1);
			gamePanelBackgrounds[i] = new ImageIcon(GameConfig.getSkinPath() + "/bgimg/bg_0" + (i + 1) + ".png").getImage();
		}

		gameNumberImages = new Image[10];
		split(gameNumberImages, gameNumbers, 2, 4);
		gameMinoImages = new Image[8];
		split(gameMinoImages, gameMinos, 4, 4);
		flashImages = new Image[8];
		split(flashImages, flashes, 4, 4);
	}

	private static void split(Image[] images, Image img, int w, int h)
	{
		int sw = img.getWidth(null) / images.length;
		int sh = img.getHeight(null);
		int iw = w * GameConfig.getUnit();
		int ih = h * GameConfig.getUnit();
		for(int i = 0; i < images.length; i++)
		{
			BufferedImage temp = new BufferedImage(iw, ih, BufferedImage.TYPE_INT_ARGB);
			Graphics g = temp.getGraphics();
			g.drawImage(img, 0, 0, iw, ih, i * sw, 0, (i + 1) * sw, sh, null);
			images[i] = temp;
		}
	}

	public static Image getGamePanelBorder()
	{
		return gamePanelBorder;
	}

	public static Image getGamePanelBackgrounds(int index)
	{
		return gamePanelBackgrounds[index % 10];
	}

	public static Image getGamePlayer()
	{
		return gamePlayer;
	}

	public static Image getGameLevel()
	{
		return gameLevel;
	}

	public static Image getGamePoint()
	{
		return gamePoint;
	}

	public static Image getGameNext()
	{
		return gameNext;
	}

	public static Image getGameRow()
	{
		return gameRow;
	}

	public static Image[] getGameNumImgs()
	{
		return gameNumberImages;
	}

	public static Image getMinoImage(int index)
	{
		return gameMinoImages[index];
	}

	public static Image getGameProcessBar()
	{
		return gameProcessBar;
	}

	public static Image getFlashImages(int index)
	{
		return flashImages[index];
	}

	public static Image getGameFrameBackground()
	{
		return gameFrameBackground;
	}

	public static ImageIcon getGameFrameMiniButtonSelected()
	{
		return gameFrameMiniButtonSelected;
	}

	public static ImageIcon getGameFrameMiniButtonUnselected()
	{
		return gameFrameMiniButtonUnselected;
	}

	public static ImageIcon getGameFrameCloseButtonSelected()
	{
		return gameFrameCloseButtonSelected;
	}

	public static ImageIcon getGameFrameCloseButtonUnselected()
	{
		return gameFrameCloseButtonUnselected;
	}

	public static Image getLeaderboardBackground()
	{
		return leaderboardBackground;
	}

	public static ImageIcon getWelcomeBackground()
	{
		return welcomeBackground;
	}
}
