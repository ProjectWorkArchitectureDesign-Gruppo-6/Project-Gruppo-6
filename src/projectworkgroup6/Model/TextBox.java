package projectworkgroup6.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TextBox extends Rectangle {
    private String text;
    private String fontFamily;
    private int fontSize;
    private ColorModel fontColor;

    private boolean isEditing=true;

    public TextBox(double x, double y, boolean selected, double width, double height,
                   ColorModel border, ColorModel fill,
                   String text, String fontFamily, int fontSize, ColorModel fontColor) {
        super(x, y, selected, width, height, border, fill);
        this.text = text;
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
        this.fontColor = fontColor;
    }

    // Getter e Setter
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public ColorModel getFontColor() {
        return fontColor;
    }

    public void setFontColor(ColorModel fontColor) {
        this.fontColor = fontColor;
    }

    public boolean isEditing() {
        return isEditing;
    }

    public void setEditing(boolean editing) {
        this.isEditing = editing;
    }
}

