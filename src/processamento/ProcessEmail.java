package processamento;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import auxiliar.AuxiliarConstantes;

/**
 * Classe responsavel por enviar um email com anexo
 * @author john
 *
 */
public class ProcessEmail {
	      
	public void enviarEmailArquivo(String assunto, String mensagem, String emailTo, String caminhoArquivo) throws MessagingException {
		
		 Properties props = new Properties();
		 
		 /* Parametros de conexao com servidor Gmail */
		 props.put("mail.smtp.host", "smtp.gmail.com");
		 props.put("mail.smtp.socketFactory.port", "465");
		 props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		 props.put("mail.smtp.auth", "true");
		 props.put("mail.smtp.port", "465");

		 Session session = Session.getDefaultInstance(props,
				 new javax.mail.Authenticator() {
		         protected PasswordAuthentication getPasswordAuthentication()
		                     {
		                           return new PasswordAuthentication(AuxiliarConstantes.EMAIL, "despacho");
		                     }
		                });

		    /** Ativa Debug para sessao */
		    session.setDebug(true);

		    // Define message
		    MimeMessage message = 
		      new MimeMessage(session);
		    message.addRecipient(
		              Message.RecipientType.TO, 
		              new InternetAddress(emailTo));

		    message.setSubject(
		      assunto);

		    // create the message part 
		    MimeBodyPart messageBodyPart = 
		      new MimeBodyPart();
		    
		    //fill message
		    messageBodyPart.setText(mensagem);
		    Multipart multipart = new MimeMultipart();
		    multipart.addBodyPart(messageBodyPart);
		    
		    // Part two is attachment
		    messageBodyPart = new MimeBodyPart();
		    DataSource source = 
		      new FileDataSource(caminhoArquivo);
		    messageBodyPart.setDataHandler(
		      new DataHandler(source));
		    messageBodyPart.setFileName(caminhoArquivo);
		    multipart.addBodyPart(messageBodyPart);
		    
		    // Put parts in message
		    message.setContent(multipart);
		    // Send the message
		    Transport.send( message );
	}
	
	public static void main(String args[]) throws MessagingException {
		
		ProcessEmail email = new ProcessEmail();
		email.enviarEmailArquivo("Relatorio de erros", "Teste de envio de email com anexo pelo SISCE!", "johanessevero@gmail.com", "HRT-UNIDADE_DE_GINECOLOGIA_E_OBSTETRICIA6_2012_RELATORIO_DE_ERROS.pdf");
		
	}
}