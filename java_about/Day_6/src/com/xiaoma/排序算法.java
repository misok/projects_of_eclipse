package com.xiaoma;
import java.util.*;

public class �����㷨 {

	/**
	 * @param args
	 * @author  С��
	 * @date��14.2.12
	 * @���ܣ����������㷨/���ں������������
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int len=100000;
		int arr1[]=new int[len];
		for(int i=0;i<len;i++)
		{
			//�ó����������1-10000����
			//Math���������һ��0-1����
			int t=(int)(Math.random()*10000);
			arr1[i]=t;
		}
		//int arr[]={1,6,0,-1,9};
	    //����һ��Bubble��
		/*Bubble bubble=new Bubble();
		Calendar cal=Calendar.getInstance();
		System.out.println("����ǰ"+cal.getTime());
		bubble.sort(arr1);
		Calendar cal1=Calendar.getInstance();
		System.out.println("�����"+cal1.getTime());//ð��32��
		/*for(int i=0;i<arr.length;i++){
			System.out.println(arr[i]+" ");
		}*/
		/*int a=12;
		bubble.test(a);
		System.out.println(a);//���������Ͳ������ݲ��ı���ֵ
		*/
		//����һ��select��
		/*Select select=new Select();
		//������ǰ��ӡϵͳʱ��
		Calendar cal=Calendar.getInstance();
		System.out.println("����ǰ"+cal.getTime());
		select.sort(arr1);
		//���µõ�ʵ����ʱ�亯���ǵ�̬��
		Calendar cal1=Calendar.getInstance();
		System.out.println("�����"+cal1.getTime());//ѡ��10��
		/*for(int i=0;i<arr.length;i++){
			System.out.println(arr[i]+" ");
		}*/
		
		//����һ��insert��
		/*InsertSort insert=new InsertSort();
		Calendar cal=Calendar.getInstance();
		System.out.println("����ǰ"+cal.getTime());
		insert.sort(arr1);
		Calendar cal1=Calendar.getInstance();
		System.out.println("�����"+cal1.getTime());//��������
		/*for(int i=0;i<arr1.length;i++){
			System.out.println(arr1[i]+" ");
		}*/
		
		//����һ��quick��
		QuickSort quick=new QuickSort();
		Calendar cal=Calendar.getInstance();
		System.out.println("����ǰ"+cal.getTime());
		quick.sort(0, arr1.length-1, arr1);
		Calendar cal1=Calendar.getInstance();
		System.out.println("�����"+cal1.getTime());//���Ų���һ��
		
	}
}
//��װ
class Bubble{//--ð������
	/*public void test(int a){
		a++;
	}*/
	public void sort(int arr[]){
		int temp=0;
		//����
		//���ѭ���������ż���n-1
		for(int i=0;i<arr.length-1;i++){
			//�ڲ�ѭ������Ƚϣ��������ǰһ�����Ⱥ�һ�������򽻻�
			for(int j=0;j<arr.length-1-i;j++){
				if(arr[j]>arr[j+1])
				{
					//��λ
					temp=arr[j];
					arr[j]=arr[j+1];
					arr[j+1]=temp;
				}
			}
		}
	}
}
class Select{//ѡ������
	public void sort(int arr[])
	{
		//��Ϊ��һ��������С��
		int temp=0;
		for(int j=0;j<arr.length-1;j++)
		{
			int min=arr[j];
		    int minIndex=j;//��С�����±�
		    for(int k=j+1;k<arr.length;k++)
		    {
		    	if(min>arr[k])
		    	{
		    		min=arr[k];
		    		minIndex=k;
		    	}
		    }//���˳�ʱ���ҵ��˴���Сֵ
		    temp=arr[j];
		    arr[j]=arr[minIndex];
		    arr[minIndex]=temp;
		}
	}
}
class InsertSort{//��������
	public void sort(int arr[])
	{
		for(int i=1;i<arr.length;i++)
		{
			int insertVal=arr[i];
			//׼����ǰһ�����Ƚ�
			int index=i-1;
			while(index>=0&&insertVal<arr[index])
			{
				//��arr[index]����ƶ�
				arr[index+1]=arr[index];
				index--;
			}
			//��inserVal���뵽�ʵ�λ��
			arr[index+1]=insertVal;
		}
	}
	
}
class QuickSort{//��������
	public void sort(int left,int right,int []arr)
	{
		int l=left;
		int r=right;
		int pivot=arr[(left+right)/2];
		int temp=0;
		
		while(l<r)
		{
			while(arr[l]<pivot) l++;
			while(arr[r]>pivot) r--;
			
			if(l>=r) break;
			
			temp=arr[l];
			arr[l]=arr[r];
			arr[r]=temp;
			
			if(arr[l]==pivot) --r;
			if(arr[r]==pivot) ++l;
			
			
		}
	}
}
