package projectworkgroup6.Command;

import projectworkgroup6.Model.TextBox;

public class ChangeFontSizeCommand implements Command{

    TextBox tb;
    int oldFontSize;
    int fontSize;

    public ChangeFontSizeCommand(TextBox tb, int fontSize) {
        this.tb = tb;
        this.oldFontSize = this.tb.getFontSize();
        this.fontSize = fontSize;
    }

    @Override
    public void execute() {
        tb.setFontSize(fontSize);
    }

    @Override
    public void undo() {
        tb.setFontSize(oldFontSize);
    }
}
