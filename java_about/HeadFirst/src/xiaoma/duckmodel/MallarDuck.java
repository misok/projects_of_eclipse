package xiaoma.duckmodel;

public class MallarDuck extends Duck
{
	public MallarDuck()
	{
		quackBehavior=new Quack();
		flyBehavior=new FlyWithWings();
	}
	public void display()
	{
		System.out.println("I am a real ,Mallar Duck!");
	}
}