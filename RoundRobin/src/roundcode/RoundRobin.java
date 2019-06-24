package roundcode;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class RoundRobin {

	private static int timeSlice; //时间片
	
    private static Queue<Process> readyQueue= new LinkedBlockingQueue<>();//就绪队列
    private static Queue<Process> unreachQueue=new LinkedBlockingQueue<>(); //未到达队列
    private static Queue<Process> executedQueue= new LinkedList<>(); //执行完成队列
    
    private static int executedTime = 1;//总运行时间

    private RoundRobin() {
        
    }
    
    public static void RoundRobinAlgorithm() {
    	//就绪队列或未到达队列不为空
    	while(readyQueue!=null || unreachQueue!=null) {
    		//将时间到达的进程加入就绪队列
    		if(unreachQueue!=null) {
    			for (Process unreachprocess : unreachQueue) {
    				if(unreachprocess.getReachTime()<= executedTime) {
    					unreachQueue.remove(unreachprocess);
    					readyQueue.add(unreachprocess);
    				}
    			}
    		}
    		//若就绪队列不为空，执行
    		if(readyQueue.peek()!=null){
    			//进入新的时间片
    			Process executeProcess=readyQueue.poll();
    			executedTime=executeOneTimeSlice(executeProcess,executedTime);
    		}
    		//就绪队列为空，未到达队列不为空
    		else if(readyQueue.peek()==null && unreachQueue.peek()!=null) {
    			executedTime=unreachQueue.peek().getReachTime();
    			executedTime=executeOneTimeSlice(unreachQueue.poll(),executedTime);
    		}
    		//所有进程执行完毕
    		else {
    			System.out.println("——————所有进程执行完毕——————");
    			break;
    		}
    	}
    	
    }
    
    //执行一个时间片
    private static int executeOneTimeSlice(Process s_executeProcess, int s_executedTime) {
    	showCurrentMessage(s_executeProcess,s_executedTime);
    	s_executedTime+=timeSlice;
    	//若当前进程在时间片中能执行完成，加入执行完成队列
    	if((s_executeProcess.getNeedTime()-s_executeProcess.getExecutedTime())<=timeSlice){
    		s_executeProcess.setExecutedTime(s_executeProcess.getNeedTime());
    		s_executeProcess.setprocessState("E");  
    		executedQueue.add(s_executeProcess);
    	}
    	else{
    		//未执行完，加入就绪队列末端
    		s_executeProcess.setExecutedTime(s_executeProcess.getExecutedTime()+timeSlice);
    		//将当前时间片到达的进程加入就绪队列
    		if(unreachQueue!=null) {
    			for (Process unreachprocess : unreachQueue) {
    				if(unreachprocess.getReachTime()<= s_executedTime) {
    					unreachQueue.remove(unreachprocess);
    					readyQueue.add(unreachprocess);
    				}
    			}
    		}
    		readyQueue.add(s_executeProcess);
    	}
    	return s_executedTime;
    }
    
    //显示当前时间片的执行信息
    public static void showCurrentMessage(Process executeProcess,int executedTime) {
    	int nextexecutedTime=executedTime+timeSlice-1;
    	System.out.println("——————显示时间"+executedTime+"~"+nextexecutedTime+"的执行信息——————");
    	System.out.println("运行进程："+executeProcess.getprocessNumber());
    	System.out.print("就绪队列：");
    	for (Process process : readyQueue) { 
    		System.out.print(process.getprocessNumber());
    	}
    	System.out.println();
    	System.out.print("未到达队列：");
    	for (Process process : unreachQueue) { 
    		System.out.print(process.getprocessNumber());
    	}
    	System.out.println();
    	System.out.print("执行完成队列：");
    	for (Process process : executedQueue) { 
    		System.out.print(process.getprocessNumber());
    	}    	
    	System.out.println();
    }
    
    //显示当前进程信息
    public static void showProcessMessage() {
    	System.out.println("——————显示当前进程信息——————");
    	System.out.println("当前运行时间"+executedTime);
    	for (Process process : unreachQueue) { 
    		System.out.println("进程名 "+process.getprocessNumber()+" "+"状态 "+process.getprocessState()+" "
    				+"到达时间 "+process.getReachTime()+" "+"执行时间 "+process.getNeedTime()+
    				" "+"已执行时间 "+process.getExecutedTime());
    		}   
    }
    
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		//初始化五个进程
		System.out.println("——————开始设定您的进程——————");
		for(int i=0;i<5;i++) {
			int number=i+1;
			String processNumber = "Q"+number;
			Process process=new Process(processNumber);
			process.setprocessState("R");
			process.setExecutedTime(0);			 
			System.out.println("设定进程Q"+number+"的执行时间");
			process.setNeedTime(scanner.nextInt());
			System.out.println("设定进程Q"+number+"的到达时间");
			process.setReachTime(scanner.nextInt());
			unreachQueue.add(process);
		}
		System.out.println("——————设定时间片大小——————");
		timeSlice=scanner.nextInt();
		scanner.close();
		
		//显示所有进程情况
		showProcessMessage();
		RoundRobinAlgorithm();
	}

}
