package projectworkgroup6.State;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import projectworkgroup6.Command.*;
import projectworkgroup6.Controller.StateController;
import projectworkgroup6.Decorator.*;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Group;
import projectworkgroup6.Model.Polygon;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.GroupView;
import projectworkgroup6.View.ShapeView;

import javax.swing.plaf.nimbus.State;
import java.util.*;

// Nello stato Seleziona, ci occupiamo della modifica delle figure presenti nel Canvas.

public class MultipleSelectState implements CanvasState {

    // --- Singleton classico ---
    private static MultipleSelectState instance;

    // In questo stato gestisco un Group temporaneo che permette di operare su più shape selezionate
    // Il group sarà poi inserito nello stato solo quando l'utente cliccherà su "Crea Gruppo"
    private static Group group;
    private static List<ShapeView> selections = new ArrayList<ShapeView>();

    private MultipleSelectState() {
        // Costruttore privato per Singleton
    }

    public static MultipleSelectState getInstance() {
        if (instance == null) {
            instance = new MultipleSelectState();
        }
        //group = null; // Ogni volta che entro nello stato, considero un nuovo gruppo temporaneo
        //selections = new ArrayList<ShapeView>(); // basato su un insieme temporaneo di figure
        return instance;
    }

    public void setGroup(Group group){
        this.group = group;
        if(group == null){
            selections = new ArrayList<>();
        }

    }

    private int currentGroup;





    public void handleClick(MouseEvent e,double x, double y, Map<Shape, ShapeView> map) {
        System.out.println("Hai cliccato in modalità MULTIPLE SELECT su: " + x + ", " + y);

        if (e.getButton()== MouseButton.SECONDARY) {
            StateController.getInstance().notifyMouseRightClick(x,y);
        } else{
            for (Shape s : map.keySet()) { // controllo se il click è accaduto su una figura, per ogni figura
                if (s.contains(x, y)) { // se ho cliccato su una figura
                    if (s.isSelected()) { // se questa fa già parte del gruppo
                        deselectShape(s, map); // la deseleziono
                        break; //gestisco un click per volta, non ha senso continuare il ciclo
                    } else { // altrimenti, se ho cliccato su una shape non selezionata
                        selectShape(s, map); // la seleziono
                        break;
                    }
                }
                // se clicchi a vuoto non fai nulla
            }
            calculateBorderGroup(selections, map);
        }
    }



    private void selectShape(Shape s, Map<Shape, ShapeView> map) {

        s.setSelected(true);
        s.setGroup(currentGroup + 1);
        ShapeView view = map.get(s);
        System.out.println(map);
        System.out.println("Nella ms : " + view);
        view = new MultiSelectedDecorator(view);
        StateController.getInstance().addShape(s,view);
        selections.add(view);

    }

    private void deselectShape(Shape s, Map<Shape, ShapeView> map) {
        s.setSelected(false);
        s.setGroup(0);
        //StateController.getInstance().notifyShapeDeselected();
        ShapeView view = map.get(s);
        StateController.getInstance().removeShape(s, view); // rimuovo la grafica di selezione
        StateController.getInstance().addShape(s, view.undecorate()); // aggiungo la grafica prima del selezionamento
        selections.remove(view);

    }


