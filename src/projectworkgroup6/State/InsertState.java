package projectworkgroup6.State;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Factory.ShapeCreator;
import projectworkgroup6.Model.Shape;

import java.util.ArrayList;
import java.util.List;

// Nello stato di Inserimento, ci occupiamo dell'inserimento delle figure.

public class InsertState implements CanvasState {


    private final ShapeCreator creator; //utilizzo del Factory

    public InsertState(ShapeCreator creator) {
        this.creator = creator;
    }

    @Override
    public void handleClick(double x, double y, List<Shape> shapes) {  //Disegno della figura
        Shape shape = creator.createShape(x, y, javafx.scene.paint.Color.BLACK);
        StateController.getInstance().addShape(shape); // Aggiungo la shape allo stato
    }

    @Override
    public void handleMoveClick(double x, double y) {
        //System.out.println("Non definito");
    }

    @Override
    public void handleMouseDragged(double x, double y) {
        //System.out.println("Non definito");
    }

    @Override
    public void handleMouseReleased(double x, double y) {
        //System.out.println("Non definito");
    }

    @Override
    public void recoverShapes(List<Shape> shapes) {
        List<Shape> copy = new ArrayList<Shape>(shapes);
        for(Shape s : copy){
            s.setSelected(false);
            StateController.getInstance().removeShape(s);
            Shape sh = s.getShapebase();
            sh.setSelected(false);
            StateController.getInstance().addShape(sh);
        }
    }

    @Override
    public void handleDelete(KeyEvent event, List<Shape> shapes) {
        //Nulla
    }
}
