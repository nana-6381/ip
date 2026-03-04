package kiki;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import kiki.task.Todo;

public class TodoTest {
    @Test
    public void testStringConversion() {
        Todo todo = new Todo("read book");
        // Check initial state
        assertEquals("[T][ ] read book", todo.toString());

        // Check state after marking done
        todo.setDone();
        assertEquals("[T][X] read book", todo.toString());
    }
}