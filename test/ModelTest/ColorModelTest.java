import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.ColorModel;

import static org.junit.jupiter.api.Assertions.*;

public class ColorModelTest {

    @Test
    public void testDefaultConstructor() {
        ColorModel cm = new ColorModel();
        assertEquals(0, cm.getRed());
        assertEquals(0, cm.getGreen());
        assertEquals(0, cm.getBlue());
        assertEquals(1.0, cm.getAlpha(), 0.001);
    }

    @Test
    public void testFromColor() {
        Color fxColor = new Color(0.5, 0.25, 0.75, 0.8); // RGB(128,64,192)
        ColorModel cm = ColorModel.fromColor(fxColor);
        assertEquals(128, cm.getRed());
        assertEquals(64, cm.getGreen());
        assertEquals(192, cm.getBlue());
        assertEquals(0.8, cm.getAlpha(), 0.001);
    }

    @Test
    public void testToColor() {
        ColorModel cm = new ColorModel(128, 64, 192, 0.8);
        Color fxColor = cm.toColor();
        assertEquals(0.5, fxColor.getRed(), 0.01);
        assertEquals(0.25, fxColor.getGreen(), 0.01);
        assertEquals(0.75, fxColor.getBlue(), 0.01);
        assertEquals(0.8, fxColor.getOpacity(), 0.01);
    }

    @Test
    public void testRgbaSerialization() {
        ColorModel cm = new ColorModel(10, 20, 30, 0.5);
        String rgba = cm.toRgbaString();
        assertEquals("rgba(10,20,30,0.50)", rgba);
        ColorModel parsed = ColorModel.fromRgbaString(rgba);
        assertEquals(cm.getRed(), parsed.getRed());
        assertEquals(cm.getGreen(), parsed.getGreen());
        assertEquals(cm.getBlue(), parsed.getBlue());
        assertEquals(cm.getAlpha(), parsed.getAlpha(), 0.001);
    }

    @Test
    public void testInvalidRgbaStringThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            ColorModel.fromRgbaString("rgb(255,255,255,1.0)");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            ColorModel.fromRgbaString("rgba(255,255)");
        });
    }
}
