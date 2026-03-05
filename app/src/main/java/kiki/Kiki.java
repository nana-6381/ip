package kiki;

import java.util.ArrayList;

import kiki.parser.Parser;
import kiki.storage.Storage;
import kiki.task.Deadline;
import kiki.task.Event;
import kiki.task.Task;
import kiki.task.TaskList;
import kiki.task.Todo;
import kiki.ui.Ui;

/**
 * Main class for the Kiki chatbot.
 * Handles the initialization and the main execution loop.
 */

public class Kiki {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Creates a new instance of the class.
     */
    public Kiki(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            // Load existing tasks into the TaskList
            this.tasks = new TaskList(this.storage.loadTasks());
        } catch (Exception e) {
            ui.showLoadingError();
            this.tasks = new TaskList();
        }
    }

    /**
     * Starts the application.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            String fullCommand = ui.readCommand();
            if (fullCommand.isEmpty()) {
                ui.printMessage("(say something please)");
                continue;
            }

            ui.showLine();
            String commandWord = Parser.getCommandWord(fullCommand);

            try {
                switch (commandWord) {
                case "bye":
                    isExit = true;
                    ui.printMessage("Bye, hope to see you soon.\n- (づ｡◕‿‿◕｡)づ  K i k i");
                    break;
                case "filter":
                    handleFilter(fullCommand);
                    break;
                case "find":
                    handleFind(fullCommand);
                    break;
                case "list":
                    handleList();
                    break;
                case "todo":
                    handleTodo(fullCommand);
                    break;
                case "deadline":
                    handleDeadline(fullCommand);
                    break;
                case "event":
                    handleEvent(fullCommand);
                    break;
                case "delete":
                    handleDelete(fullCommand);
                    break;
                default:
                    ui.printMessage("(try: todo, deadline, event, list, delete, or bye)");
                }
            } catch (Exception e) {
                ui.showError(e.getMessage());
            }
            ui.showLine();
        }
    }

    /**
     * Handles the 'filter' command by filtering tasks based on the given criteria.
     * Supported criteria: todo, deadline, event, done, undone.
     *
     * @param input The full command string (e.g., "filter done").
     */
    private void handleFilter(String input) {
        String criteria = input.substring(6).trim().toLowerCase();
        ArrayList<Task> filtered = tasks.filterTasks(criteria);
        if (filtered.isEmpty()) {
            ui.printMessage("No tasks matching filter: " + criteria);
        } else {
            ui.printMessage("Here are the filtered tasks:");
            for (int i = 0; i < filtered.size(); i++) {
                ui.printMessage((i + 1) + ". " + filtered.get(i));
            }
        }
    }

    /**
     * Handles the 'find' command by searching for tasks that contain the keyword.
     *
     * @param input The full command string (e.g., "find book").
     */
    private void handleFind(String input) {
        // We assume 'find ' is 5 characters
        if (input.length() <= 5) {
            ui.printMessage("What am I looking for? Usage: find <keyword>");
            return;
        }

        String keyword = input.substring(5).trim();
        ArrayList<Task> foundTasks = tasks.findTasks(keyword);

        if (foundTasks.isEmpty()) {
            ui.printMessage("I couldn't find any tasks matching: " + keyword);
        } else {
            ui.printMessage("Here are the matching tasks in your list:");
            for (int i = 0; i < foundTasks.size(); i++) {
                ui.printMessage((i + 1) + "." + foundTasks.get(i));
            }
        }
    }

    private void handleList() {
        if (tasks.getSize() == 0) {
            ui.printMessage("Your list is empty!");
            return;
        } else {

            for (int i = 0; i < tasks.getSize(); i++) {
                ui.printMessage((i + 1) + "." + tasks.getTask(i));
            }
        }
    }

    private void handleTodo(String input) {
        String desc = input.substring(4).trim();
        if (desc.isEmpty()) {
            ui.printMessage("Wait! The description of a todo cannot be empty.");
            return;
        }
        addTask(new Todo(desc));
    }

    private void handleDeadline(String input) {
        try {
            String rest = input.substring(9).trim();
            int byIndex = rest.indexOf(" /by ");
            String desc = rest.substring(0, byIndex).trim();
            String by = rest.substring(byIndex + 5).trim();
            addTask(new Deadline(desc, by));
        } catch (Exception e) {
            ui.printMessage("Format: deadline <desc> /by yyyy-mm-dd");
        }
    }

    private void handleEvent(String input) {
        try {
            String rest = input.substring(6).trim();
            int fromIndex = rest.indexOf(" /from ");
            int toIndex = rest.indexOf(" /to ");
            String desc = rest.substring(0, fromIndex).trim();
            String from = rest.substring(fromIndex + 7, toIndex).trim();
            String to = rest.substring(toIndex + 5).trim();
            addTask(new Event(desc, from, to));
        } catch (Exception e) {
            ui.printMessage("Format: event <desc> /from yyyy-mm-dd /to yyyy-mm-dd");
        }
    }

    private void handleDelete(String input) {
        int idx = Parser.parseIndex(input.substring(6)) - 1;
        if (idx >= 0 && idx < tasks.getSize()) {
            Task removed = tasks.deleteTask(idx);
            storage.saveTasks(tasks.getTasks());
            ui.printMessage("Noted. I've removed this task:\n  " + removed);
            ui.printMessage("Now you have " + tasks.getSize() + " tasks in the list.");
        } else {
            ui.printMessage("Invalid task number!");
        }
    }

    private void addTask(Task t) {
        tasks.addTask(t);
        storage.saveTasks(tasks.getTasks());
        ui.printMessage("Got it. I've added this task:\n  " + t);
        ui.printMessage("Now you have " + tasks.getSize() + " tasks in the list.");
    }

    /**
     * Entry point of the application
     */
    public static void main(String[] args) {
        new Kiki("data/kiki.txt").run();
    }
}
