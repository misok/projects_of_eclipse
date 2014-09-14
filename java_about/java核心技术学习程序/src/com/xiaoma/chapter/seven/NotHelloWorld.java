package com.xiaoma.chapter.seven;
import java.awt.*;
import javax.swing.*;
public class NotHelloWorld {

	/**
	 * @param args
	 * 7-3
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				NotHelloWorldFrame nframe=new NotHelloWorldFrame();
				nframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				nframe.setVisible(true);
			}
		});
		

	}

}
//һ����ʾ��Ϣ�����
class NotHelloWorldComponent extends JComponent{
	public static final int MESSAGE_X=75;
	public static final int MESSAGE_Y=100;
	public void paintComponent(Graphics g){
		g.drawString("Not a Hello World!", MESSAGE_X, MESSAGE_Y);
		
	}
}

//�������������
class NotHelloWorldFrame extends JFrame{
	public static final int DEFAULT_WIDTH=300;
		public static final int DEFAULT_HEIGHT=200;
	public NotHelloWorldFrame(){
		
		setTitle("NotHelloWorld");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		NotHelloWorldComponent comp=new NotHelloWorldComponent();
		add(comp);
	}
}