/**
 * 功能描述：游戏地图，包含落地Block集，提供边界检测、消行操作、游戏结束判断 
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
	 * 方块销毁
	 */
	public void damaged()
	{
		for(Block block : landedBlocks)
		{
			block.damaged();
		}
	}

	/**
	 * 消行操作
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
	 * 重置填充状态，每次消行后，让已落地的方块整体下移
	 * @param row 需要下移的行数
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
	 * 已落地的方块整体下移
	 * @param row 整体下移行数
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
	 * 检测。包含边界检测和方块间的碰撞检测
	 * @param blocks 被检测的方块
	 * @return true 碰撞   false 未碰撞
	 */
	public boolean check(Block[] blocks)
	{
		return checkBound(blocks) && checkCollision(blocks);
	}

	/**
	 * 方块间的碰撞检测
	 * @param blocks 被检测方块
	 * @return true 碰撞 false 未碰撞
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
	 * 边界检测
	 * @param blocks 被检测方块
	 * @return true 超越边界 false 未超越边界
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
	 * 获得已经落地的方块
	 * @return 已经落地的方块
	 */
	public ArrayList<Block> getLandedBlocks()
	{
		return landedBlocks;
	}

	/**
	 * 获得完成的行数
	 * @return 完成的行数
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
