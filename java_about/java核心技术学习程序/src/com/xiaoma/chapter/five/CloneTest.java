package com.xiaoma.chapter.five;

import java.util.Date;
import java.util.GregorianCalendar;

public class CloneTest {

	/**
	 * @param args
	 * this program dmonstrats cloning
	 * 6-2
	 * @author Administrator
	
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			Employee1 original=new Employee1("xiaoma",50000);
			original.setHireDay(2014, 7, 15);
			Employee1 copy=original.clone();
			copy.raiseSalary(10);
			copy.setHireDay(2014, 7, 16);
			System.out.println("original="+original);
			System.out.println("copy="+copy);
			
		}catch(CloneNotSupportedException e){
		e.printStackTrace();}
	
	}
}

	
	class Employee1 implements Cloneable{
	private String name;
	private double salary;
	private Date hireDay;
	
	public Employee1 (String name,double salary){
		this.name=name;
		this.salary=salary;
		this.hireDay=hireDay;
		
	}
	public Employee1 clone() throws  CloneNotSupportedException{
		Employee1 cloned=(Employee1)super.clone();
		cloned.hireDay=(Date)hireDay.clone();
		return cloned;
		
		
	}
	public void setHireDay(int year,int month,int day){
		Date newHireDay=new GregorianCalendar(year,month-1,day).getTime();
		hireDay.setTime(newHireDay.getTime());
	}
	public void raiseSalary(double byPercent){
		double raise=salary*byPercent/100;
		salary+=raise;
	}
	public String toString(){
	return "Employee[name="+name+",salary="+salary+",hireday="+hireDay+"]";
		
	}
}