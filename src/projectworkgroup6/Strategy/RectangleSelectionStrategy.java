package projectworkgroup6.Strategy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import projectworkgroup6.Model.Shape;

public class RectangleSelectionStrategy extends SelectionStrategy {
    @Override
    public void drawSelectionBorder(GraphicsContext gc, Shape shape) {
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2.0);
        gc.strokeRect(shape.getXc(), shape.getYc(), shape.getDim1(), shape.getDim2());
    }

    @Override
    public void drawHandles(GraphicsContext gc, Shape shape) {
        double x = shape.getXc();
        double y = shape.getYc();
        double w = shape.getDim1();
        double h = shape.getDim2();
        double size = 6;
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        drawHandle(gc, x, y, size);
        drawHandle(gc, x + w, y, size);
        drawHandle(gc, x, y + h, size);
        drawHandle(gc, x + w, y + h, size);
    }

    @Override
    public void drawMoveButton(GraphicsContext gc, Shape shape) {

        double buttonX = getMoveButtonX(shape);
        double buttonY = getMoveButtonY(shape);


        gc.setFill(Color.WHITE);
        gc.fillRect(buttonX, buttonY, buttonWidth, buttonHeight);

        gc.setStroke(Color.BLUE.brighter());
        gc.strokeOval(buttonX, buttonY, buttonWidth, buttonHeight);

        gc.setFill(Color.BLACK);
        gc.fillText("↔", buttonX + 3 , buttonY + 15);
    }

    @Override
    public double getMoveButtonX(Shape shape) {
        return shape.getXc() + (shape.getDim1() - buttonWidth) / 2;
    }

    public double getMoveButtonY(Shape shape) {
        return shape.getYc() + shape.getDim2() + 5; // appena sotto la figura
    }


}

