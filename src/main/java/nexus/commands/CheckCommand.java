package nexus.commands;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import nexus.exception.NexusException;
import nexus.storage.Storage;
import nexus.tasks.Task;
import nexus.tasks.TaskList;
import nexus.ui.Ui;

public class CheckCommand extends Command {
    private final String dateToCheck;

    public CheckCommand(String date) {
        this.dateToCheck = date;
    }

    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) throws NexusException {
        ArrayList<Task> taskList = tasks.getTasks();
        List<Task> result;

        System.out.println("    [NEXUS]: Checking for existing deadlines/events with the specified date...");

        try {
            result = taskList.stream()
                    .filter(t -> t.isDuringDate(this.dateToCheck))
                    .toList();
            ui.printTaskList(result);
        } catch (DateTimeParseException e) {
            System.out.println("    [NEXUS]: Did you follow the date format (DD/MM/YYYY)?");
            System.out.println("    // e.g. 'check 01/01/2002'");
        }
    }
}
