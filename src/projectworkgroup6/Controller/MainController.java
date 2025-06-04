package projectworkgroup6.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
    //
    private DropDownController dropDownMenuController;

    //private GroupMenuController groupMenuController;

    private StateController stateController;

    @FXML
    private BorderPane mainVBox;


    private void loadInterfaceComponents() throws IOException {
        FXMLLoader loader;

        // Carica MenuBar
        loader = new FXMLLoader(getClass().getResource("/projectworkgroup6/Interfacce/MenuBar.fxml"));
        VBox menu = loader.load();
        menuBarController = loader.getController();
        menuBarController.setMainController(this);

        // Carica ToolBar
        loader = new FXMLLoader(getClass().getResource("/projectworkgroup6/Interfacce/ToolBar.fxml"));
        VBox tool = loader.load();
        toolBarController = loader.getController();
        toolBarController.setMainController(this);

        // Carica Canvas
        loader = new FXMLLoader(getClass().getResource("/projectworkgroup6/Interfacce/Canvas.fxml"));
        ScrollPane scrollCanvas = loader.load();
        canvasController = loader.getController();
        //aggiunto per link fra controller
        canvasController.setMainController(this);
        toolBarController.setCanvasController(canvasController);


        // Carica Menu a Tendina

        loader = new FXMLLoader(getClass().getResource("/projectworkgroup6/Interfacce/DropDownMenu.fxml"));
        AnchorPane dropdownmenu = loader.load();
        dropDownMenuController = loader.getController();
        //aggiunto per link fra controller
        dropDownMenuController.setMainController(this);
        dropDownMenuController.setCanvasController(canvasController);
        toolBarController.setDropDownController(dropDownMenuController);


        dropDownMenuController.setToolBarController(toolBarController);


        menuBarController.setCanvasController(canvasController);

        /*focus al canvas*/
        StateController.getInstance().setCanvasController(canvasController);

        // Aggiungi tutto alla vista principale

        //Aggiungo il menu a tendina prima al canvas, se l'aggiungo direttamente a VBox il menu
        //comparirebbe sotto il canvas
        AnchorPane canvasPane=(AnchorPane)scrollCanvas.getContent();
        canvasPane.getChildren().add(dropdownmenu);

        // Stesso per GroupMenu
        //canvasPane.getChildren().add(groupMenuPane);

        //Carico scrollPane
        //mainVBox.getChildren().addAll(menu, tool, scrollCanvas);

        //VBox.setVgrow(scrollCanvas, Priority.ALWAYS);
        mainVBox.setLeft(tool);
        tool.getChildren().add(0, menu);
        mainVBox.setCenter(scrollCanvas);

        canvasController.bindCanvasSize(scrollCanvas);

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





    // --- Set controller figli --- //
    public void setToolBarController(ToolBarController toolBarController) {
        this.toolBarController = toolBarController;
    }

    public void setCanvasController(CanvasController canvasController) {
        this.canvasController = canvasController;
    }

    public void setMenuBarController(MenuBarController menuBarController){
        this.menuBarController = menuBarController;
    }

    public void setDropDownMenuController(DropDownController dropDownMenuController) {
        this.dropDownMenuController = dropDownMenuController;
    }

    public void passScene(Scene scene) {
        canvasController.setScene();
    }

}