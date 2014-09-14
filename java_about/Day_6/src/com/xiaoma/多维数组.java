package com.xiaoma;

public class 多维数组 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a[][]=new int [4][6];
		a[1][2]=1;
		a[2][1]=2;
		a[2][3]=3;
		//把图形输出
		//行
		for(int i=0;i<4;i++)
		{
			//列
			for(int j=0;j<6;j++)
			{
				System.out.print(a[i][j]+" ");
			}
			System.out.println();//换行
		}
	}

}
