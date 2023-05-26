import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {


    public static void main(String[] args) {
        launch();
    }
    private static Stage primaryStage = new Stage();
    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        Scene scene = new Scene(new Frame(), 600, 400);
        scene.getStylesheets().add("Properties.css");

        stage.setTitle("Hanoi Game");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }
}