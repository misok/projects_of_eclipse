package xiaoma.com.decoratermodel;
//����װ����Ħ��
public class Mocha extends CondimentDecorator{
	Beverage beverage;//��¼����
	public Mocha(Beverage beverage){
		this.beverage=beverage;//�ѱ�װ���ߴ�����
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