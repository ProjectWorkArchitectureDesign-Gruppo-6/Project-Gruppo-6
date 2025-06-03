package projectworkgroup6.State;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.Command.StretchCommand;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.Polygon;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.Model.Line;
import projectworkgroup6.View.ShapeView;

import java.util.Map;

import static projectworkgroup6.State.SingleSelectState.rotatePointBack;

public class StretchState implements CanvasState {
    private final SelectedDecorator shapeView;
    private double lastX;
    private double lastY;

    private double startX, startY; // posizione iniziale del click

    private String direction = "NONE";

    private StretchCommand currentStretchCommand;

    public StretchState(SelectedDecorator shapeView) {
        this.shapeView = shapeView;
    }

    public void startDragging(double x, double y) {
        lastX = x;
        lastY = y;
        startX = x;
        startY = y;
    }

    public void setStretchCommand(StretchCommand stretchCommand) {
        this.currentStretchCommand = stretchCommand;
    }

    @Override
    public void handleClick(MouseEvent e, double x, double y, Map<Shape, ShapeView> map) {
        StateController.getInstance().setState(SingleSelectState.getInstance());
    }

    @Override
    public void handlePression(double x, double y) {
        // Non usato
    }

    @Override
    public void handlePressionRotate(double x, double y) {

    }

    @Override
    public void handleMouseDragged(double x, double y) {


        StateController.getInstance().notifyShapeDeselected();
        Shape shape = shapeView.getShape();

        Point2D unrotated = rotatePointBack(x, y, shape);
        x = unrotated.getX();
        y = unrotated.getY();

        StateController.getInstance().removeShape(shape, shapeView);

        double dx =x - lastX;
        double dy= y - lastY;

        double angle = Math.toRadians(shape.getRotation());
        dx = dx * Math.cos(angle) - dy * Math.sin(angle);
        dy = dx * Math.sin(angle) + dy * Math.cos(angle);

        if (shape instanceof Polygon) {
            strechPolygonMethod(x,y,dx,dy,shape);
            return;
        }
        double width = shape.getDim1();
        double height = shape.getDim2();


       // Stretch DESTRA
        if ((x <= shape.getX() + width/2 + 10 && x > shape.getX() + width/2 - 10 && direction.equals("NONE") ) || direction.equals("RIGHT") || (shape.getDim1()<=1 && direction.equals("LEFT")) ) {

            direction = "RIGHT";
            System.out.println(direction);
            shape.stretch(dx, 0, direction);
            currentStretchCommand.accumulate(dx,0 , direction);
        }

        // Stretch SINISTRA
        if ((x <= shape.getX() - width/2 + 10 && x > shape.getX() - width/2 -10 && direction.equals("NONE") ) || direction.equals("LEFT") || (shape.getDim1()<=1 && direction.equals("RIGHT")) ) {
              direction = "LEFT";
              System.out.println(direction);

              shape.stretch(dx, 0, direction);
              // shape.move(dx, 0);
              currentStretchCommand.accumulate(dx, 0, direction);
        }

        // Stretch BASSO
         if ((y <= shape.getY() + height/2 + 10 && y > shape.getY() + height/2 -10 && direction.equals("NONE")) || direction.equals("DOWN") || (shape.getDim2()<=1 && direction.equals("UP")) ) {
            direction = "DOWN";
            System.out.println(direction);
            shape.stretch(0, dy, direction);
            currentStretchCommand.accumulate(0, dy, direction);
        }

        // Stretch ALTO
         if ((y <= shape.getY() - height/2 + 10 && x > shape.getY() - height/2 - 10 && direction.equals("NONE")) || direction.equals("UP") || (shape.getDim2()<=1 && direction.equals("DOWN") )) {
            direction = "UP";
            System.out.println(direction);
            shape.stretch(0, dy, direction);
            currentStretchCommand.accumulate(0, dy, direction);
        }


        lastX = x;
        lastY = y;
        StateController.getInstance().addShape(shape, shapeView);
    }


