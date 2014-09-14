package com.xiaoma.chapter.five;

import java.util.Arrays;

public class EmployeeSortTest {

	/**
	 * @param args
	 * this program demonstrtes the use of Comparable interface
	 * @author Administrator
	 * 6-1
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Employee[]staff=new Employee[3];
		staff[0]=new Employee("xiaoma",350000);
		staff[1]=new Employee("xiaoma1",200);
		staff[2]=new Employee("xiaoma2",550000);
		Arrays.sort(staff);
		for(Employee e:staff)
			System.out.println("name="+e.getName()+",salary="+e.getSalary());

	}

}
class Employee implements Comparable<Employee>{
	private String name;
	private double salary;
	//private int hireDay;
	
	public Employee(String name,double salary){
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
		salary+=raise;
	}
	@Override
	public int compareTo(Employee other) {
		// TODO Auto-generated method stub
		if (salary<other.salary) return -1;
		if(salary>other.salary) return 1;
		return 0;
		
	}
	
}