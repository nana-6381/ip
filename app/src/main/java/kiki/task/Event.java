package kiki.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that occurs within a specific time range.
 */
public class Event extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");
    private LocalDate start;
    private LocalDate end;

    /**
     * Creates a new instance of the class.
     */
    public Event(String desc, String start, String end) {
        super(desc);
        this.start = LocalDate.parse(start);
        this.end = LocalDate.parse(end);
    }

    /**
     * Returns the requested data.
     * @return The value of the requested field.
     */
    public LocalDate getStart() {
        return start;
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
        return "[E]" + super.toString() + " (from: " + start.format(OUTPUT_FORMAT)
                + " to: " + end.format(OUTPUT_FORMAT) + ")";
    }
}
