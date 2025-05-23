package projectworkgroup6.Controller;

import javafx.scene.paint.Color;
import projectworkgroup6.Decorator.SelectedDecorator;
import projectworkgroup6.Factory.EllipseCreator;
import projectworkgroup6.Factory.LineCreator;
import projectworkgroup6.Factory.RectangleCreator;
import projectworkgroup6.Factory.ShapeCreator;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.CanvasState;
import projectworkgroup6.State.SingleSelectState;
import projectworkgroup6.View.ShapeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateController{

    //StateController rappresenta il contesto degli stati concreti, implementati con il pattern State
    //Per ogni stato attivo, StateController gestisce le comunicazioni tra i vari Controller,
    // che effettuano operazioni diverse in base allo stato in cui ci troviamo

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

    //// STATO DEL CURSORE ////
    private CanvasState currentState = SingleSelectState.getInstance();


    //// STATO DEL COLORE ////
    private Color currentStroke = new Color(0,0,0,1); //nero di default


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

    //// STATO DEL CANVAS ////


    private void notifyCanvasToRepaint() {
        for (StateObserver o : observers)
            o.onCanvasChanged(getMap());
    }



    private final Map<Shape, ShapeView> map = new HashMap<>();

    public Map<Shape, ShapeView> getMap() {
        return map;
    }


    public void addShape(Shape shape, ShapeView shapeView) {
        map.put(shape,shapeView);
        notifyCanvasToRepaint();
    }




    public void removeShape(Shape shape, ShapeView shapeView){
        map.remove(shape,shapeView);
        notifyCanvasToRepaint();
    }


    public void setStrokeColor(Color borderColor) {
        this.currentStroke = borderColor;
        notifyObserversToHandleColor();
    }

    private void notifyObserversToHandleColor() {
        for (StateObserver o : observers)
            o.onColorChanged(currentStroke);
    }


    // Mappa per se
    private static final Map<String, ShapeCreator> creators = new HashMap<>();

    static {
        creators.put("Rectangle", RectangleCreator.getInstance());
        creators.put("Ellipse", EllipseCreator.getInstance());
        creators.put("Line", LineCreator.getInstance());
    }

    public Map<String, ShapeCreator> getCreators(){
        return creators;
    }



}
