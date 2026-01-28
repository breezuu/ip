class Parser {
    public static Command parse(String fullCommand) throws NexusException {
        String[] split = fullCommand.trim().split(" ", 2);
        String keyword = split[0].toLowerCase();
        String args = split.length > 1 ? split[1].trim() : "";

        switch (keyword) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
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
            return new CheckCommand(args);
        default:
            throw new NexusException("INVALID COMMAND. PLEASE TRY AGAIN.");
        }
    }

    public static int parseIndex(String[] split) throws NexusException {
        if (split.length < 2 || split[1].isBlank()) {
            System.out.println("    [NEXUS]: Please specify the task number you wish to mark or delete.");
            throw new NexusException("// EXAMPLE: mark 6, delete 7");
        }

        try {
            return Integer.parseInt(split[1]);
        } catch (NumberFormatException e) {
            throw new NexusException("// ERROR: PROVIDE A VALID INDEX NUMBER");
        }
    }

    private static Command prepareTodo(String info) throws NexusException {
        if (info.isBlank()) {
            System.out.println("    // ERROR: INVALID TO-DO TASK");
            System.out.println("    [NEXUS]: Did you forget to specify the 'To-Do' description?");
            throw new NexusException("// EXAMPLE: todo tidy up room");
        }
        return new AddTodoCommand(info);
    }

    private static Command prepareDeadline(String info) throws NexusException {
        if (!info.contains(" /by ")) {
            System.out.println("    // ERROR: INVALID DEADLINE TASK");
            System.out.println("    [NEXUS]: Did you miss out on the '/by' specifier?");
            throw new NexusException("// EXAMPLE: 'deadline quiz /by 01/01/2002 12:00 PM'");
        }

        String[] split = info.split(" /by ");
        String description = split[0].trim();

        if (description.isEmpty()) {
            throw new NexusException("// ERROR: DEADLINE HAS NO DESCRIPTION");
        }

        String deadline = split[1].trim();

        return new AddDeadlineCommand(description, deadline, false);
    }

    private static Command prepareEvent(String info) throws NexusException {
        if (!info.contains(" /from ") || !info.contains(" /to ")) {
            System.out.println("    [NEXUS]: Events require BOTH '/from' and '/to' timings.");
            throw new NexusException("// EXAMPLE: event tuition /from <startTime> /to <endTime>");
        }

        String[] split = info.split(" /from ", 2);
        String eventDesc = split[0].trim();

        if (eventDesc.isEmpty()) {
            throw new NexusException("// ERROR: EVENT HAS NO DESCRIPTION");
        }

        String[] eventTimeSplit = split[1].split(" /to ", 2);
        String startTime = eventTimeSplit[0].trim();
        String endTime = eventTimeSplit[1].trim();

        return new AddEventCommand(eventDesc, startTime, endTime, false);
    }
}
