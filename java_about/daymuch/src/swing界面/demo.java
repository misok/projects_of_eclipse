package swing����;
/**
	 * @param args
	 * @���ܣ�gui���濪��
	 */
import java.awt.*;
import javax.swing.*;
public class demo extends JFrame {
	//����Ҫ��swing�������������
	
	JButton jb1=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		demo demo1=new demo();

		}
		//���캯��
		public demo()
		{
			
		//����һ����ť
		jb1=new JButton("����һ����ť");
		//��Ӱ�ť���
		this.add(jb1);
		//���������ñ���
		this.setTitle("hello,world��");
		//���������ô�С��������
		this.setSize(200,200);
		
		//���ó�ʼλ��
		this.setLocation(100, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//���õ��رմ��ڵ�ʱ�򡣱�֤jvmҲ�˳�
		//��ʾ
		this.setVisible(true);
		}
		
		
		
	

}
