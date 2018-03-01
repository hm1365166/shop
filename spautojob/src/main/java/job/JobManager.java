package job;

import entity.Job;
import service.impl.JobServiceImpl;
import util.QuartzUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobManager implements BaseCircleJob {

	static Log logger = LogFactory.getLog(JobManager.class);
	@Autowired
	JobServiceImpl jobService;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		try {
			logger.info("轮询job中---------------------------------------------------");
			List<Job> jobs = jobService.getActionJob();
			scanAndHandle(jobs);
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			logger.error(throwable.getCause());
		}
	}

	private void scanAndHandle(List<Job> jobs) throws Throwable {
		for (Job jobList : jobs) {

			int serialNo = jobList.getSerialNo();
			String jobName = jobList.getJobName();            //任务名
			String jobGroup = jobList.getJobGroup();            //任务组名
			String triggerName = jobList.getJobName();    //触发器名
			String triggerGroup = jobList.getTriggerGroup();    //触发器组名
			String time = jobList.getTime();            //时间
			String jobClass = jobList.getJobClass();    //执行类
			String jobParams = jobList.getJobParams();    //参数
			String action = jobList.getAction();        //操作

			Job jobEntity = new Job(serialNo, jobName, jobGroup, triggerName, triggerGroup,
					time, jobClass, jobParams, action);
			System.out.println(jobEntity.toString());
			boolean flag = doAction(jobEntity);
			if (flag) {
				Map<String, Object> job = new HashMap<>();
				job.put("job", jobEntity);
				jobService.updateActionedJob(job);
			}
		}
	}

	private boolean doAction(Job job) throws Exception {
		boolean flag = false;
		String action = job.getAction();
		if (action != null && !"".equals(action)) {
			logger.info("-------------------job action---------------------" + action);
		}
		Class cls = Class.forName(job.getJobClass());
		if ("Add".equals(action)) {
			Map paramsMap = new HashMap();
			if (!"".equals(job.getJobParams())) {
				String[] params = job.getJobParams().split("@");
				for (String param : params) {
					int index = param.indexOf("=");
					paramsMap.put(param.substring(0, index), param.substring(index + 1));
				}
			}

			//先删除再新增，避免存在重复
			QuartzUtil.removeJob(job.getTriggerName(), job.getTriggerGroup(), job.getJobName(), job.getJobGroup());
			QuartzUtil.addJob(job.getJobName(), job.getJobGroup(), job.getTriggerName(), job.getTriggerGroup(), cls, job.getTime(), paramsMap);
			flag = true;
		} else if ("Mod".equals(action)) {
			//修改job执行时间
			QuartzUtil.modifyJobTime(job.getTriggerName(), job.getTriggerGroup(), job.getTime());
			flag = true;
		} else if ("Del".equals(action)) {
			//删除job
			QuartzUtil.removeJob(job.getTriggerName(), job.getTriggerGroup(), job.getJobName(), job.getJobGroup());
			//psDelete.setInt(1, job.getSerialNo());
			//psDelete.addBatch();
			flag = true;
		} else if ("Pause".equals(action)) {
			//暂停job
			QuartzUtil.pause(job.getJobName(), job.getJobGroup());
			flag = true;
		} else if ("Resume".equals(action)) {
			//重启job
			QuartzUtil.resume(job.getJobName(), job.getJobGroup());
			flag = true;
		}
		return flag;
	}

}
