package com.xiaoma;

public class Demo5_1 {

	/**
	 * @param args
	 * @author С��
	 * @date 14.2.10
	 * @���ܣ�1.java��������֮��������
	 * @����:2.java��������֮��̬
	 */
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*//����һֻè
		Cat cat1=new Cat();
		cat1.cry();
		Dog dog1=new Dog();
		dog1.cry();*/
		
		//��̬
		/*Animal an=new Cat();//�򵥶�̬
		an.cry();
		an=new Dog();
		an.cry();  //���è��û��cty����������ͻ���Լ���cry*/
		Master master=new Master();
		master.feed(new Dog(), new Bone());
		master.feed(new Cat(), new Fish());//����ֻ������ιʳ�Ϳ���

	}

}

/*class Animal
{
   int age;
   String name;
   
   public void cry()//���෽��
   {
	   System.out.println("���Ƕ����֪����ô��");
	   
   }
}

class Cat extends Animal
{
	//���Ǹ���
	public void cry()
	{
		System.out.println("��������");
	}
}     
class Dog extends Animal
{
	//���Ǹ���
	public void cry()
	{
		System.out.println("��������");
	}
}     */
class Master
{
	//������ιʳ�ʹ�ö�̬�����Ϳ�����һ��
	public void feed(Animal an,Food f){
		an.eat();
		f.showName();
		
	}
	
}
class Food
{
	String name;
	public void showName(){
		
		
	}
}
class Fish extends Food{
	public void showName(){
		System.out.println("��");
	}
}
class Bone extends Food{
	public void showName(){
		System.out.println("��ͷ");
	}
}
class Animal
{
    String name;
	int age;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	public void cry(){
		System.out.println("��֪����ô��");
	}
	//������ԳԶ���
	public void eat(){
		System.out.println("�Ҳ�֪����ʲô");
	}
}
class Cat extends Animal
{
	//���Ǹ���
	public void cry()
	{
		System.out.println("��������");
	}
	public void eat(){
		System.out.println("��ϲ������");
	}
}     
class Dog extends Animal
{
	//���Ǹ���
	public void cry()
	{
		System.out.println("��������");
	}
	//���Զ���
	public void eat(){
		System.out.println("��ϲ���Թ�ͷ");
	}
}    