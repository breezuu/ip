import java.util.Scanner;

public class Nexus {
    private static final int WIDTH = 60;
    private static final String BORDER = "  " + "─".repeat(WIDTH);
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
            }

            if (!userInput.isEmpty()) {
                printResponse(userInput);
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

    public static void simulateBoot() {
        int maxWidth = 40;
        String[] bootSteps = {
                "// INITIALIZING NEXUS ",
                "// LOADING DATA "
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
    }
}