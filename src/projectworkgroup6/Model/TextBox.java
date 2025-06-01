package projectworkgroup6.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TextBox extends Rectangle {
    private String text;
    private String fontFamily;
    private int fontSize;
    private ColorModel fontColor;

    private boolean isEditing=true;

    //per serializzazione
    public TextBox() {
        super();
        this.text = "";
        this.fontFamily = "Arial";
        this.fontSize = 14;
        this.fontColor = new ColorModel(0, 0, 0, 1.0);
        this.isEditing = true;
    }

    public TextBox(double x, double y, boolean selected, double width, double height,
                   ColorModel border, ColorModel fill, int layer, int group,
                   String text, String fontFamily, int fontSize, ColorModel fontColor) {
        super(x, y, selected, width, height, border, fill, layer, group);
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

    @Override
    public String type() {
        return "TextBox";
    }

    @Override
    public Shape cloneAt(double x, double y, int layer) {
        TextBox tb=new TextBox(x, y, true, this.getWidth(), this.getHeight(), this.border, this.fill, layer, this.group ,this.getText(), this.getFontFamily(), this.getFontSize(), this.getFontColor());
        tb.setEditing(false);
        return tb;
    }
}