    /*public void strechLineMethod(double x, double y,double dx,double dy, Shape shape) {
        double x1 = shape.getXc();
        double y1 = shape.getYc();
        double x2 = shape.getDim1();
        double y2 = shape.getDim2();
        double xQuarter = x1 + (x2 - x1)/4;
        double yQuarter = y1 + (y2 - y1)/4;
        double xThreeQuarter = x1 + 3*(x2 - x1)/4;
        double yThreeQuarter = y1 +  3*(y2 - y1)/4;


        if ((x <= xQuarter + 10 && x > xQuarter - 10  && y <= yQuarter +10 && y> yQuarter -10 && direction =="NONE") || direction =="UP" ) {
            direction = "UP";
            shape.stretch(dx, dy, "UP");
            currentStretchCommand.accumulate(dx, dy, direction);
        }
        if  ((x <= xThreeQuarter + 10 && x > xThreeQuarter - 10  && y <= yThreeQuarter +10 && y> yThreeQuarter -10 && direction =="NONE") || direction =="DOWN" ) {
        direction = "DOWN";
        shape.stretch(dx, dy,"DOWN" );
            currentStretchCommand.accumulate(dx, dy, direction);
    }
        lastX = x;
        lastY = y;
        StateController.getInstance().addShape(shape, shapeView);
    }

     */

    public void strechPolygonMethod(double x,double y,double dx,double dy,Shape shape) {
        double width = shape.getDim1();
        double height = shape.getDim2();

        if ((x <= shape.getXc() + 10 && x > shape.getXc() - 10 && y<=shape.getYc() + height/2 +10 && y > shape.getYc() + height/2 -10 && direction.equals("NONE")) || direction.equals("LEFT")|| (shape.getDim1()<=5 && direction.equals("RIGHT")) ) {
            direction = "LEFT";
            shape.stretch(dx, 0, direction);
            currentStretchCommand.accumulate(dx,0 , direction);
        }
        if ((x <= shape.getXc() +width + 10 && x > shape.getXc() + width- 10 && y<=shape.getYc() + height/2 +10 && y > shape.getYc() +height/2 -10 && direction.equals("NONE")) || direction.equals("RIGHT")|| (shape.getDim1()<=5 && direction.equals("LEFT")) ) {
            direction = "RIGHT";
            shape.stretch(dx, 0, direction);
            currentStretchCommand.accumulate(dx,0 , direction);
        }
        if ((x <= shape.getXc() +width/2 + 10 && x > shape.getXc() +width/2 - 10 && y<=shape.getYc()  +10 && y > shape.getYc() -10 && direction.equals("NONE")) || direction.equals("UP")|| (shape.getDim2()<=1 && direction.equals("DOWN")) ) {
            direction = "UP";
            shape.stretch(0, dy, direction);
            currentStretchCommand.accumulate(0,dy , direction);
        }
        if ((x <= shape.getXc() +width/2 + 10 && x > shape.getXc() +width/2 - 10 && y<=shape.getYc() + height +10 && y > shape.getYc() +height -10 && direction.equals("NONE")) || direction.equals("DOWN") || (shape.getDim2()<=1 && direction.equals("UP")) ) {
            direction = "DOWN";
            shape.stretch(0, dy, direction);
            currentStretchCommand.accumulate(0,dy , direction);
        }
        lastX = x;
        lastY = y;
        StateController.getInstance().addShape(shape, shapeView);
    }



    @Override
    public void handleMouseReleased(double x, double y) {
        System.out.println("release");
        direction = "NONE";
        currentStretchCommand.undo();
        CommandManager.getInstance().executeCommand(currentStretchCommand);
        StateController.getInstance().notifyShapeSelected(shapeView.getShape());
    }

    @Override
    public void recoverShapes(Map<Shape, ShapeView> map) {}

    @Override
    public void handleDelete(KeyEvent event, Map<Shape, ShapeView> map) {}

    @Override
    public void handleColorChanged(Color currentStroke, Color currentFill) {}

    @Override
    public void handleKeyTyped(KeyEvent event, Map<Shape, ShapeView> map) {}

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
