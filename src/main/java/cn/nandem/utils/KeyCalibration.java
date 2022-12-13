/**
 * 
 */
package cn.nandem.utils;

import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * @author Nandem
 *
 */
public class KeyCalibration
{
	private static Properties config;
	static
	{

		try
		{
			config = new Properties();
			config.load(KeyCalibration.class.getClassLoader().getResourceAsStream("configs/keyConfig.ini"));
		}
		catch(Exception e)
		{
		}
	}
	
	public static void setKeyAndValue(String key, String value)
	{
		config.setProperty(key, value);
	}
	
	public static String getKeyValue(String key)
	{
		return config.getProperty(key);
	}

	public static void saveConfig()
	{
		try
		{
			config.store(new FileOutputStream("configs/keyConfig.ini"), "Keyvalues configs");
		}
		catch(Exception e)
		{
		}

	}

}
