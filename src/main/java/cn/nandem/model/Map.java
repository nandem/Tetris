/**
 * ������������Ϸ��ͼ���������Block�����ṩ�߽��⡢���в�������Ϸ�����ж� 
 */
package cn.nandem.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import cn.nandem.config.GameConfig;
import cn.nandem.utils.SoundPlayer;
import cn.nandem.view.GameFrame;
import cn.nandem.view.InfomationLayer;

/**
 * @author Nandem
 *
 */
public class Map
{
	private ArrayList<Block> landedBlocks;
	private int[] filledColumsCountOfRows;
	private ArrayList<Integer> completedRows;
	private RecordListener listener;

	public void addRecordListener(RecordListener listener)
	{
		this.listener = listener;
	}

	public Map()
	{
		int capcity = GameConfig.ROWS * GameConfig.COLS;
		landedBlocks = new ArrayList<Block>(capcity);
		filledColumsCountOfRows = new int[GameConfig.ROWS];
		completedRows = new ArrayList<Integer>();
	}

	public void landed(Block[] blocks)
	{
		for(Block block : blocks)
		{
			landedBlocks.add(block);
			filledColumsCountOfRows[block.getRow()]++;
			if(filledColumsCountOfRows[block.getRow()] == (GameConfig.COLS + 5 ))
				completedRows.add(block.getRow());
		}
	}

	/**
	 * ��������
	 */
	public void damaged()
	{
		for(Block block : landedBlocks)
		{
			block.damaged();
		}
	}

	/**
	 * ���в���
	 */
	public void removeLines()
	{
		SoundPlayer.play("erase");
		Collections.sort(completedRows);
		for(int row : completedRows)
		{
			for(int i = landedBlocks.size() - 1; i >= 0; i--)
			{
				Block block = landedBlocks.get(i);
				if(block.getRow() == row)
				{
					landedBlocks.remove(block);
				}
			}
			shiftDown(row);
			resetFilledState(row);
		}
		if(listener != null)
			listener.refresh(completedRows.size());
		completedRows.clear();
	}

	/**
	 * �������״̬��ÿ�����к�������صķ�����������
	 * @param row ��Ҫ���Ƶ�����
	 */
	private void resetFilledState(int row)
	{
		for(int i = row; i > 0; i--)
		{
			filledColumsCountOfRows[i] = filledColumsCountOfRows[i - 1];
		}
		filledColumsCountOfRows[0] = 0;
	}

	/**
	 * ����صķ�����������
	 * @param row ������������
	 */
	private void shiftDown(int row)
	{
		for(Block block : landedBlocks)
		{
			if(block.getRow() < row)
				block.setRow(block.getRow() + 1);
		}
	}

	/**
	 * ��⡣�����߽���ͷ�������ײ���
	 * @param blocks �����ķ���
	 * @return true ��ײ   false δ��ײ
	 */
	public boolean check(Block[] blocks)
	{
		return checkBound(blocks) && checkCollision(blocks);
	}

	/**
	 * ��������ײ���
	 * @param blocks ����ⷽ��
	 * @return true ��ײ false δ��ײ
	 */
	private boolean checkCollision(Block[] blocks)
	{
		for(Block block : blocks)
		{
			if(landedBlocks.contains(block))
				return false;
		}
		return true;
	}

	/**
	 * �߽���
	 * @param blocks ����ⷽ��
	 * @return true ��Խ�߽� false δ��Խ�߽�
	 */
	private boolean checkBound(Block[] blocks)
	{
		for(Block block : blocks)
		{
			if(block.getColumn() < 0 || block.getColumn() >= GameConfig.COLS + 5|| block.getRow() < 0 || block.getRow() >= GameConfig.ROWS)
				return false;
		}
		return true;
	}

	/**
	 * ����Ѿ���صķ���
	 * @return �Ѿ���صķ���
	 */
	public ArrayList<Block> getLandedBlocks()
	{
		return landedBlocks;
	}

	/**
	 * �����ɵ�����
	 * @return ��ɵ�����
	 */
	public int[] getCompletedRows()
	{
		int[] rows = new int[completedRows.size()];
		int i = 0;
		for(int x : completedRows)
		{
			rows[i] = x;
			i++;
		}
		return rows;
	}
}
