package cn.nandem.config;

import org.dom4j.Element;

/**
 * 图层配置类，包含每个游戏层在游戏区域里的位置等属性。
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
