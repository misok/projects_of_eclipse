package com.xiaoma1;
import java.util.*;

public class ������ {

	/**
	 * @param args
	 * @author Administrator
	 * @date��14.2.18
	 * @���ܣ���ʾ������֮arraylist
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList al=new ArrayList();
		System.out.println("a1�Ĵ�С��"+al.size());
		//��a1�м������ݣ�������object��
		//����һ��ְԱ
		Clerk clerk1=new Clerk("�ν�",50,1000);
		Clerk clerk2=new Clerk("����",45,1200);
		//��ְԱ����뵽a1��
		al.add(clerk1);//�ӵ��������
		al.add(clerk2);
		al.add(clerk1);//�ɲ������ٴη���ͬһ����/����
		System.out.println("a1�Ĵ�С��"+al.size());
		//��η���al�еĶ���
		//���ʵ�һ������
		Clerk temp=(Clerk)al.get(0);		
		System.out.println("��һ���˵������ǣ�"+temp.getName());
		Clerk temp1=(Clerk)al.get(1);
		System.out.println("�ڶ����˵������ǣ�"+temp1.getName());
		//����al���еĶ���
		for(int i=0;i<al.size();i++)
		{
			Clerk temp3=(Clerk)al.get(i);
			System.out.println("��"+(i+1)+"���˵������ǣ�"+temp3.getName());
		}
		
		//���ɾ��һ������
		al.remove(1);
		System.out.println("ɾ������");
		for(int i=0;i<al.size();i++)
		{
			Clerk temp3=(Clerk)al.get(i);
			System.out.println("��"+(i+1)+"���˵������ǣ�"+temp3.getName());
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
