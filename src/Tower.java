import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Stack;

public class Tower extends StackPane {
    private final VBox diskContainer; // Declare diskContainer
    private final Stack<Integer> disksSizes; // Declare disksSizes Stack
    private final Stack<Disk> disks; // Declare disks Stack

    public Tower() {
        // Creating the tower's stick
        Rectangle tower = new Rectangle(10, 200);
        tower.getStyleClass().add("tower");

        // Container for holding disks
        diskContainer = new VBox();

        // Setting Properties of diskContainer
        diskContainer.getStyleClass().add("disk-container");

        // Adding the container and tower's stick to the tower i.e. a StackPane
        getChildren().addAll(tower, diskContainer);


        // Initialize disks and DisksSizes
        disksSizes = new Stack<>();
        disks = new Stack<>();
    }

    /*----- Adding disks to the tower Functions -----*/
    public void addDisk(int diskSize) {
        // Adding disk's size to the disksSizes Stack
        disksSizes.push(diskSize);

        // Creating disk
        Disk disk = new Disk(diskSize * 30, 12);
        disks.push(disk); // Adding disk to the disks Stack

        // Adding disks to the diskContainer
        diskContainer.getChildren().add(0, disk);
    }

    public void addDisk(int diskSize, Disk disk) {
        // Adding disk's size to the disksSizes Stack
        disksSizes.push(diskSize);

        // Creating disk
        Disk newDisk = new Disk(disk);
        disks.push(newDisk); // Adding disk to the disks Stack

        // Adding disks to the container
        diskContainer.getChildren().add(0, newDisk);
    }

    // Removing disks from the tower
    public void emptyTower(){
        for (Disk d :
                disks) {
            diskContainer.getChildren().remove(d);
        }
        disks.clear(); // Clearing disks Stack
        disksSizes.clear(); // Clearing disksSizes Stack
    }

    // Moving the top disk to the destination
    public void moveTopDisk(Tower destination) {
        if(!disksSizes.isEmpty()) {
            int topDiskSize = disksSizes.pop();
            Disk topDisk = disks.pop();
            destination.addDisk(topDiskSize, topDisk);

            diskContainer.getChildren().remove(0);
        }
    }

    // Moving disks from one tower to another
    public void moveDisk(Tower tower1, Tower tower2, Text moves) {
        if (!disks.isEmpty()) {
            Disk topDisk = (Disk) diskContainer.getChildren().get(0);
            topDisk.drag(this, tower1, tower2, moves);
        }
    }

    /*----- Getters -----*/

    // Get disks sizes
    public Stack<Disk> getDisksSizes() {
        return disks;
    }

    // Get top disk
    public Disk getTopDisk(){
        return disks.peek();
    }

    // Check if the tower is empty
    public boolean isEmpty(){
        return !disks.isEmpty();
    }
}