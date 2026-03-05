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

    /**
     * Filters tasks in the list based on the given criteria.
     * Supported criteria: todo, deadline, event, done, undone.
     *
     * @param criteria The filter criteria to apply.
     * @return An ArrayList containing all tasks that match the criteria.
     */
    public ArrayList<Task> filterTasks(String criteria) {
        ArrayList<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            switch (criteria) {
            case "todo":
                if (t instanceof Todo) {
                    result.add(t);
                }
                break;
            case "deadline":
                if (t instanceof Deadline) {
                    result.add(t);
                }
                break;
            case "event":
                if (t instanceof Event) {
                    result.add(t);
                }
                break;
            case "done":
                if (t.isDone()) {
                    result.add(t);
                }
                break;
            case "undone":
                if (!t.isDone()) {
                    result.add(t);
                }
                break;
            default:
                break;
            }
        }
        return result;
    }

    /**
     * Searches for tasks in the list that contain the given keyword in their description.
     *
     * @param keyword The sequence of characters to search for.
     * @return An ArrayList containing all tasks that match the keyword.
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
}
