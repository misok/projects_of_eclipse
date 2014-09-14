package com.xiaoma.chapter.four;
import java.util.*;
public class EmployeeTest {

	/**
	 * @param args
	 * this program test the Employee calss.
	 * @author xiaoma
	 * 4-2
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Employee[] staff=new Employee[3];
		staff [0]=new Employee("xiaoma",75000,2014,7,11);
		staff [1]=new Employee("doubi",60000,2014,7,11);
		staff [2]=new Employee("mayiwen",70000,2014,7,11);
		
		for(Employee e:staff)
			e.raiseSalary(5);
		for(Employee e:staff)
			System.out.println("name=" +e.getName()+",salary=" +e.getSalary()+",hireday=" +e.getHireday());
		

	}
	

}
class Employee{
	private String name;
	private double salary;
	private Date hireDay;
	
	public Employee(String name,double salary,int year,int month,int day){
		this.name=name;
		this.salary=salary;
		GregorianCalendar calendar=new GregorianCalendar(year,month-1,day);
		hireDay=calendar.getTime();
		
	}
	public String getName(){
		return name;
	}
	public double getSalary()
	{
		return salary;
	}
	 
	public Date getHireday()
	{
		return hireDay;
	}
	public void raiseSalary(double byPercent)
	{
		double raise=salary*byPercent/100;
		salary += raise;
	}
	
}