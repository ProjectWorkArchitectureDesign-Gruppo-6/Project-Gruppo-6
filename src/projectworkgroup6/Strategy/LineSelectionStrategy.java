package projectworkgroup6.Strategy;

import javafx.scene.canvas.GraphicsContext;
import projectworkgroup6.Model.Shape;
import javafx.scene.paint.Color;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;


public class LineSelectionStrategy extends SelectionStrategy {
    @Override
    public void drawSelectionBorder(GraphicsContext gc, Shape shape) {
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(3.0);
        gc.strokeLine(shape.getXc(), shape.getYc(), shape.getDim1(), shape.getDim2());
    }

    @Override
    public void drawHandles(GraphicsContext gc, Shape shape) {
        double size = 6;
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        drawHandle(gc, shape.getXc(), shape.getYc(), size);
        drawHandle(gc, shape.getDim1(), shape.getDim2(), size);
    }

    @Override
    public void drawMoveButton(GraphicsContext gc, Shape shape) {



        double buttonX = getMoveButtonX(shape);
        double buttonY =  getMoveButtonY(shape);

        gc.setFill(Color.WHITE);
        gc.fillRect(buttonX, buttonY, buttonWidth, buttonHeight);

        gc.setStroke(Color.BLUE.brighter());
        gc.strokeOval(buttonX, buttonY, buttonWidth, buttonHeight);

        gc.setFill(Color.BLACK);
        gc.fillText("↔", buttonX + 3 , buttonY + 15);
    }

    @Override
    public double getMoveButtonX(Shape shape) {
        return (shape.getXc() + shape.getDim1() - 40) / 2;
    }

    @Override
    public double getMoveButtonY(Shape shape) {
        return shape.getDim2() - 10;
    }

    @Override
    public List<AbstractMap.SimpleEntry<Double, Double>> getHandles(Shape shape) {


        // Lista di mappe semplici con due double x e y
        List<AbstractMap.SimpleEntry<Double, Double>> handles = new ArrayList<>();

        double x = shape.getXc();
        double y = shape.getYc();
        double w = shape.getDim1();
        double h = shape.getDim2();
        double size = 6;
        double half = size / 2;


        // Coordinate maniglia primo vertice
        handles.add(new AbstractMap.SimpleEntry<>(x-half,y-half));

        // Coordinate maniglia secondo vertice
        handles.add(new AbstractMap.SimpleEntry<>(w-half,h-half));


        return handles;
    }


}
