package com.xiaoma.tankgame.version1;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.sound.sampled.*;
/**
	 * @param args
	 * 坦克大战1.0版本  可以移动
	 * 子弹连发问题,最多连发五颗
	 * 击中敌人坦克
	 * 爆炸效果：先准备三张图片，爆炸效果，
	 * 敌人坦克击中我时我也爆炸
	 * 8/13控制敌人的坦克不重叠运行  判断是否碰撞的函数写到EnemyTank类
	 * 8/13:记录击中敌人的坦克数和存盘退出功能
	 *可以暂停游戏  (按空格键暂停。当用户点击暂停时，子弹的速度和坦克的速度设为0.坦克的方向不要发生变化
	 *可以选关（1.做一个panel,用来提示2.字体做闪烁效果）
	 *  记录玩家的成绩（用文件流， 写一个记录类，完成对战绩的记录）
	 *java如何操作声音文件
	 * 14/8/3、4
	 */
public class TankGameV1 extends JFrame implements ActionListener{
	Mypanel mp=null;
	//定义提示面板
	MyStartPanel msp=null;
	
	//做出我的菜单
	JMenuBar jmb=null;
	JMenu jm1=null;
	JMenuItem jmi1=null;//开始游戏
	JMenuItem jmi2=null;//退出游戏
	JMenuItem jmi3=null;//存盘退出
	JMenuItem jmi4=null;//续上局
	public TankGameV1()
	{
		
		
		//创建菜单和菜单选项
		jmb=new JMenuBar();
		jm1=new JMenu("游戏");
		//设置快捷键
		jm1.setMnemonic('G');
		jmi1=new JMenuItem("开始新游戏(N)");
		jmi2=new JMenuItem("退出新游戏(E)");
		jmi3=new JMenuItem("存盘退出游戏(C)");
		jmi4=new JMenuItem("续上局(G)");
		//对jmi1响应
		jmi1.addActionListener(this);
	    jmi2.addActionListener(this);
	    jmi3.addActionListener(this);
	    jmi4.addActionListener(this);
	    
	    jmi1.setActionCommand("newgame");
	    jmi2.setActionCommand("exit");
	    jmi3.setActionCommand("saveexit");
	    jmi4.setActionCommand("continue");
		
		jm1.add(jmi1);
		jm1.add(jmi2);
		jm1.add(jmi3);
		jm1.add(jmi4);
		jmb.add(jm1);
		
		msp=new MyStartPanel();
		Thread t=new Thread(msp);
		t.start();
		this.setJMenuBar(jmb);
		this.add(msp);
		this.setSize(600,500);
		//mp.setBackground(Color.yellow);
		this.setVisible(true);
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    TankGameV1 tg1=new TankGameV1();

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//对用户不同的点击做出不同的处理
		if(e.getActionCommand().equals("newgame"))
		{
			//创建游戏面板
			mp=new Mypanel("newgame");
			//启动PANEL线程
			Thread t=new Thread(mp);
			t.start();
			this.remove(msp);//要先删除旧的paNel
			this.add(mp);
			//注册监听
			this.addKeyListener(mp);
			//显示，刷新
			this.setVisible(true);
			//this.setSize(400,300);
		}else if(e.getActionCommand().equals("exit"))
		{
			//保存击毁敌人数量信息
			Recorder.keepRecorder();
			System.exit(0);
		}else if(e.getActionCommand().equals("saveexit"))
		{
			Recorder.setEts(mp.ets);
			//对存盘退出做处理 1.保存击毁敌人的数量和敌人的坐标
			Recorder.keepRecorderandEnemy();
			//退出
			System.exit(0);
		}else if(e.getActionCommand().equals("continue"))
		{
			//续上局
			mp=new Mypanel("con");
			
			
			//启动PANEL线程
			Thread t=new Thread(mp);
			t.start();
			this.remove(msp);//要先删除旧的paNel
			this.add(mp);
			//注册监听
			this.addKeyListener(mp);
			//显示，刷新
			this.setVisible(true);
		}
	}

}
//提示分关面板
class  MyStartPanel extends JPanel implements Runnable
{
	int times=0;
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		if(times%2==0)//控制闪烁效果
		{
			g.setColor(Color.yellow);
			//开关信息的字体
			Font myFont =new Font("华文新魏",Font.BOLD,30);
			g.setFont(myFont);
			g.drawString("stage：1", 150, 150);
		}
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			times++;
			
