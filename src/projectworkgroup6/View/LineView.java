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

        //
        gc.setStroke(line.getBorder().toColor());

        gc.strokeLine(line.getXc(), line.getYc(), line.getDim1(), line.getDim2());
    }
}
