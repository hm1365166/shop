package service.impl;

import dao.JobDao;
import entity.Job;
import service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JobServiceImpl implements JobService {
	@Autowired
	JobDao jobDao;

	@Override public List<Job> getActionJob() throws Throwable {
		return jobDao.getActionJob();
	}

	@Override public void updateActionedJob(Map job) throws Throwable {
		jobDao.updateActionedJob(job);
	}

}
