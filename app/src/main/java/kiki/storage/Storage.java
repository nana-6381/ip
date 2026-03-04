package kiki.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import kiki.task.Deadline;
import kiki.task.Event;
import kiki.task.Task;
import kiki.task.Todo;

/**
 * Handles saving and loading tasks to/from a file.
 */
public class Storage {
    private String filePath;

    /**
     * Initializes the Storage object with a specific file path.
     * @param filePath The path to the file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the local file system.
     * If the data folder or file does not exist, they will be created.
     *
     * @return An ArrayList of Task objects parsed from the storage file.
     * @throws IOException If an I/O error occurs while opening or creating the file.
     */
    public ArrayList<Task> loadTasks() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            Path path = Paths.get(this.filePath);

            // Create folder if missing
            Files.createDirectories(path.getParent());

            // Create file if missing
            if (!Files.exists(path)) {
                Files.createFile(path);
                return tasks;
            }

            BufferedReader br = Files.newBufferedReader(path);
            String line;

            while ((line = br.readLine()) != null) {
                Task t = parseTask(line);
                if (t != null) {
                    tasks.add(t);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Warning: problem loading saved tasks");
        }
        return tasks;
    }

    /**
     * Saves the provided list of tasks to the local file system.
     * The tasks are encoded into a string format before being written to the file.
     *
     * @param tasks The ArrayList of Task objects to be persisted.
     */
    public void saveTasks(ArrayList<Task> tasks) {
        try {
            Path path = Paths.get(this.filePath);
            Files.createDirectories(path.getParent());

            BufferedWriter bw = Files.newBufferedWriter(path);

            for (Task t : tasks) {
                bw.write(encodeTask(t));
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("Warning: problem saving tasks");
        }
    }

    private String encodeTask(Task t) {
        if (t instanceof Todo) {
            return "T | " + (t.isDone() ? "1" : "0") + " | " + t.getDescription();
        } else if (t instanceof Deadline d) {
            return "D | " + (d.isDone() ? "1" : "0") + " | " + d.getDescription() + " | " + d.getEnd();
        } else if (t instanceof Event e) {
            return "E | " + (t.isDone() ? "1" : "0") + " | "
                    + e.getDescription() + " | " + e.getStart() + " | " + e.getEnd();
        }
        return "";
    }

    private Task parseTask(String line) {
        try {
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            boolean done = parts[1].equals("1");

            if (type.equals("T")) {
                Task t = new Todo(parts[2]);
                if (done) {
                    t.setDone();
                }
                return t;
            }

            if (type.equals("D")) {
                Task t = new Deadline(parts[2], parts[3]);
                if (done) {
                    t.setDone();
                }
                return t;
            }

            if (type.equals("E")) {
                Task t = new Event(parts[2], parts[3], parts[4]);
                if (done) {
                    t.setDone();
                }
                return t;
            }
        } catch (Exception e) {
            // Ignore correpted line
            return null;
        }
        return null;
    }
}
