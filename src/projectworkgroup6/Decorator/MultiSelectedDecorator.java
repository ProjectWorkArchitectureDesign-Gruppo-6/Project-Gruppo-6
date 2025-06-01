package projectworkgroup6.Decorator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import projectworkgroup6.Model.Polygon;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.Strategy.LineSelectionStrategy;
import projectworkgroup6.Strategy.RectangleSelectionStrategy;
import projectworkgroup6.Strategy.SelectionStrategy;
import projectworkgroup6.View.LineView;
import projectworkgroup6.View.ShapeView;

import java.util.List;

public class MultiSelectedDecorator extends ShapeView {

    private final ShapeView base;
    protected SelectionStrategy strategy;

    public MultiSelectedDecorator(ShapeView base) {
        super(base.getShape());
        this.base = base;
        this.strategy = base.undecorate().undecorate() instanceof LineView ? new LineSelectionStrategy() : new RectangleSelectionStrategy();
    }

    @Override
    public void draw(GraphicsContext gc) {
        // Disegna la shape originale
        base.draw(gc);

        //Calcola centro e angolo della figura
        double angle = shape.getRotation(); // angolo attuale della figura

        double centerX, centerY;

        if (shape instanceof Polygon) {
            List<double[]> vertices = ((Polygon) shape).getVertices();

            centerX = vertices.stream().mapToDouble(v -> v[0]).average().orElse(0);
            centerY = vertices.stream().mapToDouble(v -> v[1]).average().orElse(0);
        } else {
            centerX = shape.getXc() + shape.getDim1() / 2.0;
            centerY = shape.getYc() + shape.getDim2() / 2.0;
        }

        gc.save();

        //Ruota il contesto per decorazioni
        gc.translate(centerX, centerY);
        gc.rotate(angle);
        gc.translate(-centerX, -centerY);


        // Disegna il bordo di evidenziazione (azzurro)
        drawHighlightBorder(gc);


        gc.restore();
    }

    private void drawHighlightBorder(GraphicsContext gc) {
        strategy.drawSelectionBorder(gc, base.getShape());

    }

    @Override
    public ShapeView undecorate() {
        return base;
    }

}
