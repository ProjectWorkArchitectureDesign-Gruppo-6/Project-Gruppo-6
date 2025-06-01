package projectworkgroup6.Decorator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import projectworkgroup6.Model.Group;
import projectworkgroup6.Strategy.RectangleSelectionStrategy;
import projectworkgroup6.View.ShapeView;

import java.util.AbstractMap;
import java.util.List;


public class GroupBorderDecorator extends ShapeView {
    private final ShapeView baseView;
    private RectangleSelectionStrategy strategy = new RectangleSelectionStrategy();

    public GroupBorderDecorator(ShapeView baseView) {
        super(baseView.getShape());
        this.baseView = baseView;
    }

    @Override
    public void draw(GraphicsContext gc) {

        // 1. Disegno il bordo tratteggiato
        Group group = (Group) getShape();
        double x = group.getX() - group.getWidth() / 2;
        double y = group.getY() - group.getHeight() / 2;

        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineDashes(6);
        gc.setLineWidth(2);
        gc.strokeRect(x, y, group.getWidth(), group.getHeight());
        gc.setLineDashes(); // resetta dash pattern

        // 2. Disegna le maniglie
        drawHandles(gc);

        // 3. Disegna il bottone di movimento
        drawMoveButton(gc);

        // Disegno il contenuto
        baseView.draw(gc);
    }

    @Override
    public ShapeView undecorate() {
        return baseView;
    }

    public double getMoveButtonX(){
        return strategy.getMoveButtonX(baseView.getShape());
    }

    public double getMoveButtonY(){
        return strategy.getMoveButtonY(baseView.getShape());
    }

    private void drawHandles(GraphicsContext gc) {
        strategy.drawHandles(gc, baseView.getShape());
    }

    private void drawMoveButton(GraphicsContext gc){
        strategy.drawMoveButton(gc, baseView.getShape());
    }

    public List<AbstractMap.SimpleEntry<Double, Double>> getHandles() {return strategy.getHandles(baseView.getShape()); }

}