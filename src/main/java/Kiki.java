import java.util.ArrayList;
import java.util.Scanner;

public class Kiki {

    private static final int MAX_ITEMS = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String nameAlt = "(づ｡◕‿‿◕｡)づ  K i k i\n";
        String logo = " _  __ _ _    _\n"
                + "| |/ /(_) | _(_)\n"
                + "| ' / | | |/ / |\n"
                + "| . \\ | |   <| |\n"
                + "|_|\\_\\|_|_|\\_\\_|\n";

        ArrayList<Task> tasks = new ArrayList<>();
        int crowdedListThreshold = 10;

        System.out.println("Hey what's up, from\n" + logo);
        System.out.println("How can I help you today?");

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("(say something please)");
                continue;
            }

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("Bye, hope to see you soon.");
                System.out.println("- " + nameAlt);
                break;
            }

            if (input.equalsIgnoreCase("list")) {
                if (tasks.isEmpty()) {
                    System.out.println("(Oops, nothing here yet)");
                } else {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                }
                continue;
            }

            if (input.toLowerCase().startsWith("mark ")) {
                int index = parseIndex(input.substring(5));
                if (index == -1 || index > tasks.size()) {
                    System.out.println("(hmm… which task number is that?)");
                    continue;
                }

                Task t = tasks.get(index - 1);
                t.setDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + t);
                continue;
            }

            if (input.toLowerCase().startsWith("unmark ")) {
                int index = parseIndex(input.substring(7));
                if (index == -1 || index > tasks.size()) {
                    System.out.println("(hmm… which task number is that?)");
                    continue;
                }

                Task t = tasks.get(index - 1);
                t.setNotDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + t);
                continue;
            }

            // Level-6: delete
            if (input.toLowerCase().startsWith("delete ")) {
                int index = parseIndex(input.substring(7));
                if (index == -1 || index > tasks.size()) {
                    System.out.println("(please give a valid task number to delete)");
                    continue;
                }

                Task removed = tasks.remove(index - 1);
                System.out.println("Noted. I've removed this task:");
                System.out.println("  " + removed);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                continue;
            }

            // Level-4: add tasks
            if (input.toLowerCase().startsWith("todo ")) {
                String desc = input.substring(5).trim();
                if (desc.isEmpty()) {
                    System.out.println("(todo needs a description!)");
                    continue;
                }

                addTask(tasks, new Todo(desc), crowdedListThreshold);
                continue;
            }

            if (input.toLowerCase().startsWith("deadline ")) {
                String rest = input.substring(9).trim();
                int byIndex = rest.indexOf(" /by ");
                if (byIndex == -1) {
                    System.out.println("(format: deadline <task> /by <when>)");
                    continue;
                }

                String desc = rest.substring(0, byIndex).trim();
                String by = rest.substring(byIndex + 5).trim();

                if (desc.isEmpty() || by.isEmpty()) {
                    System.out.println("(format: deadline <task> /by <when>)");
                    continue;
                }

                addTask(tasks, new Deadline(desc, by), crowdedListThreshold);
                continue;
            }

            if (input.toLowerCase().startsWith("event ")) {
                String rest = input.substring(6).trim();

                int fromIndex = rest.indexOf(" /from ");
                int toIndex = rest.indexOf(" /to ");

                if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
                    System.out.println("(format: event <task> /from <start> /to <end>)");
                    continue;
                }

                String desc = rest.substring(0, fromIndex).trim();
                String from = rest.substring(fromIndex + 7, toIndex).trim();
                String to = rest.substring(toIndex + 5).trim();

                if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                    System.out.println("(format: event <task> /from <start> /to <end>)");
                    continue;
                }

                addTask(tasks, new Event(desc, from, to), crowdedListThreshold);
                continue;
            }

            // If none matched:
            System.out.println("(try: todo <task> | deadline <task> /by <when> | event <task> /from <start> /to <end> | mark N | unmark N | delete N | list | bye)");
        }

        scanner.close();
    }

    private static void addTask(ArrayList<Task> tasks, Task task, int crowdedListThreshold) {
        if (tasks.size() >= MAX_ITEMS) {
            System.out.println("(uh oh... I'm at full capacity :<)");
            return;
        }

        tasks.add(task);

        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");

        // Kiki personality extras (cosmetic only)
        if (tasks.size() == crowdedListThreshold + 1) {
            System.out.println("(ok wow… your list is getting kinda real now 😄)");
        }
        if (tasks.size() == 95) {
            System.out.println("(psst... we're close to 100 items, watchout!)");
        }
    }

    private static int parseIndex(String text) {
        try {
            int n = Integer.parseInt(text.trim());
            return n <= 0 ? -1 : n;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
