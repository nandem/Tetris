/**
 * 
 */
package cn.nandem.model;

import java.lang.reflect.Constructor;
import java.util.Random;

import cn.nandem.config.MinoConfig;
import cn.nandem.utils.Point;

/**
 * ���鹤�����ڴ�ʵ�ʲ���ÿ���µķ��顣
 *@author Nandem
 */
public class MinoFactory
{
	private static Mino[] minos;
	
	static 
	{
		try
		{
			minos = new Mino[MinoConfig.getConfigs().size()];
			int i = 0;
			for(MinoConfig minoConfig : MinoConfig.getConfigs())
			{
				Class<?> cls = Class.forName(minoConfig.getName());
				Constructor<?> ctor = cls.getConstructor(Block[].class, int.class, int.class);
				Block[] blocks = new Block[minoConfig.getCoords().length];
				int j = 0;
				for(Point p : minoConfig.getCoords())
				{
					blocks[j++] = new Block(p.getX(), p.getY(), minoConfig.getMinoColor());
				}
				minos[i++] = (Mino) ctor.newInstance(blocks, minoConfig.getWidth(), minoConfig.getHeight());
			}
		}catch(Exception e){}
	}
	
	/**
	 * �����������
	 * @return �²����ķ���
	 */
	public static Mino create()
	{
		Random random = new Random();
		int index = random.nextInt(minos.length);
		return (Mino) minos[index].clone();
	}
}
