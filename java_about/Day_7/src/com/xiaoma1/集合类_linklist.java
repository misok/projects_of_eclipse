package com.xiaoma1;
import java.util.*;

public class ������_linklist {

	/**
	 * @param args
	 * @author Administrator
	 * @date��14.2.18
	 * @���ܣ���ʾlinklist��
	 * @���ܣ���ʾvector����÷�
	 * @���ܣ���ʾstack���÷�
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedList ll=new LinkedList();
		Clerk clerk=new Clerk("aa", 50, 10000);
		Clerk clerk1=new Clerk("bb", 50, 10000);
		ll.addFirst(clerk);
		ll.addFirst(clerk1);//�Ѷ�������������ǰ��,��ջ
		for(int i=0;i<ll.size();i++)
		{
			System.out.println(((Clerk)ll.get(i)).getName());//bb aa
		}
		//addlast�����Ǽ�������棬�����
		//removeall������ɾ������
		
		
		//vector���÷�
		Vector vv=new Vector();
		Clerk clerk2=new Clerk("cc", 50, 10000);
		vv.add(clerk2);
		for(int i=0;i<vv.size();i++)
		{
			System.out.println(((Clerk)vv.get(i)).getName());
		}
		
		//stack��ÿ�ΰѶ������ǰ��

	}

}
