package swing����;
import java.awt.*;
import javax.swing.*;
public class �����б���� extends JFrame {

	/**
	 * @param args
	 */
	
	JComboBox jc1;
	JPanel jp1,jp2;
	JLabel jl1,jl2;
	JList list;
	JScrollPane jsp;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		�����б���� show=new �����б����();

	}
	public �����б����()
	{
		jc1=new JComboBox();
		jp1=new JPanel();
		jp2=new JPanel();
		jl1=new JLabel("��ļ���");
		jl2=new JLabel("��ϲ��ȥ�ĵط�");
		
		String []jg={"����","�Ϻ�","���","����"};
		jc1=new JComboBox(jg);
		
		String []dd={"��ɽ","��ɽ","��կ��","��üɽ"};
		list=new JList(dd);
		list.setVisibleRowCount(2);
		jsp=new JScrollPane(list);//�ѹ������н�ȥ
		
		
		this.setLayout(new GridLayout(3,1));//���ò���
		
		jp1.add(jl1);
		jp1.add(jc1);
		jp2.add(jl2);
		jp2.add(jsp);//�������Թ���
		
		this.add(jp1);
		this.add(jp2);//������
		
		this.setSize(300,150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}
