package ModelTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.TextBox;

class TextBoxTest {

    private TextBox textBox;
    private ColorModel border;
    private ColorModel fill;
    private ColorModel fontColor;

    @BeforeEach
    void setUp() {
        border = new ColorModel(10, 20, 30, 1.0);
        fill = new ColorModel(100, 110, 120, 0.5);
        fontColor = new ColorModel(200, 210, 220, 0.75);

        textBox = new TextBox(5, 10, false, 200, 100, border, fill,
                "Hello World", "Times New Roman", 16, fontColor);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(5, textBox.getX());
        assertEquals(10, textBox.getY());
        assertFalse(textBox.isSelected());
        assertEquals(200, textBox.getWidth());
        assertEquals(100, textBox.getHeight());
        assertEquals(border, textBox.getBorder());
        assertEquals(fill, textBox.getFill());

        assertEquals("Hello World", textBox.getText());
        assertEquals("Times New Roman", textBox.getFontFamily());
        assertEquals(16, textBox.getFontSize());
        assertEquals(fontColor, textBox.getFontColor());

        assertTrue(textBox.isEditing());
    }

    @Test
    void testSetters() {
        textBox.setText("New Text");
        assertEquals("New Text", textBox.getText());

        textBox.setFontFamily("Arial");
        assertEquals("Arial", textBox.getFontFamily());

        textBox.setFontSize(20);
        assertEquals(20, textBox.getFontSize());

        ColorModel newFontColor = new ColorModel(50, 60, 70, 1.0);
        textBox.setFontColor(newFontColor);
        assertEquals(newFontColor, textBox.getFontColor());

        textBox.setEditing(false);
        assertFalse(textBox.isEditing());

        textBox.setSelected(true);
        assertTrue(textBox.isSelected());

        textBox.setWidth(300);
        assertEquals(300, textBox.getWidth());

        textBox.setHeight(150);
        assertEquals(150, textBox.getHeight());
    }
}
