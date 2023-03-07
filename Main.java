import java.util.Scanner;

class Main {
    public static void main(String[] args) {

        String username = "user";
        String password = "password";

        Scanner scanner = new Scanner(System.in);

        boolean isLoggedIn = false;
        for (int i = 1; i <= 3 && !isLoggedIn; i++) {
            System.out.println("Please enter your username:");
            String inputUsername = scanner.nextLine();

            System.out.println("Please enter your password:");
            String inputPassword = scanner.nextLine();

            if (inputUsername.equals(username) && inputPassword.equals(password)) {
                System.out.println("Login successful!");
                Interface I = new Interface();
                isLoggedIn = true;
            } else {
                System.out.println("Incorrect username or password. Please try again.");
            }
        }

        if (!isLoggedIn) {
            System.out.println("You have exceeded the maximum number of login attempts. Goodbye.");
            System.exit(0);
        }
        scanner.close();
    }
}
