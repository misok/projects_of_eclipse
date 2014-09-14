package com.xiaoma.io.���±�ʵ��;
import java.io.*;
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
/**
 * @param args
 * ���±���д���ܵ�ʵ��
 * ����ӹ���
 * 2014/8/13
 */
public class textBook extends JFrame implements ActionListener{

	//������Ҫ�����
	JTextArea jta=null;//�ı���
	//����˵���
	JMenuBar jmb=null;
	JMenu jm1=null;
	JMenuItem jmi1=null;
	JMenuItem jmi2=null;
	
	//������
	public textBook()
	{
		jta=new JTextArea();
		jmb=new JMenuBar();
		jm1=new JMenu("��ʼ(o)");
		//�������Ƿ�
		jm1.setMnemonic('F');
		jmi1=new JMenuItem("��",new ImageIcon("new.jpg"));
		//ע�����
		jmi1.addActionListener(this);
		jmi1.setActionCommand("Open");	
		jmi2=new JMenuItem("����");
		jmi2.addActionListener(this);
		jmi2.setActionCommand("Save");
		//����
		this.setJMenuBar(jmb);
		//��jm1����jmb
		jmb.add(jm1);
		//��ITEM���뵽menu
		jm1.add(jmi1);
		jm1.add(jmi2);
		//����JFrame
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
		//�ж����ĸ��˵���ѡ��
		if(e.getActionCommand().equals("Open"))
		{
			//System.out.println("Open");
			//�ļ�ѡ��JFileChooser
			JFileChooser jfc1=new JFileChooser();
			//��������
			jfc1.setDialogTitle("��ѡ���ļ�");
			//Ĭ�Ϸ�ʽ
			jfc1.showOpenDialog(null);
			//��ʾ
			jfc1.setVisible(true);
			//Ҫ�õ��û�ѡ����ļ�·��
			String filename=jfc1.getSelectedFile().getAbsolutePath();
			FileReader fr=null;
			BufferedReader br=null;
			try {
				fr=new FileReader(filename);
				br=new BufferedReader(fr);
				//���ļ��ж�ȡ��Ϣ����ʾ���ı���jta
				String s="";
				String allCon="";
				while((s=br.readLine())!=null)
				{
					//��ʾ���ı���
					allCon+=s+"\r\n";
				}
				//���õ�jta����
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
			//���ֱ���Ի���
			JFileChooser jfc2=new JFileChooser();
			jfc2.setDialogTitle("���Ϊ");
			jfc2.showSaveDialog(null);
			jfc2.setVisible(true);
			
			//�õ��û�ϣ�����浽��·��
			String filename2=jfc2.getSelectedFile().getAbsolutePath();
			//׼��д�뵽ָ���ļ�
			FileWriter fw=null;
			BufferedWriter bw=null;
			try {
				fw=new FileWriter(filename2);
				bw=new BufferedWriter(fw);
				bw.write(this.jta.getText());//���Ż�
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
