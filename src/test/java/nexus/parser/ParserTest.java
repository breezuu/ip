package nexus.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import nexus.exception.NexusException;
import nexus.commands.Command;
import nexus.commands.AddTodoCommand;

public class ParserTest {

    @Test
    public void parse_todoCommand_success() throws NexusException {
        Command c = Parser.parse("todo read book");
        assertEquals(true, c instanceof AddTodoCommand);
    }

    @Test
    public void parse_invalidCommand_exceptionThrown() {
        assertThrows(NexusException.class, () -> {
            Parser.parse("blah blah");
        });
    }
}