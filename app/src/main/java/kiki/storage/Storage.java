package kiki.storage;

import kiki.task.*;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class Storage {
    private static final String FILE_PATH = "data/kiki.txt";
    public static ArrayList<Task> loadTasks() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            Path path = Paths.get(FILE_PATH);

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

    public static void saveTasks(ArrayList<Task> tasks) {
        try {
            Path path = Paths.get(FILE_PATH);
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

    private static String encodeTask(Task t) {
        if (t instanceof Todo) {
            return "T | " + (t.isDone() ? "1" : "0") + " | " + t.getDescription();
        } else if (t instanceof Deadline d) {
            return "D | " + (d.isDone() ? "1" : "0") + " | " + d.getDescription() + " | " + d.getEnd();
        } else if (t instanceof Event e) {
            return "E | " + (t.isDone() ? "1" : "0") + " | " + e.getDescription() + " | " + e.getStart() + " | " + e.getEnd();
        }
        return "";
    }

    private static Task parseTask(String line) {
        try {
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            boolean done = parts[1].equals("1");

            if (type.equals("T")) {
                Task t = new Todo(parts[2]);
                if (done) t.setDone();
                return t;
            }

            if (type.equals("D")) {
                Task t = new Deadline(parts[2], parts[3]);
                if (done) t.setDone();
                return t;
            }

            if (type.equals("E")) {
                Task t = new Event(parts[2], parts[3], parts[4]);
                if (done) t.setDone();
                return t;
            }
        } catch (Exception e) {
            // Ignore correpted line
            return null;
        }
        return null;
    }
}
