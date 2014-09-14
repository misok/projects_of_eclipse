package com.xiaoma.tankgame.version1;

import java.io.*;
import java.util.Vector;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.*;
//坦克类
class Tank
{
	//坦克的横纵坐标
	int x=0;
	int y=0;
	int direct=0;//坦克的方向 0表示上，1表示右，2下，3左
	//坦克的速度
	int speed=3;
	//坦克的颜色
	int color;
	boolean isLive=true;
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getDirect() {
		return direct;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}
	public Tank(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	
}

//播放声音的类
class AePlayWave extends Thread
{
	private String filename;
	public AePlayWave(String wavefile)
	{
		filename=wavefile;
		
	}
	public void run()
	{
		File soundFile=new File(filename);
		AudioInputStream ais=null;
		try {
			ais=AudioSystem.getAudioInputStream(soundFile);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return;
		}
		AudioFormat format=ais.getFormat();
		SourceDataLine auline=null;
		DataLine.Info info=new DataLine.Info(SourceDataLine.class,format);
		try {
			auline=(SourceDataLine)AudioSystem.getLine(info);
			auline.open(format);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return;
		}
		auline.start();
		int nBytesRead=0;
		byte []abData=new byte[1024];
		try {
			while(nBytesRead!=-1)
			{
				nBytesRead=ais.read(abData, 0, abData.length);
				if(nBytesRead>=0)
				{
					auline.write(abData, 0, nBytesRead);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return;
		}finally{
			auline.drain();
			auline.close();
		}
	}
}
//我的坦克
class Hero extends Tank
{
	//子弹变量
	
	Vector<Shot> ss=new Vector<Shot>();
	Shot s=null;
	public Hero(int x,int y)
	{
		super(x,y);
		
	}
	
	//开火
	public void ShotEnemy()
	{
		
		switch(this.direct)
		{
		case 0:
			s=new Shot(x+9,y,0);//子弹向上
			ss.add(s);//添加到子弹向量中
			break;
		case 1:
			s=new Shot(x+9,y+30,1);//子弹向下
			ss.add(s);
			break;
		case 2:
			s=new Shot(x,y+9,2);//向左
			ss.add(s);
			break;
		case 3:
			s=new Shot(x+30,y+9,3);//向右
			ss.add(s);
			break;
		
		}
		//启动子弹线程
		Thread t=new Thread(s);
		t.start();
		
	}
	//坦克向上移动
	public void moveUp()
	{
		y-=speed;
	}
	public void moveRight()
	{
		x+=speed;
	}
	public void moveDown()
	{
		y+=speed;
	}
	public void moveLeft()
	{
		x-=speed;
	}
}

//敌人的坦克,把敌人做成线程类
class EnemyTank extends Tank implements Runnable
{
	//定义一个向量，可以访问到MyPanel上所有敌人的坦克
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	//int times=0;//控制坦克连发时间
	//定义一个向量，可以存放敌人的子弹
	Vector<Shot> ss=new Vector<Shot>();
	//敌人添加子弹应该在创建坦克和敌人的坦克子弹死亡过后
	

	public EnemyTank(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				// TODO: handle exception
			}
			switch(this.direct)
			{
			case 0:
				//方向向上
				for(int i=0;i<30;i++)
				{
					if(y>0&&!this.isTouch())
					{
						y-=speed;
					}
					
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				
				break;
			case 1:
				for(int i=0;i<30;i++)
				{
					if(y<270&&!this.isTouch())
					{
						y+=speed;
					}
					
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				
				break;
			case 2:
				for(int i=0;i<30;i++)
				{
					if(x>0&&!this.isTouch())
					{
						x-=speed;
					}
					
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				
				break;
			case 3:
				for(int i=0;i<30;i++)
				{
					if(x<370&&!this.isTouch())//保证坦克不出边界  这边有像素的问题
					{
						x+=speed;
					}
					
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				
				break;
			}
			
			//让坦克随机产生新的方向
			this.direct=(int)(Math.random()*4);
			//判断敌人的坦克是否死亡
			if(this.isLive==false)
			{
				//让坦克死亡后退出线程
				break;
			}
			
		}
	}
	//得到Mypanel的敌人坦克向量
	public void setEts(Vector<EnemyTank> vv)
	{
		this.ets=vv;
	}
	//判断是否碰到了别的敌人坦克，即重叠运行
	public boolean isTouch()
	{
		boolean b=false;
		switch(this.direct)
		{
		case 0:
			//敌人的这个坦克向上
			//取出所有的敌人坦克
			for(int i=0;i<ets.size();i++)
			{
				EnemyTank et=ets.get(i);
				//看看是不是自己
				if(et!=this)
				{
					//如果要判断的敌人坦克方向是向上或者向下
					if(et.direct==0||et.direct==1)
					{
						if(this.x>=et.x&&this.x<=et.x+20&&this.y>=et.y &&this.y<=et.y+30)
						{
							return true;
						}
						if(this.x+20>=et.x&&this.x+20<=et.x+20&&this.y>=et.y &&this.y<=et.y+30)
						{
							return true;
						}
					}
					//如果要判断的敌人坦克方向是向左或者向右
					if(et.direct==2||et.direct==3)
					{
						if(this.x>=et.x&&this.x<=et.x+30&&this.y>=et.y &&this.y<=et.y+20)
						{
							return true;
						}
						if(this.x+20>=et.x&&this.x+20<=et.x+30&&this.y>=et.y &&this.y<=et.y+20)
						{
							return true;
						}
					}
				}
			}
			break;
		case 1:
			//敌人的这个坦克向下
			//取出所有的敌人坦克
			for(int i=0;i<ets.size();i++)
			{
				EnemyTank et=ets.get(i);
				//看看是不是自己
				if(et!=this)
				{
					//如果要判断的敌人坦克方向是向上或者向下
					if(et.direct==0||et.direct==1)
					{
						//我的左点
						if(this.x>=et.x&&this.x<=et.x+20&&this.y+30>=et.y &&this.y+30<=et.y+30)
						{
							return true;
						}
						//我的右点
						if(this.x+20>=et.x&&this.x+20<=et.x+20&&this.y+30>=et.y &&this.y+30<=et.y+30)
						{
							return true;
						}
					}
					//如果要判断的敌人坦克方向是向左或者向右
					if(et.direct==2||et.direct==3)
					{
						if(this.x>=et.x&&this.x<=et.x+30&&this.y+30>=et.y &&this.y+30<=et.y+20)
						{
							return true;
						}
						if(this.x+20>=et.x&&this.x+20<=et.x+30&&this.y+30>=et.y &&this.y+30<=et.y+20)
						{
							return true;
						}
					}
				}
			}
			
			break;
		case 2:
			//敌人的这个坦克向左
			//取出所有的敌人坦克
			for(int i=0;i<ets.size();i++)
			{
				EnemyTank et=ets.get(i);
				//看看是不是自己
				if(et!=this)
				{
					//如果要判断的敌人坦克方向是向上或者向下
					if(et.direct==0||et.direct==1)
					{
						if(this.x>=et.x&&this.x<=et.x+20&&this.y>=et.y &&this.y<=et.y+30)
						{
							return true;
						}
						if(this.x>=et.x&&this.x<=et.x+20&&this.y+20>=et.y &&this.y+20<=et.y+30)
						{
							return true;
						}
					}
					//如果要判断的敌人坦克方向是向左或者向右
					if(et.direct==2||et.direct==3)
					{
						if(this.x>=et.x&&this.x<=et.x+30&&this.y>=et.y &&this.y<=et.y+20)
						{
							return true;
						}
						if(this.x>=et.x&&this.x<=et.x+30&&this.y+20>=et.y &&this.y+20<=et.y+20)
						{
							return true;
						}
					}
				}
			}
			
			break;
		case 3:
			//敌人的这个坦克向右
			//取出所有的敌人坦克
			for(int i=0;i<ets.size();i++)
			{
				EnemyTank et=ets.get(i);
				//看看是不是自己
				if(et!=this)
				{
					//如果要判断的敌人坦克方向是向上或者向下
					if(et.direct==0||et.direct==1)
					{
						if(this.x+30>=et.x&&this.x+30<=et.x+20&&this.y>=et.y &&this.y<=et.y+30)
						{
							return true;
						}
						if(this.x+30>=et.x&&this.x+30<=et.x+20&&this.y+20>=et.y &&this.y+20<=et.y+30)
						{
							return true;
						}
					}
					//如果要判断的敌人坦克方向是向左或者向右
					if(et.direct==2||et.direct==3)
					{
						if(this.x+30>=et.x&&this.x+30<=et.x+30&&this.y>=et.y &&this.y<=et.y+20)
						{
							return true;
						}
						if(this.x+30>=et.x&&this.x+30<=et.x+30&&this.y+20>=et.y &&this.y+20<=et.y+20)
						{
							return true;
						}
					}
				}
			}
			break;
			
		}
		return b;
		
	}
}
//子弹类

class Shot implements Runnable
{
	int x;
	int y;
	int direct;
	int speed=5;
	boolean isLive=true;
	public Shot(int x,int y,int direct)
	{
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			try {
				Thread.sleep(50);//刚出来让子弹休息一下
			} catch (Exception e) {
				// TODO: handle exception
			}
			switch(direct)
			{
			case 0:
				y-=speed;
				break;
			case 1:
				y+=speed;
				break;
			case 2:
				x-=speed;
				break;
			case 3:
				x+=speed;
				break;
				
			}
			//子弹何时死亡的问题？
			//判断改子弹是否碰到边缘
			if(x<0||x>400||y<0||y>300)
			{
				this.isLive=false;
				break;
			}
		}
	}
}
//记录点
class Node
{
	int   x;
	int   y;
	int   direct;
	public Node(int x,int y,int direct)
	{
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
}
//记录类，记录各种战绩和有些设置
class Recorder
{
	//记录每一关有多少敌人
	private static int enNum=20;
	//设置我有多少可用坦克
	private static int myLife=3;
	//记录总共消灭了多少敌人
	private static int allEnNum=0;
	private static FileWriter fw=null;
	private static BufferedWriter bw=null;
	private static FileReader fr=null;
	private static BufferedReader br=null;
	private static Vector<EnemyTank> ets=new Vector<EnemyTank>();
    static  Vector<Node> nodes=new Vector<Node>();
	
	//完成读取任务
	public Vector<Node>  getNodesAndEnNums()
	{
		try {
			fr=new FileReader("e:\\myTankRecorder.txt");
			br=new BufferedReader(fr);
			String n="";
			//先读取第一行
			n=br.readLine();
			allEnNum=Integer.parseInt(n);
			while((n=br.readLine())!=null)
			{
				String []xyz=n.split(" ");//按照空格返回数组
				Node node=new Node(Integer.parseInt(xyz[0]),Integer.parseInt(xyz[1]),Integer.parseInt(xyz[2]));
				nodes.add(node);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				br.close();
				fr.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return nodes;
	}

	//保存击毁敌人的数量和敌人坦克坐标,fangxiang
	public static void keepRecorderandEnemy()
	{
		try {
			fw=new FileWriter("e:\\myTankRecorder.txt");
			bw=new BufferedWriter(fw);
			bw.write(allEnNum+"\r\n");
			
			//保存当前还活着的坦克坐标和方向
			for(int i=0;i<ets.size();i++)
			{
				//取出坦克
				EnemyTank et=ets.get(i);
				if(et.isLive)
				{
					//活的就保存
					String tank=et.x+" "+et.y+" "+et.direct;
					//写入
					bw.write(tank+"\r\n");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//后开的先关闭
				bw.close();
				fw.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	
	//把玩家击毁敌人坦克的数量保存到文件
	public static void keepRecorder()

	{
		try {
			fw=new FileWriter("e:\\myTankRecorder.txt");
			bw=new BufferedWriter(fw);
			bw.write(allEnNum+"\r\n");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//后开的先关闭
				bw.close();
				fw.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	//从文件中读取记录
	public static void getRecorder()
	{
		try {
			fr=new FileReader("e:\\myTankRecorder.txt");
			br=new BufferedReader(fr);
			String n=br.readLine();
			allEnNum=Integer.parseInt(n);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				br.close();
				fr.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	public static int getAllEnNum() {
		return allEnNum;
	}
	public static void setAllEnNum(int allEnNum) {
		Recorder.allEnNum = allEnNum;
	}
	public static int getEnNum() {
		return enNum;
	}
	public static void setEnNum(int enNum) {
		Recorder.enNum = enNum;
	}
	public static int getMyLife() {
		return myLife;
	}
	public static void setMyLife(int myLife) {
		Recorder.myLife = myLife;
	}
	
	public static Vector<EnemyTank> getEts() {
		return ets;
	}

	public static void setEts(Vector<EnemyTank> ets) {
		Recorder.ets = ets;
	}
	//显示减掉敌人坦克数量
	public  static void reduceEnNum()
	{
		enNum--;
	}
	//消灭敌人
	public static void addEnNum()
	{
		allEnNum++;
	}
}
//炸弹类
class Boom
{
	int x;
	int y;
	int life=9;//炸弹的生存时间
	boolean isLive=true;
	public Boom(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	//减少生命值
	public void LifeDown()
	{
		if(life>0)
		{
			life--;
		}else{
			this.isLive=false;
			
		}
	}
}