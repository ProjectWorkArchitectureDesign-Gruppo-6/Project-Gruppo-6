package projectworkgroup6.State;

import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.MoveCommand;
import projectworkgroup6.Command.ResizeCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

import java.util.Map;

public class ResizeState implements CanvasState{
    private final SelectedDecorator shapeView;
    private double lastX;
    private double lastY;

    private double centerX, centerY; // coordinate del centro della shape prima del ridimensionamento

    private double startX, startY; // coordinate della maniglia cliccata

    private ResizeCommand currentResizeCommand;

    public ResizeState(SelectedDecorator shapeView) {
        this.shapeView = shapeView;
        this.centerX = shapeView.getShape().getX();
        this.centerY = shapeView.getShape().getY();
    }

    public void startDragging(double x, double y) {
        lastX = x;
        lastY = y;
        startX = x;
        startY = y;
    }

    @Override
    public void handleClick(double x, double y, Map<Shape, ShapeView> map) {
        //System.out.println("Non definito");
    }

    @Override
    public void handlePression(double x, double y) {
        //System.out.println("Non definito");
    }


    // Implementa il ridimensionamento solo a livello grafico
    @Override
    public void handleMouseDragged(double x, double y) {
        Shape shape = shapeView.getShape();

        double oldw = shape.getDim1();
        double oldh = shape.getDim2();


        // cancello la shape con la dimensione base
        StateController.getInstance().removeShape(shape, shapeView);

        // Calcolo il passo di ridimensionamento effettuato valutando la variazione del punto dalla distanza dal centro
        double previousDistance = Math.hypot(lastX - centerX, lastY - centerY);
        double currentDistance = Math.hypot(x - centerX, y - centerY);

        // Per rendere il fattore di scala univoco, considero come fattore solo il minimo tra i due.
        double scaleFactor = currentDistance / previousDistance;

        shape.resize(scaleFactor);  // Resize uniforme in larghezza e altezza
        currentResizeCommand.accumulate(scaleFactor); //Accumula ridimensionamento totale


        // Visivamente la ridimensione cambia in base alla maniglia che sta venendo trascinata

        if(startX > shape.getX() && startY > shape.getY()){ // Trascino la maniglia nel vertice in basso a destra
            // Il nuovo centro si sposta in basso a destra
            shape.setX(shape.getX() - oldw/2 * (1-scaleFactor));
            shape.setY(shape.getY() - oldh/2 * (1-scaleFactor));
            // La nuova posizione della maniglia segue lo stesso spostamento del centro, simmetricamente ricopre sempre il vertice in basso a destra della figura ridimensionata.
            startX = startX - oldw/2 * (1-scaleFactor);
            startY = startY - oldw/2 * (1-scaleFactor);


        } else if(startX > shape.getX() && startY <= shape.getY()){ // Trascino la maniglia nel vertice in alto a destra
            // Il nuovo centro si sposta in alto a destra
            shape.setX(shape.getX() - oldw/2 * (1-scaleFactor));
            shape.setY(shape.getY() + oldh/2 * (1-scaleFactor));

            // La nuova posizione della maniglia segue lo stesso spostamento del centro, simmetricamente ricopre sempre il vertice in alto a destra della figura ridimensionata.
            startX = startX - oldw/2 * (1-scaleFactor);
            startY = startY + oldw/2 * (1-scaleFactor);

        } else if(startX <= shape.getX() && startY > shape.getY()){ // Trascino la maniglia nel vertice in basso a sinistra
            // Il nuovo centro si sposta in basso a sinistra
            shape.setX(shape.getX() + oldw/2 * (1-scaleFactor));
            shape.setY(shape.getY() - oldh/2 * (1-scaleFactor));

            // La nuova posizione della maniglia segue lo stesso spostamento del centro, simmetricamente ricopre sempre il vertice in basso a sinistra della figura ridimensionata.
            startX = startX + oldw/2 * (1-scaleFactor);
            startY = startY - oldw/2 * (1-scaleFactor);
        } else{ //Trascino maniglia nel vertice in alto a sinistra

            // Il nuovo centro si sposta in alto a sinistra
            shape.setX(shape.getX() + oldw/2 * (1-scaleFactor));
            shape.setY(shape.getY() + oldh/2 * (1-scaleFactor));

            // La nuova posizione della maniglia segue lo stesso spostamento del centro, simmetricamente ricopre sempre il vertice in alto a sinistra della figura ridimensionata.
            startX = startX + oldw/2 * (1-scaleFactor);
            startY = startY + oldw/2 * (1-scaleFactor);
        }


        // Aggiorno coordinate di ridimensionamento
        lastX = x;
        lastY = y;

        // Ridisegno la shape ridimensionata
        StateController.getInstance().addShape(shape, shapeView);
    }

    // Implementa il ridimensionamento solo a livello logico
    @Override
    public void handleMouseReleased(double x, double y) {
        // a livello logico riporto la shape alla sua posizione iniziale, undo si basa sullo spostamento incrementale calcolato
        currentResizeCommand.undo();

        // Ora che logicamente la shape si trova nello stato iniziale, faccio una volta l'execute per riportarla logicamente alla posizione in cui è stata spostata.
        CommandManager.getInstance().executeCommand(currentResizeCommand);


        StateController.getInstance().setState(SingleSelectState.getInstance());

    }

    @Override
    public void recoverShapes(Map<Shape, ShapeView> map) {
        //nulla
    }

    @Override
    public void handleDelete(KeyEvent event, Map<Shape, ShapeView> map) {
        // Poi
    }

    @Override
    public void handleColorChanged(Color currentStroke, Color currentFill) {
        //nulla
    }

    public void setResizeCommand(ResizeCommand resizeCommand) {
        this.currentResizeCommand = resizeCommand;
    }

}
