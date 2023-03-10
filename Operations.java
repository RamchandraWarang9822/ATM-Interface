import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

class Operations {
    double balance;
    String filename = "transaction_record.csv";
    String status;

    Operations() {
        File file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        balance = readBalanceFromCsv(filename);
    }

    private double readBalanceFromCsv(String filename) {
        double balance = 0.0;
        try (CSVReader reader = new CSVReader(new FileReader(filename))) {
            String[] nextRecord;

            while ((nextRecord = reader.readNext()) != null) {
                String fourthValue = nextRecord[3];
                balance += Double.parseDouble(fourthValue);
            }
        } catch (IOException | CsvValidationException | NumberFormatException e) {
            e.printStackTrace();
        }
        return balance;
    }

    void updateRecord(String transactionType, double amount, String status) {
        updateRecord(transactionType, amount, "Self", status);
    }

    void updateRecord(String transactionType, double amount, String toWhom, String status) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String timestamp = formatter.format(now);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            String record = timestamp + "," + transactionType + "," + amount + "," + balance + "," + toWhom + ","
                    + status + "\n";
            writer.write(record);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the transaction record file.");
            e.printStackTrace();
        }
    }

    void withdrawMoney() {
        System.out.println("Enter the amount to withdraw:");
        Scanner scanner = new Scanner(System.in);
        double amount = scanner.nextDouble();

        if (amount > balance) {
            System.out.println("Insufficient Balance");
            status = "Failed";
        } else {
            balance -= amount;
            System.out.println("Withdrawal Successful");
            status = "Success";
        }

        updateRecord("Withdrawal", amount, status);
        status = null;
    }

    void depositMoney() {
        System.out.println("Enter the amount to deposit:");
        Scanner scanner = new Scanner(System.in);
        double amount = scanner.nextDouble();

        balance += amount;
        System.out.println("Deposit Successful");

        updateRecord("Deposit", amount, status = "Success");
        status = null;
    }

    void transferMoney() {
        System.out.println("Enter the amount to transfer:");
        Scanner scanner = new Scanner(System.in);
        double amount = scanner.nextDouble();

        if (amount > balance) {
            System.out.println("Insufficient Balance");
            status = "Failed";
        } else {
            balance -= amount;
            System.out.println("Transfer Successful");
            status = "Success";
        }

        updateRecord("Transfer", amount, "Other", status);
        status = null;
    }

    void transactionHistory() {
        System.out.println("Transaction History:");
        System.out.printf("%-20s%-15s%-15s%-15s%-15s%s%n",
                "Timestamp", "Transaction", "Amount", "Balance", "To/From", "Status");
        System.out.println("---------------------------------------------------------------------------------------------------------");
    
        try (CSVReader reader = new CSVReader(new FileReader(filename))) {
            String[] nextRecord;
            while ((nextRecord = reader.readNext()) != null) {
                String timestamp = nextRecord[0];
                String transactionType = nextRecord[1];
                String amount = nextRecord[2];
                String balance = nextRecord[3];
                String toWhom = nextRecord[4];
                String status = nextRecord[5];
    
                System.out.printf("%-20s%-15s%-15s%-15s%-15s%s%n",
                        timestamp, transactionType, amount, balance, toWhom, status);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}
