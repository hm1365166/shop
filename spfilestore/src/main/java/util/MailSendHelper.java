package util;

import com.file.entity.MailPojo;
import com.file.service.UserService;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
public class MailSendHelper {
	private static final Logger log = LoggerFactory.getLogger(MailSendHelper.class);

	@Value("${mail.from_email}")
	private String fromEmail;

	@Value("${mail.from_name}")
	private String fromName;


	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private VelocityEngine velocityEngine;//spring配置中定义

	@Autowired
	private UserService userService;

	/**
	 * 发送VM模板邮件
	 *
	 * @param toEmail
	 * @param subject
	 * @param model
	 * @param vm
	 * @return
	 */
	public int sendVmMail(String toEmail, String subject, Map<String, Object> model, String vm) {
		MailPojo mailBean = new MailPojo();
		mailBean.setFrom(fromEmail);
		mailBean.setFromName(fromName);//发件人:  友金所
		mailBean.setSubject(subject);
		mailBean.setToEmails(new String[] { toEmail });
		try {
			MimeMessage msg = createVmMessage(mailBean, model, vm);
			javaMailSender.send(msg);
			log.info("发送成功！"+toEmail);
			return ResultCode.SUCCESS;
		} catch (Exception e) {
			log.error("error in sendVmMail " + toEmail + " " + e.getCause());
			e.printStackTrace();
			return 0;
		}
	}

	private MimeMessage createVmMessage(MailPojo mailBean, Map<String, Object> vmModel, String vm) throws MessagingException, UnsupportedEncodingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		messageHelper.setFrom(mailBean.getFrom(), mailBean.getFromName());
		messageHelper.setSubject(mailBean.getSubject());
		messageHelper.setTo(mailBean.getToEmails());
		String result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, vm, "UTF-8", vmModel);
		messageHelper.setText(result, true);
		return mimeMessage;
	}

}
