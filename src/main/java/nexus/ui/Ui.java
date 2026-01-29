package nexus.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import nexus.tasks.Task;
import nexus.tasks.TaskList;

/**
 * User interface for the Nexus chatbot.
 */
public class Ui {
    private static final int WIDTH = 60;
    private static final String BORDER = "  " + "-".repeat(WIDTH);
    private final Scanner sc;

    /**
     * Constructor for the Ui class.
     */
    public Ui() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Reads a command from the user input.
     * @return The user's command as a trimmed string.
     */
    public String readCommand() {
        return this.sc.nextLine().trim();
    }

    /**
     * Prints a greeting message to the user.
     */
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

    /**
     * Prints a farewell message to the user.
     */
    public void printFarewell() {
        System.out.println("    [NEXUS]: Request acknowledged. Nexus is now going offline.");
    }

    /**
     * Prints the list of tasks to the user.
     * @param tasks The list of tasks to be displayed.
     */
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

    /**
     * Prints a confirmation message after adding a task to the list.
     * @param task The task that was added.
     * @param tasks The list of tasks to which the task was added.
     */
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

    /**
     * Prints a confirmation message after deleting a task from the list.
     * @param removedTask The task that was deleted.
     * @param tasks The list of tasks from which the task was deleted.
     */
    public void printTaskDeleted(Task removedTask, TaskList tasks) {
        System.out.println("    [NEXUS]: Databank entry purged.");
        System.out.println("    >>>> " + removedTask.toString());
        String numTasks = tasks.getSize() == 1 ? " TASK" : " TASKS";
        System.out.println("    // CURRENT_TOTAL: " + tasks.getSize() + numTasks);
    }

    /**
     * Prints a confirmation message after updating a task in the list.
     * @param tasks The list of tasks to which the task was updated.
     * @param index The index of the task that was updated.
     */
    public void printTaskUpdated(ArrayList<Task> tasks, int index) {
        System.out.println("    [NEXUS]: Databank updated successfully.");
        System.out.printf("    TASK@ADDR_%d. %s", index, tasks.get(index - 1).toString());
        System.out.println();
    }

    /**
     * Prints a line separator for formatting output.
     */
    public void printLine() {
        System.out.println(BORDER);
    }

    /**
     * Prints an error message to the user.
     * @param errorMsg The error message to be displayed.
     */
    public void printError(String errorMsg) {
        System.out.println("    " + errorMsg);
    }

    /**
     * Simulates the boot process of Nexus, displaying initialization and loading steps.
     */
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
