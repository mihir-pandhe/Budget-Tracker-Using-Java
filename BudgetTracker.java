import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {
    String description;
    double amount;
    String category;
    LocalDate date;

    Transaction(String description, double amount, String category, LocalDate date) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " - " + category + " - " + description + ": $"
                + amount;
    }
}

class BudgetTracker {
    private List<Transaction> transactions;

    BudgetTracker() {
        transactions = new ArrayList<>();
    }

    void addTransaction(String description, double amount, String category, LocalDate date) {
        transactions.add(new Transaction(description, amount, category, date));
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

    void viewMonthlyReport(int year, int month) {
        System.out.println("Monthly Report for " + year + "-" + (month < 10 ? "0" : "") + month);
        double income = 0;
        double expenses = 0;
        for (Transaction transaction : transactions) {
            if (transaction.date.getYear() == year && transaction.date.getMonthValue() == month) {
                if (transaction.amount > 0) {
                    income += transaction.amount;
                } else {
                    expenses += transaction.amount;
                }
            }
        }
        System.out.println("Total Income: $" + income);
        System.out.println("Total Expenses: $" + expenses);
        System.out.println("Net Balance: $" + (income + expenses));
    }

    public static void main(String[] args) {
        BudgetTracker tracker = new BudgetTracker();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Expense");
            System.out.println("2. Add Income");
            System.out.println("3. View Transactions");
            System.out.println("4. View Balance");
            System.out.println("5. View Monthly Report");
            System.out.println("6. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.print("Enter expense description: ");
                String description = scanner.nextLine();
                System.out.print("Enter expense amount: ");
                double amount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter expense category: ");
                String category = scanner.nextLine();
                System.out.print("Enter date (yyyy-mm-dd): ");
                String dateString = scanner.nextLine();
                LocalDate date = LocalDate.parse(dateString);
                tracker.addTransaction(description, -amount, category, date);
                System.out.println("Expense added.");
            } else if (choice == 2) {
                System.out.print("Enter income description: ");
                String description = scanner.nextLine();
                System.out.print("Enter income amount: ");
                double amount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter income category: ");
                String category = scanner.nextLine();
                System.out.print("Enter date (yyyy-mm-dd): ");
                String dateString = scanner.nextLine();
                LocalDate date = LocalDate.parse(dateString);
                tracker.addTransaction(description, amount, category, date);
                System.out.println("Income added.");
            } else if (choice == 3) {
                tracker.viewTransactions();
            } else if (choice == 4) {
                double balance = tracker.calculateBalance();
                System.out.println("Current balance: $" + balance);
            } else if (choice == 5) {
                System.out.print("Enter year: ");
                int year = scanner.nextInt();
                System.out.print("Enter month: ");
                int month = scanner.nextInt();
                tracker.viewMonthlyReport(year, month);
            } else if (choice == 6) {
                break;
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }

        scanner.close();
    }
}