			this.repaint();
		}
	}
}

//我的面板panel
class Mypanel extends JPanel implements KeyListener,Runnable
{
	//定义一个我的坦克
	Hero hero=null;
	
	
	//定义敌人的坦克组
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	Vector<Node> nodes=new Vector<Node>();
	int enSize=5;
	
	//定义炸弹向量
	Vector<Boom> booms=new Vector<Boom>();
	
	//定义三张图片
	Image image1=null;
	Image image2=null;
	Image image3=null;
	//播放开始声音
	
	
	
	
	public Mypanel(String flag)
	{
		AePlayWave apw=new AePlayWave("e:\\开始.wav");
	    apw.start();
		//回复记录
		Recorder.getRecorder();
		hero=new Hero(200, 200);
		
		if(flag.equals("newgame"))
		{
			for(int i=0;i<enSize;i++)
			{
			//创建一辆敌人的坦克对象
			EnemyTank et=new EnemyTank((i+1)*50, 5);//这句话有问题
			et.setColor(1);//敌人的坦克颜色为1，我的为0
			et.setDirect(2);
			//将MyPanel的敌人坦克向量交给该敌人坦克，判断重叠用
			et.setEts(ets);
			//启动敌人的坦克
			Thread t=new Thread(et);
			t.start();
			//给敌人的坦克加入子弹
			Shot s=new Shot(et.x+9, et.y+30,2 );//跟敌人坦克初始方向有关
			//加入给敌人
			et.ss.add(s);
			Thread t2=new Thread(s);
			t2.start();
			ets.add(et);
			}
		}else
		{
			nodes=new Recorder().getNodesAndEnNums();
			for(int i=0;i<nodes.size();i++)
			{
				Node node=nodes.get(i);
			//创建一辆敌人的坦克对象
			EnemyTank et=new EnemyTank(node.x,node.y);//这句话有问题
			et.setColor(1);//敌人的坦克颜色为1，我的为0
			et.setDirect(node.direct);
			//将MyPanel的敌人坦克向量交给该敌人坦克，判断重叠用
			et.setEts(ets);
			//启动敌人的坦克
			Thread t=new Thread(et);
			t.start();
			//给敌人的坦克加入子弹
			Shot s=new Shot(et.x+9, et.y+30,2 );//跟敌人坦克初始方向有关
			//加入给敌人
			et.ss.add(s);
			Thread t2=new Thread(s);
			t2.start();
			ets.add(et);
			}
		}
		
		//初始化图片,三张图片的切换组成一颗炸弹
		image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/boom1.png"));
		image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/boom2.png"));
		image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/boom3.png"));
//		try {
//			image1=ImageIO.read(new File("boom1.png"));
//			image2=ImageIO.read(new File("boom2.png"));
//			image3=ImageIO.read(new File("boom3.png"));//解决了第一个图片没爆炸效果的bug
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
	}
	//画出提示信息
	public void showInfo(Graphics g)
	{
		//画出提示信息坦克(该坦克不参与战斗）
		this.DrawTank(80, 330, g, 0, 1);
		g.setColor(Color.black);
		g.drawString(Recorder.getEnNum()+"", 110, 350);
		this.DrawTank(140,330, g, 0, 0);
		g.setColor(Color.black);
		g.drawString(Recorder.getMyLife()+"", 170, 350);
		
		//画出玩家的总成绩
		g.setColor(Color.black);
		Font f=new Font("宋体", Font.BOLD, 20);
		g.setFont(f);
		g.drawString("您的总成绩：", 420, 30);
		this.DrawTank(420, 60, g, 0, 0);
		g.setColor(Color.black);
		g.drawString(Recorder.getAllEnNum()+"", 460, 80);
	}
	//重写paint方法
	public void paint(Graphics g)//子弹要动起来要不停的绘制，所以MYpanel应该做成一个线程
	{
		super.paint(g);
		g.fillRect(0, 0, 400, 300);//让背景变成黑色
		
		this.showInfo(g);
		
		//画出自己的坦克
		if(hero.isLive)
		{
			this.DrawTank(hero.getX(), hero.getY(), g, hero.direct, 0);
		}
		
		
		//画出敌人的坦克
		for(int i=0;i<ets.size();i++)
		{
			EnemyTank et=ets.get(i);
			if(et.isLive)
			{
				this.DrawTank(et.getX(), et.getY(), g, et.getDirect(), 1);
				//再画出敌人的子弹
				for(int j=0;j<et.ss.size();j++)
				{
					Shot enemyShot=et.ss.get(j);
					if(enemyShot.isLive)
					{
						g.draw3DRect(enemyShot.x, enemyShot.y, 1, 1, false);
					}else{
						//如果敌人的坦克死亡就从Vecitor去掉
						et.ss.remove(enemyShot);
					}
				}
			}
			
		}
		//画出炸弹类，即爆炸效果
		for(int i=0;i<booms.size();i++)
		{
			Boom b=booms.get(i);
			if(b.life>6)
			{
				g.drawImage(image1, b.x, b.y, 30, 30, this);
				
			}else if(b.life>3)
			{
				g.drawImage(image3, b.x, b.y, 30, 30, this);
			}
			else{
				g.drawImage(image2, b.x, b.y, 30, 30, this);
			}
			b.LifeDown();
			if(b.life==0)
			{
				booms.remove(b);
			}
		}
		//画出一颗子弹
		//画出多颗子弹加循环,从ss中取出每颗子弹，并绘制
		for(int i=0;i<hero.ss.size();i++)
		{
			Shot myShot=hero.ss.get(i);
			if(myShot!=null&&hero.s.isLive==true)
			{
				g.setColor(Color.red);
				g.draw3DRect(myShot.x, myShot.y, 1, 1, false);
				
			}
			if(myShot.isLive==false)
			{
				hero.ss.remove(myShot);//想想为什么不用i
			}
		}
		
		
		
	}
	//判断我的子弹是否击中敌人的坦克
	public void hitEnemyTank()
	{
		//判断是否击中敌人的坦克
		for(int i=0;i<hero.ss.size();i++)
		{
			Shot myShot=hero.ss.get(i);//取出子弹
			if(myShot.isLive==true)//判断子弹是否有效
			{
				for(int j=0;j<ets.size();j++)
				{
					EnemyTank et=ets.get(j);
					if(et.isLive)
					{
						if(this.hitTank(myShot, et))
						{
							Recorder.reduceEnNum();
							Recorder.addEnNum();//增加我的记录
						}
					}
				}
			}
			
		}
	}
	//判断敌人的子弹是否击中我的坦克
	public void hitMe()
	{
		//取出每一个敌人的子弹与我的坦克匹配
		for(int i=0;i<this.ets.size();i++)
		{
			//取出坦克
			EnemyTank et=ets.get(i);
			//取出每一刻子弹
			for(int j=0;j<et.ss.size();j++)
			{
				Shot enemyShot=et.ss.get(j);
				if(hero.isLive)
				{
					if(this.hitTank(enemyShot, hero))
					{
						
						
					}
				}
				
			}
			
		}
	}
	//击中行为函数，首先写击中敌人的坦克
	public boolean hitTank(Shot s,Tank et)
	{
		boolean bl=false;//默认没有击中
			//需要判断方向
			switch(et.direct)
			{
			//敌人坦克方向是上、下
			case 0:
			case 1:
				if (s.x>et.x&&s.x<et.x+20&&s.y>et.y&&s.y<et.y+30)
				{
					//击中后，子弹死亡，敌人坦克死亡
					s.isLive=false;
					et.isLive=false;
					bl=true;
					//创建炸弹，放入炸弹向量
					Boom b=new Boom(et.x, et.y);
					booms.add(b);
				}
				break;
			case 2:
			case 3:
				if(s.x>et.x&&s.x<et.x+30&&s.y>et.y&&s.y<et.y+20)
				{
					s.isLive=false;
					et.isLive=false;
					bl=true;
					Boom b=new Boom(et.x, et.y);
					booms.add(b);
				}
				break;
			}
			return bl;
	}
	
