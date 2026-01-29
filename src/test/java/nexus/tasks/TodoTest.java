package nexus.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    @Test
    public void testStringConversion() {
        Todo todo = new Todo("join sports club", false);
        assertEquals("[T][ ] join sports club", todo.toString());
    }

    @Test
    public void testMarkAsDone() {
        Todo todo = new Todo("join sports club", false);
        todo.mark();
        assertEquals("[T][X] join sports club", todo.toString());
    }
}