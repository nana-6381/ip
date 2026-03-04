package kiki.task;

import java.util.ArrayList;

/**
 * Contains the task list and provides operations to modify it.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Creates a new instance of the class.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a new instance of the class.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a task from the list.
     * @param index The position of the task in the list.
     * @return The task that was removed.
     */
    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int getSize() {
        return tasks.size();
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }
}
