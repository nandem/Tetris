/**
 * 功能描述：视图层基类，包含层的基本信息(位置、大小等)和基本操作(绘制边框等) 
 */
package cn.nandem.view;

import java.awt.Graphics;
import java.awt.Image;

import cn.nandem.config.GameConfig;
import cn.nandem.model.DataCarrier;
import cn.nandem.utils.GameImages;

/**
 * @author Nandem
 *
 */
public abstract class Layer
{

	protected int x, y;
	protected int width, height;
	protected int cx, cy, cw, ch;
	protected boolean border = true;
	
	protected abstract void drawContentArea(Graphics g);

	public Layer(int x, int y)
	{
		this.x = x * GameConfig.getUnit();
		this.y = y * GameConfig.getUnit();
		this.cx = this.x + GameConfig.getUnit();
		this.cy = this.y + GameConfig.getUnit();
	}

	public void draw(Graphics g)
	{
		if(border)
			drawBorder(g);
		drawContentArea(g);
	}

	private void drawBorder(Graphics g)
	{
		Image img = GameImages.getGamePanelBorder();
		int d = img.getWidth(null);// 边框正方形边长
		int unit = GameConfig.getUnit();
		int imgBorderWidth = 8;
		g.drawImage(img, x, y, x + unit, y + unit, 0, 0, imgBorderWidth, imgBorderWidth, null);// 左上
		g.drawImage(img, x + unit, y, x + width - unit, y + unit, imgBorderWidth, 0, d - imgBorderWidth, imgBorderWidth, null);// 上
		g.drawImage(img, x + width - unit, y, x + width, y + unit, d - imgBorderWidth, 0, d, imgBorderWidth, null);// 右上
		g.drawImage(img, x + width - unit, y + unit, x + width, y + height - unit, d - imgBorderWidth, imgBorderWidth, d, d - imgBorderWidth, null);// 右
		g.drawImage(img, x + width - unit, y + height - unit, x + width, y + height, d - imgBorderWidth, d - imgBorderWidth, d, d, null);// 右下
		g.drawImage(img, x + unit, y + height - unit, x + width - unit, y + height, imgBorderWidth, d - imgBorderWidth, d - imgBorderWidth, d, null);// 下
		g.drawImage(img, x, y + height - unit, x + unit, y + height, 0, d - imgBorderWidth, imgBorderWidth, d, null);// 左下
		g.drawImage(img, x, y + unit, x + unit, y + height - unit, 0, imgBorderWidth, imgBorderWidth, d - imgBorderWidth, null);// 左
		g.drawImage(img, x + unit, y + unit, x + width - unit, y + height - unit, imgBorderWidth, imgBorderWidth, d - imgBorderWidth, d
				- imgBorderWidth, null);// 中
	}
}
