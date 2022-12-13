/**
 * 
 */
package cn.nandem.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * @author Nandem
 *
 */
public class Animation extends JPanel
{
	private Image bufferImage;
	private Image img;
	private int x;
	private int y;
	private int stopX;
	private int stopY;
	private boolean xIsEnd;
	private boolean yIsEnd;
	private int zoomWidth;
	private int zoomHeight;
	private int imgWidth;
	private int imgHeight;
	private int speed;

	private boolean rotated;
	private boolean zoom;

	private static boolean firstImageFreeze;
	private static boolean secondImageFreeze;
	private static boolean thirdImageFreeze;
	private static boolean fourthImageFreeze;

	private static Image firstImage;
	private static Image secondImage;
	private static Image thirdImage;
	private static Image fourthImage;

	private static int firstStopX, firstStopY, firstImageWidth, firstImageHeight;
	private static int secondStopX, secondStopY, secondImageWidth, secondImageHeight;
	private static int thirdStopX, thirdStopY, thirdImageWidth, thirdImageHeight;
	private static int fourthStopX, fourthStopY, fourthImageWidth, fourthImageHeight;

	private static int imagesCnt[] = {1, 2, 3, 4};
	private static int imagesCntIndex = 0;
	private static int degree = 0;

	public Animation(Image img, int imgWidth, int imgHeight, Point startPosition, Point endPosition, int speed, boolean rotated, boolean zoom)
	{
		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
		this.x = startPosition.x;
		this.y = startPosition.y;
		this.stopX = endPosition.x;
		this.stopY = endPosition.y;
		this.speed = speed;
		this.rotated = rotated;
		this.zoom = zoom;
		this.img = img;
	}

	public boolean start()
	{
		while(true)
		{
			this.repaint();
			try
			{
				Thread.sleep(this.speed);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			if(xIsEnd && yIsEnd)
			{
				imagesCntIndex++;
				this.freezeImage(imagesCntIndex);
				return true;
			}

		}
	}

	public void freezeImage(int num)
	{
		switch(num)
		{
			case 1 :
				firstImageFreeze = true;
				firstImage = img;
				firstStopX = this.stopX - imgWidth / 2;
				firstStopY = this.stopY - imgHeight / 2;
				firstImageWidth = imgWidth;
				firstImageHeight = imgHeight;
				break;
			case 2 :
				secondImageFreeze = true;
				secondImage = img;
				secondStopX = this.stopX - imgWidth / 2;
				secondStopY = this.stopY - imgHeight / 2;
				secondImageWidth = imgWidth;
				secondImageHeight = imgHeight;
				break;
			case 3 :
				thirdImageFreeze = true;
				thirdImage = img;
				thirdStopX = this.stopX - imgWidth / 2;
				thirdStopY = this.stopY - imgHeight / 2;
				thirdImageWidth = imgWidth;
				thirdImageHeight = imgHeight;
				break;
			case 4 :
				fourthImageFreeze = true;
				fourthImage = img;
				fourthStopX = this.stopX - imgWidth / 2;
				fourthStopY = this.stopY - imgHeight / 2;
				fourthImageWidth = imgWidth;
				fourthImageHeight = imgHeight;
				break;
		}

	}

	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		super.paint(g);
		if(firstImageFreeze)
			g2d.drawImage(firstImage, firstStopX, firstStopY, firstImageWidth, firstImageHeight, this);
		if(secondImageFreeze)
			g2d.drawImage(secondImage, secondStopX, secondStopY, secondImageWidth, secondImageHeight, this);
		if(thirdImageFreeze)
			g2d.drawImage(thirdImage, thirdStopX, thirdStopY, thirdImageWidth, thirdImageHeight, this);
		if(fourthImageFreeze)
			g2d.drawImage(fourthImage, fourthStopX, fourthStopY, fourthImageWidth, fourthImageHeight, this);
		
		if(zoom)
		{
			if(x < stopX)
				x++;
			else if(x > stopX)
				x--;
			if(y < stopY)
				y++;
			else if( y > stopY)
				y--;
			if(zoomWidth < imgWidth)
				zoomWidth++;
			if(zoomHeight < imgHeight)
				zoomHeight++;
			if(x >= stopX && zoomWidth > imgWidth)
				xIsEnd = true;
			if(y >= stopY && zoomHeight > imgHeight)
				yIsEnd = true;
			g2d.drawImage(img, x - imgWidth / 2, y - imgHeight / 2, zoomHeight, zoomHeight, this);
			return;
		}
		if(x < stopX)
			x++;
		else if(x > stopX)
			x--;
		else
			xIsEnd = true;
		if(y < stopY)
			y++;
		else if( y > stopY)
			y--;
		else
			yIsEnd = true;
		if(rotated)
		{
			degree += 10;
		}
		else
			degree = 0;
		g2d.rotate(degree * Math.PI / 180, x, y);
		g2d.drawImage(img, x - imgWidth / 2, y - imgHeight / 2, imgWidth, imgHeight, this);
		g2d.rotate(-degree * Math.PI / 180, x, y);
		//g2d.drawRoundRect(x - imgWidth / 2, y - imgHeight / 2, imgWidth, imgHeight, 0, 0);

	}

	@Override
	public void update(Graphics g)
	{
		if(bufferImage == null)
		{
			bufferImage = this.createImage(this.getWidth(), this.getHeight());
		}
		Graphics gBuffer = bufferImage.getGraphics();// 获取内存画板的画�?
		if(gBuffer != null)
		{
			paint(gBuffer);
		}
		else
		{
			paint(g);
		}
		gBuffer.dispose();
		g.drawImage(bufferImage, 0, 0, null);// 将内�? �?次�?�写到屏幕上
	}

	public static void main(String args[])
	{

		Animation a = new Animation(Toolkit.getDefaultToolkit().getImage("images/startAnimationLogo.png"), 
				50, 50, new Point(190, 70), new Point(190,170), 30, false, false);
		Animation b = new Animation(Toolkit.getDefaultToolkit().getImage("images/startAnimationCenterTop.png"), 
				125, 25, new Point(290, 200), new Point(290, 155), 30, false, false);
		Animation c = new Animation(Toolkit.getDefaultToolkit().getImage("images/startAnimationCenterBottom.png"), 
				125, 25, new Point(350, 180),new Point(290, 180), 30, false, false);
		Animation d = new Animation(Toolkit.getDefaultToolkit().getImage("images/startAnimationRight.png"), 280, 35, 
				new Point(180, 220), new Point(260, 220), 30, false, false);
		JFrame jf = new JFrame();
		jf.setUndecorated(true);
		jf.setSize(550, 400);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setTitle("test");
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);

		a.setBackground(Color.WHITE);
		a.setBorder(new LineBorder(Color.BLACK));
		b.setBackground(Color.WHITE);
		b.setBorder(new LineBorder(Color.BLACK));
		c.setBackground(Color.WHITE);
		c.setBorder(new LineBorder(Color.BLACK));
		d.setBackground(Color.WHITE);
		d.setBorder(new LineBorder(Color.BLACK));
		
		jf.add(a);
		jf.setVisible(true);
		a.start();
		jf.add(b);
		jf.setVisible(true);
		b.start();
		jf.add(c);
		jf.setVisible(true);
		c.start();
		jf.add(d);
		jf.setVisible(true);
		d.start();
		try
		{
			Thread.sleep(5000);
			jf.dispose();
			d.setBackground(Color.WHITE);
		}
		catch(Exception e)
		{
		}

		// a.repaint();
	}

}