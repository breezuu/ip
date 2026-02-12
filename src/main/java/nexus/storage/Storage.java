package nexus.storage;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import nexus.Nexus;
import nexus.exception.NexusException;
import nexus.tasks.Deadline;
import nexus.tasks.Event;
import nexus.tasks.Task;
import nexus.tasks.Todo;

/**
 * Storage class responsible for loading and saving tasks to a file.
 */
public class Storage {
    private static final String INVALID_TASK_FORMAT = "// ERROR: INVALID TASK FORMAT";
    private static final String INVALID_TASK_TYPE = "// ERROR: INVALID TASK TYPE";
    private static final String CORRUPTED_DEADLINE_FORMAT = "// ERROR: CORRUPTED DEADLINE FORMAT";
    private static final String CORRUPTED_EVENT_FORMAT = "// ERROR: CORRUPTED EVENT FORMAT";

    private static final String SEPARATOR = "\\s*\\|\\s*";

    private final String filePath;

    /**
     * Constructs a Storage object with the specified file path.
     * @param filePath Path to the file for storing tasks.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file and returns them as an ArrayList.
     * @return ArrayList of loaded tasks.
     * @throws NexusException If there is an error loading tasks from the file.
     */
    public ArrayList<Task> loadTasks() throws NexusException {
        checkDirectoryAndFile();
        ArrayList<Task> loadedTasks = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(this.filePath));

            for (String s : lines) {
                processLine(s, loadedTasks);
            }

        } catch (IOException e) {
            throw new NexusException(e.getMessage());
        }

        return loadedTasks;
    }

    private void processLine(String s, ArrayList<Task> loadedTasks) throws NexusException {
        try {
            Task task = parseTasks(s);
            loadedTasks.add(task);
        } catch (NexusException e) {
            throw new NexusException(e.getMessage());
        }
    }

    /**
     * Parses a task string and returns a Task object.
     * @param s Task string to be parsed.
     * @return Task object representing the parsed task.
     * @throws NexusException If the task string is invalid or cannot be parsed.
     */
    public Task parseTasks(String s) throws NexusException {
        String[] components = s.split(SEPARATOR); // Splits the task based on '|'
        if (components.length < 3) {
            throw new NexusException(INVALID_TASK_FORMAT);
        }

        String taskType = components[0].trim();
        boolean isDone = components[1].trim().equals("1");
        String taskDesc = components[2].trim();

        switch (taskType) {
        case "T":
            return new Todo(taskDesc, isDone);
        case "D":
            return createDeadline(components, taskDesc, isDone);
        case "E":
            return createEvent(components, taskDesc, isDone);
        default:
            throw new NexusException(INVALID_TASK_TYPE);
        }
    }

    private static Deadline createDeadline(String[] components, String taskDesc, boolean isDone) throws NexusException {
        if (components.length < 4) {
            throw new NexusException(CORRUPTED_DEADLINE_FORMAT);
        }
        return new Deadline(taskDesc, components[3], isDone);
    }

    private static Event createEvent(String[] components, String taskDesc, boolean isDone) throws NexusException {
        if (components.length < 5) {
            throw new NexusException(CORRUPTED_EVENT_FORMAT);
        }
        return new Event(taskDesc, components[3], components[4], isDone);
    }

    /**
     * Saves the tasks to the file.
     * @param tasks List of tasks to be saved.
     */
    public void saveTasks(List<Task> tasks) throws NexusException {
        try {
            FileWriter fw = new FileWriter(this.filePath);
            for (Task t : tasks) {
                fw.write(t.saveString() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            throw new NexusException(e.getMessage());
        }
    }

    /**
     * Checks if the data directory and file exist and creates them if they do not exist.
     * @throws NexusException If there is an error creating the directory.
     */
    private void checkDirectoryAndFile() throws NexusException {
        try {
            Path path = Paths.get(this.filePath);
            Path parentDir = path.getParent();

            if ((parentDir != null) && (Files.notExists(parentDir))) {
                Files.createDirectories(parentDir);
            }

            if (Files.notExists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            throw new NexusException(e.getMessage());
        }
    }
}
