/**
	 * @param args
	 * ͨ���������ҹ��������С����ƶ�
	 * �¼��������
	 * 
	 */
package xiaoma.com.Demo2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Demo2 extends JFrame {//��Ҫ������������֪������Demo2,���������¼�Դ����MYpanel����¼�������

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

//�����Լ���panel
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
	public void keyPressed(KeyEvent e) {//����һ����
		// TODO Auto-generated method stub
		//System.out.println("��������"+(char)e.getKeyCode());
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			//System.out.println("12");
			y+=10;//x���겻�䣬y���£����������ƶ�,�ܵĿ�һ��
			
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
		this.repaint();//�ػ����
	}

	@Override
	public void keyReleased(KeyEvent e) {//�ɿ�һ����
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {//����һ��ֵ�����
		// TODO Auto-generated method stub
		
	}
}
