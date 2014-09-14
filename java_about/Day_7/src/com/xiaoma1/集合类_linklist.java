package com.xiaoma1;
import java.util.*;

public class 集合类_linklist {

	/**
	 * @param args
	 * @author Administrator
	 * @date：14.2.18
	 * @功能：演示linklist类
	 * @功能：演示vector类的用法
	 * @功能：演示stack的用法
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedList ll=new LinkedList();
		Clerk clerk=new Clerk("aa", 50, 10000);
		Clerk clerk1=new Clerk("bb", 50, 10000);
		ll.addFirst(clerk);
		ll.addFirst(clerk1);//把对象加在链表的最前面,向栈
		for(int i=0;i<ll.size();i++)
		{
			System.out.println(((Clerk)ll.get(i)).getName());//bb aa
		}
		//addlast方法是加在最后面，像队列
		//removeall方法，删除所有
		
		
		//vector的用法
		Vector vv=new Vector();
		Clerk clerk2=new Clerk("cc", 50, 10000);
		vv.add(clerk2);
		for(int i=0;i<vv.size();i++)
		{
			System.out.println(((Clerk)vv.get(i)).getName());
		}
		
		//stack是每次把对象加在前面

	}

}
