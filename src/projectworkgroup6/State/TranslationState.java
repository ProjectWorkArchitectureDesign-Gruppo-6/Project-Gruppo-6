package projectworkgroup6.State;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.MoveCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

import java.util.Map;

public class TranslationState implements CanvasState{
    private final SelectedDecorator shapeView;
    private double lastX;
    private double lastY;

    private MoveCommand currentMoveCommand;

    public TranslationState(SelectedDecorator shapeView) {
        this.shapeView = shapeView;
    }

    @Override
    public void handleClick(MouseEvent e,double x, double y, Map<Shape, ShapeView> map) {

        StateController.getInstance().setState(SingleSelectState.getInstance());

    }

    @Override
    public void handlePression(double x, double y) {
        //Non definito
    }

    @Override
    public void handlePressionRotate(double x, double y) {

    }

    public void startDragging(double x, double y) {
        lastX = x;
        lastY = y;
    }

    // Implementa lo spostamento solo a livello grafico
    @Override
    public void handleMouseDragged(double x, double y) { // va in esecuzione più volte durante lo spostamento

        Shape shape = shapeView.getShape();

        //nascondi il menu a tendina
        StateController.getInstance().notifyShapeDeselected();

        StateController.getInstance().removeShape(shape,shapeView); // rimuovo la shape dalla posizione iniziale

        // Calcolo il microspostamento
        double dx = x - lastX;
        double dy = y - lastY;

        shape.move(dx,dy); // muovo la shape per la grafica
        currentMoveCommand.accumulate(dx, dy); // salvo il piccolo passo per lo spostamento totale

        lastX = x;
        lastY = y; // salvataggio delle coordinate ottenute in base al piccolo passo

        StateController.getInstance().addShape(shape, shapeView); // aggiungo shape alla posizione nuova

        // Questo permette di spostatare la shape visivamente

    }

    // Implementa lo spostamento solo a livello logico
    @Override
    public void handleMouseReleased(double xf, double yf) { // evento che avviene al rilascio del mouse


        // a livello logico riporto la shape alla sua posizione iniziale, undo si basa sullo spostamento incrementale calcolato
        currentMoveCommand.undo();


        // Ora che logicamente la shape si trova nello stato iniziale, faccio una volta l'execute per riportarla logicamente alla posizione in cui è stata spostata.
        CommandManager.getInstance().executeCommand(currentMoveCommand);

        //mostra il menu a tendina
        StateController.getInstance().notifyShapeSelected(shapeView.getShape());
    }



    @Override
    public void recoverShapes(Map<Shape, ShapeView> map) {
        // Non deve fare nulla
    }

    @Override
    public void handleDelete(KeyEvent event, Map<Shape, ShapeView> map) {
        // Poi
    }

    @Override
    public void handleColorChanged(Color currentStroke, Color currentFill) {
        // Nulla
    }

    @Override
    public void handleKeyTyped(KeyEvent event, Map<Shape, ShapeView> map) {

    }

    public void setMoveCommand(MoveCommand moveCommand) {
        this.currentMoveCommand = moveCommand;
    }
}
