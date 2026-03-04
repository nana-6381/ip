import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task{
    private LocalDate end;
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Deadline(String description, String end) {
        super(description);
        this.end = LocalDate.parse(end);
    }

    public LocalDate getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + end.format(OUTPUT_FORMAT) + ")";
    }
}
