package projectworkgroup6.Controller;

import javafx.scene.paint.Color;
import projectworkgroup6.Factory.*;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.CanvasState;
import projectworkgroup6.State.SingleSelectState;
import projectworkgroup6.View.ShapeView;

import java.util.*;

public class StateController{

    //StateController rappresenta il contesto degli stati concreti, implementati con il pattern State
    //Per ogni stato attivo, StateController gestisce le comunicazioni tra i vari Controller,
    // che effettuano operazioni diverse in base allo stato in cui ci troviamo


    public CanvasController getCanvasController() {
        return canvasController;
    }


    // --- Singleton classico ---
    private static StateController instance;

    private StateController() {
        // Costruttore privato per Singleton
    }

    public static StateController getInstance() {
        if (instance == null) {
            instance = new StateController();
        }
        return instance;
    }

    //Solo per i test
    public static void setInstance(StateController testInstance) {
        instance = testInstance;
    }


    //// STATO DEL CURSORE ////
    private CanvasState currentState = SingleSelectState.getInstance();


    //// STATO DEL COLORE ////
    private Color currentStroke = new Color(0,0,0,1); //nero di default
    private Color currentFill = new Color(1,1,1,1);




    //Subject degli Observer
    private List<StateObserver> observers = new ArrayList<>();

    // --- Gestione stato cursore ---
    public void setState(CanvasState state) {
        this.currentState = state;
        notifyObservers();
    }

    private void notifyObservers() {
        for (StateObserver o : observers) {
            o.onStateChanged(currentState);
        }
    }


    public void addObserver(StateObserver o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }


    //OBSERVER SELEZIONE
    private final List<SelectionObserver> selectionObservers = new ArrayList<>();

    public void addSelectionObserver(SelectionObserver observer) {
        selectionObservers.add(observer);
    }

    public void notifyShapeSelected(Shape shape) {
        for (SelectionObserver observer : selectionObservers) {
            observer.onShapeSelected(shape);
        }
    }

    public void notifyShapeDeselected() {
        for (SelectionObserver observer : selectionObservers) {
            observer.onShapeDeselected();
        }
    }

    public void notifyMouseRightClick(double x,double y) {
        for (SelectionObserver observer : selectionObservers) {
            observer.onMouseRightClick(x,y);
        }
    }

    // Observer gruppo per menù a tendina diverso

    /*private final List<GroupObserver> groupObservers = new ArrayList<>();

    public void addGroupObserver(GroupObserver observer){
        groupObservers.add(observer);
    }

    public void notifyGroupSelected(Shape shape) {

        for (GroupObserver observer : groupObservers) {
            observer.onShapeSelected(shape);
        }
    }

    public void notifyGroupDeselected() {
        for (GroupObserver observer : groupObservers) {
            observer.onShapeDeselected();
        }
    }

    public void notifyMouseRight(double x,double y) {
        for (GroupObserver observer : groupObservers) {
            observer.onMouseRightClick(x,y);
        }
    }*/


    //// STATO DEL CANVAS ////

    private CanvasController canvasController;

    public void setCanvasController(CanvasController canvasController) {
        this.canvasController = canvasController;
    }

    public void requestCanvasFocus() {
        if (canvasController != null && canvasController.getCanvas() != null) {
            canvasController.getCanvas().requestFocus();
        }
    } //sono entrambi i metodi per dare il focus al canvas quando necessario


    public void notifyCanvasToRepaint() {
        for (StateObserver o : observers)
            o.onCanvasChanged(getMap());
    }


    private Map<Shape, ShapeView> map = new HashMap<>();
    private int currentGroup = 0;

    public Map<Shape, ShapeView> getMap() {
        return map;
    }

    public void setMap(Map<Shape, ShapeView> shapes) {
        this.map = shapes;
    }

    public void addShape(Shape shape, ShapeView shapeView) {
        map.put(shape,shapeView);
        notifyCanvasToRepaint();
    }


    public void removeShape(Shape shape, ShapeView shapeView){
        map.remove(shape,shapeView);
        notifyCanvasToRepaint();
    }

    // Per aggiornare il colore in base alla selezione sul colorPicker
    public void setStrokeColor(Color borderColor) {
        this.currentStroke = borderColor;
        notifyObserversToHandleColor();
    }

    // Per settarlo di default in base a quello dello stato attuale (evitare che al cambiamento della shape, ritorna il colore di default)
    public Color getStrokeColor(){
        return currentStroke;
    }

    public void setFillColor(Color fillColor) {
        this.currentFill = fillColor;
        notifyObserversToHandleColor();

    }

    public Color getFillColor(){
        return currentFill;
    }

    private void notifyObserversToHandleColor() {
        for (StateObserver o : observers)
            o.onColorChanged(currentStroke, currentFill);
    }

    //viene impiegata per ridisegnare il canvas durante la scrittura
    public void redrawCanvas() {
        notifyCanvasToRepaint();
    }


    //metodi per notificare il canvas dei valori selezionati dall'utente per il font
    private String currentFontName = "Arial";
    private int currentFontSize = 12;
    private Color currentFontColor = Color.BLACK;

    public void setFontFamily(String fontName) {
        this.currentFontName = fontName;
    }


    public void setFontSize(int fontSize) {
        this.currentFontSize = fontSize;
    }

    public void setFontColor(Color color) {
        this.currentFontColor = color;
    }


    public String getFontFamily() {
        return currentFontName;
    }

    public int getFontSize() {
        return currentFontSize;
    }

    public Color getFontColor() {
        return currentFontColor;
    }



    // Mappa per se
    private static final Map<String, ShapeCreator> creators = new HashMap<>();

    static {
        creators.put("Rectangle", RectangleCreator.getInstance());
        creators.put("Ellipse", EllipseCreator.getInstance());
        creators.put("Line", LineCreator.getInstance());
        creators.put("Polygon", PolygonCreator.getInstance());
        creators.put("TextBox", TextBoxCreator.getInstance());
    }

    public Map<String, ShapeCreator> getCreators(){
        return creators;
    }




    public int getCurrentGroup() {
        return currentGroup;
    }

    public void addCurrentGroup(){
        currentGroup += 1;
    }

    public void remCurrentGroup(){
        currentGroup -= 1;
    }


    public void addGroup(ShapeView groupView) {
        for (StateObserver o : observers)
            o.onCanvasAddGroup(groupView);
    }

}
