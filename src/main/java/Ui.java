import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Ui {
    private static final int WIDTH = 60;
    private static final String BORDER = "  " + "-".repeat(WIDTH);
    private final Scanner sc;

    public Ui() {
        this.sc = new Scanner(System.in);
    }

    public String readCommand() {
        return this.sc.nextLine().trim();
    }

    public void printGreeting() {
        String logo = """
              _   _
             | \\ | | _____  ___   _ ___
             |  \\| |/ _ \\ \\/ / | | / __|
             | |\\  |  __/>  <| |_| \\__ \\
             |_| \\_|\\___/_/\\_\\\\__,_|___/
            """;

        printLine();
        System.out.println(logo);
        simulateBoot();
        printLine();
        System.out.println("    [NEXUS]: Greetings. I am Nexus, your personal chatbot.");
        System.out.println("    [NEXUS]: How can I assist you?");
        printLine();
    }

    public void printFarewell() {
        System.out.println("    [NEXUS]: Request acknowledged. Nexus is now going offline.");
    }

    public void printTaskList(List<Task> tasks) {
        System.out.println("    [NEXUS]: Accessing databank...");
        if (!tasks.isEmpty()) {
            String numTasks = tasks.size() > 1 ? " TASKS" : " TASK";
            System.out.println("    // CURRENT_TOTAL: " + tasks.size() + numTasks + "\n");

            for (int i = 0; i < tasks.size(); i++) {
                System.out.printf("    " + "TASK@ADDR_%d. %s\n", i + 1, tasks.get(i).toString());
            }
        } else {
            System.out.println("    // NO TASKS FOUND");
        }
    }

    public void printTaskAdded(Task task, TaskList tasks) {
        int addr = tasks.getTasks().indexOf(task) + 1;

        System.out.println("    // TASK STORED @ ADDR_" + addr + ":\n" + "    >>>> " + task.toString());
        System.out.println();
        String taskType = "";

        switch (task.getType()) {
        case "T":
            taskType = "Todo";
            break;
        case "D":
            taskType = "Deadline";
            break;
        case "E":
            taskType = "Event";
            break;
        default:
            // Do nothing
        }

        System.out.println("    [NEXUS]: Your " + taskType + " task" + " has been added into the databank.");
        String numTasks = tasks.getSize() > 1 ? " TASKS" : " TASK";
        System.out.println("    // CURRENT_TOTAL: " + tasks.getSize() + numTasks);
    }

    public void printTaskDeleted(Task removedTask, TaskList tasks) {
        System.out.println("    [NEXUS]: Databank entry purged.");
        System.out.println("    >>>> " + removedTask.toString());
        String numTasks = tasks.getSize() == 1 ? " TASK" : " TASKS";
        System.out.println("    // CURRENT_TOTAL: " + tasks.getSize() + numTasks);
    }

    public void printTaskUpdated(Task updatedTask, ArrayList<Task> tasks, int index) {
        System.out.println("    [NEXUS]: Databank updated successfully.");
        System.out.printf("    TASK@ADDR_%d. %s", index, tasks.get(index - 1).toString());
        System.out.println();
    }

    public void printLine() {
        System.out.println(BORDER);
    }

    public void printError(String errorMsg) {
        System.out.println("    " + errorMsg);
    }

    public void simulateBoot() {
        int maxWidth = 40;
        String[] bootSteps = {"// INITIALIZING NEXUS ", "// LOADING PRECEPTS "};
        for (String s : bootSteps) {
            System.out.print(s);

            int padding = maxWidth - s.length();

            for (int i = 0; i < padding; i++) {
                System.out.print(".");
            }

            System.out.println(" [OK]");
        }
    }
}
