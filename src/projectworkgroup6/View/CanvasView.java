package projectworkgroup6.View;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import projectworkgroup6.Controller.CanvasController;
import projectworkgroup6.Model.Group;
import projectworkgroup6.Model.Shape;

import java.util.*;

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

        canvas.setOnMouseClicked(e -> {
            double x = e.getX(); //mi prendo le coordinate del click
            double y = e.getY();

            if (controller.hasShapeToInsert()) { //se sto cercando di inserire una shape personalizzata (legata al click del bottone nel toolbar)
                controller.pasteShapeToCanvas(x, y); //non passo semplicemente il controllo del click al canvasController ma invoco questo metodo

                /*in questo modo riesco a distinguere due tipologie di click diversi ma senza creare un nuovo stato per l'inserimento delle figure personalizzate*/
                return;
            }

            controller.handleCanvasClick(e, x, y);
        });
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

        // Crea una lista ordinata per layer crescente (dal fondo al primo piano)
        List<ShapeView> sortedViews = new ArrayList<>(views);
        sortedViews.sort(Comparator.comparingInt(ShapeView::getLayer));

        for (ShapeView v : sortedViews) {
            v.draw(gc);
        }
    }

    public void renderTemporaryGroup(ShapeView groupView) {
        groupView.draw(gc);
    }
}
