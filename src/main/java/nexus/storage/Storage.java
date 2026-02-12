package nexus.storage;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;

import nexus.exception.NexusException;
import nexus.tasks.Deadline;
import nexus.tasks.Event;
import nexus.tasks.Task;
import nexus.tasks.Todo;

/**
 * Storage class responsible for loading and saving tasks to a file.
 */
public class Storage {
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
        ArrayList<Task> loadedTasks = new ArrayList<>();

        Path filePath = Paths.get("data", "databank.txt");

        try {
            checkDirectory(); // Check if the 'data' directory exists

            if (Files.notExists(filePath)) { // If the file 'databank.txt' does not exist...
                Files.createFile(filePath); // Create the file
            }

            List<String> lines = Files.readAllLines(filePath);

            int lineNumber = 1;

            for (String s : lines) {
                try {
                    Task task = parseTasks(s);
                    loadedTasks.add(task);
                } catch (NexusException e) {
                    String errorMsg = e.getMessage() + " (at line " + lineNumber + ")";
                    throw new NexusException(errorMsg);
                }
                lineNumber++;
            }

        } catch (IOException e) {
            System.out.println("    // ERROR: " + e.getMessage());
        }

        return loadedTasks;
    }

    /**
     * Parses a task string and returns a Task object.
     * @param s Task string to be parsed.
     * @return Task object representing the parsed task.
     * @throws NexusException If the task string is invalid or cannot be parsed.
     */
    public Task parseTasks(String s) throws NexusException {
        String[] components = s.split("\\s*\\|\\s*"); // Splits the task based on '|'
        if (components.length < 3) {
            throw new NexusException("// ERROR: INVALID TASK FORMAT");
        }

        String taskType = components[0].trim();
        boolean isDone = components[1].trim().equals("1");
        String taskDesc = components[2].trim();

        switch (taskType) {
        case "T":
            return new Todo(taskDesc, isDone);
        case "D":
            if (components.length < 4) {
                throw new NexusException("// ERROR: CORRUPTED DEADLINE FORMAT");
            }

            try {
                return new Deadline(taskDesc, components[3], isDone);
            } catch (DateTimeException e) {
                throw new NexusException("// ERROR: INVALID DATE FORMAT");
            }
        case "E":
            if (components.length < 5) {
                throw new NexusException("// ERROR: CORRUPTED EVENT FORMAT");
            }

            try {
                return new Event(taskDesc, components[3], components[4], isDone);
            } catch (DateTimeException e) {
                throw new NexusException("// ERROR: INVALID DATE FORMAT");
            }
        default:
            assert false : "Unknown task type";
            throw new NexusException("// ERROR: INVALID TASK TYPE");
        }
    }

    /**
     * Saves the tasks to the file.
     * @param tasks List of tasks to be saved.
     */
    public void saveTasks(List<Task> tasks) {
        try {
            FileWriter fw = new FileWriter("./data/databank.txt");
            for (Task t : tasks) {
                fw.write(t.saveString() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("    // ERROR: " + e.getMessage());
        }
    }

    /**
     * Checks if the data directory exists and creates it if it does not exist.
     * @throws IOException If there is an error creating the directory.
     */
    public void checkDirectory() throws IOException {
        Path path = Paths.get("data");
        if (Files.notExists(path)) {
            Files.createDirectory(path);
        }
    }
}
