import javafx.animation.AnimationTimer;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.concurrent.atomic.AtomicInteger;


public final class Frame extends GridPane{

    //Creating Towers
    private static final Tower source = new Tower();
    private static final Tower auxiliary = new Tower();
    private static final Tower destination = new Tower();
    private static AnimationTimer timer = null;

    Frame(){
        HBox towerContainer = new HBox();
        towerContainer.getChildren().addAll(source, auxiliary, destination);
        towerContainer.getStyleClass().add("tower-container");
        //Adding Towers to grid

        add(towerContainer, 0, 1, 3, 1);

        // Initialize source tower
        initializeSource();

        // Create minimum moves text
        Text minMoves = new Text("Minimum Moves: " + (Math.pow(2, 3) - 1));
        minMoves.getStyleClass().add("text");
        add(minMoves, 2, 0); // Adding minMoves text to the frame

        // Create moves used text
        Text movesUsed = new Text("Moves Used: " + 0);
        movesUsed.getStyleClass().add("text");
        add(movesUsed, 1, 0); // Adding movesUsed text to the frame

        // Create source disks size
        AtomicInteger sourceDisksSize = new AtomicInteger(source.getDisksSizes().size());

        // Create slider
        Slider slider = movesSlider();

        slider.valueProperty().addListener((obs, oldVal, newVal)->{
            slider.setValue(newVal.intValue());

            if(source.isEmpty())
                source.emptyTower();
            if(auxiliary.isEmpty())
                auxiliary.emptyTower();
            if(destination.isEmpty())
                destination.emptyTower();

           for (int i = newVal.intValue(); i > 0; i--) {
               source.addDisk(i);
           }

           // Update minMoves and movesUsed text values when slider's value is changed
            minMoves.setText("Minimum Moves: " + (Math.pow(2, newVal.intValue()) - 1));
            movesUsed.setText("Moves Used: " + 0);
            sourceDisksSize.set(source.getDisksSizes().size());
        });

        // Adding slider to the frame
        add(slider, 0, 0);

        // Set flag to true
        final boolean[] flag = {true};

        // Create a new AnimationTimer
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                // Showing Popup of Starting the Game
                if(flag[0]) {
                    stop();
                    Popups.startPopup(timer);
                    flag[0] = false;
                }

                // Move disks from source to destination using the auxiliary tower
                source.moveDisk(auxiliary, destination, movesUsed);
                auxiliary.moveDisk(source, destination, movesUsed);
                destination.moveDisk(source, auxiliary, movesUsed);

                // Check if the game is over
                if (destination.getDisksSizes().size() == sourceDisksSize.get()) {
                    Popups.congratulationPopup(source, auxiliary, destination, timer);
                    stop();
                }
            }
        };

        // Adding Buttons on the Frame
        GameButtons gameButtons = new GameButtons(source, auxiliary, destination, timer);

        // Add Buttons on the Frame
        add(gameButtons.resetButton(movesUsed), 0,3);
        add(gameButtons.exitBtn(), 1,3);

        // Set the frame's style
        getStyleClass().add("frame");

        // Start the timer
        timer.start();
    }

    // Initialize source
    public static void initializeSource(){
        for (int i = 3; i > 0; i--) {
            source.addDisk(i);
        }
    }

    // Slider
    public static Slider movesSlider(){
        Slider slider = new Slider();

        // Set Slider properties
        slider.getStyleClass().add("slider");
        slider.setMin(3);
        slider.setMax(10);

        return slider;
    }
}
