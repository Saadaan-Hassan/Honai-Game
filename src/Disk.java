import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicReference;

public class Disk extends Rectangle{

    // Constructors
    Disk(int w, int h){
        SecureRandom random = new SecureRandom();
        setWidth(w);
        setHeight(h);
        setFill(new Color(random.nextDouble(1), random.nextDouble(1), random.nextDouble(1), 1));
        setTranslateZ(1);
    }

    Disk(Disk d){
        setWidth(d.getWidth());
        setHeight(d.getHeight());
        setFill( d.getFill());
        setTranslateZ(1);
    }

    // Variables
    private final AtomicReference<Double> orgSceneX = new AtomicReference<>((double) 0); // Initialize orgScene X
    private final AtomicReference<Double> orgSceneY = new AtomicReference<>((double) 0); // Initialize orgScene Y
    private final AtomicReference<Double> orgTranslateX = new AtomicReference<>((double) 0); // Initialize orgTranslate X
    private final AtomicReference<Double> orgTranslateY = new AtomicReference<>((double) 0); // Initialize orgTranslate Y

    private static int count = 0; // Initialize counter for moves used

    // Methods

    // Dragging the disk function
    public void drag(Tower source, Tower tower1, Tower tower2, Text moves) {
        setOnMousePressed(e -> {
            orgSceneX.set(e.getSceneX());
            orgSceneY.set(e.getSceneY());
            orgTranslateX.set(getTranslateX());
            orgTranslateY.set(getTranslateY());
        });


        setOnMouseDragged(event -> {
            double offsetX = event.getSceneX() - orgSceneX.get();   // Getting the offset X
            double offsetY = event.getSceneY() - orgSceneY.get();   // Getting the offset Y
            double newTranslateX = orgTranslateX.get() + offsetX;   // Adding offset to translate X
            double newTranslateY = orgTranslateY.get() + offsetY;   // Adding offset to translate Y

            // Setting the new position
            setTranslateX(newTranslateX);
            setTranslateY(newTranslateY);
        });

        setOnMouseReleased(event -> {
            if (isDiskWithinRegion(tower1)) {
                if (isValidMove(tower1)) {
                    source.moveTopDisk(tower1);
                    moves.setText("Moves Used: " + (++count));
                }
            } else if (isDiskWithinRegion(tower2)) {
                if (isValidMove(tower2)) {
                    source.moveTopDisk(tower2);
                    moves.setText("Moves Used: " + (++count));
                }
            }
            else {
                // Move the disk back to its original position
                setTranslateX(orgTranslateX.get());
                setTranslateY(orgTranslateY.get());
            }
        });
    }

    // Check if the disk is within the tower's bounds
    public boolean isDiskWithinRegion(Tower tower) {
        Point2D towerLocation = tower.localToScene(0, 0); // Get the tower's location on the screen

        double x = towerLocation.getX(); // Get the tower's x coordinate
        double y = towerLocation.getY(); // Get the tower's y coordinate
        double width = tower.getWidth(); // Get the tower's width
        double height = tower.getHeight(); // Get the tower's height

        Point2D diskLocation = localToScene(0, 0); // Get the disk's location on the screen

        double diskX = diskLocation.getX(); // Get the disk's x coordinate
        double diskY = diskLocation.getY(); // Get the disk's y coordinate
        double diskWidth = getWidth(); // Get the disk's width
        double diskHeight = getHeight(); // Get the disk's height

        // Check if the center point of the disk is within the tower's bounds
        double centerX = diskX + diskWidth / 2;
        double centerY = diskY + diskHeight / 2;

        return centerX >= x && centerX <= x + width && centerY >= y && centerY <= y + height;
    }

    // Check if the disk being moved is smaller than the top disk of the tower
    private boolean isValidMove(Tower target) {
        return target.getDisksSizes().isEmpty() || target.getTopDisk().getWidth() > getWidth();
    }
}