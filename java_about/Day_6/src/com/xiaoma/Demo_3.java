package com.xiaoma;
import java.io.*;
/*
 * @作者：小马
 * @date：14.2.12
 * @功能:数组
 */

public class Demo_3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception  {
		// TODO Auto-generated method stub
		//定义一个对象数组
		Dog dogs[]=new Dog[4];
		//初始化
		
		//从控制台输入
		InputStreamReader isr=new InputStreamReader(System.in);
		BufferedReader br=new BufferedReader(isr);
		
		
		for(int i=0;i<4;i++){
			dogs[i]=new Dog();//定义了对象数组在使用时一定要先new。不然会出现空指针异常
			System.out.println("请输入"+(i+1)+"狗的名字");
			//从控制台读取
			
				String name=br.readLine();
			
			//将名字赋给对象
			dogs[i].setName(name);
			System.out.println("请输入狗的体重");
			String s_weight=br.readLine();
			float weight=Float.parseFloat(s_weight);//把string转成int
			dogs[i].setWeight(weight);	
			
		}
		//计算平均体重
		float allWeight=0;
		for(int i=0;i<4;i++){
			allWeight+=dogs[i].getWeight();
			
			
		}
		float avgWeight=allWeight/dogs.length;
		System.out.println("总体中="+allWeight+"平均体重="+avgWeight);
		//找出体重最大的狗,即排序算法
		//假设第一只体重最大
		float maxWeight=dogs[0].getWeight();
		int maxIndex=0;
		for(int i=1;i<dogs.length;i++){
			if(maxWeight<dogs[i].getWeight()){
				maxWeight=dogs[i].getWeight();
				maxIndex=i;
			}
		}
		System.out.println("体重最大的狗是"+(maxIndex+1)+"体重是"+dogs[maxIndex].getWeight());
		//通过查找狗名输出体重
		//即查找，比较字符串内容是否相等要用equals方法。
		
		

	}

}
class Dog{
	private String name;
	private float weight;
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}