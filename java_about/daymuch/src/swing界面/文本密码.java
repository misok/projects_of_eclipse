package swing����;
import java.awt.*;
import javax.swing.*;
public class �ı����� extends JFrame {

	/**
	 * @param args
	 * ����jpanel��Ȼ�������jpanel�ŵ����񲼾���ȥ
	 */
	JPanel jp1,jp2,jp3;
	JLabel jl1,jl2;
	JButton jb1,jb2;
	JTextField jt1;
	JPasswordField jpf1;
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		�ı����� text=new �ı�����();

	}
	public �ı�����()
	{
		jp1=new JPanel();
	    jp2=new JPanel();
	    jp3=new JPanel();
	
	    jl1=new JLabel("�û���");
	    jl2=new JLabel("��     ��");
	    jt1=new JTextField(10);
	    jpf1=new JPasswordField(10);
	    jb1=new JButton("��½");
	    jb2=new JButton("ȡ��");
	    
	    this.setLayout(new GridLayout(3,1));//���ò��ֹ�����������
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
