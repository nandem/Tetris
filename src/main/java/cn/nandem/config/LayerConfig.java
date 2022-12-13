package cn.nandem.config;

import org.dom4j.Element;

/**
 * ͼ�������࣬����ÿ����Ϸ������Ϸ�������λ�õ����ԡ�
 * @author Nandem
 *
 */
public class LayerConfig {
	private int x;
	private int y;
	private String className;
	
	public LayerConfig(Element element){
		x = Integer.parseInt(element.attributeValue("x"));
		y = Integer.parseInt(element.attributeValue("y"));
		className = element.attributeValue("name");
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String getClassName() {
		return className;
	}
}
