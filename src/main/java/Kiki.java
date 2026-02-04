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

            // Handle empty input first (so we don't store blank tasks)
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
                        System.out.println((i + 1) + ". " + tasks[i]);
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

            // Otherwise: treat as a new task to add (Level-2 behavior continues)
            if (taskCount >= MAX_ITEMS) {
                System.out.println("(uh oh... I'm at full capacity :<)");
                continue;
            }

            tasks[taskCount] = new Task(input);
            taskCount++;
            System.out.println("added: " + input);

            // Extra~ behavior after “more than N tasks” (cosmetic only)
            if (taskCount == crowdedListThreshold + 1) {
                System.out.println("(ok wow… your list is getting kinda real now 😄)");
            }

            if (taskCount == 95) {
                System.out.println("(psst... we're close to 100 items, watchout!)");
            }
        }

        scanner.close();
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