package com.xiaoma;
import java.io.*;
/*
 * @���ߣ�С��
 * @date��14.2.12
 * @����:����
 */

public class Demo_3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception  {
		// TODO Auto-generated method stub
		//����һ����������
		Dog dogs[]=new Dog[4];
		//��ʼ��
		
		//�ӿ���̨����
		InputStreamReader isr=new InputStreamReader(System.in);
		BufferedReader br=new BufferedReader(isr);
		
		
		for(int i=0;i<4;i++){
			dogs[i]=new Dog();//�����˶���������ʹ��ʱһ��Ҫ��new����Ȼ����ֿ�ָ���쳣
			System.out.println("������"+(i+1)+"��������");
			//�ӿ���̨��ȡ
			
				String name=br.readLine();
			
			//�����ָ�������
			dogs[i].setName(name);
			System.out.println("�����빷������");
			String s_weight=br.readLine();
			float weight=Float.parseFloat(s_weight);//��stringת��int
			dogs[i].setWeight(weight);	
			
		}
		//����ƽ������
		float allWeight=0;
		for(int i=0;i<4;i++){
			allWeight+=dogs[i].getWeight();
			
			
		}
		float avgWeight=allWeight/dogs.length;
		System.out.println("������="+allWeight+"ƽ������="+avgWeight);
		//�ҳ��������Ĺ�,�������㷨
		//�����һֻ�������
		float maxWeight=dogs[0].getWeight();
		int maxIndex=0;
		for(int i=1;i<dogs.length;i++){
			if(maxWeight<dogs[i].getWeight()){
				maxWeight=dogs[i].getWeight();
				maxIndex=i;
			}
		}
		System.out.println("�������Ĺ���"+(maxIndex+1)+"������"+dogs[maxIndex].getWeight());
		//ͨ�����ҹ����������
		//�����ң��Ƚ��ַ��������Ƿ����Ҫ��equals������
		
		

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