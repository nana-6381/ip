import java.util.Scanner;

public class Kiki {
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

        // Kiki's personality trait
        String lastInput = "";
        int repeatCount = 0;

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("Bye, hope to see you soon.");
                System.out.println("- " + nameAlt);
                break;
            }

            // Level-1: echo behaviour
            System.out.println(input);

            if (input.equals(lastInput)) {
                repeatCount++;
            } else {
                repeatCount = 1;
                lastInput = input;
            }

            if (repeatCount == 6) {
                System.out.println("(...okay, I get it already -.-)");
            }
        }

        scanner.close();
    }
}