    private void calculateBorderGroup(List<ShapeView> selections, Map<Shape, ShapeView> map) {

        currentGroup = StateController.getInstance().getCurrentGroup();
        System.out.println("Il gruppo corrente è: " + currentGroup);

        // Se non ho selezionato nulla, non devo creare il gruppo
        if (selections == null || selections.isEmpty()) return;

        // Dalle shape selezionate, ottengo i model
        List<Shape> shapes = new ArrayList<Shape>();
        for (int i = 0; i < selections.size(); i++){
            selections.get(i).getShape().setGroup(currentGroup+1);
            shapes.add(selections.get(i).getShape());
        }

        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;

        for (Shape shape : shapes) {
            double x = shape.getXc();
            double y = shape.getYc();
            double w = shape.getDim1();
            double h = shape.getDim2();
            double angle = Math.toRadians(shape.getRotation());

            double cx;
            double cy;

            if(shape instanceof Polygon){
                List<double[]> vertices = ((Polygon) shape).getVertices();

                cx = vertices.stream().mapToDouble(v -> v[0]).average().orElse(0);
                cy = vertices.stream().mapToDouble(v -> v[1]).average().orElse(0);

            }else{
                cx = x + w / 2.0;
                cy = y + h / 2.0;
            }


            double[][] corners = {
                    {x, y},
                    {x + w, y},
                    {x, y + h},
                    {x + w, y + h}
            };

            for (double[] corner : corners) {
                double dx = corner[0] - cx;
                double dy = corner[1] - cy;

                double rotatedX = cx + dx * Math.cos(angle) - dy * Math.sin(angle);
                double rotatedY = cy + dx * Math.sin(angle) + dy * Math.cos(angle);

                minX = Math.min(minX, rotatedX);
                minY = Math.min(minY, rotatedY);
                maxX = Math.max(maxX, rotatedX);
                maxY = Math.max(maxY, rotatedY);
            }
        }


        // Definisci centro e limiti bordi del Group
        double centerX = minX + (maxX - minX) / 2;
        double centerY = minY + (maxY - minY) / 2;

        double groupX = centerX;
        double groupY = centerY;
        double groupWidth = maxX - minX;
        double groupHeight = maxY - minY;


        // Costruisci il Group temporaneo

        group = new Group(shapes,groupX,groupY,false,groupWidth,groupHeight,new ColorModel(0,0,0,0), new ColorModel(0,0,0,0),map.size() + 1 - shapes.size(),currentGroup+1);


        // Costruisci la view
        ShapeView groupView = new GroupView(group,selections);
        groupView = new GroupBorderDecorator(groupView);

        // rendering del gruppo temporaneo
        StateController.getInstance().addGroup(groupView);

        if(selections.size() > 1){
            StateController.getInstance().notifyShapeSelected(group);
            StateController.getInstance().addGroup(groupView);

        }
        else{
            StateController.getInstance().notifyShapeDeselected();
        }


    }



    @Override
    public void handlePression(double x, double y) {

        // Il metodo capisce se l'utente vuole traslare, ridimensionare o stretchare la shape
        if (group != null) {

            ShapeView groupView = new GroupView(group,selections);
            GroupBorderDecorator view = new GroupBorderDecorator(groupView);
            Map<Shape,ShapeView> map = StateController.getInstance().getMap();

            // Controllo se vuole spostare il gruppo

            boolean isMoveClicked = checkClickOnMoveButton(view, x, y);


            if (isMoveClicked) {


                // Aggiungo temporaneamente il gruppo allo stato
                List<ShapeView> individualViews = new ArrayList<ShapeView>();
                for(Shape s : group.getShapes()){
                    individualViews.add(map.get(s));
                    StateController.getInstance().removeShape(s, map.get(s));
                }
                StateController.getInstance().addShape(group,view);

                TemporaryTranslationState ts = new TemporaryTranslationState(view);
                ts.startDragging(x,y,individualViews);
                ts.setMoveCommand(new MoveCommand(group));
                StateController.getInstance().setState(ts);
            }
            else{

                // Controllo se vuole ridimensionare
                AbstractMap.SimpleEntry<Double,Double> mobilePoint;
                mobilePoint = checkClickOnHandles(view, x, y);

                if(mobilePoint != null){

                    // Aggiungo temporaneamente il gruppo allo stato
                    List<ShapeView> individualViews = new ArrayList<ShapeView>();
                    for(Shape s : group.getShapes()){
                        individualViews.add(map.get(s));
                        StateController.getInstance().removeShape(s, map.get(s));
                    }
                    StateController.getInstance().addShape(group,view);

                    TemporaryResizeState rs = new TemporaryResizeState(view);
                    rs.startDragging(mobilePoint.getKey(), mobilePoint.getValue(), individualViews);
                    rs.setResizeCommand(new ResizeCommand(group));
                    StateController.getInstance().setState(rs);

                }
            }
        }
    }

