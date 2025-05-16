/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectworkgroup6.Model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

/**
 *
 * @author Dario
 */

public class ColorModel {
    private final ObjectProperty<Color> selectedColor = new SimpleObjectProperty<>(Color.BLACK);
    private String color;

    public Color getSelectedColor() {
        return selectedColor.get();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        // Aggiorna anche selectedColor per coerenza
        this.selectedColor.set(Color.web(color));
    }

    public void setSelectedColor(Color color) {
        this.selectedColor.set(color);
        // Aggiorna anche la stringa per coerenza
        this.color = toHexString(color);
    }

    public void setSelectedColorFromHex(String hex) {
        long argb = Long.decode(hex);
        setSelectedColorFromLong(argb);
    }

    public void setSelectedColorFromLong(long argb) {
        int a = (int) ((argb >> 24) & 0xff);
        int r = (int) ((argb >> 16) & 0xff);
        int g = (int) ((argb >> 8) & 0xff);
        int b = (int) (argb & 0xff);

        Color fxColor = Color.rgb(r, g, b, a / 255.0);
        this.setSelectedColor(fxColor); // usa il setter
    }

    public ObjectProperty<Color> selectedColorProperty() {
        return selectedColor;
    }

    private String toHexString(Color color) {
        int a = (int) (color.getOpacity() * 255);
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);
        return String.format("0x%02x%02x%02x%02x", a, r, g, b);
    }
}