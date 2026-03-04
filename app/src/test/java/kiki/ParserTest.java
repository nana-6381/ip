package kiki;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import kiki.parser.Parser;

public class ParserTest {
    @Test
    public void parseIndex_validInput_success() {
        // Test normal case
        assertEquals(1, Parser.parseIndex("1"));
        // Test with extra spaces
        assertEquals(5, Parser.parseIndex("  5  "));
    }

    @Test
    public void parseIndex_invalidInput_returnsMinusOne() {
        // Test non-numeric
        assertEquals(-1, Parser.parseIndex("abc"));
        // Test negative numbers (should be invalid for task indices)
        assertEquals(-1, Parser.parseIndex("-5"));
        // Test zero
        assertEquals(-1, Parser.parseIndex("0"));
    }
}