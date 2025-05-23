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

public class SelectedDecorator extends ShapeView{

    private ShapeView base;
    protected SelectionStrategy strategy;

    public SelectedDecorator(ShapeView base) {
        super(base.getShape());
        this.base = base;
        this.strategy = base.undecorate() instanceof LineView ? new LineSelectionStrategy() : new RectangleSelectionStrategy(); // oppure meglio delegare questo alla Factory
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


/*
    @Override
    public void move(double dx, double dy) {

        base.move(dx, dy); // spostamento della figura

    }

    @Override
    public void resize(double scaleFactor) {
        base.resize(scaleFactor);
    }

    @Override
    public void setColor(Color color) {
        base.setColor(color);
    }

    @Override
    public boolean contains(double x, double y) {
        return base.contains(x,y);
    }

 */
}
