/**
 * 功能描述：游戏方块，包含Block的数组，提供移动/旋转与预移动/预旋转等功能 
 */
package cn.nandem.model;

import cn.nandem.config.GameConfig;
import cn.nandem.config.MinoConfig;
import cn.nandem.utils.Point;

/**
 * @author Nandem
 *
 */
public abstract class Mino implements Cloneable
{
	private Block[] blocks;
	private int width, height;
	protected double degree = Math.PI / 2;
	
	protected abstract void turn();

	public Mino(Block[] blocks, int width, int height)
	{
		this.blocks = blocks;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * 游戏结束，方块销毁方法
	 */
	public void damaged()
	{
		for(Block block : blocks)
		{
			block.damaged();
		}
	}
	
	@Override
	protected Object clone()
	{
		Mino mino = null;
		try
		{
			mino = (Mino)super.clone();
			Block[] dest = new Block[blocks.length];

			int i = 0;
			for(Block block : blocks)
			{
				dest[i++] = (Block) block.clone();
			}
			mino.blocks = dest;
		}
		catch(Exception e){}
		return mino;
	}
	
	/**
	 * 方块移动
	 * @param xOffset x轴偏移量
	 * @param yOffset y轴偏移量
	 */
	public void move(int xOffset, int yOffset)
	{
		for(Block block : blocks)
		{
			block.setColumn(block.getColumn() + xOffset);
			block.setRow(block.getRow() + yOffset);
		}
	}
	
	/**
	 * 预移动，判断能不能移动
	 * @param xOffset
	 * @param yOffset
	 * @return
	 */
	public Block[] preMove(int xOffset, int yOffset)
	{
		Mino mino = (Mino)clone();
		mino.move(xOffset, yOffset);
		return mino.blocks;
	}
	
	/**
	 * 方块旋转, 逆时针
	 */
	public void rotate()
	{
		int xO = blocks[0].getColumn();
		int yO = blocks[0].getRow();
		for(Block block : blocks)
		{
			int x = xO + (int) Math.sin(degree) * (block.getRow() - yO)
					+ (int) Math.cos(degree) * (block.getColumn() - xO);
			int y = yO + (int) Math.cos(degree) * (block.getRow() - yO)
					- (int) Math.sin(degree) * (block.getColumn() - xO);
			block.setColumn(x);
			block.setRow(y);
		}
		turn();
	}
	
	/**
	 * 预旋转，判断能不能旋转
	 * @return 若果能旋转则返回旋转后的方块，否则返回原来的方块
	 */
	public Block[] preRotate()
	{
		Mino mino = (Mino) clone();
		mino.rotate();
		return mino.blocks;
	}
	
	/**
	 * 每次产生方块时，设置其位置居中
	 */
	public void setCenter()
	{
		int xOffset = 7 - blocks[0].getColumn();
		for(Block block : blocks)
		{
			block.setColumn(block.getColumn() + xOffset);
		}
	}
	
	/**
	 * 获取Mino的宽度，像素单位
	 * @return Mino的宽度
	 */
	public int getWidth()
	{
		return width * 4 * GameConfig.getUnit();
	}
	/**
	 * 获取Mino的高度，像素单位
	 * @return Mino的高度
	 */
	public int getHeight()
	{
		return height * 4 * GameConfig.getUnit();
	}
	
	/**
	 * 返回方块
	 * @return 返回方块
	 */
	public Block[] getBlocks()
	{
		return blocks;
	}
}
