package projectworkgroup6.View;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import projectworkgroup6.Model.Line;

public class LineView extends ShapeView {

    public LineView(Line shape) {
        super(shape);
    }

    @Override
    public void draw(GraphicsContext gc) {

        Line line = (Line) shape;

        double centerX = (line.getXc() + line.getX2()) / 2.0;
        double centerY = (line.getYc() + line.getY2()) / 2.0;
        double angle = line.getRotation();

        gc.save();
        gc.translate(centerX, centerY);
        gc.rotate(angle);
        gc.translate(-centerX, -centerY);

        gc.setStroke(line.getBorder().toColor());
        gc.strokeLine(line.getXc(), line.getYc(), line.getX2(), line.getY2());

        gc.restore();
    }
}
