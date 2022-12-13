package cn.nandem.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * ��Ϸ�������ã�����ÿ����Ϸ��������ꡢ��ߵ����ԡ�
 * @author Nandem
 *
 */
public class CanvasConfig
{
	private static int width;
	private static int height;
	private static ArrayList<LayerConfig> layers;

	static
	{
		try
		{
			SAXReader reader = new SAXReader();
			Document document = reader.read(CanvasConfig.class.getClassLoader().getResourceAsStream("configs/canvas.xml"));
			Element window = document.elementByID(GameConfig.getPattern());
			width = Integer.parseInt(window.attributeValue("width"));
			height = Integer.parseInt(window.attributeValue("height"));
			layers = new ArrayList<LayerConfig>();
			List<Element> elements = window.elements("layer");
			for(Element element : elements)
				layers.add(new LayerConfig(element));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @return �ͻ������ڿ�ȣ�����Ϊ��λ
	 */
	public static int getWidth()
	{
		return width * GameConfig.getUnit();
	}
	/**
	 * @return �ͻ������ڸ߶ȣ�����Ϊ��λ
	 */
	public static int getHeight()
	{
		return height * GameConfig.getUnit();
	}
	public static ArrayList<LayerConfig> getLayers()
	{
		return layers;
	}

}
