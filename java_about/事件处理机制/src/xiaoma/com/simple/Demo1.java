/**
	 * @param args
	 * �򵥵��¼��������
	 * �����ť��������ɫ
	 * �����߿����Ǹ�����
	 * 
	 * �¼�Դ������JButton�Ķ���jb1\jb2
	 * �����ť֮�󽫷���ActionEvent�¼�������ͻ����һ��ActionEvent�Ķ����������arg0��e
	 * Ȼ��mypanel��catע���˼��������¼�����ʱ�����Ǿͻ���ܵ��¼����Ӷ������¼�����������
	 */
package xiaoma.com.simple;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Demo1 extends JFrame implements ActionListener{

	JPanel mp=null;
	JButton jb1=null;
	JButton jb2=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Demo1 demo1=new Demo1();

	}
	public Demo1()
	{
		mp=new JPanel();
		jb1=new JButton("��ɫ");
		jb2=new JButton("��ɫ");
		
		this.add(jb1,BorderLayout.NORTH);
		mp.setBackground(Color.black);
		this.add(mp);
		this.add(jb2,BorderLayout.SOUTH);
		
		//ע�����
		jb1.addActionListener(this);//��JB1�����Ϸ����¼�����Ӽ�������Demo1ȥ����������дthis��
		//ָ��action����
		jb1.setActionCommand("aa");//�������˺�ɫ�ͻ��aa���ݸ��¼�������
		jb2.addActionListener(this);//�¼�Դ������jb2���¼����������Ǵ���this���࣬�Ǿ���demo
		jb2.setActionCommand("bb");
		
		Cat cat=new Cat();
		jb1.addActionListener(cat);//�¼�Դ������jb1���¼�����������cat
		jb2.addActionListener(cat);
		this.setSize(200,150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	@Override
	//���¼�����ķ���
	public void actionPerformed(ActionEvent e) {//e�����¼�����
		// TODO Auto-generated method stub
		System.out.println("ok");
		//�ж����ĸ���ť�����
		
		if(e.getActionCommand().equals("aa"))
		{
			System.out.println("������ɫ��ť");
			mp.setBackground(Color.black);
		}else if(e.getActionCommand().equals("bb")){
			System.out.println("������ɫ��ť");
			mp.setBackground(Color.red);
		}else{
			System.out.println("��֪��");
		}
		
	}

}

class Cat implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getActionCommand().equals("aa"))
		{
			System.out.println("è��Ҳ֪���㰴�º�ɫ��ť");
		}else if(arg0.getActionCommand().equals("bb"))
		{
			System.out.println("è��Ҳ֪���㰴�º�ɫ��ť��");
		}
	}
	
}
//class MyPanel extends JPanel
//{
//	public void paint(Graphics g)
//	{
//		super.paint(g);
//		g.fillRect(0, 0, , height)
//	}
//}