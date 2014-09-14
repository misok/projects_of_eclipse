package swing界面;
import java.awt.*;
import javax.swing.*;
public class 单选复选 extends JFrame {

	/**
	 * @param args
	 */
	JPanel jp1,jp2,jp3;
	JLabel jl1,jl2;
	JButton jb1,jb2;
	JCheckBox jc1,jc2,jc3;
	JRadioButton jr1,jr2;
	ButtonGroup bg;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		单选复选 select=new 单选复选();

	}
	public 单选复选()
	{
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		jl1=new JLabel("你喜欢的运动");
		jl2=new JLabel("你的性别");
		jc1=new JCheckBox("足球");
		jc2=new JCheckBox("篮球");
		jc3=new JCheckBox("羽毛球");
		jr1=new JRadioButton("男");//一定要把单选框放到按钮组里管理
		jr2=new JRadioButton("女");
		jb1=new JButton("注册用户");
		jb2=new JButton("取消注册");
		bg=new ButtonGroup();
		
		bg.add(jr1);
		bg.add(jr2);//单选框加进来
		
		this.setLayout(new GridLayout(3,1));
		
		jp1.add(jl1);
		jp1.add(jc1);
		jp1.add(jc2);
		jp1.add(jc3);
		
		jp2.add(jl2);
		jp2.add(jr1);
		jp2.add(jr2);//单选框在这里也要一个个放，但之前要放在按钮组里
		
		jp3.add(jb1);
		jp3.add(jb2);
		
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		
		this.setSize(300,150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}
