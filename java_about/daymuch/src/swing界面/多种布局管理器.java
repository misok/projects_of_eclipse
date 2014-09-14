
package swing界面;
import java.awt.*;
import javax.swing.*;
public class 多种布局管理器 extends JFrame {
	//定义组件
	JPanel jp1,jp2;
	JButton jb1,jb2,jb3,jb4,jb5,jb6;
	

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		多种布局管理器 x=new 多种布局管理器();

	}
	//构造函数
	public 多种布局管理器()
	{
		jp1=new JPanel();//默认流式
		jp2=new JPanel();
		jb1=new JButton("xigua");
		jb2=new JButton("pinguo");
		jb3=new JButton("lizhi");
		jb4=new JButton("putao");
		jb5=new JButton("juzi");
		jb6=new JButton("xiangjiao");
		
		jp1.add(jb1);
		jp2.add(jb2);
		jp2.add(jb3);
		jp2.add(jb4);
		jp2.add(jb5);
		
		this.add(jp1,BorderLayout.NORTH);
		this.add(jb6,BorderLayout.CENTER);
		this.add(jp2,BorderLayout.SOUTH);
		
		this.setSize(300,150);
		this.setLocation(200,200);
		this.setVisible(true);
		
		
	}

}
