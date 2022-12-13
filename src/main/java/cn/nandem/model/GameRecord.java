/**
 * 
 */
package cn.nandem.model;

import cn.nandem.config.GameConfig;
import cn.nandem.utils.SoundPlayer;

/**
 * @author Nandem
 *
 */
public class GameRecord
{
	private int lines;
	private int score;
	private int level = 0;
	private static int[] rules = {0, 10, 30, 60, 100};
	
	public void modify(int rows)
	{
		lines += rows;
		score += rules[rows];
		if(lines / 20 + 1 > level)
			SoundPlayer.play("levelup");
		level = lines / 20 + 1;
		int x = level > 10 ? 10 : level;
		int speed = (20 - x * 3 / 2 - 1);
		GameConfig.setSpeed(speed);
	}
	
	public void reset()
	{
		lines = 0;
		score = 0;
		level = 0;
		int x = level > 10 ? 10 : level;
		int speed = (20 - x * 3 / 2 - 1);
		GameConfig.setSpeed(speed);
	}
	
	public int getLines()
	{
		return lines;
	}
	public int getScore()
	{
		return score;
	}
	public int getLevel()
	{
		return level;
	}
}
