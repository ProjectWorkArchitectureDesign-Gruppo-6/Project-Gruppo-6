package projectworkgroup6.State;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.MoveCommand;
import projectworkgroup6.Command.RotateCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.GroupBorderDecorator;
import projectworkgroup6.Model.Group;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.GroupView;
import projectworkgroup6.View.ShapeView;

import java.util.List;
import java.util.Map;

public class TemporaryRotationState implements CanvasState{

    private final GroupBorderDecorator shapeView;
    private double lastX;
    private double lastY;

    private double startX, startY;
    private double initialAngle;
    private boolean isRotating = false;

    private RotateCommand rotateCommand;

    private List<ShapeView> individualViews;

    public TemporaryRotationState(GroupBorderDecorator shapeView) {
        this.shapeView = shapeView;
    }



    public void startRotating(double x, double y, List<ShapeView> individualViews) {
        Shape shape = shapeView.getShape();

        this.startX = x;
        this.startY = y;
        this.initialAngle = shape.getRotation();
        this.isRotating = true;
        this.individualViews = individualViews;
    }



    @Override
    public void handleClick(MouseEvent e, double x, double y, Map<Shape, ShapeView> map) {

        Group group = (Group)shapeView.getShape();
        StateController.getInstance().removeShape(group, shapeView);

        double groupAngle = Math.toRadians(group.getRotation());
        double cx = group.getX();
        double cy = group.getY();

        for(int i = 0; i< group.getShapes().size(); i++){

            // Offset rispetto al centro del gruppo
            double dx = group.getShapes().get(i).getX() - cx;
            double dy = group.getShapes().get(i).getY() - cy;

            // Rotazione attorno al centro del gruppo
            double rotatedX = dx * Math.cos(groupAngle) - dy * Math.sin(groupAngle);
            double rotatedY = dx * Math.sin(groupAngle) + dy * Math.cos(groupAngle);

            // Nuova posizione assoluta
            group.getShapes().get(i).setX(cx + rotatedX);
            group.getShapes().get(i).setY(cy + rotatedY);

            // Nuova rotazione: somma con quella del gruppo
            group.getShapes().get(i).setRotation(group.getShapes().get(i).getRotation() + group.getRotation());
            StateController.getInstance().addShape(group.getShapes().get(i), individualViews.get(i));
        }

        // rendering del gruppo temporaneo
        //StateController.getInstance().addGroup(shapeView);

        MultipleSelectState.getInstance().setGroup(group);
        StateController.getInstance().setState(MultipleSelectState.getInstance());

    }

    @Override
    public void handlePression(double x, double y) {

    }

    @Override
    public void handlePressionRotate(double x, double y) {

    }

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

    @Override
    public void handleMouseReleased(double x, double y) {

        if (!isRotating) return;

        CommandManager.getInstance().executeCommand(rotateCommand);
        isRotating = false;

        //mostra il menu a tendina
        Group group = (Group)shapeView.getShape();
        if(group.getShapes().size() > 1){
            StateController.getInstance().notifyShapeSelected(shapeView.getShape());
        }

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

    public void setRotateCommand(RotateCommand rotateCommand) {
        this.rotateCommand = rotateCommand;
    }
}
