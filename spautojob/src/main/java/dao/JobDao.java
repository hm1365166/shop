package dao;

import entity.Job;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface JobDao {
	List<Job> getActionJob() throws Throwable;

	void updateActionedJob(Map job) throws Throwable;
}
