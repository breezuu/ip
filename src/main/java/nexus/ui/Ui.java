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
    private static final String FIRST_GREETING_MESSAGE = "[NEXUS]: Greetings. I am Nexus, your personal chatbot.\n";
    private static final String SECOND_GREETING_MESSAGE = "[NEXUS]: How can I assist you?";
    private static final String FAREWELL_MESSAGE = "[NEXUS]: Request acknowledged. Nexus is now going offline.";
    private static final String NEXUS_ACCESSING_DATABANK = "[NEXUS]: Accessing databank...\n";
    private static final String NEXUS_DATABANK_ENTRY_PURGED = "[NEXUS]: Databank entry purged.\n";
    private static final String NEXUS_DATABANK_UPDATED_SUCCESSFULLY = "[NEXUS]: Databank updated successfully.\n";
    private static final String NEXUS_ARROW_INDENT = ">>>> %s\n";
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
        sb.append(FIRST_GREETING_MESSAGE);
        sb.append(SECOND_GREETING_MESSAGE);
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
        return FAREWELL_MESSAGE;
    }

    /**
     * Prints the list of tasks to the user.
     * @param taskList The list of tasks to be displayed.
     */
    public void printTaskList(List<Task> taskList) {
        System.out.println(NEXUS_ACCESSING_DATABANK);

        if (taskList.isEmpty()) {
            System.out.println("    // NO TASKS FOUND");
            return;
        }

        String numTasks = taskList.size() > 1 ? " TASKS" : " TASK";
        System.out.println("    // CURRENT_TOTAL: " + taskList.size() + numTasks + "\n");

        for (int i = 0; i < taskList.size(); i++) {
            System.out.printf("    " + "TASK@ADDR_%d. %s\n", i + 1, taskList.get(i).toString());
        }
    }

    /**
     * Prints the list of tasks to the user.
     * @param taskList The list of tasks to be displayed.
     * @return A formatted string representation of the task list.
     */
    public String printTaskListGui(List<Task> taskList) {
        StringBuilder sb = new StringBuilder(NEXUS_ACCESSING_DATABANK);

        if (taskList.isEmpty()) {
            sb.append("// NO TASKS FOUND");
            return sb.toString();
        }

        String numTasks = taskList.size() > 1 ? " TASKS" : " TASK";
        sb.append("// CURRENT_TOTAL: ").append(taskList.size()).append(numTasks).append("\n");

        for (int i = 0; i < taskList.size(); i++) {
            sb.append("TASK@ADDR_%d. %s\n".formatted(i + 1, taskList.get(i).toString()));
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
        StringBuilder sb = new StringBuilder();
        int addr = taskList.getTasks().indexOf(task) + 1;

        sb.append("// TASK STORED @ ADDR_%d\n>>>> %s\n".formatted(addr, task.toString()));
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
        String numTasks = taskList.getSize() > 1 ? " TASKS" : " TASK";
        sb.append("// CURRENT_TOTAL: %d%s\n".formatted(taskList.getSize(), numTasks));
        sb.append("[NEXUS]: Your %s task has been added into the databank.".formatted(taskType));

        return sb.toString();
    }

    /**
     * Prints a confirmation message after deleting a task from the list.
     * @param removedTask The task that was deleted.
     * @param taskList The list of tasks from which the task was deleted.
     * @return A formatted string representation of the task deletion confirmation.
     */
    public String printDeletedTask(Task removedTask, TaskList taskList) {
        StringBuilder sb = new StringBuilder(NEXUS_DATABANK_ENTRY_PURGED);
        // System.out.println("    [NEXUS]: Databank entry purged.");
        sb.append(NEXUS_ARROW_INDENT.formatted(removedTask.toString()));
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
        StringBuilder sb = new StringBuilder(NEXUS_DATABANK_UPDATED_SUCCESSFULLY);
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
