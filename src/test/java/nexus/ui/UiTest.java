package nexus.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nexus.tasks.TaskList;
import nexus.tasks.Todo;

public class UiTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Ui ui;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent)); // Redirect System.out to read what is printed
        ui = new Ui();
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut); // Restore System.out after testing
    }

    @Test
    public void printTaskAdded_todoTask_correctOutputFormatting() {
        TaskList tasks = new TaskList();
        Todo todo = new Todo("Read Book", false);
        tasks.addTask(todo);

        ui.printTaskAdded(todo, tasks);

        String output = outContent.toString();

        // Check if it is able to identify the task as a "Todo" task
        assertTrue(output.contains("Your Todo task has been added"));
        // Check if it is able to calculate the address correctly
        assertTrue(output.contains("TASK STORED @ ADDR_1"));
        // Check if it is able to show the total number of tasks correctly
        assertTrue(output.contains("CURRENT_TOTAL: 1 TASK"));
    }
}