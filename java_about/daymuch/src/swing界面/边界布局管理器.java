/**
	 * @param args
	 * 1.�̳�JFrame(������
	 * 2.��������Ҫ�����
	 * 3.������������캯�����棩
	 * 4.������
	 * 5.�Դ�������
	 * 6.��ʾ����
	 */
package swing����;
import java.awt.*;
import javax.swing.*;
public class �߽粼�ֹ����� extends JFrame {
	//�������
	JButton jb1,jb2,jb3,jb4,jb5;

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		�߽粼�ֹ�����  x=new �߽粼�ֹ�����();

	}
	public �߽粼�ֹ�����()
	{
		jb1=new JButton("�в�");
		jb2=new JButton("����");
		jb3=new JButton("����");
		jb4=new JButton("�ϲ�");
		jb5=new JButton("����");
		
		//������
		this.add(jb1,BorderLayout.CENTER);
		this.add(jb2,BorderLayout.NORTH);
		this.add(jb3,BorderLayout.EAST);
		this.add(jb4,BorderLayout.SOUTH);
		this.add(jb5,BorderLayout.WEST);
		
		//���ô�������
		this.setTitle("�߽粼�ְ���");
		this.setSize(300,200);
		this.setLocation(200, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//��ʾ����
		this.setVisible(true);
		
	}

}
