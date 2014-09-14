package com.xiaoma1;
import java.util.*;

public class 集合类 {

	/**
	 * @param args
	 * @author Administrator
	 * @date；14.2.18
	 * @功能：演示集合类之arraylist
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList al=new ArrayList();
		System.out.println("a1的大小："+al.size());
		//向a1中加入数据（类型是object）
		//创建一个职员
		Clerk clerk1=new Clerk("宋江",50,1000);
		Clerk clerk2=new Clerk("吴用",45,1200);
		//将职员类加入到a1中
		al.add(clerk1);//加到数列最后
		al.add(clerk2);
		al.add(clerk1);//可不可以再次放入同一个人/可以
		System.out.println("a1的大小："+al.size());
		//如何访问al中的对象
		//访问第一个对象
		Clerk temp=(Clerk)al.get(0);		
		System.out.println("第一个人的名字是："+temp.getName());
		Clerk temp1=(Clerk)al.get(1);
		System.out.println("第二个人的名字是："+temp1.getName());
		//遍历al所有的对象
		for(int i=0;i<al.size();i++)
		{
			Clerk temp3=(Clerk)al.get(i);
			System.out.println("第"+(i+1)+"个人的名字是："+temp3.getName());
		}
		
		//如何删除一个对象
		al.remove(1);
		System.out.println("删除吴用");
		for(int i=0;i<al.size();i++)
		{
			Clerk temp3=(Clerk)al.get(i);
			System.out.println("第"+(i+1)+"个人的名字是："+temp3.getName());
		}
	}

}
class Clerk{
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public float getSal() {
		return sal;
	}
	public void setSal(float sal) {
		this.sal = sal;
	}
	private int age;
	private float sal;
	public Clerk(String name,int age,float sal)
	{
		this.name=name;
		this.age=age;
		this.sal=sal;
	
	}
	
}
