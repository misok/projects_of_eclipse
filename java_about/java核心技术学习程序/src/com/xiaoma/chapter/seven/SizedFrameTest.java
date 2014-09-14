package com.xiaoma.chapter.seven;
import java.awt.*;

import javax.swing.*;
public class SizedFrameTest {

	/**
	 * @param args
	 * 7-2
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				SizedFrame sFrame=new SizedFrame();
				sFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				sFrame.setVisible(true);
			}
		});
		

	}

}
class SizedFrame extends JFrame{
	public SizedFrame(){
		//得到屏幕像素,固定语句
		
		Toolkit kit=Toolkit.getDefaultToolkit();
		Dimension screenSize=kit.getScreenSize();
		int screenHeight=screenSize.height;
		int screenWidth=screenSize.width;
		
		
		//设置框架大小为屏幕的一半
		setSize(screenWidth/2,screenHeight/2);
		setLocationByPlatform(true);
		
		//设置标题
		setTitle("SizedFrame");
	}
}