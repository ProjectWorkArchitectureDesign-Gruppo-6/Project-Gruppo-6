package projectworkgroup6.Controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import projectworkgroup6.Command.Command;
import projectworkgroup6.Command.ZoomCommand;
import projectworkgroup6.Decorator.BorderDecorator;
import projectworkgroup6.Decorator.FillDecorator;
import projectworkgroup6.Factory.*;
import projectworkgroup6.Model.*;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.Command.CommandManager;

import projectworkgroup6.State.SingleSelectState;
import projectworkgroup6.View.*;
import projectworkgroup6.View.ShapeView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuBarController {

    @FXML
    private MenuBar topMenuBar;
    @FXML
    private Menu fileBtn;
    @FXML
    private Menu viewBtn;

    private File fileCorrente = null;

    private MainController mainController;
    private CanvasController canvasController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setCanvasController(CanvasController controller) {
        this.canvasController = controller;
    }
    // Eventuali metodi di gestione per i menu

    @FXML
    private void load(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Carica disegno JSON");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("File JSON", "*.json"));

            StateController.getInstance().getMap().clear();
            //
            StateController.getInstance().notifyShapeDeselected();
            fileCorrente = fileChooser.showOpenDialog(topMenuBar.getScene().getWindow());
            if (fileCorrente != null) {
                ObjectMapper mapper = new ObjectMapper();

                Map<String,ShapeCreator> creators = StateController.getInstance().getCreators();

                List<Shape> shapes = mapper.readValue(fileCorrente, new TypeReference<List<Shape>>() {});

                for (Shape shape : shapes) {

                    String type = shape.getClass().getSimpleName(); // "Rectangle", "Ellipse", ...
                    ShapeCreator creator = creators.get(type);


                    ShapeView view = creator.createShapeView(shape);
                    BorderDecorator border = new BorderDecorator(view,shape.getBorder().toColor());
                    FillDecorator fill = new FillDecorator(border, shape.getFill().toColor());
                    StateController.getInstance().addShape(shape, fill);

                }


                System.out.println("Disegno caricato da: " + fileCorrente.getAbsolutePath());
            }
        } catch (IOException ex) {
            Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    @FXML
    private void save(ActionEvent event) {
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Salva disegno come JSON");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("File JSON", "*.json"));

            Map<Shape, ShapeView> map = StateController.getInstance().getMap();
            List<Shape> shapes = new ArrayList<>(map.keySet());

            fileCorrente = fileChooser.showSaveDialog(topMenuBar.getScene().getWindow());
            if (fileCorrente != null) {
                ObjectMapper mapper = new ObjectMapper();
                ObjectWriter writer = mapper.writerFor(new TypeReference<List<Shape>>() {}).withDefaultPrettyPrinter();
                writer.writeValue(fileCorrente, shapes);
                System.out.println("Disegno salvato in: " + fileCorrente.getAbsolutePath());
            }
        } catch (IOException ex) {
            Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void saveOnFileExist(ActionEvent event) {
        if (fileCorrente == null) {
            System.err.println("Nessun file selezionato per il salvataggio.");
            return;
        }

        Map<Shape, ShapeView> shapes = StateController.getInstance().getMap();
        List<Shape> shapeList = new ArrayList<>(shapes.keySet());

        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writerFor(new TypeReference<List<Shape>>() {}).withDefaultPrettyPrinter();
            writer.writeValue(fileCorrente, shapeList);
            System.out.println("Salvataggio completato su: " + fileCorrente.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Errore durante il salvataggio:");
            e.printStackTrace();
        }
    }
    @FXML
    private void undo(ActionEvent event) {
CommandManager.getInstance().undoLastCommand();
    }

    @FXML
    private void zoomIn50(ActionEvent event)
    {
        canvasController.zoomTo(1.5);
    }

    @FXML
    private void zoomIn100(ActionEvent event) {
        canvasController.zoomTo(2.0);
    }

    @FXML
    private void zoomIn150(ActionEvent event) {
        canvasController.zoomTo(2.5);
    }

    @FXML
    private void zoomIn200(ActionEvent event) {
        canvasController.zoomTo(3.0);
    }

    @FXML
    private void zoomOut50(ActionEvent event) {
        canvasController.zoomTo(1.0);
    }

    @FXML
    private void zoomOut100(ActionEvent event) {
        canvasController.zoomTo(0.75);
    }

    @FXML private void zoomOut150(ActionEvent event) {
        canvasController.zoomTo(0.5);
    }

    @FXML private void zoomOut200(ActionEvent event) {
        canvasController.zoomTo(0.25);
    }

    @FXML private void grid5x5(ActionEvent event) {
        canvasController.insertGrid(5);
    }

    @FXML private void grid10x10(ActionEvent event) {
        canvasController.insertGrid(10);
    }

    @FXML private void grid15x15(ActionEvent event) {
        canvasController.insertGrid(15);
    }

    @FXML private void grid20x20(ActionEvent event) {
        canvasController.insertGrid(20);
    }

    @FXML private void grid25x25(ActionEvent event) {
        canvasController.insertGrid(25);
    }
}