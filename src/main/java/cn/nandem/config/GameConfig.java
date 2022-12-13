/**
 * ������������Ϸ������
 */
package cn.nandem.config;

import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ��Ϸ�����࣬������Ϸ�����������������ˢ��ʱ��Ƭ���ٶȵȻ������ԡ�
 *@author Nandem
 */
public class GameConfig
{
	public static final int ROWS;
	public static final int COLS;
	public static final int TIME_SLICE; 
	private static int speed;
	private static int unit; // ���������λ
	private static String skinPath;
	private static String language;
	private static String pattern;
	private static Properties config;

	static
	{
		try
		{
			config = new Properties();
			config.load(GameConfig.class.getClassLoader().getResourceAsStream("configs/gameConfig.ini"));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

		skinPath = config.getProperty("skin");
		//skinPath = "images/chinastyle";
		setPattern(config.getProperty("pattern"));
		language = config.getProperty("language");
		ROWS = Integer.parseInt(config.getProperty("rows"));
		COLS = Integer.parseInt(config.getProperty("cols"));
		TIME_SLICE = Integer.parseInt(config.getProperty("timeslice"));
		unit = Integer.parseInt(config.getProperty("unit"));
		speed = Integer.parseInt(config.getProperty("speed"));
		if(Toolkit.getDefaultToolkit().getScreenSize().getHeight() < 800)
			unit = (unit * 3) / 4;
	}

	public static void saveConfig()
	{
		try
		{
			config.store(new FileOutputStream("configs/gameConfig.ini"), "configs");
		}
		catch(Exception e)
		{
		}

	}

	public static int getCellSize()
	{
		return unit * 4;
	}

	public static String getSkinPath()
	{
		return skinPath;
	}

	public static void setSkinPath(String skinPath)
	{
		GameConfig.skinPath = skinPath;
	}

	public static String getLanguage()
	{
		return language;
	}

	public static void setLanguage(String language)
	{
		GameConfig.language = language;
		config.setProperty("language", language);
	}

	public static int getUnit()
	{
		return unit;
	}

	public static String getPattern()
	{
		return pattern;
	}

	public static void setPattern(String pattern)
	{
		GameConfig.pattern = pattern;
	}

	public static int getSpeed()
	{
		return speed;
	}

	public static void setSpeed(int speed)
	{
		GameConfig.speed = speed;
	}
}
