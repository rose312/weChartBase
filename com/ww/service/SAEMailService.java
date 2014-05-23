package com.ww.service;

import com.sina.sae.mail.SaeMail;

public class SAEMailService {

	/**
	 * @param args
	 */
	private static String mailContent = "";

	// private static Log logger;

	public static void main(String[] args) {
		
		System.out.println(true+"111");
	}

	public static boolean sendMail() {

		SaeMail mail = new SaeMail();
		// mail.setTls(true);
		mail.setFrom("w1225750225@163.com");
		mail.setSmtpUsername("w1225750225@163.com");
		mail.setSmtpPassword("314315");
		mail.setSmtpHost("smtp.163.com");
		mail.setSmtpPort(25);
		mail.setTo(new String[] { "514758946@qq.com" });
		mail.setSubject("Password Reset");
		mail.setContentType("TEXT");
		mail.setContent(mailContent + mail.getAppName());
		// logger.info("mail message: host " + mail.getSmtpHost() + " port "
		// + mail.getSmtpPort() + " username " + mail.getSmtpUsername()
		// + " password " + mail.getSmtpPassword());
		boolean isOk = mail.send();
		if (!isOk) {
			// logger.error("Send reset password mail fail: error code is"
			// + mail.getErrno() + ", error message is: "
			// + mail.getErrmsg());
		}
		return isOk;
	}

}
