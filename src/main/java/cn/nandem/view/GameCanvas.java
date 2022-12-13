/**
 * 功能描述：游戏画布，组装各视图层，监听键盘 
 */
package cn.nandem.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Panel;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import cn.nandem.config.CanvasConfig;
import cn.nandem.config.LayerConfig;
import cn.nandem.model.DataCarrier;
import cn.nandem.utils.ShadowBorder;

/**
 * @author Nandem
 *
 */
public class GameCanvas extends JPanel
{
	private static Layer[] layers;
	private Layer layer;
	private static Constructor<?> ctor;
	private static int layerIndex;

	static
	{
		layers = new Layer[CanvasConfig.getLayers().size()];
		for(int i = 0; i < layers.length; i++)
		{
			LayerConfig cfg = CanvasConfig.getLayers().get(i);
			try
			{
				Class<?> cls = Class.forName(cfg.getClassName());
				ctor = cls.getConstructor(int.class, int.class);
				layers[i] = (Layer) ctor.newInstance(1, 1);
				// layers[i].setCarrier(carrier);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public GameCanvas(int x, int y)
	{
		layer = layers[++layerIndex];
		this.setBorder(BorderFactory.createCompoundBorder(ShadowBorder.newInstance(), BorderFactory.createLineBorder(new Color(230, 0, 0), 2)));
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		// for(Layer layer : layers)
		// {
		layer.draw(g);
		// }
	}
}
