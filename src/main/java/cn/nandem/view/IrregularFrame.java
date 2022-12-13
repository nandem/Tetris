/**
 * 
 */
package cn.nandem.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Area;
import java.awt.image.PixelGrabber;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.sun.awt.AWTUtilities;

/**
 * @author Nandem
 *
 */
public class IrregularFrame extends JFrame
{
	private Point origin; // �����ƶ�����
	private Image backgroundImage; // �����趨���岻������ʽ��ͼƬ
	
	public IrregularFrame()
	{
		MediaTracker mt = new MediaTracker(this);
		backgroundImage = Toolkit.getDefaultToolkit().createImage("images/interface.png");
		mt.addImage(backgroundImage, 0);
		try
		{
			mt.waitForAll();
		}
		catch(InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// �趨�����С��ͼƬһ����
		this.setSize(backgroundImage.getWidth(null), backgroundImage.getHeight(null));
		// �趨���ô���װ�Σ�������ȡ����Ĭ�ϵĴ���ṹ
		this.setUndecorated(true);
		// ��ʼ�������ƶ������ԭ��
		this.origin = new Point();
		
		//AWTUtilities.setWindowOpaque(this, false);

		// ����AWTUtilities��setWindowShape�����趨������Ϊ�ƶ���Shape��״
		AWTUtilities.setWindowShape(this, getImageShape(backgroundImage));
		// �趨����ɼ���
		//AWTUtilities.setWindowOpacity(this, 1.0f);
		this.addMouseListener(new MouseMoveHandler());
		this.addMouseMotionListener(new MouseDragHandler());
	}
	
	private class MouseDragHandler extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent e)
		{
			Point p = getLocation();
			setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
		}
	}
	
	private class MouseMoveHandler extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			origin.x = e.getX();
			origin.y = e.getY();
		}

		// �����ϵ�������Ҽ��رճ���
		public void mouseClicked(MouseEvent e)
		{
			if(e.getButton() == MouseEvent.BUTTON3)
				System.exit(0);
		}

		public void mouseReleased(MouseEvent e)
		{
			super.mouseReleased(e);
		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
			//repaint();
		}
	}
	
	/**
	 * ��Imageͼ��ת��ΪShapeͼ��
	 * 
	 * @param img
	 * @param isFiltrate
	 * @return Imageͼ���Shapeͼ�α�ʾ
	 * @author Hexen
	 */
	public Shape getImageShape(Image img)
	{
		ArrayList<Integer> x = new ArrayList<Integer>();
		ArrayList<Integer> y = new ArrayList<Integer>();
		int width = img.getWidth(null);// ͼ����
		int height = img.getHeight(null);// ͼ��߶�

		// ɸѡ����
		// ���Ȼ�ȡͼ�����е�������Ϣ
		PixelGrabber pgr = new PixelGrabber(img, 0, 0, -1, -1, true);
		try
		{
			pgr.grabPixels();
		}
		catch(InterruptedException ex)
		{
			ex.getStackTrace();
		}
		int pixels[] = (int[]) pgr.getPixels();

		// ѭ������
		for(int i = 0; i < pixels.length; i++)
		{
			// ɸѡ������͸�������ص�������뵽����ArrayList x��y��
			int alpha = getAlpha(pixels[i]);
			if(alpha == 0)
			{
				continue;
			}
			else
			{
				x.add(i % width > 0 ? i % width - 1 : 0);
				y.add(i % width == 0 ? (i == 0 ? 0 : i / width - 1) : i / width);
			}
		}

		// ����ͼ����󲢳�ʼ��(0Ϊ͸��,1Ϊ��͸��)
		int[][] matrix = new int[height][width];
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				matrix[i][j] = 0;
			}
		}

		// ��������ArrayList�еĲ�͸��������Ϣ
		for(int c = 0; c < x.size(); c++)
		{
			matrix[y.get(c)][x.get(c)] = 1;
		}

		/*
		 * ��һˮƽ"ɨ��"ͼ������ÿһ�У�����͸������������ΪRectangle���ٽ�ÿһ�е�Rectangleͨ��Area���rec������кϲ���
		 * ����γ�һ��������Shapeͼ��
		 */
		Area rec = new Area();
		int temp = 0;

		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				if(matrix[i][j] == 1)
				{
					if(temp == 0)
						temp = j;
					else if(j == width)
					{
						if(temp == 0)
						{
							Rectangle rectemp = new Rectangle(j, i, 1, 1);
							rec.add(new Area(rectemp));
						}
						else
						{
							Rectangle rectemp = new Rectangle(temp, i, j - temp, 1);
							rec.add(new Area(rectemp));
							temp = 0;
						}
					}
				}
				else
				{
					if(temp != 0)
					{
						Rectangle rectemp = new Rectangle(temp, i, j - temp, 1);
						rec.add(new Area(rectemp));
						temp = 0;
					}
				}
			}
			temp = 0;
		}
		return rec;
	}

	/**
	 * ��ȡ���ص�Alphaֵ
	 * 
	 * @param pixel
	 * @return ���ص�Alphaֵ
	 */
	private int getAlpha(int pixel)
	{
		return (pixel >> 24) & 0xff;
	}

	@Override
	public void paint(Graphics g)
	{
		// super.paint(g); //���д��лᵼ�������봰��ʱ������˸
		g.drawImage(backgroundImage, 0, 0, null);
	}
}
