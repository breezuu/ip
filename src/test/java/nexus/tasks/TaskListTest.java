package nexus.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import nexus.exception.NexusException;

public class TaskListTest {

    @Test
    public void addTask_validTask_sizeIncreases() {
        TaskList taskList = new TaskList();
        Task todo = new Todo("Read book", false);
        taskList.addTask(todo);
        assertEquals(1, taskList.getSize());
        assertEquals(todo, taskList.getTasks().get(0));
    }

    @Test
    public void validateIndex_emptyList_throwsException() {
        TaskList taskList = new TaskList();
        NexusException exception = assertThrows(NexusException.class, () -> {
            taskList.validateIndex(1);
        });
        assertTrue(exception.getMessage().contains("DATABANK EMPTY"));
    }

    @Test
    public void validateIndex_outOfBounds_throwsException() {
        TaskList taskList = new TaskList();
        taskList.addTask(new Todo("Testing", false));

        // Index too high
        NexusException highException = assertThrows(NexusException.class, () -> {
            taskList.validateIndex(2);
        });
        assertTrue(highException.getMessage().contains("CURRENT_TOTAL: 1 TASK"));

        // Index too low
        assertThrows(NexusException.class, () -> {
            taskList.validateIndex(0);
        });
    }

    @Test
    public void deleteTask_validIndex_returnsCorrectTask() {
        TaskList taskList = new TaskList();
        Task todo = new Todo("Delete me for testing purposes", false);
        taskList.addTask(todo);
        Task deleted = taskList.deleteTask(0);
        assertEquals(todo, deleted);
        assertEquals(0, taskList.getSize());
    }
}