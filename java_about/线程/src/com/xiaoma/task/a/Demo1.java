package com.xiaoma.task.a;

public class Demo1 {

	/**
	 * @param args
	 * a.��дһ�����򣬸ó���ÿ��һ���ڿ���̨���hello world�����ʮ��
	 * 14/8/2
	 * ���߳���ʾ
	 * @throws InterruptedException 
	 * �ü̳�Thread�ķ���
	 */
	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		Show show=new Show();
		show.start();//�����̣߳��߳̽�������״̬������run����
		

	}

}

class Show extends Thread{
	//��дrun����
	public void run(){
		int times=0;
		while(true){
			System.out.println("Hello World!"+times);
			try {
				Thread.sleep(1000);//���ߣ��߳̽�������״̬ ���ͷ���Դ��1000�ĵ�λ��ms
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