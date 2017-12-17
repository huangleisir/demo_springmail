package service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class JavaMailTest1 {
	public static void main(String[] args) throws MessagingException {
		long t1 = System.currentTimeMillis();
		Properties props = new Properties();
		// 开启debug调试
		props.setProperty("mail.debug", "true");
		// 发送服务器需要身份验证
		props.setProperty("mail.smtp.auth", "false");
		// 设置邮件服务器主机名
		props.setProperty("mail.host", "smtp.126.com");
		// 发送邮件协议名称
		props.setProperty("mail.transport.protocol", "smtp");
		
		// 设置环境信息
		Session session = Session.getInstance(props);
		
		// 创建邮件对象
		Message msg = new MimeMessage(session);
		
		/*添加正文内容*/
		Multipart multipart = new MimeMultipart();
		BodyPart contentPart = new MimeBodyPart();
		contentPart.setText("");

		contentPart.setHeader("Content-Type", "text/html; charset=GBK");
		multipart.addBodyPart(contentPart);
		
		/*添加附件*/
		/*String[] files = {"C:\\Users\\Administrator\\Desktop\\Screenshot_20171210-112337.png"};
		for (String file : files) {
			File usFile = new File(file);
			MimeBodyPart fileBody = new MimeBodyPart();
			DataSource source = new FileDataSource(usFile);
			fileBody.setDataHandler(new DataHandler(source));
			sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
//			fileBody.setFileName("111");
			multipart.addBodyPart(fileBody);
		}*/
		BodyPart attachmentBodyPart = new MimeBodyPart();  
        // 根据附件路径获取文件,  
        FileDataSource dataSource = new FileDataSource("1.png");  
        attachmentBodyPart.setDataHandler(new DataHandler(dataSource));  
        //MimeUtility.encodeWord可以避免文件名乱码  
        try {
			attachmentBodyPart.setFileName(MimeUtility.encodeWord(dataSource.getFile().getName()));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        multipart.addBodyPart(attachmentBodyPart); 
		msg.setContent(multipart);
		msg.setSubject("这不是广告啊     "+ new Date());
		// 设置邮件内容
		msg.setText("这是一封由JavaMail发送的邮件！"+ new Date());
		// 设置发件人
		msg.setFrom(new InternetAddress("huangleisir@126.com"));
		
		Transport transport = session.getTransport();
		// 连接邮件服务器
		transport.connect("huangleisir@126.com", "13579HL");
		// 发送邮件
		transport.sendMessage(msg, new Address[] {new InternetAddress("huangleisir@qq.com")});
		// 关闭连接
		transport.close();
		System.out.println(String.format("发送邮件耗时：【%s】", (System.currentTimeMillis()-t1)/1000.0)+"秒钟");
	}
}
