package roundcode;

class Process {
	private String processNumber;//进程名
	private String processState;//状态 E结束 R就绪
    private int reachTime; //到达时间
    private int needTime; //要求运行时间
    private int executedTime; //已运行时间时间

    public Process(String processNumber) {
        this.processNumber = processNumber;
    }

    //获得P进程名
    public String getprocessNumber() {
        return processNumber;
    }
    //设置进程名
    public void setprocessNumber(String processNumber) {
        this.processNumber = processNumber;
    }
    
    public String getprocessState() {
        return processState;
    }

    public void setprocessState(String processState) {
        this.processState = processState;
    }

    public int getReachTime() {
        return reachTime;
    }

    public void setReachTime(int reachTime) {
        this.reachTime = reachTime;
    }
    
    public int getNeedTime() {
        return needTime;
    }

    public void setNeedTime(int needTime) {
        this.needTime = needTime;
    }
    

    public int getExecutedTime() {
        return executedTime;
    }

    public void setExecutedTime(int executedTime) {
        this.executedTime = executedTime;
    }

}
