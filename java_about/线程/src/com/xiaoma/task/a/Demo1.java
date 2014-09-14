package com.xiaoma.task.a;

public class Demo1 {

	/**
	 * @param args
	 * a.编写一个程序，该程序每隔一秒在控制台输出hello world，输出十次
	 * 14/8/2
	 * 简单线程显示
	 * @throws InterruptedException 
	 * 用继承Thread的方法
	 */
	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		Show show=new Show();
		show.start();//启动线程，线程进入运行状态，运行run函数
		

	}

}

class Show extends Thread{
	//重写run函数
	public void run(){
		int times=0;
		while(true){
			System.out.println("Hello World!"+times);
			try {
				Thread.sleep(1000);//休眠，线程进入阻塞状态 ，释放资源，1000的单位是ms
			} catch (Exception e) {
				// TODO: handle exception
			}
			times++;
			if(times==10){
				break;
			}
		}
		
	}
}