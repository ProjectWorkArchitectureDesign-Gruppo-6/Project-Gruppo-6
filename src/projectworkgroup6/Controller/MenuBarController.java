package projectworkgroup6.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import projectworkgroup6.Decorator.BorderDecorator;
import projectworkgroup6.Decorator.FillDecorator;
import projectworkgroup6.Factory.*;
import projectworkgroup6.Model.Group;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.Command.CommandManager;
import projectworkgroup6.View.GroupView;
import projectworkgroup6.View.ShapeView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller per la barra dei menu dell'applicazione.
 * Gestisce le operazioni di salvataggio/caricamento file,
 * undo delle operazioni, inserimento e rimozione della griglia e dello zoom del canvas
 */

public class MenuBarController {

    @FXML
    private VBox topMenuBar;
    @FXML
    private Menu fileBtn;
    @FXML
    private Menu viewBtn;

    private File fileCorrente = null;

    private MainController mainController;
    private CanvasController canvasController;

    //Imposta il controller principale dell'applicazione
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    //Imposta il controller del canvas
    public void setCanvasController(CanvasController controller) {
        this.canvasController = controller;
    }
    // Eventuali metodi di gestione per i menu

    //Metodo per caricare un disegno da file JSON selezionato dall'utente
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

                // Inizialmente carica solo le shape NON Group
                for (Shape shape : shapes) {
                    String type = shape.getClass().getSimpleName();
                    if (!type.equals("Group")) {
                        ShapeCreator creator = creators.get(type);
                        ShapeView view = creator.createShapeView(shape);
                        BorderDecorator border = new BorderDecorator(view, shape.getBorder().toColor());
                        FillDecorator fill = new FillDecorator(border, shape.getFill().toColor());
                        StateController.getInstance().addShape(shape, fill);
                    }
                }

                // Carica gruppi e gruppi annidati
                for (Shape shape : shapes) {
                    String type = shape.getClass().getSimpleName();
                    if (type.equals("Group")) {
                        processGroup((Group) shape, creators);
                    }
                }
                System.out.println("Disegno caricato da: " + fileCorrente.getAbsolutePath());
            }
        } catch (IOException ex) {
            Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void processGroup(Group group, Map<String, ShapeCreator> creators) {

        Map<Shape,ShapeView> map = StateController.getInstance().getMap();

        // Prendo le shapes dal gruppo
        List<Shape> children = group.getShapes();

        // inizializzo le view oer ogni shape
        List<ShapeView> views = new ArrayList<>();

        // Per ogni shape del gruppo
        for (Shape child : children) {

            // Controllo se si tratta di un gruppo innestato
            if (child instanceof Group) {

                Group nestedGroup = (Group)child;

                // Ricorsione: processa il gruppo interno
                processGroup(nestedGroup, creators);

                // Aggiungo la vista del gruppo innestato nel gruppo esterno
                views.add(map.get(nestedGroup));

                // Rimuovi dalla mappa il gruppo annidato perchè viene compreso nel gruppo esterno
                StateController.getInstance().removeShape(nestedGroup,map.get(nestedGroup));


            } else {
                // È una shape normale
                ShapeCreator creator = creators.get(child.getClass().getSimpleName());
                ShapeView childView = creator.createShapeView(child);
                BorderDecorator border = new BorderDecorator(childView, child.getBorder().toColor());
                FillDecorator fill = new FillDecorator(border, child.getFill().toColor());

                views.add(fill);
            }
        }

        // Ora crea la view per il gruppo principale
        ShapeView groupView = new GroupView(group,views);

        StateController.getInstance().addShape(group, groupView);
    }




    //Metodo per salvare lo stato attuale del canvas e le figure che contiene
    // in un nuovo file JSON selezionato dall'utente
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

    //Metodo per salvare direttamente sul file già aperto, senza aprire il dialogo di scelta
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
    /*

    @FXML
    private void zoomIn50(ActionEvent event)
    {
        StateController.getInstance().setZoom(1.5);
    }

    @FXML
    private void zoomIn100(ActionEvent event) {
        StateController.getInstance().setZoom(2.0);
    }

    @FXML
    private void zoomIn150(ActionEvent event) {
        StateController.getInstance().setZoom(2.5);    }

    @FXML
    private void zoomIn200(ActionEvent event) {
        StateController.getInstance().setZoom(3.0);    }

    @FXML
    private void zoomOut50(ActionEvent event) {
        //canvasController.zoomTo(1.0);
    }

    @FXML
    private void zoomOut100(ActionEvent event) {
        //canvasController.zoomTo(0.75);
    }

    @FXML private void zoomOut150(ActionEvent event) {
        //canvasController.zoomTo(0.5);
    }

    @FXML private void zoomOut200(ActionEvent event) {
        //canvasController.zoomTo(0.25);
    }


     */

    //I seguenti metodi inseriscono una griglia di dimensione prefissata
    //Il metodo removeGrid permette di rimuovere una griglia inserita

    @FXML private void grid20x20(ActionEvent event) {
        canvasController.insertGrid(20);
    }

    @FXML private void grid30x30(ActionEvent event) {
        canvasController.insertGrid(30);
    }

    @FXML private void grid50x50(ActionEvent event) {
        canvasController.insertGrid(50);
    }

    @FXML private void grid80x80(ActionEvent event) {
        canvasController.insertGrid(80);
    }

    @FXML private void grid100x100(ActionEvent event) {
        canvasController.insertGrid(100);
    }

    public void removeGrid(ActionEvent actionEvent) { canvasController.insertGrid(0);}


}