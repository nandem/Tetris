package cn.nandem.model;

public class NotRotateMino extends Mino
{

	public NotRotateMino(Block[] blocks, int width, int height)
	{
		super(blocks, width, height);
		degree = 0;
	}

	@Override
	protected void turn()
	{
	}

}
