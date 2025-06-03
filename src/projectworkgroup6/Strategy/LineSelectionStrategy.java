package projectworkgroup6.Strategy;

import javafx.scene.canvas.GraphicsContext;
import projectworkgroup6.Model.Line;
import projectworkgroup6.Model.Shape;
import javafx.scene.paint.Color;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class LineSelectionStrategy extends SelectionStrategy {

    //Disegna il bordo di selezione intorno alla shape e viene evidenziato in blu.
    //Per le linee, il bordo coincide con la linea stessa.
    @Override
    public void drawSelectionBorder(GraphicsContext gc, Shape shape) {

        gc.setStroke(Color.BLUE);
        gc.setLineWidth(3.0);
        gc.strokeLine(shape.getXc(), shape.getYc(), shape.getX2(), shape.getY2());
    }

    //Disegna le maniglie di ridimensionamento alle estremità della linea.
    //Le maniglie sono piccoli quadratini bianchi con bordo nero.
    @Override
    public void drawHandles(GraphicsContext gc, Shape shape) {
        double size = 6;
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        drawHandle(gc, shape.getXc(), shape.getYc(), size);
        drawHandle(gc, shape.getX2(), shape.getY2(), size);

    }

    @Override
    public void drawStretchHandles(GraphicsContext gc, Shape shape) {
        double x1 = shape.getXc();
        double y1 = shape.getYc();
        double x2 = shape.getDim1();
        double y2 = shape.getDim2();
        double size = 6;

        //maniglia di streach
        gc.setStroke(Color.BLACK);
        //maniglia stretch+mirroring
        drawStretchHandle(gc,x1 +  (x2 - x1)/4,y1 + (y2 - y1)/4, size);

        drawStretchHandle(gc,x1 +  3*(x2 - x1)/4,y1 + 3*(y2 - y1)/4, size);
    }

    //Disegna il pulsante di rotazione sotto alla figura selezionata.
    //Questo bottone è rappresentato da un cerchio bianco con un'icona ⟳.
    @Override
    public void RotateButton(GraphicsContext gc, Shape shape) {
        double buttonX = getRotateButtonX(shape);
        double buttonY =  getRotateButtonY(shape);

        gc.setFill(Color.WHITE);
        gc.fillRect(buttonX, buttonY, buttonWidth, buttonHeight);

        gc.setStroke(Color.BLUE.brighter());
        gc.strokeOval(buttonX, buttonY, buttonWidth, buttonHeight);

        gc.setFill(Color.BLACK);
        gc.fillText("⟳", buttonX + 3 , buttonY + 15);
    }

    //Restituisce la coordinata X del centro del pulsante di rotazione,
    //calcolata a partire dalla posizione del pulsante di movimento.
    @Override
    public double getRotateButtonX(Shape shape) {
        return getMoveButtonX(shape) + 20;
    }

    //Restituisce la coordinata Y del centro del pulsante di rotazione,
    //calcolata a partire dalla posizione del pulsante di movimento.
    @Override
    public double getRotateButtonY(Shape shape) {
        return getMoveButtonY(shape) + 12;
    }

    //Disegna il pulsante muovi sotto alla figura selezionata.
    //È rappresentato da un cerchio bianco con l'icona ↔
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

    //Calcola la coordinata X del pulsante di spostamento (↔)
    @Override
    public double getMoveButtonX(Shape shape) {
        return shape.getX() - 20;
    }

    //Calcola la coordinata Y del pulsante di spostamento (↔)
    @Override
    public double getMoveButtonY(Shape shape) {

        return shape.getY() + 20;

    }

    //Restituisce una lista di maniglie in termini di coordinate alle due estremità della linea.
    //Ogni maniglia è centrata su uno dei due punti estremi.
    @Override
    public List<AbstractMap.SimpleEntry<Double, Double>> getHandles(Shape shape) {


        // Lista di mappe semplici con due double x e y
        List<AbstractMap.SimpleEntry<Double, Double>> handles = new ArrayList<>();

        double x = shape.getXc();
        double y = shape.getYc();
        double w = shape.getX2();
        double h = shape.getY2();
        double size = 6;
        double half = size / 2;


        // Coordinate maniglia primo vertice
        handles.add(new AbstractMap.SimpleEntry<>(x-half,y-half));

        // Coordinate maniglia secondo vertice
        handles.add(new AbstractMap.SimpleEntry<>(w-half,h-half));


        return handles;
    }

    @Override
    public List<AbstractMap.SimpleEntry<Double, Double>> getStretchHandles(Shape shape) {

        // Lista di mappe semplici con due double x e y
        List<AbstractMap.SimpleEntry<Double, Double>> stretchHandles = new ArrayList<>();

        double x1 = shape.getXc();
        double y1 = shape.getYc();
        double x2 = shape.getDim1();
        double y2 = shape.getDim2();
        double size = 6;
        double half = size / 2;

System.out.println("aggiungo handle nella mappa[LineSelectionStrategy]");
        stretchHandles.add(new AbstractMap.SimpleEntry<>( x1 + (x2 - x1)/4 - half,y1 + (y2 - y1)/4 - half));
        stretchHandles.add(new AbstractMap.SimpleEntry<>( x1 + 3*(x2 - x1)/4 - half,y1 + 3*(y2 - y1)/4 - half));
        return stretchHandles;
    }


}