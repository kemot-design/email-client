package pl.kemot;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Jesli uzywamy Mavena to pliki jak fxml czy zdjecia dajemy do odpowiednich folderow w resources, bo getClass() zwraca classpath do skompilowanego pliku ktory jest przeciez w target, a folder resources jest chyba wlasnie po to zeby tam dodawac takie pliki, bo tutaj to dziala
        Parent root = FXMLLoader.load(getClass().getResource("view/first.fxml"));

        Scene scene = new Scene(root, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