	public void DrawTank(int x,int y,Graphics g,int direct,int type)
	{
		//判断是什么类型
		switch(type)
		{
		case 0:
			g.setColor(Color.red);
			break;
		case 1:
			g.setColor(Color.blue);
			break;
		
		}
		//判断方向
		switch(direct)
		{
		case 0://方向向上
			
			//画出我的坦克,由五部分组成的，到时封装成一个函数
			//1.画出左边矩形
			g.fill3DRect(x, y, 5, 30,false);
			//2。画出右边矩形
			g.fill3DRect(x+15, y, 5, 30,false);
			//3.画出中间矩形
			g.fill3DRect(x+5, y+5, 10, 20,false);
			//4.画出小球
			g.fillOval(x+4, y+10, 10, 10);
			//5.画出炮弹筒
			g.drawLine(x+9, y+15, x+9, y);
			break;
		case 1://向下
			//1.画出左边矩形
			g.fill3DRect(x, y, 5, 30,false);
			//2。画出右边矩形
			g.fill3DRect(x+15, y, 5, 30,false);
			//3.画出中间矩形
			g.fill3DRect(x+5, y+5, 10, 20,false);
			//4.画出小球
			g.fillOval(x+4, y+10, 10, 10);
			//5.画出炮弹筒
			g.drawLine(x+9, y+15, x+9, y+30);
			break;
			
		case 2://向左
			g.fill3DRect(x, y, 30, 5,false);
			g.fill3DRect(x, y+15, 30, 5, false);
			g.fill3DRect(x+5, y+5, 20, 10, false);
			g.fillOval(x+10, y+4, 10, 10);
			g.drawLine(x+15, y+9, x, y+9);
			break;
		case 3://向右
			g.fill3DRect(x, y, 30, 5,false);
			g.fill3DRect(x, y+15, 30, 5, false);
			g.fill3DRect(x+5, y+5, 20, 10, false);
			g.fillOval(x+10, y+4, 10, 10);
			g.drawLine(x+15, y+9, x+30, y+9);
			break;
			
		}
	}
	@Override // 键按下 a s d w
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_W)
		{
			//设置我的坦克的方向//坦克的方向 0表示上，2表示左，1下，3右
			this.hero.setDirect(0);
			this.hero.moveUp();
		}else if(e.getKeyCode()==KeyEvent.VK_S)
		{
			this.hero.setDirect(1);
			this.hero.moveDown();
		}else if(e.getKeyCode()==KeyEvent.VK_A)
		{
			this.hero.setDirect(2);
			this.hero.moveLeft();
		}else if(e.getKeyCode()==KeyEvent.VK_D)
		{
			this.hero.setDirect(3);
			this.hero.moveRight();
		}
		
		//判断玩家是否按下j，发射子弹
		if(e.getKeyCode()==KeyEvent.VK_J)
		{
			if(this.hero.ss.size()<5) 
			{
				this.hero.ShotEnemy();
			}
			//开火
			
		}
		//必须重绘窗口
		this.repaint();
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//每隔一百ms去重画子弹
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
			//判断是否需要给坦克加入新的子弹
			for(int i=0;i<ets.size();i++)
			{
				EnemyTank et=ets.get(i);
				if(et.isLive)
				{
					if(et.ss.size()<1)//控制敌人子弹连发数 problems  1h18min
					{
						Shot s=null;
						switch(et.direct)
						{
						case 0:
							s=new Shot(et.x+9, et.y, 0);
							et.ss.add(s);
							break;
						case 1:
							s=new Shot(et.x+9, et.y+30, 1);
							et.ss.add(s);
							break;
						case 2:
							s=new Shot(et.x, et.y+9, 2);
							et.ss.add(s);
							break;
						case 3:
							s=new Shot(et.x+30, et.y+9, 3);
							et.ss.add(s);
							break;
							
						}
						//启动子弹线程
						Thread t =new Thread(s);
						t.start();
					}
				}
			}
			//函数，判断是否击中敌人坦克
			this.hitEnemyTank();
			//函数，判断敌人子弹是否击中我的坦克
			this.hitMe();
			
			//重绘
			this.repaint();
		}
	}
}