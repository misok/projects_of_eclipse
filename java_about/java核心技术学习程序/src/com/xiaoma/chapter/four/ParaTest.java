package com.xiaoma.chapter.four;

public class ParaTest {

	/**
	 * @param args  
	 * @author Administrator
	 * this program demonstrates ��������
	 * ���������޸Ļ����������͵Ĳ���
	 * �������Ըı�һ�����������״̬
	 * ��������ʵ���ö����������һ������
	 * 4-4
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// ���ԣ����������޸Ļ����������͵Ĳ���
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