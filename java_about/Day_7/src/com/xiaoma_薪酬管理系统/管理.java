package com.xiaoma_薪酬管理系统;
import java.util.*;
import java.io.*;

public class 管理 {

	/**
	 * @param args
	 * @author 小马
	 * @date：14.2.18
	 * @功能：薪水管理系统
	 * 当有新员工时，将该员工加入系统
	 * 可以根据员工号，显示该员工信息
	 * 可以显示所有员工信息
	 * 可以修改员工的薪水
	 * 当员工离职时，将该员工从管理系统中删除
	 * 可以按照薪水从低到高顺序排序
	 * 可以统计员工的平均工资和最低最高工资
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//创建empManage对象
		EmpManage em=new EmpManage();
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		//做出一个建议菜单
		while(true)
		{
			System.out.println("请选择：");
			System.out.println("1.添加雇员");
			System.out.println("2.查找雇员");
			System.out.println("3.修改一个雇员的工资");
			System.out.println("4.删除一个雇员");
			System.out.println("5.退出系统");
			String operType=br.readLine();
			if(operType.equals("1"))
			{
				System.out.println("请输入编号：");
				String empNo=br.readLine();
				System.out.println("请输入名字：");
				String name=br.readLine();
				System.out.println("请输入工资：");
				float sal=Float.parseFloat(br.readLine());
				Emp emp=new Emp(empNo, name, sal);
				em.addEmp(emp);
			}
			else if(operType.equals("2"))
			{
				System.out.println("请输入编号：");
				String empNo=br.readLine();
				em.showInfo(empNo);
			}
			else if(operType.equals("3"))
			{
				//em.updateSal(empNo, newSal)
			}
			else if(operType.equals("4"))
			{
				
			}
			else if(operType.equals("5"))
			{
				System.exit(0);//exit,静态函数。报0整除退出。-1要检查
			}
		}
		

	}

}
//雇员管理类
class EmpManage{
	private ArrayList al=null;
	public EmpManage()
	{
		al=new ArrayList();//构造函数，初始化成员变量
		
	}
	//加入员工
	public void addEmp(Emp emp)
	{
		al.add(emp);
	}
	//显示员工相关信息
	public void showInfo(String empNo)
	{
		//遍历整个Arraylist
		for(int i=0;i<al.size();i++)
		{
			//取出Emp对象
			Emp emp=(Emp)al.get(i);
			//比较编号
			if(emp.getEmpNo().equals(empNo))//比较字符串不能直接等
			{
				System.out.println("找到该员工，他的信息是：");
				System.out.println("编号是"+empNo);
				System.out.println("名字是"+emp.getName());
				System.out.println("工资是"+emp.getSal());
			}
		}
	}
	public  void showAll()
	{
		for(int i=0;i<al.size();i++)
		{
			//取出Emp对象
			Emp emp1=(Emp)al.get(i);
			//打印员工信息
			System.out.println("第"+(i+1)+"个员工的编号是"+emp1.getEmpNo());
			System.out.println("第"+(i+1)+"个员工的名字是"+emp1.getName());
			System.out.println("第"+(i+1)+"个员工的工资是"+emp1.getSal());
			System.out.println();
			
		}
	}
	//修改工资
	public void updateSal(String empNo,float newSal)
	{
		for(int i=0;i<al.size();i++)
		{
			Emp emp=(Emp)al.get(i);
			if(emp.getEmpNo().equals(empNo))
			{
				emp.setSal(newSal);
			}
		}
	}
    //删除员工
	public void deleteEmp(String empNo)
	{
		for(int i=0;i<al.size();i++)
		{
			Emp emp=(Emp)al.get(i);
			if(emp.getEmpNo().equals(empNo))
			{
				al.remove(i);//al.remove(emp);
			}
		}
	}
	
	
}
//雇员类
class Emp{
	private String empNo;//工号
	private String name;//姓名
	private float sal;//工资
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getSal() {
		return sal;
	}
	public void setSal(float sal) {
		this.sal = sal;
	}
	
	//构造函数
	public Emp(String empNo,String name,float sal)
	{
		this.empNo=empNo;
		this.name=name;
		this.sal=sal;
		
	}
}
