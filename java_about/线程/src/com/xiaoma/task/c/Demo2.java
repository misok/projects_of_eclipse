package com.xiaoma.task.c;

public class Demo2 {

	/**
	 * @param args
	 * ��ʵ�ֽӿڵķ���д�߳�
	 * a.��дһ�����򣬸ó���ÿ��һ���ڿ���̨���hello world ���ʮ��
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub. 
		Show show =new Show();
		Thread t = new Thread(show);//�ýӿڵķ���ʵ�����ķ�����ͬ���ӿ���û��strat����
		t.start();

	}

}
class Show implements Runnable{
	//��дrun����
	int times=0;
	public void run(){
		
		while(true)
		{
			System.out.println("Hello World!"+times);
			try 
			{
				Thread.sleep(1000);//���ߣ��߳̽�������״̬ ���ͷ���Դ��1000�ĵ�λ��ms
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