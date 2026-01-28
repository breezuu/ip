import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Nexus {
    private static final int WIDTH = 60;
    private static final String BORDER = "  " + "-".repeat(WIDTH);
    private static final List<Task> DATABANK = new ArrayList<>();
    private static final String[] taskTypes = new String[]{"Todo", "Deadline", "Event"};
    private static boolean isTesting = false;

    public static void main(String[] args) {
        for (String arg : args) {
            if (arg.equalsIgnoreCase("--test")) {
                isTesting = true;
                break;
            }
        }

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
        loadTasks();
        printLine();
        System.out.println("    [NEXUS]: Greetings. I am Nexus, your personal chatbot.");
        System.out.println("    [NEXUS]: How can I assist you?");
        printLine();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println();
            String userInput = sc.nextLine().trim();
            System.out.println();

            if (userInput.equalsIgnoreCase("bye")) {
                saveTasks(DATABANK); // Any corruption in the 'databank.txt' file will be fixed when data is overwritten
                printFarewell();
                break;
            }

            try {
                if (userInput.equalsIgnoreCase("list")) {
                    printLine();
                    printList(DATABANK);
                } else if (userInput.startsWith("mark") || userInput.startsWith("unmark")) {
                    String[] split = userInput.split(" ");
                    printLine();
                    int index = validateIndex(split);

                    if (split[0].equalsIgnoreCase("mark")) {
                        DATABANK.get(index - 1).mark();
                    } else {
                        DATABANK.get(index - 1).unmark();
                    }

                    saveTasks(DATABANK);

                    System.out.println("    [NEXUS]: Databank updated successfully.");
                    System.out.printf("    TASK@ADDR_%d. %s", index, DATABANK.get(index - 1).toString());
                    System.out.println();
                    printLine();

                } else if (userInput.startsWith("todo")) {
                    if (userInput.length() < 5) {
                        printLine();
                        System.out.println("    // ERROR: INVALID TO-DO TASK");
                        System.out.println("    [NEXUS]: Did you forget to specify the 'To-Do' description?");
                        throw new NexusException("// EXAMPLE: todo tidy up room");

                    } else {
                        String description = userInput.substring(5);
                        Task todoTask = new Todo(description, false);
                        DATABANK.add(todoTask);
                        saveTasks(DATABANK);
                        printTask(todoTask);
                    }

                } else if (userInput.startsWith("deadline")) {
                    if (userInput.length() < 9) {
                        printLine();
                        System.out.println("    // ERROR: INVALID DEADLINE TASK");
                        System.out.println("    [NEXUS]: Did you miss out on the '/by' specifier?");
                        throw new NexusException("// EXAMPLE: 'deadline quiz /by 01/01/2002 12:00 PM'");

                    } else {
                        String[] split = userInput.substring(9).split(" /by ");
                        if (split.length < 2) {
                            printLine();
                            System.out.println("    // ERROR: INVALID DEADLINE TASK");
                            System.out.println("    [NEXUS]: Did you miss out on the '/by' specifier?");
                            throw new NexusException("// EXAMPLE: 'deadline quiz /by 01/01/2002 12:00 PM'");

                        } else {
                            try {
                                String taskInfo = split[0];
                                String deadline = split[1];
                                Task deadlineTask = new Deadline(taskInfo, deadline, false);
                                DATABANK.add(deadlineTask);
                                saveTasks(DATABANK);
                                printTask(deadlineTask);
                            } catch (DateTimeParseException e) {
                                System.out.println("    [NEXUS]: Did you follow the date and time format (12-hour)?");
                                System.out.println("    // e.g. 'deadline quiz /by 01/01/2002 10:00 AM'");
                                throw e;
                            }
                        }
                    }

                } else if (userInput.startsWith("event")) {
                    if (userInput.length() < 6) {
                        printLine();
                        System.out.println("    // ERROR: INVALID EVENT TASK");
                        System.out.println("    [NEXUS]: Events require BOTH '/from' and '/to' timings.");
                        throw new NexusException("// EXAMPLE: event tuition /from <startTime> /to <endTime>");

                    } else {
                        try {
                            String taskDetails = userInput.substring(6);
                            String[] firstSplit = taskDetails.split(" /from ");
                            if (firstSplit.length < 2) {
                                printLine();
                                System.out.println("    [NEXUS]: Events require BOTH '/from' and '/to' timings.");
                                throw new NexusException("// EXAMPLE: event tuition /from <startTime> /to <endTime>");
                            }

                            String eventDesc = firstSplit[0];

                            String[] eventTimeSplit = firstSplit[1].split(" /to ");
                            if (eventTimeSplit.length < 2) {
                                printLine();
                                System.out.println("    [NEXUS]: Events require BOTH '/from' and '/to' timings.");
                                throw new NexusException("// EXAMPLE: event tuition /from <startTime> /to <endTime>");
                            }

                            String startTime = eventTimeSplit[0];
                            String endTime = eventTimeSplit[1];

                            Event eventTask = new Event(eventDesc, startTime, endTime, false);
                            DATABANK.add(eventTask);
                            saveTasks(DATABANK);
                            printTask(eventTask);
                        } catch (DateTimeParseException e) {
                            System.out.println("    [NEXUS]: Did you follow the date and time format (12-hour)?");
                            System.out.println("    // e.g. 'event x /from 01/01/2002 1:00 PM /to 01/01/2002 2:00 PM'");
                        }
                    }
                } else if (userInput.startsWith("delete")) {
                    String[] split = userInput.split(" ");
                    printLine();
                    int index = validateIndex(split);

                    Task removedTask = DATABANK.remove(index - 1);

                    saveTasks(DATABANK);

                    System.out.println("    [NEXUS]: Databank entry purged.");
                    System.out.println("    >>>> " + removedTask.toString());
                    String numTasks = DATABANK.size() == 1 ? " TASK" : " TASKS";
                    System.out.println("    // CURRENT_TOTAL: " + DATABANK.size() + numTasks);
                    printLine();

                } else if (userInput.startsWith("check")) {
                    printLine();
                    System.out.println("    [NEXUS]: Type the number beside ADDR for the task type you wish to check:");
                    System.out.println("    [NEXUS]: NOTE: Typing an invalid number will exit the check!");
                    for (int i = 0; i < taskTypes.length; i++) {
                        System.out.println("    " + "TYPE@ADDR_" + (i + 1) + ". " + taskTypes[i]);
                    }
                    printLine();

                    System.out.println();
                    String taskToCheck = sc.nextLine().trim();
                    String date;
                    List<Task> result;
                    System.out.println();

                    printLine();
                    switch (taskToCheck) {
                    case "1": // Todo
                        System.out.println("    [NEXUS]: Checking for existing to-do tasks...");
                        result = DATABANK.stream()
                                .filter(t -> t.getType().equals("T"))
                                .toList();
                        printList(result);
                        break;
                    case "2": // Deadline
                        System.out.println("    [NEXUS]: Specify the date (in DD/MM/YYYY format) you wish to check:");
                        date = sc.nextLine().trim();
                        System.out.println("    [NEXUS]: Checking for existing deadlines with the specified date...");
                        result = DATABANK.stream()
                                .filter(t -> t.getType().equals("D"))
                                .filter(t -> t.isDuringDate(date))
                                .toList();
                        printList(result);
                        break;
                    case "3": // Event
                        System.out.println("    [NEXUS]: Specify the date (in DD/MM/YYYY format) you wish to check:");
                        date = sc.nextLine().trim();
                        System.out.println("    [NEXUS]: Checking for existing events with the specified date...");
                        result = DATABANK.stream()
                                .filter(t -> t.getType().equals("E"))
                                .filter(t -> t.isDuringDate(date))
                                .toList();
                        printList(result);
                        break;
                    default:
                        System.out.println("    [NEXUS]: You are checking for an invalid task type. Exiting check.");
                    }

                } else {
                    printLine();
                    throw new NexusException("// INVALID COMMAND. PLEASE TRY AGAIN.");
                }
            } catch (NexusException e) {
                System.out.println("    " + e.getMessage());
                printLine();
            }
        }

        sc.close();
    }

    public static void printLine() {
        System.out.println(BORDER);
    }

    public static void printFarewell() {
        printLine();
        System.out.println("    [NEXUS]: Request acknowledged. Nexus is now going offline.");
        printLine();
    }

    public static void printTask(Task task) {
        int addr = DATABANK.indexOf(task) + 1;

        printLine();
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
        String numTasks = DATABANK.size() > 1 ? " TASKS" : " TASK";
        System.out.println("    // CURRENT_TOTAL: " + DATABANK.size() + numTasks);
        printLine();
    }

    public static void printList(List<Task> tasks) {
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

        printLine();
    }

    public static void simulateBoot() {
        int maxWidth = 40;
        String[] bootSteps = {
            "// INITIALIZING NEXUS ",
            "// LOADING PRECEPTS "
        };

        try {
            for (String s : bootSteps) {
                System.out.print(s);

                int padding = maxWidth - s.length();

                for (int i = 0; i < padding; i++) {
                    if (!isTesting) {
                        Thread.sleep(100);
                    }

                    System.out.print(".");
                }

                System.out.println(" [OK]");
                if (!isTesting) {
                    Thread.sleep(200);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println();
    }

    private static int validateIndex(String[] split) throws NexusException {
        if (split.length < 2) {
            System.out.println("    [NEXUS]: Please specify the task number you wish to mark or delete.");
            throw new NexusException("// EXAMPLE: mark 6, delete 7");
        }

        try {
            int index = Integer.parseInt(split[1]);

            if (DATABANK.isEmpty()) {
                throw new NexusException("// ERROR: DATABANK EMPTY");

            } else if (index < 1 || index > DATABANK.size()) {
                System.out.println("    // ERROR: TASK NUMBER OUT OF BOUNDS");
                String numTasks = DATABANK.size() > 1 ? " TASKS" : " TASK";
                throw new NexusException("// CURRENT_TOTAL: " + DATABANK.size() + numTasks);
            }
            return index;

        } catch (NumberFormatException e) {
            throw new NexusException("// ERROR: PROVIDE VALID INDEX NUMBER");
        }
    }

    private static void loadTasks() {
        Path filePath = Paths.get("data", "databank.txt");

        try {
            checkDirectory(); // Check if the 'data' directory exists

            if (Files.notExists(filePath)) { // If the file 'databank.txt' does not exist...
                Files.createFile(filePath); // Create the file
            }

            List<String> lines = Files.readAllLines(filePath);
            for (String s : lines) {
                try {
                    Task task = parseTasks(s);
                    DATABANK.add(task);
                } catch (NexusException e) {
                    System.out.println("    " + e.getMessage());
                    printLine();
                }
            }

        } catch (IOException e) {
            System.out.println("    // ERROR: " + e.getMessage());
        }
    }

    private static Task parseTasks(String s) throws NexusException {
        String[] components = s.split("\\s*\\|\\s*"); // Splits the task based on '|'
        if (components.length < 3) {
            throw new NexusException("// ERROR: INVALID TASK FORMAT");
        }

        String taskType = components[0].trim();
        boolean isDone = components[1].trim().equals("1");
        String taskDesc = components[2].trim();

        switch (taskType) {
        case "T":
            return new Todo(taskDesc, isDone);
        case "D":
            if (components.length < 4) {
                throw new NexusException("// ERROR: CORRUPTED DEADLINE FORMAT");
            }
            return new Deadline(taskDesc, components[3], isDone);
        case "E":
            if (components.length < 5) {
                throw new NexusException("// ERROR: CORRUPTED EVENT FORMAT");
            }
            return new Event(taskDesc, components[3], components[4], isDone);
        default:
            throw new NexusException("// ERROR: INVALID TASK TYPE");
        }
    }

    private static void saveTasks(List<Task> tasks) {
        try {
            FileWriter fw = new FileWriter("./data/databank.txt");
            for (Task t : tasks) {
                fw.write(t.saveString() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("    // ERROR: " + e.getMessage());
        }
    }

    private static void checkDirectory() throws IOException {
        Path path = Paths.get("data");
        if (Files.notExists(path)) {
            Files.createDirectory(path);
        }
    }
}
