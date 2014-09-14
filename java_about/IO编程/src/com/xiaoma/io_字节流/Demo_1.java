package com.xiaoma.io_字节流;
import java.io.*;
public class Demo_1 {

	/**
	 * @param args
	 * 演示字节输出流
	 * 注：\r\n：换行
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File f=new File("e:\\Demo_1.txt");
		FileOutputStream fos=null;
		try {
			fos=new FileOutputStream(f);
			String b="马逸文逗比";
			fos.write(b.getBytes());//设计到字符串转字节的问题
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			fos.close();
		}
	}

}
