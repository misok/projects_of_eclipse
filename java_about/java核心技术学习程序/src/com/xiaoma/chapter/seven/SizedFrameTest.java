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
		//�õ���Ļ����,�̶����
		
		Toolkit kit=Toolkit.getDefaultToolkit();
		Dimension screenSize=kit.getScreenSize();
		int screenHeight=screenSize.height;
		int screenWidth=screenSize.width;
		
		
		//���ÿ�ܴ�СΪ��Ļ��һ��
		setSize(screenWidth/2,screenHeight/2);
		setLocationByPlatform(true);
		
		//���ñ���
		setTitle("SizedFrame");
	}
}