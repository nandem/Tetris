/**
 * 功能描述：游戏数据载体，在界面和游戏业务间进行传递，包含游戏Map、当前方块、下一个方块、游戏信息
 */
package cn.nandem.model;

import java.awt.TextArea;
import java.awt.TextField;

import cn.nandem.utils.SoundPlayer;
import cn.nandem.view.GameFrame;

/**
 * @author Nandem
 *
 */
public class DataCarrier
{
	private Mino current;
	private Mino next;
	private Map map;
	private boolean pause = false;
	private boolean over = false;
	private OverListener listener;
	private GameRecord record = new GameRecord();;

	public void addOverListener(OverListener listener)
	{
		this.listener = listener;
	}

	public void initialize()
	{
		current = MinoFactory.create();
		current.setCenter();
		next = MinoFactory.create();
		map = new Map();
		map.addRecordListener(new RecordListener()
		{
			@Override
			public void refresh(int rows)
			{
				record.modify(rows);//1.定义接口2.定义初始化事件监听器3.初始化事件监听器4.触发事件.5.定制
			}
		});
		pause = false;
		over = false;
		record.reset();
	}

	/**
	 * 移动方块
	 * @param xOffset 水平偏移量
	 * @param yOffset 垂直偏移量
	 * @return true 可以偏移并移动 false 不可偏移不移动
	 */
	public boolean moveMino(int xOffset, int yOffset)
	{
		if(!map.check(current.preMove(xOffset, yOffset)))
			return false;
		current.move(xOffset, yOffset);;
		return true;
	}

	/**
	 * 旋转方块
	 */
	public void rotateMino()
	{
		if(map.check(current.preRotate()))
			current.rotate();
	}

	/**
	 * 下落
	 */
	public void landed()
	{
		map.landed(current.getBlocks());
		current = next;
		current.setCenter();
		next = MinoFactory.create();
		if(!map.check(current.getBlocks()))
		{
			over = true;
			map.damaged();
			current.damaged();
			GameLeaderboard.getInstance().judgeCanBeRecorded(record.getScore());

			SoundPlayer.setBgmPlayFlag(!SoundPlayer.isBgmPlayFlag());
			SoundPlayer.setBgmIsStop(!SoundPlayer.isBgmIsStop());
			SoundPlayer.playLoop();
			SoundPlayer.play("gameover");
			SoundPlayer.setBgmPlayFlag(!SoundPlayer.isBgmPlayFlag());
			SoundPlayer.setBgmIsStop(!SoundPlayer.isBgmIsStop());
			listener.performed();
		}
	}

	public Mino getCurrent()
	{
		return current;
	}
	public Mino getNext()
	{
		return next;
	}
	public Map getMap()
	{
		return map;
	}

	public boolean isPause()
	{
		return pause;
	}

	public void setPause(boolean pause)
	{
		this.pause = pause;

		SoundPlayer.setBgmPlayFlag(!SoundPlayer.isBgmPlayFlag());
		SoundPlayer.playLoop();
	}

	public boolean isOver()
	{
		return over;
	}

	public GameRecord getRecord()
	{
		return record;
	}
}
