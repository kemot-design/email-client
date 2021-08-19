package pl.kemot;

import javafx.scene.control.TreeItem;
import pl.kemot.model.EmailAccount;
import pl.kemot.model.EmailTreeItem;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;

public class EmailManager {

    //Folder handling
    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<>("");

    public EmailTreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    public void addEmailAccount(EmailAccount emailAccount){
        EmailTreeItem<String> emailTreeItem = new EmailTreeItem<>(emailAccount.getAdress());
        emailTreeItem.setExpanded(true);
            emailTreeItem.getChildren().add(new EmailTreeItem<String>("INBOX"));
            emailTreeItem.getChildren().add(new EmailTreeItem<String>("Sent"));
            emailTreeItem.getChildren().add(new EmailTreeItem<String>("Inportant"));
            emailTreeItem.getChildren().add(new EmailTreeItem<String>("Spam"));
        foldersRoot.getChildren().add(emailTreeItem);
    }

    public void showEmailsFolders(EmailAccount emailAccount) throws MessagingException {
        Store store = emailAccount.getStore();
        System.out.println("Stor: " + store);

        Folder[] folders = store.getDefaultFolder().list();
        for (Folder folder : folders) {
            System.out.println(">> " + folder.getName());
        }
    }
}
