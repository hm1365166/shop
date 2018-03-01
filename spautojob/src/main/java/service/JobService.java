package service;

import entity.Job;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface JobService {
	List<Job> getActionJob() throws Throwable;

	void updateActionedJob(Map job) throws Throwable;
}
