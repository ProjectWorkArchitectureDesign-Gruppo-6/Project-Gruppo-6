package projectworkgroup6.State;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.ResizeCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.GroupBorderDecorator;
import projectworkgroup6.Model.Group;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

import java.util.List;
import java.util.Map;

import static projectworkgroup6.State.SingleSelectState.rotatePointBack;

public class TemporaryResizeState implements CanvasState{
    private final GroupBorderDecorator shapeView;

    private List<ShapeView> individualViews;

    private double lastX;
    private double lastY;

    private double lastCenterX;
    private double lastCenterY;

    private double centerX, centerY; // coordinate del centro della shape prima del ridimensionamento

    private double startX, startY; // coordinate della maniglia cliccata

    private ResizeCommand currentResizeCommand;

    public TemporaryResizeState(GroupBorderDecorator shapeView) {
        this.shapeView = shapeView;
        this.centerX = shapeView.getShape().getX();
        this.centerY = shapeView.getShape().getY();
    }

    public void startDragging(double x, double y, List<ShapeView> individualViews) {
        lastX = x;
        lastY = y;
        startX = x;
        startY = y;
        this.individualViews = individualViews;
    }

    @Override
    public void handleClick(MouseEvent e, double x, double y, Map<Shape, ShapeView> map) {
        Group group = (Group)shapeView.getShape();
        StateController.getInstance().removeShape(group, shapeView);
        for(int i = 0; i< group.getShapes().size(); i++){
            StateController.getInstance().addShape(group.getShapes().get(i), individualViews.get(i));
        }

        // rendering del gruppo temporaneo
        StateController.getInstance().addGroup(shapeView);

        MultipleSelectState.getInstance().setGroup(group);
        StateController.getInstance().setState(MultipleSelectState.getInstance());
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

        Point2D unrotated = rotatePointBack(x, y, shape);
        x = unrotated.getX();
        y = unrotated.getY();

        double oldw = shape.getDim1();
        double oldh = shape.getDim2();

        // cancello la shape con la dimensione base
        //StateController.getInstance().removeShape(shape, shapeView);

        // Ad ogni aggiornamento calcolo il centro della shape
        double oldCenterX = shape.getX();
        double oldCenterY = shape.getY();


        double factorX;
        double factorY;

        double dx;
        double dy;

        double boundWidth = 30;
        double boundHeight = 20;



        // Visivamente la ridimensione cambia in base alla maniglia che sta venendo trascinata

        if(startX > shape.getX() && startY >= shape.getY()){ // Trascino la maniglia nel vertice in basso a destra

            System.out.println("in basso a destra");

            factorX = (x - shape.getXc())/shape.getDim1();
            factorY = (y - shape.getYc()) / shape.getDim2();

            // Il nuovo centro si sposta in basso a destra
            dx = -oldw/2 * (1 - factorX);
            dy = -oldh/2 * (1 - factorY);


        } else if(startX > shape.getX() && startY <= shape.getY()){ // Trascino la maniglia nel vertice in alto a destra

            System.out.println("in alto a destra");

            factorX = (x - shape.getXc())/shape.getDim1();
            factorY = ((shape.getYc()+shape.getDim2()) - y ) / shape.getDim2();


            // Il nuovo centro si sposta in alto a destra
            dx= - oldw/2 * (1-factorX);
            dy = oldh/2 * (1-factorY);


        } else if(startX <= shape.getX() && startY > shape.getY()){ // Trascino la maniglia nel vertice in basso a sinistra

            System.out.println("in basso a sinistra");

            factorX = ((shape.getXc() + shape.getDim1()) - x)/shape.getDim1();
            factorY = (y - shape.getYc()) / shape.getDim2();

            // Il nuovo centro si sposta in basso a sinistra
            dx = oldw/2 * (1-factorX);
            dy = - oldh/2 * (1-factorY);


        } else{ //Trascino maniglia nel vertice in alto a sinistra

            System.out.println("in alto a sinistra");

            double xbd = shape.getXc() + shape.getDim1();
            double ybd = shape.getYc() + shape.getDim2();
            factorX = (xbd - x)/shape.getDim1();
            factorY = (ybd - y) / shape.getDim2();

            // Il nuovo centro si sposta in alto a sinistra
            dx = oldw/2 * (1-factorX);
            dy = oldh/2 * (1-factorY);

        }

        // Proietto lo spostamento del centro in base alla rotazione della figura
        double angle = Math.toRadians(shape.getRotation());
        double dxRot = dx * Math.cos(angle) - dy * Math.sin(angle);
        double dyRot = dx * Math.sin(angle) + dy * Math.cos(angle);

        if(shape.getDim1() * factorX >= boundWidth && shape.getDim2() * factorY >= boundHeight){

            shape.setX(shape.getX() + dxRot);
            shape.setY(shape.getY() + dyRot);


            // La nuova posizione della maniglia segue lo stesso spostamento del centro
            startX = startX + dxRot;
            startY = startY + dyRot;


            shape.resize(factorX, factorY, oldCenterX, oldCenterY);  // Resize uniforme in larghezza e altezza
            currentResizeCommand.accumulate(factorX,factorY); //Accumula ridimensionamento totale

        } else if (shape.getDim1() * factorX < boundWidth && shape.getDim2() * factorY >= boundHeight) {

            shape.setY(shape.getY() + dyRot);


            // La nuova posizione della maniglia segue lo stesso spostamento del centro
            startY = startY + dyRot;


            shape.resize(1, factorY, oldCenterX, oldCenterY);  // Resize uniforme in larghezza e altezza
            currentResizeCommand.accumulate(1,factorY); //Accumula ridimensionamento totale

        } else if (shape.getDim1() * factorX >= boundWidth && shape.getDim2() * factorY < boundHeight) {
            shape.setX(shape.getX() + dxRot);


            // La nuova posizione della maniglia segue lo stesso spostamento del centro
            startX = startX + dxRot;


            shape.resize(factorX, 1, oldCenterX, oldCenterY);  // Resize uniforme in larghezza e altezza
            currentResizeCommand.accumulate(factorX,1); //Accumula ridimensionamento totale
        }


        //Usati per il command nel released. Contengono ultimo centro raggiunto
        lastCenterX = shape.getX();
        lastCenterY = shape.getY();

        // Ridisegno la shape ridimensionata
        //StateController.getInstance().addShape(shape, shapeView);
        StateController.getInstance().notifyCanvasToRepaint();
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
        Group group = (Group)shapeView.getShape();
        if(group.getShapes().size() > 1){
            StateController.getInstance().notifyShapeSelected(shapeView.getShape());
        }

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