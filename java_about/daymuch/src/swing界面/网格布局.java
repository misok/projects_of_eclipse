/**
	 * @param args
	 */
package swing界面;
import java.awt.*;
import javax.swing.*;
public class 网格布局  extends JFrame{
    int size=9;
	JButton jbs[]=new JButton[size];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		网格布局 y=new 网格布局();

	}
	public 网格布局()
	{
		for(int i=0;i<size;i++)
		{
			jbs[i]=new JButton(String.valueOf(i));
		}
		
		this.setLayout(new GridLayout(3,3,10,10));//显示网格三行三列,以及让网格之间有空隙
		for(int i=0;i<size;i++)
		{
			this.add(jbs[i]);
		}
		this.setTitle("网格布局");
		this.setSize(300,200);
		this.setLocation(200, 200);
		this.setResizable(false);//不允许改变窗体大小
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setVisible(true);
	}

}
