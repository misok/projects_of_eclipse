/**
	 * @param args
	 */
package swing����;
import java.awt.*;
import javax.swing.*;
public class ���񲼾�  extends JFrame{
    int size=9;
	JButton jbs[]=new JButton[size];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		���񲼾� y=new ���񲼾�();

	}
	public ���񲼾�()
	{
		for(int i=0;i<size;i++)
		{
			jbs[i]=new JButton(String.valueOf(i));
		}
		
		this.setLayout(new GridLayout(3,3,10,10));//��ʾ������������,�Լ�������֮���п�϶
		for(int i=0;i<size;i++)
		{
			this.add(jbs[i]);
		}
		this.setTitle("���񲼾�");
		this.setSize(300,200);
		this.setLocation(200, 200);
		this.setResizable(false);//������ı䴰���С
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setVisible(true);
	}

}
