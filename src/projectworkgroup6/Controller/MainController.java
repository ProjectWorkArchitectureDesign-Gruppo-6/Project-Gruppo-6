package projectworkgroup6.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import projectworkgroup6.Model.CursorMode;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {


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
        loader = new FXMLLoader(getClass().getResource("/projectworkgroup6/View/MenuBar.fxml"));
        AnchorPane menu = loader.load();
        menuBarController = loader.getController();
        menuBarController.setMainController(this);

        // Carica ToolBar
        loader = new FXMLLoader(getClass().getResource("/projectworkgroup6/View/ToolBar.fxml"));
        AnchorPane tool = loader.load();
        toolBarController = loader.getController();
        toolBarController.setMainController(this);

        // Carica Canvas
        loader = new FXMLLoader(getClass().getResource("/projectworkgroup6/View/Canvas.fxml"));
        AnchorPane canvas = loader.load();
        canvasController = loader.getController();
        canvasController.setMainController(this);

        // Aggiungi tutto alla vista principale
        //principalPane.getChildren().addAll(menu, tool, canvas);
        mainVBox.getChildren().addAll(menu, tool, canvas);

        // Puoi anche posizionarli con AnchorPane.setTopAnchor(...) ecc.
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            stateController = StateController.getInstance();
            loadInterfaceComponents();

            //stateController.addObserver(canvasController); /*l'osservazione dell'evento che avviene sul convas va implementata direttamente nel canvas controller*/
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

}
