package swing界面;
import java.awt.*;
import javax.swing.*;
public class 下拉列表滚动 extends JFrame {

	/**
	 * @param args
	 */
	
	JComboBox jc1;
	JPanel jp1,jp2;
	JLabel jl1,jl2;
	JList list;
	JScrollPane jsp;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		下拉列表滚动 show=new 下拉列表滚动();

	}
	public 下拉列表滚动()
	{
		jc1=new JComboBox();
		jp1=new JPanel();
		jp2=new JPanel();
		jl1=new JLabel("你的籍贯");
		jl2=new JLabel("你喜欢去的地方");
		
		String []jg={"北京","上海","天津","火星"};
		jc1=new JComboBox(jg);
		
		String []dd={"黄山","华山","九寨沟","峨眉山"};
		list=new JList(dd);
		list.setVisibleRowCount(2);
		jsp=new JScrollPane(list);//把滚动条叫进去
		
		
		this.setLayout(new GridLayout(3,1));//设置布局
		
		jp1.add(jl1);
		jp1.add(jc1);
		jp2.add(jl2);
		jp2.add(jsp);//这样可以滚动
		
		this.add(jp1);
		this.add(jp2);//添加组件
		
		this.setSize(300,150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}
