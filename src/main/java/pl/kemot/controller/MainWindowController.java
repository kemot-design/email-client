package pl.kemot.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import pl.kemot.EmailManager;
import pl.kemot.controller.services.MessageRendererService;
import pl.kemot.model.EmailMessage;
import pl.kemot.model.EmailTreeItem;
import pl.kemot.model.SizeInteger;
import pl.kemot.view.ViewFactory;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    @FXML
    private TreeView<String> emailsTreeView;

    @FXML
    private TableView<EmailMessage> emailsTableView;


    @FXML
    private TableColumn<EmailMessage, String> senderCol;

    @FXML
    private TableColumn<EmailMessage, String> subjectCol;

    @FXML
    private TableColumn<EmailMessage, String> recipientCol;

    @FXML
    private TableColumn<EmailMessage, Date> dateCol;

    @FXML
    private TableColumn<EmailMessage, SizeInteger> sizeCol;

    @FXML
    private WebView emailsWebView;

    private MessageRendererService messageRendererService;

    public MainWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlFileName) {
        super(emailManager, viewFactory, fxmlFileName);
    }

    @FXML
    void optionsAction() {
        viewFactory.showOptionsWindow();
    }

    @FXML
    void openAction() {
        
    }

    @FXML
    void addAccountAction() {
        viewFactory.showLoginWindow();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpEmailsTreeView();
        // Here we bind columns in table vie with corresponding property values from EmailMessage class
        setUpEmailTableView();
        //In this method we specify which folder is selected to display messages in table view
        setUpFolderSelection();
        setUpBoldRows();
        setUpMessageRendererService();
        // we indicate which message we clicked to display
        setUpMessageSelection();
    }

    private void setUpMessageSelection() {
        emailsTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                EmailMessage emailMessage = emailsTableView.getSelectionModel().getSelectedItem();
                messageRendererService.setEmailMessage(emailMessage);
                // we need to use restart because we will invoke this many times and with start we will be unable to do this - it would produce an error
                messageRendererService.restart();
            }
        });
    }

    private void setUpMessageRendererService() {
        messageRendererService = new MessageRendererService(emailsWebView.getEngine());
    }

    private void setUpBoldRows() {
        emailsTableView.setRowFactory(new Callback<TableView<EmailMessage>, TableRow<EmailMessage>>() {
            @Override
            public TableRow<EmailMessage> call(TableView<EmailMessage> param) {
                return new TableRow<EmailMessage>(){
                    @Override
                    protected void updateItem(EmailMessage item, boolean empty){
                        super.updateItem(item, empty);
                        if(item != null){
                            if(item.isRead()){
                                setStyle("");
                            } else {
                                setStyle("-fx-font-weight: bold");
                            }
                        }
                    }
                };
            }
        });
    }

    private void setUpFolderSelection() {
        emailsTreeView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                EmailTreeItem<String> selectedFolder = (EmailTreeItem<String>) emailsTreeView.getSelectionModel().getSelectedItem();
                //if we will click below our folders the selected folder will be null
                if(selectedFolder != null){
                    //if we have chosen a correct folder we set items in table view with observable list of our messages from EmailTreeItem class
                    emailsTableView.setItems(selectedFolder.getEmailMessages());
                }
            }
        });
    }

    private void setUpEmailTableView() {
        //For each table column we need a new cellValueFactory, this is how javaFX works
        //in the PropertyValueFactory we use main type of object in table view and the specific SimpleXXXProperty type and then name from that class
        senderCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("sender"));
        subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("subject"));
        recipientCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("recipient"));
        dateCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, Date>("date"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, SizeInteger>("size"));
    }

    private void setUpEmailsTreeView() {
        TreeItem<String> foldersRoot = emailManager.getFoldersRoot();
        emailsTreeView.setRoot(foldersRoot);
        emailsTreeView.setShowRoot(false);
    }
}
