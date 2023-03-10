import java.util.Scanner;

class Interface {

    Operations op = new Operations();

    Interface() {
        while (true) {
            System.out.println("\nWelcome to the User!");
            System.out.println("What do you want to do?");
            System.out.println("------------------------------");
            System.out.println("1. Traction History");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Deposit Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. Exit");
            System.out.println("Please enter your choice:");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    op.transactionHistory();
                    break;
                case 2:
                    op.withdrawMoney();
                    break;
                case 3:
                    op.depositMoney();
                    break;
                case 4:
                    op.transferMoney();
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            
        }
    }
    
}
