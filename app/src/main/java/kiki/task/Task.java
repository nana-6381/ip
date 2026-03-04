package kiki.task;

/**
 * Represents a task with a description and completion status.
 */
public class Task {
    private final String description;
    private boolean isDone;

    /**
     * Creates a new instance of the class.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as completed.
     */
    public void setDone() {
        this.isDone = true;
    }

    public void setNotDone() {
        this.isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the requested data.
     * @return The value of the requested field.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the status icon based on whether the task is done.
     * @return "X" if done, " " if not done.
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
