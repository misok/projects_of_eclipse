/**
	 * @param args
	 * 默认居中对齐
	 */
package swing界面;

import java.awt.*;
import javax.swing.*;
public class 流式布局 extends JFrame {
	JButton jb1,jb2,jb3,jb4,jb5,jb6;

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		流式布局 fl=new 流式布局();

	}
	public 流式布局()
	{
		jb1=new JButton("关羽");
		jb2=new JButton("张飞");
		jb3=new JButton("赵云");
		jb4=new JButton("马超");
		jb5=new JButton("黄忠");
		jb6=new JButton("威严");
		
		this.add(jb1);
		this.add(jb2);
		this.add(jb3);
		this.add(jb4);
		this.add(jb5);
		this.add(jb6);
		
		//设置布局管理器，因为默认是边界
		this.setLayout(new FlowLayout(FlowLayout.LEFT));//对齐方式默认居中对齐
		this.setTitle("流式管理器");
		this.setSize(300,200);
		this.setLocation(200, 200);
		this.setResizable(false);//不允许改变窗体大小
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//显示窗体
		this.setVisible(true);
		
	}

}
