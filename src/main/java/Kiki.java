import java.util.Scanner;

public class Kiki {
    // Maximum number of items on the list
    private static final int MAX_ITEMS = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String nameAlt = "(づ｡◕‿‿◕｡)づ  K i k i\n";
        String logo = " _  __ _ _    _\n"
                + "| |/ /(_) | _(_)\n"
                + "| ' / | | |/ / |\n"
                + "| . \\ | |   <| |\n"
                + "|_|\\_\\|_|_|\\_\\_|\n";
        System.out.println("Hey what's up, from\n" + logo);
        System.out.println("How can I help you today?");

        // Level-2 Personalisation
        String[] items = new String[MAX_ITEMS];
        int itemCount = 0;
        int crowdedListThreshold = 10;

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("Bye, hope to see you soon.");
                System.out.println("- " + nameAlt);
                break;
            }

            // Level-2: item-storing behaviour
            if (input.equalsIgnoreCase("list")) {
                if (itemCount == 0) {
                    System.out.println("(Oops, nothing here yet)");
                } else {
                    for (int i = 0; i < itemCount; i++) {
                        System.out.println((i + 1) + ". " + items[i]);
                    }
                }
                continue;
            }

            if (itemCount < MAX_ITEMS) {
                items[itemCount] = input;
                itemCount++;
                System.out.println("added: " + input);

                // Personality comment after threshold
                if (itemCount == 95) {
                    System.out.println("(psst... we're close to 100 items, watchout!)");
                }
            } else {
                System.out.println("(uh oh... I'm at full capacity :<)");
            }

            if (input.isEmpty()) {
                System.out.println("(say something please)");
                continue;
            }
        }

        scanner.close();
    }
}
