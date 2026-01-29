package nexus.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import nexus.exception.NexusException;
import nexus.tasks.Deadline;
import nexus.tasks.Task;
import nexus.tasks.Todo;

public class StorageTest {

    private final Storage storage = new Storage("data/databank.txt");

    @Test
    public void parseTasks_validTodo_returnsCorrectTodo() throws NexusException {
        Task task = storage.parseTasks("T | 1 | read book");
        assertTrue(task instanceof Todo);
        assertEquals("read book", task.getDescription());
        assertTrue(task.isDone());
    }

    @Test
    public void parseTasks_validDeadline_returnsCorrectDescription() throws NexusException {
        Task task = storage.parseTasks("D | 0 | return book | 1/2/2003 11:59 PM");
        assertTrue(task instanceof Deadline);
        assertEquals("return book", task.getDescription());
    }

    @Test
    public void parseTasks_invalidFormat_throwsNexusException() {
        assertThrows(NexusException.class, () -> {
            storage.parseTasks("T | 1");
        });
    }

    @Test
    public void parseTasks_corruptedDeadline_throwsNexusException() {
        NexusException exception = assertThrows(NexusException.class, () -> {
            storage.parseTasks("D | 1 | finish project");
        });
        assertTrue(exception.getMessage().contains("CORRUPTED DEADLINE FORMAT"));
    }

    @Test
    public void parseTasks_unknownType_throwsNexusException() {
        assertThrows(NexusException.class, () -> {
            storage.parseTasks("X | 0 | unknown task");
        });
    }
}