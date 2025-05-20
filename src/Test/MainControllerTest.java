package Test;

import javafx.scene.Scene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Controller.CanvasController;
import projectworkgroup6.Controller.MainController;
import projectworkgroup6.Controller.MenuBarController;
import projectworkgroup6.Controller.ToolBarController;

import static org.mockito.Mockito.*;

public class MainControllerTest {

    private MainController mainController;

    @BeforeEach
    void setUp() {
        mainController = new MainController();
    }

    @Test
    void testSetToolBarControllerStoresReference() {
        ToolBarController toolBarController = mock(ToolBarController.class);
        mainController.setToolBarController(toolBarController);
        // Non ha effetto visibile, ma assicura che il metodo non lanci eccezioni
    }

    @Test
    void testSetCanvasControllerStoresReference() {
        CanvasController canvasController = mock(CanvasController.class);
        mainController.setCanvasController(canvasController);
    }

    @Test
    void testSetMenuBarControllerStoresReference() {
        MenuBarController menuBarController = mock(MenuBarController.class);
        mainController.setMenuBarController(menuBarController);
    }

    @Test
    void testPassSceneCallsSetSceneOnCanvasController() {
        CanvasController mockCanvasController = mock(CanvasController.class);
        mainController.setCanvasController(mockCanvasController);

        Scene dummyScene = mock(Scene.class);
        mainController.passScene(dummyScene);

        verify(mockCanvasController).setScene();
    }
}
