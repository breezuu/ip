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
     * @return A formatted string representation of the greeting message.
     */
    public String printGreeting() {
        StringBuilder sb = new StringBuilder();

        String logo = """
              _   _
             | \\ | | _____  ___   _ ___
             |  \\| |/ _ \\ \\/ / | | / __|
             | |\\  |  __/>  <| |_| \\__ \\
             |_| \\_|\\___/_/\\_\\\\__,_|___/
            """;

        sb.append(logo);

        // printLine();
        System.out.println(logo);
        simulateBoot();
        // printLine();
        sb.append("\n");
        sb.append("[NEXUS]: Greetings. I am Nexus, your personal chatbot.\n");
        sb.append("[NEXUS]: How can I assist you?");
        // System.out.println("    [NEXUS]: Greetings. I am Nexus, your personal chatbot.");
        // System.out.println("    [NEXUS]: How can I assist you?");
        // printLine();
        return sb.toString();
    }

    /**
     * Prints a farewell message to the user.
     */
    public String printFarewell() {
        // System.out.println("    [NEXUS]: Request acknowledged. Nexus is now going offline.");
        return "[NEXUS]: Request acknowledged. Nexus is now going offline.";
    }

    /**
     * Prints the list of tasks to the user.
     * @param taskList The list of tasks to be displayed.
     */
    public void printTaskList(List<Task> taskList) {
        System.out.println("    [NEXUS]: Accessing databank...");
        if (!taskList.isEmpty()) {
            String numTasks = taskList.size() > 1 ? " TASKS" : " TASK";
            System.out.println("    // CURRENT_TOTAL: " + taskList.size() + numTasks + "\n");

            for (int i = 0; i < taskList.size(); i++) {
                System.out.printf("    " + "TASK@ADDR_%d. %s\n", i + 1, taskList.get(i).toString());
            }
        } else {
            System.out.println("    // NO TASKS FOUND");
        }
    }

    /**
     * Prints the list of tasks to the user.
     * @param taskList The list of tasks to be displayed.
     * @return A formatted string representation of the task list.
     */
    public String printTaskListGui(List<Task> taskList) {
        StringBuilder sb = new StringBuilder("[NEXUS]: Accessing databank...\n");
        if (!taskList.isEmpty()) {
            String numTasks = taskList.size() > 1 ? " TASKS" : " TASK";
            sb.append("// CURRENT_TOTAL: ").append(taskList.size()).append(numTasks).append("\n");

            for (int i = 0; i < taskList.size(); i++) {
                sb.append("TASK@ADDR_%d. %s\n".formatted(i + 1, taskList.get(i).toString()));
            }
        } else {
            sb.append("// NO TASKS FOUND");
        }

        return sb.toString();
    }

    /**
     * Prints a confirmation message after adding a task to the list.
     * @param task The task that was added.
     * @param taskList The list of tasks to which the task was added.
     * @return A formatted string representation of the task addition confirmation.
     */
    public String printAddedTask(Task task, TaskList taskList) {
        int addr = taskList.getTasks().indexOf(task) + 1;

        System.out.println("// TASK STORED @ ADDR_" + addr + ":\n" + "    >>>> " + task.toString());
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

        // System.out.println("    [NEXUS]: Your " + taskType + " task " + "has been added into the databank.");
        String numTasks = taskList.getSize() > 1 ? " TASKS" : " TASK";
        System.out.println("// CURRENT_TOTAL: " + taskList.getSize() + numTasks);
        return "[NEXUS]: Your " + taskType + " task" + " has been added into the databank.";
    }

    /**
     * Prints a confirmation message after deleting a task from the list.
     * @param removedTask The task that was deleted.
     * @param taskList The list of tasks from which the task was deleted.
     * @return A formatted string representation of the task deletion confirmation.
     */
    public String printDeletedTask(Task removedTask, TaskList taskList) {
        StringBuilder sb = new StringBuilder("[NEXUS]: Databank entry purged.\n");
        // System.out.println("    [NEXUS]: Databank entry purged.");
        sb.append(">>>> %s".formatted(removedTask.toString()));
        // System.out.println("    >>>> " + removedTask.toString());
        String numTasks = taskList.getSize() == 1 ? " TASK" : " TASKS";
        // System.out.println("    // CURRENT_TOTAL: " + tasks.getSize() + numTasks);
        sb.append("// CURRENT_TOTAL: ").append(taskList.getSize()).append(numTasks).append("\n");
        return sb.toString();
    }

    /**
     * Prints a confirmation message after updating a task in the list.
     * @param tasks The list of tasks to which the task was updated.
     * @param index The index of the task that was updated.
     * @return A formatted string representation of the task update confirmation.
     */
    public String printUpdatedTask(ArrayList<Task> tasks, int index) {
        StringBuilder sb = new StringBuilder("[NEXUS]: Databank updated successfully.\n");
        // System.out.println("    [NEXUS]: Databank updated successfully.");
        sb.append("TASK@ADDR_%d. %s".formatted(index, tasks.get(index - 1).toString()));
        // System.out.printf("    TASK@ADDR_%d. %s", index, tasks.get(index - 1).toString());
        return sb.toString();
    }

    /**
     * Prints a line separator for formatting output.
     * @return A string representation of a line separator.
     */
    public String printLine() {
        return BORDER;
    }

    /**
     * Prints an error message to the user.
     * @param errorMsg The error message to be displayed.
     * @return A formatted string representation of the error message.
     */
    public String printError(String errorMsg) {
        StringBuilder sb = new StringBuilder();
        sb.append(errorMsg);
        // System.out.println("    " + errorMsg);
        return sb.toString();
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
