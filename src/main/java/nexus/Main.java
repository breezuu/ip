package nexus;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Nexus using FXML.
 */
public class Main extends Application {

    private final Nexus nexus = new Nexus("data/databank.txt");

    @Override
    public void start(Stage stage) {
        try {
            // Set the initial and minimum dimensions
            stage.setWidth(600.0);
            stage.setHeight(700.0);

            stage.setMinWidth(500.0);
            stage.setMinHeight(600.0);

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            // Set title
            stage.setTitle("Nexus");

            // Inject the Nexus instance
            fxmlLoader.<MainWindow>getController().setNexus(nexus);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
