package com.xiaoma.io.记事本实现;
import java.io.*;
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
/**
 * @param args
 * 记事本读写功能的实现
 * 界面加功能
 * 2014/8/13
 */
public class textBook extends JFrame implements ActionListener{

	//定义需要的组件
	JTextArea jta=null;//文本域
	//定义菜单条
	JMenuBar jmb=null;
	JMenu jm1=null;
	JMenuItem jmi1=null;
	JMenuItem jmi2=null;
	
	//构造器
	public textBook()
	{
		jta=new JTextArea();
		jmb=new JMenuBar();
		jm1=new JMenu("开始(o)");
		//设置助记符
		jm1.setMnemonic('F');
		jmi1=new JMenuItem("打开",new ImageIcon("new.jpg"));
		//注册监听
		jmi1.addActionListener(this);
		jmi1.setActionCommand("Open");	
		jmi2=new JMenuItem("保存");
		jmi2.addActionListener(this);
		jmi2.setActionCommand("Save");
		//加入
		this.setJMenuBar(jmb);
		//把jm1放入jmb
		jmb.add(jm1);
		//把ITEM放入到menu
		jm1.add(jmi1);
		jm1.add(jmi2);
		//放入JFrame
		this.add(jta);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400,300);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		textBook tb=new textBook();

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//判断是哪个菜单被选中
		if(e.getActionCommand().equals("Open"))
		{
			//System.out.println("Open");
			//文件选择JFileChooser
			JFileChooser jfc1=new JFileChooser();
			//设置名字
			jfc1.setDialogTitle("请选择文件");
			//默认方式
			jfc1.showOpenDialog(null);
			//显示
			jfc1.setVisible(true);
			//要得到用户选择的文件路径
			String filename=jfc1.getSelectedFile().getAbsolutePath();
			FileReader fr=null;
			BufferedReader br=null;
			try {
				fr=new FileReader(filename);
				br=new BufferedReader(fr);
				//从文件中读取信息并显示到文本域jta
				String s="";
				String allCon="";
				while((s=br.readLine())!=null)
				{
					//显示到文本域
					allCon+=s+"\r\n";
				}
				//放置到jta即可
				jta.setText(allCon);
			} catch (Exception e2) {
				// TODO: handle exception
			}finally{
				try {
					br.close();
					fr.close();
				} catch (Exception e3) {
					// TODO: handle exception
				}
			}
			
			
		}
		else if(e.getActionCommand().equals("Save"))
		{
			//出现保存对话框
			JFileChooser jfc2=new JFileChooser();
			jfc2.setDialogTitle("另存为");
			jfc2.showSaveDialog(null);
			jfc2.setVisible(true);
			
			//得到用户希望保存到的路径
			String filename2=jfc2.getSelectedFile().getAbsolutePath();
			//准备写入到指定文件
			FileWriter fw=null;
			BufferedWriter bw=null;
			try {
				fw=new FileWriter(filename2);
				bw=new BufferedWriter(fw);
				bw.write(this.jta.getText());//可优化
			} catch (Exception e2) {
				// TODO: handle exception
			}finally{
				try {
					bw.close();
					fw.close();
				} catch (Exception e3) {
					// TODO: handle exception
				}
			}
		}
	}
	

}
