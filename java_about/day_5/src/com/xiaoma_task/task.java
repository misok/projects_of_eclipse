package com.xiaoma_task;

public class task {

	/**
	 * @param args
	 * @author С��
	 * @date 14.2.10
	 * @���ܣ���ҵ��
	 * 1.Լɪ�����⣺����Ϊ1.2....n��n����Χ��һȦ��Լ�����Ϊk���˴�1��ʼ����������m���Ǹ��˳��У�������һλ�ִ�1��ʼ����������m���˳��У�
	 * �Դ����ƣ�ֱ�������˳��У��ɴ˲���һ�����ӱ�����С�
	 * ��ʾ����һ������ͷ����ѭ��������
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CycLink cyclink=new CycLink();
		cyclink.setLen(5);
		cyclink.createLink();
		cyclink.setK(2);
		cyclink.setM(2);
		cyclink.show();
		cyclink.play();

	}

}
class Node//���
{
	int no;
	Node nextNode;
	public Node(int no)
	{
		this.no=no;
	}
}
//��������
class CycLink
{
	Node firstNode=null;//�ҵ���һ���������ã�ָ���һ��С�������ò��ܶ�
	Node temp=null;
	int len=0;//���ж��ٽ��
	int k=0;
	int m=0;
	
	//���������С
	public void setLen(int len)
	{
		this.len=len;
	}
	//��ʼ��Ϸ

		//���ôӵڼ����˿�ʼ����
	public void setK(int k)
	{
		this.k=k;
	}
	
	//����m
	public void setM(int m)
	{
		this.m=m;
	}
	public void play()
	{
		Node temp=this.firstNode;
		//1.���ҵ���ʼ��������
		for(int i=1;i<k;i++)//�Լ�Ҳ��һ�£�����k��ȡ��
		{
			temp=temp.nextNode;
		}
		
		//2.��M��
		while(this.len!=1)
		{
			for(int j=1;j<m;j++)
		    {
			    temp=temp.nextNode;
		    }
		
		     //3.������m��С�����˳�Ȧ
		    Node temp2=temp;
		    while(temp2.nextNode!=temp)
		    {
			     temp2=temp2.nextNode;
		     }//�ҵ�Ҫ��Ȧ��ǰһ��С��
		
		    temp2.nextNode=temp.nextNode;
		    temp=temp.nextNode;
		    
		    this.len--;
		}
		//���һ��С��
		System.out.println("����Ȧ��"+temp.no);
	}
	

	
	//��ʼ����������
	public void createLink()
	{
		for(int i=1;i<=len;i++)
		{
			if(i==1){//������һ�����
				Node node=new Node(i);
			    this.firstNode=node;
			    this.temp=node;
			}
			else{
				if(i==len){
					//�������һ�����
					Node node=new Node(i);
					temp.nextNode=node;	
					temp=node;
					temp.nextNode=this.firstNode;
				}else{
					Node node=new Node(i);
				    temp.nextNode=node;	
				    temp=node;
				}
				
			}
		}
	}
	//��ӡ��������
	public void show()
	{
		Node temp=this.firstNode;
		do{
			System.out.println(temp.no+" ");
			temp=temp.nextNode;
			
		}while(temp!=this.firstNode);
	}
}