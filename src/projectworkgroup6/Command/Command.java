package projectworkgroup6.Command;

public interface Command {
    void execute();
    void undo(); // opzionale se vuoi supportare Undo
}
