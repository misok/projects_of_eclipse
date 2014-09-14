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
	 * ̹�˴�ս1.0�汾  �����ƶ�
	 * �ӵ���������,����������
	 * ���е���̹��
	 * ��ըЧ������׼������ͼƬ����ըЧ����
	 * ����̹�˻�����ʱ��Ҳ��ը
	 * 8/13���Ƶ��˵�̹�˲��ص�����  �ж��Ƿ���ײ�ĺ���д��EnemyTank��
	 * 8/13:��¼���е��˵�̹�����ʹ����˳�����
	 *������ͣ��Ϸ  (���ո����ͣ�����û������ͣʱ���ӵ����ٶȺ�̹�˵��ٶ���Ϊ0.̹�˵ķ���Ҫ�����仯
	 *����ѡ�أ�1.��һ��panel,������ʾ2.��������˸Ч����
	 *  ��¼��ҵĳɼ������ļ����� дһ����¼�࣬��ɶ�ս���ļ�¼��
	 *java��β��������ļ�
	 * 14/8/3��4
	 */
public class TankGameV1 extends JFrame implements ActionListener{
	Mypanel mp=null;
	//������ʾ���
	MyStartPanel msp=null;
	
	//�����ҵĲ˵�
	JMenuBar jmb=null;
	JMenu jm1=null;
	JMenuItem jmi1=null;//��ʼ��Ϸ
	JMenuItem jmi2=null;//�˳���Ϸ
	JMenuItem jmi3=null;//�����˳�
	JMenuItem jmi4=null;//���Ͼ�
	public TankGameV1()
	{
		
		
		//�����˵��Ͳ˵�ѡ��
		jmb=new JMenuBar();
		jm1=new JMenu("��Ϸ");
		//���ÿ�ݼ�
		jm1.setMnemonic('G');
		jmi1=new JMenuItem("��ʼ����Ϸ(N)");
		jmi2=new JMenuItem("�˳�����Ϸ(E)");
		jmi3=new JMenuItem("�����˳���Ϸ(C)");
		jmi4=new JMenuItem("���Ͼ�(G)");
		//��jmi1��Ӧ
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
		//���û���ͬ�ĵ��������ͬ�Ĵ���
		if(e.getActionCommand().equals("newgame"))
		{
			//������Ϸ���
			mp=new Mypanel("newgame");
			//����PANEL�߳�
			Thread t=new Thread(mp);
			t.start();
			this.remove(msp);//Ҫ��ɾ���ɵ�paNel
			this.add(mp);
			//ע�����
			this.addKeyListener(mp);
			//��ʾ��ˢ��
			this.setVisible(true);
			//this.setSize(400,300);
		}else if(e.getActionCommand().equals("exit"))
		{
			//������ٵ���������Ϣ
			Recorder.keepRecorder();
			System.exit(0);
		}else if(e.getActionCommand().equals("saveexit"))
		{
			Recorder.setEts(mp.ets);
			//�Դ����˳������� 1.������ٵ��˵������͵��˵�����
			Recorder.keepRecorderandEnemy();
			//�˳�
			System.exit(0);
		}else if(e.getActionCommand().equals("continue"))
		{
			//���Ͼ�
			mp=new Mypanel("con");
			
			
			//����PANEL�߳�
			Thread t=new Thread(mp);
			t.start();
			this.remove(msp);//Ҫ��ɾ���ɵ�paNel
			this.add(mp);
			//ע�����
			this.addKeyListener(mp);
			//��ʾ��ˢ��
			this.setVisible(true);
		}
	}

}
//��ʾ�ֹ����
class  MyStartPanel extends JPanel implements Runnable
{
	int times=0;
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		if(times%2==0)//������˸Ч��
		{
			g.setColor(Color.yellow);
			//������Ϣ������
			Font myFont =new Font("������κ",Font.BOLD,30);
			g.setFont(myFont);
			g.drawString("stage��1", 150, 150);
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

//�ҵ����panel
class Mypanel extends JPanel implements KeyListener,Runnable
{
	//����һ���ҵ�̹��
	Hero hero=null;
	
	
	//������˵�̹����
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	Vector<Node> nodes=new Vector<Node>();
	int enSize=5;
	
	//����ը������
	Vector<Boom> booms=new Vector<Boom>();
	
	//��������ͼƬ
	Image image1=null;
	Image image2=null;
	Image image3=null;
	//���ſ�ʼ����
	
	
	
	
	public Mypanel(String flag)
	{
		AePlayWave apw=new AePlayWave("e:\\��ʼ.wav");
	    apw.start();
		//�ظ���¼
		Recorder.getRecorder();
		hero=new Hero(200, 200);
		
		if(flag.equals("newgame"))
		{
			for(int i=0;i<enSize;i++)
			{
			//����һ�����˵�̹�˶���
			EnemyTank et=new EnemyTank((i+1)*50, 5);//��仰������
			et.setColor(1);//���˵�̹����ɫΪ1���ҵ�Ϊ0
			et.setDirect(2);
			//��MyPanel�ĵ���̹�����������õ���̹�ˣ��ж��ص���
			et.setEts(ets);
			//�������˵�̹��
			Thread t=new Thread(et);
			t.start();
			//�����˵�̹�˼����ӵ�
			Shot s=new Shot(et.x+9, et.y+30,2 );//������̹�˳�ʼ�����й�
			//���������
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
			//����һ�����˵�̹�˶���
			EnemyTank et=new EnemyTank(node.x,node.y);//��仰������
			et.setColor(1);//���˵�̹����ɫΪ1���ҵ�Ϊ0
			et.setDirect(node.direct);
			//��MyPanel�ĵ���̹�����������õ���̹�ˣ��ж��ص���
			et.setEts(ets);
			//�������˵�̹��
			Thread t=new Thread(et);
			t.start();
			//�����˵�̹�˼����ӵ�
			Shot s=new Shot(et.x+9, et.y+30,2 );//������̹�˳�ʼ�����й�
			//���������
			et.ss.add(s);
			Thread t2=new Thread(s);
			t2.start();
			ets.add(et);
			}
		}
		
		//��ʼ��ͼƬ,����ͼƬ���л����һ��ը��
		image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/boom1.png"));
		image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/boom2.png"));
		image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/boom3.png"));
//		try {
//			image1=ImageIO.read(new File("boom1.png"));
//			image2=ImageIO.read(new File("boom2.png"));
//			image3=ImageIO.read(new File("boom3.png"));//����˵�һ��ͼƬû��ըЧ����bug
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
	}
	//������ʾ��Ϣ
	public void showInfo(Graphics g)
	{
		//������ʾ��Ϣ̹��(��̹�˲�����ս����
		this.DrawTank(80, 330, g, 0, 1);
		g.setColor(Color.black);
		g.drawString(Recorder.getEnNum()+"", 110, 350);
		this.DrawTank(140,330, g, 0, 0);
		g.setColor(Color.black);
		g.drawString(Recorder.getMyLife()+"", 170, 350);
		
		//������ҵ��ܳɼ�
		g.setColor(Color.black);
		Font f=new Font("����", Font.BOLD, 20);
		g.setFont(f);
		g.drawString("�����ܳɼ���", 420, 30);
		this.DrawTank(420, 60, g, 0, 0);
		g.setColor(Color.black);
		g.drawString(Recorder.getAllEnNum()+"", 460, 80);
	}
	//��дpaint����
	public void paint(Graphics g)//�ӵ�Ҫ������Ҫ��ͣ�Ļ��ƣ�����MYpanelӦ������һ���߳�
	{
		super.paint(g);
		g.fillRect(0, 0, 400, 300);//�ñ�����ɺ�ɫ
		
		this.showInfo(g);
		
		//�����Լ���̹��
		if(hero.isLive)
		{
			this.DrawTank(hero.getX(), hero.getY(), g, hero.direct, 0);
		}
		
		
		//�������˵�̹��
		for(int i=0;i<ets.size();i++)
		{
			EnemyTank et=ets.get(i);
			if(et.isLive)
			{
				this.DrawTank(et.getX(), et.getY(), g, et.getDirect(), 1);
				//�ٻ������˵��ӵ�
				for(int j=0;j<et.ss.size();j++)
				{
					Shot enemyShot=et.ss.get(j);
					if(enemyShot.isLive)
					{
						g.draw3DRect(enemyShot.x, enemyShot.y, 1, 1, false);
					}else{
						//������˵�̹�������ʹ�Vecitorȥ��
						et.ss.remove(enemyShot);
					}
				}
			}
			
		}
		//����ը���࣬����ըЧ��
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
		//����һ���ӵ�
		//��������ӵ���ѭ��,��ss��ȡ��ÿ���ӵ���������
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
				hero.ss.remove(myShot);//����Ϊʲô����i
			}
		}
		
		
		
	}
	//�ж��ҵ��ӵ��Ƿ���е��˵�̹��
	public void hitEnemyTank()
	{
		//�ж��Ƿ���е��˵�̹��
		for(int i=0;i<hero.ss.size();i++)
		{
			Shot myShot=hero.ss.get(i);//ȡ���ӵ�
			if(myShot.isLive==true)//�ж��ӵ��Ƿ���Ч
			{
				for(int j=0;j<ets.size();j++)
				{
					EnemyTank et=ets.get(j);
					if(et.isLive)
					{
						if(this.hitTank(myShot, et))
						{
							Recorder.reduceEnNum();
							Recorder.addEnNum();//�����ҵļ�¼
						}
					}
				}
			}
			
		}
	}
	//�жϵ��˵��ӵ��Ƿ�����ҵ�̹��
	public void hitMe()
	{
		//ȡ��ÿһ�����˵��ӵ����ҵ�̹��ƥ��
		for(int i=0;i<this.ets.size();i++)
		{
			//ȡ��̹��
			EnemyTank et=ets.get(i);
			//ȡ��ÿһ���ӵ�
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
	//������Ϊ����������д���е��˵�̹��
	public boolean hitTank(Shot s,Tank et)
	{
		boolean bl=false;//Ĭ��û�л���
			//��Ҫ�жϷ���
			switch(et.direct)
			{
			//����̹�˷������ϡ���
			case 0:
			case 1:
				if (s.x>et.x&&s.x<et.x+20&&s.y>et.y&&s.y<et.y+30)
				{
					//���к��ӵ�����������̹������
					s.isLive=false;
					et.isLive=false;
					bl=true;
					//����ը��������ը������
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
		//�ж���ʲô����
		switch(type)
		{
		case 0:
			g.setColor(Color.red);
			break;
		case 1:
			g.setColor(Color.blue);
			break;
		
		}
		//�жϷ���
		switch(direct)
		{
		case 0://��������
			
			//�����ҵ�̹��,���岿����ɵģ���ʱ��װ��һ������
			//1.������߾���
			g.fill3DRect(x, y, 5, 30,false);
			//2�������ұ߾���
			g.fill3DRect(x+15, y, 5, 30,false);
			//3.�����м����
			g.fill3DRect(x+5, y+5, 10, 20,false);
			//4.����С��
			g.fillOval(x+4, y+10, 10, 10);
			//5.�����ڵ�Ͳ
			g.drawLine(x+9, y+15, x+9, y);
			break;
		case 1://����
			//1.������߾���
			g.fill3DRect(x, y, 5, 30,false);
			//2�������ұ߾���
			g.fill3DRect(x+15, y, 5, 30,false);
			//3.�����м����
			g.fill3DRect(x+5, y+5, 10, 20,false);
			//4.����С��
			g.fillOval(x+4, y+10, 10, 10);
			//5.�����ڵ�Ͳ
			g.drawLine(x+9, y+15, x+9, y+30);
			break;
			
		case 2://����
			g.fill3DRect(x, y, 30, 5,false);
			g.fill3DRect(x, y+15, 30, 5, false);
			g.fill3DRect(x+5, y+5, 20, 10, false);
			g.fillOval(x+10, y+4, 10, 10);
			g.drawLine(x+15, y+9, x, y+9);
			break;
		case 3://����
			g.fill3DRect(x, y, 30, 5,false);
			g.fill3DRect(x, y+15, 30, 5, false);
			g.fill3DRect(x+5, y+5, 20, 10, false);
			g.fillOval(x+10, y+4, 10, 10);
			g.drawLine(x+15, y+9, x+30, y+9);
			break;
			
		}
	}
	@Override // ������ a s d w
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_W)
		{
			//�����ҵ�̹�˵ķ���//̹�˵ķ��� 0��ʾ�ϣ�2��ʾ��1�£�3��
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
		
		//�ж�����Ƿ���j�������ӵ�
		if(e.getKeyCode()==KeyEvent.VK_J)
		{
			if(this.hero.ss.size()<5) 
			{
				this.hero.ShotEnemy();
			}
			//����
			
		}
		//�����ػ洰��
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
		//ÿ��һ��msȥ�ػ��ӵ�
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
			//�ж��Ƿ���Ҫ��̹�˼����µ��ӵ�
			for(int i=0;i<ets.size();i++)
			{
				EnemyTank et=ets.get(i);
				if(et.isLive)
				{
					if(et.ss.size()<1)//���Ƶ����ӵ������� problems  1h18min
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
						//�����ӵ��߳�
						Thread t =new Thread(s);
						t.start();
					}
				}
			}
			//�������ж��Ƿ���е���̹��
			this.hitEnemyTank();
			//�������жϵ����ӵ��Ƿ�����ҵ�̹��
			this.hitMe();
			
			//�ػ�
			this.repaint();
		}
	}
}