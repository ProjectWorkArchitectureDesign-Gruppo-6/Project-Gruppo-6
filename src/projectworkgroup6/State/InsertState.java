package projectworkgroup6.State;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.BorderDecorator;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Factory.ShapeCreator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Nello stato di Inserimento, ci occupiamo dell'inserimento delle figure.

public class InsertState implements CanvasState {


    private final ShapeCreator creator; //utilizzo del Factory
    private Color currentStroke = new Color(0,0,0,1);

    public InsertState(ShapeCreator creator) {
        this.creator = creator;
    }

    @Override
    public void handleClick(double x, double y, Map<Shape, ShapeView> map) {  //Disegno della figura
        Shape shape = creator.createShape(x, y);
        ShapeView shapeView = creator.createShapeView(shape);
        BorderDecorator shapeBorder = new BorderDecorator(shapeView,currentStroke);
        StateController.getInstance().addShape(shape, shapeBorder); // Aggiungo logica e grafica della shape allo stato

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
    public void recoverShapes(Map<Shape, ShapeView> map) {
        Map<Shape, ShapeView> copy = new HashMap<Shape, ShapeView>(map);
        for (Map.Entry<Shape, ShapeView> entry : copy.entrySet()) {
            Shape s = entry.getKey();
            ShapeView v = entry.getValue();

            // Deseleziona logicamente
            s.setSelected(false);

            // Se la view è decorata (cioè è un SelectedDecorator), la sostituiamo
            if (v instanceof SelectedDecorator) {
                // Rimuovi la versione decorata dalla vista (cioè dallo stato attuale)
                StateController.getInstance().removeShape(s,v);

                // Crea la versione "base" della view senza decorator
                ShapeView baseView = ((SelectedDecorator) v).undecorate();

                // Aggiungi di nuovo la versione base alla vista
                StateController.getInstance().addShape(s,baseView);

            }
        }
    }

    @Override
    public void handleDelete(KeyEvent event, Map<Shape,ShapeView> map) {
        //Nulla
    }

    @Override
    public void handleColorChanged(Color currentStroke) {
        this.currentStroke = currentStroke;

    }
}
