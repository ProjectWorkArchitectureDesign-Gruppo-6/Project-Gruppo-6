package FactoryTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Factory.TextBoxCreator;
import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.Model.TextBox;
import projectworkgroup6.View.ShapeView;
import projectworkgroup6.View.TextBoxView;

import static org.junit.jupiter.api.Assertions.*;

class TextBoxCreatorTest {

    private TextBoxCreator creator;

    @BeforeEach
    void setUp() {
        creator = TextBoxCreator.getInstance();
        creator.reset(); // resetto prima di ogni test
    }

    @Test
    void testSingletonInstance() {
        TextBoxCreator instance1 = TextBoxCreator.getInstance();
        TextBoxCreator instance2 = TextBoxCreator.getInstance();
        assertSame(instance1, instance2, "Deve restituire la stessa istanza (singleton)");
    }

    @Test
    void testCreateShape_UsesConfiguredTextAndFont() {
        creator.setText("Ciao mondo");
        creator.setFontFamily("Times New Roman");
        creator.setFontSize(20);
        ColorModel fontColor = new ColorModel(128, 0, 128, 1.0);
        creator.setFontColor(fontColor);

        ColorModel border = new ColorModel(0, 0, 0, 1.0);
        ColorModel fill = new ColorModel(255, 255, 255, 1.0);

        Shape shape = creator.createShape(50, 100, border, fill);
        assertTrue(shape instanceof TextBox);

        TextBox tb = (TextBox) shape;
        assertEquals("Ciao mondo", tb.getText());
        assertEquals("Times New Roman", tb.getFontFamily());
        assertEquals(20, tb.getFontSize());
        assertEquals(fontColor, tb.getFontColor());

        // Coordinate e dimensioni di default
        assertEquals(50, tb.getXc());
        assertEquals(100, tb.getYc());
        assertEquals(100, tb.getDim1());
        assertEquals(50, tb.getDim2());
    }

    @Test
    void testResetRestoresDefaults() {
        creator.setText("Testo");
        creator.setFontFamily("Verdana");
        creator.setFontSize(30);
        creator.setFontColor(new ColorModel(123, 123, 123, 0.5));

        creator.reset();

        Shape shape = creator.createShape(0, 0,
                new ColorModel(0, 0, 0, 1),
                new ColorModel(255, 255, 255, 1));

        TextBox tb = (TextBox) shape;
        assertEquals("", tb.getText());
        assertEquals("Arial", tb.getFontFamily());
        assertEquals(14, tb.getFontSize());
        assertEquals(new ColorModel(0, 0, 0, 1.0), tb.getFontColor());
    }

    @Test
    void testCreateShapeView_ReturnsTextBoxView() {
        Shape shape = creator.createShape(0, 0,
                new ColorModel(0, 0, 0, 1),
                new ColorModel(255, 255, 255, 1));
        ShapeView view = creator.createShapeView(shape);

        assertTrue(view instanceof TextBoxView);
        assertSame(shape, view.getShape(), "La view deve contenere la stessa shape");
    }

    @Test
    void testUnimplementedMethods_DoNothingOrReturnNull() {
        assertNull(creator.getTempVertices());
        // Non solleva eccezioni
        creator.resetVertices();
        creator.addVertex(1, 1);
    }
}
