package kiki.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that needs to be done before a specific date.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");
    private LocalDate end;

    /**
     * Creates a new instance of the class.
     */
    public Deadline(String description, String end) {
        super(description);
        this.end = LocalDate.parse(end);
    }

    /**
     * Returns the requested data.
     * @return The value of the requested field.
     */
    public LocalDate getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + end.format(OUTPUT_FORMAT) + ")";
    }
}
