package com.xiaoma.task.d;

public class Demo3 {

	/**
	 * @param args
	 * 编写一个程序，该程序接受一个整数n，创建线程，一个线程计算1+++n
        并输出结果，另外一个线程每隔一秒在控制台输出：我是一个线程，正在输出第几个helloworld ,
        要求同时进行
	 * 14/8/2
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calculate cal=new Calculate(10);
		Show show=new Show(10);
		Thread t1=new Thread(cal);
		Thread t2=new Thread(show);
		t1.start();
		t2.start();
		

	}

}
class Calculate implements Runnable
{
	int n=0;
	int res;
	int i=0;
	public  Calculate(int n)
	{
		this.n=n;
	}
	@Override
	public void run() 
	{
		while(true)
		{
			try 
			{
			// TODO Auto-generated method stub
			Thread.sleep(1000);
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
		}
			
		res+=(++i);
		System.out.println("当前结果是"+res);
		if(i==n)
		{
			System.out.println("最后结果是"+res);
			break;
		}
		}
		}
		
	
}

class Show implements Runnable{
	//重写run函数
	int times=0;
	int n=0;
	public Show(int n){
		this.n=n;
	}
	public void run(){
		
		while(true)
		{
			
			try 
			{
				Thread.sleep(1000);//休眠，线程进入阻塞状态 ，释放资源，1000的单位是ms
			} catch (Exception e)
			{
				// TODO: handle exception
			}
			times++;
			System.out.println("Hello World!"+times);
			if(times==n)
			{
				break;
			}
		}
		
	}
}