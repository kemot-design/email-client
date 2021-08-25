package pl.kemot.controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pl.kemot.model.EmailTreeItem;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;
import java.util.ArrayList;
import java.util.List;

public class FetchFoldersService extends Service<Void> {

    private Store store;
    private EmailTreeItem<String> foldersRoot;
    private List<Folder> folderList;

    public FetchFoldersService(Store store, EmailTreeItem<String> foldersRoot, List<Folder> folderList) {
        this.store = store;
        this.foldersRoot = foldersRoot;
        this.folderList = folderList;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                fetchFolders();
                return null;
            }
        };
    }

    private void fetchFolders() throws MessagingException {
        Folder[] folders = store.getDefaultFolder().list();
        handleFolders(folders, foldersRoot);
    }

    private void handleFolders(Folder[] folders, EmailTreeItem<String> foldersRoot) throws MessagingException {
        for (Folder folder : folders){
            folderList.add(folder);
            EmailTreeItem<String> emailTreeItem = new EmailTreeItem<>(folder.getName());
            foldersRoot.getChildren().add(emailTreeItem);
            foldersRoot.setExpanded(true);
            fetchMessageOnFolder(folder, emailTreeItem);
            addMessageListenerToFolder(folder, emailTreeItem);
            // Now we check if the folder holds some subfolders
            if(folder.getType() == Folder.HOLDS_FOLDERS){
                Folder[] subFolders = folder.list();
                handleFolders(subFolders, emailTreeItem);
            }
        }
    }

    private void addMessageListenerToFolder(Folder folder, EmailTreeItem<String> emailTreeItem) {
        folder.addMessageCountListener(new MessageCountListener() {
            @Override
            public void messagesAdded(MessageCountEvent event) {
                // new messages that was sent after starting of our client will be strored in an array in event, so we have to iterate through them to catch em all
                for (int i = 0 ; i < event.getMessages().length ; i++){
                    try {
                        // we get the new messages from folder cuz they are already there but their indexes are the last ones
                        Message message = folder.getMessage(folder.getMessageCount() - i);
                        emailTreeItem.addEmailToTop(message);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void messagesRemoved(MessageCountEvent messageCountEvent) {
                System.out.println("Messaje deleted");
            }
        });
    }

    private void fetchMessageOnFolder(Folder folder, EmailTreeItem<String> emailTreeItem) {
        Service fetchMessagesService = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        if(folder.getType() != Folder.HOLDS_FOLDERS){
                            //we have to open folder first
                            folder.open(Folder.READ_WRITE);
                            int messageCount = folder.getMessageCount();
                            for(int i = messageCount ; i > 0 ; i--){
                                emailTreeItem.addEmail(folder.getMessage(i));
                            }
                        }
                        return null;
                    }
                };
            }
        };
        fetchMessagesService.start();
    }
}
