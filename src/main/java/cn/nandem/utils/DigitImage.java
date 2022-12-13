/**
 * 
 */
package cn.nandem.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import cn.nandem.config.GameConfig;

/**
 * @author Nandem
 *
 */
public class DigitImage
{
	public static Image getImage(int value)
	{
		Image[] imgs = GameImages.getGameNumImgs();
		int bits = String.valueOf(value).length();
		if(bits == 1)
		{
			return imgs[value];
		}
		int iw = imgs[0].getWidth(null);
		int ih = imgs[0].getHeight(null);
		BufferedImage bufferedImage = new BufferedImage(iw * bits, ih, BufferedImage.TYPE_INT_ARGB);
		int n = bits - 1;
		Graphics g = bufferedImage.getGraphics();
		while(value != 0)
		{
			int x = value % 10;
			Image number = imgs[x];
			g.drawImage(number, iw * n, 0, null);
			value /= 10;
			n--;
		}
		return bufferedImage;
	}
}
