package com.xiaoma;
/*
 * @���ߣ�С��
 * @date��14.2.11
 * @���ܣ��ӿ�
 */

public class Demo_1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(Usb.a);//���ʽӿ��еı���
		
		Computer computer=new Computer();
		Camera camera1=new Camera();
		Phone phone1=new Phone();
		computer.useUsb(camera1);
		computer.useUsb(phone1);

	}

}
//usb�ӿ�
interface Usb{
	int a=1;//�ӿ��еı���
	//������������
	public void start();
	public void stop();
	
}
//�������ʵ��usb�ӿ�
//һ����Ҫԭ��һ����ʵ��һ���ӿ�ʱ��Ҫ�����ʵ�ָ�������з���
class Camera implements Usb{
	public void start(){
		System.out.println("�����������ʼ����");
		
	}
	public void stop(){
		System.out.println("���������ֹͣ����");
	}
}
//�ֻ���
class Phone implements Usb{
	public void start(){
		System.out.println("�����ֻ�����ʼ����");
		
	}
	public void stop(){
		System.out.println("�����ֻ���ֹͣ����");
	}
}
class Computer{
	//ʹ��usb�ӿ�
	public void useUsb(Usb usb){//�ӿ��ڴ˴������˶�̬
		usb.start();
		usb.stop();
	}
}



	