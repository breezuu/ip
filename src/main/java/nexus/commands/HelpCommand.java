package nexus.commands;

import java.util.HashMap;

import nexus.exception.NexusException;
import nexus.parser.CommandType;
import nexus.storage.Storage;
import nexus.tasks.TaskList;
import nexus.ui.Ui;

public class HelpCommand extends Command {
    private static final HashMap<String, String> commandHelpMap = new HashMap<>();
    private static final String ARROW_INDENT = ">> ";

    @Override
    public String run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        String availableCmdMsg = "[NEXUS]: The following commands are available:\n";
        String deadlineHelp = "deadline - Add a deadline task with a due date and time\n";
        String eventHelp = "event - Add an event task with a start and end date and time\n";
        String noteHelp = "note - Add a note\n";
        String todoHelp = "todo - Add a To-Do task\n";
        String checkHelp = "check - Check for existing deadlines/events with the specified date\n";
        String deleteHelp = "delete - Delete a task corresponding to the specified index from the task list\n";
        String byeHelp = "bye - Exit the application\n";
        String findHelp = "find - Find tasks in the task list based on a keyword\n";
        String listHelp = "list - List tasks in the task list based on type (tasks/deadlines/events/notes)\n";
        String markHelp = "mark - Mark a task as done\n";
        String unmarkHelp = "unmark - Unmark a task\n";

        commandHelpMap.put("deadline", deadlineHelp);
        commandHelpMap.put("event", eventHelp);
        commandHelpMap.put("note", noteHelp);
        commandHelpMap.put("todo", todoHelp);
        commandHelpMap.put("check", checkHelp);
        commandHelpMap.put("delete", deleteHelp);
        commandHelpMap.put("bye", byeHelp);
        commandHelpMap.put("find", findHelp);
        commandHelpMap.put("list", listHelp);
        commandHelpMap.put("mark", markHelp);
        commandHelpMap.put("unmark", unmarkHelp);

        StringBuilder sb = new StringBuilder();
        sb.append(availableCmdMsg);
        String cmdInfo = displayBriefCommandInfo();
        sb.append(cmdInfo);
        return sb.toString();
    }

    private String displayBriefCommandInfo() {
        StringBuilder sb = new StringBuilder();
        for (CommandType cmdType : CommandType.values()) {
            if (cmdType == CommandType.INVALID || cmdType == CommandType.HELP) {
                continue;
            }
            sb.append(ARROW_INDENT).append(commandHelpMap.get(cmdType.toString().toLowerCase()));
        }

        return sb.toString().trim();
    }

    @Override
    public String getName() {
        return "HelpCommand";
    }
}
