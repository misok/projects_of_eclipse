package com.xiaoma1;

public class ��λ��������� {

	/**
	 * @param args
	 * @author С��
	 * @date��14.2.18
	 * @���ܣ���λ����
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
		System.out.println("c="+c);//4���ƶ�һλ�൱�ڳ�2
		System.out.println("d="+d);//-4
		System.out.println("e="+e);//0
		System.out.println("~2="+(~2));//-3
		System.out.println("2&3="+(2&3));//2
		System.out.println("2|3="+(2|3));//3
		System.out.println("~-5="+(~-5));//4
		System.out.println("-3^3="+(-3^3));//-2,����-3��ԭ�룬�ٷ��룬�����룬��3�Ĳ��������
		//�����������Ǹ�������-1���䷴�룬��ȡ������ԭ��
	}

}
