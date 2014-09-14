package com.xiaoma;
import java.util.*;

public class 排序算法 {

	/**
	 * @param args
	 * @author  小马
	 * @date：14.2.12
	 * @功能：各种排序算法/日期函数，随机函数
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int len=100000;
		int arr1[]=new int[len];
		for(int i=0;i<len;i++)
		{
			//让程序随机产生1-10000的数
			//Math会随机产生一个0-1的数
			int t=(int)(Math.random()*10000);
			arr1[i]=t;
		}
		//int arr[]={1,6,0,-1,9};
	    //创建一个Bubble类
		/*Bubble bubble=new Bubble();
		Calendar cal=Calendar.getInstance();
		System.out.println("排序前"+cal.getTime());
		bubble.sort(arr1);
		Calendar cal1=Calendar.getInstance();
		System.out.println("排序后"+cal1.getTime());//冒泡32秒
		/*for(int i=0;i<arr.length;i++){
			System.out.println(arr[i]+" ");
		}*/
		/*int a=12;
		bubble.test(a);
		System.out.println(a);//简单数据类型参数传递不改变其值
		*/
		//创建一个select类
		/*Select select=new Select();
		//在排序前打印系统时间
		Calendar cal=Calendar.getInstance();
		System.out.println("排序前"+cal.getTime());
		select.sort(arr1);
		//重新得到实例。时间函数是单态的
		Calendar cal1=Calendar.getInstance();
		System.out.println("排序后"+cal1.getTime());//选择10秒
		/*for(int i=0;i<arr.length;i++){
			System.out.println(arr[i]+" ");
		}*/
		
		//创建一个insert类
		/*InsertSort insert=new InsertSort();
		Calendar cal=Calendar.getInstance();
		System.out.println("排序前"+cal.getTime());
		insert.sort(arr1);
		Calendar cal1=Calendar.getInstance();
		System.out.println("排序后"+cal1.getTime());//插入六秒
		/*for(int i=0;i<arr1.length;i++){
			System.out.println(arr1[i]+" ");
		}*/
		
		//创建一个quick类
		QuickSort quick=new QuickSort();
		Calendar cal=Calendar.getInstance();
		System.out.println("排序前"+cal.getTime());
		quick.sort(0, arr1.length-1, arr1);
		Calendar cal1=Calendar.getInstance();
		System.out.println("排序后"+cal1.getTime());//快排不到一秒
		
	}
}
//封装
class Bubble{//--冒泡排序
	/*public void test(int a){
		a++;
	}*/
	public void sort(int arr[]){
		int temp=0;
		//排序
		//外层循环，决定排几趟n-1
		for(int i=0;i<arr.length-1;i++){
			//内层循环逐个比较，如果发现前一个数比后一个数大，则交换
			for(int j=0;j<arr.length-1-i;j++){
				if(arr[j]>arr[j+1])
				{
					//换位
					temp=arr[j];
					arr[j]=arr[j+1];
					arr[j+1]=temp;
				}
			}
		}
	}
}
class Select{//选择排序
	public void sort(int arr[])
	{
		//认为第一个数是最小的
		int temp=0;
		for(int j=0;j<arr.length-1;j++)
		{
			int min=arr[j];
		    int minIndex=j;//最小数的下标
		    for(int k=j+1;k<arr.length;k++)
		    {
		    	if(min>arr[k])
		    	{
		    		min=arr[k];
		    		minIndex=k;
		    	}
		    }//当退出时就找到此次最小值
		    temp=arr[j];
		    arr[j]=arr[minIndex];
		    arr[minIndex]=temp;
		}
	}
}
class InsertSort{//插入排序
	public void sort(int arr[])
	{
		for(int i=1;i<arr.length;i++)
		{
			int insertVal=arr[i];
			//准备和前一个数比较
			int index=i-1;
			while(index>=0&&insertVal<arr[index])
			{
				//将arr[index]向后移动
				arr[index+1]=arr[index];
				index--;
			}
			//将inserVal插入到适当位置
			arr[index+1]=insertVal;
		}
	}
	
}
class QuickSort{//快速排序
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
