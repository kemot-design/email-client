package pl.kemot.controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pl.kemot.controller.MessageSendResult;
import pl.kemot.model.EmailAccount;

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
                return null;
            }
        };
    }
}
