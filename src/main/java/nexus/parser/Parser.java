package nexus.parser;

import nexus.commands.AddDeadlineCommand;
import nexus.commands.AddEventCommand;
import nexus.commands.AddNoteCommand;
import nexus.commands.AddTodoCommand;
import nexus.commands.CheckCommand;
import nexus.commands.Command;
import nexus.commands.DeleteCommand;
import nexus.commands.ExitCommand;
import nexus.commands.FindCommand;
import nexus.commands.ListCommand;
import nexus.commands.MarkCommand;
import nexus.commands.UnmarkCommand;
import nexus.exception.NexusException;

/**
 * Parser class responsible for parsing user input commands.
 */
public class Parser {
    /**
     * Parses the user input command and returns the corresponding Command object.
     * @param fullCommand User input command string.
     * @return Command object representing the parsed command.
     * @throws NexusException If the command is invalid or cannot be parsed.
     */
    public static Command parse(String fullCommand) throws NexusException {
        String[] split = fullCommand.trim().split(" ", 2);
        String keyword = split[0].toLowerCase();
        String args = split.length > 1 ? split[1].trim() : "";

        switch (keyword) {
        case "bye":
            return new ExitCommand();
        case "list":
            return prepareList(args);
        case "mark":
            return new MarkCommand(parseIndex(split));
        case "unmark":
            return new UnmarkCommand(parseIndex(split));
        case "todo":
            return prepareTodo(args);
        case "deadline":
            return prepareDeadline(args);
        case "event":
            return prepareEvent(args);
        case "delete":
            return new DeleteCommand(parseIndex(split));
        case "check":
            return new CheckCommand(parseDate(args));
        case "find":
            return new FindCommand(parseKeyword(args));
        case "note":
            return prepareNote(args);
        default:
            assert false : "Unknown command type";
            throw new NexusException("INVALID COMMAND. PLEASE TRY AGAIN.");
        }
    }

    /**
     * Parses the task index from the user input.
     * @param split Array of command arguments.
     * @return Parsed task index as an integer.
     * @throws NexusException If the index is invalid or cannot be parsed.
     */
    public static int parseIndex(String[] split) throws NexusException {
        if (split.length < 2 || split[1].isBlank()) {
            StringBuilder sb = new StringBuilder();
            sb.append("[NEXUS]: Please specify the task number you wish to mark or delete.\n");
            sb.append("// EXAMPLE: mark 6, delete 7");
            throw new NexusException(sb.toString());
        }

        try {
            return Integer.parseInt(split[1]);
        } catch (NumberFormatException e) {
            throw new NexusException("// ERROR: PROVIDE A VALID INDEX NUMBER");
        }
    }

    /**
     * Parses the date from the user input.
     * @param date Date string provided by the user.
     * @return Parsed date string.
     * @throws NexusException If the date is invalid or cannot be parsed.
     */
    public static String parseDate(String date) throws NexusException {
        if (date.isBlank()) {
            StringBuilder sb = new StringBuilder();
            sb.append("// ERROR: NO DATE SPECIFIED\n");
            sb.append("[NEXUS]: Please specify the date you wish to check.\n");
            sb.append("// EXAMPLE: check 1/1/2002");
            throw new NexusException(sb.toString());
        }

        return date;
    }

    /**
     * Parses the keyword from the user input.
     * @param keyword Keyword string provided by the user.
     * @return Parsed keyword string.
     * @throws NexusException If the keyword is invalid or cannot be parsed.
     */
    public static String parseKeyword(String keyword) throws NexusException {
        if (keyword.isBlank()) {
            StringBuilder sb = new StringBuilder();
            sb.append("// ERROR: NO KEYWORD SPECIFIED\n");
            sb.append("[NEXUS]: Please specify a keyword.\n");
            sb.append("// EXAMPLE: find exam");
            throw new NexusException(sb.toString());
        }

        return keyword;
    }

