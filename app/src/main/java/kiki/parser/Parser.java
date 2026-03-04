package kiki.parser;

public class Parser {
    public static String getCommandWord(String input) {
        return input.split(" ")[0].toLowerCase();
    }

    public static int parseIndex(String text) {
        try {
            int n = Integer.parseInt(text.trim());
            return n <= 0 ? -1 : n;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
