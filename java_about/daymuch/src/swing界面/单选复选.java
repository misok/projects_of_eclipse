package swing����;
import java.awt.*;
import javax.swing.*;
public class ��ѡ��ѡ extends JFrame {

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
		��ѡ��ѡ select=new ��ѡ��ѡ();

	}
	public ��ѡ��ѡ()
	{
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		jl1=new JLabel("��ϲ�����˶�");
		jl2=new JLabel("����Ա�");
		jc1=new JCheckBox("����");
		jc2=new JCheckBox("����");
		jc3=new JCheckBox("��ë��");
		jr1=new JRadioButton("��");//һ��Ҫ�ѵ�ѡ��ŵ���ť�������
		jr2=new JRadioButton("Ů");
		jb1=new JButton("ע���û�");
		jb2=new JButton("ȡ��ע��");
		bg=new ButtonGroup();
		
		bg.add(jr1);
		bg.add(jr2);//��ѡ��ӽ���
		
		this.setLayout(new GridLayout(3,1));
		
		jp1.add(jl1);
		jp1.add(jc1);
		jp1.add(jc2);
		jp1.add(jc3);
		
		jp2.add(jl2);
		jp2.add(jr1);
		jp2.add(jr2);//��ѡ��������ҲҪһ�����ţ���֮ǰҪ���ڰ�ť����
		
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
