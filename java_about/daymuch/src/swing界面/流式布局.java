/**
	 * @param args
	 * Ĭ�Ͼ��ж���
	 */
package swing����;

import java.awt.*;
import javax.swing.*;
public class ��ʽ���� extends JFrame {
	JButton jb1,jb2,jb3,jb4,jb5,jb6;

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		��ʽ���� fl=new ��ʽ����();

	}
	public ��ʽ����()
	{
		jb1=new JButton("����");
		jb2=new JButton("�ŷ�");
		jb3=new JButton("����");
		jb4=new JButton("��");
		jb5=new JButton("����");
		jb6=new JButton("����");
		
		this.add(jb1);
		this.add(jb2);
		this.add(jb3);
		this.add(jb4);
		this.add(jb5);
		this.add(jb6);
		
		//���ò��ֹ���������ΪĬ���Ǳ߽�
		this.setLayout(new FlowLayout(FlowLayout.LEFT));//���뷽ʽĬ�Ͼ��ж���
		this.setTitle("��ʽ������");
		this.setSize(300,200);
		this.setLocation(200, 200);
		this.setResizable(false);//������ı䴰���С
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//��ʾ����
		this.setVisible(true);
		
	}

}
