package mqw.com.task1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;



public class task_scheduling_system {

	/**
	 * @param args
	 * @author xiaoma 
	 * @function:a task scheduling system
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Task t1=new Task(0, 1, 4, 1);
		Task t2=new Task(0, 2, 3, 2);
		Task t3=new Task(1, 3, 2, 3);
		
		//优先队列比较器，实现先用时间比较再用优先级比较的功能
		Comparator<Task> taskComparator=new Comparator<Task>()
		{
			public int compare(Task t1,Task t2)
			{
				if (t1.getTime()==t2.getTime())
				{
					return (int)(t2.getPriority()-t1.getPriority());
				}
				else
					return (int)(t1.getTime()- t2.getTime());
				
			}
		};
		//100表示优先队列的大小为100
		PriorityQueue<Task>  pq = new PriorityQueue<Task>(100, taskComparator);
		pq.add(t1);
		pq.add(t2);
		pq.add(t3);
		//System.out.println(bq.poll().getId());
		//System.out.println(bq.poll().getId());
		//System.out.println(bq.poll().getId());
		
		Cpu cpu = new Cpu(pq);
		cpu.add_task();
		
	}

}

//task类
class Task
{
	private int time;//d到达时间
	private int priority;//优先级
	private int page;//任务量
	private int id;//编号
	public Task(int time,int priority,int page,int id)
	{
		this.id=id;
		this.time=time;
		this.page=page;
		this.priority=priority;
	}
	
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}

//cpu类，模拟cpu
class Cpu
{
	PriorityQueue<Task> pq = null;
	private  ArrayList<Task> task_array=new ArrayList<Task>();
	
	public Cpu(PriorityQueue<Task> pq)
	{
		this.pq = pq;
		
	}
	public void add_task()
	{
		int global_time = 0;
		//Task tk=null;
		while(!pq.isEmpty()){
			Task tk=(Task)pq.poll();
			System.out.println("current time: " + global_time);
			if(task_array.isEmpty())
			{
				task_array.add(tk);
				System.out.println("one task come id: " + tk.getId());
			}else{
				int during_time=0;
				during_time = global_time - task_array.get(0).getTime();
				if(during_time >= task_array.get(0).getPage())
				{
					System.out.println("task id: " + task_array.get(0).getId()+ "has done!");
					task_array.remove(0);
					task_array.add(tk);
					System.out.println("one task come id: " + tk.getId());
				}else{
					if(tk.getPriority() > task_array.get(0).getPriority())
					{
						Task quit_task = task_array.get(0);
						quit_task.setPage(quit_task.getPage() - during_time);
						System.out.println("Task go back to queue it's id: " + quit_task.getId());
						task_array.remove(0);
						task_array.add(tk);
						System.out.println("one task come id: " + tk.getId());
						pq.add(quit_task);
					}else
					{
						tk.setTime(global_time + 1);
						pq.add(tk);
						
					}
				}
			}
			global_time++;
			
		}	
	}
	
}

//class buffer_queue extends PriorityQueue
//{
//	private  PriorityQueue<Task> pq=null;
//	public   buffer_queue()
//	{
//		 pq = new PriorityQueue<Task>(100, timeComparator);
//	}
//	public void add_task(Task task)
//	{
//		pq.add(task);
//	}
//	
//	
//}