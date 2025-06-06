package projectworkgroup6.View;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import projectworkgroup6.Model.Rectangle;

public class RectangleView extends ShapeView {

    public RectangleView(Rectangle shape) {
        super(shape);
    }

    @Override
    public void draw(GraphicsContext gc) {

        Rectangle rect = (Rectangle) shape;

        double centerX = rect.getXc() + rect.getDim1() / 2.0;
        double centerY = rect.getYc() + rect.getDim2() / 2.0;
        double angle = rect.getRotation();

        gc.save();

        gc.translate(centerX, centerY);
        gc.rotate(angle);
        gc.translate(-centerX, -centerY);

        gc.setStroke(rect.getBorder().toColor());
        gc.setFill(rect.getFill().toColor());


        gc.strokeRect(rect.getXc(), rect.getYc(), rect.getDim1(), rect.getDim2());
        gc.fillRect(rect.getXc(), rect.getYc(), rect.getDim1(), rect.getDim2());

        gc.restore();

    }
}



