package com.xiaoma.task.d;

public class Demo3 {

	/**
	 * @param args
	 * ��дһ�����򣬸ó������һ������n�������̣߳�һ���̼߳���1+++n
        ��������������һ���߳�ÿ��һ���ڿ���̨���������һ���̣߳���������ڼ���helloworld ,
        Ҫ��ͬʱ����
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
		System.out.println("��ǰ�����"+res);
		if(i==n)
		{
			System.out.println("�������"+res);
			break;
		}
		}
		}
		
	
}

class Show implements Runnable{
	//��дrun����
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
				Thread.sleep(1000);//���ߣ��߳̽�������״̬ ���ͷ���Դ��1000�ĵ�λ��ms
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