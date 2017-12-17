package service;
import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/***
 * 创建日期：2014年6月3日
 * 作者：黄飞
 * 邮箱：834865081@qq.com
 * 博客地址：http://my.csdn.net/hfmbook
 * */
public class AnnexMailService {
	
	public static boolean sendMail(String subject, String toMail,
			String content, String... files) {
		boolean isFlag = false;
		try {

			String smtpFromMail = "huangleisir@126.com";  //账号
			String pwd = "13579HL"; //密码
			int port = 25; //端口
			String host = "smtp.126.com"; //端口

			Properties props = new Properties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true");
                        props.put("mail.smtp.ssl.enable", "true");
			Session session = Session.getDefaultInstance(props);
			session.setDebug(false);

			MimeMessage message = new MimeMessage(session);
			try {
				message.setFrom(new InternetAddress(smtpFromMail, "QQ邮件测试"));
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(toMail));
				message.setSubject(subject);
				message.addHeader("charset", "UTF-8");
				
				/*添加正文内容*/
				Multipart multipart = new MimeMultipart();
				BodyPart contentPart = new MimeBodyPart();
				contentPart.setText(content);

				contentPart.setHeader("Content-Type", "text/html; charset=GBK");
				multipart.addBodyPart(contentPart);
				
				/*添加附件*/
				for (String file : files) {
					File usFile = new File(file);
					MimeBodyPart fileBody = new MimeBodyPart();
					DataSource source = new FileDataSource(file);
					fileBody.setDataHandler(new DataHandler(source));
					sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
					fileBody.setFileName("=?GBK?B?"
							+ enc.encode(usFile.getName().getBytes()) + "?=");
					multipart.addBodyPart(fileBody);
				}

				message.setContent(multipart);
				message.setSentDate(new Date());
				message.saveChanges();
				Transport transport = session.getTransport("smtp");

				transport.connect(host, port, smtpFromMail, pwd);
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
				isFlag = true;
			} catch (Exception e) {
				isFlag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isFlag;
	}

	public static void main(String[] args) {
		sendMail("你好", "869328841@qq.com", "朋友好久不见" , 
				"C:\\Users\\Administrator\\Desktop\\Screenshot_20171210-112337.png");
	}
	

    
}