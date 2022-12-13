/**
 * ������������Ϸ���飬����Block�����飬�ṩ�ƶ�/��ת��Ԥ�ƶ�/Ԥ��ת�ȹ��� 
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
	 * ��Ϸ�������������ٷ���
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
	 * �����ƶ�
	 * @param xOffset x��ƫ����
	 * @param yOffset y��ƫ����
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
	 * Ԥ�ƶ����ж��ܲ����ƶ�
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
	 * ������ת, ��ʱ��
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
	 * Ԥ��ת���ж��ܲ�����ת
	 * @return ��������ת�򷵻���ת��ķ��飬���򷵻�ԭ���ķ���
	 */
	public Block[] preRotate()
	{
		Mino mino = (Mino) clone();
		mino.rotate();
		return mino.blocks;
	}
	
	/**
	 * ÿ�β�������ʱ��������λ�þ���
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
	 * ��ȡMino�Ŀ�ȣ����ص�λ
	 * @return Mino�Ŀ��
	 */
	public int getWidth()
	{
		return width * 4 * GameConfig.getUnit();
	}
	/**
	 * ��ȡMino�ĸ߶ȣ����ص�λ
	 * @return Mino�ĸ߶�
	 */
	public int getHeight()
	{
		return height * 4 * GameConfig.getUnit();
	}
	
	/**
	 * ���ط���
	 * @return ���ط���
	 */
	public Block[] getBlocks()
	{
		return blocks;
	}
}
