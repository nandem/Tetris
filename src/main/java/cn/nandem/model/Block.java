/**
 * ������������Ϸ��С��ɵ�Ԫ(ʵ����)������Mino��Map������λ����Ϣ������������Ϣ�� 
 */
package cn.nandem.model;

import java.awt.Graphics;
import java.awt.Image;

import cn.nandem.config.GameConfig;
import cn.nandem.utils.GameImages;

/**
 * @author Nandem
 *
 */
public class Block implements Cloneable
{
	/**
	 * ��Ա�������壬���ڱ�������������ݣ���Ϣ��
	 * get��set���������ṩ��Ա�������ܿط���
	 * ���췽������ʼ����Ա����
	 */
	
	private int left;
	private int top;
	private MinoColor minoColor;
	
	
	public Block(int left, int top, MinoColor minoColor)
	{
		this.left = left;
		this.top = top;
		this.minoColor = minoColor;
	}
	
	/**
	 * @return ����С����Ŀͻ��������꣬���ص�λ
	 */
	public int getLeft()
	{
		return left * 4 * GameConfig.getUnit();
	}
	
	/**
	 * ����С������кţ�4��unit��λ
	 * @return
	 */
	public int  getColumn()
	{
		return left;
	}
	
	/**
	 * ����С������кţ�4��unit��λ
	 * @param left
	 */
	public void setColumn(int left)
	{
		this.left = left;
	}
	
	/**
	 * @return ����С����Ŀͻ��������꣬���ص�λ
	 */
	public int getTop()
	{
		return top * 4 * GameConfig.getUnit();
	}
	
	/**
	 * ���С������кţ�4unit
	 * @return
	 */
	public int getRow()
	{
		return top;
	}
	
	public void setRow(int right)
	{
		this.top = right;
	}
	
	public void damaged()
	{
		this.minoColor = MinoColor.Damaged;
	}
	
	/**
	 * @return ��ö�Ӧ��ͼƬ 
	 */
	public Image getImage()
	{
		return GameImages.getMinoImage(minoColor.ordinal());
	}

	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(!(obj instanceof Block))
			return false;
		
		Block block = (Block) obj;
		return this.left == block.left && this.top == block.top;
	}
}
