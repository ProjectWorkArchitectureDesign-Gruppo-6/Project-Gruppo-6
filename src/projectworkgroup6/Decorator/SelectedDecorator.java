package projectworkgroup6.Decorator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import projectworkgroup6.Model.Line;
import projectworkgroup6.Model.Rectangle;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.Strategy.LineSelectionStrategy;
import projectworkgroup6.Strategy.RectangleSelectionStrategy;
import projectworkgroup6.Strategy.SelectionStrategy;
import projectworkgroup6.View.LineView;
import projectworkgroup6.View.ShapeView;

import java.util.AbstractMap;
import java.util.List;


public class SelectedDecorator extends ShapeView{

    private ShapeView base;
    protected SelectionStrategy strategy;

    public SelectedDecorator(ShapeView base) {
        super(base.getShape());
        this.base = base;
        this.strategy = base.undecorate().undecorate() instanceof LineView ? new LineSelectionStrategy() : new RectangleSelectionStrategy();
    }

    @Override
    public void draw(GraphicsContext gc) {
        // 1. Disegna la shape originale
        base.draw(gc);

        // 2. Disegna il bordo di selezione
        drawSelectionBorder(gc);

        // 3. Disegna le maniglie
        drawHandles(gc);

        // Disegna il bottone di movimento
        drawMoveButton(gc);

    }


    public ShapeView undecorate(){
        return base;
    }

    private void drawSelectionBorder(GraphicsContext gc) {
        strategy.drawSelectionBorder(gc, base.getShape());
    }

    private void drawHandles(GraphicsContext gc) {
        strategy.drawHandles(gc, base.getShape());
    }

    private void drawMoveButton(GraphicsContext gc){
        strategy.drawMoveButton(gc, base.getShape());
    }

    public double getMoveButtonX(){
        return strategy.getMoveButtonX(base.getShape());
    }

    public double getMoveButtonY(){
        return strategy.getMoveButtonY(base.getShape());
    }

    public List<AbstractMap.SimpleEntry<Double, Double>> getHandles() {return strategy.getHandles(base.getShape()); }

}