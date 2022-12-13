/**
 * 功能描述：玩家控制器，包含游戏中控，键盘事件处理程序 
 */
package cn.nandem.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import cn.nandem.utils.KeyCalibration;

/**
 * @author Nandem
 *
 */
public class PlayerController extends KeyAdapter
{
	private GameController gameController;
	private static int keyLeft;
	private static int keyRight;
	private static int keyUp;
	private static int keyDown;
	private static int keyLanded;
	private static int keyPause;

	public PlayerController(GameController gameController)
	{
		this.gameController = gameController;

		keyLeft = Integer.valueOf(KeyCalibration.getKeyValue("left"));
		keyRight = Integer.valueOf(KeyCalibration.getKeyValue("right"));
		keyUp = Integer.valueOf(KeyCalibration.getKeyValue("rotate"));
		keyDown = Integer.valueOf(KeyCalibration.getKeyValue("down"));
		keyLanded = Integer.valueOf(KeyCalibration.getKeyValue("land"));
		keyPause = Integer.valueOf(KeyCalibration.getKeyValue("pause"));
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == keyLeft)
		{
			gameController.moveToLeft();
		}
		else if(e.getKeyCode() == keyRight)
		{
			gameController.moveToRight();
		}
		else if(e.getKeyCode() == keyUp)
		{
			gameController.rotate();
		}
		else if(e.getKeyCode() == keyDown)
		{
			gameController.moveToBottom();
		}
		else if(e.getKeyCode() == keyLanded)
		{
			gameController.moveToBottomImmediately();
		}
		else if(e.getKeyCode() == keyPause)
		{
			gameController.pause();
		}
		if(e.getKeyText(e.getKeyCode()).equals("Alt"))
			e.consume();
	}

	public static void setKeyLeft(int keyLeft)
	{
		PlayerController.keyLeft = keyLeft;
	}

	public static void setKeyRight(int keyRight)
	{
		PlayerController.keyRight = keyRight;
	}

	public static void setKeyUp(int keyUp)
	{
		PlayerController.keyUp = keyUp;
	}

	public static void setKeyDown(int keyDown)
	{
		PlayerController.keyDown = keyDown;
	}

	public static void setKeyLanded(int keyLanded)
	{
		PlayerController.keyLanded = keyLanded;
	}

	public static void setKeyPause(int keyPause)
	{
		PlayerController.keyPause = keyPause;
	}
}
