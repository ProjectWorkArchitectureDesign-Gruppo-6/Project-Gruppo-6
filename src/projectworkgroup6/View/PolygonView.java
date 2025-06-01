package projectworkgroup6.View;

import javafx.scene.canvas.GraphicsContext;
import projectworkgroup6.Model.Polygon;

public class PolygonView extends ShapeView {
    public PolygonView(Polygon shape) {
        super(shape);
    }


    public void draw(GraphicsContext gc, boolean isPreview) {


        Polygon pol = (Polygon) shape;
        int n = pol.getVertices().size();
        if (n == 0) return;

        double[] xPoints = new double[n];
        double[] yPoints = new double[n];
        double sumX = 0, sumY = 0;

        for (int i = 0; i < n; i++) {
            xPoints[i] = pol.getVertices().get(i)[0];
            yPoints[i] = pol.getVertices().get(i)[1];
            sumX += xPoints[i];
            sumY += yPoints[i];
        }

        double centerX = sumX / n;
        double centerY = sumY / n;
        double angle = pol.getRotation();

        gc.save();
        gc.translate(centerX, centerY);
        gc.rotate(angle);
        gc.translate(-centerX, -centerY);

        gc.setStroke(pol.getBorder().toColor());
        gc.setFill(pol.getFill().toColor());

        gc.setStroke(pol.getBorder().toColor());
        gc.setFill(pol.getFill().toColor());

        if (n < 3) {
            gc.strokePolyline(xPoints, yPoints, n);
        } else {
            gc.strokePolygon(xPoints, yPoints, n);
            gc.fillPolygon(xPoints, yPoints, n);
        }

        gc.restore();

    }

    @Override
    public void draw(GraphicsContext gc) {
        draw(gc, false); // normale
    }
}
