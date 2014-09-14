package xiaoma.com.decoratermodel;
//具体装饰着摩卡
public class Mocha extends CondimentDecorator{
	Beverage beverage;//记录饮料
	public Mocha(Beverage beverage){
		this.beverage=beverage;//把被装饰者传进来
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return beverage.getDescription()+",Mocha";
	}

	@Override
	public double cost() {
		// TODO Auto-generated method stub
		return beverage.cost()+0.20;
	}
	

}
