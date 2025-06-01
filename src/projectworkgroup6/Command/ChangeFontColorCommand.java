package projectworkgroup6.Command;

import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.Shape;
import projectworkgroup6.Model.TextBox;

import javax.xml.soap.Text;

public class ChangeFontColorCommand implements Command{

    TextBox tb;
    ColorModel oldFontColor;
    ColorModel fontColor;

    public ChangeFontColorCommand(TextBox tb, ColorModel fontColor) {
        this.tb = tb;
        this.oldFontColor = this.tb.getFontColor();
        this.fontColor = fontColor;
    }

    @Override
    public void execute() {
        tb.setFontColor(fontColor);
    }

    @Override
    public void undo() {
        tb.setFontColor(oldFontColor);
    }
}
