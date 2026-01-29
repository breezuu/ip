package nexus.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import nexus.tasks.TaskList;
import nexus.tasks.Deadline;
import nexus.ui.Ui;
import nexus.storage.Storage;
import nexus.exception.NexusException;

public class AddDeadlineCommandTest {

    @Test
    public void run_validDate_taskAddedToTaskList() throws NexusException {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("data/test.txt");

        // Using the format expected by Deadline.java (d/M/yyyy h:mm a)
        AddDeadlineCommand command = new AddDeadlineCommand("Submit project", "1/1/2026 11:59 PM", false);
        command.run(tasks, ui, storage);

        assertEquals(1, tasks.getSize(), "TaskList should have 1 task after execution");
        assertTrue(tasks.getTasks().get(0) instanceof Deadline);
        assertEquals("Submit project", tasks.getTasks().get(0).getDescription());
    }

    @Test
    public void run_invalidDate_taskNotAdded() throws NexusException {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("data/test.txt");

        // Invalid date format (missing AM/PM and wrong separators used for date)
        AddDeadlineCommand command = new AddDeadlineCommand("Broken Task", "2026-01-01 12:00", false);

        command.run(tasks, ui, storage);

        assertEquals(0, tasks.getSize(), "The task above should not be added if date parsing fails");
    }
}