/**
 * 功能描述：游戏信息层， 显示游戏信息(等级、得分、升级进度等) 
 */
package cn.nandem.view;

import java.awt.Graphics;
import java.awt.Image;

import cn.nandem.config.GameConfig;
import cn.nandem.model.GameRecord;
import cn.nandem.utils.DigitImage;
import cn.nandem.utils.GameImages;

/**
 * @author Nandem
 *
 */
public class InfomationLayer extends Layer
{
	private GameRecord record;
	
	public InfomationLayer(int x, int y)
	{
		super(x, y);
		width = 6 * GameConfig.getCellSize() + GameConfig.getUnit() * 2;
		height = 14 * GameConfig.getCellSize() + GameConfig.getUnit() * 2;
		cw = width - 2 * GameConfig.getUnit();
		ch = height - 2 * GameConfig.getUnit();
		record = GameFrame.getCarrier().getRecord();
	}

	@Override
	protected void drawContentArea(Graphics g)
	{
		drawPlayer(g, 1);
		drawLevel(g, 3);
		drawRow(g, 6);
		drawPoints(g, 9);
		drawProgressBar(g, 12);
	}

	private void drawProgressBar(Graphics g, int row)
	{
		Image img = GameImages.getGameProcessBar();
		int iw = img.getWidth(null);
		int ih = img.getHeight(null);
		int cx = super.cx;
		int cy = 4 * row * GameConfig.getUnit() + super.cy + 2 * GameConfig.getUnit();
		int cw = 24 * GameConfig.getUnit();
		int ch = 2 * GameConfig.getUnit();
		g.draw3DRect(cx, cy, cw, ch, true);
		
		
		GameRecord r = GameFrame.getCarrier().getRecord();
		double progress =(r.getLines() -  (r.getLevel() - 1) * 20) / 20.0;
		g.drawImage(img, cx + 1, cy + 1, (int) (progress * cw) + cx, cy + ch,
				0, 0, (int) (iw * progress), ih, null);
	}

	private void drawRow(Graphics g, int row)
	{
		Image img = GameImages.getGameRow();
		int cx = 4 * GameConfig.getUnit() + super.cx;
		int cy = 4 * row * GameConfig.getUnit() + super.cy;
		int cw = 16 * GameConfig.getUnit();
		int ch = 4 * GameConfig.getUnit();
		g.drawImage(img, cx, cy, cw, ch, null);
		
		GameRecord r = GameFrame.getCarrier().getRecord();
		Image levelNumber = DigitImage.getImage(r.getLines());
		g.drawImage(levelNumber, cx + (cw- levelNumber.getWidth(null) ) / 2, cy + ch + 2 * GameConfig.getUnit(), null);
	}

	private void drawPoints(Graphics g, int row)
	{
		Image img = GameImages.getGamePoint();
		int cx = 4 * GameConfig.getUnit() + super.cx;
		int cy = 4 * row * GameConfig.getUnit() + (y + GameConfig.getUnit());
		int cw = 16 * GameConfig.getUnit();
		int ch = 4 * GameConfig.getUnit();
		g.drawImage(img, cx, cy, cw, ch, null);

		GameRecord r = GameFrame.getCarrier().getRecord();
		Image levelNumber = DigitImage.getImage(r.getScore());
		g.drawImage(levelNumber, cx + (cw- levelNumber.getWidth(null) ) / 2, cy + ch + 2 * GameConfig.getUnit(), null);
	}

	private void drawLevel(Graphics g, int row)
	{
		Image img = GameImages.getGameLevel();
		int cx = 4 * GameConfig.getUnit() + super.cx;
		int cy = 4 * row * GameConfig.getUnit() + super.cy;
		int cw = 16 * GameConfig.getUnit();
		int ch = 4 * GameConfig.getUnit();
		g.drawImage(img, cx, cy, cw, ch, null);

		GameRecord r = GameFrame.getCarrier().getRecord();
		Image levelNumber = DigitImage.getImage(r.getLevel());
		g.drawImage(levelNumber, cx + (cw- levelNumber.getWidth(null) ) / 2, cy + ch + 2 * GameConfig.getUnit(), null);
	}

	private void drawPlayer(Graphics g, int row)
	{
		Image img = GameImages.getGamePlayer();
		int x = 4 * GameConfig.getUnit() + super.cx;
		int y = 4 * row * GameConfig.getUnit() + super.cy;
		int w = 16 * GameConfig.getUnit();
		int h = 4 * GameConfig.getUnit();
		g.drawImage(img, x, y, w, h, null);
	}
}
