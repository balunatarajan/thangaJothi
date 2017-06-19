package sabai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

import sabai.anbar.AnbarData;

@Service
public class MailService {
	@Autowired
	private JavaMailSender jms;	
	
	 
	//public MailService(JavaMailSender jms){}
	@Async
	public void sendAnbarAddMail(AnbarData ad){
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setTo("balu.natarajan@gmail.com");
		smm.setFrom("thanajothignanasabai@gmail.com");
		smm.setSubject("Test mail from Thanga Jothi Gnana Sabai");
		smm.setText("Regards, Balu N");
		jms.send(smm);
	}
	
	public void transactionAddMail(){
		
	}
	
	public void bookAddToTrustMail(){
		
	}
	
	public void bookTakenOutFromTrustMail(){
		
	}
	
	public void bookReorderRemainderMail(){
		
	}
}
