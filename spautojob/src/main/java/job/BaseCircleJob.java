package job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public interface BaseCircleJob extends Job {
	@Override void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException;
}
