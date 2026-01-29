package nexus.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import nexus.commands.AddTodoCommand;
import nexus.commands.ExitCommand;
import nexus.exception.NexusException;

public class ParserTest {

    @Test
    public void parse_validTodo_returnsAddTodoCommand() throws NexusException {
        assertTrue(Parser.parse("todo read book") instanceof AddTodoCommand);
    }

    @Test
    public void parse_exitCommand_returnsExitCommand() throws NexusException {
        assertTrue(Parser.parse("bye") instanceof ExitCommand);
    }

    @Test
    public void parse_invalidCommand_exceptionThrown() {
        NexusException exception = assertThrows(NexusException.class, () -> {
            Parser.parse("fakeCommand");
        });
        assertTrue(exception.getMessage().contains("INVALID COMMAND"));
    }

    @Test
    public void parse_emptyTodoDescription_exceptionThrown() {
        try {
            Parser.parse("todo   ");
            fail(); // This method should not be run
        } catch (NexusException e) {
            assertTrue(e.getMessage().contains("EXAMPLE: todo tidy up room"));
        }
    }

    @Test
    public void parse_deadlineMissingBy_exceptionThrown() {
        assertThrows(NexusException.class, () -> {
            Parser.parse("deadline return book");
        });
    }
}
