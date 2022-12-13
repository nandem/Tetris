/**
 * 
 */
package cn.nandem;

import java.awt.EventQueue;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import cn.nandem.config.CanvasConfig;
import cn.nandem.controller.GameController;
import cn.nandem.view.GameFrame;
import cn.nandem.view.Irregular;
import cn.nandem.view.WelcomeFrame;

/**
 * @author Nandem
 *
 */
public class TetrisEntry
{
	public static void main(String[] args)
	{
//		WelcomeFrame wf = new WelcomeFrame(0);
//		wf.showAndLoad();
		GameController game = new GameController();
		game.showWindow();
	}
}
