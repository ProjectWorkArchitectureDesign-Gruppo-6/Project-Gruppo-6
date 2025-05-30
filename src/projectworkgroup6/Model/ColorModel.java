package projectworkgroup6.Model;

import javafx.scene.paint.Color;

import java.util.Locale;

public class ColorModel {
    private int red;
    private int green;
    private int blue;
    private double alpha;

    public ColorModel() {
        this(0, 0, 0, 1.0); // default: nero opaco
    }

    public ColorModel(int red, int green, int blue, double alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public static ColorModel fromColor(Color fxColor) {
        int red = (int) Math.round(fxColor.getRed() * 255);
        int green = (int) Math.round(fxColor.getGreen() * 255);
        int blue = (int) Math.round(fxColor.getBlue() * 255);
        double alpha = fxColor.getOpacity();
        return new ColorModel(red, green, blue, alpha);
    }

    public Color toColor() {
        return new Color(red / 255.0, green / 255.0, blue / 255.0, alpha);
    }

    public String toRgbaString() {
        return String.format(Locale.US, "rgba(%d,%d,%d,%.2f)", red, green, blue, alpha);
    }

    public static ColorModel fromRgbaString(String rgba) {
        if (!rgba.startsWith("rgba(") || !rgba.endsWith(")")) {
            throw new IllegalArgumentException("Formato colore non valido: " + rgba);
        }

        String[] parts = rgba.substring(5, rgba.length() - 1).split(",");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Formato RGBA errato: " + rgba);
        }

        int r = Integer.parseInt(parts[0].trim());
        int g = Integer.parseInt(parts[1].trim());
        int b = Integer.parseInt(parts[2].trim());
        double a = Double.parseDouble(parts[3].trim());

        return new ColorModel(r, g, b, a);
    }

    // Getter e Setter

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public String toString() {
        return toRgbaString();
    }

}


