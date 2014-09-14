package com.xiaoma.chapter.seven;
import java.awt.*;
import javax.swing.*;
public class SimpleFrameTest {

	/**
	 * @param args
	 * @author Administrator
	 * 7-1
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//下面这段是默认的写法
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				SimpleFrame frame=new SimpleFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				
			}
		});

	}

}
class SimpleFrame extends JFrame{
	public SimpleFrame(){
		setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
	}
	public static final int DEFAULT_WIDTH=300;
	public static final int DEFAULT_HEIGHT=200;
}