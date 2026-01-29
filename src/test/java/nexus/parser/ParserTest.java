package nexus.parser;

import org.junit.jupiter.api.Test;
import nexus.exception.NexusException;
import nexus.commands.Command;
import nexus.commands.AddDeadlineCommand;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {

    @Test
    public void parse_validDeadline_returnsDeadlineCommand() throws NexusException {
        Command result = Parser.parse("deadline return book /by 1/1/2002 6:00 PM");
        assertTrue(result instanceof AddDeadlineCommand, "Result should be an AddDeadlineCommand");
    }

    @Test
    public void parse_emptyDescription_throwsException() {
        // Checks that "todo " (with no description) triggers an error
        NexusException thrown = assertThrows(NexusException.class, () -> {
            Parser.parse("todo ");
        });
        assertEquals("// EXAMPLE: todo tidy up room", thrown.getMessage());
    }

    @Test
    public void parse_unknownCommand_throwsException() {
        assertThrows(NexusException.class, () -> {
            Parser.parse("test");
        });
    }
}