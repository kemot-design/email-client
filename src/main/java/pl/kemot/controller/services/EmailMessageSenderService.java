package pl.kemot.controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pl.kemot.controller.MessageSendResult;
import pl.kemot.model.EmailAccount;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailMessageSenderService extends Service<MessageSendResult> {

    private EmailAccount senderEmailAccount;
    private String recipient;
    private String subject;
    private String content;

    public EmailMessageSenderService(EmailAccount senderEmailAccount, String recipient, String subject, String content) {
        this.senderEmailAccount = senderEmailAccount;
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
    }

    @Override
    protected Task<MessageSendResult> createTask() {
        return new Task<MessageSendResult>() {
            @Override
            protected MessageSendResult call() throws Exception {
                try{
                    // Create the message
                    MimeMessage mimeMessage = new MimeMessage(senderEmailAccount.getSession());
                    mimeMessage.setFrom(senderEmailAccount.getAdress());
                    mimeMessage.addRecipients(Message.RecipientType.TO, recipient);
                    mimeMessage.setSubject(subject);
                    // Set the content
                    Multipart multipart = new MimeMultipart();
                    BodyPart messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setContent(content, "text/html");
                    multipart.addBodyPart(messageBodyPart);
                    mimeMessage.setContent(multipart);
                    //Sending the message
                    Transport transport = senderEmailAccount.getSession().getTransport();
                    transport.connect(
                            senderEmailAccount.getProperties().getProperty("outgoingHost"),
                            senderEmailAccount.getAdress(),
                            senderEmailAccount.getPassword()
                    );
                    transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
                    transport.close();

                    return MessageSendResult.SUCCESS;
                } catch (MessagingException e){
                    e.printStackTrace();
                    return MessageSendResult.FAILED_BY_PROVIDER;
                } catch (Exception e){
                    e.printStackTrace();
                    return MessageSendResult.FAILED_BY_UNEXPECTED_ERROR;
                }
            }
        };
    }
}
