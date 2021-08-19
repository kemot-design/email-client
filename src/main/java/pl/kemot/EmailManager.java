package pl.kemot;

import javafx.scene.control.TreeItem;
import pl.kemot.model.EmailAccount;

public class EmailManager {

    //Folder handling
    private TreeItem<String> foldersRoot = new TreeItem<>("");

    public TreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    public void addEmailAccount(EmailAccount emailAccount){
        TreeItem<String> emailTreeItem = new TreeItem<>(emailAccount.getAdress());
        emailTreeItem.setExpanded(true);
            emailTreeItem.getChildren().add(new TreeItem<String>("INBOX"));
            emailTreeItem.getChildren().add(new TreeItem<String>("Sent"));
            emailTreeItem.getChildren().add(new TreeItem<String>("Inportant"));
            emailTreeItem.getChildren().add(new TreeItem<String>("Spam"));
        foldersRoot.getChildren().add(emailTreeItem);
    }
}
