import java.applet.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class TicTacToe extends Applet implements MouseListener {
	//�������е����Ӹ�ֵ
	int FREE=0,NOUGHT=1,CROSS=4;
	//�ó���Ϊ9�������ʾ3*3����
	int board[]=new int[9];
	//����ֱ��ʾԲȦ�Ͳ���ͼƬ
	private Image noughtImage,crossImage;
	//��Ϸ�Ƿ��Ѿ������ı�־
	boolean gameover;
	//��¼��Ϸ��ֵ���Ϣ
	String message;
	//init��ɶ�����ͼƬ�Ķ�ȡ����
	public void init()
	{
		noughtImage=getImage(getCodeBase(),"image/nought.gif");
		crossImage=getImage(getCodeBase(),"image/cross.gif");
		
		setSize(200,200);
		addMouseListener(this);
	}
	//paint�����������̣�������borad�������ֵȷ���ø���ԲȦ���ǲ��
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
			//����Ϸ����������ƽ����Ϣ
			g.setColor(Color.green);
			g.setFont(new Font("����",Font.BOLD,25));
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
				//��������������λ��
				Dimension d=getSize();
				int col=(e.getX()*3)/d.width;
				int row=(e.getY()*3)/d.height;
				int pos=row*3+col;
				
				//ֻ�ܵ����δ��ռ�õĸ���
				if(board[pos]==FREE)
				{
					board[pos]=NOUGHT;
					//��������Ƿ��Ѿ�ʤ��
					if(won(board,NOUGHT)==true)
					{
						message="��ϲ����Ӯ�ˣ�";
					
					}else{
						//�����ü������һ�����Ҳ��Խ��
						/*public void amateur()
						{
							ArrayList blanks=new ArrayList();
							//���������еĿո��¼��free��
							for(int i=0;i<9;i++)
							{
								if(board[i]==FREE)
								{
									blanks.add(new Integer(i));
								}
							}
							if(blanks.size()==0)
							{
								message="ƽ��";
								gameover=true;
							}else
							{
								int index=(int)(Math.random()*blanks.size());
								board[((Integer)(blanks.get(index))).intValue()]=CROSS;
							}
							
						}*/
						if(won(board,CROSS)==true)
						{
							message="�����������ˣ�";
							gameover=true;
						}
					}
				}
			}
		repaint();
		
	}
	
	static int[][] lines={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
	//�����Ƿ���ͨ
	public boolean won(int[] board,int value)
	{
		//���μ���ÿ��ÿ���Լ������Խ���
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
