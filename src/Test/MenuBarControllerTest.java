package Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Controller.MainController;
import projectworkgroup6.Controller.MenuBarController;

import static org.junit.jupiter.api.Assertions.*;

public class MenuBarControllerTest {

    private MenuBarController controller;

    @BeforeEach
    void setUp() {
        controller = new MenuBarController();
    }

    @Test
    void testSetMainControllerDoesNotThrow() {
        MainController mainController = new MainController();
        assertDoesNotThrow(() -> controller.setMainController(mainController));
    }

    @Test
    void testControllerInstantiatesCorrectly() {
        assertNotNull(controller);
    }
}
