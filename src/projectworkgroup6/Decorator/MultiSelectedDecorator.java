package projectworkgroup6.Decorator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.Strategy.LineSelectionStrategy;
import projectworkgroup6.Strategy.RectangleSelectionStrategy;
import projectworkgroup6.Strategy.SelectionStrategy;
import projectworkgroup6.View.LineView;
import projectworkgroup6.View.ShapeView;

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

        // Disegna il bordo di evidenziazione (azzurro)
        drawHighlightBorder(gc);
    }

    private void drawHighlightBorder(GraphicsContext gc) {
        strategy.drawSelectionBorder(gc, base.getShape());

    }

    @Override
    public ShapeView undecorate() {
        return base;
    }

}
