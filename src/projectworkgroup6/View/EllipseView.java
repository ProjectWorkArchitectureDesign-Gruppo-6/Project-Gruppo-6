package projectworkgroup6.View;

import javafx.scene.canvas.GraphicsContext;
import projectworkgroup6.Model.Ellipse;

public class EllipseView extends ShapeView {

    public EllipseView(Ellipse shape) {
        super(shape);
    }

    @Override
    public void draw(GraphicsContext gc) {

        Ellipse ell = (Ellipse) shape;

        double centerX = ell.getXc() + ell.getDim1() / 2.0;
        double centerY = ell.getYc() + ell.getDim2() / 2.0;
        double angle = ell.getRotation();

        gc.save();
        gc.translate(centerX, centerY);
        gc.rotate(angle);
        gc.translate(-centerX, -centerY);

        gc.setStroke(ell.getBorder().toColor());
        gc.setFill(ell.getFill().toColor());

        gc.strokeOval(ell.getXc(), ell.getYc(), ell.getDim1(), ell.getDim2());
        gc.fillOval(ell.getXc(), ell.getYc(), ell.getDim1(), ell.getDim2());

        gc.restore();

    }
}
