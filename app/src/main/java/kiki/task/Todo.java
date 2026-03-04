package kiki.task;

/**
 * Represents a simple task without any date/time.
 */
public class Todo extends Task{

    /**
     * Creates a new instance of the class.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

}
