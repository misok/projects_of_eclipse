package com.xiaoma.chapter.eight;
import java.awt.*;
import javax.swing.*;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ButtonTest {

	/**
	 * @param args
	 * 8-1
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				ButtonFrame bframe=new ButtonFrame();
			    bframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			    bframe.setVisible(true);
			}
			
		});
		

	}

}
class ButtonFrame extends JFrame{
	public static final int DEFAULT_HEIGHT=300;
	public static final int DEFAULT_WIDTH=200;
	private JPanel buttonPanel;
	public ButtonFrame()
	{
		setTitle("BUTTONTEST");
		setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		buttonPanel=new JPanel();
		//创建button
		JButton yButton=new JButton("yellow");
		JButton bButton=new JButton("blue");
		JButton rButton=new JButton("red");
		
		//添加到panel
		buttonPanel.add(yButton);
		buttonPanel.add(bButton);
		buttonPanel.add(rButton);
		
		add(buttonPanel);
		//给按钮添加事件
		ColorAction yAction=new ColorAction(Color.YELLOW);
		ColorAction bAction=new ColorAction(Color.blue);
		ColorAction rAction=new ColorAction(Color.RED);
		
		yButton.addActionListener(yAction);
		bButton.addActionListener(bAction);
		rButton.addActionListener(rAction);
		
	}
		
		//事件监听类
		private class ColorAction implements ActionListener{
			private Color backgroundColor;
			public ColorAction(Color c)
			{
				backgroundColor=c;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				buttonPanel.setBackground(backgroundColor);
			}
		}
		
		
	
	
	
	
}