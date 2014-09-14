package System;
/*
 * ������Ա��ʱ������Ա������ϵͳ
	 * ���Ը���Ա���ţ���ʾ��Ա����Ϣ
	 * ������ʾ����Ա����Ϣ
	 * �����޸�Ա����нˮ
	 * ��Ա����ְʱ������Ա���ӹ���ϵͳ��ɾ��
	 * ���԰���нˮ�ӵ͵���˳������
	 * ����ͳ��Ա����ƽ�����ʺ������߹���
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
			System.out.println("��ѡ��");
			System.out.println("1.��ӹ�Ա");
			System.out.println("2.���ҹ�Ա");
			System.out.println("3.�޸�һ����Ա�Ĺ���");
			System.out.println("4.ɾ��һ����Ա");
			System.out.println("5.��ʾ����Ա����Ϣ");
			System.out.println("6.����нˮ����");
			System.out.println("7.�˳�ϵͳ");
			String operType=br.readLine();
			if(operType.equals("1"))
			{
				System.out.println("���������֣�");
				String empNo=br.readLine();
				System.out.println("�������ţ�");
				String name=br.readLine();
				System.out.println("�����빤�ʣ�");
				double salary=Double.parseDouble(br.readLine());
				Employee emp=new Employee(empNo, name, salary);
				me.addEmp(emp);
			}
			else if(operType.equals("2"))
			{
				System.out.println("�������ţ�");
				String empNo=br.readLine();
				me.showMessage(empNo);
			}
			else if(operType.equals("3"))
			{
				
				System.out.println("��������Ҫ�޸Ĺ��ʵĹ�Ա�ı�ţ�");
				String empNo=br.readLine();
				System.out.println("�������µĹ�����");
				double newSla=Double.parseDouble(br.readLine());
				me.setSla(empNo, newSla);
			}
			else if(operType.equals("4"))
			{
				System.out.println("��������Ҫɾ���Ĺ�Ա�ı�ţ�");
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
				System.exit(0);//exit,��̬��������0�����˳���-1Ҫ���
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
	public void addEmp(Employee emp)//����Ա��
	{
		al.add(emp);
	}
	
	public  void showMessage(String empNo)//����ĳһԱ����Ϣ
	{
		for(int i=0;i<al.size();i++)
		{
			Employee emp=(Employee)al.get(i);
			if(emp.getEmpNo().equals(empNo))
			{
				System.out.println("�ҵ���Ա����������Ϣ�ǣ�");
				System.out.println("�����"+empNo);
				System.out.println("������"+emp.getName());
				System.out.println("������"+emp.getSalary());
			}
		}
	}
	
	public void showAll()//��ʾ����Ա����Ϣ
	{
		for(int i=0;i<al.size();i++)
		{
			//ȡ��Emp����
			Employee emp=(Employee)al.get(i);
			//��ӡԱ����Ϣ
			System.out.println("��"+(i+1)+"��Ա���ı����"+emp.getEmpNo());
			System.out.println("��"+(i+1)+"��Ա����������"+emp.getName());
			System.out.println("��"+(i+1)+"��Ա���Ĺ�����"+emp.getSalary());
			System.out.println();
		}
	}
	
	public void setSla(String empNo,double newSla)//�޸�Ա����нˮ
	{
		for(int i=0;i<al.size();i++)
		{
			Employee emp=(Employee)al.get(i);
			if(emp.getEmpNo().equals(empNo))
			{
				emp.setSalary(newSla);
			}
			
		}
		System.out.println("�޸���ɣ�");
	}
	
	public void deleteEmp(String empNo)//ɾ����Ա��Ϣ
	{
		for(int i=0;i<al.size();i++)
		{
			Employee emp=(Employee)al.get(i);
			if(emp.getEmpNo().equals(empNo))
			{
				al.remove(i);
			}
			
		}
		System.out.println("ɾ���ɹ���");
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
		
		System.out.println("�����");
		for(int i=0;i<al2.size();i++)
		{
			//ȡ��Emp����
			Employee emp=(Employee)al2.get(i);
			//��ӡԱ����Ϣ
			System.out.println("��"+(i+1)+"��Ա���ı����"+emp.getEmpNo());
			System.out.println("��"+(i+1)+"��Ա����������"+emp.getName());
			System.out.println("��"+(i+1)+"��Ա���Ĺ�����"+emp.getSalary());
			System.out.println();
		}
		
		
	}
	
}