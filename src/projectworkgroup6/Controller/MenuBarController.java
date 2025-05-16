package projectworkgroup6.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

public class MenuBarController {

    @FXML
    private MenuBar topMenuBar;
    @FXML
    private Menu fileBtn;
    @FXML
    private Menu viewBtn;
    @FXML
    private Menu saveBtn;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    // Eventuali metodi di gestione per i menu
}