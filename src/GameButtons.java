import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public final class GameButtons {
    private static Tower source;
    private static Tower auxiliary;
    private static Tower destination;
    private static AnimationTimer timer;

    // Constructor
    GameButtons(Tower source, Tower auxiliary, Tower destination, AnimationTimer timer) {
        GameButtons.source = source;
        GameButtons.auxiliary = auxiliary;
        GameButtons.destination = destination;
        GameButtons.timer = timer;
    }

    // Restart Button
    public Button resetButton(Text movesUsed) {
        Button restartBtn = new Button("Restart");

        restartBtn.getStyleClass().addAll("btn", "restart-btn");
        restartBtn.setOnAction(e->{
            timer.stop();
            Popups.restartPopup(source, auxiliary, destination, timer, movesUsed);
        });

        return restartBtn;
    }

    // Exit Button
    public Button exitBtn() {
        Button exitBtn = new Button("Exit");

        exitBtn.getStyleClass().add("exit-btn");
        exitBtn.getStyleClass().addAll("btn", "exit-btn");

//        exitBtn.setPrefSize(80, 40);
        exitBtn.setOnAction(e->{
            timer.stop();
            Popups.exitPopup(timer);
        });

        return exitBtn;
    }
    }
