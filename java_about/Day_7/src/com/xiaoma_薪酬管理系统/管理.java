package com.xiaoma_н�����ϵͳ;
import java.util.*;
import java.io.*;

public class ���� {

	/**
	 * @param args
	 * @author С��
	 * @date��14.2.18
	 * @���ܣ�нˮ����ϵͳ
	 * ������Ա��ʱ������Ա������ϵͳ
	 * ���Ը���Ա���ţ���ʾ��Ա����Ϣ
	 * ������ʾ����Ա����Ϣ
	 * �����޸�Ա����нˮ
	 * ��Ա����ְʱ������Ա���ӹ���ϵͳ��ɾ��
	 * ���԰���нˮ�ӵ͵���˳������
	 * ����ͳ��Ա����ƽ�����ʺ������߹���
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//����empManage����
		EmpManage em=new EmpManage();
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		//����һ������˵�
		while(true)
		{
			System.out.println("��ѡ��");
			System.out.println("1.��ӹ�Ա");
			System.out.println("2.���ҹ�Ա");
			System.out.println("3.�޸�һ����Ա�Ĺ���");
			System.out.println("4.ɾ��һ����Ա");
			System.out.println("5.�˳�ϵͳ");
			String operType=br.readLine();
			if(operType.equals("1"))
			{
				System.out.println("�������ţ�");
				String empNo=br.readLine();
				System.out.println("���������֣�");
				String name=br.readLine();
				System.out.println("�����빤�ʣ�");
				float sal=Float.parseFloat(br.readLine());
				Emp emp=new Emp(empNo, name, sal);
				em.addEmp(emp);
			}
			else if(operType.equals("2"))
			{
				System.out.println("�������ţ�");
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
				System.exit(0);//exit,��̬��������0�����˳���-1Ҫ���
			}
		}
		

	}

}
//��Ա������
class EmpManage{
	private ArrayList al=null;
	public EmpManage()
	{
		al=new ArrayList();//���캯������ʼ����Ա����
		
	}
	//����Ա��
	public void addEmp(Emp emp)
	{
		al.add(emp);
	}
	//��ʾԱ�������Ϣ
	public void showInfo(String empNo)
	{
		//��������Arraylist
		for(int i=0;i<al.size();i++)
		{
			//ȡ��Emp����
			Emp emp=(Emp)al.get(i);
			//�Ƚϱ��
			if(emp.getEmpNo().equals(empNo))//�Ƚ��ַ�������ֱ�ӵ�
			{
				System.out.println("�ҵ���Ա����������Ϣ�ǣ�");
				System.out.println("�����"+empNo);
				System.out.println("������"+emp.getName());
				System.out.println("������"+emp.getSal());
			}
		}
	}
	public  void showAll()
	{
		for(int i=0;i<al.size();i++)
		{
			//ȡ��Emp����
			Emp emp1=(Emp)al.get(i);
			//��ӡԱ����Ϣ
			System.out.println("��"+(i+1)+"��Ա���ı����"+emp1.getEmpNo());
			System.out.println("��"+(i+1)+"��Ա����������"+emp1.getName());
			System.out.println("��"+(i+1)+"��Ա���Ĺ�����"+emp1.getSal());
			System.out.println();
			
		}
	}
	//�޸Ĺ���
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
    //ɾ��Ա��
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
//��Ա��
class Emp{
	private String empNo;//����
	private String name;//����
	private float sal;//����
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
	
	//���캯��
	public Emp(String empNo,String name,float sal)
	{
		this.empNo=empNo;
		this.name=name;
		this.sal=sal;
		
	}
}
