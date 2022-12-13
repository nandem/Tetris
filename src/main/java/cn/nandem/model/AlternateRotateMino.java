package cn.nandem.model;

public class AlternateRotateMino extends Mino
{

	public AlternateRotateMino(Block[] blocks, int width, int height)
	{
		super(blocks, width, height);
	}

	@Override
	protected void turn()
	{
		degree *= -1;
	}

}
