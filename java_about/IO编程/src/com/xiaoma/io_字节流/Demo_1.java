package com.xiaoma.io_�ֽ���;
import java.io.*;
public class Demo_1 {

	/**
	 * @param args
	 * ��ʾ�ֽ������
	 * ע��\r\n������
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File f=new File("e:\\Demo_1.txt");
		FileOutputStream fos=null;
		try {
			fos=new FileOutputStream(f);
			String b="�����Ķ���";
			fos.write(b.getBytes());//��Ƶ��ַ���ת�ֽڵ�����
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			fos.close();
		}
	}

}
