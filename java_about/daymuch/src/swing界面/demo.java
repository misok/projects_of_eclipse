package swing界面;
/**
	 * @param args
	 * @功能：gui界面开发
	 */
import java.awt.*;
import javax.swing.*;
public class demo extends JFrame {
	//把需要的swing组件定义在这里
	
	JButton jb1=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		demo demo1=new demo();

		}
		//构造函数
		public demo()
		{
			
		//设置一个按钮
		jb1=new JButton("我是一个按钮");
		//添加按钮组件
		this.add(jb1);
		//给窗体设置标题
		this.setTitle("hello,world！");
		//给窗体设置大小，按像素
		this.setSize(200,200);
		
		//设置初始位置
		this.setLocation(100, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置当关闭窗口的时候。保证jvm也退出
		//显示
		this.setVisible(true);
		}
		
		
		
	

}
