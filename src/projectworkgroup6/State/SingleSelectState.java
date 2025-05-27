package projectworkgroup6.State;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import projectworkgroup6.Command.*;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Controller.DropDownController;
import projectworkgroup6.Decorator.BorderDecorator;
import projectworkgroup6.Decorator.FillDecorator;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;
import javafx.scene.input.MouseButton;

import java.util.*;

// Nello stato Seleziona, ci occupiamo della modifica delle figure presenti nel Canvas.

public class SingleSelectState implements CanvasState {

    // --- Singleton classico ---
    private static SingleSelectState instance;

    private SingleSelectState() {
        // Costruttore privato per Singleton
    }

    public static SingleSelectState getInstance() {
        if (instance == null) {
            instance = new SingleSelectState();
        }
        return instance;
    }

    private SelectedDecorator selectedShape = null;


    @Override
    public void handleClick(MouseEvent e,double x, double y, Map<Shape, ShapeView> map) {
        System.out.println("Hai cliccato in modalità SELECT su: " + x + ", " + y);

        if (e.getButton()==MouseButton.SECONDARY) {
            StateController.getInstance().notifyMouseRightClick(x,y);
        }
        for (Shape s : map.keySet()) {
            if (s.contains(x, y)) {
                if (s.isSelected()) {
                    deselectShape(s);
                    return;
                }

                // Deseleziona eventuale altra shape selezionata
                deselectAll(map);

                selectShape(s, map);
                return;
            }
        }

        deselectAll(map);
        //nascondi il menu a tendina
        StateController.getInstance().notifyShapeDeselected();

    }

    private void deselectAll(Map<Shape, ShapeView> map) {
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


    private void deselectShape(Shape s) {
        s.setSelected(false);
        //notifica deselezionamento della figura
        StateController.getInstance().notifyShapeDeselected();
        StateController.getInstance().removeShape(s, selectedShape);
        StateController.getInstance().addShape(s, selectedShape.undecorate());
        selectedShape = null;
    }


    private void selectShape(Shape s, Map<Shape, ShapeView> map) {

        s.setSelected(true);
        //notifica selezionamento della figura
        StateController.getInstance().notifyShapeSelected(s);
        ShapeView baseShapeView = map.get(s);
        selectedShape = new SelectedDecorator(baseShapeView);
        StateController.getInstance().addShape(s,selectedShape);

    }

    public ShapeView getSelectedShape() {
        return selectedShape;
    }

    @Override
    public void handlePression(double x, double y) {

        // Il metodo capisce se l'utente vuole traslare, ridimensionare o stretchare la shape
        if (selectedShape != null) {

            // Controllo se vuole spostare la shape

            boolean isMoveClicked = checkClickOnMoveButton(selectedShape,x,y);


            if (isMoveClicked) {
                TranslationState ts = new TranslationState(selectedShape);
                ts.startDragging(x,y);
                ts.setMoveCommand(new MoveCommand(selectedShape.getShape()));
                StateController.getInstance().setState(ts);
            }
            else{

                // Controllo se vuole ridimensionare
                AbstractMap.SimpleEntry<Double,Double> mobilePoint;
                mobilePoint = checkClickOnHandles(selectedShape, x, y);

                if(mobilePoint != null){
                    ResizeState rs = new ResizeState(selectedShape);
                    rs.startDragging(mobilePoint.getKey(), mobilePoint.getValue());
                    rs.setResizeCommand(new ResizeCommand(selectedShape.getShape()));
                    StateController.getInstance().setState(rs);

                }
            }
        }
    }

    private AbstractMap.SimpleEntry<Double, Double> checkClickOnHandles(SelectedDecorator selectedShape, double x, double y) {
        List<AbstractMap.SimpleEntry<Double, Double>> handles = selectedShape.getHandles();
        double size = 10; // tolleranza

        for (AbstractMap.SimpleEntry<Double, Double> handle : handles) { // per ogni maniglia
            double hx = handle.getKey(); // prendo coordinata x
            double hy = handle.getValue(); // prendo coordinata y

            if (x >= hx && x <= hx + size && y >= hy && y <= hy + size) { // se il click avviene sulla maniglia
                return new AbstractMap.SimpleEntry<>(hx, hy); // restituisco la maniglia cliccata
            }
        }

        return null; // altrimenti nessuna maniglia è stata cliccata
    }


    private boolean checkClickOnMoveButton(SelectedDecorator selectedShape, double x, double y) {
        double buttonX = selectedShape.getMoveButtonX();
        double buttonY = selectedShape.getMoveButtonY();

        double diameter = 20;


        boolean first = x >= buttonX  && x <= buttonX + diameter;
        boolean second = y >= buttonY && y <= buttonY + diameter;

        return first && second;
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
        //Non deve fare nulla
    }

    @Override
    public void handleDelete(KeyEvent event, Map<Shape,ShapeView> map) {
        if(event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE){
            //
            StateController.getInstance().notifyShapeDeselected();
            for(Shape s : map.keySet()){
                if (map.get(s) == selectedShape) {
                    DeleteCommand cmd = new DeleteCommand(s, map.get(s));
                    CommandManager.getInstance().executeCommand(cmd);
                    selectedShape = null;
                    break;
                }
            }
        }
    }


    @Override
    public void handleColorChanged(Color currentStroke, Color currentFill) {

        StateController.getInstance().removeShape(selectedShape.getShape(),selectedShape); // rimuovo la shape dallo stato

        // Converto i colori
        ColorModel border = ColorModel.fromColor(currentStroke);
        ColorModel fill = ColorModel.fromColor(currentFill);



        if(border.toRgbaString().equals(selectedShape.getShape().getBorder().toRgbaString())){
            CommandManager.getInstance().executeCommand(new ChangeFillCommand(selectedShape.getShape(),fill));

        } else{
            CommandManager.getInstance().executeCommand(new ChangeBorderCommand(selectedShape.getShape(),border));
        }

        selectedShape.getShape().setSelected(false);
        BorderDecorator borderDecorator = new BorderDecorator(selectedShape.undecorate().undecorate().undecorate(),border.toColor());
        FillDecorator fillDecorator = new FillDecorator(borderDecorator,fill.toColor());
        StateController.getInstance().addShape(selectedShape.getShape(), fillDecorator);
        System.out.println(StateController.getInstance().getMap());
    }

    @Override
    public void handleKeyTyped(KeyEvent event, Map<Shape, ShapeView> map) {

    }
}
