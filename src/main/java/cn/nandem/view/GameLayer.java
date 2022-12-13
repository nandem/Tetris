/**
 * 功能描述：游戏才呢过，负责游戏地图、方块等的绘制 
 */
package cn.nandem.view;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import cn.nandem.config.GameConfig;
import cn.nandem.model.Block;
import cn.nandem.model.DataCarrier;
import cn.nandem.model.Map;
import cn.nandem.model.Mino;
import cn.nandem.model.MinoFactory;
import cn.nandem.utils.GameImages;

/**
 * @author Nandem
 *
 */
public class GameLayer extends Layer
{
	private int flashCount = 0;
	public GameLayer(int x, int y)
	{
		super(x, y);
		width = (GameConfig.COLS + 5)* GameConfig.getCellSize() + GameConfig.getUnit() * 2 ;
		height = GameConfig.ROWS * GameConfig.getCellSize() + GameConfig.getUnit() * 2;
		cw = width - 2 * GameConfig.getUnit();
		ch = height - 2 * GameConfig.getUnit();
	}

	@Override
	protected void drawContentArea(Graphics g)
	{
		drawCurrentBlocks(g);
		drawMap(g);
	}

	private void drawMap(Graphics g)
	{
		Map map = GameFrame.getCarrier().getMap();
		if(map == null)
			return;
		ArrayList<Block> blocks = map.getLandedBlocks();
		for(int i = 0; i < blocks.size(); i++)
		{
			Image img = blocks.get(i).getImage();
			int dx = cx + blocks.get(i).getLeft();
			int dy = cy + blocks.get(i).getTop();
			g.drawImage(img, dx, dy, null);
		}
		
		if(map.getCompletedRows().length != 0)
		{
			flashLines(map.getCompletedRows(), g);
		}
	}

	private void flashLines(int[] completedRows, Graphics g)
	{
		for(int i = 0; i < completedRows.length; i++)
		{
			Image img = GameImages.getFlashImages(flashCount);
			int dx = cx;
			int dy = cy + completedRows[i] * 4 * GameConfig.getUnit();
			int dw = cw;
			int dh = img.getHeight(null);
			g.drawImage(img, dx, dy, dw, dh, null);
		}
		flashCount++;
		if(flashCount == 8)
		{
			flashCount = 0;
			Map map = GameFrame.getCarrier().getMap();
			map.removeLines();
		}
	}

	private void drawCurrentBlocks(Graphics g)
	{
		Mino mino = GameFrame.getCarrier().getCurrent();
		if(mino == null)
			return;
		for(Block block: mino.getBlocks())
		{
			Image img = block.getImage();
			int dx = cx + block.getLeft();
			int dy = cy + block.getTop();
			g.drawImage(img, dx, dy, null);
		}
	}
}
