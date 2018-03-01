package job;

import entity.Mail;
import util.MailSendHelper;
import util.SpringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class notifyPrizeJob implements Runnable {
	private Logger logger = LoggerFactory.getLogger(notifyPrizeJob.class);
	private MailSendHelper mailSenderHelper = (MailSendHelper) SpringHelper.getBean(MailSendHelper.class);
	private Mail mail;

	notifyPrizeJob(Mail mail) {
		this.mail = mail;
	}

	@Override public void run() {
		Map<String, Object> param = new HashMap<>();
		param.put("mail", mail);

		mailSenderHelper.sendVmMail(mail.getReceiverMail(), "中奖通知", param, "PrivilegeTicketNotify.vm");
	}
}
