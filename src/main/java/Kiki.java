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

        Task[] tasks = new Task[MAX_ITEMS];
        int taskCount = 0;

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
                if (taskCount == 0) {
                    System.out.println("(Oops, nothing here yet)");
                } else {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "." + tasks[i]);
                    }
                }
                continue;
            }

            if (input.toLowerCase().startsWith("mark ")) {
                int index = parseIndex(input.substring(5));
                if (index == -1 || index > taskCount) {
                    System.out.println("(hmm… which task number is that?)");
                    continue;
                }

                Task t = tasks[index - 1];
                t.setDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + t);
                continue;
            }

            if (input.toLowerCase().startsWith("unmark ")) {
                int index = parseIndex(input.substring(7));
                if (index == -1 || index > taskCount) {
                    System.out.println("(hmm… which task number is that?)");
                    continue;
                }

                Task t = tasks[index - 1];
                t.setNotDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + t);
                continue;
            }

            // Level-4

            if (input.toLowerCase().startsWith("todo ")) {
                String desc = input.substring(5).trim();
                if (desc.isEmpty()) {
                    System.out.println("(todo needs a description!)");
                    continue;
                }

                taskCount = addTask(tasks, taskCount, new Todo(desc), crowdedListThreshold);
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

                taskCount = addTask(tasks, taskCount, new Deadline(desc, by), crowdedListThreshold);
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

                taskCount = addTask(tasks, taskCount, new Event(desc, from, to), crowdedListThreshold);
                continue;
            }

            // If none matched:
            System.out.println("(try: todo <task> | deadline <task> /by <when> | event <task> /from <start> /to <end>)");
        }

        scanner.close();
    }

    private static int addTask(Task[] tasks, int taskCount, Task task, int crowdedListThreshold) {
        if (taskCount >= MAX_ITEMS) {
            System.out.println("(uh oh... I'm at full capacity :<)");
            return taskCount;
        }

        tasks[taskCount] = task;
        taskCount++;

        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");

        // Kiki personality extras (cosmetic only)
        if (taskCount == crowdedListThreshold + 1) {
            System.out.println("(ok wow… your list is getting kinda real now 😄)");
        }
        if (taskCount == 95) {
            System.out.println("(psst... we're close to 100 items, watchout!)");
        }

        return taskCount;
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