    /**
     * Parses the user input for a Todo command and returns the corresponding Command object.
     * @param info User input string for the Todo command.
     * @return Command object representing the parsed Todo command.
     * @throws NexusException If the Todo command is invalid or cannot be parsed.
     */
    private static Command prepareTodo(String info) throws NexusException {
        if (info.isBlank()) {
            StringBuilder sb = new StringBuilder();
            sb.append("// ERROR: INVALID TO-DO TASK\n");
            sb.append("[NEXUS]: Did you forget to specify the 'To-Do' description?\n");
            sb.append("// EXAMPLE: todo tidy up room");
            throw new NexusException(sb.toString());
        }
        return new AddTodoCommand(info);
    }

    /**
     * Parses the user input for a Deadline command and returns the corresponding Command object.
     * @param info User input string for the Deadline command.
     * @return Command object representing the parsed Deadline command.
     * @throws NexusException If the Deadline command is invalid or cannot be parsed.
     */
    private static Command prepareDeadline(String info) throws NexusException {
        if (!info.contains(" /by ")) {
            StringBuilder sb = new StringBuilder();
            sb.append("// ERROR: INVALID DEADLINE TASK\n");
            sb.append("[NEXUS]: Did you miss out on the '/by' specifier?\n");
            sb.append("// EXAMPLE: deadline quiz /by 01/01/2002 12:00 PM");
            throw new NexusException(sb.toString());
        }

        String[] split = info.split(" /by ");

        assert split.length >= 2 : "The split array should have at least 2 parts";

        String description = split[0].trim();

        if (description.isEmpty()) {
            throw new NexusException("// ERROR: DEADLINE HAS NO DESCRIPTION");
        }

        String deadline = split[1].trim();

        return new AddDeadlineCommand(description, deadline, false);
    }

    /**
     * Parses the user input for an Event command and returns the corresponding Command object.
     * @param info User input string for the Event command.
     * @return Command object representing the parsed Event command.
     * @throws NexusException If the Event command is invalid or cannot be parsed.
     */
    private static Command prepareEvent(String info) throws NexusException {
        if (!info.contains(" /from ") || !info.contains(" /to ")) {
            StringBuilder sb = new StringBuilder();
            sb.append("// ERROR: INVALID EVENT TASK\n");
            sb.append("[NEXUS]: Events require BOTH '/from' and '/to' timings.\n");
            sb.append("// EXAMPLE: event race /from 01/01/2002 1:00 PM /to 01/01/2002 2:00 PM");
            throw new NexusException(sb.toString());
        }

        String[] split = info.split(" /from ", 2);
        assert split.length == 2 : "The split array should have exactly 2 parts";

        String eventDesc = split[0].trim();

        if (eventDesc.isEmpty()) {
            throw new NexusException("// ERROR: EVENT HAS NO DESCRIPTION");
        }

        String[] eventTimeSplit = split[1].split(" /to ", 2);
        assert eventTimeSplit.length == 2 : "The eventTimeSplit array should have exactly 2 parts";

        String startTime = eventTimeSplit[0].trim();
        String endTime = eventTimeSplit[1].trim();

        return new AddEventCommand(eventDesc, startTime, endTime, false);
    }

    /**
     * Prepares a command to add a note to the note list.
     * @param info The description of the note to be added.
     * @return A command to add the note.
     * @throws NexusException If the note description is missing.
     */
    private static Command prepareNote(String info) throws NexusException {
        if (info.isBlank()) {
            StringBuilder sb = new StringBuilder();
            sb.append("// ERROR: INVALID NOTE\n");
            sb.append("[NEXUS]: Did you forget to specify the note description?\n");
            sb.append("// EXAMPLE: note finish quiz");
            throw new NexusException(sb.toString());
        }
        return new AddNoteCommand(info);
    }

    /**
     * Prepares a command to list tasks based on the specified type.
     * @param args The type of tasks to list (e.g. "tasks", "deadlines", "events", "notes").
     * @return A command to list tasks.
     */
    private static Command prepareList(String args) {
        String type = args.trim();
        if (type.isEmpty()) {
            type = "all";
        }
        return new ListCommand(type);
    }
}
