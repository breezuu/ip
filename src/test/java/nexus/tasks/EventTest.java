package nexus.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

public class EventTest {

    @Test
    public void constructor_validDates_parsesBothCorrectly() {
        // Testing that both start and end times are captured and formatted properly
        Event event = new Event("Interview", "1/1/2026 9:00 AM", "1/1/2026 9:30 AM", false);
        String expected = "[E][ ] Interview (from: Jan 01 2026 9:00 AM to: Jan 01 2026 9:30 AM)";
        assertEquals(expected, event.toString());
    }

    @Test
    public void isValidDate_matchesStartDateWindow_returnsTrue() {
        Event event = new Event("Meeting", "15/3/2026 10:00 AM", "15/3/2026 4:00 PM", false);
        assertTrue(event.isValidDateWindow("15/3/2026"));
    }

    @Test
    public void isValidDate_mismatchesStartDateWindow_returnsFalse() {
        Event event = new Event("Hackathon", "15/3/2026 10:00 AM", "16/3/2026 4:00 PM", false);
        // The result should be false regardless of the end date (since matching logic is tied to startTime)
        assertFalse(event.isValidDateWindow("16/3/2026"));
    }

    @Test
    public void saveString_outputsCorrectFormatForStorage() {
        Event event = new Event("tP", "10/10/2026 1:00 PM", "10/11/2026 2:00 PM", true);
        // Verifying the saved string structure: Type of Task | Status | Description | Start Time | End Time
        String expected = "E | 1 | tP | 10/10/2026 1:00 PM | 10/11/2026 2:00 PM";
        assertEquals(expected, event.saveString());
    }

    @Test
    public void constructor_invalidEndTime_throwsException() {
        // Ensuring that when the end date of an event is malformed, the constructor fails
        assertThrows(DateTimeParseException.class, () -> {
            new Event("Failure Task", "1/1/2026 12:00 PM", "fake date", false);
        });
    }
}
