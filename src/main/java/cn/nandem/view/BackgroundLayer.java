/**
 * π¶ƒ‹√Ë ˆ£∫±≥æ∞≤„£¨ªÊ÷∆±≥æ∞Õº∆¨ 
 */
package cn.nandem.view;

import java.awt.Graphics;
import java.awt.Image;

import cn.nandem.config.CanvasConfig;
import cn.nandem.config.GameConfig;
import cn.nandem.model.GameRecord;
import cn.nandem.utils.GameImages;

/**
 * @author Nandem
 *
 */
public class BackgroundLayer extends Layer
{

	public BackgroundLayer(int x, int y)
	{
		super(x, y);
		width = CanvasConfig.getWidth();
		height = CanvasConfig.getHeight();
		border = false;
	}

	@Override
	protected void drawContentArea(Graphics g)
	{
		GameRecord record = GameFrame.getCarrier().getRecord();
		Image backgroundImages = GameImages.getGamePanelBackgrounds(record.getLevel());
		g.drawImage(backgroundImages, x, y, width, height + 3, null);
	}

}
