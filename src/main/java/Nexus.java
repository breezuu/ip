class Nexus {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

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

    public static void main(String[] args) {
        new Nexus("data/databank.txt").run();
    }
}
