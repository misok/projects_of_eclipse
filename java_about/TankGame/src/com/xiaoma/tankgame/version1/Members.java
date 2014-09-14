package com.xiaoma.tankgame.version1;

import java.io.*;
import java.util.Vector;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.*;
//̹����
class Tank
{
	//̹�˵ĺ�������
	int x=0;
	int y=0;
	int direct=0;//̹�˵ķ��� 0��ʾ�ϣ�1��ʾ�ң�2�£�3��
	//̹�˵��ٶ�
	int speed=3;
	//̹�˵���ɫ
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

//������������
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
//�ҵ�̹��
class Hero extends Tank
{
	//�ӵ�����
	
	Vector<Shot> ss=new Vector<Shot>();
	Shot s=null;
	public Hero(int x,int y)
	{
		super(x,y);
		
	}
	
	//����
	public void ShotEnemy()
	{
		
		switch(this.direct)
		{
		case 0:
			s=new Shot(x+9,y,0);//�ӵ�����
			ss.add(s);//��ӵ��ӵ�������
			break;
		case 1:
			s=new Shot(x+9,y+30,1);//�ӵ�����
			ss.add(s);
			break;
		case 2:
			s=new Shot(x,y+9,2);//����
			ss.add(s);
			break;
		case 3:
			s=new Shot(x+30,y+9,3);//����
			ss.add(s);
			break;
		
		}
		//�����ӵ��߳�
		Thread t=new Thread(s);
		t.start();
		
	}
	//̹�������ƶ�
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

//���˵�̹��,�ѵ��������߳���
class EnemyTank extends Tank implements Runnable
{
	//����һ�����������Է��ʵ�MyPanel�����е��˵�̹��
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	//int times=0;//����̹������ʱ��
	//����һ�����������Դ�ŵ��˵��ӵ�
	Vector<Shot> ss=new Vector<Shot>();
	//��������ӵ�Ӧ���ڴ���̹�˺͵��˵�̹���ӵ���������
	

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
				//��������
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
					if(x<370&&!this.isTouch())//��֤̹�˲����߽�  ��������ص�����
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
			
			//��̹����������µķ���
			this.direct=(int)(Math.random()*4);
			//�жϵ��˵�̹���Ƿ�����
			if(this.isLive==false)
			{
				//��̹���������˳��߳�
				break;
			}
			
		}
	}
	//�õ�Mypanel�ĵ���̹������
	public void setEts(Vector<EnemyTank> vv)
	{
		this.ets=vv;
	}
	//�ж��Ƿ������˱�ĵ���̹�ˣ����ص�����
	public boolean isTouch()
	{
		boolean b=false;
		switch(this.direct)
		{
		case 0:
			//���˵����̹������
			//ȡ�����еĵ���̹��
			for(int i=0;i<ets.size();i++)
			{
				EnemyTank et=ets.get(i);
				//�����ǲ����Լ�
				if(et!=this)
				{
					//���Ҫ�жϵĵ���̹�˷��������ϻ�������
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
					//���Ҫ�жϵĵ���̹�˷����������������
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
			//���˵����̹������
			//ȡ�����еĵ���̹��
			for(int i=0;i<ets.size();i++)
			{
				EnemyTank et=ets.get(i);
				//�����ǲ����Լ�
				if(et!=this)
				{
					//���Ҫ�жϵĵ���̹�˷��������ϻ�������
					if(et.direct==0||et.direct==1)
					{
						//�ҵ����
						if(this.x>=et.x&&this.x<=et.x+20&&this.y+30>=et.y &&this.y+30<=et.y+30)
						{
							return true;
						}
						//�ҵ��ҵ�
						if(this.x+20>=et.x&&this.x+20<=et.x+20&&this.y+30>=et.y &&this.y+30<=et.y+30)
						{
							return true;
						}
					}
					//���Ҫ�жϵĵ���̹�˷����������������
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
			//���˵����̹������
			//ȡ�����еĵ���̹��
			for(int i=0;i<ets.size();i++)
			{
				EnemyTank et=ets.get(i);
				//�����ǲ����Լ�
				if(et!=this)
				{
					//���Ҫ�жϵĵ���̹�˷��������ϻ�������
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
					//���Ҫ�жϵĵ���̹�˷����������������
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
			//���˵����̹������
			//ȡ�����еĵ���̹��
			for(int i=0;i<ets.size();i++)
			{
				EnemyTank et=ets.get(i);
				//�����ǲ����Լ�
				if(et!=this)
				{
					//���Ҫ�жϵĵ���̹�˷��������ϻ�������
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
					//���Ҫ�жϵĵ���̹�˷����������������
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
//�ӵ���

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
				Thread.sleep(50);//�ճ������ӵ���Ϣһ��
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
			//�ӵ���ʱ���������⣿
			//�жϸ��ӵ��Ƿ�������Ե
			if(x<0||x>400||y<0||y>300)
			{
				this.isLive=false;
				break;
			}
		}
	}
}
//��¼��
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
//��¼�࣬��¼����ս������Щ����
class Recorder
{
	//��¼ÿһ���ж��ٵ���
	private static int enNum=20;
	//�������ж��ٿ���̹��
	private static int myLife=3;
	//��¼�ܹ������˶��ٵ���
	private static int allEnNum=0;
	private static FileWriter fw=null;
	private static BufferedWriter bw=null;
	private static FileReader fr=null;
	private static BufferedReader br=null;
	private static Vector<EnemyTank> ets=new Vector<EnemyTank>();
    static  Vector<Node> nodes=new Vector<Node>();
	
	//��ɶ�ȡ����
	public Vector<Node>  getNodesAndEnNums()
	{
		try {
			fr=new FileReader("e:\\myTankRecorder.txt");
			br=new BufferedReader(fr);
			String n="";
			//�ȶ�ȡ��һ��
			n=br.readLine();
			allEnNum=Integer.parseInt(n);
			while((n=br.readLine())!=null)
			{
				String []xyz=n.split(" ");//���տո񷵻�����
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

	//������ٵ��˵������͵���̹������,fangxiang
	public static void keepRecorderandEnemy()
	{
		try {
			fw=new FileWriter("e:\\myTankRecorder.txt");
			bw=new BufferedWriter(fw);
			bw.write(allEnNum+"\r\n");
			
			//���浱ǰ�����ŵ�̹������ͷ���
			for(int i=0;i<ets.size();i++)
			{
				//ȡ��̹��
				EnemyTank et=ets.get(i);
				if(et.isLive)
				{
					//��ľͱ���
					String tank=et.x+" "+et.y+" "+et.direct;
					//д��
					bw.write(tank+"\r\n");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//�󿪵��ȹر�
				bw.close();
				fw.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	
	//����һ��ٵ���̹�˵��������浽�ļ�
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
				//�󿪵��ȹر�
				bw.close();
				fw.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	//���ļ��ж�ȡ��¼
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
	//��ʾ��������̹������
	public  static void reduceEnNum()
	{
		enNum--;
	}
	//�������
	public static void addEnNum()
	{
		allEnNum++;
	}
}
//ը����
class Boom
{
	int x;
	int y;
	int life=9;//ը��������ʱ��
	boolean isLive=true;
	public Boom(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	//��������ֵ
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