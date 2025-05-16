package projectworkgroup6.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import projectworkgroup6.Factory.ShapeCreator;
import projectworkgroup6.Model.CursorMode;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StateController implements Initializable {


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

    // Stato del cursore
    private CursorMode currentMode = CursorMode.SELECT;
    private ShapeCreator currentCreator = null;


    //Subject degli Observer
    private List<CursorObserver> observers = new ArrayList<>();

    // --- Gestione stato cursore ---
    public void setCursorMode(CursorMode mode, ShapeCreator creator) {
        this.currentMode = mode;
        this.currentCreator = creator;

        notifyObservers();
    }

    public CursorMode getCursorMode() {

        return currentMode;
    }

    public void addObserver(CursorObserver o) {
        observers.add(o);
    }

    private void notifyObservers() {
        for (CursorObserver o : observers) {
            o.onCursorModeChanged(currentMode);

            o.onCurrentCreatorChanged(currentCreator);

        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
    }









}
