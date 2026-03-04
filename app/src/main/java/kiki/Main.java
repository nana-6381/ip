package kiki;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import kiki.ui.MainWindow;

/**
 * A GUI for Kiki using FXML.
 */
public class Main extends Application {

    private Kiki kiki = new Kiki("data/kiki.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setKiki(kiki);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
