package com.xiaoma;
/*
 * @作者：小马
 * @date：14.2.11
 * @功能：接口
 */

public class Demo_1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(Usb.a);//访问接口中的变量
		
		Computer computer=new Computer();
		Camera camera1=new Camera();
		Phone phone1=new Phone();
		computer.useUsb(camera1);
		computer.useUsb(phone1);

	}

}
//usb接口
interface Usb{
	int a=1;//接口中的变量
	//声明两个方法
	public void start();
	public void stop();
	
}
//照相机类实现usb接口
//一个重要原则，一个类实现一个接口时，要求该类实现该类的所有方法
class Camera implements Usb{
	public void start(){
		System.out.println("我是相机，开始工作");
		
	}
	public void stop(){
		System.out.println("我是相机，停止工作");
	}
}
//手机类
class Phone implements Usb{
	public void start(){
		System.out.println("我是手机，开始工作");
		
	}
	public void stop(){
		System.out.println("我是手机，停止工作");
	}
}
class Computer{
	//使用usb接口
	public void useUsb(Usb usb){//接口在此处体现了多态
		usb.start();
		usb.stop();
	}
}



	