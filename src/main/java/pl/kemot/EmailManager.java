package pl.kemot;

import javafx.scene.control.TreeItem;
import pl.kemot.controller.services.FetchFoldersService;
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
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(), emailTreeItem);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(emailTreeItem);
    }

}
