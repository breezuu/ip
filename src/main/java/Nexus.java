import java.util.Scanner;
import java.util.ArrayList;

public class Nexus {
    private static final int WIDTH = 60;
    private static final String BORDER = "  " + "-".repeat(WIDTH);
    private static final ArrayList<Task> dataBank = new ArrayList<>();
    private static boolean testMode = false;

    public static void main(String[] args) {
        for (String arg : args) {
            if (arg.equalsIgnoreCase("--test")) {
                testMode = true;
                break;
            }
        }

        String logo = "  _   _\n"
                + " | \\ | | _____  ___   _ ___\n"
                + " |  \\| |/ _ \\ \\/ / | | / __|\n"
                + " | |\\  |  __/>  <| |_| \\__ \\\n"
                + " |_| \\_|\\___/_/\\_\\\\__,_|___/\n";

        printLine();
        System.out.println(logo);
        simulateBoot();
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
                printFarewell();
                break;
            }

            try {
                if (userInput.equalsIgnoreCase("list")) {
                    printList();
                } else if (userInput.startsWith("mark") || userInput.startsWith("unmark")) {
                    String[] split = userInput.split(" ");
                    if (split.length < 2) {
                        printLine();
                        System.out.println("    // ERROR: INVALID COMMAND");
                        System.out.println("    [NEXUS]: Did you forget to specify the task number?");
                        throw new NexusException("EXAMPLE: mark 1, unmark 6");
                    } else {
                        String command = split[0];
                        int index = Integer.parseInt(split[1]);
                        if (index < 1 || index > dataBank.size()) {
                            printLine();
                            System.out.println("    // ERROR: INVALID MEMORY ADDRESS");
                            System.out.println("    [NEXUS]: Invalid task number. Please try again.");
                            printLine();
                        } else {
                            if (command.equalsIgnoreCase("mark")) {
                                dataBank.get(index - 1).mark();
                            } else {
                                dataBank.get(index - 1).unmark();
                            }

                            printLine();
                            System.out.println("    [NEXUS]: Databank updated successfully.");
                            System.out.printf("    TASK@ADDR_%d. %s", index, dataBank.get(index - 1).toString());
                            System.out.println();
                            printLine();

                        }
                    }

                } else if (userInput.startsWith("todo")) {
                    if (userInput.length() < 5) {
                        printLine();
                        System.out.println("    // ERROR: INVALID TO-DO TASK");
                        System.out.println("    [NEXUS]: Did you forget to specify the 'To-Do' description?");
                        throw new NexusException("EXAMPLE: todo tidy up room");

                    } else {
                        String description = userInput.substring(5);
                        Task todoTask = new Todo(description);
                        dataBank.add(todoTask);
                        printTask(todoTask);
                    }

                } else if (userInput.startsWith("deadline")) {
                    if (userInput.length() < 9) {
                        printLine();
                        System.out.println("    // ERROR: INVALID DEADLINE TASK");
                        System.out.println("    [NEXUS]: Did you forget to specify the 'Deadline' description?");
                        throw new NexusException("EXAMPLE: deadline <taskDescription> /by <time>");

                    } else {
                        String[] split = userInput.substring(9).split(" /by ");
                        if (split.length < 2) {
                            printLine();
                            System.out.println("    // ERROR: INVALID DEADLINE TASK");
                            System.out.println("    [NEXUS]: Did you miss out on the '/by' specifier?");
                            throw new NexusException("EXAMPLE: deadline <taskDescription> /by <time>");

                        } else {
                            String taskInfo = split[0];
                            String deadline = split[1];
                            Task deadlineTask = new Deadline(taskInfo, deadline);
                            dataBank.add(deadlineTask);
                            printTask(deadlineTask);
                        }
                    }

                } else if (userInput.startsWith("event")) {
                    if (userInput.length() < 6) {
                        printLine();
                        System.out.println("    // ERROR: INVALID EVENT TASK");
                        System.out.println("    [NEXUS]: Events require BOTH '/from' and '/to' timings.");
                        throw new NexusException("EXAMPLE: event tuition /from Tues 4pm /to 6pm");

                    } else {
                        String taskDetails = userInput.substring(6);
                        String[] firstSplit = taskDetails.split(" /from ");
                        if (firstSplit.length < 2) {
                            printLine();
                            System.out.println("    [NEXUS]: Events require BOTH '/from' and '/to' timings.");
                            throw new NexusException("EXAMPLE: event tuition /from Tues 4pm /to 6pm");
                        }

                        String eventDesc = firstSplit[0];

                        String[] eventTimeSplit = firstSplit[1].split(" /to ");
                        if (eventTimeSplit.length < 2) {
                            printLine();
                            System.out.println("    [NEXUS]: Events require BOTH '/from' and '/to' timings.");
                            throw new NexusException("EXAMPLE: event tuition /from Tues 4pm /to 6pm");
                        }

                        String startTime = eventTimeSplit[0];
                        String endTime = eventTimeSplit[1];

                        Event eventTask = new Event(eventDesc, startTime, endTime);
                        dataBank.add(eventTask);
                        printTask(eventTask);
                    }

                } else {
                    printLine();
                    throw new NexusException("INVALID COMMAND. PLEASE TRY AGAIN.");
                }
            } catch (NexusException e) {
                System.out.println("    // " + e.getMessage());
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
        int addr = dataBank.indexOf(task) + 1;

        printLine();
        System.out.println("    // TASK STORED @ ADDR_" + addr + ":\n" + "    >>>> " + task.toString());
        System.out.println();
        System.out.println("    [NEXUS]: Your " + task.getType() + " task" + " has been added into the databank.");
        String numTasks = dataBank.size() > 1 ? " TASKS" : " TASK";
        System.out.println("    // CURRENT_TOTAL: " + dataBank.size() + numTasks);
        printLine();
    }

    public static void printList() {
        printLine();
        System.out.println("    [NEXUS]: Accessing databank...");
        if (!dataBank.isEmpty()) {
            String numTasks = dataBank.size() > 1 ? " TASKS" : " TASK";
            System.out.println("    // CURRENT_TOTAL: " + dataBank.size() + numTasks +"\n");

            for (int i = 0; i < dataBank.size(); i++) {
                System.out.printf("    " + "TASK@ADDR_%d. %s\n", i + 1, dataBank.get(i).toString());
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
                    if (!testMode) Thread.sleep(100);
                    System.out.print(".");
                }

                System.out.println(" [OK]");
                if (!testMode) Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println();
    }
}