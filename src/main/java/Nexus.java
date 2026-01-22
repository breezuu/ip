public class Nexus {
    private static final int WIDTH = 60;
    private static final String BORDER = "─".repeat(WIDTH);

    public static void main(String[] args) {
        String logo = """
                  _   _                     \s
                 | \\ | | _____  ___   _ ___\s
                 |  \\| |/ _ \\ \\/ / | | / __|
                 | |\\  |  __/>  <| |_| \\__ \\
                 |_| \\_|\\___/_/\\_\\\\__,_|___/
                     \s""";

        printLine();
        System.out.println(logo);
        printLine();
        System.out.println(" Hello! I'm Nexus.\n What can I do for you?");
        printLine();
        System.out.println(" Bye. Hope to see you again soon!");
        printLine();
    }

    public static void printLine() {
        System.out.println(BORDER);
    }
}