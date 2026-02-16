package nexus.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import nexus.exception.NexusException;
import nexus.tasks.Deadline;
import nexus.tasks.Event;
import nexus.tasks.Note;
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
    private static final String CORRUPTED_DATA_PROMPT_1 = "[NEXUS]: Corrupted data has been detected. To ";
    private static final String CORRUPTED_DATA_PROMPT_2 = "avoid issues, the existing databank file has been wiped.\n";
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

        System.out.println(logo);
        simulateBoot();
        sb.append("\n");
        // sb.append(FIRST_GREETING_MESSAGE);
        // sb.append(SECOND_GREETING_MESSAGE);
        return sb.toString();
    }

    /**
     * Prints a farewell message to the user.
     */
    public String printFarewell() {
        return FAREWELL_MESSAGE;
    }

    /**
     * Prints the list of tasks to the user in a GUI format.
     * @param taskList The list of tasks to be displayed.
     * @param listType The type of tasks to be displayed (e.g. "all", "tasks", "deadlines", "events", "notes").
     */
    public String printTaskListGui(List<Task> taskList, String listType) throws NexusException {
        StringBuilder sb = new StringBuilder(NEXUS_ACCESSING_DATABANK);
        List<String> possibleListTypes = new ArrayList<>(Arrays.asList("all", "tasks", "deadlines", "events", "notes"));

        if (taskList.isEmpty()) {
            sb.append("// NO RESULTS FOUND");
            return sb.toString();
        }

        if (!possibleListTypes.contains(listType)) {
            throw new NexusException("[NEXUS]: Invalid list type. Available: tasks/deadlines/events/notes.");
        }

        List<Task> filteredList = getFilteredList(taskList, listType);

        return printFilteredListContents(filteredList, sb);
    }

    /**
     * Prints the filtered task list contents to the StringBuilder.
     *
     * @param filteredList The filtered list of tasks.
     * @param sb The StringBuilder to append the output to.
     * @return The StringBuilder with the formatted output.
     */
    private static String printFilteredListContents(List<Task> filteredList, StringBuilder sb) {
        if (filteredList.isEmpty()) {
            sb.append("// NO RESULTS FOUND");
            return sb.toString();
        }

        String label = filteredList.size() > 1 ? " RESULTS" : " RESULT";
        sb.append("// ").append("TOTAL: ").append(filteredList.size()).append(label).append("\n");

        String body = IntStream.range(0, filteredList.size())
                .mapToObj(i -> "TASK@ADDR_%d. %s".formatted(i + 1, filteredList.get(i).toString()))
                .collect(Collectors.joining("\n"));

        return sb.append(body).toString();
    }

    /**
     * Filters the task list based on the specified list type.
     *
     * @param taskList The list of tasks to filter.
     * @param listType The type of tasks to filter for.
     * @return A filtered list of tasks based on the specified type.
     */
    private static List<Task> getFilteredList(List<Task> taskList, String listType) {
        return taskList.stream()
                .filter(task -> {
                    if (listType.equalsIgnoreCase("all")) {
                        return true;
                    } else if (listType.equalsIgnoreCase("tasks")) {
                        return !(task instanceof Note);
                    } else if (listType.equalsIgnoreCase("deadlines")) {
                        return task instanceof Deadline;
                    } else if (listType.equalsIgnoreCase("events")) {
                        return task instanceof Event;
                    } else if (listType.equalsIgnoreCase("notes")) {
                        return task instanceof Note;
                    }

                    return false;
                })
                .toList();
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
        case "N":
            taskType = "Note";
            break;
        default:
            assert false : "Unknown task type";
        }
        String numTasks = taskList.getSize() > 1 ? " TASKS" : " TASK";
        sb.append("// CURRENT_TOTAL: %d%s\n".formatted(taskList.getSize(), numTasks));
        sb.append("[NEXUS]: Your %s has been added into the databank.".formatted(taskType));

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
        sb.append(NEXUS_ARROW_INDENT.formatted(removedTask.toString()));
        String numTasks = taskList.getSize() == 1 ? " TASK" : " TASKS";
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
        sb.append("TASK@ADDR_%d. %s".formatted(index, tasks.get(index - 1).toString()));
        return sb.toString();
    }

    /**
     * Prints an error message to the user.
     * @param errorMsg The error message to be displayed.
     * @return A formatted string representation of the error message.
     */
    public String printError(String errorMsg) {
        StringBuilder sb = new StringBuilder();
        sb.append(errorMsg);
        return sb.toString();
    }

    public String printCorruptedDataWarning() {
        return CORRUPTED_DATA_PROMPT_1 + CORRUPTED_DATA_PROMPT_2;
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
