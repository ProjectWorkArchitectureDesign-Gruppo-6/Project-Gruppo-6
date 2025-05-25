package projectworkgroup6.View;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import projectworkgroup6.Model.Ellipse;

public class EllipseView extends ShapeView {

    public EllipseView(Ellipse shape) {
        super(shape);
    }

    @Override
    public void draw(GraphicsContext gc) {
        Ellipse ell = (Ellipse) shape;

        gc.strokeOval(ell.getXc(), ell.getYc(), ell.getDim1(), ell.getDim2());
        gc.fillOval(ell.getXc(), ell.getYc(), ell.getDim1(), ell.getDim2());
    }
}
