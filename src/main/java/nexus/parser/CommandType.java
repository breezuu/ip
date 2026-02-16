package nexus.parser;

/**
 * Represents the different types of user commands supported by the chatbot.
 */
public enum CommandType {
    DEADLINE,
    EVENT,
    NOTE,
    TODO,
    CHECK,
    DELETE,
    BYE,
    FIND,
    LIST,
    MARK,
    UNMARK,
    HELP,
    INVALID;

    public static CommandType getType(String keyword) {
        try {
            return CommandType.valueOf(keyword.toUpperCase());
        } catch (IllegalArgumentException e) {
            return INVALID;
        }
    }
}
