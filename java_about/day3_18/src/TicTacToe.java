import java.applet.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class TicTacToe extends Applet implements MouseListener {
	//给棋盘中的棋子赋值
	int FREE=0,NOUGHT=1,CROSS=4;
	//用长度为9的数组表示3*3棋盘
	int board[]=new int[9];
	//定义分别表示圆圈和叉叉的图片
	private Image noughtImage,crossImage;
	//游戏是否已经结束的标志
	boolean gameover;
	//记录游戏结局的消息
	String message;
	//init完成对两个图片的读取工作
	public void init()
	{
		noughtImage=getImage(getCodeBase(),"image/nought.gif");
		crossImage=getImage(getCodeBase(),"image/cross.gif");
		
		setSize(200,200);
		addMouseListener(this);
	}
	//paint函数画出棋盘，并根据borad变量里的值确定该格是圆圈还是叉叉
	public void paint(Graphics g)
	{
		Dimension d=getSize();
		int xoff=d.width/3;
		int yoff=d.width/3;
		g.setColor(Color.black);
		g.drawLine(xoff, 0, xoff, d.height);
		g.drawLine(2*xoff, 0, 2*xoff, d.height);
		g.drawLine(0, yoff, d.width, yoff);
		g.drawLine(0, 2*yoff, d.width, 2*yoff);
		
		for(int i=0;i<9;i++)
		{
			if (NOUGHT==board[i])
			{
				g.drawImage(noughtImage, i%3*xoff, i/3*yoff,this);
			}else if(CROSS==board[i])
			{
				g.drawImage(crossImage, i%3*xoff, i/3*yoff,this);
			}
		}
		if(true==gameover)
		{
			//若游戏结束，则绘制结局信息
			g.setColor(Color.green);
			g.setFont(new Font("宋体",Font.BOLD,25));
			g.drawString(message, 10, 100);
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(true==gameover)
		{
			gameover=false;
			
			for(int i=0;i<9;i++)
			{
				board[i]=0;
			}
		}else{
				//计算玩家鼠标点击的位置
				Dimension d=getSize();
				int col=(e.getX()*3)/d.width;
				int row=(e.getY()*3)/d.height;
				int pos=row*3+col;
				
				//只能点击的未被占用的格子
				if(board[pos]==FREE)
				{
					board[pos]=NOUGHT;
					//测试玩家是否已经胜利
					if(won(board,NOUGHT)==true)
					{
						message="恭喜，你赢了！";
					
					}else{
						//否则让计算机下一步并且测试结果
						/*public void amateur()
						{
							ArrayList blanks=new ArrayList();
							//搜索出所有的空格记录到free中
							for(int i=0;i<9;i++)
							{
								if(board[i]==FREE)
								{
									blanks.add(new Integer(i));
								}
							}
							if(blanks.size()==0)
							{
								message="平局";
								gameover=true;
							}else
							{
								int index=(int)(Math.random()*blanks.size());
								board[((Integer)(blanks.get(index))).intValue()]=CROSS;
							}
							
						}*/
						if(won(board,CROSS)==true)
						{
							message="哈哈，你输了！";
							gameover=true;
						}
					}
				}
			}
		repaint();
		
	}
	
	static int[][] lines={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
	//测试是否联通
	public boolean won(int[] board,int value)
	{
		//依次计算每行每列以及两个对角线
		for(int i=0;i<lines.length;i++)
		{
			int[] line=lines[i];
			
			int sum=0;
			for(int j=0;j<line.length;j++)
			{
				int k=line[j];
				sum=sum+board[k];
			}
			if(sum==3*value)
			{
				return true;
			}
			
		}
		return false;
	}
	
	
}
