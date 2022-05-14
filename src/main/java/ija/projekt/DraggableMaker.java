package ija.projekt;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;

/**
 * @author Magdalena Bellayova
 * Class for dragging actions
 */

public class DraggableMaker {
    private double mouseAnchorX;
    private double mouseAnchorY;
    public void makeDraggable(Node node, Pane class_pane){
        node.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getSceneX();
            mouseAnchorY = mouseEvent.getSceneY();
            node.setLayoutX (mouseAnchorX);
            node.setLayoutY (mouseAnchorY);
            node.relocate(mouseAnchorX,mouseAnchorY);
        });
        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutX (mouseEvent.getSceneX() - mouseAnchorX);
            node.setLayoutY (mouseEvent.getSceneY() - mouseAnchorY);

        });

        node.setOnMouseReleased(mouseEvent -> {
            node.setLayoutX (mouseEvent.getSceneX() - mouseAnchorX);
            node.setLayoutY (mouseEvent.getSceneY() - mouseAnchorY);
            node.relocate(mouseEvent.getSceneX() - mouseAnchorX,mouseEvent.getSceneY() - mouseAnchorY);
        });
    }
}
