import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;

public final class Popups {

    private static final Popup popup = new Popup();
    private static Button btn1;
    private static Button btn2;

    // Global Popup
    private static void popup(String message, String btn1Txt, String btn2Txt) {
        // Popup Container
        VBox vBox = new VBox();

        // Setting Properties
        vBox.getStyleClass().add("popup");

        // Creating Text
        Text text = new Text( message);
        text.getStyleClass().add("text");

        // Adding Text to Popup
        vBox.getChildren().add(text);

        //Buttons Container
        HBox btnContainer = new HBox();

        // Setting Properties of Buttons Container
        btnContainer.getStyleClass().add("btn-container");

        btn1 = new Button(btn1Txt);
        btn2 = new Button(btn2Txt);

        btn1.getStyleClass().add("btn");
        btn2.getStyleClass().addAll("btn", "exit-btn");

        // Adding Buttons to Buttons Container
        btnContainer.getChildren().addAll(btn1, btn2);

        // Adding Button Container to vBox
        vBox.getChildren().add(btnContainer);

        // Adding vBox to Popup
        popup.getContent().add(vBox);

        // Showing Popup
        popup.show(Main.getPrimaryStage());
    }

    // Start Popup
    public static void startPopup(AnimationTimer timer) {
        popup("Welcome to Hanoi Game!", "Start Game", "Exit");

        /*----- Button Actions -----*/

        // Start Button
        btn1.setOnAction(event -> {
            popup.hide();
            timer.start();
        });

        // Exit Button
        btn2.setOnAction(event -> System.exit(0));
    }

    // Congratulation Popup
    public static void congratulationPopup(Tower source, Tower auxiliary, Tower destination,AnimationTimer timer) {
        popup("Congratulations!", "New Game", "Exit");

        //   Button Actions

        // New Game Button
        btn1.setOnAction(event -> {
            popup.hide();
            if(source.isEmpty())
                source.emptyTower();
            if(auxiliary.isEmpty())
                auxiliary.emptyTower();
            if(destination.isEmpty())
                destination.emptyTower();

            Frame.initializeSource();
            timer.start();
        });

        // Exit Button
        btn2.setOnAction(event -> System.exit(0));
    }

    // Reset Popup
    public static void restartPopup(Tower source, Tower auxiliary, Tower destination,AnimationTimer timer, Text movesUsed) {
        Popups.popup("Are you sure you want to restart?", "Yes", "No");

        // Button Actions

        // Reset Button
        btn1.setOnAction(e->{
            if(source.isEmpty())
                source.emptyTower();
            if(auxiliary.isEmpty())
                auxiliary.emptyTower();
            if(destination.isEmpty())
                destination.emptyTower();

            Frame.initializeSource();
            movesUsed.setText("Moves Used: " + 0);
            popup.hide();
            timer.start();
        });

        // Close Button
       btn2.setOnAction(e->{
            popup.hide();
            timer.start();
        });
    }

    // Exit Popup
    public static void exitPopup(AnimationTimer timer) {
        popup("Are you sure you want to exit?", "Yes", "No");

        // Button Actions

        // Exit Button
        btn1.setOnAction(e-> System.exit(0));

        // Close Button
        btn2.setOnAction(e->{
            popup.hide();
            timer.start();
        });

        popup.show(Main.getPrimaryStage());
    }
}
