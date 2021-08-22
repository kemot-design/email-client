package pl.kemot.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class EmailTreeItem<String> extends TreeItem<String> {

    private String name;
    //We will store our email messages in this class in an observable list from javafx.collections
    private ObservableList<EmailMessage> emailMessages;

    public EmailTreeItem(String name) {
        super(name);
        this.name = name;
        // since ObservableList in an interface we have to use FXCollection helper class to make a new instance of observable list
        this.emailMessages = FXCollections.observableArrayList();
    }

    public void addEmail(Message message) throws MessagingException {
        boolean messageIsRead = message.getFlags().contains(Flags.Flag.SEEN);
        EmailMessage emailMessage = new EmailMessage(
                message.getSubject(),
                message.getFrom()[0].toString(),
                message.getRecipients(MimeMessage.RecipientType.TO)[0].toString(),
                message.getSize(),
                message.getSentDate(),
                messageIsRead,
                message
        );
        this.emailMessages.add(emailMessage);
        System.out.println("Added to " + name + " " + message.getSubject());
    }

}
