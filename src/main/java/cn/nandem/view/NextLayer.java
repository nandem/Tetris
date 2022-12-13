/**
 * 功能描述：下一个方块，绘制下一个方块 
 */
package cn.nandem.view;

import java.awt.Graphics;
import java.awt.Image;

import cn.nandem.config.GameConfig;
import cn.nandem.model.Block;
import cn.nandem.model.Mino;

/**
 * @author Nandem
 *
 */
public class NextLayer extends Layer
{

	public NextLayer(int x, int y)
	{
		super(x, y);
		width = 6 * GameConfig.getCellSize() + GameConfig.getUnit() * 2;
		height = 5 * GameConfig.getCellSize() + GameConfig.getUnit() * 2;
		cw = width - 2 * GameConfig.getUnit();
		ch = height - 2 * GameConfig.getUnit();
	}

	@Override
	protected void drawContentArea(Graphics g)
	{
		Mino mino = GameFrame.getCarrier().getNext();
		if(mino == null)
			return;
		for(Block block: mino.getBlocks())
		{
			Image img = block.getImage();
			int dx = cx + (cw - mino.getWidth()) / 2 + block.getLeft();
			int dy = cy + (ch - mino.getHeight()) / 2 + block.getTop();
			g.drawImage(img, dx, dy, null);
		}
	}

}
