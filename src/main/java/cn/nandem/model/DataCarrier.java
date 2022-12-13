/**
 * ������������Ϸ�������壬�ڽ������Ϸҵ�����д��ݣ�������ϷMap����ǰ���顢��һ�����顢��Ϸ��Ϣ
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
				record.modify(rows);//1.����ӿ�2.�����ʼ���¼�������3.��ʼ���¼�������4.�����¼�.5.����
			}
		});
		pause = false;
		over = false;
		record.reset();
	}

	/**
	 * �ƶ�����
	 * @param xOffset ˮƽƫ����
	 * @param yOffset ��ֱƫ����
	 * @return true ����ƫ�Ʋ��ƶ� false ����ƫ�Ʋ��ƶ�
	 */
	public boolean moveMino(int xOffset, int yOffset)
	{
		if(!map.check(current.preMove(xOffset, yOffset)))
			return false;
		current.move(xOffset, yOffset);;
		return true;
	}

	/**
	 * ��ת����
	 */
	public void rotateMino()
	{
		if(map.check(current.preRotate()))
			current.rotate();
	}

	/**
	 * ����
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
