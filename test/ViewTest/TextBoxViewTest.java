package ViewTest;

import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.TextBox;
import projectworkgroup6.View.TextBoxView;

import static org.junit.jupiter.api.Assertions.*;

public class TextBoxViewTest {

    @Test
    public void testTextBoxViewInitialization() {
        TextBox textBox = new TextBox(0, 0, false, 100, 50,
                new ColorModel(), new ColorModel(), 1, 1,
                "Test", "Arial", 12, new ColorModel());
        TextBoxView view = new TextBoxView(textBox);
        assertNotNull(view);
    }
}