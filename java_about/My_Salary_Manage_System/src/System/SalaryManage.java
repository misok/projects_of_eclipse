package System;
/*
 * 当有新员工时，将该员工加入系统
	 * 可以根据员工号，显示该员工信息
	 * 可以显示所有员工信息
	 * 可以修改员工的薪水
	 * 当员工离职时，将该员工从管理系统中删除
	 * 可以按照薪水从低到高顺序排序
	 * 可以统计员工的平均工资和最低最高工资
 * 
 *
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;





public class SalaryManage {
	
	public static void main(String[] args) throws Exception
	{
		ManageEmp me=new ManageEmp();
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		while(true)
		{
			System.out.println("请选择：");
			System.out.println("1.添加雇员");
			System.out.println("2.查找雇员");
			System.out.println("3.修改一个雇员的工资");
			System.out.println("4.删除一个雇员");
			System.out.println("5.显示所有员工信息");
			System.out.println("6.按照薪水排序");
			System.out.println("7.退出系统");
			String operType=br.readLine();
			if(operType.equals("1"))
			{
				System.out.println("请输入名字：");
				String empNo=br.readLine();
				System.out.println("请输入编号：");
				String name=br.readLine();
				System.out.println("请输入工资：");
				double salary=Double.parseDouble(br.readLine());
				Employee emp=new Employee(empNo, name, salary);
				me.addEmp(emp);
			}
			else if(operType.equals("2"))
			{
				System.out.println("请输入编号：");
				String empNo=br.readLine();
				me.showMessage(empNo);
			}
			else if(operType.equals("3"))
			{
				
				System.out.println("请输入需要修改工资的雇员的编号：");
				String empNo=br.readLine();
				System.out.println("请输入新的工资数");
				double newSla=Double.parseDouble(br.readLine());
				me.setSla(empNo, newSla);
			}
			else if(operType.equals("4"))
			{
				System.out.println("请输入需要删除的雇员的编号：");
				String empNo=br.readLine();
				me.deleteEmp(empNo);
			}
			else if(operType.equals("5"))
			{
				me.showAll();
			}
			else if(operType.equals("6"))
			{
				
				
				me.sortEmp();
				
			}
			else if(operType.equals("7"))
			{
				System.exit(0);//exit,静态函数。报0整除退出。-1要检查
			}
		}
	}
	

}


class Employee
{
	private String name;
    private String empNo;
    private double salary;
    public Employee(String name,String empNo,double salary)
    {
    	this.empNo=empNo;
    	this.name=name ;
    	this.salary=salary;
    }
    public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
    
    
    public String getName()
    {
    	return name;
    }
    public void setName(String name)
    {
    	this.name=name;
    }
}

class ManageEmp
{
	private ArrayList al=null;
	
	public ManageEmp()
	{
		al=new ArrayList();
	}
	public void addEmp(Employee emp)//加入员工
	{
		al.add(emp);
	}
	
	public  void showMessage(String empNo)//查找某一员工信息
	{
		for(int i=0;i<al.size();i++)
		{
			Employee emp=(Employee)al.get(i);
			if(emp.getEmpNo().equals(empNo))
			{
				System.out.println("找到该员工，他的信息是：");
				System.out.println("编号是"+empNo);
				System.out.println("名字是"+emp.getName());
				System.out.println("工资是"+emp.getSalary());
			}
		}
	}
	
	public void showAll()//显示所有员工信息
	{
		for(int i=0;i<al.size();i++)
		{
			//取出Emp对象
			Employee emp=(Employee)al.get(i);
			//打印员工信息
			System.out.println("第"+(i+1)+"个员工的编号是"+emp.getEmpNo());
			System.out.println("第"+(i+1)+"个员工的名字是"+emp.getName());
			System.out.println("第"+(i+1)+"个员工的工资是"+emp.getSalary());
			System.out.println();
		}
	}
	
	public void setSla(String empNo,double newSla)//修改员工的薪水
	{
		for(int i=0;i<al.size();i++)
		{
			Employee emp=(Employee)al.get(i);
			if(emp.getEmpNo().equals(empNo))
			{
				emp.setSalary(newSla);
			}
			
		}
		System.out.println("修改完成！");
	}
	
	public void deleteEmp(String empNo)//删除雇员信息
	{
		for(int i=0;i<al.size();i++)
		{
			Employee emp=(Employee)al.get(i);
			if(emp.getEmpNo().equals(empNo))
			{
				al.remove(i);
			}
			
		}
		System.out.println("删除成功！");
	}
	
	public void sortEmp()
	{
		 ArrayList al2=new ArrayList();
		 int min=0;
		 int num=al.size();
		 for(int j=0;j<num;j++)
			{
			    for(int k=0;k<al.size();k++)
			    {
			    	if(((Employee)al.get(min)).getSalary()>=((Employee)al.get(k)).getSalary())
			    	{
			    		min=k;	
			    	}
			    }
			    al2.add((Employee)al.get(min));
		    	al.remove(min);
		    	min=0;
			    
			}
		
		System.out.println("排序后");
		for(int i=0;i<al2.size();i++)
		{
			//取出Emp对象
			Employee emp=(Employee)al2.get(i);
			//打印员工信息
			System.out.println("第"+(i+1)+"个员工的编号是"+emp.getEmpNo());
			System.out.println("第"+(i+1)+"个员工的名字是"+emp.getName());
			System.out.println("第"+(i+1)+"个员工的工资是"+emp.getSalary());
			System.out.println();
		}
		
		
	}
	
}