/**
 * 
 */
package cn.nandem.model;

/**
 * @author Nandem
 *
 */
public class FullRotateMino extends Mino
{
	/**
	 * @param blocks
	 * @param width
	 * @param height
	 */
	public FullRotateMino(Block[] blocks, int width, int height)
	{
		super(blocks, width, height);
	}
	@Override
	protected void turn()
	{
		degree *= 1;
	}

}
