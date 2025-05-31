package projectworkgroup6.Command;

import java.util.Stack;

public class CommandManager {
    private Stack<Command> history = new Stack<>();

    private static CommandManager instance;

    private CommandManager() {}

    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    public void executeCommand(Command cmd) {
        cmd.execute();
        history.push(cmd);
        System.out.println("push ");
    }

    public void undoLastCommand() {
        if (!history.isEmpty()) {
            Command cmd = history.pop();
            cmd.undo();
            System.out.println("pop ");
        }
    }

    //per test
    public void reset() {
        history.clear();
    }

    // Solo per i test
    public static void setInstance(CommandManager testInstance) {
        instance = testInstance;
    }

}

