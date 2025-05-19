package projectworkgroup6.Strategy;

import javafx.scene.canvas.GraphicsContext;
import projectworkgroup6.Model.Shape;

public abstract class SelectionStrategy {

    double buttonWidth = 20;
    double buttonHeight = 20;


    public abstract void drawSelectionBorder(GraphicsContext gc, Shape shape);
    public abstract void drawHandles(GraphicsContext gc, Shape shape);

    public abstract void drawMoveButton(GraphicsContext gc, Shape shape);

    public abstract double getMoveButtonX(Shape shape);
    public abstract double getMoveButtonY(Shape shape);

    protected void drawHandle(GraphicsContext gc, double cx, double cy, double size) {
        double half = size / 2;
        gc.fillOval(cx - half, cy - half, size, size);
        gc.strokeOval(cx - half, cy - half, size, size);
    }




}
