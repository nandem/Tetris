/**
 * 功能描述：游戏最小组成单元(实体类)，构建Mino，Map，包含位置信息、方块类型信息、 
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
	 * 成员变量定义，用于保存类或对象的数据（信息）
	 * get或set访问器：提供成员变量的受控访问
	 * 构造方法：初始化成员变量
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
	 * @return 返回小方块的客户横区坐标，像素单位
	 */
	public int getLeft()
	{
		return left * 4 * GameConfig.getUnit();
	}
	
	/**
	 * 返回小方块的列号，4个unit单位
	 * @return
	 */
	public int  getColumn()
	{
		return left;
	}
	
	/**
	 * 设置小方块的列号，4个unit单位
	 * @param left
	 */
	public void setColumn(int left)
	{
		this.left = left;
	}
	
	/**
	 * @return 返回小方块的客户区纵坐标，像素单位
	 */
	public int getTop()
	{
		return top * 4 * GameConfig.getUnit();
	}
	
	/**
	 * 获得小方块的行号，4unit
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
	 * @return 获得对应的图片 
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
