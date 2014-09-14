package com.xiaoma;

public class 查找算法 {

	/**
	 * @param args
	 * @author 小马
	 * @date：14.2.13
	 * @功能：二分查找
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int arr[]={2,5,7,12,56,78};
		for(int i=0;i<arr.length;i++)
		{
			System.out.print(arr[i]+" ");
		}
		BinaryFind bf=new BinaryFind();
		bf.find(0, arr.length-1, 3, arr);
		
		
		
		

	}

}
class BinaryFind
{
	public void find(int leftIndex,int rightIndex,int val,int  arr[])
	{
		//首先找到中间的数
		int midIndex=(rightIndex+leftIndex)/2;
		int midVal=arr[midIndex];
		
		if(rightIndex>=leftIndex)
		{
			//如果要找的数比midVal大
			if(midVal>val)
			{
				find(leftIndex,midIndex-1,val,arr);//在左边找
			
			}
			else if(midVal<val)
			{
				find(midIndex+1,rightIndex,val,arr);//在右边找
			
			}
			else if(midVal==val)
			{
				System.out.println("找到了！,是第"+(midIndex+1)+"个数");
			
			}
		}
		
		System.out.println("很抱歉，没有找到");
	}
	
	
}