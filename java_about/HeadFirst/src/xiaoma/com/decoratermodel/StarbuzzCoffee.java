package xiaoma.com.decoratermodel;

public class StarbuzzCoffee {

	/**
	 * @param args
	 * װ����ģ��֮���ȵ�   
	 * 7��23��14
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//��һ����Espresso
		Beverage beverage=new Espresso();
		System.out.println(beverage.getDescription()+" $"+beverage.cost());
		
		//��һ������װ��������
		Beverage beverage2=new HouseBlend();
		beverage2=new Soy(beverage2);
		beverage2=new Mocha(beverage2);
		beverage2=new Whip(beverage2);
		
		System.out.println(beverage2.getDescription()+" $"+beverage2.cost());

	}

}
