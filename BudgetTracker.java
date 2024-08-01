import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {
    String description;
    double amount;

    Transaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return description + ": $" + amount;
    }
}

class BudgetTracker {
    private List<Transaction> transactions;

    BudgetTracker() {
        transactions = new ArrayList<>();
    }

    void addTransaction(String description, double amount) {
        transactions.add(new Transaction(description, amount));
    }

    void viewTransactions() {
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    double calculateBalance() {
        double balance = 0;
        for (Transaction transaction : transactions) {
            balance += transaction.amount;
        }
        return balance;
    }

    public static void main(String[] args) {
        BudgetTracker tracker = new BudgetTracker();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Expense");
            System.out.println("2. Add Income");
            System.out.println("3. View Transactions");
            System.out.println("4. View Balance");
            System.out.println("5. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter expense description: ");
                String description = scanner.nextLine();
                System.out.print("Enter expense amount: ");
                double amount = scanner.nextDouble();
                tracker.addTransaction(description, -amount);
                System.out.println("Expense added.");
            } else if (choice == 2) {
                System.out.print("Enter income description: ");
                String description = scanner.nextLine();
                System.out.print("Enter income amount: ");
                double amount = scanner.nextDouble();
                tracker.addTransaction(description, amount);
                System.out.println("Income added.");
            } else if (choice == 3) {
                tracker.viewTransactions();
            } else if (choice == 4) {
                double balance = tracker.calculateBalance();
                System.out.println("Current balance: $" + balance);
            } else if (choice == 5) {
                break;
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }

        scanner.close();
    }
}
