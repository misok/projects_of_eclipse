package xiaoma.duckmodel;

public class MiniDuckSimulator {

	/**
	 * @param args
	 * @time:7/22/14
	 * @author xiamao 
	 * ģʽһ������ģʽ
	 * ���ģʽֻ����ӿڱ�̣��ѿɱ����Ϊ�������������Ϊ��ʵ��
	 * qustion:ΪɶҪ��duck��Ƴɳ����ࡣ����
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Duck mallar=new MallarDuck();
		mallar.performFly();
		mallar.performQuack();
		ModelDuck modelduck=new ModelDuck();
		modelduck.performFly();
		modelduck.setFlyBehavior(new FlydrivedRoket());//�趨��Ҫ��ʾ
		modelduck.performFly();
		
	}

}









