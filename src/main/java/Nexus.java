import java.util.Scanner;
import java.util.ArrayList;

public class Nexus {
    private static final int WIDTH = 60;
    private static final String BORDER = "  " + "─".repeat(WIDTH);
    private static final ArrayList<Task> dataBank = new ArrayList<>();
    private static boolean testMode = false;

    public static void main(String[] args) {
        for (String arg : args) {
            if (arg.equalsIgnoreCase("--test")) {
                testMode = true;
                break;
            }
        }

        String logo = """
                  _   _                     \s
                 | \\ | | _____  ___   _ ___\s
                 |  \\| |/ _ \\ \\/ / | | / __|
                 | |\\  |  __/>  <| |_| \\__ \\
                 |_| \\_|\\___/_/\\_\\\\__,_|___/
                     \s""";

        printLine();
        System.out.println(logo);
        simulateBoot();
        printLine();
        System.out.println("    [NEXUS]: Greetings. I am Nexus, your personal chatbot. ");
        System.out.println("    [NEXUS]: How can I assist you? ");
        printLine();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println();
            String userInput = sc.nextLine().trim();
            System.out.println();

            if (userInput.equalsIgnoreCase("bye")) {
                printFarewell();
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                printList();
            } else if (userInput.startsWith("mark") || userInput.startsWith("unmark")) {
                String[] split = userInput.split(" ");
                if (split.length < 2) {
                    printLine();
                    System.out.println("    [NEXUS]: Invalid command. Did you forget to specify the task number? ");
                    System.out.println("    [NEXUS]: Example: mark <indexNumber>, unmark <indexNumber> ");
                    printLine();
                }

                else {
                    String command = split[0];
                    int index = Integer.parseInt(split[1]);

                    if ((index - 1) < dataBank.size()) {
                        if (command.equalsIgnoreCase("mark")) {
                            dataBank.get(index - 1).mark();
                        } else {
                            dataBank.get(index - 1).unmark();
                        }

                        printLine();
                        System.out.println("    [NEXUS]: Databank updated successfully. ");
                        System.out.printf("    TASK@ADDR_%d. %s", index, dataBank.get(index - 1).toString());
                        System.out.println();
                        printLine();

                    } else {
                        printLine();
                        System.out.println("    // ERROR: INVALID MEMORY ADDRESS ");
                        System.out.println("    [NEXUS]: Invalid task number. Please try again. ");
                        printLine();
                    }
                }

            } else {
                addData(userInput);
            }
        }

        sc.close();
    }

    public static void printLine() {
        System.out.println(BORDER);
    }

    public static void printFarewell() {
        printLine();
        System.out.println("    [NEXUS]: Request acknowledged. Nexus is now going offline. ");
        printLine();
    }

    public static void printResponse(String command) {
        printLine();
        System.out.println("    " + command);
        printLine();
    }

    public static void addData(String s) {
        Task newTask = new Task(s);
        dataBank.add(newTask);

        int addr = dataBank.size();

        printLine();
        System.out.printf("    // TASK STORED @ ADDR_%d: %s\n", addr, s);
        System.out.println("    [NEXUS]: Your task has been added into the databank successfully. ");
        printLine();
    }

    public static void printList() {
        printLine();
        System.out.println("    [NEXUS]: Accessing databank... \n");

        for (int i = 0; i < dataBank.size(); i++) {
            System.out.printf("    " + "TASK@ADDR_%d. %s\n", i + 1, dataBank.get(i).toString());
        }

        System.out.println();

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