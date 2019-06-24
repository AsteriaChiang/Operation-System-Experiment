package ffcode;

public class Job {
	    public int jobNumber;//作业号
	    public int jobSize;//所需内存大小
	    public int jobAddress;//作业存储的地址
	    public String jobState;//作业状态
	    
	    public Job(int jobNumber){
	    	this.jobNumber=jobNumber;
	    }
	    
	    public void setJobNumber(int jobNumber) {
			this.jobNumber=jobNumber;
		}	
		public int getJobNumber() {
			return jobNumber;
			
		}
	    
		public void setJobSize(int jobSize) {
			this.jobSize=jobSize;
		}	
		public int getJobSize() {
			return jobSize;
			
		}

		public void setJobAddress(int jobAddress) {
			this.jobAddress=jobAddress;
		}	
		public int getJobAddress() {
			return jobAddress;
			
		}
}
