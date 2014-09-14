package com.xiaoma.chapter.four;

public class StaticTest {

	/**
	 * @param args
	 * this program demonstrats static method
	 * @author Administrator
	 * 2014/7/11
	 * 4-3
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Employee2[] staff=new Employee2[3];
		
		staff[0]=new Employee2("xiaoma",50000);
		staff[1]=new Employee2("doubi",60000);
		staff[2]=new Employee2("mayiwen",60000);
		
		//打印雇员所有信息
		for(Employee2 e:staff)
		{
			e.setId();
			System.out.println("name="+e.getName()+"salary="+e.getSalary()+"id="+e.getId());
		
		}
		//调用静态方法
		int n=Employee2.getNextId();//直接用类名调用
		System.out.println("next available id is "+n);
	}

}

class Employee2{
	private String name;
	private double salary;
	private int id;
	private static int nextId=1;
	
	public Employee2(String name,double salary){
		this.name=name;
		this.salary=salary;
		id=0;
	}
	public String getName()
	{
		return name;
	}
	public double getSalary(){
		return salary;
	}
	public int getId()
	{
		return id;
	}
	public void setId()
	{
		id=nextId;
		nextId++;
	}
	//静态方法
	public static int getNextId(){
		return nextId;
	}
	
	//单元测试，即在类里面写main方法直接测试
	public static void main(String[] args){
		Employee2 e=new Employee2("xiaoma",50000);
		System.out.println(e.getName()+" "+e.getSalary());
	}
}