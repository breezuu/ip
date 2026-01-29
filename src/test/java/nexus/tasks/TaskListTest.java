package nexus.tasks;

import org.junit.jupiter.api.Test;
import nexus.exception.NexusException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskListTest {

    @Test
    public void deleteTask_validIndex_success() throws NexusException {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("Task 1", false));
        tasks.add(new Todo("Task 2", false));
        TaskList taskList = new TaskList(tasks);

        Task deleted = taskList.deleteTask(0); // Delete first task (zero-based index)

        assertEquals("Task 1", deleted.getDescription());
        assertEquals(1, taskList.getSize(), "Size of task list should be reduced to 1");
    }
}