package xiaoma.com.singletonmodel;
/*
 * ����ģ�ͣ�ȷ��һ����ֻ��һ��ʵ�������ṩȫ�ַ��ʵ�
 * ���Ƿ���һ��������߳�����
 * �������1����get����ǰ���ͬ�����ƣ�synchronized  ȱ�㣺���ͳ���Ч��
 * �������2�����д���ʵ�� 
 * �������3��˫�ؼ�����  
 * ʵ�ֵ���ģʽ����Ҫһ��˽�й�������һ����̬��������һ����̬������
 * 
 */
public class ChocolateBoiler {
	private boolean empty;
	private boolean boiled;
	private static ChocolateBoiler uniqueChocolateBoiler;//����һ;��̬������¼�������Ψһʵ��
	//private static ChocolateBoiler uniqueChocolateBoiler=new ChocolateBoiler; ���2
	//private volatile static ChocolateBoiler uniqueChocolateBoiler;���3��ȷ���ؼ���
	private ChocolateBoiler(){//����������Ϊ˽�� ��ֻ�������ܵ���
		empty=true;
		boiled=false;
	}
	
	public static ChocolateBoiler getChocolateBoiler(){//����һ���ô˷���ʵ�������󣬲��������ʵ��
		if(uniqueChocolateBoiler==null){
			uniqueChocolateBoiler=new ChocolateBoiler();
		}
		return uniqueChocolateBoiler;
		
	}
	/*public static ChocolateBoiler getChocolateBoiler(){
		
		return uniqueChocolateBoiler;//�������2
		
	}*/
	/*public static ChocolateBoiler getChocolateBoiler(){//�������3
		if(uniqueChocolateBoiler==null){//���ʵ���������ڣ��ͽ���ͬ��
		   synchronized (ChocolateBoiler.class){//ֻ�е�һ�βų���ִ������Ĵ���
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
