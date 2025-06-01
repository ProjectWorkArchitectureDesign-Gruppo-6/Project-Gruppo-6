package projectworkgroup6.State;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.RotateCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

import java.util.Map;

/**
 * Stato della canvas responsabile della gestione della rotazione delle figure.
 * Questo stato viene attivato quando l'utente clicca sul pulsante di rotazione di una shape selezionata.
 * Gestisce:
 * - il tracciamento del movimento del mouse per calcolare l'angolo di rotazione,
 * - l'applicazione dell’angolo alla figura,
 * - l’esecuzione del comando di rotazione per supportare l’undo.
 */

public class RotationState implements CanvasState {

    private final SelectedDecorator shapeView;
    private double startX, startY;
    private double initialAngle;
    private boolean isRotating = false;

    private RotateCommand rotateCommand;

    public RotationState(SelectedDecorator shapeView) {
        this.shapeView = shapeView;
    }

    //Inizializza la rotazione salvando la posizione iniziale del mouse e l'angolo corrente della figura.
    public void startRotating(double x, double y) {
        Shape shape = shapeView.getShape();

        this.startX = x;
        this.startY = y;
        this.initialAngle = shape.getRotation();
        this.isRotating = true;
    }

    public void setRotateCommand(RotateCommand rotateCommand) {
        this.rotateCommand = rotateCommand;
    }

    //Gestisce il trascinamento del mouse durante la rotazione.
    //Calcola la differenza angolare tra la posizione iniziale e quella attuale del mouse,
    //aggiorna l'angolo della figura e richiede di ridisegnare il canvas.
    @Override
    public void handleMouseDragged(double x, double y) {
        if (!isRotating) return;

        Shape shape = shapeView.getShape();


        double centerX = shape.getXc() + shape.getDim1() / 2.0;
        double centerY = shape.getYc() + shape.getDim2() / 2.0;

        double dx1 = startX - centerX;
        double dy1 = startY - centerY;
        double dx2 = x - centerX;
        double dy2 = y - centerY;

        double angle1 = Math.toDegrees(Math.atan2(dy1, dx1));
        double angle2 = Math.toDegrees(Math.atan2(dy2, dx2));
        double delta = angle2 - angle1;

        shape.setRotation(initialAngle + delta);
        rotateCommand.finalizeRotation();

        StateController.getInstance().notifyCanvasToRepaint();

    }

    //Gestisce il rilascio del mouse dopo la rotazione.
    //Registra il comando di rotazione nello storico dei comandi per abilitare undo
    //e ripristina lo stato di selezione singola.
    @Override
    public void handleMouseReleased(double x, double y) {
        if (!isRotating) return;

        CommandManager.getInstance().executeCommand(rotateCommand);
        isRotating = false;
        StateController.getInstance().setState(SingleSelectState.getInstance());
    }

    //Reindirizza il click normale a SingleSelectState (non fa nulla in questo stato)
    @Override
    public void handleClick(MouseEvent e, double x, double y, Map<Shape, ShapeView> map) {
        StateController.getInstance().setState(SingleSelectState.getInstance());
    }

    //I seguenti metodi non hanno implementazione nello stato di rotazione
    @Override public void handlePression(double x, double y) {}
    @Override public void handlePressionRotate(double x, double y) {}
    @Override public void recoverShapes(Map<Shape, ShapeView> map) {}
    @Override public void handleDelete(KeyEvent event, Map<Shape, ShapeView> map) {}
    @Override public void handleColorChanged(Color currentStroke, Color currentFill) {}
    @Override public void handleKeyTyped(KeyEvent event, Map<Shape, ShapeView> map) {}

    @Override
    public void handleChangeFontColor(Color currentFontColor) {

    }

    @Override
    public void handleChangeFontName(String currentFontName) {

    }

    @Override
    public void handleChangeFontSize(int currentFontSize) {

    }
}
