package projectworkgroup6.State;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.ResizeCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

import java.util.Map;

public class ResizeState implements CanvasState{
    private final ShapeView shapeView;
    private double lastX;
    private double lastY;


    private double centerX, centerY; // coordinate del centro della shape prima del ridimensionamento

    private double startX, startY; // coordinate della maniglia cliccata

    private ResizeCommand currentResizeCommand;

    private double oldWidth;
    private double oldHeight;


    private double lastCenterX;
    private double lastCenterY;

    public ResizeState(ShapeView shapeView) {
        this.shapeView = shapeView;
        this.centerX = shapeView.getShape().getX();
        this.centerY = shapeView.getShape().getY();
        this.oldWidth = shapeView.getShape().getDim1();
        this.oldHeight = shapeView.getShape().getDim2();
    }

    public void startDragging(double x, double y) {
        lastX = x;
        lastY = y;
        startX = x;
        startY = y;
    }

    @Override
    public void handleClick(MouseEvent e, double x, double y, Map<Shape, ShapeView> map) {
        StateController.getInstance().setState(SingleSelectState.getInstance());
    }

    @Override
    public void handlePression(double x, double y) {
        //System.out.println("Non definito");
    }

    @Override
    public void handlePressionRotate(double x, double y) {

    }


    // Implementa il ridimensionamento solo a livello grafico
    @Override
    public void handleMouseDragged(double x, double y) {

        //nascondi il menu a tendina
        StateController.getInstance().notifyShapeDeselected();


        Shape shape = shapeView.getShape();

        double oldw = shape.getDim1();
        double oldh = shape.getDim2();

        // cancello la shape con la dimensione base
        StateController.getInstance().removeShape(shape, shapeView);

        // Ad ogni aggiornamento calcolo il centro della shape
        double oldCenterX = shape.getX();
        double oldCenterY = shape.getY();


        double scaleFactor;


        // Visivamente la ridimensione cambia in base alla maniglia che sta venendo trascinata

        if(startX > shape.getX() && startY > shape.getY()){ // Trascino la maniglia nel vertice in basso a destra

            System.out.println("in basso a destra");

            double factorX = (x - shape.getXc())/shape.getDim1();
            double factorY = (y - shape.getYc()) / shape.getDim2();
            scaleFactor = Math.min(factorX,factorY);

            // Il nuovo centro si sposta in basso a destra
            shape.setX(shape.getX() - oldw/2 * (1-scaleFactor));
            shape.setY(shape.getY() - oldh/2 * (1-scaleFactor));
            // La nuova posizione della maniglia segue lo stesso spostamento del centro, simmetricamente ricopre sempre il vertice in basso a destra della figura ridimensionata.
            startX = startX - oldw/2 * (1-scaleFactor);
            startY = startY - oldw/2 * (1-scaleFactor);




        } else if(startX > shape.getX() && startY <= shape.getY()){ // Trascino la maniglia nel vertice in alto a destra

            System.out.println("in alto a destra");
            double factorX = (x - shape.getXc())/shape.getDim1();
            double factorY = (y + shape.getYc()) / shape.getDim2();
            scaleFactor = Math.min(factorX,factorY);

            // Il nuovo centro si sposta in alto a destra
            shape.setX(shape.getX() - oldw/2 * (1-scaleFactor));
            shape.setY(shape.getY() + oldh/2 * (1-scaleFactor));

            // La nuova posizione della maniglia segue lo stesso spostamento del centro, simmetricamente ricopre sempre il vertice in alto a destra della figura ridimensionata.
            startX = startX - oldw/2 * (1-scaleFactor);
            startY = startY + oldw/2 * (1-scaleFactor);




        } else if(startX <= shape.getX() && startY > shape.getY()){ // Trascino la maniglia nel vertice in basso a sinistra

            System.out.println("in basso a sinistra");

            double factorX = (x + shape.getXc())/shape.getDim1();
            double factorY = (y - shape.getYc()) / shape.getDim2();
            scaleFactor = Math.min(factorX,factorY);


            // Il nuovo centro si sposta in basso a sinistra
            shape.setX(shape.getX() + oldw/2 * (1-scaleFactor));
            shape.setY(shape.getY() - oldh/2 * (1-scaleFactor));

            // La nuova posizione della maniglia segue lo stesso spostamento del centro, simmetricamente ricopre sempre il vertice in basso a sinistra della figura ridimensionata.
            startX = startX + oldw/2 * (1-scaleFactor);
            startY = startY - oldw/2 * (1-scaleFactor);

        } else{ //Trascino maniglia nel vertice in alto a sinistra

            System.out.println("in alto a sinistra");

            double xbd = shape.getXc() + shape.getDim1();
            double ybd = shape.getYc() + shape.getDim2();
            double factorX = (xbd - x)/shape.getDim1();
            double factorY = (ybd - y) / shape.getDim2();
            scaleFactor = Math.min(factorX,factorY);

            // Il nuovo centro si sposta in alto a sinistra
            shape.setX(shape.getX() + oldw/2 * (1-scaleFactor));
            shape.setY(shape.getY() + oldh/2 * (1-scaleFactor));

            // La nuova posizione della maniglia segue lo stesso spostamento del centro, simmetricamente ricopre sempre il vertice in alto a sinistra della figura ridimensionata.
            startX = startX + oldw/2 * (1-scaleFactor);
            startY = startY + oldw/2 * (1-scaleFactor);
        }

        //Usati per il command nel released. Contengono ultimo centro raggiunto
        lastCenterX = shape.getX();
        lastCenterY = shape.getY();

        shape.resize(scaleFactor, oldCenterX, oldCenterY);  // Resize uniforme in larghezza e altezza
        currentResizeCommand.accumulate(scaleFactor); //Accumula ridimensionamento totale


        // Ridisegno la shape ridimensionata
        StateController.getInstance().addShape(shape, shapeView);
    }

    // Implementa il ridimensionamento solo a livello logico
    @Override
    public void handleMouseReleased(double x, double y) {
        // a livello logico riporto la shape alla sua posizione iniziale, undo si basa sullo spostamento incrementale calcolato
        Shape shape = shapeView.getShape();
        shape.setX(centerX);
        shape.setY(centerY);
        currentResizeCommand.undofactor(lastCenterX, lastCenterY);


        shape.setX(lastCenterX);
        shape.setY(lastCenterY);
        // Ora che logicamente la shape si trova nello stato iniziale, faccio una volta l'execute per riportarla logicamente alla posizione in cui è stata spostata.
        CommandManager.getInstance().executeCommand(currentResizeCommand);


        //mostra il menu a tendina
        StateController.getInstance().notifyShapeSelected(shapeView.getShape());


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

    @Override
    public void handleKeyTyped(KeyEvent event, Map<Shape, ShapeView> map) {

    }

    @Override
    public void handleChangeFontColor(Color currentFontColor) {

    }

    @Override
    public void handleChangeFontName(String currentFontName) {

    }

    @Override
    public void handleChangeFontSize(int currentFontSize) {

    }

    public void setResizeCommand(ResizeCommand resizeCommand) {
        this.currentResizeCommand = resizeCommand;
    }

}