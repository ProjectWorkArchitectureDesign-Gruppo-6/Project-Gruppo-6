package projectworkgroup6.State;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.DeleteCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Factory.TextBoxCreator;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;

import java.util.Map;

public class DeleteState implements CanvasState{

    @Override
    public void handleClick(MouseEvent e, double x, double y, Map<Shape, ShapeView> map) {

        for (Shape shape : map.keySet()) {
            if (shape.contains(x, y)) {
                ShapeView view = map.get(shape);
                DeleteCommand cmd = new DeleteCommand(shape, view);
                CommandManager.getInstance().executeCommand(cmd);
                break; // elimina solo il primo shape cliccato
            }
        }
    }

    @Override
    public void handlePression(double x, double y) {

    }

    @Override
    public void handlePressionRotate(double x, double y) {

    }

    @Override
    public void handleMouseDragged(double x, double y) {

    }

    @Override
    public void handleMouseReleased(double x, double y) {

    }

    @Override
    public void recoverShapes(Map<Shape, ShapeView> map) {

    }

    @Override
    public void handleDelete(KeyEvent event, Map<Shape, ShapeView> map) {

    }

    @Override
    public void handleColorChanged(Color currentStroke, Color currentFill) {

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
}
