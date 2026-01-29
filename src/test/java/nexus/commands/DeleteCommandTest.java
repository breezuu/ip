package nexus.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import nexus.exception.NexusException;
import nexus.storage.Storage;
import nexus.tasks.TaskList;
import nexus.tasks.Todo;
import nexus.ui.Ui;

public class DeleteCommandTest {

    @Test
    public void execute_validIndex_taskRemoved() throws NexusException {
        ArrayList<nexus.tasks.Task> tasks = new ArrayList<>();
        tasks.add(new Todo("First Task", false));
        TaskList taskList = new TaskList(tasks);
        Ui ui = new Ui();

        Storage storage = new Storage("data/test.txt");

        DeleteCommand command = new DeleteCommand(1);
        command.run(taskList, ui, storage);

        assertEquals(0, taskList.getSize(), "TaskList should be empty after deletion");
    }

    @Test
    public void execute_invalidIndex_throwsNexusException() {
        TaskList taskList = new TaskList(new ArrayList<>());
        Ui ui = new Ui();
        Storage storage = new Storage("data/test.txt");

        DeleteCommand command = new DeleteCommand(1);

        assertThrows(NexusException.class, () -> {
            command.run(taskList, ui, storage);
        }, "NexusException should be thrown due to invalid index via validateIndex()");
    }
}
