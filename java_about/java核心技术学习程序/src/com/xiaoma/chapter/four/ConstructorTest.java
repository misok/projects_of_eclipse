package com.xiaoma.chapter.four;

import java.util.Random;

public class ConstructorTest {

	/**
	 * @param args
	 * this program ������
	 * 4-5
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
class Employee4{
	private static int nextId;
	private int id;
	private String name;
	private double salary;
	
	public String getName() {
		return name;
	}
	
	public double getSalary() {
		return salary;
	}
	public int getId(){
		return id;
	}
	
	//�������ؽṹ��
	public Employee4(String name,double salary){
		this.name=name;
		this.salary=salary;
	}
	public Employee4(double salary){
		//���ñ�Ĺ�����
		this("Employee4#"+nextId,salary);
		
		
	}
	public Employee4(){
		
	}
	//��̬��ʼ����
	static
	{
		Random generator =new Random();
		nextId=generator.nextInt(1000);
	}
	//�����ʼ����
	{
		id=nextId;
		nextId++;
	}
}