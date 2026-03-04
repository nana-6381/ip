package kiki;

import kiki.task.*;
import kiki.storage.Storage;
import kiki.ui.Ui;
import kiki.parser.Parser;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import kiki.task.TaskList;
import kiki.task.Task;
import kiki.task.Todo;
import kiki.task.Deadline;
import kiki.task.Event;
import kiki.ui.Ui;
import kiki.storage.Storage;
import kiki.parser.Parser;

public class Kiki {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Kiki(String filePath) {
        ui = new Ui();
        try {
            // Load existing tasks into the TaskList
            tasks = new TaskList(Storage.loadTasks());
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

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
                if (commandWord.equals("bye")) {
                    isExit = true;
                    ui.printMessage("Bye, hope to see you soon.\n- (づ｡◕‿‿◕｡)づ  K i k i");
                } else if (commandWord.equals("list")) {
                    handleList();
                } else if (commandWord.equals("todo")) {
                    handleTodo(fullCommand);
                } else if (commandWord.equals("deadline")) {
                    handleDeadline(fullCommand);
                } else if (commandWord.equals("event")) {
                    handleEvent(fullCommand);
                } else if (commandWord.equals("delete")) {
                    handleDelete(fullCommand);
                } else {
                    ui.printMessage("(try: todo, deadline, event, list, delete, or bye)");
                }
            } catch (Exception e) {
                ui.showError(e.getMessage());
            }
            ui.showLine();
        }
    }

    private void handleList() {
        if (tasks.getSize() == 0) {
            ui.printMessage("Your list is empty!");
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

    public static void main(String[] args) {
        new Kiki("data/kiki.txt").run();
    }
}