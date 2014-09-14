/**
	 * @param args
	 * 通过上下左右光标来控制小球的移动
	 * 事件处理机制
	 * 
	 */
package xiaoma.com.Demo2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Demo2 extends JFrame {//我要按键，则最先知道的是Demo2,所以它是事件源，而MYpanel变成事件监听了

	MyPanel mp=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Demo2 demo2=new Demo2();
		

	}
	public Demo2()
		{
			mp=new MyPanel();
			this.add(mp);
			this.addKeyListener(mp);
			this.addKeyListener(mp);
			this.setSize(300,400);
			this.setVisible(true);
			
		}

}

//定义自己的panel
class MyPanel extends JPanel implements KeyListener
{
	int x=10;
	int y=10;
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillOval(x,y, 10, 10);
	}

	@Override
	public void keyPressed(KeyEvent e) {//按下一个键
		// TODO Auto-generated method stub
		//System.out.println("键被按下"+(char)e.getKeyCode());
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			//System.out.println("12");
			y+=10;//x坐标不变，y向下，所以向下移动,跑的快一点
			
		}else if(e.getKeyCode()==KeyEvent.VK_UP)
		{
			y--;
		}else if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			x--;
		}else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			x++;
		}
		this.repaint();//重绘界面
	}

	@Override
	public void keyReleased(KeyEvent e) {//松开一个键
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {//键的一个值被输出
		// TODO Auto-generated method stub
		
	}
}