    private AbstractMap.SimpleEntry<Double, Double> checkClickOnHandles(GroupBorderDecorator selectedShape, double x, double y) {
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


    private boolean checkClickOnMoveButton(GroupBorderDecorator selectedShape, double x, double y) {
        double buttonX = selectedShape.getMoveButtonX();
        double buttonY = selectedShape.getMoveButtonY();

        double diameter = 20;


        boolean first = x >= buttonX  && x <= buttonX + diameter;
        boolean second = y >= buttonY && y <= buttonY + diameter;

        return first && second;
    }

    @Override
    public void handlePressionRotate(double x, double y) {

    }

    @Override
    public void handleMouseDragged(double x, double y) {
        //
    }

    @Override
    public void handleMouseReleased(double x, double y) {
        //
    }

    @Override
    public void recoverShapes(Map<Shape, ShapeView> map) {
        
        Map<Shape, ShapeView> copy = new HashMap<Shape, ShapeView>(map);
        for (Map.Entry<Shape, ShapeView> entry : copy.entrySet()) {
            Shape s = entry.getKey();
            ShapeView v = entry.getValue();

            s.setEditing(false);


            System.out.println("dopo il translate: " + group);


            if(group == null || !group.getShapes().contains(s)){

                // Deseleziona logicamente
                s.setSelected(false);

                // Rimuovi dal gruppo provvisorio
                s.setGroup(0);

                //Nascondi menù a tendina
                StateController.getInstance().notifyShapeDeselected();

                //Nascondi menù a tendina
                StateController.getInstance().notifyShapeDeselected();

                if(v instanceof SelectedDecorator){
                    // Rimuovi la versione decorata dalla vista (cioè dallo stato attuale)
                    StateController.getInstance().removeShape(s,v);

                    // Crea la versione "base" della view senza decorator
                    ShapeView baseView = v.undecorate();

                    // Aggiungi di nuovo la versione base alla vista
                    StateController.getInstance().addShape(s,baseView);
                }


                // Ridisegno il bordo sul gruppo corrente
                if(group != null){
                    ShapeView groupView = new GroupView(group,selections);
                    groupView = new GroupBorderDecorator(groupView);
                    StateController.getInstance().addGroup(groupView);
                }

            }

        }

    }

    @Override
    public void handleDelete(KeyEvent event, Map<Shape,ShapeView> map) {
        if(event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE){
            //
            StateController.getInstance().notifyShapeDeselected();
            for(Shape s : group.getShapes()){
                DeleteCommand cmd = new DeleteCommand(s, map.get(s));
                CommandManager.getInstance().executeCommand(cmd);

            }
            group = null;
            selections = new ArrayList<>();
        }
    }

    @Override
    public void handleColorChanged(Color currentStroke, Color currentFill) {

        Map<Shape,ShapeView> map = StateController.getInstance().getMap();

        for(Shape s : group.getShapes()){
            StateController.getInstance().removeShape(s, map.get(s)); // rimuovo la shape dallo stato
        }

        // Converto i colori
        ColorModel border = ColorModel.fromColor(currentStroke);
        ColorModel fill = ColorModel.fromColor(currentFill);

        // Controllo se ha cambiato il bordo o il riempimento
        if(border.toRgbaString().equals(group.getBorder().toRgbaString())){
            CommandManager.getInstance().executeCommand(new ChangeFillCommand(group,fill));
        } else{
            CommandManager.getInstance().executeCommand(new ChangeBorderCommand(group,border));
        }

        int i = 0;
        for(Shape s : group.getShapes()){
            BorderDecorator borderDecorator = new BorderDecorator(selections.get(i),border.toColor());
            FillDecorator fillDecorator = new FillDecorator(borderDecorator,fill.toColor());
            StateController.getInstance().addShape(s, fillDecorator);
            i++;
        }
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
