package com.xiaoma.task.c;

public class Demo2 {

	/**
	 * @param args
	 * 用实现接口的方法写线程
	 * a.编写一个程序，该程序每隔一秒在控制台输出hello world 输出十次
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub. 
		Show show =new Show();
		Thread t = new Thread(show);//用接口的方法实例化的方法不同，接口类没有strat方法
		t.start();

	}

}
class Show implements Runnable{
	//重写run函数
	int times=0;
	public void run(){
		
		while(true)
		{
			System.out.println("Hello World!"+times);
			try 
			{
				Thread.sleep(1000);//休眠，线程进入阻塞状态 ，释放资源，1000的单位是ms
			} catch (Exception e)
			{
				// TODO: handle exception
			}
			times++;
			if(times==10)
			{
				break;
			}
		}
		
	}
}