package xiaoma.duckmodel;

public class MiniDuckSimulator {

	/**
	 * @param args
	 * @time:7/22/14
	 * @author xiamao 
	 * 模式一：策略模式
	 * 设计模式只面向接口编程，把可变的行为抽象出来，让行为类实现
	 * qustion:为啥要把duck设计成抽象类。。。
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Duck mallar=new MallarDuck();
		mallar.performFly();
		mallar.performQuack();
		ModelDuck modelduck=new ModelDuck();
		modelduck.performFly();
		modelduck.setFlyBehavior(new FlydrivedRoket());//设定后还要显示
		modelduck.performFly();
		
	}

}









