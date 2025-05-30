package projectworkgroup6.View;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import projectworkgroup6.Controller.CanvasController;
import projectworkgroup6.Model.Shape;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CanvasView {

    private Canvas canvas;


    private ScrollPane scrollPane;

    private AnchorPane canvasPane;

    private GraphicsContext gc;

    public final CanvasController controller;

    private Scene scene;

    public CanvasView(Canvas canvas, AnchorPane canvasPane, CanvasController controller, Scene scene) {

        this.canvas = canvas;
        this.canvasPane = canvasPane;
        this.gc = canvas.getGraphicsContext2D();
        this.controller = controller;
        this.scene = scene;
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        canvas.setOnMouseClicked(e -> controller.handleCanvasClick(e, e.getX(), e.getY()));
        canvas.setOnMousePressed(e -> controller.handlePression(e.getX(), e.getY()));

        canvas.setOnMouseDragged(e -> controller.handleMouseDragged(e.getX(), e.getY()));
        canvas.setOnMouseReleased(e -> controller.handleMouseReleased(e.getX(), e.getY()));

        canvas.getScene().setOnKeyPressed(controller::handleDelete);
        canvas.getScene().setOnKeyTyped(controller::handleKeyTyped);
    }

    private Collection<ShapeView> currentViews;

    public void render(Collection<ShapeView> views) {
        this.currentViews = views; // salva per eventuali ridisegni
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (ShapeView v : views) {
            v.draw(gc);
        }
    }

}
