package projectworkgroup6.State;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import projectworkgroup6.Command.*;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Controller.DropDownController;
import projectworkgroup6.Decorator.BorderDecorator;
import projectworkgroup6.Decorator.FillDecorator;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Polygon;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.Model.TextBox;
import projectworkgroup6.View.ShapeView;

import java.util.*;


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

    private SelectedDecorator selectedShape;


    @Override
    public void handleClick(MouseEvent e, double x, double y, Map<Shape, ShapeView> map) {
        System.out.println("Hai cliccato in modalità SELECT su: " + x + ", " + y);

        if (e.getButton()== MouseButton.SECONDARY) {
            StateController.getInstance().notifyMouseRightClick(x,y);
        } else
        {
            for (Shape s : map.keySet()) {
                Point2D unrotated = rotatePointBack(x, y, s);
                double x2 = unrotated.getX();
                double y2 = unrotated.getY();
                if (s.contains(x2, y2)) {
                    if (s.isSelected()) {
                        deselectShape(s);
                        return;
                    }

                    deselectAll(map);

                    selectShape(s, map);
                    return;
                }
            }

            deselectAll(map);
            StateController.getInstance().notifyShapeDeselected();
            System.out.println("dopo lo spostamento");

        }
    }

    private void deselectAll(Map<Shape, ShapeView> map) {
        Map<Shape, ShapeView> copy = new HashMap<Shape, ShapeView>(map);
        for (Map.Entry<Shape, ShapeView> entry : copy.entrySet()) {
            Shape s = entry.getKey();
            ShapeView v = entry.getValue();


            // Deseleziona logicamente
            s.setSelected(false);

            // Rimuovi dal gruppo provvisorio
            s.setGroup(0);

            // Annulla gruppo provvisorio
            MultipleSelectState.getInstance().setGroup(null);

            //Nascondi menù a tendina
            StateController.getInstance().notifyGroupDeselected();

            // Rimuovi la versione decorata dalla vista (cioè dallo stato attuale)
            StateController.getInstance().removeShape(s,v);

            // Crea la versione "base" della view senza decorator
            ShapeView baseView = v.undecorate();

            // Aggiungi di nuovo la versione base alla vista
            StateController.getInstance().addShape(s,baseView);


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

    //Gestisce la pressione del mouse su una figura selezionata.
    //Determina se l'utente vuole traslare o ridimensionare la figura.
    @Override
    public void handlePression(double x, double y) {
        //Controlla se nessuna figura selezionata o shape null
        if (selectedShape == null || selectedShape.getShape() == null) {
            return;
        }

        // Converto il punto cliccato rispetto alla rotazione della figura
        Point2D unrotated = rotatePointBack(x, y, selectedShape.getShape());
        double x2 = unrotated.getX();
        double y2 = unrotated.getY();

        // Il metodo capisce se l'utente vuole traslare, ridimensionare o stretchare la shape
        if (selectedShape != null) {

            // Controllo se vuole spostare la shape

            boolean isMoveClicked = checkClickOnMoveButton(selectedShape,x2, y2);


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

                } else {
                    //controllo se vuole stretchare
                    AbstractMap.SimpleEntry<Double, Double> stretchPoint;
                    stretchPoint = checkClickOnStretchHandles(selectedShape, x, y);
                    if (stretchPoint != null) {
                        StretchState ss = new StretchState(selectedShape);
                        ss.startDragging(stretchPoint.getKey(), stretchPoint.getValue());
                        ss.setStretchCommand(new StretchCommand(selectedShape.getShape()));
                        StateController.getInstance().setState(ss);

                    }
                }
            }
        }
    }

    //Gestisce il click sul bottone di rotazione.
    //Se cliccato, cambia lo stato in modalità rotazione.
    @Override
    public void handlePressionRotate(double x, double y) {
        //Controlla se nessuna figura selezionata o shape null
        if (selectedShape == null || selectedShape.getShape() == null) {
            return;
        }

        //Converti il punto cliccato rispetto alla rotazione della figura
        Point2D unrotated = rotatePointBack(x, y, selectedShape.getShape());
        double x2 = unrotated.getX();
        double y2 = unrotated.getY();

        if (selectedShape != null) {
            // Controllo se è stato cliccato il bottone di rotazione
            boolean isRotateClicked = checkClickOnRotateButton(selectedShape, x2, y2);

            if (isRotateClicked) {
                // Creo lo stato di rotazione
                RotationState rs = new RotationState(selectedShape);
                rs.startRotating(x, y);
                rs.setRotateCommand(new RotateCommand(selectedShape.getShape()));

                StateController.getInstance().setState(rs);
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

    //gestico maniglie per lo strch
    private AbstractMap.SimpleEntry<Double, Double> checkClickOnStretchHandles(SelectedDecorator selectedShape, double x, double y) {
        List<AbstractMap.SimpleEntry<Double, Double>> stretchHandles = selectedShape.getStretchHandles();
        double size = 10; // tolleranza

        for (AbstractMap.SimpleEntry<Double, Double> stretchHandle : stretchHandles) { // per ogni maniglia
            double hx = stretchHandle.getKey(); // prendo coordinata x
            double hy = stretchHandle.getValue(); // prendo coordinata y

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

    //Verifico se il click è avvenuto sul bottone di rotazione
    private boolean checkClickOnRotateButton(SelectedDecorator selectedShape, double x, double y) {
        double buttonX = selectedShape.getRotateButtonX();
        double buttonY = selectedShape.getRotateButtonY();

        double diameter = 20;

        return x >= buttonX && x <= buttonX + diameter && y >= buttonY && y <= buttonY + diameter;
    }

    //Metodo per inverte la rotazione di un punto rispetto al centro della shape.
    //Serve per riportare il punto cliccato nelle coordinate originali della shape.
    public static Point2D rotatePointBack(double x, double y, Shape shape) {
        double angle = Math.toRadians(-shape.getRotation()); // rotazione inversa

        double centerX, centerY;
        if (shape instanceof Polygon) {
            List<double[]> vertices = ((Polygon) shape).getVertices();
            centerX = vertices.stream().mapToDouble(v -> v[0]).average().orElse(0);
            centerY = vertices.stream().mapToDouble(v -> v[1]).average().orElse(0);
        } else {
            centerX = shape.getXc() + shape.getDim1() / 2.0;
            centerY = shape.getYc() + shape.getDim2() / 2.0;
        }

        double dx = x - centerX;
        double dy = y - centerY;

        double rotatedX = dx * Math.cos(angle) - dy * Math.sin(angle) + centerX;
        double rotatedY = dx * Math.sin(angle) + dy * Math.cos(angle) + centerY;

        return new Point2D(rotatedX, rotatedY);
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

            if(v != selectedShape){
                // Deseleziona logicamente
                s.setSelected(false);

                // Rimuovi dal gruppo provvisorio
                s.setGroup(0);

                // Annulla gruppo provvisorio
                MultipleSelectState.getInstance().setGroup(null);

                //Nascondi menù a tendina
                StateController.getInstance().notifyGroupDeselected();

                // Rimuovi la versione decorata dalla vista (cioè dallo stato attuale)
                StateController.getInstance().removeShape(s,v);

                // Crea la versione "base" della view senza decorator
                ShapeView baseView = v.undecorate();

                // Aggiungi di nuovo la versione base alla vista
                StateController.getInstance().addShape(s,baseView);
            }



        }
    }

    @Override
    public void handleDelete(KeyEvent event, Map<Shape,ShapeView> map) {
        if(event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE){
            
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

    @Override
    public void handleChangeFontColor(Color currentFontColor) {


        StateController.getInstance().removeShape(selectedShape.getShape(),selectedShape); // rimuovo la shape dallo stato

        ColorModel fontColor = ColorModel.fromColor(currentFontColor);

        selectedShape.getShape().setSelected(false); //rimuovo la selezione

        CommandManager.getInstance().executeCommand(new ChangeFontColorCommand((TextBox) selectedShape.getShape(),fontColor)); //richiamo il comando per cambiare il font sulla shape selezionata

        StateController.getInstance().addShape(selectedShape.getShape(), selectedShape); //aggiorno lo stato con la shape modificata
        //usando il pattern command sono tutte e tre operazioni undoabili
    }

    @Override
    public void handleChangeFontName(String currentFontName) {
        StateController.getInstance().removeShape(selectedShape.getShape(),selectedShape); // rimuovo la shape dallo stato


        selectedShape.getShape().setSelected(false);

        CommandManager.getInstance().executeCommand(new ChangeFontNameCommand((TextBox) selectedShape.getShape(),currentFontName));

        StateController.getInstance().addShape(selectedShape.getShape(), selectedShape);
    }

    @Override
    public void handleChangeFontSize(int currentFontSize){
        StateController.getInstance().removeShape(selectedShape.getShape(),selectedShape); // rimuovo la shape dallo stato


        selectedShape.getShape().setSelected(false);

        CommandManager.getInstance().executeCommand(new ChangeFontSizeCommand((TextBox) selectedShape.getShape(),currentFontSize));

        StateController.getInstance().addShape(selectedShape.getShape(), selectedShape);
    }
}
