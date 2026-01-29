package nexus;

import nexus.commands.Command;
import nexus.exception.NexusException;
import nexus.parser.Parser;
import nexus.storage.Storage;
import nexus.tasks.TaskList;
import nexus.ui.Ui;

/**
 * Main application class for Nexus.
 */
class Nexus {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Constructs a Nexus application with the specified data file path.
     * @param filePath The path to the data file.
     */
    public Nexus(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (NexusException e) {
            ui.printError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Runs the Nexus application, handling user commands and interactions.
     */
    public void run() {
        ui.printGreeting();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.printLine();

                Command cmd = Parser.parse(fullCommand);
                cmd.run(tasks, ui, storage);

                isExit = cmd.isExit();
            } catch (NexusException e) {
                ui.printError(e.getMessage());
            } finally {
                ui.printLine();
            }
        }
    }

    /**
     * Main entry point for the Nexus application.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Nexus("data/databank.txt").run();
    }
}
