package projectworkgroup6.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    private Scene scene;
    // Riferimenti ad altri controller
    private ToolBarController toolBarController;
    private CanvasController canvasController;
    private MenuBarController menuBarController;

    private StateController stateController;


   // @FXML
    //private AnchorPane principalPane;

    @FXML
    private VBox mainVBox;


    private void loadInterfaceComponents() throws IOException {
        FXMLLoader loader;

        // Carica MenuBar
        loader = new FXMLLoader(getClass().getResource("/projectworkgroup6/Interfacce/MenuBar.fxml"));
        AnchorPane menu = loader.load();
        menuBarController = loader.getController();
        menuBarController.setMainController(this);

        // Carica ToolBar
        loader = new FXMLLoader(getClass().getResource("/projectworkgroup6/Interfacce/ToolBar.fxml"));
        AnchorPane tool = loader.load();
        toolBarController = loader.getController();
        toolBarController.setMainController(this);

        // Carica Canvas
        loader = new FXMLLoader(getClass().getResource("/projectworkgroup6/Interfacce/Canvas.fxml"));
        AnchorPane canvas = loader.load();
        canvasController = loader.getController();
        canvasController.setMainController(this);

        // Aggiungi tutto alla vista principale
        mainVBox.getChildren().addAll(menu, tool, canvas);
        VBox.setVgrow(canvas, Priority.ALWAYS);

        canvasController.bindCanvasSize(canvas);


    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            loadInterfaceComponents();
            stateController = StateController.getInstance();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    // --- Set controller figli ---
    public void setToolBarController(ToolBarController toolBarController) {
        this.toolBarController = toolBarController;
    }

    public void setCanvasController(CanvasController canvasController) {
        this.canvasController = canvasController;
    }

    public void setMenuBarController(MenuBarController menuBarController){
        this.menuBarController = menuBarController;
    }

    public void passScene(Scene scene) {
        canvasController.setScene();
    }

}
