package com.xiaoma;

public class �����㷨 {

	/**
	 * @param args
	 * @author С��
	 * @date��14.2.13
	 * @���ܣ����ֲ���
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
		//�����ҵ��м����
		int midIndex=(rightIndex+leftIndex)/2;
		int midVal=arr[midIndex];
		
		if(rightIndex>=leftIndex)
		{
			//���Ҫ�ҵ�����midVal��
			if(midVal>val)
			{
				find(leftIndex,midIndex-1,val,arr);//�������
			
			}
			else if(midVal<val)
			{
				find(midIndex+1,rightIndex,val,arr);//���ұ���
			
			}
			else if(midVal==val)
			{
				System.out.println("�ҵ��ˣ�,�ǵ�"+(midIndex+1)+"����");
			
			}
		}
		
		System.out.println("�ܱ�Ǹ��û���ҵ�");
	}
	
	
}