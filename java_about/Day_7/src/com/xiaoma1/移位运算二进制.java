package com.xiaoma1;

public class 移位运算二进制 {

	/**
	 * @param args
	 * @author 小马
	 * @date：14.2.18
	 * @功能：移位运算
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a=1>>2;//
		int b=-1>>2;
		int c=1<<2;
		int d=-1<<2;
		int e=3>>>2;
		System.out.println("a="+a);//0
		System.out.println("b="+b);//-1
		System.out.println("c="+c);//4，移动一位相当于乘2
		System.out.println("d="+d);//-4
		System.out.println("e="+e);//0
		System.out.println("~2="+(~2));//-3
		System.out.println("2&3="+(2&3));//2
		System.out.println("2|3="+(2|3));//3
		System.out.println("~-5="+(~-5));//4
		System.out.println("-3^3="+(-3^3));//-2,先求-3的原码，再反码，再求补码，跟3的补码做异或
		//出来结果如果是负数，则-1求其反码，再取反求其原码
	}

}
