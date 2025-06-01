package projectworkgroup6.Factory;

import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.TextBox;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.View.ShapeView;
import projectworkgroup6.View.TextBoxView;

import java.util.List;

public class TextBoxCreator implements ShapeCreator {

    private static TextBoxCreator instance;

    private String text = "";
    private String fontFamily = "Arial";
    private int fontSize = 14;
    private ColorModel fontColor = new ColorModel(0, 0, 0, 1.0); // nero

    private TextBoxCreator() {
        // costruttore privato per Singleton
    }

    public static TextBoxCreator getInstance() {
        if (instance == null) {
            instance = new TextBoxCreator();
        }
        return instance;
    }

    // Setter per configurare il testo e font prima di creare la shape
    public void setText(String text) {
        this.text = text;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setFontColor(ColorModel fontColor) {
        this.fontColor = fontColor;
    }

    // Reset ai valori di default
    public void reset() {
        this.text = "";
        this.fontFamily = "Arial";
        this.fontSize = 14;
        this.fontColor = new ColorModel(0, 0, 0, 1.0);
    }

    @Override
    public Shape createShape(double x, double y, double width, double height, ColorModel border, ColorModel fill, int layer, int group) {
        // Creo TextBox con dimensioni di default, posizione data e parametri font configurati
        return new TextBox(x, y, false, width, height, border, fill, layer, group,
                text, fontFamily, fontSize, fontColor);
    }

    @Override
    public ShapeView createShapeView(Shape shape) {
        return new TextBoxView((TextBox) shape);
    }

}
