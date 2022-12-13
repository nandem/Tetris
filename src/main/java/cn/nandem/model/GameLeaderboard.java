/**
 * 
 */
package cn.nandem.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * 这里采用文件读写方式操作排行榜数据。单例模式。
 * 
 * @author Nandem
 *
 */
public class GameLeaderboard
{
	private String names[];
	private int scores[];
	private String filePath;

	public void judgeCanBeRecorded(int score)
	{
		this.rank();
		if(score > scores[2])
		{
			scores[2] = score;
			JOptionPane jp = new JOptionPane();
			//jp.setMessage("");
			//jp.add(new JLabel("大侠留名！"));
			names[2] = jp.showInputDialog(null, "大侠留名！");
			this.saveLeaderboard();
		}
	}

	public String[] getTopThree()
	{
		this.rank();
		for(int i = 0; i < scores.length; i++)
		{
			//System.out.println();
			topThrees[i] = names[i] + ":" + scores[i];
		}
		return topThrees;
	}
	
	public void rank()
	{
		this.readLeaderboard();
		scores[0] = Integer.valueOf(topThrees[0].split(":")[1]);
		names[0] = topThrees[0].split(":")[0];
		scores[1] = Integer.parseInt((topThrees[1].split(":")[1]));
		names[1] = topThrees[1].split(":")[0];
		scores[2] = Integer.valueOf(topThrees[2].split(":")[1]);
		names[2] = topThrees[2].split(":")[0];
		int tempInt;
		String tempStr;
		for(int i = 0; i < scores.length; i++)
		{
			for(int j = 0; j < scores.length - i - 1; ++j)
			{
				if(scores[j] < scores[j + 1])
				{
					tempInt = scores[j];
					tempStr = names[j];
					scores[j] = scores[j + 1];
					names[j] = names[j + 1];
					scores[j + 1] = tempInt;
					names[j + 1] = tempStr;
				}
			}
		}
	}
	
	public void saveLeaderboard()
	{
		try
		{
			File file = new File(filePath);
			if(!file.exists())
				file.createNewFile();
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			//bw.write(name + ":" + score);
			for(int i = 0; i < scores.length; i++)
			{
				//System.out.println(names[i] + ":" + scores[i] + "***");
				//topThrees[i] = names[i] + ":" + scores[i];
				bw.write(names[i] + ":" + scores[i] + "\r");
			}
			bw.close();
		}
		catch(IOException ex)
		{
			System.out.println(ex.getStackTrace());
		}
	}

	/**
	 * 读取前三名用户数据，包括一个结尾判定符
	 * 
	 * @return 返回长度为4的字符串数组，保存用户得分数据，格式为“姓名 ：得分”
	 */
	private void readLeaderboard()
	{
		StringBuffer sb = new StringBuffer();
		topThrees = new String[4];
		try
		{
			File file = new File(filePath);
			if(!file.exists())
				throw new FileNotFoundException();
			int t = 0;
			BufferedReader br = new BufferedReader(new FileReader(file));
			while((topThrees[t++] = br.readLine()) != null);
			for(int i = 0; i < topThrees.length; i++)
			{
				System.out.println(topThrees[i]);
				
			}
		}
		catch(IOException ex)
		{
			System.out.println(ex.getStackTrace());
		}
	}

	private String getProjectPath()
	{
		try
		{
			return new File("").getCanonicalPath();
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/* ^_^*-------------------------------------------------------*^_^ */
	private volatile static GameLeaderboard instance = null;
	private String topThrees[];

	private GameLeaderboard()
	{
		this.names = new String[3];
		this.scores = new int[3];
		filePath = this.getProjectPath() + "/record/record.txt";
	}

	public static GameLeaderboard getInstance()
	{
		if(instance == null)
		{
			synchronized(GameLeaderboard.class)
			{
				if(null == instance)
				{
					instance = new GameLeaderboard();
				}
			}
		}
		return instance;
	}
	/* ^_^*-------------------------------------------------------*^_^ */

	public String[] getNames()
	{
		return names;
	}

	public void setNames(String[] names)
	{
		this.names = names;
	}

	public int[] getSocres()
	{
		return scores;
	}

	public void setSocres(int[] socres)
	{
		this.scores = socres;
	}
}
