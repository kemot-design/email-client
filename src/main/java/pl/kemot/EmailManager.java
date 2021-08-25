package pl.kemot;

import javafx.scene.control.TreeItem;
import pl.kemot.controller.services.FetchFoldersService;
import pl.kemot.controller.services.FolderUpdaterService;
import pl.kemot.model.EmailAccount;
import pl.kemot.model.EmailMessage;
import pl.kemot.model.EmailTreeItem;

import javax.mail.Flags;
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

    private FolderUpdaterService folderUpdaterService;

    private EmailMessage selectedMessage;
    private EmailTreeItem<String> selectedFolder;

    public void setSelectedMessage(EmailMessage selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public void setSelectedFolder(EmailTreeItem<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
    }

    public EmailManager(){
        this.folderUpdaterService = new FolderUpdaterService(folderList);
        folderUpdaterService.start();
    }

    //Folder handling
    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<>("");

    public EmailTreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    public void addEmailAccount(EmailAccount emailAccount){
        EmailTreeItem<String> emailTreeItem = new EmailTreeItem<>(emailAccount.getAdress());
        emailTreeItem.setExpanded(true);
        // as the arrays and lists as parameters are passed by value this means that the reference to the list is copied and the reference to list is an adress of it, so the copy of an adress still indicates to the same spot in the memory, so when we update the passed list in the method it affects also the original list
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(), emailTreeItem, folderList);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(emailTreeItem);
    }


    public void setMessageToRead() {
        try {
            selectedMessage.setRead(true);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN,true);
            selectedFolder.decrementUnreadMessageCount();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
