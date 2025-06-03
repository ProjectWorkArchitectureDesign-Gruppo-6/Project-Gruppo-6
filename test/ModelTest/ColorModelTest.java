package ModelTest;

import org.junit.jupiter.api.Test;
import projectworkgroup6.Model.ColorModel;
import javafx.scene.paint.Color;

import static org.junit.jupiter.api.Assertions.*;

public class ColorModelTest {

    @Test
    public void testConstructorAndGetters() {
        ColorModel color = new ColorModel(100, 150, 200, 0.5);
        assertEquals(100, color.getRed());
        assertEquals(150, color.getGreen());
        assertEquals(200, color.getBlue());
        assertEquals(0.5, color.getAlpha(), 0.01);
    }

    @Test
    public void testToColorConversion() {
        ColorModel colorModel = new ColorModel(255, 128, 0, 0.75);
        Color fxColor = colorModel.toColor();

        assertEquals(1.0, fxColor.getRed(), 0.01);
        assertEquals(0.5, fxColor.getGreen(), 0.01);
        assertEquals(0.0, fxColor.getBlue(), 0.01);
        assertEquals(0.75, fxColor.getOpacity(), 0.01);
    }

    @Test
    public void testFromColorConversion() {
        Color fxColor = new Color(0.2, 0.4, 0.6, 0.8);
        ColorModel colorModel = ColorModel.fromColor(fxColor);

        assertEquals(51, colorModel.getRed());
        assertEquals(102, colorModel.getGreen());
        assertEquals(153, colorModel.getBlue());
        assertEquals(0.8, colorModel.getAlpha(), 0.01);
    }

    @Test
    public void testRgbaStringConversion() {
        ColorModel original = new ColorModel(10, 20, 30, 0.6);
        String rgba = original.toRgbaString();

        assertEquals("rgba(10,20,30,0.60)", rgba);

        ColorModel parsed = ColorModel.fromRgbaString(rgba);
        assertEquals(original.getRed(), parsed.getRed());
        assertEquals(original.getGreen(), parsed.getGreen());
        assertEquals(original.getBlue(), parsed.getBlue());
        assertEquals(original.getAlpha(), parsed.getAlpha(), 0.01);
    }

    @Test
    public void testInvalidRgbaString() {
        assertThrows(IllegalArgumentException.class, () -> {
            ColorModel.fromRgbaString("rgb(255,255,255)");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            ColorModel.fromRgbaString("rgba(255,255)");
        });
    }
}
