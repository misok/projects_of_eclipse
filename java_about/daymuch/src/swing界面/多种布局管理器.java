
package swing����;
import java.awt.*;
import javax.swing.*;
public class ���ֲ��ֹ����� extends JFrame {
	//�������
	JPanel jp1,jp2;
	JButton jb1,jb2,jb3,jb4,jb5,jb6;
	

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		���ֲ��ֹ����� x=new ���ֲ��ֹ�����();

	}
	//���캯��
	public ���ֲ��ֹ�����()
	{
		jp1=new JPanel();//Ĭ����ʽ
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
