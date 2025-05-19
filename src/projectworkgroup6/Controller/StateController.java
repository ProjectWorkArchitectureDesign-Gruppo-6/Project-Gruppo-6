package projectworkgroup6.Controller;

import projectworkgroup6.Model.Shape;
import projectworkgroup6.State.CanvasState;
import projectworkgroup6.State.MultipleSelectState;
import projectworkgroup6.State.SingleSelectState;

import java.util.ArrayList;
import java.util.List;

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


    //Subject degli Observer
    private List<CursorObserver> observers = new ArrayList<>();

    // --- Gestione stato cursore ---
    public void setState(CanvasState state) {
        this.currentState = state;
        notifyObservers();
    }

    private void notifyObservers() {
        for (CursorObserver o : observers) {
            o.onStateChanged(currentState);
        }
    }


    public void addObserver(CursorObserver o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    //// STATO DEL CANVAS ////

    private final List<CanvasObserver> canvasObservers = new ArrayList<>();

    public void addCanvasObserver(CanvasObserver o) {
        if (!canvasObservers.contains(o))
            canvasObservers.add(o);
    }

    private void notifyCanvasToRepaint() {
        for (CanvasObserver o : canvasObservers)
            o.onCanvasChanged(getShapes());
    }

    private final List<Shape> shapes = new ArrayList<>();

    public List<Shape> getShapes() {
        return shapes;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
        notifyCanvasToRepaint();
    }

    public void removeShape(Shape shape){
        shapes.remove(shape);
        notifyCanvasToRepaint();
    }











}
