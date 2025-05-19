package projectworkgroup6.Decorator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import projectworkgroup6.Model.Line;
import projectworkgroup6.Model.Rectangle;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.Strategy.LineSelectionStrategy;
import projectworkgroup6.Strategy.RectangleSelectionStrategy;
import projectworkgroup6.Strategy.SelectionStrategy;

public class SelectedDecorator extends Shape {

    private Shape base;
    private SelectionStrategy strategy;

    public SelectedDecorator(Shape base) {
        super(base.getX(), base.getY(), base.isSelected());
        this.base = base;
        this.strategy = base instanceof Line ? new LineSelectionStrategy() : new RectangleSelectionStrategy(); // oppure meglio delegare questo alla Factory
    }

    @Override
    public double getDim1() {
        return base.getDim1();
    }

    @Override
    public double getDim2() {
        return base.getDim2();
    }

    @Override
    public double getXc() {
        return base.getXc();
    }

    @Override
    public double getYc() {
        return base.getYc();
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

    @Override
    public Shape getShapebase(){
        return base;
    }

    private void drawSelectionBorder(GraphicsContext gc) {
        strategy.drawSelectionBorder(gc, base);
    }

    private void drawHandles(GraphicsContext gc) {
        strategy.drawHandles(gc, base);
    }

    private void drawMoveButton(GraphicsContext gc){
        strategy.drawMoveButton(gc, base);
    }

    public double getMoveButtonX(){
        return strategy.getMoveButtonX(this);
    }

    public double getMoveButtonY(){
        return strategy.getMoveButtonY(this);
    }


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
}
