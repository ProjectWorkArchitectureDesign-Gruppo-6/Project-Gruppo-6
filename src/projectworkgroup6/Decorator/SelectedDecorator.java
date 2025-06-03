package projectworkgroup6.Decorator;

import javafx.scene.canvas.GraphicsContext;
import projectworkgroup6.Model.Polygon;
import projectworkgroup6.Model.TextBox;
import projectworkgroup6.Strategy.LineSelectionStrategy;
import projectworkgroup6.Strategy.RectangleSelectionStrategy;
import projectworkgroup6.Strategy.SelectionStrategy;
import projectworkgroup6.View.LineView;
import projectworkgroup6.View.ShapeView;

import java.util.AbstractMap;
import java.util.List;


/**
 * La classe SelectedDecorator che implementa ShapeView aggiunge funzionalità di selezione:
 * Bordo di selezione
 * Maniglie di ridimensionamento
 * Pulsanti per spostare e ruotare la figura
 * Questo decoratore usa una strategia specifica (Rectangle o Line)
 * per disegnare le decorazioni in base al tipo della figura.
 */

public class SelectedDecorator extends ShapeView{

    private ShapeView base;
    protected SelectionStrategy strategy;

    //Costruisce un decoratore di selezione per la figura specificata.
    //Determina la strategia in base al tipo effettivo della shape non decorata.
    public SelectedDecorator(ShapeView base) {
        super(base.getShape());
        this.base = base;
        this.strategy = base.undecorate().undecorate() instanceof LineView ? new LineSelectionStrategy() : new RectangleSelectionStrategy();
    }

    ///**
    //Disegna la figura decorata con:
    //La figura originale ruotata
    //Bordo, maniglie e pulsanti, anch'essi ruotati per restare allineati alla figura
    @Override
    public void draw(GraphicsContext gc) {
        // 1. Disegna la shape originale
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

        //Disegna decorazioni allineate
        drawSelectionBorder(gc);
        drawHandles(gc);
        drawStretchHandles(gc);

        // Disegna il bottone di movimento
        drawMoveButton(gc);
        drawRotateButton(gc);

        gc.restore();

    }

    //Restituisce le coordinate X del pulsante di movimento
    public double getMoveButtonX(){
        return strategy.getMoveButtonX(base.getShape());
    }

    //Restituisce le coordinate Y del pulsante di movimento
    public double getMoveButtonY(){
        return strategy.getMoveButtonY(base.getShape());
    }

    //Restituisce le coordinate X del pulsante di rotazione
    public double getRotateButtonX() {
        return strategy.getRotateButtonX(shape);
    }

    //Restituisce le coordinate Y del pulsante di rotazione
    public double getRotateButtonY() {
        return strategy.getRotateButtonY(shape);
    }

    //Rimuove il decoratore, restituendo la shape originale
    public ShapeView undecorate(){
        return base;
    }

    // Disegna il bordo di selezione attorno alla figura
    private void drawSelectionBorder(GraphicsContext gc) {
        strategy.drawSelectionBorder(gc, base.getShape());
    }

    // Disegna le maniglie per il ridimensionamento
    private void drawHandles(GraphicsContext gc) {
        strategy.drawHandles(gc, base.getShape());
    }
//
    private void drawStretchHandles(GraphicsContext gc) {strategy.drawStretchHandles(gc, base.getShape());}

    // Disegna il pulsante per il movimento
    private void drawMoveButton(GraphicsContext gc) {
        strategy.drawMoveButton(gc, base.getShape());
    }

    // Disegna il pulsante per la rotazione
    private void drawRotateButton(GraphicsContext gc) {
        strategy.RotateButton(gc, base.getShape());
    }

    //Restituisce la lista delle maniglie di ridimensionamento
    public List<AbstractMap.SimpleEntry<Double, Double>> getHandles() {return strategy.getHandles(base.getShape()); }
//
public List<AbstractMap.SimpleEntry<Double, Double>> getStretchHandles() {return strategy.getStretchHandles(base.getShape()); }

}