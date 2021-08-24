package pl.kemot;

import javafx.scene.control.TreeItem;
import pl.kemot.controller.services.FetchFoldersService;
import pl.kemot.model.EmailAccount;
import pl.kemot.model.EmailTreeItem;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;
import java.util.ArrayList;
import java.util.List;

public class EmailManager {

    private List<Folder> folderList = new ArrayList<>();

    public List<Folder> getFolderList() {
        return this.folderList;
    }

    //Folder handling
    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<>("");

    public EmailTreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    public void addEmailAccount(EmailAccount emailAccount){
        EmailTreeItem<String> emailTreeItem = new EmailTreeItem<>(emailAccount.getAdress());
        emailTreeItem.setExpanded(true);
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(), emailTreeItem, folderList);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(emailTreeItem);
    }

}
