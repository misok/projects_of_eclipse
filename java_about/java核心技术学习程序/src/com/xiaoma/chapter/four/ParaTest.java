package com.xiaoma.chapter.four;

public class ParaTest {

	/**
	 * @param args  
	 * @author Administrator
	 * this program demonstrates 参数传递
	 * 方法不能修改基本数据类型的参数
	 * 方法可以改变一个对象参数的状态
	 * 方法不能实现让对象参数引用一个参数
	 * 4-4
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 测试：方法不能修改基本数据类型的参数
		System.out.println("Testing triplevalue:");
		double percent=10;
		

	}

}
class Employee3{
	private String name;
	private double salary;
	public Employee3(String name,double salary){
		this.name=name;
		this.salary=salary;
	}
	public String getName(){
		return name;
	}
	public double getSalary(){
		return salary;
	}
	public void raiseSalary(double byPercent){
		double raise=salary*byPercent/100;
		salary += raise;
	}
}