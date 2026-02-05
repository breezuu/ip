package nexus.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    @Test
    public void constructor_validFormat_parsesCorrectly() {
        // Testing the parsing of "1/1/2002 12:00 PM"
        Deadline deadline = new Deadline("Submit report", "1/1/2002 12:00 PM", false);
        assertEquals("[D][ ] Submit report (by: Jan 01 2002 12:00 PM)", deadline.toString());
    }

    @Test
    public void isValidDate_matchingDateWindow_returnsTrue() {
        Deadline deadline = new Deadline("Quiz", "15/3/2024 11:59 PM", false);
        assertTrue(deadline.isValidDateWindow("15/3/2024"));
    }

    @Test
    public void isValidDate_wrongDateWindow_returnsFalse() {
        Deadline deadline = new Deadline("Quiz", "15/3/2024 11:59 PM", false);
        assertFalse(deadline.isValidDateWindow("16/3/2024"));
    }

    @Test
    public void saveString_returnsCorrectFormat() {
        Deadline deadline = new Deadline("Homework", "5/12/2023 6:30 AM", true);
        // Check to ensure that the saved string is read back by Storage correctly
        assertEquals("D | 1 | Homework | 5/12/2023 6:30 AM", deadline.saveString());
    }
}
