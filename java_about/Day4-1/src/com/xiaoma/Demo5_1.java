package com.xiaoma;

public class Demo5_1 {

	/**
	 * @param args
	 * @author 小马
	 * @date 14.2.10
	 * @功能：1.java三大特征之方法覆盖
	 * @功能:2.java三大特征之多态
	 */
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*//创建一只猫
		Cat cat1=new Cat();
		cat1.cry();
		Dog dog1=new Dog();
		dog1.cry();*/
		
		//多态
		/*Animal an=new Cat();//简单多态
		an.cry();
		an=new Dog();
		an.cry();  //如果猫类没有cty方法，父类就会调自己的cry*/
		Master master=new Master();
		master.feed(new Dog(), new Bone());
		master.feed(new Cat(), new Fish());//父类只定义了喂食就可以

	}

}

/*class Animal
{
   int age;
   String name;
   
   public void cry()//父类方法
   {
	   System.out.println("我是动物，不知道怎么叫");
	   
   }
}

class Cat extends Animal
{
	//覆盖父类
	public void cry()
	{
		System.out.println("我喵喵叫");
	}
}     
class Dog extends Animal
{
	//覆盖父类
	public void cry()
	{
		System.out.println("我汪汪叫");
	}
}     */
class Master
{
	//给动物喂食物，使用多态方法就可以用一个
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
		System.out.println("鱼");
	}
}
class Bone extends Food{
	public void showName(){
		System.out.println("骨头");
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
		System.out.println("不知道怎么叫");
	}
	//动物可以吃东西
	public void eat(){
		System.out.println("我不知道吃什么");
	}
}
class Cat extends Animal
{
	//覆盖父类
	public void cry()
	{
		System.out.println("我喵喵叫");
	}
	public void eat(){
		System.out.println("我喜欢吃鱼");
	}
}     
class Dog extends Animal
{
	//覆盖父类
	public void cry()
	{
		System.out.println("我汪汪叫");
	}
	//狗吃东西
	public void eat(){
		System.out.println("我喜欢吃骨头");
	}
}    