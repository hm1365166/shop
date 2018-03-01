package com.file.entity;

public class Job {
	private int serialNo;
	private String jobName;			//任务名
	private String jobGroup;		//任务组名
	private String triggerName;		//触发器名
	private String triggerGroup;	//触发器组名
	private String time;			//时间
	private String jobClass;		//执行类
	private String jobParams;		//参数
	private String action;			//操作
	private String jobDescription;	//任务类描述
	
	public Job(){
		
	}
	
	public Job(int serialNo, String jobName, String jobGroup, String triggerName,String triggerGroup,
			String time, String jobClass, String jobParams, String action){
		this.serialNo = serialNo;
		this.jobName = jobName;
		this.jobGroup = jobGroup;
		this.triggerName = triggerName;
		this.triggerGroup = triggerGroup;
		this.time = time;
		this.jobClass = jobClass;
		this.jobParams = jobParams;
		this.action = action;
	}
	
	@Override
	public String toString(){
		return "Job: " + "\nserialNo=" + serialNo + 
				"\njobName=" + jobName + 
				"\njobGroup=" + jobGroup + 
				"\ntriggerName=" + triggerName + 
				"\ntriggerGroup=" + triggerGroup + 
				"\ntime=" + time + 
				"\njobClass=" + jobClass + 
				"\njobParams=" + jobParams + 
				"\naction=" + action +
				"\n";
	}

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerGroup() {
		return triggerGroup;
	}

	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public String getJobParams() {
		return jobParams;
	}

	public void setJobParams(String jobParams) {
		this.jobParams = jobParams;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
}
