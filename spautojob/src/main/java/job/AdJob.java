package job;

import entity.User;
import entity.Mail;
import org.springframework.stereotype.Service;
import service.UserService;
import util.DateHelper;
import util.MailHelper;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
@DisallowConcurrentExecution
public class AdJob implements Job {
	private Logger logger = LoggerFactory.getLogger(AdJob.class);
	@Autowired
	UserService userService;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		try {
			List<User> UserInfo = userService.getUserInfo();
			for (User user : UserInfo) {
				Mail userNameToMail = getUserNameToMail(user);
				MailHelper.submit(new notifyPrizeJob(userNameToMail));
			}
		} catch (Throwable throwable) {
			logger.error("查询用户信息错误:" + throwable.getCause());
			throwable.printStackTrace();
		}
	}

	private Mail getUserNameToMail(User user) {
		Mail taskMail = new Mail();
		taskMail.setReceiverName(user.getUserName());
		taskMail.setDate(DateHelper.getCurrenTimestamp());
		taskMail.setId(user.getId());
		taskMail.setContent("国庆大礼包通知！");
		taskMail.setReceiverMail(user.getEmail());
		return taskMail;
	}
}


