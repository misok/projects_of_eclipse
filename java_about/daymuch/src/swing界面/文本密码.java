package swing界面;
import java.awt.*;
import javax.swing.*;
public class 文本密码 extends JFrame {

	/**
	 * @param args
	 * 三个jpanel，然后把三个jpanel放到网格布局上去
	 */
	JPanel jp1,jp2,jp3;
	JLabel jl1,jl2;
	JButton jb1,jb2;
	JTextField jt1;
	JPasswordField jpf1;
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		文本密码 text=new 文本密码();

	}
	public 文本密码()
	{
		jp1=new JPanel();
	    jp2=new JPanel();
	    jp3=new JPanel();
	
	    jl1=new JLabel("用户名");
	    jl2=new JLabel("密     码");
	    jt1=new JTextField(10);
	    jpf1=new JPasswordField(10);
	    jb1=new JButton("登陆");
	    jb2=new JButton("取消");
	    
	    this.setLayout(new GridLayout(3,1));//设置布局管理器，网格
	    jp1.add(jl1);
	    jp1.add(jt1);
	    jp2.add(jl2);
	    jp2.add(jpf1);
	    jp3.add(jb1);
	    jp3.add(jb2);
	    this.add(jp1);
	    this.add(jp2);
	    this.add(jp3);
	    this.setSize(300,500);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setVisible(true);
	    
	    
	}

}
