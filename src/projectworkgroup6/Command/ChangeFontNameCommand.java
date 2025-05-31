package projectworkgroup6.Command;

import projectworkgroup6.Model.ColorModel;
import projectworkgroup6.Model.TextBox;

public class ChangeFontNameCommand implements Command{

    TextBox tb;
    String oldFontName;
    String fontName;

    public ChangeFontNameCommand(TextBox tb, String fontName) {
        this.tb = tb;
        this.oldFontName = this.tb.getFontFamily();
        this.fontName = fontName;
    }
    @Override
    public void execute() {
        tb.setFontFamily(fontName);
    }

    @Override
    public void undo() {
        tb.setFontFamily(oldFontName);
    }
}
