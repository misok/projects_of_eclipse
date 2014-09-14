/**
	 * @param args
	 * 简单的事件处理机制
	 * 点击按钮更换背景色
	 * 监听者可以是各种类
	 * 
	 * 事件源：就是JButton的对象jb1\jb2
	 * 点击按钮之后将发生ActionEvent事件，程序就会产生一个ActionEvent的对象，这里就是arg0和e
	 * 然后mypanel和cat注册了监听，当事件发生时，他们就会接受的事件，从而交给事件处理函数处理
	 */
package xiaoma.com.simple;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Demo1 extends JFrame implements ActionListener{

	JPanel mp=null;
	JButton jb1=null;
	JButton jb2=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Demo1 demo1=new Demo1();

	}
	public Demo1()
	{
		mp=new JPanel();
		jb1=new JButton("黑色");
		jb2=new JButton("红色");
		
		this.add(jb1,BorderLayout.NORTH);
		mp.setBackground(Color.black);
		this.add(mp);
		this.add(jb2,BorderLayout.SOUTH);
		
		//注册监听
		jb1.addActionListener(this);//在JB1的身上发生事件，添加监听，让Demo1去监听（所以写this）
		//指定action命令
		jb1.setActionCommand("aa");//将来点了黑色就会把aa传递给事件处理方法
		jb2.addActionListener(this);//事件源对象是jb2，事件监听对象是创建this的类，那就是demo
		jb2.setActionCommand("bb");
		
		Cat cat=new Cat();
		jb1.addActionListener(cat);//事件源对象是jb1，事件监听对象是cat
		jb2.addActionListener(cat);
		this.setSize(200,150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	@Override
	//对事件处理的方法
	public void actionPerformed(ActionEvent e) {//e就是事件对象
		// TODO Auto-generated method stub
		System.out.println("ok");
		//判断是哪个按钮被点击
		
		if(e.getActionCommand().equals("aa"))
		{
			System.out.println("你点击黑色按钮");
			mp.setBackground(Color.black);
		}else if(e.getActionCommand().equals("bb")){
			System.out.println("你点击红色按钮");
			mp.setBackground(Color.red);
		}else{
			System.out.println("不知道");
		}
		
	}

}

class Cat implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getActionCommand().equals("aa"))
		{
			System.out.println("猫咪也知道你按下黑色按钮");
		}else if(arg0.getActionCommand().equals("bb"))
		{
			System.out.println("猫咪也知道你按下红色按钮了");
		}
	}
	
}
//class MyPanel extends JPanel
//{
//	public void paint(Graphics g)
//	{
//		super.paint(g);
//		g.fillRect(0, 0, , height)
//	}
//}