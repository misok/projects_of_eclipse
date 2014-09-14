package com.xiaoma_task;

public class task {

	/**
	 * @param args
	 * @author 小马
	 * @date 14.2.10
	 * @功能：作业题
	 * 1.约瑟夫问题：设编号为1.2....n的n个人围坐一圈，约定编号为k的人从1开始报数，数到m的那个人出列，他的下一位又从1开始报数，数到m的人出列，
	 * 以此类推，直到所有人出列，由此产生一个出队编号序列。
	 * 提示：用一个不带头结点的循环链表处理
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
class Node//结点
{
	int no;
	Node nextNode;
	public Node(int no)
	{
		this.no=no;
	}
}
//环形链表
class CycLink
{
	Node firstNode=null;//找到第一个结点的引用，指向第一个小孩的引用不能动
	Node temp=null;
	int len=0;//共有多少结点
	int k=0;
	int m=0;
	
	//设置链表大小
	public void setLen(int len)
	{
		this.len=len;
	}
	//开始游戏

		//设置从第几个人开始数数
	public void setK(int k)
	{
		this.k=k;
	}
	
	//设置m
	public void setM(int m)
	{
		this.m=m;
	}
	public void play()
	{
		Node temp=this.firstNode;
		//1.先找到开始数数的人
		for(int i=1;i<k;i++)//自己也算一下，所以k不取等
		{
			temp=temp.nextNode;
		}
		
		//2.数M下
		while(this.len!=1)
		{
			for(int j=1;j<m;j++)
		    {
			    temp=temp.nextNode;
		    }
		
		     //3.将数到m的小孩，退出圈
		    Node temp2=temp;
		    while(temp2.nextNode!=temp)
		    {
			     temp2=temp2.nextNode;
		     }//找到要出圈的前一个小孩
		
		    temp2.nextNode=temp.nextNode;
		    temp=temp.nextNode;
		    
		    this.len--;
		}
		//最后一个小孩
		System.out.println("最后出圈："+temp.no);
	}
	

	
	//初始化环形链表
	public void createLink()
	{
		for(int i=1;i<=len;i++)
		{
			if(i==1){//创建第一个结点
				Node node=new Node(i);
			    this.firstNode=node;
			    this.temp=node;
			}
			else{
				if(i==len){
					//创建最后一个结点
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
	//打印环形链表
	public void show()
	{
		Node temp=this.firstNode;
		do{
			System.out.println(temp.no+" ");
			temp=temp.nextNode;
			
		}while(temp!=this.firstNode);
	}
}