package kiki.parser;

/**
 * Parses user input to extract command words and indices.
 */
public class Parser {

    /**
     * Parses the user input and returns the corresponding command.
     *
     * @param input The full string input from the user.
     * @return A string representing the command type in lowercase.
     */
    public static String getCommandWord(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "";
        }
        return input.trim().split(" ")[0].toLowerCase();
    }

    /**
     * Converts a string into a valid 1-based index for the task list.
     *
     * @param text The string to be parsed (e.g., "5").
     * @return The integer index, or -1 if the input is not a valid positive number.
     */
    public static int parseIndex(String text) {
        try {
            int n = Integer.parseInt(text.trim());
            return n <= 0 ? -1 : n;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
