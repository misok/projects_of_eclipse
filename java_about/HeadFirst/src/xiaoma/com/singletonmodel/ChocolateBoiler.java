package xiaoma.com.singletonmodel;
/*
 * 单件模型：确保一个类只有一个实例，并提供全局访问点
 * 但是方法一会带来多线程问题
 * 解决方案1：在get方法前面加同步控制：synchronized  缺点：降低程序效率
 * 解决方案2：急切创建实例 
 * 解决方案3：双重检查加锁  
 * 实现单件模式：需要一个私有构造器，一个静态方法，和一个静态变量；
 * 
 */
public class ChocolateBoiler {
	private boolean empty;
	private boolean boiled;
	private static ChocolateBoiler uniqueChocolateBoiler;//方法一;静态变量记录单件类的唯一实例
	//private static ChocolateBoiler uniqueChocolateBoiler=new ChocolateBoiler; 解决2
	//private volatile static ChocolateBoiler uniqueChocolateBoiler;解决3，确保关键词
	private ChocolateBoiler(){//构造器声明为私有 ，只有类内能调用
		empty=true;
		boiled=false;
	}
	
	public static ChocolateBoiler getChocolateBoiler(){//方法一：用此方法实例化对象，并返回这个实例
		if(uniqueChocolateBoiler==null){
			uniqueChocolateBoiler=new ChocolateBoiler();
		}
		return uniqueChocolateBoiler;
		
	}
	/*public static ChocolateBoiler getChocolateBoiler(){
		
		return uniqueChocolateBoiler;//解决方案2
		
	}*/
	/*public static ChocolateBoiler getChocolateBoiler(){//解决方案3
		if(uniqueChocolateBoiler==null){//检查实例，不存在，就进入同步
		   synchronized (ChocolateBoiler.class){//只有第一次才彻底执行这里的代码
		       if(uniqueChocolateBoiler==null){
		       
			       uniqueChocolateBoiler=new ChocolateBoiler();
			   }
		   }
		}
		return uniqueChocolateBoiler;
		
	}*/
	public void fill(){
		if(isEmpty()){
			empty=false;
			boiled=false;
		}
	}
	public void boil(){
		if(!isEmpty()&&!isBoiled()){
			boiled=true;
		}
	}
	public void drain(){
		if(!isEmpty()&&isBoiled()){
			empty=true;
		}
	}
	public boolean isEmpty(){
		return empty;
	}
	public boolean isBoiled(){
		return boiled;
	}

}
