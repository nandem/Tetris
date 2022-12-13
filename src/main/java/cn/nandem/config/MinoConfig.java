package cn.nandem.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.nandem.model.MinoColor;
import cn.nandem.utils.Point;

/**
 * ∑ΩøÈ≈‰÷√¿‡°£
 *@author Nandem
 */
public class MinoConfig
{
	private String name;
	private MinoColor minoColor;
	private int width, height;
	private Point[] coords;
	private static ArrayList<MinoConfig> configs;

	static 
	{
		try
		{
			SAXReader read = new SAXReader();
			Document document = read.read(new File("configs/mino.xml"));
			Element rootElement = document.getRootElement();
			List<Element> minos = rootElement.elements();
			configs = new ArrayList<MinoConfig>();
			for(Element element : minos)
			{
				MinoConfig minoConfig = new MinoConfig();
				minoConfig.name = element.attributeValue("name");
				minoConfig.minoColor = Enum.valueOf(MinoColor.class, element.attributeValue("minocolor"));
				minoConfig.width = Integer.parseInt(element.attributeValue("width"));
				minoConfig.height = Integer.parseInt(element.attributeValue("height"));
				List<Element> blocks = element.elements();
				minoConfig.coords = new Point[blocks.size()];
				int i = 0;
				for(Element element2 : blocks)
				{
					int x = Integer.parseInt(element2.attributeValue("x"));
					int y = Integer.parseInt(element2.attributeValue("y"));
					minoConfig.coords[i] = new Point(x, y);
					i++;
				}
				configs.add(minoConfig);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	

	public String getName()
	{
		return name;
	}

	public MinoColor getMinoColor()
	{
		return minoColor;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public Point[] getCoords()
	{
		return coords;
	}

	public static ArrayList<MinoConfig> getConfigs()
	{
		return configs;
	}
}
