package projectworkgroup6.Model;

import javafx.scene.paint.Color;

public class ColorModel {
    private int r, g, b, a;

    public ColorModel(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public String toHex() {
        return String.format("0x%02x%02x%02x%02x", a, r, g, b);
    }

    public static ColorModel fromHex(String hex) {
        long argb = Long.decode(hex);
        int a = (int) ((argb >> 24) & 0xff);
        int r = (int) ((argb >> 16) & 0xff);
        int g = (int) ((argb >> 8) & 0xff);
        int b = (int) (argb & 0xff);
        return new ColorModel(r, g, b, a);
    }

    public Color toFXColor() {
        return new Color(
                r / 255.0,
                g / 255.0,
                b / 255.0,
                a / 255.0
        );
    }


}
